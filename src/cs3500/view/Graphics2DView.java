package cs3500.view;

import cs3500.model.Card;
import cs3500.model.NESWCard;
import cs3500.model.Player;
import cs3500.model.ReadOnlyGameGridModel;


import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.Path2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;



/**
 * Creates the 2D graphics.
 */
public class Graphics2DView<C extends Card> extends FunGraphics implements Graphics2DInf {

  ReadOnlyGameGridModel<C> model;
  private Features features;
  private MouseClick<C> cardSelected;
  private MouseClick<C> cellSelected;
  /**
   * Constructor for Graphics2DView.
   *
   * @param model model that the view is based off of.
   */

  public Graphics2DView(ReadOnlyGameGridModel<C> model) {

    this.model = model;

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int numRows = model.getBoard().length;
    int numCols = model.getBoard()[0].length;
    int cellWidth = getWidth() / (numCols + 2);
    int cellHeight = getHeight() / (numRows);

    //int cellHeightHand = getHeight() / (model.getHand(Player.BLUE).size());

    for (int row = 0; row < numRows; row++) {
      for (int col = 1; col <= numCols; col++) {
        int xPos = col * cellWidth;
        int yPos = row * cellHeight;
        fillRect(g2d, xPos, yPos, cellWidth, cellHeight, row, col, false);
      }
    }
    int[] handCols = new int[]{0, getWidth() - cellWidth};

    for (int col = 0; col < handCols.length; col++) {
      // rowHand = each row in this context is a card
      // gets for the blue player since it will always be the larger hand
      for (int rowCard = 0; rowCard < model.getHand(Player.BLUE).size(); rowCard++) {
        int cellHeightHand;
        if (col == 0 && model.getHand(Player.RED).size() < model.getHand(Player.BLUE).size()) {
          cellHeightHand = getHeight() / (model.getHand(Player.RED).size());
        } else {
          cellHeightHand = getHeight() / (model.getHand(Player.BLUE).size());
        }
        int xPos = handCols[col];
        int yPos = rowCard * cellHeightHand;
        if (col == 0 && model.getHand(Player.RED).size() == rowCard) {
          // for when red has less cards than blue
        } else {
          fillRect(g2d, xPos, yPos, cellWidth, cellHeightHand, rowCard, col, true);

        }
      }
    }

  }


  @Override
  public Graphics create() {
    return super.create(0, 0, getWidth(), getHeight());
  }

  @Override
  public void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                       boolean isHand) {

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


    if (isHand) {
      List<NESWCard> hand = (List<NESWCard>)
              (col == 0 ? model.getHand(Player.RED) : model.getHand(Player.BLUE));
      // row = the idx of the card in the hand
      if (col == 0 && row == model.getHand(Player.RED).size()) {
        // should not be added to the card
      } else {
        NESWCard currCard = hand.get(row);
        addCardNumbers(g2d, x, y, width, height, currCard);
      }
    } else {
      if (model.getBoard()[row][col - 1].getCard() != null) {
        NESWCard currCard = (NESWCard) model.getBoard()[row][col - 1].getCard();
        addCardNumbers(g2d, x, y, width, height, currCard);
      }
    }

    if (!isHand && model.getBoard()[row][col - 1].getCard() != null) {
      NESWCard currCard = (NESWCard) model.getBoard()[row][col - 1].getCard();
      addCardNumbers(g2d, x, y, width, height, currCard);
    }

  }

  private void fillRectAndBorder(Graphics2D g2d, int x, int y, int width, int height) {
    g2d.fillRect(x, y, width, height);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, width, height);
  }

  private void addCardNumbers(Graphics2D g2d, int x, int y, int width, int height,
                              NESWCard currCard) {
    int horizCenter = x + width / 2;
    int vertCenter = y + height / 2;

    int fontSize = width / 6;

    int offX = width / 12;
    int offY = height / 4;

    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
    g2d.setColor(Color.BLACK);
    g2d.drawString(currCard.getNorth().toString(), horizCenter - offX / 2,
            vertCenter - offY / 2);
    g2d.drawString(currCard.getSouth().toString(), horizCenter - offX / 2,
            vertCenter + offY);
    g2d.drawString(currCard.getWest().toString(), horizCenter - (offX * 4),
            vertCenter + offY / 2);
    g2d.drawString(currCard.getEast().toString(), horizCenter + (offX * 3),
            vertCenter + offY / 2);
  }

  @Override
  public void drawLines(Graphics2D g2d, int x, int y, int width, int height) {
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

  protected void setFont(JLabel label, String name, int style, int size) {
    label.setFont(new Font(name, style, size));
  }


  /**
   * This class determines the cardPlacement.
   */
  public class CardPlacement extends Path2D.Double {

    private final double width;
    private final double height;
    private int cardIndex;

    /**
     * This is the constructor for card placement.
     *
     * @param x      coordinate of card
     * @param y      coordinate of card
     * @param width  width of card
     * @param height height of card
     */
    public CardPlacement(double x, double y, double width, double height) {
      this.width = width;
      this.height = height;
      this.cardIndex = cardIndex;
      cardSize(x, y);
    }

    private void cardSize(double x, double y) {
      moveTo(x, y);
      lineTo(x + width, y);
      lineTo(x + width, y + height);
      lineTo(x, y + height);
    }

    public void render(Graphics2D g2d) {
      g2d.draw(this);
    }

    /**
     * This gets the card index.
     *
     * @return the card index
     */
    public int getCardIndex() {
      return cardIndex;
    }

  }


  @Override
  public void setVisible(boolean b) {
    MouseClick<C> mouseClickListener = new MouseClick<>(model, this, model.getTurn());
    this.addMouseListener(mouseClickListener);

    JFrame frame = new JFrame("Three Trios");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocation(0, 0);
    frame.setPreferredSize(new Dimension(1200, 1200));

    frame.add(this);

    frame.pack();
    frame.setVisible(true);
  }


  /**
   * This incorporates the features interface and uses action listeners for it.
   *
   * @param features features class variable
   */
  public void addFeature(Features features) {
    JButton cellSelectedButton = new JButton("Click Me");
    JButton cardSelectedButton = new JButton("Click Me");


    cellSelectedButton.addActionListener(e ->
            features.cellSelected(cellSelected.getRow(),
                    cellSelected.getCol()));

    cardSelectedButton.addActionListener(e ->
            features.cardSelected(cardSelected.getIdx()));

  }

}

