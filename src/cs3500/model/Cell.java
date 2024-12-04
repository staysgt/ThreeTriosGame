package cs3500.model;

/**
 * Representation of a cell in a ThreeTrios grid.
 */
public class Cell<C extends Card> implements CellInterface {
  private Player owner = null;
  private Card card = null;
  private final CellState cellstate;

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

  /**
   * Constructor for a cell.
   *
   * @param state state of the card.
   * @param card  card for the cell.
   * @param owner original owner of the cell.
   */

  public Cell(CellState state, Card card, Player owner) {
    this.cellstate = state;
    if (cellstate == CellState.HOLE) {
      card = null;
    }
    this.card = card;
    this.owner = owner;
  }

  @Override
  public void setCard(Card card, Player player) {
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


  @Override
  public void setOwner(Player player) {
    if (cellstate == CellState.HOLE) {
      throw new IllegalArgumentException("model.Card cannot be placed in hole");
    }
    owner = player;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public CellState getCellState() {
    return cellstate;
  }


  @Override
  public Card getCard() {
    return card;
  }
}
