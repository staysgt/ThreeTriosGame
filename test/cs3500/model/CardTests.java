package cs3500.model;

import org.junit.Test;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.*;

public class CardTests {

  @Test
  public void testFunnyGetName() {
    NESWCard model = new NESWCard("LOLSOFUNNY", NESWCard.AttVal.THREE,
            NESWCard.AttVal.FOUR, NESWCard.AttVal.TWO, NESWCard.AttVal.ONE);
    assertEquals("LOLSOFUNNY", model.getName());

  }

  @Test
  public void tesGetValueNothing() {
    NESWCard model = new NESWCard(null, NESWCard.AttVal.TWO, NESWCard.AttVal.FOUR,
            NESWCard.AttVal.TWO, NESWCard.AttVal.ONE);
    assertNull(model.getName());

  }

  @Test
  public void testToStringValue() {
    NESWCard model = new NESWCard("Card", NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR,
            NESWCard.AttVal.TWO, NESWCard.AttVal.ONE);
    String expected = "Card THREE FOUR TWO ONE";
    assertEquals(expected, model.toString());
  }

  @Test
  public void testToStringNothing() {
    NESWCard model = new NESWCard("", NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR,
            NESWCard.AttVal.TWO, NESWCard.AttVal.ONE);
    String expected = "";
    assertEquals(expected, model.toString());
  }

  @Test
  public void testGetEastWithValidValue() {
    NESWCard model = new NESWCard("Card", NESWCard.AttVal.ONE, NESWCard.AttVal.TWO,
            NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR);
    assertEquals(NESWCard.AttVal.THREE, model.getEast());
  }

  @Test
  public void testGetWestWithValidValue() {
    NESWCard model = new NESWCard("Card", NESWCard.AttVal.ONE, NESWCard.AttVal.TWO,
            NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR);
    assertEquals(NESWCard.AttVal.FOUR, model.getWest());
  }

  @Test
  public void testGetNorthWithValidValue() {
    NESWCard model = new NESWCard("Card", NESWCard.AttVal.ONE, NESWCard.AttVal.TWO,
            NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR);
    assertEquals(NESWCard.AttVal.ONE, model.getNorth());
  }

  @Test
  public void testGetSouthWithValidValue() {
    NESWCard model = new NESWCard("Card", NESWCard.AttVal.ONE, NESWCard.AttVal.TWO,
            NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR);
    assertEquals(NESWCard.AttVal.TWO, model.getSouth());
  }

  @Test
  public void testTwoCardsEqualsFalse() {
    NESWCard card1 = new NESWCard("Card 1", NESWCard.AttVal.A, NESWCard.AttVal.ONE,
            NESWCard.AttVal.TWO, NESWCard.AttVal.THREE);
    NESWCard card2 = new NESWCard("Card 2", NESWCard.AttVal.A, NESWCard.AttVal.ONE,
            NESWCard.AttVal.TWO, NESWCard.AttVal.THREE);
    assertFalse(card1.equals(card2));
  }

  @Test
  public void testCardsEqualsTrue() {
    NESWCard card = new NESWCard("Card", NESWCard.AttVal.A, NESWCard.AttVal.ONE,
            NESWCard.AttVal.TWO, NESWCard.AttVal.THREE);
    assertTrue(card.equals(card));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardTooBigValueThrowsException() {
    NESWCard card = new NESWCard("Card11", NESWCard.AttVal.valueOf("11"),
            NESWCard.AttVal.TWO, NESWCard.AttVal.FIVE, NESWCard.AttVal.FOUR);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardTooSmallValueThrowsException() {
    NESWCard card = new NESWCard("Card0", NESWCard.AttVal.valueOf("0"),
            NESWCard.AttVal.TWO, NESWCard.AttVal.FIVE, NESWCard.AttVal.FOUR);
  }
}
