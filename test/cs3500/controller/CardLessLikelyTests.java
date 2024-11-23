package cs3500.controller;


import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cs3500.controller.strategy.CardLessLikelyFlippedStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.MockFlipModel;
import cs3500.model.Player;

/**
 * Testing class for strategy of placing a card that is less likely to be flipped.
 */
public class CardLessLikelyTests<C extends Card> {

  private final GameGrid<C> modelNH = new GameGridModel(new Random(3));
  private final ConfigurationFileReader noHoles;
  private final NESWCardFileReader<C> cardFile;

  {
    try {
      ConfigurationFileReader conFigFile =
              new ConfigurationFileReader("src" + File.separator + "walkableholes");
      cardFile = new NESWCardFileReader<>("src/cardsexample");
      NESWCardFileReader<C> badCardFile = new NESWCardFileReader<>("src/notenoughcards");
      noHoles = new ConfigurationFileReader("src/noholes");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCLLFirstMoveEmptyGrid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    ThreeTriosStrategy cardLL = new CardLessLikelyFlippedStrategy();

    List<int[]> listMoves = cardLL.choosePosition(modelNH, Player.RED);

    for (int i = 0; i < listMoves.size(); i++) {
      System.out.println(Arrays.toString(listMoves.get(i)));
    }

    List<int[]> expected = Arrays.asList(
            new int[]{0, 0, 0},
            new int[]{0, 0, 1},
            new int[]{0, 0, 2},
            new int[]{0, 0, 3},
            new int[]{0, 0, 4},
            new int[]{0, 1, 0},
            new int[]{0, 1, 1},
            new int[]{0, 1, 2},
            new int[]{0, 1, 3},
            new int[]{0, 2, 0},
            new int[]{0, 2, 1},
            new int[]{0, 3, 0},
            new int[]{0, 3, 1},
            new int[]{0, 4, 0},
            new int[]{1, 0, 0}
    );

    for (int idx = 0; idx < expected.size(); idx++) {
      Assert.assertEquals(Arrays.toString(expected.get(idx)), Arrays.toString(listMoves.get(idx)));
    }

    Assert.assertEquals(15, listMoves.size());
  }

  @Test
  public void testCLLMoveMidGame() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    modelNH.playToGrid(0, 0, 0);
    modelNH.playToGrid(0, 1, 0);
    modelNH.playToGrid(0, 2, 0);
    modelNH.playToGrid(1, 0, 0);
    modelNH.playToGrid(2, 3, 0);
    modelNH.playToGrid(1, 2, 0);
    modelNH.playToGrid(2, 0, 0);
    modelNH.playToGrid(2, 1, 0);

    ThreeTriosStrategy cardLL = new CardLessLikelyFlippedStrategy();
    List<int[]> listMoves = cardLL.choosePosition(modelNH, Player.RED);

    System.out.println(listMoves.size());

    List<int[]> expected = Arrays.asList(
            new int[]{0, 3, 0},
            new int[]{0, 3, 1},
            new int[]{0, 3, 2},
            new int[]{0, 4, 0},
            new int[]{0, 4, 1},
            new int[]{1, 1, 0},
            new int[]{1, 3, 0}
    );

    for (int idx = 0; idx < expected.size(); idx++) {
      Assert.assertEquals(Arrays.toString(expected.get(idx)), Arrays.toString(listMoves.get(idx)));
    }
  }


  @Test
  public void testCLLWithMockEmptyGrid() {
    MockFlipModel<C> mockModel = new MockFlipModel<>();
    mockModel.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    ThreeTriosStrategy<C> cardLessLikely = new CardLessLikelyFlippedStrategy<>();
    cardLessLikely.choosePosition(mockModel, Player.RED);

    // with the mock there should be no best move for this strategy
    Assert.assertEquals(List.of(), cardLessLikely.choosePosition(mockModel, Player.RED));
  }

  @Test
  public void testCLLWithMockNotEmpty() {
    MockFlipModel<C> mockModel = new MockFlipModel<>();
    mockModel.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    ThreeTriosStrategy<C> cardLessLikely = new CardLessLikelyFlippedStrategy<>();


    mockModel.playToGrid(0, 2, 0);
    mockModel.playToGrid(1, 0, 0);
    mockModel.playToGrid(2, 0, 0);
    mockModel.playToGrid(0, 1, 0);

    List<int[]> bestMoves = cardLessLikely.choosePosition(mockModel, Player.RED);
    for (int i = 0; i < bestMoves.size(); i++) {
      System.out.println(Arrays.toString(bestMoves.get(i)));
    }
    // 0, 0, 0 is best move
    Assert.assertEquals(Arrays.toString(bestMoves.get(0)), Arrays.toString(new int[]{0, 0, 0}));

  }

}
