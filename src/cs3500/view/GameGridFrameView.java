package cs3500.view;

import javax.swing.*;
import java.awt.*;

public class GameGridFrameView implements FrameView {

  private JFrame frame;

  public GameGridFrameView(int width, int height) {
    JFrame frame = new JFrame("Three Trios");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.setPreferredSize(new Dimension(width, height));
    frame.pack();
  }


  @Override
  public void showFrame() {
    frame.setVisible(true);
  }

}

