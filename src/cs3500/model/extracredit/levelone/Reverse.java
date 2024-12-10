package cs3500.model.extracredit.levelone;

import cs3500.model.Card;
import cs3500.model.CellInterface;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;

/**
 * This is the reverse rule and is implemented through the decorator pattern.
 *
 * @param <C>
 */
public class Reverse<C extends Card> implements RuleOneInf<C> {

  /**
   * This makes it so that if cardB is greater than card a the game flips
   * @param cardA
   * @param cardB
   * @param currentPlayer
   * @param model
   * @param cell
   */
  @Override
  public void battle(NESWCard cardA, NESWCard cardB, Player currentPlayer, GameGrid<C> model, CellInterface cell) {
    if (cardA.getNorthOurs().compareTo(cardB.getSouthOurs()) < 0) {
      cell.setOwner(currentPlayer);
    }
  }


}