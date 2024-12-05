package cs3500.model;


/**
 * This interface is used for the cells on the grid.
 */
public interface CellInterface {

  /**
   * Sets the card for a cell and original owner of a cell.
   *
   * @param card   the card that the cell is being set as.
   * @param player the player that is setting the cell.
   */
  void setCard(Card card, Player player);


  /**
   * Sets the owner for a cell.
   *
   * @param player the player that is the new owner of the cell.
   */
  void setOwner(Player player);

  /**
   * Gets the owner of the cell.
   *
   * @return the owner of the cell.
   */
  Player getOwner();

  /**
   * Gets the CellState of the cell.
   *
   * @return the CellState of the cell.
   */
  CellState getCellState();

  /**
   * Gets the current card occupying the cell.
   *
   * @return the card occupying the cell.
   */
  Card getCard();
}
