package cs3500.controller.strategy;

import java.util.ArrayList;
import java.util.List;

import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.Player;

public class CardLessLikelyDecorator<C extends Card> implements ThreeTriosStrategy {
  private final ThreeTriosStrategy<C> baseStrategy;

  public CardLessLikelyDecorator(ThreeTriosStrategy<C> baseStrategy) {
    this.baseStrategy = baseStrategy;
  }

  @Override
  public List<int[]> choosePosition(GameGrid model, Player player) {
    List<int[]> baseList = baseStrategy.choosePosition(model, player);

    CardLessLikelyFlippedStrategy<C> cardStrat = new CardLessLikelyFlippedStrategy<>();
    List<int[]> cardBestPosns = cardStrat.choosePosition(model, player);

    List<int[]> combinedPosns = new ArrayList<>();

    for (int baseIdx = 0; baseIdx < baseList.size(); baseIdx++) {
      for (int lessLikelyIdx = 0; lessLikelyIdx < cardBestPosns.size(); lessLikelyIdx++) {
        if (baseList.get(baseIdx)[0] == cardBestPosns.get(lessLikelyIdx)[0] &&
                baseList.get(baseIdx)[1] == cardBestPosns.get(lessLikelyIdx)[1] &&
                baseList.get(baseIdx)[2] == cardBestPosns.get(lessLikelyIdx)[2]) {
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
