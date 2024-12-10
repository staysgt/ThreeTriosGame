package cs3500.model.extracredit.leveltwo;

import java.util.List;

import cs3500.model.Card;
import cs3500.model.Cell;
import cs3500.model.CellInterface;
import cs3500.model.GameGrid;
import cs3500.model.Player;

public class VariantRuleSetTwo<C extends Card> implements GameGrid<C> {

  private GameGrid<C> model;
  private CellInterface cell;
  // private List<RuleTwoInf> rules;


  //public VariantRuleSetTwo(GameGrid<C> model, CellInterface cell, List<RuleTwoInf> rules) {
  // this.model = model;
  // this.cell = cell;
  // this.rules = rules;
  // }


  @Override
  public void playToGrid(int row, int col, int handIdx) {

  }

  @Override
  public void startGame(List<C> cards, int cols, int rows, List<String> rowConf) {
    model.startGame(cards, cols, rows, rowConf);
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
  public Cell<C>[][] getBoard() {
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
