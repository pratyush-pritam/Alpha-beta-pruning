
package ai;

import ai.Connect4;
import java.util.Scanner;

public class stimulation {
    public static void main(String[] args) {
        // Initialize the Connect4 game
        Connect4 game = new Connect4();

        // Initialize the Scanner object
        Scanner input = new Scanner(System.in);

        // Variable to determine the current player
        int currentPlayer = 1;

        // Main game loop
        while (!game.isWin() && !game.isDraw()) {
            // Clear the console
            System.out.print("\033[H\033[2J");

            // Print the game board
            game.printBoard();

            // Prompt the current player for a move
            System.out.print("Player " + currentPlayer + ", enter a column number: ");

            // Read the user's input
            int column = input.nextInt();

            // Attempt to place a token in the specified column
            if (game.isValidMove(column)) {
                game.makeMove(column, currentPlayer);

                // Update the current player
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
            } else {
                System.out.println("Invalid move, please try again.");
            }
        }

        // Clear the console
        System.out.print("\033[H\033[2J");

        // Print the final game board
        game.printBoard();

        // Determine the winner
        int winner = game.getWinner();

        // Print the game result
        if (winner == 0) {
            System.out.println("It's a draw!");
        } else {
            System.out.println("Player " + winner + " wins!");
        }

        // Close the Scanner object
        input.close();
    }
}