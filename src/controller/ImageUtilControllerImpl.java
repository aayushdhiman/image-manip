package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.ImageUtilModel;
import view.ImageUtilView;
import model.ImageUtilModel.Grayscale;


/**
 * A class to implement the controller for the {@code ImageUtil}. Tells the model when to execute
 * the commands, and tells the view what to display when a command either succeeds or fails.
 * Currently, supports loading an image, saving an image, creating grayscale with the red,
 * green, blue, value, intensity, and luma components, flipping an image horizontally or
 * vertically, brightening or darkening an image, and reading a script from a txt file.
 */
public class ImageUtilControllerImpl implements ImageUtilController {
  private final ImageUtilModel model;
  private final ImageUtilView view;
  private final Readable readable;

  /**
   * Creates a controller for the {@code ImageUtil} application. Handles input from the user and
   * delegates tasks to the model and view.
   *
   * @param model    the model that enforces the rules and completes tasks for the controller
   * @param view     the view that displays the output
   * @param readable the input from the user
   * @throws IllegalArgumentException if the model, view, or input is null
   */
  public ImageUtilControllerImpl(ImageUtilModel model, ImageUtilView view, Readable readable)
          throws IllegalArgumentException {
    if (model == null || view == null || readable == null) {
      throw new IllegalArgumentException("The model, view, nor the readable can be null.");
    }
    this.model = model;
    this.view = view;
    this.readable = readable;
  }

  /**
   * Turns the user input into an array using {@code .split( )}.
   *
   * @param scanner the Scanner object with the user input
   * @return the String array of inputs
   * @throws IllegalStateException if the {@code Scanner} runs out of inputs
   */
  private String[] getUserInput(Scanner scanner) throws IllegalStateException {
    try {
      view.writeMessage(System.lineSeparator() + "Enter command or \"q\" to quit: ");
      return scanner.nextLine().split(" ");
    } catch (IOException e) {
      System.out.println(e.getMessage());
      return scanner.nextLine().split(" ");
    } catch (NoSuchElementException e) {
      try {
        view.writeMessage("No more inputs in file.");
      } catch (IOException ex) {
        System.out.println(ex.getMessage());
      }
      throw new IllegalStateException("Out of inputs.");
    }
  }

  /**
   * Uses the view to print the welcome message with a list of all the commands the controller
   * can handle as well as how to use them properly.
   */
  private void printWelcomeMessage() {
    try {
      view.writeMessage("ImageUtil commands: " + System.lineSeparator());
      view.writeMessage("\"load filepath-on-disk reference-name\": Loads an image into the " +
              "editor. It will be referred to as \"reference-name\"." + System.lineSeparator());
      view.writeMessage("\"save-ppm filepath-on-disk reference-name\": Saves the image with the" +
              " name \"reference-name\" to your disk at the location \"filepath-on-disk\" as a " +
              "PPM file." + System.lineSeparator());
      view.writeMessage("\"save-png filepath-on-disk reference-name\": Saves the image with the" +
              " name \"reference-name\" to your disk at the location \"filepath-on-disk\" as a " +
              "PNG file." + System.lineSeparator());
      view.writeMessage("\"red-component reference-name new-reference-name\": Creates a " +
              "grayscale image with the red component of the image with the name " +
              "\"reference name\" and stores it with the name " +
              "\"new-reference-name\"." + System.lineSeparator());
      view.writeMessage("\"green-component reference-name new-reference-name\": Creates a " +
              "grayscale image with the green component of the image with the name " +
              "\"reference name\" and stores it with the name " +
              "\"new-reference-name\"." + System.lineSeparator());
      view.writeMessage("\"blue-component reference-name new-reference-name\": Creates a " +
              "grayscale image with the blue component of the image with the name " +
              "\"reference name\" and stores it with the name " +
              "\"new-reference-name\"." + System.lineSeparator());
      view.writeMessage("\"value-component reference-name new-reference-name\": Creates a " +
              "grayscale image with the value component of the image with the name " +
              "\"reference name\" and stores it with the name " +
              "\"new-reference-name\"." + System.lineSeparator());
      view.writeMessage("\"intensity-component reference-name new-reference-name\": Creates a " +
              "grayscale image with the intensity component of the image with the name " +
              "\"reference name\" and stores it with the name " +
              "\"new-reference-name\"." + System.lineSeparator());
      view.writeMessage("\"luma-component reference-name new-reference-name\": Creates a " +
              "grayscale image with the luma component of the image with the name " +
              "\"reference name\" and stores it with the name " +
              "\"new-reference-name\"." + System.lineSeparator());
      view.writeMessage("\"horizontal-flip reference-name new-reference-name\": Flips the " +
              "image with the name \"reference-name\" horizontally and stores it with the " +
              "name \"new-reference-name\"." + System.lineSeparator());
      view.writeMessage("\"vertical-flip reference-name new-reference-name\": Flips the " +
              "image with the name \"reference-name\" vertically and stores it with the " +
              "name \"new-reference-name\"." + System.lineSeparator());
      view.writeMessage("\"brighten increment reference-name new-reference-name\": " +
              "Brightens the image with the name \"reference-name\" by \"increment\" and " +
              "stores it with the name \"new-reference-name\". Negative increments will darken " +
              "the image." + System.lineSeparator());
      view.writeMessage("\"read-script path-to-script\": Reads the txt file provided and " +
              "runs the commands inside of it." + System.lineSeparator());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


  @Override
  public void startEditor() throws IllegalArgumentException {
    boolean quitEditor = false;
    Scanner sc = new Scanner(readable);
    this.printWelcomeMessage();

    while (!quitEditor) {
      boolean completedCommand = true;
      String[] input = null;
      try {
        input = this.getUserInput(sc);
      } catch (IllegalStateException e) {
        break;
      }
      try {
        switch (input[0]) {
          case "load":
            try {
              model.loadImage(input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (FileNotFoundException | IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Loaded " + input[1] + " as \"" + input[2] + "\"");
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "save-png":
            try {
              model.savePNGImage(input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IOException e) {
              try {
                completedCommand = false;
                view.writeMessage("The file you are trying to write to has errored.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Save successful");
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "save-ppm":
            try {
              model.savePPMImage(input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IOException e) {
              try {
                completedCommand = false;
                view.writeMessage("Filepath doesn't exist!");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Save successful");
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "red-component":
            try {
              model.grayscale(Grayscale.Red, input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Red component saved as " + input[2]);
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "blue-component":
            try {
              model.grayscale(Grayscale.Blue, input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Blue component saved as " + input[2]);
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "green-component":
            try {
              model.grayscale(Grayscale.Green, input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Green component saved as " + input[2]);
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "value-component":
            try {
              model.grayscale(Grayscale.Value, input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Value component saved as " + input[2]);
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "luma-component":
            try {
              model.grayscale(Grayscale.Luma, input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Luma component saved as " + input[2]);
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "intensity-component":
            try {
              model.grayscale(Grayscale.Intensity, input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Intensity component saved as " + input[2]);
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "horizontal-flip":
            try {
              model.flipHorizontal(input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Horizontal flip completed");
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "vertical-flip":
            try {
              model.flipVertical(input[1], input[2]);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Vertical flip completed");
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "brighten":
            try {
              model.brighten(Integer.parseInt(input[1]), input[2], input[3]);
            } catch (NumberFormatException e) {
              try {
                completedCommand = false;
                view.writeMessage("Increment is not a number.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalArgumentException e) {
              try {
                completedCommand = false;
                view.writeMessage("That image hasn't been loaded yet.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              try {
                completedCommand = false;
                view.writeMessage(e.getMessage());
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            }

            if (completedCommand) {
              try {
                view.writeMessage("Image brightened by " + input[1]);
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "read-script":
            try {
              File file = new File(input[1]);
              sc = new Scanner(file);
            } catch (IndexOutOfBoundsException e) {
              try {
                completedCommand = false;
                view.writeMessage("Not enough inputs.");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (FileNotFoundException e) {
              try {
                completedCommand = false;
                view.writeMessage("Filepath doesn't exist!");
              } catch (IOException ex) {
                System.out.println(ex.getMessage());
              }
            } catch (IllegalStateException e) {
              completedCommand = true;
              sc = new Scanner(readable);
            }

            if (completedCommand) {
              try {
                view.writeMessage("Script file loaded");
              } catch (IOException e) {
                System.out.println(e.getMessage());
              }
            }
            break;
          case "q":
            quitEditor = true;
            try {
              view.writeMessage("Quitting.");
            } catch (IOException e) {
              System.out.println(e.getMessage());
            }
            break;
          default:
            try {
              view.writeMessage("Invalid input, try again.");
            } catch (IOException e) {
              System.out.println(e.getMessage());
            }
            break;
        }
      } catch (IndexOutOfBoundsException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
