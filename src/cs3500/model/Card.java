package cs3500.model;

/**
 * Interface for a card in a ThreeTriosGame.
 */
public interface Card {


  /**
   * Gets the name of the card.
   * @return the name of a card.
   */
  String getName();

  /**
   * Convert the card into string format.
   * @return string version of the card.
   */
  String toString();

  /**
   * Overrides equals to compare two cards to one another.
   * @param obj object that the card is being compared to.
   * @return whether the two cards are equal.
   */
  boolean equals(Object obj);

}
