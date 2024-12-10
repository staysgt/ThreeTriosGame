package cs3500.model.extracredit;

import cs3500.model.Card;
import cs3500.model.CellInterface;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;

public class Reverse<C extends Card> implements RuleOneInf<C> {

  @Override
  public void battle(NESWCard cardA, NESWCard cardB, Player currentPlayer, GameGrid<C> model, CellInterface cell) {
    if (cardA.getNorthOurs().compareTo(cardB.getSouthOurs()) < 0) {
      cell.setOwner(currentPlayer);
    }
  }


}