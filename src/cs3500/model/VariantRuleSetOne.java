package cs3500.model;

import java.util.List;

public class VariantRuleSetOne<C extends Card> implements GameGrid<C>, ReadOnlyGameGridModel {

  private ReadOnlyGameGridModel model;
  private GameGrid<C> models;
  private CellInterface cell;
  private List<RuleOneInf> rules;


  public VariantRuleSetOne(ReadOnlyGameGridModel model, GameGrid<C> models, CellInterface cell, List<RuleOneInf> rules) {
    this.model = model;
    this.models = models
    this.cell = cell;
    this.rules = rules;
  }

  public void applyRules(NESWCard cardA, NESWCard cardB, Player currentPlayer) {
    for (RuleOneInf rule : rules) {
      rule.apply(cardA, cardB, currentPlayer, models, cell);
    }
  }

  @Override
  public void playToGrid(int row, int col, int handIdx) {
    NESWCard cardA = (NESWCard) model.getBoard()[row][col].getCard();
    NESWCard cardB = ??
    applyRules(cardA, cardB, model.getTurn());
    models.playToGrid(row, col, handIdx);
  }

  private void flipCard(NESWCard card, Player player) {
    cell.setOwner(player);
  }

  @Override
  public void startGame(List<C> cards, int cols, int rows, List<String> rowConf) {
    models.startGame(cards, cols, rows, rowConf);
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

