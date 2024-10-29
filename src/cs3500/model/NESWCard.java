package cs3500.model;

import java.util.Objects;

public class NESWCard implements Card {

  private final String cardName;
  private final AttVal north;
  private final AttVal east;
  private final AttVal west;
  private final AttVal south;

  public NESWCard(String cardName, AttVal north, AttVal south, AttVal east, AttVal west) {
    this.cardName = cardName;
    this.north = north;
    this.east = east;
    this.west = west;
    this.south = south;
  }

  public enum AttVal {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    A(10);

    private final int value;
    AttVal(int i) {
      this.value = i;
    }

    public int getValue() {
      return this.value;
    }
  }

  @Override
  public String getName() {
    return this.cardName;
  }

  @Override
  public String toString() {
    return null;
  }

  public AttVal getEast() {
    return east;
  }

  public AttVal getNorth() {
    return north;
  }

  public AttVal getWest() {
    return west;
  }

  public AttVal getSouth() {
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
}
