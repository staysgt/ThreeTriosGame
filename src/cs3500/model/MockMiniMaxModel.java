package cs3500.model;

/**
 * Mock model for test minimax strategy.
 */
public class MockMiniMaxModel<C extends Card> extends GameGridModel<C> {
  StringBuilder transcript = new StringBuilder();

  boolean forStrategy = false;

  /**
   * Sets whether or not it is checking for a strategy.
   *
   * @param isForStrategy if it is for a strategy.
   */
  public void isForStrategy(boolean isForStrategy) {
    forStrategy = isForStrategy;
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    if (forStrategy) {
      transcript.append("Checking " + row + " , " + col + "\n");
    }

    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }
    if (row < 0 || row >= this.getBoard().length) {
      throw new IllegalArgumentException("Row index out of bounds");
    }
    if (col < 0 || col >= this.getBoard()[0].length) {
      throw new IllegalArgumentException("Column index out of bounds");
    }
    if (handIdx > getHand(getTurn()).size() || handIdx < 0) {
      throw new IllegalArgumentException("Hand idx out of bounds");
    }
    if (this.getBoard()[row][col].getCellState() == CellState.HOLE) {
      throw new IllegalArgumentException("Card cannot be played in a hole");
    }
    if (this.getBoard()[row][col].getCard() != null) {
      throw new IllegalArgumentException("Location is already taken");
    }

    // sets the card at the desired position
    this.getBoard()[row][col].setCard(getHand(getTurn()).get(handIdx), getTurn());
    // performs the battle stage
    battle(this.getBoard(), row, col, true, true, true, true, getTurn());
    getHand(getTurn()).remove(handIdx);
  }

  // Performs the battle stage.
  private void battle(Cell[][] grid, int rowIdx, int colIdx, boolean battleN, boolean battleE,
                      boolean battleS,
                      boolean battleW, Player player) {

    // battles to the north & sets ownership + adds to map that tracks if its been visited
    battleNorth(grid, rowIdx, colIdx, battleN, player);
    //battles card to the south & sets ownership
    battleSouth(grid, rowIdx, colIdx, battleS, player);
    // battles card to the east & sets ownership
    battleEast(grid, rowIdx, colIdx, battleE, player);
    // battles card to the west & sets ownership
    battleWest(grid, rowIdx, colIdx, battleW, player);
  }

  private void battleWest(Cell[][] grid, int rowIdx, int colIdx, boolean battleW, Player player) {
    if (battleW && colIdx - 1 >= 0 && colIdx < grid[0].length
            && grid[rowIdx][colIdx - 1].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx][colIdx - 1].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx][colIdx - 1].getOwner() != player
              && currCard.getWest().getValue() > adjCard.getEast().getValue()) {
        grid[rowIdx][colIdx - 1].setOwner(player);
        battle(grid, rowIdx, colIdx - 1, true, false, true,
                true, player);
      }
    }
  }

  private void battleEast(Cell[][] grid, int rowIdx, int colIdx, boolean battleE, Player player) {
    if (battleE && colIdx + 1 < grid[0].length && grid[rowIdx][colIdx + 1].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx][colIdx + 1].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx][colIdx + 1].getOwner() != player
              && currCard.getEast().getValue() > adjCard.getWest().getValue()) {
        grid[rowIdx][colIdx + 1].setOwner(player);
        battle(grid, rowIdx, colIdx + 1, true, true, true,
                false, player);
      }
    }
  }

  private void battleSouth(Cell[][] grid, int rowIdx, int colIdx, boolean battleS, Player player) {
    if (battleS && rowIdx - 1 >= 0 && colIdx < grid[0].length
            && grid[rowIdx - 1][colIdx].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx - 1][colIdx].getCard();
      if (adjCard != null && currCard != null
              && grid[rowIdx - 1][colIdx].getOwner() != player
              && currCard.getSouth().getValue() > adjCard.getNorth().getValue()) {
        grid[rowIdx - 1][colIdx].setOwner(player);
        battle(grid, rowIdx - 1, colIdx, false, true, true,
                true, player);
      }
    }
  }

  private void battleNorth(Cell[][] grid, int rowIdx, int colIdx, boolean battleN, Player player) {
    if (battleN && rowIdx + 1 < grid.length && colIdx < grid[0].length
            && grid[rowIdx + 1][colIdx].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx + 1][colIdx].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx + 1][colIdx].getOwner() != player
              && currCard.getNorth().getValue() > adjCard.getSouth().getValue()) {
        grid[rowIdx + 1][colIdx].setOwner(player);
        battle(grid, rowIdx + 1, colIdx, true, true, false,
                true, player);
      }
    }
  }

  /**
   * Gets the transcript of the moves checked.
   *
   * @return the spaces checked.
   */
  public String getTranscript() {
    return transcript.toString();
  }


}
