package cs3500.controller;

import cs3500.model.Card;

/**
 * Representation of a player.
 * Contains the move making action for a player.
 */
public interface IPlayer<C extends Card> {

  /**
   * Makes a move for a player.
   */
  void makeMove(int row, int col, int handIdx);

  /**
   * Makes a move for a mahcine player.
   */

  void makeMove();

}
