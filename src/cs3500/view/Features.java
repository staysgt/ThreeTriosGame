package cs3500.view;

/**
 * This is the features interface which is used for the board.
 */
public interface Features {

  /**
   * This selects a card from a players hand.
   * @param cardIdx index of the card.
   */
  void cardSelected(int cardIdx);


  /**
   * This selects the cell that the player chooses.
   * @param row row of board.
   * @param col column of board.
   */
  void cellSelected(int row, int col);

}
