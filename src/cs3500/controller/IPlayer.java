package cs3500.controller;

import cs3500.model.Card;

/**
 * Representation of a player.
 */
public interface IPlayer<C extends Card> {

  /**
   * Makes a move for a player.
   */
  void makeMove();
}
