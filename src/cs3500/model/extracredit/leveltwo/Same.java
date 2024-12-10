package cs3500.model.extracredit.leveltwo;

import java.util.List;
import java.util.ArrayList;


import cs3500.model.NESWCard;
import cs3500.model.Card;
import cs3500.model.GameGridModel;


public class Same<C extends Card> extends GameGridModel<C> {

  /**
   * This is the adjacent card and is used to make the cards same.
   * @param cardA card
   * @param adjacentCard card
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
