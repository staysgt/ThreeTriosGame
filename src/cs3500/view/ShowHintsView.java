package cs3500.view;

import java.awt.Graphics2D;
import java.awt.Graphics;


import javax.swing.JPanel;

import cs3500.model.Card;

public class ShowHintsView<C extends Card> implements Graphics2DInf {

  private Graphics2DView<C> view;
  private boolean hintsVisible = false;

  public ShowHintsView(Graphics2DView<C> view, boolean hintsVisible) {
    this.view = view;
  }

  @Override
  public void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                       boolean isHand) {
    view.fillRect(g2d, x, y, width, height, row, col, isHand);
    if (hintsVisible) {
      // add cards flipped number (method in model) to the model left corner of the box based on
      // the card index
    }
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
