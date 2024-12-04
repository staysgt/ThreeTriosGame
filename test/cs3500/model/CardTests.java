package cs3500.model;

import org.junit.Test;

import org.junit.Assert;


/**
 * Tests for the card class.
 */
public class CardTests {

  @Test
  public void testFunnyGetName() {
    NESWCard model = new NESWCard("LOLSOFUNNY", AttVal.THREE,
            AttVal.FOUR, AttVal.TWO, AttVal.ONE);
    Assert.assertEquals("LOLSOFUNNY", model.getName());
  }

  @Test (expected = IllegalArgumentException.class)
  public void tesGetValueNothing() {
    new NESWCard(null, AttVal.TWO, AttVal.FOUR,
            AttVal.TWO, AttVal.ONE);
  }

  @Test
  public void testToStringValue() {
    NESWCard card = new NESWCard("Card", AttVal.THREE, AttVal.FOUR,
            AttVal.TWO, AttVal.ONE);
    String expected = "Card THREE FOUR TWO ONE";
    Assert.assertEquals(expected, card.toString());
  }

  @Test
  public void testToStringNothing() {
    NESWCard model = new NESWCard("", AttVal.THREE, AttVal.FOUR,
            AttVal.TWO, AttVal.ONE);
    String expected = " THREE FOUR TWO ONE";
    Assert.assertEquals(expected, model.toString());
  }

  @Test
  public void testGetEastWithValidValue() {
    NESWCard card = new NESWCard("Card", AttVal.ONE, AttVal.TWO,
            AttVal.THREE, AttVal.FOUR);
    Assert.assertEquals(AttVal.THREE, card.getEastOurs());
  }

  @Test
  public void testGetWestWithValidValue() {
    NESWCard card = new NESWCard("Card", AttVal.ONE, AttVal.TWO,
            AttVal.THREE, AttVal.FOUR);
    Assert.assertEquals(AttVal.FOUR, card.getWestOurs());
  }

  @Test
  public void testGetNorthWithValidValue() {
    NESWCard card = new NESWCard("Card", AttVal.ONE, AttVal.TWO,
            AttVal.THREE, AttVal.FOUR);
    Assert.assertEquals(AttVal.ONE, card.getNorthOurs());
  }

  @Test
  public void testGetSouthWithValidValue() {
    NESWCard card = new NESWCard("Card", AttVal.ONE, AttVal.TWO,
            AttVal.THREE, AttVal.FOUR);
    Assert.assertEquals(AttVal.TWO, card.getSouthOurs());
  }

  @Test
  public void testTwoCardsEqualsFalse() {
    NESWCard card1 = new NESWCard("Card 1", AttVal.A, AttVal.ONE,
            AttVal.TWO, AttVal.THREE);
    NESWCard card2 = new NESWCard("Card 2", AttVal.A, AttVal.ONE,
            AttVal.TWO, AttVal.THREE);
    Assert.assertFalse(card1.equals(card2));
  }

  @Test
  public void testCardsEqualsTrue() {
    NESWCard card = new NESWCard("Card", AttVal.A, AttVal.ONE,
            AttVal.TWO, AttVal.THREE);
    NESWCard card2 = new NESWCard("Card", AttVal.A, AttVal.ONE,
            AttVal.TWO, AttVal.THREE);
    Assert.assertTrue(card.equals(card2));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardTooBigValueThrowsException() {
    NESWCard card = new NESWCard("Card", intToAV(11),
            AttVal.TWO, AttVal.FIVE, AttVal.FOUR);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testCardTooSmallValueThrowsException() {
    NESWCard card = new NESWCard("Card0", intToAV(-1),
            AttVal.TWO, AttVal.FIVE, AttVal.FOUR);
  }


  private AttVal intToAV(int num) {
    for (AttVal attackValue : AttVal.values()) {
      if (num == attackValue.getValue()) {
        return attackValue;
      }
    }
    throw new IllegalArgumentException("Provided num does not have an associated attack value");
  }



}
