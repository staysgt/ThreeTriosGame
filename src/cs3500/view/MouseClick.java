package cs3500.view;

import cs3500.model.Card;
import cs3500.model.ReadOnlyGameGridModel;
import cs3500.model.Player;
import cs3500.model.Cell;
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
  private int selectedIdx;

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
    int x = e.getX();
    int y = e.getY();

    int numCols = model.getBoard()[0].length;
    int numRows = model.getBoard().length;

    int cellWidth = view.getWidth() / (numCols + 2);
    int cellHeight = view.getHeight() / numRows;

    if (x < cellWidth && currentPlayer == Player.RED) {
      selectCard(y, Player.RED);
    } else if (x > view.getWidth() - cellWidth && currentPlayer == Player.BLUE) {
      selectCard(y, Player.BLUE);
    } else if (cardIdx != -1) {
      int col = (x - cellWidth) / cellWidth;
      int row = y / cellHeight;

      if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
        if (!model.legalPlay(row, col)) {
          new PopUpWindow(currentPlayer, "This cell is already occupied!").setVisible(true);
        } else {
          selectedCol = col;
          selectedRow = row;
          placeCard(row, col);
          System.out.println("These are the coordinates: " + row + ", " + col);
        }
      } else {
        highlightPanel.setVisible(false);
        new PopUpWindow(currentPlayer, "Please select a card before placing it!").setVisible(true);
      }
      if (model.isGameOver()) {
        new PopUpWindow(currentPlayer, "Game is over!").setVisible(true);
      }

      view.repaint();
    }
  }

  private void selectCard(int y, Player player) {

    List<C> hand = model.getHand(player);
    int numCards = hand.size();

    int cardHeight = view.getHeight() / Math.max(numCards, 1);
    int cardIndex = y / cardHeight;

    if (cardIndex >= 0 && cardIndex < numCards) {
      cardIdx = cardIndex;
      C card = hand.get(cardIndex);
      selectedIdx = cardIndex;
      System.out.println("The card is " + card.toString());

      int x;
      if (player == Player.RED) {
        x = 0;
      } else {
        x = view.getWidth() - (view.getWidth() / 5);
      }

      int yPos = cardIndex * cardHeight;

      highlightPanel.setBounds(x, yPos, view.getWidth() / 5, cardHeight);
      highlightPanel.setVisible(true);
    } else {
      cardIdx = -1;
      highlightPanel.setVisible(false);
    }
  }

  private void placeCard(int row, int col) {
    Cell<C> cell = model.getBoard()[row][col];
    List<C> hand = model.getHand(currentPlayer);

    if (hand.size() == 1) {
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
    return selectedIdx;
  }


}