package cs3500.controller.strategy;

import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * Class that performs the strategy of playing the card that is least likely to be flipped.
 */
public class CardLessLikelyFlippedStrategy<C extends Card> implements ThreeTriosStrategy<C> {
  @Override
  public List<int[]> choosePosition(GameGrid<C> model, Player player) {
    // iterates through rows
    for (int row = 0; row < model.getBoard().length; row++) {
      // iterates through the columns of the grid
      for (int col = 0; col < model.getBoard()[0].length; col++) {
        // goes through each hand in the players hand

        for (int handIdx = 0; handIdx < model.getHand(player).size(); handIdx++) {

        }
      }

    }
    return null;
  }
}
