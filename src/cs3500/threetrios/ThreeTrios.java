package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;


import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.HumanPlayer;
import cs3500.controller.IPlayer;
import cs3500.controller.NESWCardFileReader;
import cs3500.controller.ThreeTriosController;
import cs3500.model.Card;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.NESWCard;
import cs3500.model.Player;
import cs3500.view.Graphics2DView;
import cs3500.view.MouseClick;

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
    GameGrid<Card> model = new GameGridModel<>(new Random(3));

    ConfigurationFileReader conFigFile = new ConfigurationFileReader("src" +
            File.separator + "walkableholes");
    NESWCardFileReader<Card> cardFile = new NESWCardFileReader<>("src/cardsexample");

    model.startGame(cardFile.getCards(), conFigFile.getCols(),
            conFigFile.getRows(), conFigFile.getRowConfig());

    Graphics2DView<Card> viewP1 = new Graphics2DView<>(model);
    Graphics2DView<Card> viewP2 = new Graphics2DView<>(model);

    IPlayer<Card> player1 = new HumanPlayer<>(Player.RED);
    IPlayer<Card> player2 = new HumanPlayer<>(Player.BLUE);

    ThreeTriosController<Card> controller1 = new ThreeTriosController<>(model, player1, viewP1);
    ThreeTriosController<Card> controller2 = new ThreeTriosController<>(model, player2, viewP2);




  }
}