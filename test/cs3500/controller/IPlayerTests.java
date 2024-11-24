package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import cs3500.controller.strategy.CornersStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.Player;

/**
 * Testing class for the IPlayer interface and its subclasses.
 *
 * @param <C> generic type for card.
 */

public class IPlayerTests<C extends Card> {

  private final GameGrid<C> modelNH = new GameGridModel<C>(new Random(3));
  private final ConfigurationFileReader noHoles;
  private final NESWCardFileReader<C> cardFile;

  {
    try {
      ConfigurationFileReader conFigFile =
              new ConfigurationFileReader("src" + File.separator + "walkableholes");
      cardFile = new NESWCardFileReader("src/cardsexample");
      NESWCardFileReader<C> badCardFile =
              new NESWCardFileReader("src/notenoughcards");
      noHoles = new ConfigurationFileReader("src/noholes");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testHumanMakeMoveValid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    IPlayer<C> player = new HumanPlayer<>(modelNH);
    player.makeMove(0, 0, 0);
    Assert.assertEquals(Player.RED, modelNH.getBoard()[0][0].getOwner());
  }

  @Test
  public void testMachineMakeMoveValid() {
    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());
    ThreeTriosStrategy<C> corner = new CornersStrategy<>();

    IPlayer<C> player = new MachinePlayer<>(modelNH, corner, Player.RED);
    player.makeMove();
    Assert.assertEquals(Player.RED, modelNH.getBoard()[0][0].getOwner());
  }


}