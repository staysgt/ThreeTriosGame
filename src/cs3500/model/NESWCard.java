package cs3500.model;

import java.util.Objects;

/**
 * Representation of a NESW card in three trios.
 */
public class NESWCard implements Card {

  private final String cardName;
  private final AttVal north;
  private final AttVal east;
  private final AttVal west;
  private final AttVal south;

  /**
   * Constructor for a NESW card.
   * @param cardName name of the card
   * @param north north value of the card.
   * @param south south value of the card.
   * @param east east value of the card.
   * @param west west value of the card.
   * @throws IllegalArgumentException if given arguments are null.
   */
  public NESWCard(String cardName, AttVal north, AttVal south, AttVal east, AttVal west) {
    if (cardName == null || north == null || south == null || east == null || west == null) {
      throw new IllegalArgumentException("Given values cannot be null.");
    }
    this.cardName = cardName;
    this.north = north;
    this.east = east;
    this.west = west;
    this.south = south;
  }

  @Override
  public String getName() {
    return this.cardName;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.cardName);
    sb.append(" " + north);
    sb.append(" " + south);
    sb.append(" " + east);
    sb.append(" " + west);
    return sb.toString();
  }


  @Override
  public AttVal getEastOurs() {
    return east;
  }

  @Override
  public AttVal getNorthOurs() {
    return north;
  }

  @Override
  public AttVal getWestOurs() {
    return west;
  }

  @Override
  public AttVal getSouthOurs() {
    return south;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof NESWCard)) {
      return false;
    }

    NESWCard cm = (NESWCard) obj;
    return Objects.equals(cm.getName(), this.cardName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardName);
  }


}
