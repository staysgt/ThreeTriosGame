package cs3500.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Grid Model for a ThreeTrios game.
 * @param <C> card type.
 */
public class GameGridModel<C extends Card> implements GameGrid<C> {

  private List<Card> cardsLeft;
  private Card[][] cards;
  private Cell[][] grid;
  private List<C> blueHand = new ArrayList<>();
  private List<C> redHand = new ArrayList<>();
  private boolean gameStarted = false;

  /**
   * Constructor for a model.GameGridModel.
   */
  public GameGridModel () {
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    int gridLink = grid.length;
    if (row < 0 || row > grid.length) {
      throw new IllegalArgumentException("Too little or too many cards.");
    }
    if (col < 0 || col > grid[row].length) {
      throw new IllegalArgumentException("Too little or too many cards.");
    }
    checkGameStarted();
    if (handIdx < 0 || handIdx > getCurrPlayersHand().size()) {
      throw new IllegalArgumentException("Not a hand index");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }
    if(grid[row][col].getCellstate() == CellState.HOLE) {
      throw new IllegalArgumentException("Card cannot be played in a hole");
    }

    // sets the card at the desired position
    grid[row][col].setCard(getCurrPlayersHand().get(handIdx));
    // performs the battle stage
    battle(col,row,true,true,true,true);
    this.getCurrPlayersHand().remove(handIdx);

  }

  // Performs the battle stage.
  private void battle(int col, int row, boolean battleN, boolean battleE, boolean battleS, boolean battleW) {
    // battles to the north & sets ownership
    if(battleN && col + 1 < grid[0].length && grid[col + 1][row].getCard() != null) {
      NESWCard currCard = (NESWCard)grid[col][row].getCard();
      NESWCard adjCard = (NESWCard)grid[col + 1][row].getCard();
      if(currCard.getNorth().getValue() > adjCard.getSouth().getValue()) {
        grid[col+1][row].setOwner(getTurn());
        battle(col + 1, row, true,true,false,true);
      }
    }

    //battles card to the south & sets ownership
    if(battleS && col - 1 >= 0 && grid[col - 1][row].getCard() != null) {
      NESWCard currCard = (NESWCard)grid[col][row].getCard();
      NESWCard adjCard = (NESWCard)grid[col - 1][row].getCard();
      if(currCard.getSouth().getValue() > adjCard.getNorth().getValue()) {
        grid[col-1][row].setOwner(getTurn());
        battle(col - 1, row, false,true,true,true);
      }
    }

    // battles card to the east & sets ownership
    if(battleE && row + 1 < grid.length && grid[col][row + 1].getCard() != null) {
      NESWCard currCard = (NESWCard)grid[col][row].getCard();
      NESWCard adjCard = (NESWCard)grid[col][row + 1].getCard();
      if(currCard.getEast().getValue() > adjCard.getWest().getValue()) {
        grid[col][row+1].setOwner(getTurn());
        battle(col, row +1, true,true,true,false);
      }
    }

    // battles card to the west & sets ownership
    if(battleW && row - 1 >= 0 && grid[col][row - 1].getCard() != null) {
      NESWCard currCard = (NESWCard)grid[col][row].getCard();
      NESWCard adjCard = (NESWCard)grid[col][row - 1].getCard();
      if(currCard.getWest().getValue() > adjCard.getEast().getValue()) {
        grid[col][row-1].setOwner(getTurn());
        battle(col, row -1, true,false,true,true);
      }
    }
  }

  private void checkGameStarted() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started.");
    }
  }

  private List<? extends Card> getCurrPlayersHand() {
    if(this.getTurn() == Player.RED) {
      return redHand;
    } else {
      return blueHand;
    }

  }

  @Override
  public void startGame(List<C> cards, int cols, int rows, List<String> rowConf) {
    if(gameStarted) {
      throw new IllegalStateException("Game has already been started.");
    }
    if(cards == null || rowConf == null) {
      throw new IllegalArgumentException("Given arguments cannot be null");
    }
    if(cards.contains(null)) {
      throw new IllegalArgumentException("Given cards cannot contain null values.");
    }
    if(cols <= 0 || rows <= 0) {
      throw new IllegalArgumentException("Number of columns and rows must be greater than 0");
    }
    int emptySpaces = countEmptySpaces(rowConf);
    if((emptySpaces + 1) > cards.size()) {
      throw new IllegalArgumentException("List of cards is too short.");
    }
    // checks that the deck has no duplicate cards in it
    checkDuplicateCardNames(cards);
    // instantiates the grid
    this.grid = new Cell[cols][rows];
    // assigns the cells that are supposed to be holes vs card spaces
    rowConfigToGrid(cols, rows, rowConf);

    // gives the first half of the deck to the red player's hand
    for (int handIdx = 0; handIdx < (emptySpaces + 1)/2; handIdx++) {
      this.redHand.add(handIdx, cards.get(handIdx));
    }
    // gives the second half of the deck to the blue player's hand
    for (int handIdx = (emptySpaces + 1)/2; handIdx < emptySpaces + 1; handIdx++) {
      this.blueHand.add(handIdx - (emptySpaces + 1)/2, cards.get(handIdx));
    }

    gameStarted = true;
  }

  private void checkDuplicateCardNames(List<? extends Card> cards) {
    ArrayList<String> cardNamesDuplicate = new ArrayList<>();
    for(Card card: cards) {
      if(cardNamesDuplicate.contains(card.getName())) {
        throw new IllegalArgumentException("Given deck cannot contain duplicate card names");
      }
      cardNamesDuplicate.add(card.getName());
    }
  }

  private void rowConfigToGrid(int cols, int rows, List<String> rowConf) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if(rowConf.get(row).charAt(col) == 'X') {
          grid[col][row] = new Cell(CellState.HOLE);
        } else {
          grid[col][row] = new Cell(CellState.CARD_SPACE);
        }
      }
    }
  }

  private int countEmptySpaces(List<String> configuration) {
    int emptySpaces = 0;
    for (int row = 0; row < configuration.size(); row++) {
      for (int charInString = 0; charInString < configuration.get(row).length(); charInString++) {
        if(configuration.get(row).charAt(charInString) == 'C') {
          emptySpaces++;
        }
      }
      
    }
    return emptySpaces;
  }

  @Override
  public List<C> getHand(Player player) {
    checkGameStarted();
    if(player == Player.RED) {
      return this.redHand;
    } else {
      return this.blueHand;
    }
  }

  @Override
  public boolean isGameOver() {
    checkGameStarted();
    return redHand.isEmpty();
  }

  @Override
  public boolean isCellPlayable(int x, int y) {
    checkGameStarted();
    if (x < 0 || y < 0 || x > grid.length || y > grid[0].length) {
      throw new IllegalArgumentException("Invalid x or y.");
    }

    return grid[x][y].getCellstate().equals(CellState.EMPTY);
  }

  @Override
  public boolean isCellHole(int x, int y) {
    checkGameStarted();
    if (x < 0 || y < 0 || x > grid.length || y > grid[0].length) {
      throw new IllegalArgumentException("Invalid x or y.");
    }

    if (grid[x][y].getCellstate() == (CellState.HOLE)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Player getTurn() {
    checkGameStarted();
    if (getHand(Player.RED) == getHand(Player.BLUE)) {
      return Player.RED;
    } else {
      return Player.BLUE;
    }
  }


  @Override
  public Player getWinner() {
    checkGameStarted();
    int blueCount = 0;
    int redCount = 0;
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if(grid[row][col].getOwner() == Player.BLUE) {
          blueCount++;
        } else if (grid[row][col].getOwner() == Player.RED) {
          redCount++;
        }
      }
    }
    if(redCount > blueCount) {
      return Player.RED;
    } else {
      return Player.BLUE;
    }
  }

  @Override
  public Cell[][] getBoard() {
    checkGameStarted();
    return grid;
  }


}


