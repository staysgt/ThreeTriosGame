package cs3500.controller.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * Class that performs the strategy of flipping the most cards.
 */
public class FlipMostStrategy<C extends Card> implements ThreeTriosStrategy<C> {

  /**
   * Constructor for strategy that flips the most cards.
   */
  public FlipMostStrategy() {
  }

  @Override
  public List<int[]> choosePosition(GameGrid<C> model, Player player) {
    // list to store the best moves for a players turn
    List<int[]> bestMoves = new ArrayList<>();

    int rowMax = 0;
    int colMax = 0;
    int handIdxMax = 0;
    int maxFlips = 0;
    // iterates through each card in the hand and then each space in the grid to determine the set
    // produces the maximum amount of spaces flipped
    for (int handIdx = 0; handIdx < model.getHand(model.getTurn()).size(); handIdx++) {
      for (int row = 0; row < model.getBoard().length; row++) {
        for (int col = 0; col < model.getBoard()[0].length; col++) {
          // since the for loops iterate through the rows starts from the top and cols starting
          // from L to R, the uppermost, leftmost will always be the max and thus won't be replaced
          // by the new max.
          if (model.legalPlay(row, col)) {
            int flips = model.cardsFlipped(row, col, handIdx, player);
            if (flips > maxFlips) {
              bestMoves.clear();
              bestMoves.add(new int[]{row, col, handIdx});
              maxFlips = flips;
            } else if (flips == maxFlips) {
              bestMoves.add(new int[]{row, col, handIdx});
            }
          }
        }
      }
    }

    return bestMoves;
  }
}
