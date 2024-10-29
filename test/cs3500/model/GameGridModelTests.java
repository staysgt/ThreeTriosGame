package cs3500.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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
  public void testValidStartGame() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
    System.out.println(model.getHand(Player.RED));
    Assertions.assertTrue(model.getHand(Player.RED).contains(new NESWCard("dog", intToAV(5),
            intToAV(9),  intToAV(10),  intToAV(2))));

  }

  private NESWCard.AttVal intToAV(int num) {
    for (NESWCard.AttVal attackValue : NESWCard.AttVal.values()) {
      if(num == attackValue.getValue()) {
        return attackValue;
      }
    }
    throw new IllegalArgumentException("Provided num does not have an associated attack value");
  }


  @Test(expected = IllegalStateException.class)
  public void testCellIsntEmpty() {
    GameGridModel model = new GameGridModel();
    Card cell = model.getCellCard(5, 0);
    assertEquals(0, cell);
  }
}
