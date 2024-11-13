package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;

import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.NESWCardFileReader;
import cs3500.model.GameGridModel;
import cs3500.model.NESWCard;
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
  public static void main(String[] args) {
    ConfigurationFileReader conFigFile;
    NESWCardFileReader<NESWCard> cardFile;

    {
      try {
        conFigFile = new ConfigurationFileReader("src" + File.separator + "walkableholes");
        cardFile = new NESWCardFileReader("src/cardsexample");
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    GameGridModel<NESWCard> model = new GameGridModel<>();
    model.startGame(cardFile.getCards(), conFigFile.getRows(),
            conFigFile.getCols(), conFigFile.getRowConfig());
    Graphics2DView<NESWCard> view = new Graphics2DView<NESWCard>(model);
    view.setVisible(true);
  }
}
