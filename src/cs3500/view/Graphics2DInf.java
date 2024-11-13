package cs3500.view;

import java.awt.*;

public interface Graphics2DInf {

  void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                boolean isHand);

  void drawLines(Graphics2D g2d, int x, int y, int width, int height);

  Graphics create();
}
