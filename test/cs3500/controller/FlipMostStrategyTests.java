package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;


import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.Player;
import cs3500.view.GameGridTextView;

/**
 * Tests for flipping the most cards strategy.
 */
public class FlipMostStrategyTests<C extends Card> {
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
  public void testChoosePosnEmptyGrid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    ThreeTriosStrategy<C> flipMost = new FlipMostStrategy<>();

    // the list should contain every possible combination since there would be a tie
    Assert.assertEquals(120, flipMost.choosePosition(modelNH, Player.RED).size());

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
    GameGridTextView<C> view = new GameGridTextView<>(modelNH);
    view.render();

    for (int i = 0; i < flipMost.choosePosition(modelNH, Player.RED).size(); i++) {
      System.out.println(Arrays.toString(flipMost.choosePosition(modelNH, Player.RED).get(i)));
    }

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
    GameGridTextView<C> view = new GameGridTextView<>(modelNH);
    view.render();

    Assert.assertEquals(Arrays.toString(new int[]{2, 2, 1}),
            Arrays.toString(flipMost.choosePosition(modelNH, Player.BLUE).get(0)));
  }

}
