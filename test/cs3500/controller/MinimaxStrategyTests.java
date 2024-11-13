package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Random;

import cs3500.controller.strategy.CardLessLikelyFlippedStrategy;
import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.MiniMaxStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.MockFlipModel;
import cs3500.model.MockMiniMaxModel;
import cs3500.model.Player;

/**
 * Testing class for minimax strategies.
 *
 * @param <C> Generic type for cards.
 */
public class MinimaxStrategyTests<C extends Card> {

  private final GameGrid<C> modelNH = new GameGridModel<C>(new Random(3));
  private final ConfigurationFileReader noHoles;
  private final NESWCardFileReader<C> cardFile;

  {
    try {
      ConfigurationFileReader conFigFile =
              new ConfigurationFileReader("src" + File.separator + "walkableholes");
      cardFile = new NESWCardFileReader("src/cardsexample");
      NESWCardFileReader<C> badCardFile =
              new NESWCardFileReader("src/notenoughcards");
      noHoles = new ConfigurationFileReader("src/noholes");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testMinimaxEmptyGrid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    ThreeTriosStrategy<C> minimax = new MiniMaxStrategy<>();
    Assert.assertNull(minimax.choosePosition(modelNH, Player.RED));
  }


  @Test
  public void testMinimaxWithFlipMostBeingUsed() {
    // RED is using strategy
    // BLUE is trying to guess what strategy theyre using

    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    FlipMostStrategy<C> flipMostStrategy = new FlipMostStrategy<>();
    // R
    modelNH.playToGrid(0, 0, 0);
    modelNH.playToGrid(0, 1, 0);

    // R
    int[] flip2 = flipMostStrategy.choosePosition(modelNH, Player.RED).getFirst();
    modelNH.playToGrid(flip2[0], flip2[1], flip2[2]);
    modelNH.playToGrid(1, 0, 0);

    // R
    int[] flip3 = flipMostStrategy.choosePosition(modelNH, Player.RED).getFirst();
    modelNH.playToGrid(flip3[0], flip3[1], flip3[2]);
    MiniMaxStrategy<C> minimax = new MiniMaxStrategy<>();
    minimax.choosePosition(modelNH, Player.BLUE);
    Assert.assertNull(minimax.choosePosition(modelNH, Player.BLUE));
  }

  @Test
  public void testMiniMaxWithMock() {
    MockMiniMaxModel<C> mockModel = new MockMiniMaxModel<>();
    mockModel.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    mockModel.playToGrid(0, 2, 0);
    mockModel.playToGrid(1, 0, 0);

    ThreeTriosStrategy<C> strategy = new MiniMaxStrategy<>();

    mockModel.isForStrategy(true);
    strategy.choosePosition(mockModel, Player.RED);

    Assert.assertNull(mockModel.getTranscript());
  }
}
