package cs3500.model;

/**
 * A mock model class for the purpose of testing strategies.
 */
public class MockFlipModel<C extends Card> extends GameGridModel<C> {
  StringBuilder transcript = new StringBuilder();

  @Override
  public int cardsFlipped(int row, int col, int handIdx, Player player) {
    transcript.append("Row: " + row + " Col: " + col + " Handidx: " + handIdx + "\n");
    if (row == 0 && col == 0 && handIdx == 0) {
      return 100;
    } else {
      return 0;
    }
  }

  /**
   * Gets the transcript of the moves checked.
   *
   * @return the spaces checked.
   */
  public String getTranscript() {
    return transcript.toString();
  }

}
