package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;


import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.HumanPlayer;
import cs3500.controller.IPlayer;
import cs3500.controller.MachinePlayer;
import cs3500.controller.OTPCardReader;
import cs3500.controller.ThreeTriosController;
import cs3500.controller.strategy.CardLessLikelyFlippedStrategy;
import cs3500.controller.strategy.CornersStrategy;
import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.MiniMaxStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.OurModelToProviderAdapter;
import cs3500.model.Player;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.view.Graphics2DInf;
import cs3500.view.Graphics2DView;
import cs3500.view.ProviderViewToOurViewAdapter;


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
    OurModelToProviderAdapter model1 = new OurModelToProviderAdapter<>();

    ConfigurationFileReader conFigFile = new ConfigurationFileReader("src" + File.separator
            + "walkableholes");
    OTPCardReader<Card> cardFile = new OTPCardReader<>(
            "src" + File.separator + "cardsexample");




    model1.startGame(cardFile.getCards(), conFigFile.getCols(),
            conFigFile.getRows(), conFigFile.getRowConfig());


    IPlayer<Card> player1 = null;
    IPlayer<Card> player2 = null;

    ArgsReader result = readArgs(args, player1, model1, player2);


    Graphics2DInf viewP1 = new Graphics2DView<>(model1);
    Graphics2DInf viewP2 = new ProviderViewToOurViewAdapter<>(model1, PlayerColor.BLUE);


    ThreeTriosController<Card> controller1 = new ThreeTriosController<>(model1,
            result.player1, viewP1);
    ThreeTriosController<Card> controller2 = new ThreeTriosController<>(model1,
            result.player2, viewP2);

    // this code would cause the game to be played if it was two machine players
    for (int turn = 0; turn < 5; turn++) {
      controller1.listen();
      controller2.listen();
    }


  }


  // helper to read in the arguments from the method
  private static ArgsReader readArgs(String[] args, IPlayer<Card> player1, GameGrid<Card> model,
                                     IPlayer<Card> player2) {
    int i = 0;
    while (i < args.length) {
      // for each
      switch (args[i]) {
        case "human":
          player1 = i == 0 ? new HumanPlayer<>(model) : player1;
          player2 = i == 1 ? new HumanPlayer<>(model) : player2;
          break;
        case "flipmost":
          player1 = i == 0 ? new MachinePlayer<>(model, new FlipMostStrategy<>(),
                  Player.RED) : player1;
          player2 = i == 1 ? new MachinePlayer<>(model, new FlipMostStrategy<>(),
                  Player.BLUE) : player2;
          break;
        case "corners":
          player1 = i == 0 ? new MachinePlayer<>(model, new CornersStrategy<>(),
                  Player.RED) : player1;
          player2 = i == 1 ? new MachinePlayer<>(model, new CornersStrategy<>(),
                  Player.BLUE) : player2;
          break;
        case "minimax":
          player1 = i == 0 ? new MachinePlayer<>(model, new MiniMaxStrategy<>(),
                  Player.RED) : player1;
          player2 = i == 1 ? new MachinePlayer<>(model, new MiniMaxStrategy<>(),
                  Player.BLUE) : player2;
          break;
        case "cardlesslikely":
          player1 = i == 0 ? new MachinePlayer<>(model, new CardLessLikelyFlippedStrategy<>(),
                  Player.RED) : player1;
          player2 = i == 1 ? new MachinePlayer<>(model, new CardLessLikelyFlippedStrategy<>(),
                  Player.BLUE) : player2;
          break;
        default:
          // defaults players to being human
          player1 = i == 0 ? new HumanPlayer<>(model) : player1;
          player2 = i == 1 ? new HumanPlayer<>(model) : player2;
      }
      i++;
    }
    ArgsReader result = new ArgsReader(player1, player2);
    return result;
  }

  // helper class for the readArgs method
  private static class ArgsReader {
    public final IPlayer<Card> player1;
    public final IPlayer<Card> player2;

    public ArgsReader(IPlayer<Card> player1, IPlayer<Card> player2) {
      this.player1 = player1;
      this.player2 = player2;
    }
  }


}