package cs3500.model;

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
  final private String filePath;
  private int cols;
  private int rows;
  private final List<String> rowConfig = new ArrayList<>();


  public ConfigurationFileReader(String filePath) throws FileNotFoundException {
    Scanner scan = new Scanner(new File(filePath));
    this.filePath = filePath;
    String firstLine = scan.nextLine();
    this.cols = Integer.parseInt(firstLine.split(" ")[0]);
    this.rows = Integer.parseInt(firstLine.split(" ")[1]);
    while(scan.hasNextLine()) {
      rowConfig.add(scan.nextLine());
    }
  }

  public int getCols() {
    return this.cols;
  }

  public int getRows() {
    return this.rows;
  }

  public List<String> getRowConfig() {
    return this.rowConfig;
  }

  public static void main(String[] args) throws FileNotFoundException {
    ConfigurationFileReader cn = new ConfigurationFileReader(("src" + File.separator + "walkableholes"));

  }

}
