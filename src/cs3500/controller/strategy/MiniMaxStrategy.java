package cs3500.controller.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cs3500.model.Card;
import cs3500.model.Cell;
import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * Class that finds the best position for the strategy of minimizing the max move the
 * opponent can make.
 */
public class MiniMaxStrategy<C extends Card> implements ThreeTriosStrategy<C> {

  // chooses the position for minimax strategy and defaults to playing flip most strategy
  // TODO: make it not default to flop most strategy
  @Override
  public List<int[]> choosePosition(GameGrid<C> model, Player player) {
    System.out.println(predictStrategy(model, player));
    if (predictStrategy(model, player) == null) {
      return null;
    }
    switch (predictStrategy(model, player)) {
      case FLIP_MOST:
        return block(new FlipMostStrategy<>(), model, player);
      case MINIMAX:
        return block(new MiniMaxStrategy<>(), model, player);
      case CORNERS:
        return block(new CornersStrategy<>(), model, player);
      case CARD_LESS_LIKELY:
        return block(new CardLessLikelyFlippedStrategy<>(), model, player);
      default:
        return null;
    }
  }

  // returns a list of moves that would block the opposing players best move
  private List<int[]> block(ThreeTriosStrategy<C> strategy, GameGrid<C> model, Player player) {
    Player opposingPlayer = player == Player.RED ? Player.BLUE : Player.RED;
    return strategy.choosePosition(model, opposingPlayer);

  }


  // predicts the strategy that the user is using
  private Strategies predictStrategy(GameGrid<C> model, Player player) {

    HashMap<Strategies, Integer> strategiesScore = new HashMap<>();
    Player opposingPlayer = player == Player.RED ? Player.BLUE : Player.RED;
    // need to subtract one since hte first stored board will be empty
    // makes it so that if turn 1 was just played then the MRT will be 1
    int mostRecentTurn = model.getGameStatuses().size() - 1;

    int numMovesMadeByOpponent = 0;

    if (opposingPlayer == Player.RED) {
      numMovesMadeByOpponent = (mostRecentTurn + 1) / 2;
    } else {
      numMovesMadeByOpponent = mostRecentTurn / 2;
    }

    // if the opposing player is red, then you will want to look at the boards where the num
    // of moves is odd.
    int numMoves = opposingPlayer == Player.RED ? 1 : 2;
    while (numMoves <= numMovesMadeByOpponent) {
      GameGrid<C> currModel = (GameGrid<C>) model.getGameStatuses().get(numMoves - 1);
      GameGrid<C> alteredModel = (GameGrid<C>) model.getGameStatuses().get(numMoves);


      Cell<Card>[][] grid = currModel.getBoard();
      Cell<Card>[][] alteredGrid = alteredModel.getBoard();
      List<C> currHand = currModel.getHand(opposingPlayer);
      int[] mostRecentMove = getMostRecentMove(grid, alteredGrid, currHand);


      // now that we have the most recent move, we need to check which strategies it alligns with
      FlipMostStrategy<C> flipMost = new FlipMostStrategy<C>();

      getStrategiesScore(strategiesScore, currModel, mostRecentMove, opposingPlayer);
      numMoves = numMoves + 2;
    }

    // iterate through the hashmap to see the strategy that the user is likely using
    Strategies bestStrat = null;
    int highest = 0;
    for (Strategies strat : strategiesScore.keySet()) {
      if (strategiesScore.get(strat) > highest) {
        highest = strategiesScore.get(strat);
        bestStrat = strat;
      }
    }

    return bestStrat;
  }


  private void getStrategiesScore(HashMap<Strategies, Integer> strategiesScore, GameGrid model,
                                  int[] lastMove, Player opposingPlayer) {
    strategiesScore.put(Strategies.FLIP_MOST, 0);
    strategiesScore.put(Strategies.MINIMAX, 0);
    strategiesScore.put(Strategies.CORNERS, 0);
    strategiesScore.put(Strategies.CARD_LESS_LIKELY, 0);

    ThreeTriosStrategy<C> currStrategy;
    Strategies currStrategyEnum;

    for (int strategy = 0; strategy < 4; strategy++) {
      switch (strategy) {
        case 0:
          currStrategy = new FlipMostStrategy();
          currStrategyEnum = Strategies.FLIP_MOST;
          break;
        case 1:
          currStrategy = new MiniMaxStrategy();
          currStrategyEnum = Strategies.MINIMAX;
          break;
        case 2:
          currStrategy = new CornersStrategy();
          currStrategyEnum = Strategies.CORNERS;
          break;
        default:
          currStrategy = new CardLessLikelyFlippedStrategy();
          currStrategyEnum = Strategies.CARD_LESS_LIKELY;
          break;
      }

      if (currStrategy.choosePosition(model, opposingPlayer) != null) {
        for (int idx = 0; idx < currStrategy.choosePosition(model, opposingPlayer).size(); idx++) {
          List<int[]> potentialMoves = currStrategy.choosePosition(model, opposingPlayer);
          if (potentialMoves.get(idx)[0] == lastMove[0] &&
                  potentialMoves.get(idx)[1] == lastMove[1] &&
                  potentialMoves.get(idx)[2] == lastMove[2]) {
            strategiesScore.put(currStrategyEnum, strategiesScore.get(currStrategyEnum) + 1);
          }
        }
      }
    }


  }


  // gets the most recent move performed and returns the hand idx,
  private int[] getMostRecentMove(Cell<Card>[][] grid, Cell<Card>[][] alteredGrid, List<C> playersHand) {
    int changedRow = -1;
    int changedCol = -1;
    int handIdx = -1;
    String cardName = null;

    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col].getCard() != null && alteredGrid[row][col].getCard() != null) {
          if (!Objects.equals(grid[row][col].getCard().getName(),
                  alteredGrid[row][col].getCard().getName())) {
            changedRow = row;
            changedCol = col;
            cardName = alteredGrid[row][col].getCard().getName();
          }
        }
      }
    }

    for (int idx = 0; idx < playersHand.size(); idx++) {
      if (Objects.equals(cardName, playersHand.get(idx).getName())) {
        handIdx = idx;
      }
    }

    return new int[]{changedRow, changedCol, handIdx};
  }
}
