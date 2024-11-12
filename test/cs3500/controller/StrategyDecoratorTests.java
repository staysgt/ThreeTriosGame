package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cs3500.controller.strategy.CardLessLikelyDecorator;
import cs3500.controller.strategy.CornersDecorator;
import cs3500.controller.strategy.CornersStrategy;
import cs3500.controller.strategy.FlipMostDecorator;
import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.Player;

/**
 * Testing class for testing the decorators.
 */
public class StrategyDecoratorTests<C extends Card> {

  private final GameGrid<C> modelNH = new GameGridModel<>(new Random(3));
  NESWCardFileReader<C> cardFile;
  ConfigurationFileReader noHoles;

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
  public void testCornerWithFlipMostDecoratorEmptyGrid() {
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
    modelNH.playToGrid(1, 1, 1);

    ThreeTriosStrategy<C> cornersConcrete = new CornersStrategy<>();
    ThreeTriosStrategy<C> flipMostDecorator = new FlipMostDecorator<>(cornersConcrete);

    List<int[]> bestMoves = flipMostDecorator.choosePosition(modelNH, Player.BLUE);

    Assert.assertEquals(Arrays.toString(new int[]{2, 4, 0}), Arrays.toString(bestMoves.get(0)));
  }

  @Test
  public void testFlipMostWithCornerDecorator() {
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
    modelNH.playToGrid(1, 1, 1);

    ThreeTriosStrategy<C> flipMostConcrete = new FlipMostStrategy<>();
    ThreeTriosStrategy<C> cornersDecorator = new CornersDecorator<>(flipMostConcrete);

    List<int[]> bestMoves = cornersDecorator.choosePosition(modelNH, Player.BLUE);

    for (int i = 0; i < bestMoves.size(); i++) {
      System.out.println(Arrays.toString(bestMoves.get(i)));
    }

    Assert.assertEquals(Arrays.toString(new int[]{2, 2, 1}), Arrays.toString(bestMoves.get(0)));
  }


  @Test
  public void testFlipMostWithLessLikelyCardDecorator() {
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
    modelNH.playToGrid(1, 1, 1);

    ThreeTriosStrategy<C> flipMostConcrete = new FlipMostStrategy<>();
    ThreeTriosStrategy<C> cLLDecorator = new CardLessLikelyDecorator<>(flipMostConcrete);

    List<int[]> bestMoves = cLLDecorator.choosePosition(modelNH, Player.BLUE);

    for (int i = 0; i < bestMoves.size(); i++) {
      System.out.println(Arrays.toString(bestMoves.get(i)));
    }

    Assert.assertEquals(Arrays.toString(new int[]{2, 2, 1}), Arrays.toString(bestMoves.get(0)));
  }


}
