package cs3500.view;

/**
 * Features
 */
public interface Features {

  /**
   * Selects a card from a players hand.
   */
  void cardSelected(int cardIdx);


  /**
   * This selects the cell that the player chooses.
   */
  void cellSelected(int row, int col);

}
