package cs3500.view;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;


import cs3500.model.Card;
import cs3500.model.Player;
import cs3500.model.ReadOnlyGameGridModel;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.provider.view.HandPanel;
import cs3500.threetrios.provider.view.ThreeTriosGraphicalViewImplementation;

public class ProviderViewToOurViewAdapter<C extends Card & cs3500.threetrios.provider.model.Card>
        extends ThreeTriosGraphicalViewImplementation implements Graphics2DInf {



  ReadOnlyGameGridModel<C> model;
  private HandPanel redHandPanel;
  private HandPanel blueHandPanel;

  /**
   * Constructs a new ThreeTriosGraphicalView.
   *
   * @param model     the model being displayed by the view
   * @param playColor
   */
  public ProviderViewToOurViewAdapter(ReadonlyThreeTriosModel<?> model, PlayerColor playColor, HandPanel redHandPanel, HandPanel blueHandPanel) {
    super(model, playColor);
    this.redHandPanel = redHandPanel;
    this.blueHandPanel = blueHandPanel;

  }

  @Override
  public final void makeVisible() {
    MouseClick<C> mouseClickListener = new MouseClick<>(this.model, this, this.model.getTurn());
    this.addMouseListener(mouseClickListener);

    JFrame frame = new JFrame("Three Trios");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocation(0, 0);
    frame.setPreferredSize(new Dimension(1200, 1200));

    frame.add(this);

    frame.pack();
    frame.setVisible(true);

  }

  @Override
  public final void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col, boolean isHand) {
    if (!isHand) {
      if (model.isCellHole(row, col - 1)) {
        g2d.setColor(new Color(59, 59, 58));
      } else {
        if (model.getBoard()[row][col - 1].getCard() != null) {
          Color c = model.getBoard()[row][col - 1].getOwner() == Player.BLUE
                  ? new Color(119, 170, 252) : new Color(252, 119, 119);
          g2d.setColor(c);
        } else {
          g2d.setColor(new Color(250, 234, 90));
        }
      }
      fillRectAndBorder(g2d, x, y, width, height);
    } else {
      if (col == 0) {
        g2d.setColor(new Color(252, 119, 119));
      } else {
        g2d.setColor(new Color(119, 170, 252));
      }
      fillRectAndBorder(g2d, x, y, width, height);
    }
  }

  private void fillRectAndBorder(Graphics2D g2d, int x, int y, int width, int height) {
    g2d.fillRect(x, y, width, height);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, width, height);
  }

  @Override
  public final void drawLines(Graphics2D g2d, int x, int y, int width, int height) {
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

  @Override
  public Graphics create() {
    return create(getX(), getY(), getWidth(), getHeight());
  }

  protected Graphics create(int x, int y, int width, int height) {
    return create(x, y, width, height);

  }

  @Override
  public final void add(JPanel highlightPanel) {
    super.add(highlightPanel);
  }


  @Override
  public final void resetSelection(PlayerColor player) {
    if (player.equals(PlayerColor.RED)) {
      redHandPanel.resetSelection();
    } else {
      blueHandPanel.resetSelection();
    }
  }
}
