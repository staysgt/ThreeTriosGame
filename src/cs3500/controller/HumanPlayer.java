package cs3500.controller;

import java.util.Objects;

import cs3500.model.GameGrid;
import cs3500.model.Player;
import cs3500.model.Card;

/**
 * Representation of a human player.
 */
public class HumanPlayer<C extends Card> implements IPlayer<C> {
  private final GameGrid<C> model;

  public HumanPlayer(GameGrid<C> model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public void makeMove(int row, int col, int handIdx) {
    model.playToGrid(row, col, handIdx);
  }

}
