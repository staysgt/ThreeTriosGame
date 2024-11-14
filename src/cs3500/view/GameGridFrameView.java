package cs3500.view;

import javax.swing.JFrame;

/**
 * This is the class that allows the gui frame to be shown.
 */
public class GameGridFrameView extends JFrame implements FrameView {

  @Override
  public void showFrame() {
    setVisible(true);
  }

}

