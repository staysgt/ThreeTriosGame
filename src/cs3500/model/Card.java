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


  /**
   * Overrides hashCode method to ensure two equal cards have same hashcode value.
   * @return hashcode of given card.
   */
  int hashCode();


  /**
   * Gets the east value on a card.
   *
   * @return east value of a card.
   */
  AttVal getEastOurs();

  /**
   * Gets the west value on a card.
   *
   * @return west value of a card.
   */

  AttVal getWestOurs();

  /**
   * Gets the north value on a card.
   *
   * @return north value of a card.
   */
  AttVal getNorthOurs();


  /**
   * Gets the south value on a card.
   *
   * @return south value of a card.
   */
  AttVal getSouthOurs();


}
