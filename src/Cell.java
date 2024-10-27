public class Cell {
  Player owner;
  Card card;
  final CellState cellstate;

  private boolean cardPlayed = false;

  public Cell (CellState state) {
    this.cellstate = state;
    if(cellstate == CellState.HOLE) {
      card = null;
    }
  }

  public void setCard(Card card) {
    if(cellstate == CellState.HOLE) {
      throw new IllegalArgumentException("Card cannot be placed in hole");
    }
    if(cardPlayed) {
      throw new IllegalArgumentException("Space has already been played to");
    }

    this.card = card;
    cardPlayed = true;
  }

  public void setOwner(Player player) {
    if(cellstate == CellState.HOLE) {
      throw new IllegalArgumentException("Card cannot be placed in hole");
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
}
