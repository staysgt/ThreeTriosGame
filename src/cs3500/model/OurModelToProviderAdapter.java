package cs3500.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.provider.model.CellType;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.model.ThreeTriosModel;
import cs3500.threetrios.provider.view.ModelFeatures;

public class OurModelToProviderAdapter<C extends Card> extends GameGridModel<C> implements ThreeTriosModel<C> {
  @Override
  public void playToCell(int row, int col, int cardInHandIdx) {
    playToGrid(row, col, cardInHandIdx);
  }

  // same as our start game method...
  @Override
  public void startGame(List<C> deck, int width, int height, List<List<CellType>> cellTypes) {
    List<String> cellTypeToString = new ArrayList<>();
    for (int row = 0; row < cellTypes.size(); row++) {
      StringBuilder sb = new StringBuilder();
      for (int col = 0; col < cellTypes.size(); col++) {
        if (cellTypes.get(row).get(col) == CellType.HOLE) {
          sb.append('X');
        } else {
          sb.append('C');
        }
      }
      cellTypeToString.add(sb.toString());
    }
    startGame(deck, width, height, cellTypeToString);
  }

  @Override
  public void startGame(String gridFilePath, String deckFilePath) {

  }

  @Override
  public void addFeatures(ModelFeatures features) {

  }

  @Override
  public int numOfCardsInDeck() {
    return 0;
  }

  @Override
  public int getGridWidth() {
    return getBoard().length;
  }

  @Override
  public int getGridHeight() {
    return getBoard()[0].length;
  }

  @Override
  public boolean isGameWon() {
    return false;
  }

  @Override
  public List<C> getHand(PlayerColor player) {
    return List.of();
  }

  @Override
  public C getCardAt(int row, int col) {
    return getBoard()[row][col].getCard();
  }

  @Override
  public CellType getCellTypeAt(int row, int col) {
    return cellStateToType(getBoard()[row][col].getCellState());
  }

  private CellType cellStateToType(CellState cellState) {
    if (cellState == CellState.CARD_SPACE) {
      return CellType.CARDCELL;
    } else if (cellState == CellState.HOLE) {
      return CellType.HOLE;
    } else {
      return null;
    }
  }

  @Override
  public PlayerColor getCellPlayerAt(int row, int col) {
    return colorConverter(getBoard()[row][col].getOwner());
  }

  private PlayerColor colorConverter(Player player) {
    if (player == null) {
      return null;
    }
    return player == Player.RED ? PlayerColor.RED : PlayerColor.BLUE;
  }

  @Override
  public GameStatus getStatus() {
    if (isGameOver()) {
      return GameStatus.GameOver;
    } else if (!gameStarted) {
      return GameStatus.NotStarted;
    } else {
      return GameStatus.Playing;
    }
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    return colorConverter(getTurn());
  }

  @Override
  public boolean legalPlayToCell(int row, int col) {
    return legalPlay(row, col);
  }

  @Override
  public int numCardsFlippedWhenPlayed(int row, int col, int cardInHandIdx) {
    return cardsFlipped(row, col, cardInHandIdx, getTurn());
  }

  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    int score = 0;
    Player player = playerColor == PlayerColor.RED ? Player.RED : Player.BLUE;
    for (int row = 0; row < getBoard().length; row++) {
      for (int col = 0; col < getBoard()[0].length; col++) {
        if (getBoard()[row][col].getOwner() == player) {
          score++;
        }
      }
    }
    return score;
  }
}
