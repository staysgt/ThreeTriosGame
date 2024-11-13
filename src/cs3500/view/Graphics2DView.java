package cs3500.view;

import cs3500.model.Card;
import cs3500.model.ReadOnlyGameGridModel;


import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import javax.swing.*;


public class Graphics2DView<C extends Card> extends FunGraphics implements Graphics2DInf {

  ReadOnlyGameGridModel<C> model;

  /**
   * Constructor for Graphics2DView.
   *
   * @param model model that the view is based off of.
   */
  public Graphics2DView(ReadOnlyGameGridModel<C> model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int numRows = model.getBoard().length;
    int numCols = model.getBoard()[0].length;
    int cellWidth = getWidth() / numCols;
    int cellHeight = getHeight() / numRows;

    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        int xPos = col * cellWidth;
        int yPos = row * cellHeight;
        fillRect(g2d, xPos, yPos, cellWidth, cellHeight, row, col);
      }
    }


  }

  @Override
  public Graphics create() {
    return super.create(0, 0, getWidth(), getHeight());
  }

  @Override
  public void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col) {
    int xMax = model.getBoard().length;
    int yMax = model.getBoard()[0].length;
    if (model.isCellHole(row, col)) {
      g2d.setColor(Color.GRAY);
    } else {
      g2d.setColor(Color.YELLOW);
    }

    g2d.fillRect(x, y, width, height);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, width, height);
  }

  @Override
  public void drawLines(Graphics2D g2d, int x, int y, int width, int height) {
    x = model.getBoard().length;
    y = model.getBoard()[0].length;

    g2d.setColor(Color.BLACK);
    for (int row = 0; row < x; row++) {
      int lineY = row * height;
      g2d.drawLine(0, lineY, x * width, lineY);
    }

    for (int col = 0; col < y; col++) {
      int lineX = col * width;
      g2d.drawLine(lineX, 0, lineX, y * height);
    }
  }

  protected void setFont(JLabel label, String name, int style, int size) {
    label.setFont(new Font(name, style, size));
  }


  public class CardPlacement extends Path2D.Double {

    private double width;
    private double height;

    public CardPlacement(double x, double y, double width, double height) {
      this.width = width;
      this.height = height;
      cardSize(x, y);
    }

    private void cardSize(double x, double y) {
      moveTo(x, y);
      lineTo(x + width, y);
      lineTo(x + width, y + height);
      lineTo(x, y + height);
    }

    public void render(Graphics2D g2d) {
      g2d.draw(this);
    }
  }

//    private AffineTransform getLogicalToPhysicalTransformation() {
//        AffineTransform transform = new AffineTransform();
//        Dimension logicalDims = getLogicalDims
//    }


//    private void drawGrid(Graphics2D g2d) {
//        AffineTransform modelToLogicalXForm
//
//
//    }

}
