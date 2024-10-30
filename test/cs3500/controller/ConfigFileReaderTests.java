package cs3500.controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the ConfigurationFileReader class.
 */
public class ConfigFileReaderTests {
  private ConfigurationFileReader conFigFile;
  private ConfigurationFileReader noHoles;

  {
    try {
      conFigFile = new ConfigurationFileReader("src" + File.separator + "walkableholes");
      noHoles = new ConfigurationFileReader("src/noholes");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testGetCols() {
    Assert.assertEquals(3,conFigFile.getCols());
  }

  @Test
  public void testGetRows() {
    Assert.assertEquals(6,conFigFile.getRows());
  }

  @Test
  public void testGetRowConfig() {
    List<String> expected = new ArrayList<>();
    expected.add("CCCCC");
    expected.add("CCCCC");
    expected.add("CCCCC");
    Assert.assertEquals(expected, noHoles.getRowConfig());
  }

  @Test (expected = FileNotFoundException.class)
  public void testInvalidFileName() throws FileNotFoundException {
    new ConfigurationFileReader("blah");
  }


}
