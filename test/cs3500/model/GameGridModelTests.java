package cs3500.model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class GameGridModelTests {
  private GameGrid model = new GameGridModel();
  private ConfigurationFileReader conFigFile;
  private NESWCardFileReader cardFile;

  {
    try {
      conFigFile = new ConfigurationFileReader("src" + File.separator + "walkableholes");
      cardFile = new NESWCardFileReader("src/cardsexample");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void testStartGame() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());

  }

  @Test(expected = IllegalStateException.class)
  public void testIsCellEmpty() {
    GameGridModel model = new GameGridModel();
    Card cell = model.getCellCard(0, 0);
    assertEquals(0, cell);
  }

  @Test(expected = IllegalStateException.class)
  public void testCellIsntEmpty() {
    GameGridModel model = new GameGridModel();
    Card cell = model.getCellCard(5, 0);
    assertEquals(0, cell);
  }
}
