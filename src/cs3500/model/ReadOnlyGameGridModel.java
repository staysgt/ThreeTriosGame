package cs3500.model;

import java.util.HashMap;
import java.util.List;

public interface ReadOnlyGameGridModel<C extends Card> {

  /**
   * Returns a copy of the hand in the game. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   * @return a new list containing the cards in the player one's hand or player
   *         two hands in the same order as in the current state of the game.
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
   * @param row coordinate of grid
   * @param col coordinate of grid
   * @return true if cell is empty and false if cell isn't empty
   * @throws IllegalStateException if game has not started or game is over
   * @throws IllegalArgumentException if x or y < 0, or x > num of col, y > num of rows
   */
  boolean isCellPlayable(int row, int col);

  /**
   * This is used to see if the cell is a hole or not.
   * @param row coordinate of grid
   * @param col coordinate of grid
   * @return true if cell is a hole and false if cell is not a hole
   * @throws IllegalStateException if game has not started or game is over
   * @throws IllegalArgumentException if x or y < 0, or x > num of col, y > num of rows
   */
  boolean isCellHole(int row, int col);



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
   * @throws IllegalStateException if the game is not over yet.
   */
  Player getWinner();


  /**
   * Gets the current state of the board.
   * @return the current state of the board.
   * @throws IllegalStateException if game has not started or game is over
   */
  Cell[][] getBoard();


  /**
   * This gets a specific players current score.
   * @param player a specific player
   * @return the specific players current score.
   * @throws IllegalStateException if game has not started or game is over
   */
  int getScore(Player player);

  /**
   * This gets how many cards a player can flip.
   * @param row coordinate of grid
   * @param col coordinate of grid
   * @param handIdx the desired handIdx
   * @param player the player who is doing the flipping.
   * @return the amount of cards a player can flip.
   * @throws IllegalStateException if game has not started or game is over
   */
  int cardsFlipped(int row, int col, int handIdx, Player player);

  /**
   * This tells if the play is legal or not.
   * @param row coordinate of grid
   * @param col coordinate of grid
   * @return true or false.
   * @throws IllegalStateException if game has not started or game is over
   */
  boolean legalPlay(int row, int col);


  /**
   * Gets the previous statuses of the game.
   * Integer is the move number, the correlated hashmap is a map of the game board and an array
   * of both lists of hand
   *
   * @return the previous statuses of the game.
   * @throws IllegalStateException if game has not started
   */
  HashMap<Integer, HashMap<Cell<C>[][], List<C>[]>> getGameStatuses();

}

