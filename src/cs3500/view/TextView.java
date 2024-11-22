package cs3500.view;

import cs3500.model.Card;

/**
 * Interface for a TextView.
 * @param <C> card.
 */
public interface TextView<C extends Card> {

  /**
   * This renders the text view.
   */
  void render();
}
