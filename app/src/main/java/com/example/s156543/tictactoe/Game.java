package com.example.s156543.tictactoe;

// Class

import java.io.Serializable;
import android.view.View;



public class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    private Tile[][] board;

    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn
    private int movesPlayed;
    private Boolean gameOver;

    public Game() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = Tile.BLANK;

        playerOneTurn = true;
        gameOver = false;
    }

    // Process player's moves
    public Tile draw(int row, int column) {

        // Check if chosen spot is empty
        if (board[row][column] != Tile.BLANK)
            return Tile.INVALID;

        // Assign Cross or Circle to Tile, according to which player is playing.
        board[row][column] =  playerOneTurn ? Tile.CROSS : Tile.CIRCLE;

        // Check if a player has won
        gameOver = won(row, column);

        // Change player
        playerOneTurn = !playerOneTurn;
        return board[row][column];
    }

    // Checks if the game is won, by looking in relevant vertical and horizontal directions, and the diagonals
    private boolean won(int row, int column){
        boolean roweven = true;
        boolean columneven = true;
        boolean descdiageven = true;
        boolean ascdiageven = true;

        for(int i=0; i < BOARD_SIZE; i++){
            if (board[row][column] != board[row][i])
                roweven = false;

            if (board[row][column] != board[i][column])
                columneven = false;

            if (board[0][0] != board[i][i] || board[i][i] == Tile.BLANK)
                descdiageven = false;

            if (board[BOARD_SIZE - 1][0] != board[BOARD_SIZE - 1 - i][i] || board[BOARD_SIZE - 1 - i][i] == Tile.BLANK)
                ascdiageven = false;
        }

        return roweven || columneven || descdiageven || ascdiageven;
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }
    public Tile getTile(int x, int y) {
        return board[x][y];
    }

    public boolean getGameOver(){
        return gameOver;
    }
}
