package cs3500.view;

import java.awt.*;

/**
 * This is the interface for the 2D graphics class.
 */
public interface Graphics2DInf {

  void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                boolean isHand);

  /**
   * This draws the lines on the grid.
   * @param g2d 2D graphics
   * @param x coordinate x
   * @param y coordinate y
   * @param width width of rectangle
   * @param height height or rectangle
   */
  void drawLines(Graphics2D g2d, int x, int y, int width, int height);

  /**
   * This is the graphics.
   * @return the create
   */
  Graphics create();
}
