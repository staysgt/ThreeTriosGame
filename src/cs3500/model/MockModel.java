package cs3500.model;

/**
 * A mock model class for the purpose of testing strategies.
 */
public class MockModel<C extends Card> extends GameGridModel<C> {

  @Override
  public int cardsFlipped(int row, int col, int handIdx, Player player) {
    if (row == 0 && col == 0) {
      return 100;
    } else {
      return 0;
    }

  }

}
