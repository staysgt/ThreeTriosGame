package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

import cs3500.model.AttVal;
import cs3500.model.NESWCard;

/**
 * Tests for the NESWCardFileReader class.
 */
public class NESWCardFileReaderTests {
  private NESWCardFileReader cardFile;

  {
    try {
      cardFile = new NESWCardFileReader("src/cardsexample");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  public void testGetCardsFirstIdx() {
    NESWCard expected = new NESWCard("dog", intToAV(5), intToAV(9), intToAV(3), intToAV(2));
    Assert.assertEquals(expected, cardFile.getCards().get(0));
  }

  @Test
  public void testGetCardsLength() {
    Assert.assertEquals(20, cardFile.getCards().size());
  }

  @Test(expected = FileNotFoundException.class)
  public void testGetCardsInvalidFilepath() throws FileNotFoundException {
    NESWCardFileReader noFilePath = new NESWCardFileReader("blahblah");
  }

  @Test
  public void testGetCardsMiddleIdx() {
    NESWCard expected = new NESWCard("chicken", intToAV(3), intToAV(10), intToAV(10), intToAV(3));
    Assert.assertEquals(expected, cardFile.getCards().get(10));
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
