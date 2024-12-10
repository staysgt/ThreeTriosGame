package cs3500.view;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


import cs3500.model.Card;
import cs3500.model.Player;
import cs3500.model.ReadOnlyGameGridModel;

public class ShowHintsView<C extends Card> extends MouseClick<C> implements Graphics2DInf {

  private final ReadOnlyGameGridModel<C> model;
  private final Graphics2DView<C> view;
  private final Player player;

  public ShowHintsView(Graphics2DView<C> view, ReadOnlyGameGridModel<C> model, Player player) {
    super(model, view, model.getTurn());
    this.view = view;
    this.model = model;
    this.player = player;
  }

  @Override
  public void mouseClicked(MouseEvent e) {


    for (int rowIdx = 0; rowIdx < model.getBoard().length; rowIdx++) {
      for (int colIdx = 0; colIdx < model.getBoard()[0].length; colIdx++) {
        // only going to put the number of cards flipped on the grid for legal plays
        if (model.legalPlay(rowIdx, colIdx)) {
          // add this number ot the corner of each card
//          int num = model.cardsFlipped(rowIdx, colIdx, cardIdx, player);
          // iterate through each card space and add the number of cards that the player could
          // flip with the given card
        }

      }
    }
  }


  @Override
  public void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                       boolean isHand) {
    view.fillRect(g2d, x, y, width, height, row, col, isHand);
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

