package cs3500.model.extracredit.levelone;

import java.util.ArrayList;
import java.util.List;

import cs3500.model.AttVal;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;


/**
 * Class for fallen ace rule.
 * A now has value of 1.
 *
 * @param <C> card.
 */
public class FallenAce<C extends Card> extends AbstractLevelOne<C> {
  List<C> cards;

  public FallenAce(GameGrid<C> model, List<C> cards) {
    super(model, cards);
    this.cards = cards;
    updateCards();
  }

  @Override
  public List<C> updateCards() {
    List<C> newCards = new ArrayList<>();
    for (C card : cards) {
      // if equal to 10 (meaning ace, sets it equal to 1
      int north = card.getNorthOurs().getValue() == 10 ? 1 : card.getNorthOurs().getValue();
      int south = card.getSouthOurs().getValue() == 10 ? 1 : card.getSouthOurs().getValue();
      int east = card.getEastOurs().getValue() == 10 ? 1 : card.getEastOurs().getValue();
      int west = card.getWestOurs().getValue() == 10 ? 1 : card.getWestOurs().getValue();

      C updatedCard = (C) new NESWCard(card.getName(), intToAV(north), intToAV(south),
              intToAV(east), intToAV(west));
      newCards.add(updatedCard);
    }
    return newCards;
  }

  private AttVal intToAV(int num) {
    for (AttVal attackValue : AttVal.values()) {
      if (num == attackValue.getValue()) {
        return attackValue;
      }
    }
    throw new IllegalArgumentException("Provided num does not have an associated attack value");
  }
}
