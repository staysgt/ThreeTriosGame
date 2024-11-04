package cs3500.model;

import java.util.List;

/**
 * Representation of a game grid.
 * @param <C> card.
 */
public interface GameGrid<C extends Card> extends ReadOnlyGameGridModel {


  /**
   * Plays a card to the grid.
   * @param row x coordinate of grid.
   * @param col y coordinate of grid.
   * @param handIdx index of the card in the grid.
   * @throws IllegalStateException if game has not started or is over
   * @throws IllegalArgumentException if x or y < 0, or x > num of col, y > num of rows
   * @throws IllegalArgumentException if given coordinates are associated with a hole
   * @throws IllegalArgumentException if handIdx is <0 or greater than the number of cards in hand
   *
   */
  void playToGrid(int row, int col, int handIdx);


  /**
   * Starts a game with the given number of columns and rows.
   * @param cards desired cards if
   * @param cols desired number of columns in the game.
   * @param rows desired number of rows in the game.
   * @param rowConf configuration of the rows.
   * @throws IllegalStateException if game has already been started.
   * @throws IllegalArgumentException if cols or rows < 0
   * @throws IllegalArgumentException if given cards has null values.
   * @throws IllegalArgumentException if given cards is less than (num of spaces + 1)/2
   * @throws IllegalArgumentException if any provided values are null
   */
  void startGame(List<C> cards, int cols, int rows, List<String> rowConf);

}
