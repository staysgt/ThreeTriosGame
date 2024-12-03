package cs3500.view;

import java.awt.*;

import javax.swing.*;

import cs3500.model.Card;
import cs3500.model.Player;
import cs3500.model.ReadOnlyGameGridModel;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.provider.view.Features;
import cs3500.threetrios.provider.view.ThreeTriosGraphicalViewImplementation;

public class ProviderViewToOurViewAdapter<C extends Card & cs3500.threetrios.provider.model.Card>
        extends ThreeTriosGraphicalViewImplementation
        implements Graphics2DInf {


  ReadOnlyGameGridModel<C> model;
  private cs3500.view.Features features;
  private MouseClick<C> cardSelected;
  private MouseClick<C> cellSelected;

  /**
   * Constructs a new ThreeTriosGraphicalView.
   *
   * @param model     the model being displayed by the view
   * @param playColor
   */
  public ProviderViewToOurViewAdapter(ReadonlyThreeTriosModel<?> model, PlayerColor playColor) {
    super(model, playColor);
  }

  @Override
  public void makeVisible() {
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
  public void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col, boolean isHand) {
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
  public void drawLines(Graphics2D g2d, int x, int y, int width, int height) {

  }

  @Override
  public Graphics create() {
    return null;
  }

  @Override
  public void add(JPanel highlightPanel) {

  }

}
