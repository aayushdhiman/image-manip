import java.io.InputStreamReader;

import controller.ImageUtilController;
import controller.ImageUtilControllerImpl;
import model.ImageUtilModel;
import model.PPMUtilModel;
import view.ImageUtilView;
import view.PPMUtilView;


/**
 * Class that has the {@code main} method for the {@code ImageUtil class.} Creates the
 * controller and runs it.
 */
public class ImageUtil {
  /**
   * Run file for {@code ImageUtil class.} Creates a {@code Readable} for the input, an
   * {@code Appendable} for the output, a model, a view, and a controller, and runs the controller.
   *
   * @param args the user input/command line arguments.
   */
  public static void main(String[] args) {
    Readable readable = new InputStreamReader(System.in);
    Appendable appendable = System.out;
    ImageUtilModel model = new PPMUtilModel();
    ImageUtilView view = new PPMUtilView(appendable);
    ImageUtilController controller = new ImageUtilControllerImpl(model, view, readable);
    controller.startEditor();
  }
}

