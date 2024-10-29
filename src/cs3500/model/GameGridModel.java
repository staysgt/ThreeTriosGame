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
  private List<C> blueHand;
  private List<C> redHand;
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
    if((emptySpaces + 1) > cards.size()) {
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

  private void rowConfigToGrid(int cols, int rows, List<String> rowConf) {
    for (int col = 0; col < cols; col++) {
      for (int row = 0; row < rows; row++) {
        if(rowConf.get(col).charAt(row) == 'X') {
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
        if(configuration.get(row).charAt(charInString) == 'c') {
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


  private int cardsInGame() {
    return cardsLeft.size();
  }


  @Override
  public boolean isGameOver() {
    checkGameStarted();

    if (blueHand.equals(CellState.EMPTY)) {
      return false;
    } else if (redHand.equals(CellState.EMPTY)){
      return false;
    } else {
      return true;
    }
  }

  @Override
  public boolean isCellPlayable(int x, int y) {
    checkGameStarted();
    if (x < 0 || y < 0 || x < grid.length || y < grid[0].length) {
      throw new IllegalArgumentException("Invalid x or y.");
    }

    if (grid[x][y].equals(CellState.EMPTY)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean isCellHole(int x, int y) {
    checkGameStarted();
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid x or y.");
    }

    if (grid[x][y].equals(CellState.HOLE)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public C getCellCard(int x, int y) {
    return null;
  }

  private Player redPlayer = Player.RED;
  private Player bluePlayer = Player.BLUE;

  private Player getCurrPlayersCard(Player player) {
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
    checkGameStarted();
    return grid;

  }


}


