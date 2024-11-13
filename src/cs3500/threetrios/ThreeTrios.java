package cs3500.threetrios;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;


import javax.swing.JFrame;

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


    Graphics2DView<NESWCard> view = new Graphics2DView(model);
    MouseClick<NESWCard> mouseClickListener = new MouseClick<>(model, view, model.getTurn());
    view.addMouseListener(mouseClickListener);

    // current turn currently does not update because it is not altering the model thus not
    // accurately getting the turn. will be updated once a controller is implemented.
    JFrame frame = new JFrame("Current turn: " + model.getTurn());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocation(0, 0);
    frame.setPreferredSize(new Dimension(1200, 1200));
    frame.add(view);
    frame.pack();
    frame.setVisible(true);

  }
}