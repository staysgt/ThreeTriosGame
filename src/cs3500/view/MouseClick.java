package cs3500.view;

import cs3500.model.Card;
import cs3500.model.CellInterface;
import cs3500.model.ReadOnlyGameGridModel;
import cs3500.model.Player;
import cs3500.model.CellState;


import javax.swing.JPanel;
import javax.swing.BorderFactory;


import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * This class implements the MouseListener java interface and allows
 * the user to click on the card and print it to the grid.
 * @param <C> card
 */
public class MouseClick<C extends Card> implements MouseListener {

  private int selectedRow;
  private int selectedCol;

  private final Graphics2DInf view;
  private final ReadOnlyGameGridModel<C> model;
  private Player currentPlayer;
  private int cardIdx = -1;
  private final JPanel highlightPanel;


  /**
   * Constructor for MouseClick.
   *
   * @param model          the game model
   * @param view           the view component
   * @param startingPlayer the starting player
   */
  public MouseClick(ReadOnlyGameGridModel<C> model, Graphics2DInf view, Player startingPlayer) {
    this.model = model;
    this.view = view;
    this.currentPlayer = startingPlayer;

    highlightPanel = getJPanel(view);
  }


  private JPanel getJPanel(Graphics2DInf view) {
    final JPanel highlightPanel;
    highlightPanel = new JPanel();
    highlightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    highlightPanel.setOpaque(false);
    view.add(highlightPanel);
    highlightPanel.setVisible(false);
    return highlightPanel;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // the x and y area where the card was clicked
    int x = e.getX();
    int y = e.getY();

    // the number of columns and rows in the board
    int numCols = model.getBoard()[0].length;
    int numRows = model.getBoard().length;

    // the width and height of each cell
    int cellWidth = view.getWidth() / (numCols + 2);
    int cellHeight = view.getHeight() / numRows;


    Player currPlayer = currentPlayer == Player.RED ? Player.RED : Player.BLUE;
    // if the x is less than the cell width (meaning it is a card)
    if (x < cellWidth || x > view.getWidth() - cellWidth) {
      // runs the select card method.
      currCard(y, currPlayer);
    } else if (cardIdx != -1) {

      // if cardIdx is not equal to negative one, this means that a card has been selected.
      // converts the column and row into our gamegrid measurements
      int col = (x - cellWidth) / cellWidth;
      int row = y / cellHeight;


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
          // placeCard method is called on the row and column
          placeCard(row, col);

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

      // repaints the view to reflect these changes.
      view.repaint();
    }
  }

  // private method for displaying a popup message
  private void showPopup(String message) {
    new PopUpWindow(currentPlayer, message).setVisible(true);
  }

  private void currCard(int y, Player player) {
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
      // why is it set to view.getWidth() - (view.getWidth() / 5);
      int x = player == Player.RED ? 0 : view.getWidth() - (view.getWidth() / 5);

      int yPos = cardIndex * cardHeight;

      highlightPanel.setBounds(x, yPos, view.getWidth() / 5, cardHeight);
      highlightPanel.setVisible(true);
    }
    if (cardIndex >= 0 && cardIndex < numCards) {
      // if the card index is greater than zero and less thna the number of cards in the hand
      // cardIdx is updated to be the current index
      cardIdx = cardIndex;

      // the card is updated to be the current card
      C card = hand.get(cardIndex);
      // prints the current card to the console
      System.out.println("The card is " + card.toString());

      // variable for the x value of the card, if player is red 0
      // why is it set to view.getWidth() - (view.getWidth() / 5);
      int x = player == Player.RED ? 0 : view.getWidth() - (view.getWidth() / 5);

      int yPos = cardIndex * cardHeight;

      highlightPanel.setBounds(x, yPos, view.getWidth() / 5, cardHeight);
      highlightPanel.setVisible(true);
    }

  }


  private void placeCard(int row, int col) {
    CellInterface cell = model.getBoard()[row][col];
    List<C> hand = model.getHand(currentPlayer);

    if (model.getHand(Player.BLUE).size() == 1 && model.getHand(Player.RED).size() == 0) {
      throw new IllegalStateException("Can't place last card");
    }

    if (cell.getCellState() == null) {
      throw new IllegalArgumentException("Invalid cell");
    }

    if (cell.getCellState() == CellState.CARD_SPACE && cell.getCard() == null) {
      C selectedCard = hand.get(cardIdx);
      cell.setCard(selectedCard, currentPlayer);
      hand.remove(cardIdx);
      cardIdx = -1;

      if (currentPlayer == Player.BLUE) {
        currentPlayer = Player.RED;
      } else {
        currentPlayer = Player.BLUE;
      }
    }

    System.out.println("It is now " + currentPlayer + "'s turn");
  }


  @Override
  public void mousePressed(MouseEvent e) {
    // empty comment
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    //empty comment
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //empty comment
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //empty comment
  }

  /**
   * Gets current column selected.
   *
   * @return current column selected.
   */
  public int getCol() {
    return selectedCol;
  }

  /**
   * Gets current row selected.
   *
   * @return current row selected.
   */

  public int getRow() {
    return selectedRow;
  }

  /**
   * Gets current card selected.
   *
   * @return current card selected.
   */
  public int getIdx() {
    return cardIdx;
  }


}