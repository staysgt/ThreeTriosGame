package cs3500.controller;

import cs3500.model.Player;
import cs3500.model.Card;

/**
 * Representation of a human player.
 */
public class HumanPlayer<C extends Card> implements IPlayer<C> {
  private final Player color;

  public HumanPlayer(Player color) {
    this.color = color;
  }

  @Override
  public void makeMove() {
    // makes move

  }

}
