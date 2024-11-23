package cs3500.controller;


import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

public class MachinePlayer<C extends Card> implements IPlayer<C> {
  private final GameGrid<C> model;
  private final ThreeTriosStrategy<C> strategy;
  private final Player color;

  public MachinePlayer(GameGrid<C> model, ThreeTriosStrategy<C> strategy, Player color) {
    this.model = model;
    this.strategy = strategy;
    this.color = color;
  }

  @Override
  public void makeMove(int row, int col, int handIdx) {
    int[] move = strategy.choosePosition(model, color).get(0);
    model.playToGrid(move[0], move[1], move[2]);
  }

}
