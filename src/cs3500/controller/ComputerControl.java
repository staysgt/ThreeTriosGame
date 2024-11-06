package cs3500.controller;

import cs3500.model.Card;
import cs3500.model.GameGrid;

/**
 * Class that determines the best move for a player and makes that move.
 */
public class ComputerControl implements Computer {
  GameGrid<Card> model;

  public ComputerControl(GameGrid gameGrid) {
    model = gameGrid;

  }
}
