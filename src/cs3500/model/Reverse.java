package cs3500.model;

public class Reverse<C extends Card> implements RuleOneInf<C> {

  @Override
  public void apply(NESWCard cardA, NESWCard cardB, Player currentPlayer, GameGrid<C> model, CellInterface cell) {
    if (cardA.getNorthOurs().compareTo(cardB.getSouthOurs()) < 0) {
      cell.setOwner(currentPlayer);
    }
  }


}