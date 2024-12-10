//package cs3500.controller.strategy;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cs3500.model.Card;
//import cs3500.model.GameGrid;
//import cs3500.model.Player;
//
/// **
// * Decorator for minimax strategy. If unable to combine with minimax, this will return
// * the positions for the base (or given) strategy.
// *
// * @param <C> generic type for card interface.
// */
//public class MinimaxDecorator<C extends Card> implements ThreeTriosStrategy<C> {
//
//  private final ThreeTriosStrategy<C> baseStrategy;
//
//  public MinimaxDecorator(ThreeTriosStrategy<C> baseStrategy) {
//    this.baseStrategy = baseStrategy;
//  }
//
//  @Override
//  public List<int[]> choosePosition(GameGrid model, Player player) {
//    List<int[]> baseList = baseStrategy.choosePosition(model, player);
//
//    MiniMaxStrategy<C> miniMaxStrategy = new MiniMaxStrategy<>();
//    List<int[]> minimaxPosns = miniMaxStrategy.choosePosition(model, player);
//
//    List<int[]> combinedPosns = new ArrayList<>();
//
//    for (int baseIdx = 0; baseIdx < baseList.size(); baseIdx++) {
//      for (int miniMaxIdx = 0; miniMaxIdx < minimaxPosns.size(); miniMaxIdx++) {
//        if (baseList.get(baseIdx)[0] == minimaxPosns.get(miniMaxIdx)[0] &&
//                baseList.get(baseIdx)[1] == minimaxPosns.get(miniMaxIdx)[1] &&
//                baseList.get(baseIdx)[2] == minimaxPosns.get(miniMaxIdx)[2]) {
//          combinedPosns.add(baseList.get(baseIdx));
//        }
//      }
//    }
//
//    if (!combinedPosns.isEmpty()) {
//      return combinedPosns;
//    } else {
//      // shouold this return null? should decide-
//      return baseList;
//    }
//  }
//}
