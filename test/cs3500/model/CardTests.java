package cs3500.model;

import org.junit.Test;

import org.junit.Assert;


/**
 * Tests for the card class.
 */
public class CardTests {

  @Test
  public void testFunnyGetName() {
    NESWCard model = new NESWCard("LOLSOFUNNY", NESWCard.AttVal.THREE,
            NESWCard.AttVal.FOUR, NESWCard.AttVal.TWO, NESWCard.AttVal.ONE);
    Assert.assertEquals("LOLSOFUNNY", model.getName());
  }

  @Test (expected = IllegalArgumentException.class)
  public void tesGetValueNothing() {
    new NESWCard(null, NESWCard.AttVal.TWO, NESWCard.AttVal.FOUR,
            NESWCard.AttVal.TWO, NESWCard.AttVal.ONE);
  }

  @Test
  public void testToStringValue() {
    NESWCard card = new NESWCard("Card", NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR,
            NESWCard.AttVal.TWO, NESWCard.AttVal.ONE);
    String expected = "Card THREE FOUR TWO ONE";
    Assert.assertEquals(expected, card.toString());
  }

  @Test
  public void testToStringNothing() {
    NESWCard model = new NESWCard("", NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR,
            NESWCard.AttVal.TWO, NESWCard.AttVal.ONE);
    String expected = " THREE FOUR TWO ONE";
    Assert.assertEquals(expected, model.toString());
  }

  @Test
  public void testGetEastWithValidValue() {
    NESWCard card = new NESWCard("Card", NESWCard.AttVal.ONE, NESWCard.AttVal.TWO,
            NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR);
    Assert.assertEquals(NESWCard.AttVal.THREE, card.getEast());
  }

  @Test
  public void testGetWestWithValidValue() {
    NESWCard card = new NESWCard("Card", NESWCard.AttVal.ONE, NESWCard.AttVal.TWO,
            NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR);
    Assert.assertEquals(NESWCard.AttVal.FOUR, card.getWest());
  }

  @Test
  public void testGetNorthWithValidValue() {
    NESWCard card = new NESWCard("Card", NESWCard.AttVal.ONE, NESWCard.AttVal.TWO,
            NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR);
    Assert.assertEquals(NESWCard.AttVal.ONE, card.getNorth());
  }

  @Test
  public void testGetSouthWithValidValue() {
    NESWCard card = new NESWCard("Card", NESWCard.AttVal.ONE, NESWCard.AttVal.TWO,
            NESWCard.AttVal.THREE, NESWCard.AttVal.FOUR);
    Assert.assertEquals(NESWCard.AttVal.TWO, card.getSouth());
  }

  @Test
  public void testTwoCardsEqualsFalse() {
    NESWCard card1 = new NESWCard("Card 1", NESWCard.AttVal.A, NESWCard.AttVal.ONE,
            NESWCard.AttVal.TWO, NESWCard.AttVal.THREE);
    NESWCard card2 = new NESWCard("Card 2", NESWCard.AttVal.A, NESWCard.AttVal.ONE,
            NESWCard.AttVal.TWO, NESWCard.AttVal.THREE);
    Assert.assertFalse(card1.equals(card2));
  }

  @Test
  public void testCardsEqualsTrue() {
    NESWCard card = new NESWCard("Card", NESWCard.AttVal.A, NESWCard.AttVal.ONE,
            NESWCard.AttVal.TWO, NESWCard.AttVal.THREE);
    NESWCard card2 = new NESWCard("Card", NESWCard.AttVal.A, NESWCard.AttVal.ONE,
            NESWCard.AttVal.TWO, NESWCard.AttVal.THREE);
    Assert.assertTrue(card.equals(card2));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardTooBigValueThrowsException() {
    NESWCard card = new NESWCard("Card", intToAV(11),
            NESWCard.AttVal.TWO, NESWCard.AttVal.FIVE, NESWCard.AttVal.FOUR);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCardTooSmallValueThrowsException() {
    NESWCard card = new NESWCard("Card0", intToAV(-1),
            NESWCard.AttVal.TWO, NESWCard.AttVal.FIVE, NESWCard.AttVal.FOUR);
  }


  private NESWCard.AttVal intToAV(int num) {
    for (NESWCard.AttVal attackValue : NESWCard.AttVal.values()) {
      if (num == attackValue.getValue()) {
        return attackValue;
      }
    }
    throw new IllegalArgumentException("Provided num does not have an associated attack value");
  }



}
