package cs3500.model.extracredit;

import java.util.List;

import cs3500.model.Card;
import cs3500.model.Cell;
import cs3500.model.CellInterface;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;

public class VariantRuleSetOne<C extends Card> implements GameGrid<C> {

  private GameGrid<C> model;
  private CellInterface cell;
  private List<RuleOneInf> rules;


  public VariantRuleSetOne(GameGrid<C> model, CellInterface cell, List<RuleOneInf> rules) {
    this.model = model;
    this.cell = cell;
    this.rules = rules;
  }

  public void battleRules(NESWCard cardA, NESWCard cardB, Player currentPlayer) {
    for (RuleOneInf rule : rules) {
      rule.battle(cardA, cardB, currentPlayer, model, cell);
    }
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    NESWCard cardA = (NESWCard) model.getBoard()[row][col].getCard();
    NESWCard cardB = (NESWCard) model.getBoard()[row][col].getCard();
    battleRules(cardA, cardB, model.getTurn());
    model.playToGrid(row, col, handIdx);
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
