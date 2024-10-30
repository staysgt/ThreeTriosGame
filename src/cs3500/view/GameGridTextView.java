package cs3500.view;

import cs3500.model.Card;
import cs3500.model.CellState;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;

/**
 * Creates a textual view of a GameGrid.
 * @param <C> card.
 */
public class GameGridTextView<C extends Card> implements TextView {
  private GameGrid<NESWCard> model;

  /**
   * Constructor for a gamegridtextview.
   * @param model model that the view is being created of.
   */
  public GameGridTextView(GameGrid model) {
    this.model = model;
  }

  @Override
  public void render() {
    StringBuilder sb = new StringBuilder();
    sb.append("Red Hand: " + model.getHand(Player.RED) + "\n");
    sb.append("Blue Hand: " + model.getHand(Player.BLUE) + "\n");
    for (int row = 0; row < model.getBoard()[0].length; row++) { // Rows come first
      for (int col = 0; col < model.getBoard().length; col++) { // Columns come second
        if (model.getBoard()[col][row].getCard() != null) {
          sb.append(model.getBoard()[col][row].getCard().getName());
        } else {
          if (model.getBoard()[col][row].getCellstate() == CellState.CARD_SPACE) {
            sb.append("_");
          } else {
            sb.append(" ");
          }
        }
      }
      sb.append("\n");
    }
    System.out.println(sb.toString());
  }

}
