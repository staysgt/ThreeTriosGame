package cs3500.controller;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.MiniMaxStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.Player;

public class MinimaxStrategyTests<C extends Card> {

  private final GameGrid<C> modelNH = new GameGridModel<C>(new Random(3));
  private ConfigurationFileReader conFigFile;
  private ConfigurationFileReader noHoles;
  private NESWCardFileReader<C> cardFile;
  private NESWCardFileReader<C> badCardFile;

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
  public void testMinimaxWithFlipMostBeingUsed() {
    // RED is using strategy
    // BLUE is trying to guess what strategy theyre using

    modelNH.startGame(cardFile.getCards(), noHoles.getCols(), noHoles.getRows(),
            noHoles.getRowConfig());

    FlipMostStrategy flipMostStrategy = new FlipMostStrategy<>();
    // R
    modelNH.playToGrid(0, 0, 0);
    modelNH.playToGrid(0, 1, 0);

    // R
    int[] flip2 = (int[]) flipMostStrategy.choosePosition(modelNH, Player.RED).getFirst();
    modelNH.playToGrid(flip2[0], flip2[1], flip2[2]);
    modelNH.playToGrid(1, 0, 0);

    // R
    int[] flip3 = (int[]) flipMostStrategy.choosePosition(modelNH, Player.RED).getFirst();
    modelNH.playToGrid(flip3[0], flip3[1], flip3[2]);


    MiniMaxStrategy minimax = new MiniMaxStrategy();

    minimax.choosePosition(modelNH, Player.BLUE);


  }
}
