package cs3500.model;

public interface RuleOneInf<C extends Card> {

  void apply(NESWCard cardA, NESWCard cardB, Player currentPlayer, GameGrid<C> model, CellInterface cell);

}

