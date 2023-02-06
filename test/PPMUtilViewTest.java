import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.ImageUtilView;
import view.PPMUtilView;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the PPMUtilView class. Tests exceptions from the constructor as well
 * as the functionality of the {@code writeMessage(String)} method.
 */
public class PPMUtilViewTest {
  Appendable appendable;
  ImageUtilView imageView;

  @Before
  public void init() {
    this.appendable = new StringBuilder();
    this.imageView = new PPMUtilView(this.appendable);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalConstructor() {
    try {
      this.imageView = new PPMUtilView(null);
    } catch (IllegalStateException e) {
      throw new IllegalArgumentException("Appendable cannot be null.");
    }
  }

  @Test
  public void testWriteMessage() {
    try {
      this.imageView.writeMessage("Hello.");
      String str = appendable.toString();
      String[] strOut = (str.split("\n"));
      assertEquals("Hello.", strOut[0]);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


}