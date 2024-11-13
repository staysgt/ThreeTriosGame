package cs3500.view;

import cs3500.model.Card;
import cs3500.model.Player;
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
    int cellWidth = getWidth() / (numCols + 2);
    int cellHeight = getHeight() / (numRows);
    int cellHeightHand = getHeight() / (model.getHand(Player.BLUE).size());

    for (int row = 0; row < numRows; row++) {
      for (int col = 1; col <= numCols; col++) {
        int xPos = col * cellWidth;
        int yPos = row * cellHeight;
        fillRect(g2d, xPos, yPos, cellWidth, cellHeight, row, col, false);
      }
    }
    int[] handCols = new int[]{0, getWidth() - cellWidth};


    for (int col = 0; col < handCols.length; col++) {
      // rowHand = each row in this context is a card
      // gets for the blue player since it will always be the larger hand
      for (int rowCard = 0; rowCard < model.getHand(Player.BLUE).size(); rowCard++) {
        int xPos = handCols[col];
        int yPos = rowCard * cellHeightHand;
        fillRect(g2d, xPos, yPos, cellWidth, cellHeightHand, rowCard, col, true);
      }
    }

  }


  @Override
  public Graphics create() {
    return super.create(0, 0, getWidth(), getHeight());
  }

  @Override
  public void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                       boolean isHand) {

    if (!isHand) {
      if (model.isCellHole(row, col - 1)) {
        g2d.setColor(Color.GRAY);
      } else {
        g2d.setColor(Color.YELLOW);
      }

    } else {
      if (col == 0) {
        g2d.setColor(Color.RED);
      } else {
        g2d.setColor(Color.BLUE);
      }


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


  private double width;
  private double height;
  private int cardIndex;

  public class CardPlacement extends Path2D.Double {

    private double width;
    private double height;
    private int cardIndex;

    public CardPlacement(double x, double y, double width, double height) {
      this.width = width;
      this.height = height;
      this.cardIndex = cardIndex;
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

    public int getCardIndex() {
      return cardIndex;
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
