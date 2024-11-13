package cs3500.view;

import cs3500.controller.MiniController;
import cs3500.model.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class MouseClick<C extends Card> implements MouseListener {

  private final Graphics2DView<C> view;
  private final ReadOnlyGameGridModel<C> model;
  private Player currentPlayer;
  private int cardIdx = -1;
  private boolean cardSelected = false;


  public MouseClick(ReadOnlyGameGridModel<C> model, Graphics2DView<C> view, Player startingPlayer) {
    this.model = model;
    this.view = view;
    this.currentPlayer = startingPlayer;
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
      selectCardFromHand(y, Player.RED);
    } else if (x > view.getWidth() - cellWidth && currentPlayer == Player.BLUE) {
      selectCardFromHand(y, Player.BLUE);
    } else if (cardIdx != -1) {
      int col = (x - cellWidth) / cellWidth;
      int row = y / cellHeight;

      if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
        placeCard(row, col);
      }
    }

    if (currentPlayer == Player.BLUE && x < cellWidth) {
      playersCard(y);
    } else if (currentPlayer == Player.RED && x > view.getWidth() - cellWidth) {
      playersCard(y);
    } else if (cardIdx != -1) {
      int col = x / cellWidth - 1;
      int row = y / cellHeight;

      if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
        placeCard(row, col);
      }
    }

    if (!cardSelected) {
      view.repaint();
    }

    // view.revalidate();
    view.repaint();
  }

  private void playersCard(int y) {
    List<C> hand = model.getHand(currentPlayer);
    int height = view.getHeight() / hand.size();
    int cardIndex = y / height;

    if (cardIndex >= 0 && cardIndex < hand.size()) {
      cardIdx = cardIndex;
      C currCard = hand.get(cardIndex);
      System.out.println("The card is: " + currCard.toString());
    }
  }

  private void selectCardFromHand(int y, Player player) {
    List<C> hand = model.getHand(player);
    int cellHeightHand = view.getHeight() / hand.size();
    int cardIndex = y / cellHeightHand;

    if (cardIndex >= 0 && cardIndex < hand.size()) {
      cardIdx = cardIndex;
      C selectedCard = hand.get(cardIndex);
      System.out.println("Selected card: " + selectedCard.toString());
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

      currentPlayer = (currentPlayer == Player.BLUE) ? Player.RED : Player.BLUE;
      System.out.println("It is now " + currentPlayer + "'s turn");
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}
