package cs3500.model.extracredit.levelone;

import cs3500.model.AttVal;
import cs3500.model.Card;
import cs3500.model.CellInterface;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;


/**
 * This class uses the decorator pattern and is the fallen ace rule.
 *
 * @param <C> this is the card
 */
public class FallenAce<C extends Card> implements RuleOneInf<C> {


  /**
   * This makes it so that the attack value one is equal to attack value A.
   * @param cardA
   * @param cardB
   * @param currentPlayer
   * @param model
   * @param cell
   */
  @Override
  public void battle(NESWCard cardA, NESWCard cardB, Player currentPlayer, GameGrid<C> model, CellInterface cell) {
    if (cardA.getNorthOurs().equals(AttVal.ONE) && cardB.getSouthOurs().equals(AttVal.A)) {
      cell.setOwner(currentPlayer);
    }
  }
}