package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.HumanPlayer;
import cs3500.controller.IPlayer;
import cs3500.controller.MachinePlayer;
import cs3500.controller.NESWCardFileReader;
import cs3500.controller.ThreeTriosController;
import cs3500.controller.strategy.CardLessLikelyFlippedStrategy;
import cs3500.controller.strategy.CornersStrategy;
import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.MiniMaxStrategy;
import cs3500.controller.strategy.Strategies;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.OurModelToProviderAdapter;
import cs3500.model.Player;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.view.ThreeTriosGraphicalView;
import cs3500.threetrios.provider.view.ThreeTriosGraphicalViewImplementation;
import cs3500.view.Graphics2DView;

/**
 * Class for running the game.
 */
public final class ThreeTrios {

  /**
   * Main class that runs the game.
   *
   * @param args arguments taken in to start the game.
   */
  public static void main(String[] args) throws FileNotFoundException {

    GameGrid<Card> model = new GameGridModel<>();
    OurModelToProviderAdapter model1 = new OurModelToProviderAdapter<>();

    ConfigurationFileReader conFigFile = new ConfigurationFileReader("src" + File.separator
            + "walkableholes");
    NESWCardFileReader<Card> cardFile = new NESWCardFileReader<>("src/cardsexample");


    model.startGame(cardFile.getCards(), conFigFile.getCols(),
            conFigFile.getRows(), conFigFile.getRowConfig());


//    model1.startGame(cardFile.getCards(), conFigFile.getCols(),
//            conFigFile.getRows(), conFigFile.getRowConfig());

    IPlayer<Card> player1 = null;
    IPlayer<Card> player2 = null;

    int i = 0;
    while (i < args.length) {
      // for each
      switch (args[i]) {
        case "human":
          player1 = i == 0 ? new HumanPlayer<>(model) : player1;
          player2 = i == 1 ? new HumanPlayer<>(model) : player2;
          break;
        case "flipmost":
          player1 = i == 0 ? new MachinePlayer<>(model, new FlipMostStrategy<>(), Player.RED) : player1;
          player2 = i == 1 ? new MachinePlayer<>(model, new FlipMostStrategy<>(), Player.BLUE) : player2;
          break;
        case "corners":
          player1 = i == 0 ? new MachinePlayer<>(model, new CornersStrategy<>(), Player.RED) : player1;
          player2 = i == 1 ? new MachinePlayer<>(model, new CornersStrategy<>(), Player.BLUE) : player2;
          break;
        case "minimax":
          player1 = i == 0 ? new MachinePlayer<>(model, new MiniMaxStrategy<>(), Player.RED) : player1;
          player2 = i == 1 ? new MachinePlayer<>(model, new MiniMaxStrategy<>(), Player.BLUE) : player2;
          break;
        case "cardlesslikely":
          player1 = i == 0 ? new MachinePlayer<>(model, new CardLessLikelyFlippedStrategy<>(), Player.RED) : player1;
          player2 = i == 1 ? new MachinePlayer<>(model, new CardLessLikelyFlippedStrategy<>(), Player.BLUE) : player2;
          break;
      }
      i++;
    }


    Graphics2DView viewP1 = new Graphics2DView(model);
    Graphics2DView viewP2 = new Graphics2DView(model);

//    ThreeTriosGraphicalView viewP2 = new ThreeTriosGraphicalViewImplementation(model1, PlayerColor.BLUE);

    ThreeTriosStrategy<Card> flipMost1 = new FlipMostStrategy<>();
    ThreeTriosStrategy<Card> flipMost2 = new FlipMostStrategy<>();

//    player1 = new MachinePlayer<>(model, flipMost1, Player.RED);
//    player2 = new MachinePlayer<>(model, flipMost2, Player.BLUE);

//    IPlayer<Card> player1 = new HumanPlayer<>(model);
//    IPlayer<Card> player2 = new HumanPlayer<>(model);

    ThreeTriosController<Card> controller1 = new ThreeTriosController<>(model1, player1, viewP1);
    ThreeTriosController<Card> controller2 = new ThreeTriosController<>(model1, player2, viewP2);

    // this code would cause the game to be played if it was two machine players
    for (int turn = 0; turn < 5; turn++) {
      controller1.listen();
      controller2.listen();
    }


  }
}