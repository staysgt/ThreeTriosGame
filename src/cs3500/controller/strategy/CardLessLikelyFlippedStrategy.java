package cs3500.controller.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.Player;

/**
 * Class that performs the strategy of playing the card that is least likely to be flipped.
 */
public class CardLessLikelyFlippedStrategy<C extends Card> implements ThreeTriosStrategy<C> {
  @Override
  public List<int[]> choosePosition(GameGrid<C> model, Player player) {
    List<int[]> bestMoves = new ArrayList<>();
    int leastFlips = Integer.MAX_VALUE;
    FlipMostStrategy<C> flipMostOpp = new FlipMostStrategy<>();

    // iterates through rows
    for (int row = 0; row < model.getBoard().length; row++) {
      // iterates through the columns of the grid
      for (int col = 0; col < model.getBoard()[0].length; col++) {
        // goes through each hand in the players hand
        if (model.legalPlay(row, col)) {
          for (int handIdx = 0; handIdx < model.getHand(player).size(); handIdx++) {
            // if this card is placed in this row/col, how many cards will the other player
            // be able to flip
            GameGridModel<C> testModel = new GameGridModel<C>(model.getBoard(),
                    model.getHand(Player.RED),
                    model.getHand(Player.BLUE));
            testModel.playToGrid(row, col, handIdx);
            // now i need to know how many cards would be flipped for the other player if this move
            // were to be made
            Player opposingPlayer = player == Player.RED ? Player.BLUE : Player.RED;
            flipMostOpp.choosePosition(testModel, opposingPlayer);
            flipMostOpp.getMostFlips();

            if (flipMostOpp.getMostFlips() < leastFlips) {
              bestMoves.clear();
              bestMoves.add(new int[]{row, col, handIdx});
              leastFlips = flipMostOpp.getMostFlips();
            } else if (flipMostOpp.getMostFlips() == leastFlips) {
              bestMoves.add(new int[]{row, col, handIdx});
            }
          }

        }
      }
    }
    return List.copyOf(bestMoves);
  }
}
