package cs3500.model;

public class Cell<C extends Card> {
  Player owner = null;
  C card;
  final CellState cellstate;

  private boolean cardPlayed = false;

  public Cell (CellState state) {
    this.cellstate = state;
    if(cellstate == CellState.HOLE) {
      card = null;
    }
  }

  public void setCard(C card, Player player) {
    if(cellstate == CellState.HOLE) {
      throw new IllegalArgumentException("model.Card cannot be placed in hole");
    }
    if(cardPlayed) {
      throw new IllegalArgumentException("Space has already been played to");
    }
    this.card = card;
    cardPlayed = true;
    this.owner = player;
  }

  public void setOwner(Player player) {
    if(cellstate == CellState.HOLE) {
      throw new IllegalArgumentException("model.Card cannot be placed in hole");
    }
    owner = player;
  }

  public boolean isHole() {
    if (this.cellstate == CellState.HOLE) {
      return true;
    } else {
      return false;
    }
  }

  public Player getOwner() {
    return owner;
  }

  public CellState getCellstate() {
    return cellstate;
  }

  public C getCard() {
    return card;
  }
}
