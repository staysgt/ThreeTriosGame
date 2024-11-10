package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

import javax.swing.text.rtf.RTFEditorKit;

import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.Player;
import cs3500.view.GameGridTextView;
import cs3500.view.TextView;

/**
 * Tests for flipping the most cards strategy.
 */
public class FlipMostStrategyTests {
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
  public void testChoosePosnEmptyGrid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    ThreeTriosStrategy flipMost = new FlipMostStrategy();

    // the list should contain every possible combination since there would be a tie
    Assert.assertEquals(120, flipMost.choosePosition(modelNH, Player.RED).size());

    Assert.assertEquals(Arrays.toString(new int[]{0, 0, 0}),
            Arrays.toString((int[]) flipMost.choosePosition(modelNH, Player.RED).get(0)));

  }

  @Test
  public void testChoosePosnMidGame() {
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

    ThreeTriosStrategy flipMost = new FlipMostStrategy();
    GameGridTextView view = new GameGridTextView(modelNH);
    view.render();

    for (int i = 0; i < flipMost.choosePosition(modelNH, Player.RED).size(); i++) {
      System.out.println(Arrays.toString((int[]) flipMost.choosePosition(modelNH, Player.RED).get(i)));
    }

    Assert.assertEquals(Arrays.toString(new int[]{1, 1, 1}),
            Arrays.toString((int[]) flipMost.choosePosition(modelNH, Player.RED).get(0)));

  }

}
