import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.ImageUtilController;
import controller.ImageUtilControllerImpl;
import model.ImageUtilModel;
import model.PPMUtilModel;
import view.ImageUtilView;
import view.PPMUtilView;

import static org.junit.Assert.assertEquals;


/**
 * A JUnit test class for the {@code ImageUtilControllerImpl} class. Tests all functionality of
 * the {@code ImageUtilControllerImpl} class, as well as all exceptions thrown by it.
 */
public class ImageUtilControllerTest {

  private Appendable actualOutput;
  private ImageUtilModel model;
  private ImageUtilView view;
  private StringBuilder fakeUserInput;
  private StringBuilder expectedOutput;
  Readable input;

  @Before
  public void init() {
    this.actualOutput = new StringBuilder();
    this.model = new PPMUtilModel();
    this.view = new PPMUtilView(actualOutput);
    this.fakeUserInput = new StringBuilder();
    this.expectedOutput = new StringBuilder();
    this.input = new StringReader(fakeUserInput.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    ImageUtilController controller =
            new ImageUtilControllerImpl(null, this.view, this.input);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    ImageUtilController controller =
            new ImageUtilControllerImpl(this.model, null, this.input);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadable() {
    ImageUtilController controller =
            new ImageUtilControllerImpl(this.model, this.view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    this.view = new PPMUtilView(null);
  }

  @Test
  public void testRunningOutOfInput() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: No more inputs in file.")
    };
    this.runController(interactions, model, view);

    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testStartEditorQuit() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);

    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testLoadCommand() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testStartEditorSavePPM() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("save-ppm bSave b\n"),
        new PrintInteraction("Save successful"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testStartEditorSavePNG() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("save-png bSave b\n"),
        new PrintInteraction("Save successful"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testRedGrayscaleController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("red-component b bRed\n"),
        new PrintInteraction("Red component saved as bRed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testGreenGrayscaleController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("green-component b bRed\n"),
        new PrintInteraction("Green component saved as bRed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testBlueGrayscaleController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("blue-component b bRed\n"),
        new PrintInteraction("Blue component saved as bRed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testValueGrayscaleController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("value-component b bRed\n"),
        new PrintInteraction("Value component saved as bRed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testIntensityGrayscaleController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("intensity-component b bRed\n"),
        new PrintInteraction("Intensity component saved as bRed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testLumaGrayscaleController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("luma-component b bRed\n"),
        new PrintInteraction("Luma component saved as bRed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testFlipHorizontalController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("horizontal-flip b bH\n"),
        new PrintInteraction("Horizontal flip completed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testFlipVerticalController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("vertical-flip b bH\n"),
        new PrintInteraction("Vertical flip completed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testBrightenController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("brighten 50 b bBright\n"),
        new PrintInteraction("Image brightened by 50"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testDarkenController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("load res/b.ppm b\n"),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("brighten -50 b bDark\n"),
        new PrintInteraction("Image brightened by -50"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("q\n"),
        new PrintInteraction("Quitting.")
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testScriptReaderController() {
    Interaction[] interactions = new Interaction[]{
        new PrintInteraction("ImageUtil commands: " + System.lineSeparator()),
        new PrintInteraction("\"load filepath-on-disk reference-name\": " +
                "Loads an image into the editor. It will be referred to as " +
                "\"reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"save-ppm filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PPM file." + System.lineSeparator()),
        new PrintInteraction("\"save-png filepath-on-disk reference-name\": " +
                "Saves the image with the name \"reference-name\" to your disk at " +
                "the location \"filepath-on-disk\" as a PNG file." + System.lineSeparator()),
        new PrintInteraction("\"red-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the red component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"green-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the green component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"blue-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the blue component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"value-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the value component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"intensity-component reference-name " +
                "new-reference-name\": Creates a grayscale image with the intensity " +
                "component of the image with the name \"reference name\" and stores it" +
                " with the name \"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"luma-component reference-name new-reference-name\": " +
                "Creates a grayscale image with the luma component of the image " +
                "with the name \"reference name\" and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"horizontal-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "horizontally and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"vertical-flip reference-name " +
                "new-reference-name\": Flips the image with the name \"reference-name\" " +
                "vertically and stores it with the name " +
                "\"new-reference-name\"." + System.lineSeparator()),
        new PrintInteraction("\"brighten increment reference-name " +
                "new-reference-name\": Brightens the image with the name " +
                "\"reference-name\" by \"increment\" and stores it with the name " +
                "\"new-reference-name\". " +
                "Negative increments will darken the image." + System.lineSeparator()),
        new PrintInteraction("\"read-script path-to-script\": Reads the txt " +
                "file provided and runs the commands inside of it." + System.lineSeparator()),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new InputInteraction("read-script script.txt\n"),
        new PrintInteraction("Script file loaded"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new PrintInteraction("Loaded res/b.ppm as \"b\""),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new PrintInteraction("Image brightened by 10"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new PrintInteraction("Vertical flip completed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new PrintInteraction("Horizontal flip completed"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new PrintInteraction("Value component saved as b-value-greyscale"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new PrintInteraction("Save successful"),
        new PrintInteraction(System.lineSeparator() + "Enter command or " +
                "\"q\" to quit: "),
        new PrintInteraction("No more inputs in file."),
    };
    this.runController(interactions, model, view);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }
  
  private void runController(Interaction[] interactions, ImageUtilModel model,
                            ImageUtilView view) {
    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }
    this.input = new StringReader(fakeUserInput.toString());
    ImageUtilController controller = new ImageUtilControllerImpl(model, view, input);
    controller.startEditor();
  }

  /**
   * Represents an interaction that the user has with the controller. Allows one to test mock
   * user inputs with expected outputs.
   */
  interface Interaction {
    /**
     * Applies the operation of the interaction on either one of the StringBuilder params.
     *
     * @param in  the mock input.
     * @param out the output of the mock input.
     */
    void apply(StringBuilder in, StringBuilder out);
  }

  /**
   * Represents a mock interaction that the user has with the controller. Allows a tester to
   * fake a user input to see how the controller responds.
   */
  private class InputInteraction implements Interaction {
    private String line;

    /**
     * Creates a fake user input interaction.
     *
     * @param line the line to input to the controller.
     */
    InputInteraction(String line) {
      this.line = line;
    }

    @Override
    public void apply(StringBuilder in, StringBuilder out) {
      in.append(line);
    }
  }

  /**
   * Represents the output from a mocked interaction that the user has with the controller.
   * Allows a tester to observe the effect of a fake user input on the controller.
   */
  private class PrintInteraction implements Interaction {
    private String[] lines;

    /**
     * Creates a PrintInteraction to output the program's response.
     *
     * @param lines the lines that are outputted from the program.
     */
    public PrintInteraction(String... lines) {
      this.lines = lines;
    }

    @Override
    public void apply(StringBuilder in, StringBuilder out) {
      for (String line : lines) {
        out.append(line);
      }
    }
  }

}