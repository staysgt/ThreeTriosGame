package cs3500.model.extracredit.levelone;

import java.util.List;

import cs3500.model.Card;
import cs3500.model.CellInterface;
import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * This is an abstract level one class.
 *
 * @param <C> Card
 */
public abstract class AbstractLevelOne<C extends Card> implements LevelOneInf<C> {
  private final GameGrid<C> model;
  //  private final Map<Variant, Boolean> variants;

  public AbstractLevelOne(GameGrid<C> model) {
    this.model = model;
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    model.playToGrid(row, col, handIdx);
  }


  @Override
  public void startGame(List<C> cards, int cols, int rows, List<String> rowConf) {
    List<C> updatedCards = updateCards(cards);

    model.startGame(updatedCards, cols, rows, rowConf);
  }

  @Override
  public List<C> getHand(Player player) {
    return model.getHand(player);
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public boolean isCellHole(int row, int col) {
    return model.isCellHole(row, col);
  }

  @Override
  public Player getTurn() {
    return model.getTurn();
  }

  @Override
  public Player getWinner() {
    return model.getWinner();
  }

  @Override
  public CellInterface[][] getBoard() {
    return model.getBoard();
  }

  @Override
  public int getScore(Player player) {
    return model.getScore(player);
  }

  @Override
  public int cardsFlipped(int row, int col, int handIdx, Player player) {
    return model.cardsFlipped(row, col, handIdx, player);
  }

  @Override
  public boolean legalPlay(int row, int col) {
    return model.legalPlay(row, col);
  }

  @Override
  public List<GameGrid<C>> getGameStatuses() {
    return model.getGameStatuses();
  }
}
