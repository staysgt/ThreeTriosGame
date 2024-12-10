package cs3500.model.extracredit.levelone;

import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;

/**
 * This is an interface that is used to update the cards.
 *
 * @param <C> card
 */
public interface LevelOneInf<C extends Card> extends GameGrid<C> {

  /**
   * Updates the cards to be the correct value based on the rules.
   *
   * @return a list of updated cards.
   */
  List<C> updateCards(List<C> cards);
}
