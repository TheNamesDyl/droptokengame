package droptoken;

import java.util.List;

/*
 * TokenController
 *
 * This is the controller for TokenModel
 */
public class TokenController {

   private TokenModel model;

   public TokenController(TokenModel model) {
      this.model = model;
   }

   public String performGet() {
      List<Integer> turns = model.getPlayerTurns();
      String result = "";
      for (int i = 0; i < turns.size(); i++) {
         result += turns.get(i) + "\n";
      }
      return result;
   }

   /**
    * Receives input in the form "PUT 5" and performs a turn
    *
    * @return If input is invalid returns ERROR. If player has won or draw returns WIN or DRAW, if turn is successful
    * returns OK.
    */
   public String performPut(String input) {
      int column = getValidInput(input);
      if (column != -1 && !model.isGameDrawn() && !model.isGameWon()) {
         boolean success = model.takeTurn(column);
         if (model.isGameWon()) {
            return "WIN";
         } else if (model.isGameDrawn()) {
            return "DRAW";
         } else if (success) {
            return "OK";
         } else {
            return "ERROR";
         }
      } else {
         return "ERROR";
      }

   }

   private int getValidInput(String input) {
      // Checks that the arguments of the PUT was an int
      String[] split = input.split("\\s+");
      try {
         int value = Integer.parseInt(split[1]);
         return value;
      } catch (Exception e) {
         return -1;
      }
   }

   public String performBoard() {
      return model.toString();
   }
}
