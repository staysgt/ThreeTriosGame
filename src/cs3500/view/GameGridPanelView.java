package cs3500.view;

import java.awt.*;

import javax.swing.*;

public class GameGridPanelView extends JFrame implements PanelView {

  private JPanel panel;

  public GameGridPanelView(int width, int height) {

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setMinimumSize(new Dimension(20, 20)); // Sets minimum boundary
    panel.setPreferredSize(new Dimension(width, height));
  }


  @Override
  public void showPanel() {
    panel.setVisible(true);
  }
}


