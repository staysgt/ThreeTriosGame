package cs3500.view;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Graphics2D;


import cs3500.model.Card;
import cs3500.model.Player;
import cs3500.model.ReadOnlyGameGridModel;

/**
 * This is the class that shows the hints of the cards for the rules.
 *
 * @param <C> card
 */
public class ShowHintsView<C extends Card> extends MouseClick<C> implements Graphics2DInf {

  private final ReadOnlyGameGridModel<C> model;
  private final Graphics2DInf view;
  private final Player player;
  private int cardIdx = -1;
  private Graphics graphics;

  /**
   * This is the constructor for the show hints class.
   * @param view view
   * @param model model
   * @param player player
   */
  public ShowHintsView(Graphics2DInf view, ReadOnlyGameGridModel<C> model, Player player) {
    super(model, view, model.getTurn());
    this.view = view;
    this.model = model;
    this.player = player;
  }

  @Override
  public void mouseClicked(MouseEvent e) {

    System.out.println("mouse clicked");


    // the x and y area where the card was clicked
    int x = e.getX();
    int y = e.getY();

    // the number of columns and rows in the board
    int numCols = model.getBoard()[0].length;
    int numRows = model.getBoard().length;

    // the width and height of each cell
    int cellWidth = view.getWidth() / (numCols + 2);
    int cellHeight = view.getHeight() / numRows;


    Player currPlayer = player == Player.RED ? Player.RED : Player.BLUE;
    // if the x is less than the cell width (meaning it is a card)
    if (x < cellWidth || x > view.getWidth() - cellWidth) {
      // runs the select card method.
      currCard(y, x, currPlayer);
      showHints();
    } else if (cardIdx != -1) {
      // if cardIdx is not equal to negative one, this means that a card has been selected.
      // converts the selected column and row into our GameGrid measurements
      int col = (x - cellWidth) / cellWidth;
      int row = y / cellHeight;

      confirmMove(row, numRows, col, numCols);

      // repaints the view to reflect these changes.
      view.repaint();
    }


  }

  private void showHints() {
    for (int rowIdx = 0; rowIdx < model.getBoard().length; rowIdx++) {
      for (int colIdx = 0; colIdx < model.getBoard()[0].length; colIdx++) {
        // only going to put the number of cards flipped on the grid for legal plays
        if (model.legalPlay(rowIdx, colIdx)) {
          // add this number ot the corner of each card
          int num = model.cardsFlipped(rowIdx, colIdx, cardIdx, player);
          int cellWidth = view.getWidth() / (model.getBoard()[0].length + 2);
          int cellHeight = view.getHeight() / (model.getBoard().length);

          int x = cellWidth * (colIdx + 1);
          int y = cellHeight * (rowIdx + 1);

          int fontSize = cellWidth / 6;

          graphics.setFont(new Font("Arial", Font.BOLD, fontSize));
          graphics.setColor(Color.BLACK);
          graphics.drawString(num + "", x, y);

          // iterate through each card space and add the number of cards that the player could
          // flip with the given card
        }

      }
    }
  }

  protected void setFont(JLabel label, String name, int style, int size) {
    label.setFont(new Font(name, style, size));
  }

  private void confirmMove(int row, int numRows, int col, int numCols) {
    // this means that the user clicked a cell that is on the grid
    if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
      // if the player selects a cell that is already occupied, it sends a message to the user
      if (!model.legalPlay(row, col)) {
        showPopup("This cell is already occupied!");
      } else {
        // the selected column/row are updated to reflect the most recently selected col/row
        selectedCol = col;
        selectedRow = row;

        // FIX THIS
        // AT THIS POINT THE CONTROLLER SHOULD BE NOTIFIED OF A CHANGE


        // the selected row and column are outputted
        System.out.println("These are the coordinates: " + row + ", " + col);
      }
    } else {
      // if the user did not select a card
      showPopup("Please select a card before placing it!");
    }

    // displays a popup if the move ended the game
    if (model.isGameOver()) {
      showPopup("Game is over!");
    }

    // un-highlights the previously selected card.
    highlightPanel.setVisible(false);
  }

  // private method for displaying a popup message
  private void showPopup(String message) {
    new PopUpWindow(player, message).setVisible(true);
  }

  private void currCard(int y, int x, Player player) {
    // gets the hand of the player and the number of cards in the hand
    List<C> hand = model.getHand(player);
    int numCards = hand.size();

    // gets the height of the card and divides it by either 1 or the number of cards
    // will divide by 1 when the hand is empty, makes sure not dividing by 0
    int cardHeight = view.getHeight() / Math.max(numCards, 1);
    // the index of the card in the players hand.
    int cardIndex = y / cardHeight;


    if (cardIndex >= 0 && cardIndex < numCards) {
      // if the card index is greater than zero and less thna the number of cards in the hand
      // cardIdx is updated to be the current index
      cardIdx = cardIndex;

      // the card is updated to be the current card
      C card = hand.get(cardIndex);
      // prints the current card to the console
      System.out.println("The card is " + card.toString());

      // variable for the x value of the card, if player is red 0
      int cardWidth = view.getWidth() / (model.getBoard()[0].length + 2);

      int xPos = x < cardWidth ? 0 : view.getWidth() - cardWidth;
      int yPos = cardIndex * cardHeight;

      highlightPanel.setBounds(xPos, yPos, view.getWidth() / (model.getBoard()[0].length + 2)
              , cardHeight);
      highlightPanel.setVisible(true);
    }
  }


  @Override
  public void fillRect(Graphics2D g2d, int x, int y, int width, int height, int row, int col,
                       boolean isHand) {
    this.graphics = g2d;
    view.fillRect(g2d, x, y, width, height, row, col, isHand);
  }


  @Override
  public void drawLines(Graphics2D g2d, int x, int y, int width, int height) {
    view.drawLines(g2d, x, y, width, height);
  }

  @Override
  public Graphics create() {
    return view.create();
  }

  @Override
  public int getHeight() {
    return view.getHeight();
  }

  @Override
  public int getWidth() {
    return view.getWidth();
  }

  @Override
  public void add(JPanel highlightPanel) {
    view.add(highlightPanel);
  }

  @Override
  public void repaint() {
    view.repaint();
  }

  @Override
  public void setVisible(boolean b) {
    view.setVisible(b);
  }
}

