package cs3500.model;

/**
 * Enum values that represent the possible attack values for a card.
 */
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

  /**
   * Creates a string version of an attack value.
   *
   * @return string version of the attack value.
   */
  public String toString() {
    if (this.getValue() != 10) {
      return String.valueOf(this.getValue());
    } else {
      return "A";
    }
  }
}
