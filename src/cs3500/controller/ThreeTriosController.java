package cs3500.controller;

import cs3500.model.GameGridModel;
import cs3500.view.Graphics2DView;


public class ThreeTriosController {

  private GameGridModel gameGridModel;
  private Graphics2DView graphicsView;
  // takes in the player implementation
  private RedController redController;
  private BlueController blueController;

  private double width;
  private double height;

  public ThreeTriosController(GameGridModel gameGridModel,) {
    this.gameGridModel;

  }


  // controller can only take action when it is the players turn
  private void playersTurn() {

    if (gameGridModel == redController) {
//            return Graphics2DView.CardPlacement(, , height, width);
    }
  }

  // The controller must prevent a player from selecting cards in their opponent’s hand at all times.

  // The controller must ensure that the player has selected a card from their hand to play before they choose a
  // cell to play to. Once both have occurred and both are valid, then the player can make the move and pass over/switch
  // over to the other player.


  // The controller should ensure that its view stays up to date, even if the player is a machine and doesn’t interact
  // with the view directly. (use the toggleColor)


  // need to display the winning score

}
