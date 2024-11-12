package cs3500.controller.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

public class FlipMostDecorator<C extends Card> implements ThreeTriosStrategy<C> {
  private final ThreeTriosStrategy<C> baseStrategy;

  public FlipMostDecorator(ThreeTriosStrategy<C> baseStrategy) {
    this.baseStrategy = baseStrategy;
  }

  @Override
  public List<int[]> choosePosition(GameGrid<C> model, Player player) {
    List<int[]> baseList = baseStrategy.choosePosition(model, player);
    // now must add to the base list based on the flip most strategy
    FlipMostStrategy<C> flipMostStrategy = new FlipMostStrategy<>();
    List<int[]> flipMostPosns = flipMostStrategy.choosePosition(model, player);

    List<int[]> combinedPosns = new ArrayList<>();
    for (int baseIdx = 0; baseIdx < baseList.size(); baseIdx++) {
      for (int flipMostIdx = 0; flipMostIdx < flipMostPosns.size(); flipMostIdx++) {
        if (baseList.get(baseIdx)[0] == flipMostPosns.get(flipMostIdx)[0] &&
                baseList.get(baseIdx)[1] == flipMostPosns.get(flipMostIdx)[1] &&
                baseList.get(baseIdx)[2] == flipMostPosns.get(flipMostIdx)[2]) {
          combinedPosns.add(baseList.get(baseIdx));
        }
      }

    }

    if (!combinedPosns.isEmpty()) {
      return combinedPosns;
    } else {
      return null;
    }
  }
}
