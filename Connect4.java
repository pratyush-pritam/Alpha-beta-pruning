package ai;

class Connect4 {
    static final int ROWS = 6;
    static final int COLS = 7;
    private int currentPlayer;

    int[][] board = new int[ROWS][COLS];

    // [...] inside the Connect4 class
    public Connect4() {
        board = new int[6][7];
        currentPlayer = 1;
    }

    public boolean isWin() {
        // Check rows for a win
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row][col + 1] &&
                        board[row][col] == board[row][col + 2] && board[row][col] == board[row][col + 3]) {
                    return true;
                }
            }
        }

        // Check columns for a win
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col] &&
                        board[row][col] == board[row + 2][col] && board[row][col] == board[row + 3][col]) {
                    return true;
                }
            }
        }

        // Check diagonals for a win
        for (int col = 0; col < COLS - 3; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col + 1] &&
                        board[row][col] == board[row + 2][col + 2] && board[row][col] == board[row + 3][col + 3]) {
                    return true;
                }
            }
        }

        for (int col = 3; col < COLS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col - 1] &&
                        board[row][col] == board[row + 2][col - 2] && board[row][col] == board[row + 3][col - 3]) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isDraw() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }

        return true;
    }
    // [...] inside the Connect4 class

    public int minimax(int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (isWin()) {
            System.out.println("Player " + " wins!");

            return (isMaximizingPlayer) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        if (isDraw()) {
            System.out.println("It's a draw!");
            return 0;
        }
        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col = 0; col < COLS; col++) {
                if (isValidMove(col)) {
                    makeMove(1, col);
                    int eval = minimax(depth + 1, alpha, beta, false);
                    undoMove(1, col);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col = 0; col < COLS; col++) {
                if (isValidMove(col)) {
                    makeMove(2, col);
                    int eval = minimax(depth + 1, alpha, beta, true);
                    undoMove(2, col);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }
    // [...] inside the Connect4 class

    public void makeBestMove() {
        int bestCol = -1;
        int maxEval = Integer.MIN_VALUE;
        for (int col = 0; col < COLS; col++) {
            if (isValidMove(col)) {
                makeMove(1, col);
                int eval = minimax(0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                undoMove(1, col);
                if (eval > maxEval) {
                    maxEval = eval;
                    bestCol = col;
                }
            }
        }
        makeMove(1, bestCol);
    }

    public static void main(String[] args) {
        Connect4 game = new Connect4();
        game.makeBestMove();
    }

    public boolean makeMove(int column, int player) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][column] == 0) {
                board[row][column] = player;
                return true;
            }
        }

        return false;
    }

    public boolean undoMove(int column, int player) {
        for (int row = 0; row < ROWS; row++) {
            if (board[row][column] == player) {
                board[row][column] = 0;
                return true;
            }
        }

        return false;
    }

    public boolean isValidMove(int column) {
        return column >= 0 && column < board[0].length && board[0][column] == 0;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getWinner() {
        // Check rows for a win
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row][col + 1] &&
                        board[row][col] == board[row][col + 2] && board[row][col] == board[row][col + 3]) {
                    return board[row][col];
                }
            }
        }

        // Check columns for a win
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS - 3; row++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col] &&
                        board[row][col] == board[row + 2][col] && board[row][col] == board[row + 3][col]) {
                    return board[row][col];
                }
            }
        }

        // Check diagonals for a win
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row + 1][col + 1] &&
                        board[row][col] == board[row + 2][col + 2] && board[row][col] == board[row + 3][col + 3]) {
                    return board[row][col];
                }
            }
        }

        // Check anti-diagonals for a win
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] != 0 && board[row][col] == board[row - 1][col + 1] &&
                        board[row][col] == board[row - 2][col + 2] && board[row][col] == board[row - 3][col + 3]) {
                    return board[row][col];
                }
            }
        }

        // If no win was found, check for a draw
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == 0) {
                    return 0; // Game is not over yet
                }
            }
        }

        // If neither a win nor a draw was found, it means the game is over and ended in
        // a draw
        return 0;
    }
}