package cs3500.model.extracredit.leveltwo;

import java.util.ArrayList;
import java.util.List;

import cs3500.model.GameGridModel;

import cs3500.model.Card;
import cs3500.model.NESWCard;

public class Plus<C extends Card> extends GameGridModel<C> {


  /**
   * This makes the cards adjacent when in opposite directions.
   * @param cardA card
   * @param adjacentCard list
   * @return flip
   */
  public List<NESWCard> cardAdjacentToA(NESWCard cardA, List<NESWCard> adjacentCard) {
    List<NESWCard> flip = new ArrayList<>();

    for (NESWCard card : adjacentCard) {

      // create for opposite directions
      if (cardA.getNorthOurs() == card.getSouthOurs() ||
              cardA.getSouthOurs() == card.getNorthOurs() ||
              cardA.getEastOurs() == card.getWestOurs() ||
              cardA.getWestOurs() == card.getEastOurs()) {

        // this is for the opposite opponent
        if (!card.getName().equals(cardA.getName())) {
          flip.add(card);
        }
      }
    }
    return flip;
  }
}
