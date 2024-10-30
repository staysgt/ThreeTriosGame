package cs3500.view;

import cs3500.model.Card;

public interface TextView<C extends Card> {
  public void render();
}
