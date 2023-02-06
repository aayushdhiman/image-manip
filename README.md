# ImageProcessingPt1

## Model
The model has the implementations of the commands that the controller supports. Currently, these are load, save-ppm, save-png, red-component, green-component, blue-component, value-component, intensity-component, luma-component, horizontal-flip, vertical-flip, and brighten. 

### ImageUtilModel Interface
The purpose of this interface is to show which public methods are available to clients that are using this program. The public commands are the methods for each of the following commands, as well as getters for fields the client may need. The getters retrieve the values for the height, width, max value, and the image as a 2D ArrayList.
load adds the image to the Map imageReferences as a List of List of Colors. Each Color represents a pixel at that (height, width) value. 
save-ppm saves the image to the given filepath as a ppm file using FileWriter.
save-png saves the image as a PNG to the given filepath using BufferedImage and ImageIO.
red-component greyscales the image using the red component of the given image and stores it as the new reference name in the Map imageReferences.
green-component greyscales the image using the green component of the given image and stores it as the new reference name in the Map imageReferences.
blue-component greyscales the image using the blue component of the given image and stores it as the new reference name in the Map imageReferences.
value-component greyscales the image using the value component of the given image and stores it as the new reference name in the Map imageReferences.
intensity-component greyscales the image using the intensity component of the given image and stores it as the new reference name in the Map imageReferences.
luma-component greyscales the image using the luma component of the given image and stores it as the new reference name in the Map imageReferences.

## View
The view has the ability to write to the appendable that displays output to the user. The writeMessage method tests to see if the appendable is not null, and if it is not, it attempts to append the message to the appendable, and displays it to the user. 

## Controller
The controller parses the input from the user. It manages the input from the user and lets the model know what commands to run. Then, it uses the view to ouput messages based on the command and whether it successfully completed or not. It uses a switch statement to parse the input, as the name of the command is always the first input. Then, based on that, it runs the command using the inputs. It also catches the exceptions that are thrown from the commands using try/catch statements.

## Script
This script is saved as script.txt in this repo. Type read-script script.txt into the console to run this script.
```
load src/Koala.ppm koala
brighten 10 koala koala-brighter
vertical-flip koala koala-vertical
horizontal-flip koala-vertical koala-vertical-horizontal
value-component koala koala-value-greyscale
save-ppm src/koala-brighter koala-brighter
```

## Image Citation
b.ppm is taken from http://www.cs.uky.edu/~keen/EngageCSEdu/programs/ppm-disc.html#:~:text=To%20create%20a%20ppm%20file%20from%20some%20other%20format%20using,want%20for%20your%20result%20files. 

University of Kentucky. (n.d.). Small file for testing. CS at UKY. Retrieved June 8, 2022, from http://www.cs.uky.edu/~keen/EngageCSEdu/programs/ppm-disc.html#:~:text=To%20create%20a%20ppm%20file%20from%20some%20other%20format%20using,want%20for%20your%20result%20files. 
