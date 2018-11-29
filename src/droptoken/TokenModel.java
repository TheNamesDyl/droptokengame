package droptoken;

import java.util.*;

/*
 * Token Model
 *
 * The model class for a connect four board game
 */
public class TokenModel {
   private int[][] gameBoard;
   private int currentPlayerTurn;
   private boolean gameOver;
   private List<Integer> turns;
   private static final int CONNECT_WIN = 4; // Changes size of board and the amount to win

   public TokenModel() {
      turns = new ArrayList<Integer>();
      gameBoard = new int[CONNECT_WIN][CONNECT_WIN];
      currentPlayerTurn = 1;
      gameOver = false;
   }

   /**
    *
    * Drops the token on the specified column
    *
    * @param column The column to drop the token on
    * @return true if this was done succesfully (i.e game is not over, column is not full), false otherwise
    */
   public boolean takeTurn(int column) {
      if (column > gameBoard.length) {
         return false;
      }
      column = column - 1;
      int row = getPossibleMove(column);
      if (row != -1 && !gameOver) {
         gameBoard[row][column] = currentPlayerTurn;
         turns.add(column + 1);
         gameOver = isConnectFour(row, column);
         currentPlayerTurn = (currentPlayerTurn % 2) + 1;
         return true;
      }
      return false;
   }

   public boolean isGameDrawn() {
      return isFullBoard();
   }

   public boolean isGameWon() {
      return gameOver;
   }

   /**
    * @return All the past turns made in this game
    */
   public List<Integer> getPlayerTurns() {
      return Collections.unmodifiableList(turns); // unmodifiable list to prevent rep exposure
   }

   @Override
   public String toString() {
      String result = "";
      for (int i = 0; i < gameBoard.length; i++) {
         result += "| ";
         for (int j = 0; j < gameBoard[i].length; j++) {
            result += gameBoard[i][j] + " ";
         }
         result += "\n";
      }
      result += "+--------\n";
      result += "  1 2 3 4";
      return result;
   }


   /*
    * Helper methods
    */

   /**
    * Checks on the diagonal win based on the main diagonals through the board. Works in this
    * particular use case of size 4 board.
    */
   private boolean isDiagonalWin(int currentPlayer) {
      boolean mainDiagonal = true;
      boolean reverseDiagonal = true;
      for(int i = 0; i < gameBoard.length; i++) {
         if(gameBoard[i][gameBoard.length - 1 - i] != currentPlayer) {
            reverseDiagonal = false;
         }
         if (gameBoard[i][i] != currentPlayer) {
            mainDiagonal = false;
         }
      }

      return mainDiagonal || reverseDiagonal;
   }

   private boolean isHorizontalWin(int row, int currentPlayer) {
      for (int i = 0; i < gameBoard.length; i++) {
         if (gameBoard[row][i] != currentPlayer) {
            return false;
         }
      }
      return true;
   }

   /**
    * Gets the possible move based on the column
    *
    * @param column The column to drop the token
    * @return -1 if no move is found (column is full) or the row if there is a valid move found
    */
   private int getPossibleMove(int column) {
      for (int i = gameBoard.length - 1; i >= 0; i--) {
         if (gameBoard[i][column] == 0) {
            return i;
         }
      }
      return -1;
   }

   private boolean isVerticalWin(int column, int player) {
      for (int i = gameBoard.length - 1; i >= 0; i--) {
         if (gameBoard[i][column] != player) {
            return false;
         }
      }

      return true;
   }

   private boolean isFullBoard() {
      for (int i = 0; i < gameBoard.length; i++) {
         for (int j = 0; j < gameBoard[i].length; j++) {
            if (gameBoard[i][j] == 0) {
               return false;
            }
         }
      }
      return true;
   }

   private boolean isConnectFour(int row, int column) {
      return isHorizontalWin(row, currentPlayerTurn) || isVerticalWin(column, currentPlayerTurn) || isDiagonalWin(currentPlayerTurn);
   }
}
