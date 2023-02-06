package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Model implementation for PPM images. Supports performing commands such as load, save, and
 * others on PPM images that are read using {@code readPPM()}.
 */
public class PPMUtilModel implements ImageUtilModel {
  Map<String, List<List<Color>>> imageReferences;
  Map<String, Integer> maxValues;

  /**
   * Creates a {@code PPMUtilModel}. Initializes the fields {@code imageReferences} and {@code
   * maxValues} to be both be an empty {@code HashMap}
   */
  public PPMUtilModel() {
    this.imageReferences = new HashMap<>();
    this.maxValues = new HashMap<>();
  }

  @Override
  public int getHeight(String referenceName) {
    return this.imageReferences.get(referenceName).size();
  }

  @Override
  public int getWidth(String referenceName) {
    return this.imageReferences.get(referenceName).get(0).size();
  }

  @Override
  public int getMaxValue(String referenceName) {
    return this.maxValues.get(referenceName);
  }

  @Override
  public List<List<Color>> getImage(String referenceName) {
    return this.imageReferences.get(referenceName);
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  private List<List<Color>> readPPM(String filename, String destFilename)
          throws FileNotFoundException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();

    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    Integer maxValue = sc.nextInt();

    this.maxValues.put(destFilename, maxValue);

    List<List<Color>> imagePixels = new ArrayList<List<Color>>();

    for (int h = 0; h < height; h += 1) {
      imagePixels.add(new ArrayList<Color>());
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        Color pixel = new Color(red, green, blue);
        imagePixels.get(i).add(pixel);
      }
    }
    return imagePixels;
  }

  @Override
  public void loadImage(String filename, String destFilename) throws FileNotFoundException {
    List<List<Color>> imagePixels = this.readPPM(filename, destFilename);
    this.imageReferences.put(destFilename, imagePixels);
  }

  @Override
  public void savePNGImage(String filepath, String filenameReference) throws IOException {
    if (!this.imageReferences.containsKey(filenameReference)) {
      throw new IllegalArgumentException("Reference name has not been loaded yet.");
    }

    int height = this.imageReferences.get(filenameReference).size();
    int width = this.imageReferences.get(filenameReference).get(0).size();
    List<List<Color>> pixels = this.imageReferences.get(filenameReference);
    BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int h = 0; h < height; h += 1) {
      for (int w = 0; w < width; w += 1) {
        im.setRGB(w, h, pixels.get(h).get(w).getRGB());
      }
    }

    File output = new File(filepath + ".png");
    ImageIO.write(im, "png", output);

  }

  @Override
  public void savePPMImage(String filepath, String filenameReference) throws IOException {
    if (!this.imageReferences.containsKey(filenameReference)) {
      throw new IllegalArgumentException("Reference name has not been loaded yet.");
    }

    int height = this.getHeight(filenameReference);
    int width = this.getWidth(filenameReference);
    int maxVal = this.getMaxValue(filenameReference);

    List<List<Color>> saveImage = this.imageReferences.get(filenameReference);

    StringBuilder saveMe = new StringBuilder();
    // adds PPM file signature
    saveMe.append("P3\n");
    // formats integer height/width/maxVal into Strings and adds them
    saveMe.append(String.format("%d %d %d\n", width, height, maxVal));

    // formats the RGB values into integers and adds them
    for (int h = 0; h < height; h += 1) {
      for (int w = 0; w < width; w += 1) {
        saveMe.append(String.format("%d ", saveImage.get(h).get(w).getRed()));
        saveMe.append(String.format("%d ", saveImage.get(h).get(w).getGreen()));
        saveMe.append(String.format("%d ", saveImage.get(h).get(w).getBlue()));
      }
      saveMe.append("\n");
    }

    // writes to file
    FileWriter saver = new FileWriter(filepath + ".ppm");
    saver.write(saveMe.toString());
    saver.flush(); // saves file instantly (flushes cache)
    saver.close();
  }


  @Override
  public void grayscale(Grayscale g, String filename, String destFile) {
    if (!this.imageReferences.containsKey(filename)) {
      throw new IllegalArgumentException("Reference name has not been loaded yet.");
    }

    List<List<Color>> imagePixels = this.imageReferences.get(filename);
    for (List<Color> imagePixel : imagePixels) {
      for (int i = 0; i < imagePixel.size(); i += 1) {
        int colorValue;
        int redValue = imagePixel.get(i).getRed();
        int greenValue = imagePixel.get(i).getGreen();
        int blueValue = imagePixel.get(i).getBlue();
        switch (g) {
          case Red:
            colorValue = redValue;
            break;
          case Green:
            colorValue = greenValue;
            break;
          case Blue:
            colorValue = blueValue;
            break;
          case Value:
            if (redValue >= greenValue && redValue >= blueValue) {
              colorValue = redValue;
            } else if (greenValue >= redValue && greenValue >= blueValue) {
              colorValue = greenValue;
            } else {
              colorValue = blueValue;
            }
            break;
          case Intensity:
            colorValue = (redValue + greenValue + blueValue) / 3;
            break;
          case Luma:
            colorValue = (int) ((redValue * 0.2126) + (greenValue * 0.7152) + (blueValue * 0.0722));
            break;
          default:
            throw new IllegalArgumentException("Invalid component");
        }
        imagePixel.set(i, new Color(colorValue, colorValue, colorValue));
      }
    }
    this.imageReferences.put(destFile, imagePixels);
    this.maxValues.put(destFile, this.getMaxValue(filename));
  }


  @Override
  public void flipHorizontal(String filenameReference, String newReferenceName) {
    if (!this.imageReferences.containsKey(filenameReference)) {
      throw new IllegalArgumentException("Reference name has not been loaded yet.");
    }

    int height = this.imageReferences.get(filenameReference).size();
    int width = this.imageReferences.get(filenameReference).get(0).size();
    List<List<Color>> imagePixels = this.imageReferences.get(filenameReference);
    List<List<Color>> newImage = this.createEmptyImage(height);

    for (int h = 0; h < height; h += 1) {
      for (int w = width - 1; w >= 0; w -= 1) {
        newImage.get(h).add(imagePixels.get(h).get(w));
      }
    }
    this.imageReferences.put(newReferenceName, newImage);
    this.maxValues.put(newReferenceName, this.getMaxValue(filenameReference));
  }

  @Override
  public void flipVertical(String filenameReference, String newReferenceName) {
    if (!this.imageReferences.containsKey(filenameReference)) {
      throw new IllegalArgumentException("Reference name has not been loaded yet.");
    }

    int height = this.imageReferences.get(filenameReference).size();
    int width = this.imageReferences.get(filenameReference).get(0).size();
    List<List<Color>> imagePixels = this.getImage(filenameReference);
    List<List<Color>> newImage = this.createEmptyImage(height);

    int rowNum = 0;
    while (rowNum < height) {
      for (int h = height - 1; h >= 0; h -= 1) {
        for (int w = 0; w < width; w += 1) {
          newImage.get(rowNum).add(imagePixels.get(h).get(w));
        }
        rowNum += 1;
      }
    }
    this.imageReferences.put(newReferenceName, newImage);
    this.maxValues.put(newReferenceName, this.getMaxValue(filenameReference));
  }

  @Override
  public void brighten(int increment, String filenameReference, String newReferenceName)
          throws IllegalArgumentException {
    if (!this.imageReferences.containsKey(filenameReference)) {
      throw new IllegalArgumentException("Reference name has not been loaded yet.");
    }

    List<List<Color>> imagePixels = this.imageReferences.get(filenameReference);

    for (int h = 0; h < this.getHeight(filenameReference); h += 1) {
      for (int w = 0; w < this.getWidth(filenameReference); w += 1) {
        int redValue = rgbCap(imagePixels.get(h).get(w).getRed() + increment);
        int greenValue = rgbCap(imagePixels.get(h).get(w).getGreen() + increment);
        int blueValue = rgbCap(imagePixels.get(h).get(w).getBlue() + increment);

        imagePixels.get(h).set(w, new Color(redValue, greenValue, blueValue));
      }
    }
    this.imageReferences.put(newReferenceName, imagePixels);
    this.maxValues.put(newReferenceName, this.getMaxValue(filenameReference));
  }

  /**
   * Caps the RGB value to be between 0 and 255.
   *
   * @param value the value to cap between 0 and 255
   * @return the capped value
   */
  private int rgbCap(int value) {
    if (value > 255) {
      return 255;
    } else {
      return Math.max(value, 0);
    }
  }

  /**
   * Creates an empty image to populate with modified image.
   *
   * @param height the height of the image
   * @return the empty image
   */
  private List<List<Color>> createEmptyImage(int height) {
    List<List<Color>> newImage = new ArrayList<List<Color>>();

    for (int i = 0; i < height; i += 1) {
      newImage.add(new ArrayList<Color>());
    }
    return newImage;
  }
}
