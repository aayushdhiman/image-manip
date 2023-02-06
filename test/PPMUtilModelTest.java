import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import model.ImageUtilModel;
import model.ImageUtilModel.Grayscale;
import model.PPMUtilModel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * A JUnit test class for the {@code PPMUtilModel} class. Tests all functionality of the {@code
 * PPMUtilModel} class, as well as all exceptions thrown by it.
 */
public class PPMUtilModelTest {
  ImageUtilModel ppm;

  @Before
  public void init() {
    ppm = new PPMUtilModel();
  }

  @Test
  public void testGetHeight() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail("Unable to load b, got message " + e.getMessage());
    }

    assertEquals(36, this.ppm.getHeight("b"));
  }

  @Test
  public void testGetWidth() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail("Unable to load b, got message " + e.getMessage());
    }

    assertEquals(38, this.ppm.getWidth("b"));
  }

  @Test
  public void testGetMaxValue() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail("Unable to load b, got message " + e.getMessage());
    }

    assertEquals(255, this.ppm.getMaxValue("b"));
  }

  @Test
  public void testGetImage() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail("Unable to load b, got message " + e.getMessage());
    }


    try {
      this.ppm.loadImage("res/b.ppm", "bCopy");
    } catch (FileNotFoundException e) {
      fail("Unable to load bCopy, got message " + e.getMessage());
    }

    List<List<Color>> ogImg = this.ppm.getImage("b");
    List<List<Color>> newImg = this.ppm.getImage("bCopy");


    for (int i = 0; i < this.ppm.getHeight("b"); i++) {
      for (int j = 0; j < this.ppm.getWidth("b"); j++) {
        assertEquals(ogImg.get(i).get(j), newImg.get(i).get(j));
      }
    }

  }

  @Test
  public void testLoadImage() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail("Unable to load b, got message " + e.getMessage());
    }

    try {
      this.ppm.loadImage("src/b.ppm", "b");
      fail("Incorrectly loaded file that doesn't exist");
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testBrighten() {
    // sets pixel 0,0 to a specific color combination
    int[] expectedColors = new int[]{195, 165, 230};

    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }

    List<List<Color>> imagePixels = this.ppm.getImage("b");
    (imagePixels.get(0)).set(0, new Color(195, 165, 230));

    int red = (imagePixels.get(0).get(0)).getRed();
    int green = (imagePixels.get(0).get(0)).getGreen();
    int blue = (imagePixels.get(0).get(0)).getBlue();
    int[] actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);


    // brightens the original colors
    this.ppm.brighten(50, "b", "bBrighter");
    imagePixels = this.ppm.getImage("bBrighter");
    expectedColors = new int[]{245, 215, 255};

    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);


    // darkens the original colors
    this.ppm.brighten(-100, "b", "bDarker");
    imagePixels = this.ppm.getImage("bDarker");
    expectedColors = new int[]{145, 115, 155};

    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);


  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenException() {
    this.ppm.brighten(50, "b",
            "bBrighter");
  }

  @Test
  public void testRedGrayscale() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }

    List<List<Color>> imagePixels = this.ppm.getImage("b");
    (imagePixels.get(0)).set(0, new Color(195, 165, 230));
    int[] expectedColors = new int[]{195, 165, 230};

    int red = (imagePixels.get(0).get(0)).getRed();
    int green = (imagePixels.get(0).get(0)).getGreen();
    int blue = (imagePixels.get(0).get(0)).getBlue();
    int[] actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);

    this.ppm.grayscale(Grayscale.Red, "b", "Redb");
    imagePixels = this.ppm.getImage("Redb");
    expectedColors = new int[]{195, 195, 195};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testGrayscaleException() {
    this.ppm.grayscale(Grayscale.Red, "b",
            "bGrey");
  }

  @Test
  public void testGreenGrayscale() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }
    List<List<Color>> imagePixels = this.ppm.getImage("b");
    (imagePixels.get(0)).set(0, new Color(195, 165, 230));
    int[] expectedColors = new int[]{195, 165, 230};

    int red = (imagePixels.get(0).get(0)).getRed();
    int green = (imagePixels.get(0).get(0)).getGreen();
    int blue = (imagePixels.get(0).get(0)).getBlue();
    int[] actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);

    this.ppm.grayscale(Grayscale.Green, "b", "Greenb");
    imagePixels = this.ppm.getImage("Greenb");
    expectedColors = new int[]{165, 165, 165};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);

  }

  @Test
  public void testBlueGrayscale() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }
    List<List<Color>> imagePixels = this.ppm.getImage("b");
    (imagePixels.get(0)).set(0, new Color(195, 165, 230));
    int[] expectedColors = new int[]{195, 165, 230};

    int red = (imagePixels.get(0).get(0)).getRed();
    int green = (imagePixels.get(0).get(0)).getGreen();
    int blue = (imagePixels.get(0).get(0)).getBlue();
    int[] actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);

    this.ppm.grayscale(Grayscale.Blue, "b", "Blueb");
    imagePixels = this.ppm.getImage("Blueb");
    expectedColors = new int[]{230, 230, 230};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};

    assertArrayEquals(expectedColors, actualColors);

  }

  @Test
  public void testValueGrayscale() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }
    List<List<Color>> imagePixels = this.ppm.getImage("b");

    // when blue is the highest value
    (imagePixels.get(0)).set(0, new Color(195, 165, 230));
    int[] expectedColors = new int[]{195, 165, 230};
    int red = (imagePixels.get(0).get(0)).getRed();
    int green = (imagePixels.get(0).get(0)).getGreen();
    int blue = (imagePixels.get(0).get(0)).getBlue();
    int[] actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

    this.ppm.grayscale(Grayscale.Value, "b", "ValueBlueb");
    imagePixels = this.ppm.getImage("ValueBlueb");
    expectedColors = new int[]{230, 230, 230};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

    // when green is the highest value
    (imagePixels.get(0)).set(0, new Color(193, 240, 204));
    expectedColors = new int[]{193, 240, 204};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

    this.ppm.grayscale(Grayscale.Value,
            "b", "ValueGreenb");
    imagePixels = this.ppm.getImage("ValueGreenb");
    expectedColors = new int[]{240, 240, 240};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

    // when red is the highest value
    (imagePixels.get(0)).set(0, new Color(222, 135, 145));
    expectedColors = new int[]{222, 135, 145};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

    this.ppm.grayscale(Grayscale.Value, "b", "ValueRedb");
    imagePixels = this.ppm.getImage("ValueRedb");
    expectedColors = new int[]{222, 222, 222};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);
  }

  @Test
  public void testSaveImage() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }

    try {
      this.ppm.savePPMImage("bSaveTemp", "b");
    } catch (IOException e) {
      fail(e.getMessage());
    }

    try {
      this.ppm.loadImage("bSaveTemp.ppm", "Saveb");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }

    List<List<Color>> originalImage = this.ppm.getImage("b");
    List<List<Color>> savedImage = this.ppm.getImage("Saveb");

    for (int h = 0; h < this.ppm.getHeight("b"); h += 1) {
      for (int w = 0; w < this.ppm.getWidth("b"); w += 1) {
        assertEquals(originalImage.get(h).get(w), savedImage.get(h).get(w));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipException() {
    this.ppm.flipHorizontal("b",
            "bHorizontal");
  }

  @Test
  public void testFlipHorizontal() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }

    List<List<Color>> nonFlippedImage = this.ppm.getImage("b");
    this.ppm.flipHorizontal("b", "Horizontalb");
    List<List<Color>> flippedImage = this.ppm.getImage("Horizontalb");

    for (int h = 0; h < this.ppm.getHeight("b"); h += 1) {
      for (int w = 0; w < this.ppm.getWidth("b"); w += 1) {
        assertEquals(nonFlippedImage.get(h).get(w),
                flippedImage.get(h).get(this.ppm.getWidth("Horizontalb") - w - 1));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipException() {
    this.ppm.flipVertical("b",
            "bHorizontal");
  }

  @Test
  public void testFlipVertical() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }

    List<List<Color>> nonFlippedImage = this.ppm.getImage("b");
    this.ppm.flipVertical("b", "Verticalb");
    List<List<Color>> flippedImage = this.ppm.getImage("Verticalb");

    for (int h = 0; h < this.ppm.getHeight("b"); h += 1) {
      for (int w = 0; w < this.ppm.getWidth("b"); w += 1) {
        assertEquals(nonFlippedImage.get(h).get(w),
                flippedImage.get(this.ppm.getHeight("Verticalb") - h - 1).get(w));
      }
    }
  }

  @Test
  public void testIntensityGrayscale() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }
    List<List<Color>> imagePixels = this.ppm.getImage("b");
    (imagePixels.get(0)).set(0, new Color(195, 165, 230));
    int[] expectedColors = new int[]{195, 165, 230};
    int red = (imagePixels.get(0).get(0)).getRed();
    int green = (imagePixels.get(0).get(0)).getGreen();
    int blue = (imagePixels.get(0).get(0)).getBlue();
    int[] actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

    this.ppm.grayscale(Grayscale.Intensity,
            "b", "Intensityb");
    imagePixels = this.ppm.getImage("Intensityb");
    expectedColors = new int[]{196, 196, 196};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

  }

  @Test
  public void testLumaGrayscale() {
    try {
      this.ppm.loadImage("res/b.ppm", "b");
    } catch (FileNotFoundException e) {
      fail(e.getMessage());
    }
    List<List<Color>> imagePixels = this.ppm.getImage("b");
    (imagePixels.get(0)).set(0, new Color(195, 165, 230));
    int[] expectedColors = new int[]{195, 165, 230};
    int red = (imagePixels.get(0).get(0)).getRed();
    int green = (imagePixels.get(0).get(0)).getGreen();
    int blue = (imagePixels.get(0).get(0)).getBlue();
    int[] actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

    // 41.457 + 118.008 + 16.606 = 176.071
    this.ppm.grayscale(Grayscale.Luma, "b", "Lumab");
    imagePixels = this.ppm.getImage("Lumab");
    expectedColors = new int[]{176, 176, 176};
    red = (imagePixels.get(0).get(0)).getRed();
    green = (imagePixels.get(0).get(0)).getGreen();
    blue = (imagePixels.get(0).get(0)).getBlue();
    actualColors = new int[]{red, green, blue};
    assertArrayEquals(expectedColors, actualColors);

  }

}