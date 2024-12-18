package cs3500.controller.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.NESWCard;
import cs3500.model.Player;

/**
 * Class that performs the strategy of playing in the corners. The hardest card to flip in a corner
 * is determined by the sum of its two exposed parts of the card (ie SOUTH/EAST for the
 * top left card.
 */
public class CornersStrategy<C extends Card> implements ThreeTriosStrategy<C> {

  StringBuilder transcript = new StringBuilder();

  @Override
  public List<int[]> choosePosition(GameGrid<C> model, Player player) {
    // list to store the best moves for a players turn
    List<int[]> bestMoves = new ArrayList<>();
    boolean madeMove = false;
    int maxPower = -1;

    int[] rowCorner = {0, model.getBoard().length - 1};
    int[] colCorner = {0, model.getBoard()[0].length - 1};

    for (int row = 0; row < rowCorner.length; row++) {
      for (int col = 0; col < colCorner.length; col++) {
        int currRow = rowCorner[row];
        int currCol = colCorner[col];


        if (model.legalPlay(currRow, currCol)) {
          for (int handIdx = 0; handIdx < model.getHand(player).size(); handIdx++) {
            transcript.append("Checking: row: " + currRow + " col: " + currCol + " hand: " +
                    model.getHand(model.getTurn()).get(handIdx) + "\n");
            // checks if the spot is a valid place to play
            NESWCard currCard = (NESWCard) model.getHand(player).get(handIdx);
            int power = 0;
            if (currRow == 0) {
              power += currCard.getSouthOurs().getValue();
            } else {
              power += currCard.getNorthOurs().getValue();
            }
            if (currCol == 0) {
              power += currCard.getEastOurs().getValue();
            } else {
              power += currCard.getWestOurs().getValue();
            }
            if (power > maxPower) {
              transcript.append("New best move: row: " + currRow + " col: " + currCol + " hand: " +
                      model.getHand(model.getTurn()).get(handIdx) + "\n");
              bestMoves.clear();
              madeMove = true;
              bestMoves.add(new int[]{currRow, currCol, handIdx});
              maxPower = power;
            } else if (power == maxPower) {
              bestMoves.add(new int[]{currRow, currCol, handIdx});

            }
          }
        }

      }
    }

    if (!madeMove) {
      return null;
    }

    return bestMoves;
  }

  /**
   * Gets a transcript of the moves made.
   *
   * @return string version of the spots checked.
   */
  public String getTranscript() {
    return transcript.toString();
  }
}
