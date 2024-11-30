package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;

import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.HumanPlayer;
import cs3500.controller.IPlayer;
import cs3500.controller.NESWCardFileReader;
import cs3500.controller.ThreeTriosController;
import cs3500.controller.strategy.FlipMostStrategy;
import cs3500.controller.strategy.ThreeTriosStrategy;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.OurModelToProviderAdapter;
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

    ConfigurationFileReader conFigFile = new ConfigurationFileReader("src"
            + File.separator + "walkableholes");
    NESWCardFileReader<Card> cardFile = new NESWCardFileReader<>("src/cardsexample");
    model.startGame(cardFile.getCards(), conFigFile.getCols(),
            conFigFile.getRows(), conFigFile.getRowConfig());


    model1.startGame(cardFile.getCards(), conFigFile.getCols(),
            conFigFile.getRows(), conFigFile.getRowConfig());


    Graphics2DView viewP1 = new Graphics2DView(model1);
    ThreeTriosGraphicalView viewP2 = new ThreeTriosGraphicalViewImplementation(model1, PlayerColor.BLUE);

//    ThreeTriosStrategy<Card> flipMost1 = new FlipMostStrategy<>();
//    ThreeTriosStrategy<Card> flipMost2 = new FlipMostStrategy<>();


    IPlayer<Card> player1 = new HumanPlayer<>(model);
    IPlayer<Card> player2 = new HumanPlayer<>(model);

//    ThreeTriosController<Card> controller1 = new ThreeTriosController<>(model1, player1, viewP1);
//    ThreeTriosController<Card> controller2 = new ThreeTriosController<>(model1, player2, viewP2);

    // this code would cause the game to be played if it was two machine players
    //    while (!model.isGameOver()) {
    //      controller1.listen();
    //      controller2.listen();
    //    }


  }
}