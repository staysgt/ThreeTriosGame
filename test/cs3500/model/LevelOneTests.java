package cs3500.model;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.NESWCardFileReader;
import cs3500.model.extracredit.levelone.FallenAce;
import cs3500.model.extracredit.levelone.Reverse;
import cs3500.view.GameGridTextView;

/**
 * Tests for levle one.
 *
 * @param <C> card.
 */
public class LevelOneTests<C extends Card> {
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

  private GameGrid model = new GameGridModel(new Random(3));


  @Test
  public void testReverseTwoMoves() {
    Reverse<C> reverse = new Reverse<>(model);
    reverse.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());

    reverse.playToGrid(0, 0, 0);
    reverse.playToGrid(0, 1, 0);

    Assert.assertEquals(Player.BLUE, model.getBoard()[0][0].getOwner());
  }

  @Test
  public void testFallenAce() {
    FallenAce<C> fallenAce = new FallenAce<>(model);
    fallenAce.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());

    fallenAce.playToGrid(0, 2, 4);
    fallenAce.playToGrid(1, 2, 0);

    Assert.assertEquals(Player.BLUE, model.getBoard()[0][2].getOwner());
  }

  @Test
  public void testFallenAceReverseCombinedNoSwitch() {
    Reverse<C> reverse = new Reverse<>(model);
    GameGridTextView gameGridTextView = new GameGridTextView(model);
    FallenAce<C> fallenAce = new FallenAce<>(reverse);

    fallenAce.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());

    for (int card = 0; card < cardFile.getCards().size(); card++) {
      System.out.println(cardFile.getCards().get(card));
    }
    gameGridTextView.render();


    fallenAce.playToGrid(0, 2, 4);
    fallenAce.playToGrid(1, 2, 0);

    Assert.assertEquals(Player.RED, model.getBoard()[0][2].getOwner());
  }

  @Test
  public void testFallenAceReverseCombinedSwitch() {
    FallenAce<C> fallenAce = new FallenAce<>(model);
    Reverse<C> reverse = new Reverse<>(fallenAce);

    reverse.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());

    GameGridTextView<C> gameGridTextView = new GameGridTextView<>(model);
    gameGridTextView.render();

    fallenAce.playToGrid(0, 2, 4);
    fallenAce.playToGrid(1, 2, 0);


    // should have been converted to
    Assert.assertEquals(Player.BLUE, model.getBoard()[0][2].getOwner());
  }

  @Test
  public void testFallenAceReverseOtherOrder() {
    Reverse<C> reverse = new Reverse<>(model);
    FallenAce<C> fallenAce = new FallenAce<>(reverse);


    fallenAce.startGame(cardFile.getCards(), conFigFile.getCols(), conFigFile.getRows(),
            conFigFile.getRowConfig());

    GameGridTextView<C> gameGridTextView = new GameGridTextView<>(model);
    gameGridTextView.render();

    fallenAce.playToGrid(0, 2, 4);
    fallenAce.playToGrid(1, 2, 0);


    // should have been converted to
    Assert.assertEquals(Player.BLUE, model.getBoard()[0][2].getOwner());
  }
}
