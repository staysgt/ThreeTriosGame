package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

import cs3500.controller.strategy.CornersStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.Player;

/**
 * Tests for the corner strategy.
 */
public class CornersStrategyTests<C extends Card> {

  private final GameGrid<C> modelNH = new GameGridModel<>(new Random(3));
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
  public void testFirstMoveEmptyGrid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    ThreeTriosStrategy<C> corners = new CornersStrategy<>();

    for (int i = 0; i < corners.choosePosition(modelNH, Player.RED).size(); i++) {
      System.out.println(Arrays.toString(corners.choosePosition(modelNH, Player.BLUE).get(i)));
    }

    Assert.assertEquals(1, corners.choosePosition(modelNH, Player.RED).size());
    Assert.assertEquals(Arrays.toString(new int[]{0, 0, 4}),
            Arrays.toString(corners.choosePosition(modelNH, Player.RED).getFirst()));

  }

  @Test
  public void testFullCornersReturnsNull() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    // playing to grid so that all the corners are full
    modelNH.playToGrid(0, 0, 0);
    modelNH.playToGrid(2, 4, 0);
    modelNH.playToGrid(2, 0, 0);
    modelNH.playToGrid(0, 4, 0);

    CornersStrategy<C> corners = new CornersStrategy<>();

    Assert.assertNull(corners.choosePosition(modelNH, Player.BLUE));
    Assert.assertNull(corners.choosePosition(modelNH, Player.RED));
  }

  @Test
  public void testSemiFullCorners() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    // playing to grid so that most corners are full
    modelNH.playToGrid(0, 0, 0);
    modelNH.playToGrid(2, 4, 0);
    modelNH.playToGrid(2, 0, 0);


    CornersStrategy<C> corners = new CornersStrategy<>();
    Assert.assertEquals(Arrays.toString(new int[]{0, 4, 2}),
            Arrays.toString(corners.choosePosition(modelNH, Player.BLUE).getFirst()));

  }


}
