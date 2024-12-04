package cs3500.model;

import java.util.Objects;

/**
 * Our card to provider card adapter.
 * This class adapts our card into the provider's card.
 */
public class OTPCardAdapter extends NESWCard implements cs3500.threetrios.provider.model.Card {

  /**
   * Constructor for a NESW card.
   *
   * @param cardName name of the card
   * @param north    north value of the card.
   * @param south    south value of the card.
   * @param east     east value of the card.
   * @param west     west value of the card.
   * @throws IllegalArgumentException if given arguments are null.
   */
  public OTPCardAdapter(String cardName, AttVal north, AttVal south, AttVal east, AttVal west) {
    super(cardName, north, south, east, west);
  }

  public OTPCardAdapter(Card card) {
    super(card.getName(), card.getNorthOurs(), card.getSouthOurs(), card.getEastOurs(),
            card.getWestOurs());
  }

  @Override
  public String getName() {
    return super.getName();
  }

  @Override
  public String toString() {
    return super.toString();
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
    return Objects.equals(cm.getName(), super.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.getName());
  }

  @Override
  public int getEast() {
    return super.getEastOurs().getValue();
  }

  @Override
  public int getWest() {
    return super.getWestOurs().getValue();
  }

  @Override
  public int getNorth() {
    return super.getNorthOurs().getValue();
  }

  @Override
  public int getSouth() {
    return super.getSouthOurs().getValue();
  }
}
