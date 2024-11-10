package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

import cs3500.controller.strategy.CornersStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.Player;

/**
 * Tests for the corner strategy.
 */
public class CornersStrategyTests {

  private GameGrid modelNH = new GameGridModel(new Random(3));
  private ConfigurationFileReader conFigFile;
  private ConfigurationFileReader noHoles;
  private NESWCardFileReader cardFile;
  private NESWCardFileReader badCardFile;

  {
    try {
      conFigFile = new ConfigurationFileReader("src" + File.separator + "walkableholes");
      cardFile = new NESWCardFileReader("src/cardsexample");
      badCardFile = new NESWCardFileReader("src/notenoughcards");
      noHoles = new ConfigurationFileReader("src/noholes");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testFirstMoveEmptyGrid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    ThreeTriosStrategy corners = new CornersStrategy();

    for (int i = 0; i < corners.choosePosition(modelNH, Player.RED).size(); i++) {
      System.out.println(Arrays.toString((int[]) corners.choosePosition(modelNH, Player.BLUE).get(i)));
    }

    Assert.assertEquals(1, corners.choosePosition(modelNH, Player.RED).size());
    Assert.assertEquals(Arrays.toString(new int[]{0, 0, 4}),
            Arrays.toString((int[]) corners.choosePosition(modelNH, Player.RED).get(0)));

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

    CornersStrategy corners = new CornersStrategy();

    Assert.assertEquals(null, corners.choosePosition(modelNH, Player.BLUE));
    Assert.assertEquals(null, corners.choosePosition(modelNH, Player.RED));
  }


}
