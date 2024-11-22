package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;


import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.NESWCardFileReader;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.NESWCard;
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
    GameGrid<NESWCard> model = new GameGridModel<>(new Random(3));

    ConfigurationFileReader conFigFile = new ConfigurationFileReader("src" +
            File.separator + "walkableholes");
    NESWCardFileReader<NESWCard> cardFile = new NESWCardFileReader<>("src/cardsexample");

    model.startGame(cardFile.getCards(), conFigFile.getCols(),
            conFigFile.getRows(), conFigFile.getRowConfig());


    Graphics2DView view = new Graphics2DView(model);
    view.setVisible(true);


  }
}