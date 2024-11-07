package cs3500.controller.strategy;

import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;

/**
 * Class that performs the strategy of playing in the corners. The hardest card to flip in a corner
 * is determined by the sum of its two exposed parts of the card (ie SOUTH/EAST for the
 * top left card.
 */
public class CornersStrategy implements ThreeTriosStrategy {

  @Override
  public int[] choosePosition(GameGrid model, Player player) {
    int rowMax = 0;
    int colMax = 0;
    int handMax = 0;
    int maxPower = 0;

    int[] rowCorner = {0, model.getBoard().length - 1};
    int[] colCorner = {0, model.getBoard()[0].length - 1};

    for (int row = 0; row < rowCorner.length; row++) {
      for (int col = 0; col < colCorner.length; col++) {
        int currRow = rowCorner[row];
        int currCol = colCorner[col];
        for (int handIdx = 0; handIdx < model.getHand(player).size(); handIdx++) {
          if (model.legalCard(row, col)) {
            NESWCard currCard = (NESWCard) model.getHand(player).get(handIdx);
            int power = 0;
            if (currRow == 0) {
              power += currCard.getSouth().getValue();
            } else {
              power += currCard.getNorth().getValue();
            }
            if (currCol == 0) {
              power += currCard.getEast().getValue();
            } else {
              power += currCard.getWest().getValue();
            }
            if (power > maxPower) {
              maxPower = power;
              rowMax = row;
              colMax = col;
              handMax = handIdx;
            }

          }
        }

      }
    }

    return new int[]{rowMax, colMax, handMax};
  }
}
