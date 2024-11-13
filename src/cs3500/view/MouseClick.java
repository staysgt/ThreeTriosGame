package cs3500.view;

import cs3500.model.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * This class implements the MouseListener java interface and allows
 * the user to click on the card and print it to the grid.
 *
 * @param <C> card
 */
public class MouseClick<C extends Card> implements MouseListener {

  private final Graphics2DView<C> view;
  private final ReadOnlyGameGridModel<C> model;
  private Player currentPlayer;
  private int cardIdx = -1;
  private boolean cardSelected = false;
  private JPanel highlightPanel;

  /**
   * This is the constructor for the MouseClick class.
   * @param model takes in the model
   * @param view takes in the view
   * @param startingPlayer lets the user know who is starting
   */
  public MouseClick(ReadOnlyGameGridModel<C> model, Graphics2DView<C> view, Player startingPlayer) {
    this.model = model;
    this.view = view;
    this.currentPlayer = startingPlayer;

    highlightPanel = new JPanel();
    highlightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    highlightPanel.setOpaque(false);
    view.add(highlightPanel);
    highlightPanel.setVisible(false);
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
        placeCard(row, col);
        System.out.println("The coordinates of the grid are " + x + "," + y);
      }
    }

    if (!cardSelected) {
      view.repaint();
    }

    view.repaint();
  }


  private void selectCard(int y, Player player) {
    List<C> hand = model.getHand(player);
    int numCards = hand.size();
    int cardWidth = view.getWidth() / 5;
    int cardHeight = view.getHeight() / numCards;
    int cardIndex = y / cardHeight;

    if (cardIndex >= 0 && cardIndex < numCards) {
      if (cardIdx == cardIndex && cardSelected) {
        cardIdx = -1;
        cardSelected = false;
        highlightPanel.setVisible(false);
      } else {
        cardIdx = cardIndex;
        cardSelected = true;
        C selectedCard = hand.get(cardIndex);
        System.out.println("The card is " + selectedCard.toString());

        int x;
        if (player == Player.RED) {
          x = 0;
        } else {
          x = view.getWidth() - cardWidth;
        }

        int yPos = cardIndex * cardHeight;

        highlightPanel.setBounds(x, yPos, cardWidth, cardHeight);
        highlightPanel.setVisible(true);
      }
    }
  }


  private void placeCard(int row, int col) {
    Cell<C> cell = model.getBoard()[row][col];

    if (cell.getCellState() == CellState.CARD_SPACE && cell.getCard() == null) {
      List<C> hand = model.getHand(currentPlayer);
      C selectedCard = hand.get(cardIdx);
      cell.setCard(selectedCard, currentPlayer);
      hand.remove(cardIdx);
      cardIdx = -1;

      if (currentPlayer == Player.BLUE) {
        currentPlayer = Player.RED;
      } else {
        currentPlayer = Player.BLUE;
      }

      System.out.println("It is now " + currentPlayer + "'s turn");
    }
  }


  @Override
  public void mousePressed(MouseEvent e) {}
  @Override
  public void mouseReleased(MouseEvent e) {}
  @Override
  public void mouseEntered(MouseEvent e) {}
  @Override
  public void mouseExited(MouseEvent e) {}
}
