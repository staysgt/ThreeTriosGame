public class NESWCard implements Card {

  private final String cardName;
  private final AttackValue north;
  private final AttackValue east;
  private final AttackValue west;
  private final AttackValue south;

  public NESWCard(String cardName, AttackValue north, AttackValue south, AttackValue east, AttackValue west) {
    this.cardName = cardName;
    this.north = north;
    this.east = east;
    this.west = west;
    this.south = south;
  }

  public enum AttackValue {
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
    AttackValue(int i) {
      this.value = i;
    }

    public int getValue() {
      return this.value;
    }
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public boolean equals(Object obj) {
    return false;
  }
}
