package cs3500.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.model.AttVal;
import cs3500.model.Card;
import cs3500.model.OTPCardAdapter;

/**
 * This class reads in the provided card files and extracts,
 * the card name, and the NESW directions.
 */
public class OTPCardReader<C extends Card> implements CardFileReader {
  private final List<OTPCardAdapter> cards = new ArrayList<>();

  /**
   * Constrcutor for a NESWCardFileReader.
   *
   * @param path the path to the card file.
   * @throws FileNotFoundException if a file does not exist at the given path.
   */
  public OTPCardReader(String path) throws FileNotFoundException {
    Scanner scan = new Scanner(new File(path));
    int i = 1;
    while (scan.hasNextLine()) {
      String currLine = scan.nextLine();
      String[] split = currLine.split("\\s+");
      String name = split[0];
      // need to account for the fact that the provided value may be A
      AttVal n = split[1].equals("A") ? intToAV(10) : intToAV(Integer.parseInt(split[1]));
      AttVal s = split[2].equals("A") ? intToAV(10) : intToAV(Integer.parseInt(split[2]));
      AttVal e = split[3].equals("A") ? intToAV(10) : intToAV(Integer.parseInt(split[3]));
      AttVal w = split[4].equals("A") ? intToAV(10) : intToAV(Integer.parseInt(split[4]));
      this.cards.add(new OTPCardAdapter(name, n, s, e, w));

    }

  }

  // helper to associate int value to AttackValue
  private AttVal intToAV(int num) {
    for (AttVal attackValue : AttVal.values()) {
      if (num == attackValue.getValue()) {
        return attackValue;
      }
    }
    throw new IllegalArgumentException("Provided num does not have an associated attack value");
  }

  public List<OTPCardAdapter> getCards() {
    return this.cards;
  }


}
