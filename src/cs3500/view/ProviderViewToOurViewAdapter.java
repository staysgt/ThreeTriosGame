package cs3500.view;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.util.List;
import javax.swing.JPanel;

import cs3500.model.OTPCardAdapter;

import cs3500.threetrios.provider.model.CellType;
import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.provider.view.ThreeTriosGraphicalViewImplementation;

/**
 * Adapts the providers view into an instance of our view.
 *
 * @param <C> card variable.
 */
public class ProviderViewToOurViewAdapter<C extends OTPCardAdapter>
        extends ThreeTriosGraphicalViewImplementation implements Graphics2DInf {

  private ReadonlyThreeTriosModel<C> model;

  /**
   * Constructs a new ThreeTriosGraphicalView.
   *
   * @param model     the model being displayed by the view
   * @param playColor the player color the view is representing.
   */
  public ProviderViewToOurViewAdapter(ReadonlyThreeTriosModel<C> model, PlayerColor playColor) {
    super(model, playColor);
    this.model = model;

  }

  @Override
  public final void makeVisible() {
    this.setVisible(true);
    // all of this is done in the constructor in their model, so no need to override.
  }

  @Override
  public final void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                             boolean isHand) {
    if (!isHand) {
      // if the cell is a hole, sets teh cell to be a hole
      if (model.getCellTypeAt(col, row - 1) == CellType.HOLE) {
        g2d.setColor(new Color(59, 59, 58));
      } else {
        if (model.getCardAt(col, row - 1) != null) {
          Color c = model.getCellPlayerAt(col, row - 1) == PlayerColor.BLUE
                  ? new Color(119, 170, 252) : new Color(252, 119, 119);
          g2d.setColor(c);
        } else {
          g2d.setColor(new Color(250, 234, 90));
        }
      }
      fillRectAndBorder(g2d, x, y, width, height);
    } else {
      if (row == 0) {
        g2d.setColor(new Color(252, 119, 119));
      } else {
        g2d.setColor(new Color(119, 170, 252));
      }
      fillRectAndBorder(g2d, x, y, width, height);
    }


    if (isHand) {
      List<C> hand = (row == 0 ? model.getHand(PlayerColor.RED) : model.getHand(PlayerColor.BLUE));
      // row = the idx of the card in the hand
      if (row == 0 && col == model.getHand(PlayerColor.RED).size()) {
        // should not be added to the card
      } else {
        // sets the current card to be the selected card values
        C currCard = hand.get(col);
        PlayerColor currTurn = getCurrTurn();
        if (!model.getHand(currTurn).isEmpty()) {
          addCardNumbers(g2d, x, y, width, height, currCard);
        }
      }
    } else {
      // adds the card numbers to the board
      if (model.getCardAt(col, row - 1) != null) {
        C currCard = model.getCardAt(col, row - 1);
        addCardNumbers(g2d, x, y, width, height, currCard);
      }
    }

    // adds the card to the board
    if (!isHand && model.getCardAt(row, col - 1) != null) {
      C currCard = model.getCardAt(row, col - 1);
      addCardNumbers(g2d, x, y, width, height, currCard);
    }


    int numCols = model.getGridWidth();
    int cellWidth = getWidth() / (numCols + 2);

    int[] handCols = new int[]{0, getWidth() - cellWidth};


    for (int colm = 0; colm < handCols.length; colm++) {
      // rowHand = each row in this context is a card
      // gets for the blue player since it will always be the larger hand
      for (int rowCard = 0; rowCard < model.getHand(PlayerColor.BLUE).size(); rowCard++) {
        int cellHeightHand;
        if (colm == 0 && getCurrTurn() == PlayerColor.BLUE) {
          if (!model.getHand(PlayerColor.RED).isEmpty()) {
            cellHeightHand = getHeight() / (model.getHand(PlayerColor.RED).size());
          } else {
            cellHeightHand = getHeight();
          }

        } else {
          if (!model.getHand(PlayerColor.BLUE).isEmpty()) {
            cellHeightHand = getHeight() / (model.getHand(PlayerColor.BLUE).size());
          } else {
            cellHeightHand = getHeight();
          }
        }
        int xPos = handCols[colm];
        int yPos = rowCard * cellHeightHand;
        if (colm == 0 && model.getHand(PlayerColor.RED).size() == rowCard) {
          // for when red has less cards than blue
        } else {
          fillRect(g2d, xPos, yPos, cellWidth, cellHeightHand, rowCard, colm, true);
        }
      }
    }

  }

  private PlayerColor getCurrTurn() {
    int redSize = model.getHand(PlayerColor.RED).size();
    int blueSize = model.getHand(PlayerColor.BLUE).size();
    PlayerColor currTurn = redSize < blueSize ? PlayerColor.BLUE : PlayerColor.RED;
    return currTurn;
  }

  private void addCardNumbers(Graphics2D g2d, int x, int y, int width, int height,
                              C currCard) {
    int horizCenter = x + width / 2;
    int vertCenter = y + height / 2;

    int fontSize = width / 6;

    int offX = width / 12;
    int offY = height / 4;

    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
    g2d.setColor(Color.BLACK);
    g2d.drawString(currCard.getNorthOurs().toString(), horizCenter - offX / 2,
            vertCenter - offY / 2);
    g2d.drawString(currCard.getSouthOurs().toString(), horizCenter - offX / 2,
            vertCenter + offY);
    g2d.drawString(currCard.getWestOurs().toString(), horizCenter - (offX * 4),
            vertCenter + offY / 2);
    g2d.drawString(currCard.getEastOurs().toString(), horizCenter + (offX * 3),
            vertCenter + offY / 2);
  }

  private void fillRectAndBorder(Graphics2D g2d, int x, int y, int width, int height) {
    g2d.fillRect(x, y, width, height);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, width, height);
  }

  @Override
  public final void drawLines(Graphics2D g2d, int x, int y, int width, int height) {
    x = model.getGridHeight();
    y = model.getGridWidth();

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

}