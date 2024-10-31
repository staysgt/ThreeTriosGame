package cs3500.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.controller.CardFileReader;
import cs3500.model.Card;
import cs3500.model.NESWCard;

/**
 * This class reads in the provided card files and extracts,
 * the card name, and the NESW directions.
 */
public class NESWCardFileReader implements CardFileReader {
  private List<Card> cards = new ArrayList<>();

  /**
   * Constrcutor for a NESWCardFileReader.
   * @param path the path to the card file.
   * @throws FileNotFoundException if a file does not exist at the given path.
   */
  public NESWCardFileReader(String path) throws FileNotFoundException {
    Scanner scan = new Scanner(new File(path));
    int i = 1;
    while (scan.hasNextLine()) {
      String currLine = scan.nextLine();
      String[] split = currLine.split("\\s+");
      String name = split[0];
      // need to account for the fact that the provided value may be A
      NESWCard.AttVal n = split[1].equals("A") ? intToAV(10) : intToAV(Integer.parseInt(split[1]));
      NESWCard.AttVal s = split[2].equals("A") ? intToAV(10) : intToAV(Integer.parseInt(split[2]));
      NESWCard.AttVal e = split[3].equals("A") ? intToAV(10) : intToAV(Integer.parseInt(split[3]));
      NESWCard.AttVal w = split[4].equals("A") ? intToAV(10) : intToAV(Integer.parseInt(split[4]));
      this.cards.add(new NESWCard(name, n, s, e, w));

    }

  }

  // helper to associate int value to AttackValue
  private NESWCard.AttVal intToAV(int num) {
    for (NESWCard.AttVal attackValue : NESWCard.AttVal.values()) {
      if (num == attackValue.getValue()) {
        return attackValue;
      }
    }
    throw new IllegalArgumentException("Provided num does not have an associated attack value");
  }

  public List<Card> getCards() {
    return this.cards;
  }


}
