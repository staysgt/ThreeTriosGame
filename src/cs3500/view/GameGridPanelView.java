package cs3500.view;

import java.awt.*;

import javax.swing.*;

public class GameGridPanelView implements PanelView {

  private JPanel panel;

  public GameGridPanelView(int width, int height) {

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setPreferredSize(new Dimension(width, height));
  }

  @Override
  public void showPanel() {
    panel.setVisible(true);
  }

}
