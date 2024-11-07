package cs3500.controller.strategy;

import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * Class that performs the strategy of playing the card that is least likely to be flipped.
 */
public class CardLessLikelyFlippedStrategy implements ThreeTriosStrategy {
  @Override
  public int[] choosePosition(GameGrid model, Player player) {
    return new int[0];
  }
}
