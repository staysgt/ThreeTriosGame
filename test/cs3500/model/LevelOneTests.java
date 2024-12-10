package cs3500.model;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.NESWCardFileReader;
import cs3500.view.GameGridTextView;

public class LevelOneTests<C extends Card> {
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

  private GameGrid model = new GameGridModel(cardFile.getCards(), new Random(3));


  @Test
  public void testReverseTwoMoves() {
    GameGridTextView<C> view = new GameGridTextView<C>(model);
    model.playToGrid(0, 0, 0);
    view.render();


  }
}
