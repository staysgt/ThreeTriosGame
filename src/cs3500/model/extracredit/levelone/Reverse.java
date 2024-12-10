package cs3500.model.extracredit.levelone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs3500.model.AttVal;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;

/**
 * Implementation of the reverse rule.
 * The card that is less than now wins.
 * @param <C> card.
 */
public class Reverse<C extends Card> extends AbstractLevelOne<C> {

  List<C> cards;

  public Reverse(GameGrid<C> model, List<C> cards) {
    super(model, cards);
    this.cards = cards;
    updateCards();
  }

  @Override
  public List<C> updateCards() {
    List<C> newCards = new ArrayList<>();
    for (C card : cards) {
      // subtracts the current value from 10 to get inverse of it
      int north = 10 - card.getNorthOurs().getValue();
      int south = 10 - card.getSouthOurs().getValue();
      int east = 10 - card.getEastOurs().getValue();
      int west = 10 - card.getWestOurs().getValue();

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
