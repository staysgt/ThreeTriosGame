package cs3500.model;

public class FallenAce<C extends Card> implements RuleOneInf<C> {


  @Override
  public void apply(NESWCard cardA, NESWCard cardB, Player currentPlayer, GameGrid<C> model, CellInterface cell) {
    if (cardA.getNorthOurs().equals(AttVal.ONE) && cardB.getSouthOurs().equals(AttVal.A)) {
      cell.setOwner(currentPlayer);
    }
  }
}