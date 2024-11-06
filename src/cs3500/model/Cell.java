package cs3500.model;

/**
 * Representation of a cell in a ThreeTrios grid.
 * @param <C> card.
 */
public class Cell<C extends Card> {
  Player owner = null;
  C card;
  final CellState cellstate;

  private boolean cardPlayed = false;

  /**
   * Constructor for a cell.
   * @param state the cell state for the cell.
   */
  public Cell(CellState state) {
    this.cellstate = state;
    if (cellstate == CellState.HOLE) {
      card = null;
    }
  }

  public Cell(CellState state, C card) {
    this.cellstate = state;
    if (cellstate == CellState.HOLE) {
      card = null;
    }
    this.card = card;
  }

  /**
   * Sets the card for a cell and original owner of a cell.
   * @param card the card that the cell is being set as.
   * @param player the player that is setting the cell.
   */
  public void setCard(C card, Player player) {
    if (cellstate == CellState.HOLE) {
      throw new IllegalArgumentException("model.Card cannot be placed in hole");
    }
    if (cardPlayed) {
      throw new IllegalArgumentException("Space has already been played to");
    }
    this.card = card;
    cardPlayed = true;
    this.owner = player;
  }

  /**
   * Sets the owner for a cell.
   * @param player the player that is the new owner of the cell.
   */
  public void setOwner(Player player) {
    if (cellstate == CellState.HOLE) {
      throw new IllegalArgumentException("model.Card cannot be placed in hole");
    }
    owner = player;
  }

  public Player getOwner() {
    return owner;
  }

  public CellState getCellState() {
    return cellstate;
  }

  public C getCard() {
    return card;
  }
}
