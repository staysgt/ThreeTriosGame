package cs3500.controller.strategy;


import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * Interface for strategy for finding most desirable position.
 */
public interface ThreeTriosStrategy<C extends Card> {

  /**
   * Finds the best position to play to based on a strategy.
   *
   * @return the best position to play to.
   */
  int[] choosePosition(GameGrid<C> model, Player player);
}
