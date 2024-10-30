package cs3500.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class GameGridModelTests {
  private GameGrid model = new GameGridModel();
  private ConfigurationFileReader conFigFile;
  private NESWCardFileReader cardFile;
  private NESWCardFileReader badCardFile;

  {
    try {
      conFigFile = new ConfigurationFileReader("src" + File.separator + "walkableholes");
      cardFile = new NESWCardFileReader("src/cardsexample");
      badCardFile = new NESWCardFileReader("src/notenoughcards");
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

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameThrowsInvalidCols() {
    model.startGame(cardFile.getCards(), -3, conFigFile.getRows(), conFigFile.getRowConfig());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameThrowsInvalidRows() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), 0 , conFigFile.getRowConfig());
  }

  @Test (expected = IllegalStateException.class)
  public void testStartGameTwice() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameGivenNulls() {
    model.startGame(null, conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameCardsContainsNull() {
    List<NESWCard> cards1 = new ArrayList<>();
    cards1.add(null);
    model.startGame(cards1, conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCards() {
    model.startGame(badCardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
  }

  @Test
  public void testValidMakeMove() {

  }








  @Test(expected = IllegalStateException.class)
  public void testGetBoardGameNotStarted() {
    GameGridModel model = new GameGridModel();
    model.getBoard();
    assertEquals(model, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetBoardStartGameNotInBounds() {
    GameGridModel model = new GameGridModel();
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
    Cell[][] cells = model.getBoard();
    assertEquals(model, cells);
  }

  @Test
  public void testGetBoardStartGameEmpty() {
    GameGridModel model = new GameGridModel();
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
    Cell[][] cells = model.getBoard();
    assertNull("Empty List", cells);
  }

  @Test(expected = IllegalStateException.class)
  public void testIsCellHoleGameNotStarted() {
    GameGridModel model = new GameGridModel();
    model.isCellHole(0,0);
    assertEquals(model, null);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testIsCellHoleStartGameEmpty() {
    GameGridModel model = new GameGridModel();
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
    boolean cells = model.isCellHole(0,0);
    assertNull("Empty list", cells);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsCellHoleStartGameNotInBounds() {
    GameGridModel model = new GameGridModel();
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
    boolean cells = model.isCellHole(5,10);
    assertEquals(model, cells);
  }

}
