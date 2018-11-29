package droptoken;

import java.util.Scanner;

/*
 * TokenView
 *
 * This is the view for connect four
 */
public class TokenView {

   private TokenModel model;
   private TokenController controller;

   public TokenView(TokenModel model, TokenController controller) {
      this.model = model;
      this.controller = controller;
   }

   public void runDisplay() {
      Scanner scan = new Scanner(System.in);
      String input;
      do {
         input = scan.nextLine();
         String response = processInput(input);
         System.out.println(response);

      } while (!input.equals("EXIT"));
   }

   private String processInput(String input) {
      if (input.startsWith("PUT")) {
         return controller.performPut(input);
      } else if (input.equals("GET")) {
         return controller.performGet();
      } else if (input.equals("BOARD")) {
         return controller.performBoard();
      } else if (input.equals("EXIT")){
         return "";
      } else {
         return "ERROR";
      }
   }


   // The clients interaction
   public static void main(String[] args) {
      TokenModel model = new TokenModel();
      TokenController controller = new TokenController(model);
      TokenView view = new TokenView(model, controller);
      view.runDisplay();
   }
}
