package artof.utils;
import javax.swing.table.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NumberRenderer extends DefaultTableCellRenderer {

  public NumberRenderer() {
    super();
    this.setHorizontalAlignment(JTextField.RIGHT);
  }

  public NumberRenderer(int alignment) {
    super();
    this.setHorizontalAlignment(alignment);
  }
}