package cs3500.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.NESWCardFileReader;

import static org.junit.Assert.assertNull;

/**
 * Tests for the GameGridModel.
 */
public class GameGridModelTests {
  private GameGrid model = new GameGridModel(new Random(3));
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
  public void testValidStartGame() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    NESWCard card = new NESWCard("dog", intToAV(5), intToAV(9),
            intToAV(10), intToAV(2));
    Assert.assertTrue(model.getHand(Player.RED).contains(card));
  }

  private NESWCard.AttVal intToAV(int num) {
    for (NESWCard.AttVal attackValue : NESWCard.AttVal.values()) {
      if (num == attackValue.getValue()) {
        return attackValue;
      }
    }
    throw new IllegalArgumentException("Provided num does not have an associated attack value");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameThrowsInvalidCols() {
    model.startGame(cardFile.getCards(), -3, conFigFile.getRows(), conFigFile.getRowConfig());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameThrowsInvalidRows() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), 0, conFigFile.getRowConfig());
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameTwice() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameGivenNulls() {
    model.startGame(null, conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameCardsContainsNull() {
    List<NESWCard> cards1 = new ArrayList<>();
    cards1.add(null);
    model.startGame(cards1, conFigFile.getCols(), conFigFile.getRows(), conFigFile.getRowConfig());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCards() {
    model.startGame(badCardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
  }

  @Test
  public void testValidPlayToGrid() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    model.playToGrid(0, 0, 0);
    Assert.assertEquals(new NESWCard("horse", intToAV(2), intToAV(7),
            intToAV(8), intToAV(2)), model.getBoard()[0][0].getCard());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToGridInvalidHandIdx() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    model.playToGrid(0, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToGridInvalidRow() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    model.playToGrid(10, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayToGridInvalidCol() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    model.playToGrid(0, -1, 0);
  }

  @Test
  public void testPlayToGridChangesOwner() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    System.out.println(model.getHand(Player.BLUE));
    System.out.println(model.getHand(Player.RED));
    model.playToGrid(0, 0, 1);
    model.playToGrid(1, 0, 0);
    Assert.assertEquals(Player.BLUE, model.getBoard()[0][0].getOwner());
    Assert.assertEquals(Player.BLUE, model.getBoard()[1][0].getOwner());
  }

  @Test
  public void testGameOverTrue() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    model.playToGrid(0, 0, 0);
    model.playToGrid(1, 0, 0);
    model.playToGrid(2, 0, 0);
    model.playToGrid(0, 1, 0);
    model.playToGrid(1, 1, 0);
    model.playToGrid(2, 1, 0);
    model.playToGrid(0, 2, 0);
    model.playToGrid(1, 2, 0);
    model.playToGrid(2, 2, 0);
    model.playToGrid(0, 3, 0);
    model.playToGrid(1, 3, 0);
    model.playToGrid(2, 3, 0);
    model.playToGrid(0, 4, 0);
    model.playToGrid(1, 4, 0);
    model.playToGrid(2, 4, 0);

    Assert.assertEquals(Player.RED, model.getBoard()[0][0].getOwner());
    Assert.assertEquals(Player.RED, model.getBoard()[0][1].getOwner());
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testGameOverFalse() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    Assert.assertFalse(model.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testGameOverGameNotStarted() {
    model.isGameOver();
  }

  @Test
  public void testGameWinner() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    model.playToGrid(0, 0, 0);
    model.playToGrid(1, 0, 0);
    model.playToGrid(2, 0, 0);
    model.playToGrid(0, 1, 0);
    model.playToGrid(1, 1, 0);
    model.playToGrid(2, 1, 0);
    model.playToGrid(0, 2, 0);
    model.playToGrid(1, 2, 0);
    model.playToGrid(2, 2, 0);
    model.playToGrid(0, 3, 0);
    model.playToGrid(1, 3, 0);
    model.playToGrid(2, 3, 0);
    model.playToGrid(0, 4, 0);
    model.playToGrid(1, 4, 0);
    model.playToGrid(2, 4, 0);
    Assert.assertEquals(Player.RED, model.getWinner());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerGameNotOver() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    model.playToGrid(0, 0, 0);
    model.playToGrid(0, 1, 0);
    model.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerGameNotStarted() {
    model.getWinner();
  }

  @Test
  public void testGetTurnRedStart() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    Assert.assertEquals(Player.RED, model.getTurn());
  }

  @Test
  public void testGetTurnAfterOnePlay() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    model.playToGrid(0, 0, 0);
    Assert.assertEquals(Player.BLUE, model.getTurn());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetTurnGameNotStarted() {
    model.getTurn();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetBoardGameNotStarted() {
    model.getBoard();
    Assert.assertEquals(model, 0);
  }

  @Test
  public void testValidGetBoardDims() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    Assert.assertEquals(noHoles.getCols(), model.getBoard()[0].length);
    Assert.assertEquals(noHoles.getRows(), model.getBoard().length);
  }

  @Test
  public void testValidGetBoardAfterMove() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    System.out.println(model.getHand(model.getTurn()));
    model.playToGrid(0, 0, 0);
    Assert.assertEquals(new NESWCard("horse", intToAV(2), intToAV(7),
            intToAV(8), intToAV(2)), model.getBoard()[0][0].getCard());
  }

  @Test
  public void testGetBoardStartGameEmpty() {
    GameGridModel model = new GameGridModel();
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    Cell[][] cells = model.getBoard();
    assertNull(cells[0][0].getCard());
  }

  @Test(expected = IllegalStateException.class)
  public void testIsCellHoleGameNotStarted() {
    GameGridModel model = new GameGridModel();
    model.isCellHole(0, 0);
    assertEquals(model, 0);
  }


  @Test
  public void testIsCellHoleStartGameEmpty() {
    GameGridModel model = new GameGridModel();
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    boolean cells = model.isCellHole(0, 0);
    Assert.assertNotEquals(model, cells);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsCellHoleStartGameNotInBounds() {
    GameGridModel model = new GameGridModel();
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    boolean cells = model.isCellHole(5, 10);
    assertEquals(model, cells);
  }

  @Test
  public void testCardsFlipped() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    model.playToGrid(0, 0, 0);
    model.playToGrid(0, 1, 0);
    model.playToGrid(0, 2, 0);
    model.playToGrid(1, 0, 0);
    model.playToGrid(2, 3, 0);
    model.playToGrid(1, 2, 0);
    model.playToGrid(2, 0, 0);
    model.playToGrid(2, 1, 0);
    Assertions.assertEquals(0, model.cardsFlipped(0, 4, 0, Player.RED));
    Assertions.assertEquals(1, model.cardsFlipped(2, 2, 0, Player.RED));
  }

  @Test
  public void testGetScore() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    model.playToGrid(0, 0, 0);
    model.playToGrid(0, 1, 0);
    model.playToGrid(0, 2, 0);

    Assertions.assertEquals(1, model.getScore(Player.BLUE));
    Assertions.assertEquals(2, model.getScore(Player.RED));

  }

  @Test(expected = IllegalStateException.class)
  public void testGetScoreGameNotStarted() {
    model.getScore(Player.BLUE);
  }

  @Test
  public void testLegalCardTrue() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    Assert.assertTrue(model.legalPlay(0, 0));
  }

  @Test
  public void testLegalCardFalseCardAlreadyPlayed() {
    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    model.playToGrid(0, 0, 0);
    Assert.assertFalse(model.legalPlay(0, 0));
  }

  @Test
  public void testLegalCardFalseHole() {
    model.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());
    Assert.assertFalse(model.legalPlay(1, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testLegalCardGameNotStarted() {
    model.legalPlay(0, 0);
  }


//  @Test
//  public void testGetBoardGivenMovesStartOfGame() {
//    model.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
//            noHoles.getRowConfig());
//    model.playToGrid(0, 0, 0);
//    model.playToGrid(1, 0, 0);
//    model.playToGrid(2, 0, 0);
//    model.playToGrid(0, 1, 0);
//
//    Cell[][] cells = (Cell[][]) model.getPreviousMoves().get(0);
//    assertNull(cells[0][0].getCard());
//    assertNull(cells[0][1].getCard());
//    assertNull(cells[2][4].getCard());
//  }
//
//  @Test(expected = IllegalStateException.class)
//  public void testGetBoardGivenMovesGameNotStarted() {
//    model.getPreviousMoves();
//  }


}
