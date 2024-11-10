package cs3500.controller.strategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.swing.text.rtf.RTFEditorKit;

import cs3500.model.Card;
import cs3500.model.Cell;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.NESWCard;
import cs3500.model.Player;

/**
 * Class that finds the best position for the strategy of minimizing the max move the
 * opponent can make.
 */
public class MiniMaxStrategy implements ThreeTriosStrategy {
  @Override
  public List<int[]> choosePosition(GameGrid model, Player player) {
    return null;
  }

  // predicts the strategy that the user is using based on the
  private Strategies predictStrategy(GameGrid model, Player player) {
    HashMap<Strategies, Integer> strategiesScores = new HashMap<>();
    Player opposingPlayer = player == Player.RED ? Player.BLUE : Player.RED;
    int mostRecentTurn = (int) Collections.max(model.getGameStatuses().keySet());

    int numMovesMadeByOpponent = 0;

    if (opposingPlayer == Player.RED) {
      numMovesMadeByOpponent = (mostRecentTurn + 1) / 2;
    } else {
      numMovesMadeByOpponent = mostRecentTurn / 2;
    }

    FlipMostStrategy flipMost = new FlipMostStrategy();

    // if the opposing player is red, then you will want to look at the boards where the num
    // of moves is odd.
    int numMoves = opposingPlayer == Player.RED ? 1 : 2;
    while (numMoves <= numMovesMadeByOpponent) {
//      HashMap<Cell[][], List<Card>[]> entry = model.getGameStatuses().get(0);

//      Cell[][] grid = ;
//      Cell[][] alteredGrid =
//      getMostRecentMove()
      numMoves = numMoves + 2;
    }


    return null;
  }


  // gets the most recent move performed and returns the hand idx,
  private int[] getMostRecentMove(Cell[][] grid, Cell[][] alteredGrid, List<NESWCard> playersHand) {
    int changedRow = -1;
    int changedCol = -1;
    int handIdx = -1;
    String cardName = null;

    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (!Objects.equals(grid[row][col].getCard().getName(), alteredGrid[row][col].getCard().getName())) {
          changedRow = row;
          changedCol = col;
          cardName = alteredGrid[row][col].getCard().getName();
        }
      }
    }

    for (int idx = 0; idx < playersHand.size(); idx++) {
      if (cardName == playersHand.get(idx).getName()) {
        handIdx = idx;
      }
    }

    return new int[]{changedRow, changedCol, handIdx};
  }



}
