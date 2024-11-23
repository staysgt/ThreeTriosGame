package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;


import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.MockFlipModel;
import cs3500.model.Player;

/**
 * Tests for flipping the most cards strategy.
 */
public class FlipMostStrategyTests<C extends Card> {
  private final GameGrid<C> modelNH = new GameGridModel(new Random(3));
  private final ConfigurationFileReader noHoles;
  private final NESWCardFileReader<C> cardFile;

  {
    try {
      cardFile = new NESWCardFileReader<>("src/cardsexample");
      noHoles = new ConfigurationFileReader("src/noholes");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testChoosePosnEmptyGrid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    ThreeTriosStrategy<C> flipMost = new FlipMostStrategy<>();

    // the list should contain every possible combination since there would be a tie
    Assert.assertEquals(105, flipMost.choosePosition(modelNH, Player.RED).size());

    Assert.assertEquals(Arrays.toString(new int[]{0, 0, 0}),
            Arrays.toString(flipMost.choosePosition(modelNH, Player.RED).get(0)));

  }

  @Test
  public void testChoosePosnMidGameRED() {
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

    ThreeTriosStrategy<C> flipMost = new FlipMostStrategy<>();


    Assert.assertEquals(Arrays.toString(new int[]{1, 1, 1}),
            Arrays.toString(flipMost.choosePosition(modelNH, Player.RED).get(0)));
  }


  @Test
  public void testChoosePosnMidGameBLUE() {
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

    ThreeTriosStrategy<C> flipMost = new FlipMostStrategy<>();

    Assert.assertEquals(Arrays.toString(new int[]{2, 2, 1}),
            Arrays.toString(flipMost.choosePosition(modelNH, Player.BLUE).get(0)));
  }

  @Test
  public void testChoosePosnMidGame() {
    MockFlipModel<C> mockModel = new MockFlipModel<>();
    mockModel.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    mockModel.playToGrid(2, 0, 0);
    mockModel.playToGrid(2, 1, 0);
    mockModel.playToGrid(2, 2, 0);
    mockModel.playToGrid(1, 2, 0);

    ThreeTriosStrategy<C> flipMost = new FlipMostStrategy<>();
    Assert.assertEquals(Arrays.toString(new int[]{0, 0, 0}),
            Arrays.toString(flipMost.choosePosition(mockModel, Player.RED).get(0)));
    Assert.assertEquals(1, flipMost.choosePosition(mockModel, Player.RED).size());
    // should check this many spaces based on the number of spaces available/ cards left in game
    Assert.assertEquals(110, mockModel.getTranscript().split("\n").length);
  }


}
