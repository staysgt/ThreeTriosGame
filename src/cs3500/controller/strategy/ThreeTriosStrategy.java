package cs3500.controller.strategy;


import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * Interface for strategy for finding most desirable position.
 */
public interface ThreeTriosStrategy<C extends Card> {

  /**
   * Finds the best positions to play to based on a strategy.
   *
   * @return a list of the best positions to play to.
   */
  List<int[]> choosePosition(GameGrid<C> model, Player player);

  /**
   * Converts the list of position into a string.
   *
   * @return a string version of the list.
   */
  String toString();
}
