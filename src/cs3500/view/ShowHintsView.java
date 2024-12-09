package cs3500.view;

import java.awt.*;

import javax.swing.JPanel;


import cs3500.model.Card;
import cs3500.model.Player;
import cs3500.model.ReadOnlyGameGridModel;

public class ShowHintsView<C extends Card> implements Graphics2DInf {

  private final ReadOnlyGameGridModel<C> model;
  private final Graphics2DView<C> view;
  private final Player player;

  public ShowHintsView(Graphics2DView<C> view, ReadOnlyGameGridModel<C> model, Player player) {
    this.view = view;
    this.model = model;
    this.player = player;
  }


  private int getCardIndex(int row, int col, boolean isHand) {
    if (isHand) {
      return col;
    } else {
      return -1;
    }
  }

  @Override
  public void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                       boolean isHand) {
    view.fillRect(g2d, x, y, width, height, row, col, isHand);


    // this variable will get the card index that hte player has clicked on currently
    int cardIdx = 0;

    for (int rowIdx = 0; rowIdx < model.getBoard().length; rowIdx++) {
      for (int colIdx = 0; colIdx < model.getBoard()[0].length; colIdx++) {
        // only going to put the number of cards flipped on the grid for legal plays
        if (model.legalPlay(rowIdx, colIdx)) {
          // add this number ot the corner of each card
          int num = model.cardsFlipped(rowIdx, colIdx, cardIdx, player);
          // iterate through each card space and add the number of cards that the player could
          // flip with the given card
        }

      }
    }


    int redFlipped = model.cardsFlipped(row, col, 0, Player.RED);
    int blueFlipped = model.cardsFlipped(row, col, 0, Player.BLUE);

    g2d.setColor(Color.RED);
    g2d.drawString("R: " + redFlipped, x + 5, y + 15);

    g2d.setColor(Color.BLUE);
    g2d.drawString("B: " + blueFlipped, x + 5, y + 30);

    g2d.setColor(Color.BLACK);
    g2d.drawString("Index: " + cardIdx, x + 5, y + 45);
  }


  @Override
  public void drawLines(Graphics2D g2d, int x, int y, int width, int height) {
    view.drawLines(g2d, x, y, width, height);
  }

  @Override
  public Graphics create() {
    return view.create();
  }

  @Override
  public int getHeight() {
    return view.getHeight();
  }

  @Override
  public int getWidth() {
    return view.getWidth();
  }

  @Override
  public void add(JPanel highlightPanel) {
    view.add(highlightPanel);
  }

  @Override
  public void repaint() {
    view.repaint();
  }

  @Override
  public void setVisible(boolean b) {
    view.setVisible(b);
  }
}

