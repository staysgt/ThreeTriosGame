package cs3500.view;


import cs3500.model.ReadOnlyGameGridModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MouseClick implements MouseListener {

  private List<Graphics2DView.CardPlacement> cardPlacements;
  private ReadOnlyGameGridModel<?> model;


  @Override
  public void mouseClicked(MouseEvent e) {
    handleMouseClick(e);
    this.cardPlacements = new ArrayList<>();

  }

  private void handleMouseClick(MouseEvent e) {
    double x = e.getX();
    double y = e.getY();
    for (Graphics2DView.CardPlacement placement : cardPlacements) {
      if (placement.contains(x, y)) {
        int cardIndex = placement.getCardIndex();
        System.out.println("Card clicked: " + cardIndex);
        break;
      }
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
