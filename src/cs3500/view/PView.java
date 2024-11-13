package cs3500.view;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import cs3500.controller.ConfigurationFileReader;
import cs3500.controller.NESWCardFileReader;
import cs3500.model.GameGrid;
import cs3500.model.GameGridModel;
import cs3500.model.NESWCard;
import cs3500.model.Player;
import cs3500.model.ReadOnlyGameGridModel;


public final class PView {
  public static void main(String[] args) throws FileNotFoundException {

    GameGrid<NESWCard> model = new GameGridModel<>();

    ConfigurationFileReader conFigFile = new ConfigurationFileReader("src" +
            File.separator + "walkableholes");
    NESWCardFileReader<NESWCard> cardFile = new NESWCardFileReader<>("src/cardsexample");

    model.startGame(cardFile.getCards(), conFigFile.getCols(),
            conFigFile.getRows(), conFigFile.getRowConfig());

    model.playToGrid(0, 0, 0);
    model.playToGrid(0, 1, 0);
    model.playToGrid(0, 2, 2);



    Graphics2DView view = new Graphics2DView(model);


    JFrame frame = new JFrame("Three Trios");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocation(0, 0);
    frame.setPreferredSize(new Dimension(1200, 1200));

    frame.add(view);

    frame.pack();
    frame.setVisible(true);


  }

}
