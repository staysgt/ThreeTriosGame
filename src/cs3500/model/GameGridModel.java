package cs3500.model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Grid Model for a ThreeTrios game.
 * @param <C> card type.
 */
public class GameGridModel<C extends Card> implements GameGrid<C> {
  /**
   * INVARIANTS:
   * - r is not null.
   * This is ensured in the constructor, preserved by the methods (it is final, so this is
   * unapplicable), and it is instantaneous.
   * - blueHand/redHand is not null:
   * this is ensured in the constructor, preserved by the methods, and instantaneous.
   */
  private Cell[][] grid;
  private List<C> blueHand = new ArrayList<>();
  private List<C> redHand = new ArrayList<>();
  private boolean gameStarted = false;
  private final Random r;

  /**
   * Constructor for a model.GameGridModel.
   */
  public GameGridModel() {
    this.r = new Random();
  }

  /**
   * Constructor for a GameGridModel that takes in a random variable for controlled shuffling.
   */
  public GameGridModel(Random r) {
    this.r = r;
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
    if (handIdx < 0 || handIdx > getHand(getTurn()).size()) {
      throw new IllegalArgumentException("Not a hand index");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }
    if (grid[row][col].getCellState() == CellState.HOLE) {
      throw new IllegalArgumentException("Card cannot be played in a hole");
    }

    // sets the card at the desired position
    grid[row][col].setCard(getHand(getTurn()).get(handIdx), getTurn());
    // performs the battle stage
    battle(col, row, true, true, true, true);
    getHand(getTurn()).remove(handIdx);
  }

  // Performs the battle stage.
  private void battle(int rowIdx, int colIdx, boolean battleN, boolean battleE, boolean battleS,
                      boolean battleW) {

    // battles to the north & sets ownership + adds to map that tracks if its been visited
    if (battleN && rowIdx + 1 < grid.length && colIdx < grid[0].length
            && grid[rowIdx + 1][colIdx].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx + 1][colIdx].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx + 1][colIdx].getOwner() != getTurn()
              && currCard.getNorth().getValue() > adjCard.getSouth().getValue()) {
        grid[rowIdx + 1][colIdx].setOwner(getTurn());
        battle(rowIdx + 1, colIdx, true, true, false, true);
      }
    }

    //battles card to the south & sets ownership
    if (battleS && rowIdx - 1 >= 0 && colIdx < grid[0].length &&
            grid[rowIdx - 1][colIdx].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx - 1][colIdx].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx - 1][colIdx].getOwner() != getTurn()
              && currCard.getSouth().getValue() > adjCard.getNorth().getValue()) {
        grid[rowIdx - 1][colIdx].setOwner(getTurn());
        battle(rowIdx - 1, colIdx, false, true, true, true);
      }
    }

    // battles card to the east & sets ownership
    if (battleE && colIdx + 1 < grid[0].length && grid[rowIdx][colIdx + 1].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx][colIdx + 1].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx][colIdx + 1].getOwner() != getTurn()
              && currCard.getEast().getValue() > adjCard.getWest().getValue()) {
        grid[rowIdx][colIdx + 1].setOwner(getTurn());
        battle(rowIdx, colIdx + 1, true, true, true, false);
      }
    }

    // battles card to the west & sets ownership
    if (battleW && colIdx - 1 >= 0 && colIdx < grid[0].length
            && grid[rowIdx][colIdx - 1].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx][colIdx - 1].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx][colIdx - 1].getOwner() != getTurn()
              && currCard.getWest().getValue() > adjCard.getEast().getValue()) {
        grid[rowIdx][colIdx - 1].setOwner(getTurn());
        battle(rowIdx, colIdx - 1, true, false, true, true);
      }
    }
  }

  private void checkGameStarted() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started.");
    }
  }

  @Override
  public void startGame(List<C> cards, int cols, int rows, List<String> rowConf) {
    if (gameStarted) {
      throw new IllegalStateException("Game has already been started.");
    }
    if (cards == null || rowConf == null) {
      throw new IllegalArgumentException("Given arguments cannot be null");
    }
    if (cards.contains(null)) {
      throw new IllegalArgumentException("Given cards cannot contain null values.");
    }
    if (cols <= 0 || rows <= 0) {
      throw new IllegalArgumentException("Number of columns and rows must be greater than 0");
    }
    int emptySpaces = countEmptySpaces(rowConf);
    if ((emptySpaces + 1) > cards.size()) {
      throw new IllegalArgumentException("List of cards is too short.");
    }
    // checks that the deck has no duplicate cards in it
    checkDuplicateCardNames(cards);
    // instantiates the grid
    this.grid = new Cell[cols][rows];
    // assigns the cells that are supposed to be holes vs card spaces
    rowConfigToGrid(cols, rows, rowConf);

    // shuffles list, stores in new variable to ensure mutability.
    List<C> shuffledList = new ArrayList<>(cards);
    Collections.shuffle(shuffledList, r);
    // gives the first half of the deck to the red player's hand
    for (int handIdx = 0; handIdx < (emptySpaces + 1) / 2; handIdx++) {
      this.redHand.add(handIdx, shuffledList.get(handIdx));
    }
    // gives the second half of the deck to the blue player's hand
    for (int handIdx = (emptySpaces + 1) / 2; handIdx < emptySpaces + 1; handIdx++) {
      this.blueHand.add(handIdx - (emptySpaces + 1) / 2, shuffledList.get(handIdx));
    }

    gameStarted = true;
  }

  private void checkDuplicateCardNames(List<? extends Card> cards) {
    ArrayList<String> cardNamesDuplicate = new ArrayList<>();
    for (Card card : cards) {
      if (cardNamesDuplicate.contains(card.getName())) {
        throw new IllegalArgumentException("Given deck cannot contain duplicate card names");
      }
      cardNamesDuplicate.add(card.getName());
    }
  }

  private void rowConfigToGrid(int cols, int rows, List<String> rowConf) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (rowConf.get(row).charAt(col) == 'X') {
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
        if (configuration.get(row).charAt(charInString) == 'C') {
          emptySpaces++;
        }
      }

    }
    return emptySpaces;
  }

  @Override
  public List<C> getHand(Player player) {
    checkGameStarted();
    if (player == Player.RED) {
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

    return grid[x][y].getCellState().equals(CellState.EMPTY);
  }

  @Override
  public boolean isCellHole(int x, int y) {
    checkGameStarted();
    if (x < 0 || y < 0 || x > grid.length || y > grid[0].length) {
      throw new IllegalArgumentException("Invalid x or y.");
    }

    return grid[x][y].getCellState() == (CellState.HOLE);
  }

  @Override
  public Player getTurn() {
    checkGameStarted();
    if (getHand(Player.RED).size() == getHand(Player.BLUE).size()) {
      return Player.RED;
    } else {
      return Player.BLUE;
    }
  }


  @Override
  public Player getWinner() {
    checkGameStarted();
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not yet over");
    }
    int blueCount = 0;
    int redCount = 0;
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col].getOwner() == Player.BLUE) {
          blueCount++;
        } else if (grid[row][col].getOwner() == Player.RED) {
          redCount++;
        }
      }
    }
    if (redCount > blueCount) {
      return Player.RED;
    } else {
      return Player.BLUE;
    }
  }

  @Override
  public Cell[][] getBoard() {
    checkGameStarted();
    Cell[][] copy = new Cell[grid.length][grid[0].length];
    for (int row = 0; row < grid.length; row++) {
      copy[row] = grid[row];
    }
    return copy;
  }

  @Override
  public int getScore(Player player) {
    checkGameStarted();
    int count = 0;
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col].getOwner() == player) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * This gets how many cards a player can flip.
   * @param x coordinate of grid
   * @param y coordinate of grid
   * @return the amount of cards a player can flip.
   * @throws IllegalStateException if game has not started or game is over
   */
  @Override
  public int cardsFlipped(int x, int y, int handIdx) {
    // should supply battle no mutation with a grid from getCopy
    Cell[][] copy = getBoard();

    return 0;
  }

  /**
   * Counts the number of cards flipped within a battle.
   *
   * @return
   */
  private int battleNoMutation(Cell[][] grid, int rowIdx, int colIdx, boolean battleN, boolean battleE, boolean battleS,
                               boolean battleW, int count) {


    // battles to the north & sets ownership + adds to map that tracks if its been visited
    if (battleN && rowIdx + 1 < grid.length && colIdx < grid[0].length
            && grid[rowIdx + 1][colIdx].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx + 1][colIdx].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx + 1][colIdx].getOwner() != getTurn()
              && currCard.getNorth().getValue() > adjCard.getSouth().getValue()) {
        grid[rowIdx + 1][colIdx].setOwner(getTurn());
        battleNoMutation(grid, rowIdx + 1, colIdx, true, true, false, true, count);
      }
    }

    //battles card to the south & sets ownership
    if (battleS && rowIdx - 1 >= 0 && colIdx < grid[0].length &&
            grid[rowIdx - 1][colIdx].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx - 1][colIdx].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx - 1][colIdx].getOwner() != getTurn()
              && currCard.getSouth().getValue() > adjCard.getNorth().getValue()) {
        grid[rowIdx - 1][colIdx].setOwner(getTurn());
        return battleNoMutation(grid, rowIdx - 1, colIdx, false, true, true, true, count);
      }
    }

    // battles card to the east & sets ownership
    if (battleE && colIdx + 1 < grid[0].length && grid[rowIdx][colIdx + 1].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx][colIdx + 1].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx][colIdx + 1].getOwner() != getTurn()
              && currCard.getEast().getValue() > adjCard.getWest().getValue()) {
        grid[rowIdx][colIdx + 1].setOwner(getTurn());
        return battleNoMutation(grid, rowIdx, colIdx + 1, true, true, true, false, count);
      }
    }

    // battles card to the west & sets ownership
    if (battleW && colIdx - 1 >= 0 && colIdx < grid[0].length
            && grid[rowIdx][colIdx - 1].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx][colIdx - 1].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx][colIdx - 1].getOwner() != getTurn()
              && currCard.getWest().getValue() > adjCard.getEast().getValue()) {
        grid[rowIdx][colIdx - 1].setOwner(getTurn());
        return battleNoMutation(grid, rowIdx, colIdx - 1, true, false, true, true, count);
      }
    }
    return count;
  }


  /**
   * This tells us if the play is legal or not.
   * @param player a specific player
   * @param x coordinate of grid
   * @param y coordinate of grid
   * @return true or false.
   * @throws IllegalStateException if game has not started or game is over
   */
  @Override
  public boolean legalCard(Player player, int x, int y) {
    checkGameStarted();
    if (grid[x][y].getCellState() == CellState.HOLE) {
      return false;
    } else {
      // check to see if name is in space or not

    }
    return true;
  }

}





