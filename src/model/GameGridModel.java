package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Grid Model for a ThreeTrios game.
 * @param <C> card type.
 */
public class GameGridModel<C extends Card> implements GameGrid<C> {
  private Cell[][] grid;
  private List<C> blueHand;
  private List<C> redHand;
  private boolean gameStarted = false;


  /**
   * Constructor for a model.GameGridModel.
   */
  public GameGridModel () {
  }

  @Override
  public void playToGrid(int x, int y, int handIdx) {
    int gridLink = grid.length;
    if (x < 0 || x > grid.length) {
      throw new IllegalArgumentException("Too little or too many cards.");
    }
    if (y < 0 || y > grid[x].length) {
      throw new IllegalArgumentException("Too little or too many cards.");
    }
    checkGameStarted();
    if (handIdx < 0 || handIdx > getCurrPlayersHand().size()) {
      throw new IllegalArgumentException("Not a hand index");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Game is over.");
    }


    grid[x][y].setCard(getCurrPlayersHand().get(handIdx));
    this.getCurrPlayersHand().remove(handIdx);

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
  public void startGame(List<C> cards, int cols, int rows, String[] rowConf, int handSize) {
    if(cards == null || rowConf == null) {
      throw new IllegalArgumentException("Given arguments cannot be null");
    }
    if(gameStarted) {
      throw new IllegalStateException("Game has already been started.");
    }
    if(cards.contains(null)) {
      throw new IllegalArgumentException("Given cards cannot contain null values.");
    }
    if(cols <= 0 || rows <= 0) {
      throw new IllegalArgumentException("Number of columns and rows must be greater than 0");
    }
    int emptySpaces = countEmptySpaces(rowConf);
    if(Math.ceil(emptySpaces + handSize*2) > cards.size()) {
      throw new IllegalArgumentException("List of cards is too short.");
    }
    // checks that the deck has no duplicate cards in it
    checkDuplicateCardNames(cards);
    // instantiates the grid
    this.grid = new Cell[cols][rows];
    // assigns the cells that are supposed to be holes vs card spaces
    rowConfigToGrid(cols, rows, rowConf);
    // converts given list of cards into a mutable list, just in case an immutable list was given
    ArrayList<C> mutableCards = new ArrayList<>(cards);
    // gives the first half of the deck to the red player's hand
    for (int handIdx = 0; handIdx < (emptySpaces + 1)/2; handIdx++) {
      this.redHand.set(handIdx, mutableCards.get(handIdx));
      mutableCards.remove(handIdx);
    }
    // gives the second half of the deck to the blue player's hand
    for (int handIdx = (emptySpaces + 1)/2; handIdx < emptySpaces + 1; handIdx++) {
      this.blueHand.set(handIdx, mutableCards.get(handIdx));
      mutableCards.remove(handIdx);
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

  private void rowConfigToGrid(int cols, int rows, String[] rowConf) {
    for (int col = 0; col < cols; col++) {
      for (int row = 0; row < rows; row++) {
        if(rowConf[col].charAt(row) == 'X') {
          grid[col][row] = new Cell(CellState.HOLE);
        } else {
          grid[col][row] = new Cell(CellState.CARD_SPACE);
        }
      }
    }
  }

  private int countEmptySpaces(String[] configuration) {
    int emptySpaces = 0;
    for (int row = 0; row < configuration.length; row++) {
      for (int charInString = 0; charInString < configuration[row].length(); charInString++) {
        if(configuration[row].charAt(charInString) == 'c') {
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
      return redHand;
    } else {
      return blueHand;
    }
  }

  @Override
  public boolean isGameOver() {
    return this.redHand.isEmpty() || this.blueHand.isEmpty();
  }


  @Override
  public boolean isCellPlayable(int x, int y) {
    if (grid[x][y].getCellstate() == CellState.CARD_SPACE && grid[x][y].getOwner() == null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean isCellHole(int x, int y) {
    return grid[x][y].getCellstate() == CellState.HOLE;
  }

  @Override
  public C getCellCard(int x, int y) {
    return (C) grid[x][y].getCard();
  }

  @Override
  public Player getTurn() {
    // fiona
    return null;
  }

  private Player redPlayer = Player.RED;
  private Player bluePlayer = Player.BLUE;


  private Player getCurrPlayer(Player player) {
    if(this.getTurn() == Player.RED) {
      return redPlayer;
    } else {
      return bluePlayer;
    }

  }

  /**
   * Return the winner of the game, or {@code null} if there is no winner. If the game is not
   * over, returns {@code null}.
   *
   * @return the winner, or null if there is no winner
   * @throws IllegalStateException if game has not started
   */
  @Override
  public Player getWinner() {
    checkGameStarted();
    while (isGameOver() == true) {
      if (getCurrPlayer(redPlayer).compareTo(getCurrPlayer(bluePlayer)) > 0) {
        return redPlayer;
      } else if (getCurrPlayer(bluePlayer).compareTo(getCurrPlayer(redPlayer)) > 0) {
        return bluePlayer;
      } else if (redPlayer == bluePlayer) {
        return null;
      }
    }
    return null;
  }

  @Override
  public Cell[][] getBoard() {
    // Fiona needs to fix
    checkGameStarted();

    return grid;

  }


}
