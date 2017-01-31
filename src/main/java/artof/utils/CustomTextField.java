package artof.utils;

import javax.swing.JTextField;
import java.awt.event.*;
import javax.swing.text.Document;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CustomTextField extends JTextField {

  public CustomTextField() {
    super();
    addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent f) {
        selectAll();
      }
    });

    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        selectAll();
      }
    });
  }

  public CustomTextField(Document doc, String text, int columns) {
    super(doc, text, columns);
    addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent f) {
        selectAll();
      }
    });

    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        selectAll();
      }
    });
  }

  public CustomTextField(int columns) {
    super(columns);
    addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent f) {
        selectAll();
      }
    });

    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        selectAll();
      }
    });
  }

  public CustomTextField(String text) {
    super(text);
    addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent f) {
        selectAll();
      }
    });

    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        selectAll();
      }
    });
  }

  public CustomTextField(String text, int columns) {
    super(text, columns);
    addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent f) {
        selectAll();
      }
    });

    this.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        selectAll();
      }
    });
  }
}