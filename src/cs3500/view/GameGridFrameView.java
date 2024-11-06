package cs3500.view;

import javax.swing.*;
import java.awt.*;

public class GameGridFrameView extends JFrame implements FrameView {


  public GameGridFrameView(int width, int height) {
    JFrame frame = new JFrame("Three Trios");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.setMinimumSize(new Dimension(20, 20));
    frame.setPreferredSize(new Dimension(width, height));
    frame.pack();
  }


  @Override
  public void showFrame() {
    setVisible(true);
  }

}

