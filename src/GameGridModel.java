import com.sun.source.tree.NewArrayTree;

import java.util.List;

import static java.util.Arrays.copyOf;

public class GameGridModel<C extends Card> implements GameGrid {



  // all of these are instantiated within the startGame method

  // we chose to use

  private Cell[][] grid;
  private List<Card> blueHand;
  private List<Card> redHand;
  private boolean gameStarted = false;

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

  private List<Card> getCurrPlayersHand() {
    if(this.getTurn() == Player.RED) {
      return redHand;
    } else {
      return blueHand;
    }

  }

  @Override
  public void startGame(List cards, int cols, int rows, String[] rowConf, int handSize, boolean shuf) {
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
    if(Math.ceil(countEmptySpaces(rowConf) + handSize*2) > cards.size()) {
      throw new IllegalArgumentException("List of cards is too short.");
    }


    grid = new Cell[cols][rows];


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
  public List getHand(Player player) {
    checkGameStarted();
    if(player == Player.RED) {
      return redHand;
    } else {
      return blueHand;
    }
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean isGameWon() {
    return true;
  }

  @Override
  public boolean isCellEmpty(int x, int y) {
    // fiona
    return false;
  }

  @Override
  public boolean isCellHole(int x, int y) {
    // fiona
    return false;
  }

  @Override
  public Card getCellValue(int x, int y) {
    // fiona
    return null;
  }

  @Override
  public Player getTurn() {
    // fiona
    return null;
  }

  @Override
  public Player getWinner() {
    // Fiona

    return null;
  }

  @Override
  public Cell[][] getBoard() {
    // Fiona needs to fix
    checkGameStarted();

    return grid;

  }


}
