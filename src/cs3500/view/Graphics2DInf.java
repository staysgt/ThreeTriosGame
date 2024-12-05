package cs3500.view;

import java.awt.Graphics2D;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This is the interface for the 2D graphics class.
 */
public interface Graphics2DInf {

  /**
   * This allows the rectangle to be put on the grid.
   *
   * @param g2d    2D graphics
   * @param x      coordinate x
   * @param y      coordinate y
   * @param width  width of rectangle
   * @param height height of rectangle
   * @param row    rows
   * @param col    columns
   * @param isHand if it is in users hand
   */
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
   * @return the created graphics.
   */
  Graphics create();

  /**
   * Gets the height of the grid.
   *
   * @return height of the grid
   */

  int getHeight();


  /**
   * Gets the width of the grid.
   *
   * @return width of the grid.
   */
  int getWidth();

  /**
   * Add method for highlighting a panel.
   *
   * @param highlightPanel the panel being highlighted
   */
  void add(JPanel highlightPanel);


  /**
   * Repaints the grid.
   */
  void repaint();

  /**
   * Sets the view to be visible based on the provided boolean value.
   *
   * @param b whether the view should be set to be visible.
   */
  void setVisible(boolean b);
}
