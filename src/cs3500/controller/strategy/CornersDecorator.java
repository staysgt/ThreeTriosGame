package cs3500.controller.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

/**
 * Decorator for placing cards in the corners. If unable to combine with corners, this will return
 * the moves for the base (or given) strategy.
 *
 * @param <C> generic type for card interface.
 */
public class CornersDecorator<C extends Card> implements ThreeTriosStrategy<C> {
  private final ThreeTriosStrategy<C> baseStrategy;

  public CornersDecorator(ThreeTriosStrategy<C> baseStrategy) {
    this.baseStrategy = baseStrategy;
  }


  @Override
  public List<int[]> choosePosition(GameGrid<C> model, Player player) {
    List<int[]> baseList = baseStrategy.choosePosition(model, player);

    CornersStrategy<C> cornersStrategy = new CornersStrategy<>();
    List<int[]> cornersStrategyPosns = cornersStrategy.choosePosition(model, player);

    List<int[]> combinedPosns = new ArrayList<>();

    for (int baseIdx = 0; baseIdx < baseList.size(); baseIdx++) {
      for (int cornersIdx = 0; cornersIdx < cornersStrategyPosns.size(); cornersIdx++) {
        if (baseList.get(baseIdx)[0] == cornersStrategyPosns.get(cornersIdx)[0] &&
                baseList.get(baseIdx)[1] == cornersStrategyPosns.get(cornersIdx)[1] &&
                baseList.get(baseIdx)[2] == cornersStrategyPosns.get(cornersIdx)[2]) {
          combinedPosns.add(baseList.get(baseIdx));
        }
      }
    }

    if (!combinedPosns.isEmpty()) {
      return combinedPosns;
    } else {
      // shouold this return null? should decide-
      return baseList;
    }
  }

}
