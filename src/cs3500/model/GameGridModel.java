package cs3500.model;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.NESWCardFileReader;


/**
 * Grid Model for a ThreeTrios game.
 * All coordinates are 0-based.
 *
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
  private Cell<C>[][] grid;
  private List<C> blueHand = new ArrayList<>();
  private List<C> redHand = new ArrayList<>();
  protected boolean gameStarted = false;
  private final Random r;

  private final List<GameGridModel<C>> statuses = new ArrayList<>();

  private final HashMap<Integer, HashMap<Cell<C>[][], List<C>[]>> gameStatuses = new HashMap<>();
  private int numPlays = 0;

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

  /**
   * Constructor used for starting a game mid-game.
   * For purposes of strategies.
   *
   * @param grid     the gameGrid.
   * @param redHand  red players hand.
   * @param blueHand blue players hand.
   */
  public GameGridModel(Cell<C>[][] grid, List<C> redHand, List<C> blueHand) {
    this.r = new Random();
    this.grid = grid;
    gameStarted = true;
    this.redHand = redHand;
    this.blueHand = blueHand;
  }


  /**
   * Constructor that starts the game.
   *
   * @param configFilePath configuration file.
   * @param cardFilePath   card file to start game.
   */
  public GameGridModel(String configFilePath, String cardFilePath, Random r)
          throws FileNotFoundException {
    this.r = r;
    gameStarted = true;
    ConfigurationFileReader configFile = new ConfigurationFileReader(configFilePath);
    NESWCardFileReader cardFile = new NESWCardFileReader(cardFilePath);
    startGame(cardFile.getCards(), configFile.getCols(), configFile.getRows(),
            configFile.getRowConfig());
  }


  @Override
  public void playToGrid(int row, int col, int handIdx) {
    checkGameStarted();
    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }
    if (row < 0 || row >= grid.length) {
      throw new IllegalArgumentException("Row index out of bounds");
    }
    if (col < 0 || col >= grid[0].length) {
      throw new IllegalArgumentException("Column index out of bounds");
    }
    if (handIdx > getHand(getTurn()).size() || handIdx < 0) {
      throw new IllegalArgumentException("Hand idx out of bounds");
    }
    if (grid[row][col].getCellState() == CellState.HOLE) {
      throw new IllegalArgumentException("Card cannot be played in a hole");
    }
    if (grid[row][col].getCard() != null) {
      throw new IllegalArgumentException("Location is already taken");
    }

    // sets the card at the desired position
    grid[row][col].setCard(getHand(getTurn()).get(handIdx), getTurn());
    // performs the battle stage
    battle(grid, row, col, true, true, true, true, getTurn());
    getHand(getTurn()).remove(handIdx);
    updateStatuses();
  }

  // Performs the battle stage.
  private void battle(Cell<C>[][] grid, int rowIdx, int colIdx, boolean battleN, boolean battleE,
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

  private void battleWest(Cell<C>[][] grid, int rowIdx, int colIdx, boolean battleW, Player player) {
    if (battleW && colIdx - 1 >= 0 && colIdx < grid[0].length
            && grid[rowIdx][colIdx - 1].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx][colIdx - 1].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx][colIdx - 1].getOwner() != player
              && currCard.getWestOurs().getValue() > adjCard.getEastOurs().getValue()) {
        grid[rowIdx][colIdx - 1].setOwner(player);
        battle(grid, rowIdx, colIdx - 1, true, false, true,
                true, player);
      }
    }
  }

  private void battleEast(Cell<C>[][] grid, int rowIdx, int colIdx, boolean battleE, Player player) {
    if (battleE && colIdx + 1 < grid[0].length && grid[rowIdx][colIdx + 1].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx][colIdx + 1].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx][colIdx + 1].getOwner() != player
              && currCard.getEastOurs().getValue() > adjCard.getWestOurs().getValue()) {
        grid[rowIdx][colIdx + 1].setOwner(player);
        battle(grid, rowIdx, colIdx + 1, true, true, true,
                false, player);
      }
    }
  }

  private void battleSouth(Cell<C>[][] grid, int rowIdx, int colIdx, boolean battleS, Player player) {
    if (battleS && rowIdx - 1 >= 0 && colIdx < grid[0].length
            && grid[rowIdx - 1][colIdx].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx - 1][colIdx].getCard();
      if (adjCard != null && currCard != null
              && grid[rowIdx - 1][colIdx].getOwner() != player
              && currCard.getSouthOurs().getValue() > adjCard.getNorthOurs().getValue()) {
        grid[rowIdx - 1][colIdx].setOwner(player);
        battle(grid, rowIdx - 1, colIdx, false, true, true,
                true, player);
      }
    }
  }

  private void battleNorth(Cell<C>[][] grid, int rowIdx, int colIdx, boolean battleN, Player player) {
    if (battleN && rowIdx + 1 < grid.length && colIdx < grid[0].length
            && grid[rowIdx + 1][colIdx].getCard() != null) {
      NESWCard currCard = (NESWCard) grid[rowIdx][colIdx].getCard();
      NESWCard adjCard = (NESWCard) grid[rowIdx + 1][colIdx].getCard();
      if (adjCard != null && currCard != null && grid[rowIdx + 1][colIdx].getOwner() != player
              && currCard.getNorthOurs().getValue() > adjCard.getSouthOurs().getValue()) {
        grid[rowIdx + 1][colIdx].setOwner(player);
        battle(grid, rowIdx + 1, colIdx, true, true, false,
                true, player);
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
    this.grid = new Cell[rows][cols];
    // assigns the cells that are supposed to be holes vs card spaces
    rowConfigToGrid(rows, cols, rowConf);

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
    updateStatuses();
  }

  private void updateStatuses() {
    statuses.add(new GameGridModel<>(this.getBoard(), this.getHand(Player.RED),
            this.getHand(Player.BLUE)));
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

  private void rowConfigToGrid(int rows, int cols, List<String> rowConf) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (rowConf.get(row).charAt(col) == 'X') {
          grid[row][col] = new Cell(CellState.HOLE);
        } else {
          grid[row][col] = new Cell(CellState.CARD_SPACE);
        }
      }
    }
  }

  private int countEmptySpaces(List<String> configuration) {
    int emptySpaces = 0;
    for (String s : configuration) {
      for (int charInString = 0; charInString < s.length(); charInString++) {
        if (s.charAt(charInString) == 'C') {
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
  public boolean isCellHole(int row, int col) {
    checkGameStarted();
    if (row < 0 || col < 0 || row > grid.length || col > grid[0].length) {
      throw new IllegalArgumentException("Invalid row or y.");
    }
    return grid[row][col].getCellState() == CellState.HOLE;
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
      for (int col = 0; col < grid[row].length; col++) {
        copy[row][col] = new Cell<Card>(grid[row][col].getCellState(), grid[row][col].getCard(),
                grid[row][col].getOwner());
      }
    }
    return copy;
  }

  @Override
  public int getScore(Player player) {
    checkGameStarted();
    int count = getCount(grid, player);
    return count;
  }

  private int getCount(Cell<C>[][] grid, Player player) {
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
   *
   * @param row coordinate of grid
   * @param col coordinate of grid
   * @return the amount of cards a player can flip.
   * @throws IllegalStateException if game has not started or game is over
   */
  @Override
  public int cardsFlipped(int row, int col, int handIdx, Player player) {
    // should supply battle no mutation with a grid from getCopy
    if (grid[row][col].getCard() != null) {
      throw new IllegalArgumentException("Spot is already taken up");
    }
    Cell<C>[][] copy = getBoard();
    copy[row][col].setCard(getHand(player).get(handIdx), player);
    int before = getCount(copy, player);

    battle(copy, row, col, true, true, true, true, player);
    int after = getCount(copy, player);
    return after - before;
  }

  @Override
  public boolean legalPlay(int row, int col) {
    checkGameStarted();
    Cell<C> currCell = grid[row][col];
    return currCell.getCellState() == CellState.CARD_SPACE && currCell.getCard() == null;
  }


  @Override
  public List<GameGridModel<C>> getGameStatuses() {
    checkGameStarted();
    return statuses;
  }

  @Override
  public void selectCell(int row, int col) {
    checkGameStarted();
    Cell selectedCell = grid[row][col];

  }


}





