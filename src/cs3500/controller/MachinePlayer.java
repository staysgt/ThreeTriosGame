package cs3500.controller;


import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * Class for the moves of a machine player.
 *
 * @param <C> generic type for a card.
 */
public class MachinePlayer<C extends Card> implements IPlayer<C> {
  private final GameGrid<C> model;
  private final ThreeTriosStrategy<C> strategy;
  private final Player color;

  /**
   * Constructor for a machine player.
   * @param model model that the player is altering.
   * @param strategy strategy the machine player is using.
   * @param color color player that the machine player is playing for.
   */
  public MachinePlayer(GameGrid<C> model, ThreeTriosStrategy<C> strategy, Player color) {
    this.model = model;
    this.strategy = strategy;
    this.color = color;
  }

  @Override
  public void makeMove(int row, int col, int handIdx) {
    // does not use this -> no action performed
  }

  @Override
  public void makeMove() {
    int[] move = strategy.choosePosition(model, color).get(0);
    model.playToGrid(move[0], move[1], move[2]);
  }

}
