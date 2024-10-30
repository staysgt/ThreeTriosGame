package cs3500.model;

import java.util.List;

public interface GameGrid<C extends Card> {


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

  /**
   * Returns a copy of the hand in the game. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   * @return a new list containing the cards in the player one's hand or player
   * two hands in the same order as in the current state of the game.
   * @throws IllegalStateException if the game has not started
   *
   */
  List<C> getHand(Player player);

  /**
   * Returns if the game is over as specified by the implementation,
   * game will end if empty card cells are filled.
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();


  /**
   * This is used to see if the cell on the grid is empty or not.
   * @param x coordinate of grid
   * @param y coordinate of grid
   * @return true if cell is empty and false if cell isn't empty
   * @throws IllegalStateException if game has not started or game is over
   * @throws IllegalArgumentException if x or y < 0, or x > num of col, y > num of rows
   */
  boolean isCellPlayable(int x, int y);

  /**
   * This is used to see if the cell is a hole or not.
   * @param x coordinate of grid
   * @param y coordinate of grid
   * @return true if cell is a hole and false if cell is not a hole
   * @throws IllegalStateException if game has not started or game is over
   * @throws IllegalArgumentException if x or y < 0, or x > num of col, y > num of rows
   */
  boolean isCellHole(int x, int y);


  /**
   * Determines whose turn it is.
   * @return the player whose turn it is.
   * @throws IllegalStateException if game has not started or game is over
   */
  Player getTurn();


  /**
   * Return the winner of the game, or {@code null} if there is no winner. If the game is not
   * over, returns {@code null}.
   *
   * @return the winner, or null if there is no winner
   * @throws IllegalStateException if game has not started
   */
  Player getWinner();


  /**
   * Gets the current state of the board.
   * @return the current state of the board.
   * @throws IllegalStateException if game has not started or game is over
   */
  Cell[][] getBoard();



}
