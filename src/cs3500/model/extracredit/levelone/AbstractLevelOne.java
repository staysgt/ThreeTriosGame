package cs3500.model.extracredit.levelone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs3500.model.Card;
import cs3500.model.CellInterface;
import cs3500.model.GameGrid;
import cs3500.model.Player;

public abstract class AbstractLevelOne<C extends Card> implements LevelOneInf<C> {
  private final GameGrid<C> model;
  private List<C> blueHand = new ArrayList<>();
  private List<C> redHand = new ArrayList<>();
  //  private final Map<Variant, Boolean> variants;
  private CellInterface[][] grid;
  private List<C> cards;

  public AbstractLevelOne(GameGrid<C> model, List<C> cards) {
    this.model = model;
    this.cards = cards;
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    model.playToGrid(row, col, handIdx);
  }


  @Override
  public void startGame(int cols, int rows, List<String> rowConf) {
    model.startGame(cols, rows, rowConf);
    this.grid = model.getBoard();
    this.blueHand = model.getHand(Player.BLUE);
    this.redHand = model.getHand(Player.RED);

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
