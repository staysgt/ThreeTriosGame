package cs3500.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads in the configuration files and extracts the number
 * of rows, cols and the row configuration.
 */
public class ConfigurationFileReader {
  private int cols;
  private int rows;
  private final List<String> rowConfig = new ArrayList<>();


  /**
   * Constructor for a ConfigurationFileReader.
   * @param filePath the filepath for the configuration file.
   * @throws FileNotFoundException if a file does not exist at the given filepath.
   */
  public ConfigurationFileReader(String filePath) throws FileNotFoundException {
    Scanner scan = new Scanner(new File(filePath));
    String firstLine = scan.nextLine();
    this.cols = Integer.parseInt(firstLine.split(" ")[1]);
    this.rows = Integer.parseInt(firstLine.split(" ")[0]);
    while (scan.hasNextLine()) {
      rowConfig.add(scan.nextLine());
    }
  }

  /**
   * Gets the number of columns in the configuration file.
   *
   * @return the number of columns in the config file.
   */
  public int getCols() {
    return this.cols;
  }

  /**
   * Gets the number of rows in the configuration file.
   * @return the number of rows in the config file.
   */
  public int getRows() {
    return this.rows;
  }

  /**
   * Gets the row configuration in the configuration file.
   * @return the row configuration of the config file.
   */
  public List<String> getRowConfig() {
    return this.rowConfig;
  }

}
