package cs3500.model.extracredit.levelone;

import java.util.List;

import cs3500.model.Card;
import cs3500.model.CellInterface;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;

/**
 * This is the variant rule set one class
 *
 * @param <C> card
 */
public class VariantRuleSetOne<C extends Card> implements GameGrid<C> {

  private final GameGrid<C> model;
  private final CellInterface cell;
  private final List<RuleOneInf<C>> rules;


  public VariantRuleSetOne(GameGrid<C> model, CellInterface cell, List<RuleOneInf<C>> rules) {
    this.model = model;
    this.cell = cell;
    this.rules = rules;
  }

  /**
   * This incoorporates the ruleOneInf and takes in the model
   * @param cardA
   * @param cardB
   * @param currentPlayer
   */
  public void battleRules(NESWCard cardA, NESWCard cardB, Player currentPlayer) {
    for (RuleOneInf<C> rule : rules) {
      rule.battle(cardA, cardB, currentPlayer, model, cell);
    }
  }

  /**
   * This gets CardA and CardB to b
   * @param row x coordinate of grid.
   * @param col y coordinate of grid.
   * @param handIdx index of the card in the grid.
   */
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
