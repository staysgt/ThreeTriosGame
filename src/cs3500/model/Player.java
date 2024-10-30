package cs3500.model;

/**
 * Enum class representing the two players.
 */
public enum Player {
  RED,
  BLUE;

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }
}
