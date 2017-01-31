package artof.utils;

import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SearchComboBox extends JComboBox {
  private JTextField txtEditor;
  private String typedText;

  public SearchComboBox() {
    super();

    setEditable(true);
    txtEditor = (JTextField)getEditor().getEditorComponent();

    txtEditor.addKeyListener(new KeyListener() {
      public void keyPressed(KeyEvent e) {
      }

      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
          hidePopup();

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT ||
                   e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CAPS_LOCK ||
                   e.getKeyCode() == KeyEvent.VK_CONTROL) {
          return;

        } else {
          if (!isPopupVisible())
            showPopup();
        }

        typedText = txtEditor.getText();
        int typedTextLength = typedText.length();

        int d = selectionForKey(typedText);
        if (d > -1 && !typedText.equals("")) {
          // haal eers al die listeners uit om te keer dat events onnodig gefire word
          // want dit maak net kak
          ActionListener[] listeners = getActionListeners();
          for (int i = 0; i < listeners.length; i++) {
            removeActionListener(listeners[i]);
          }

          // kies item
          setSelectedIndex(d);

          // sit listeners terug
          for (int i = 0; i < listeners.length; i++) {
            addActionListener(listeners[i]);
          }

          String selectedText = (String)getItemAt(d);

          if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != KeyEvent.VK_DELETE) {
            txtEditor.setText(selectedText);
          }

          if (selectedText.substring(0, typedText.length()).equalsIgnoreCase(typedText)) {
            txtEditor.setCaretPosition(typedTextLength);
            txtEditor.setSelectionStart(typedTextLength);
            txtEditor.setSelectionEnd(selectedText.length());
          }
        }
      }

      public void keyTyped(KeyEvent e) {
      }
    });
  }

  public int selectionForKey(String sItem) {
    int iCount = getItemCount();
    int iPatternLen = 0;
    int iSelected = -1;

    iPatternLen = sItem.length();
    for (int pos = 0; pos < iCount; pos++) {
      String curItem = (String)getItemAt(pos);
      if (curItem.length() >= iPatternLen) {
        if (curItem.substring(0, iPatternLen).equalsIgnoreCase(sItem)) {
          iSelected = pos;
          break;
        }
      }
    }
    return iSelected;
  }
}



/*txtEditor = (JTextField)patternList.getEditor().getEditorComponent();
 patternList.getEditor().getEditorComponent().addKeyListener(new
KeyAdapter() {
      public void keyPressed(KeyEvent e) {
      }

      public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode()
== KeyEvent.VK_ESCAPE) {
                  patternList.hidePopup();
            } else {
                  if (!patternList.isPopupVisible())
                        patternList.showPopup();
            }

            System.out.println();
            System.out.println("In keyReleased");

            String typedText = txtEditor.getText();
            int typedTextLength = typedText.length();

            int d = selectionForKey(e.getKeyChar());
            if (d > -1 && !typedText.equals("")) {
                  patternList.setSelectedIndex(d);
                  String selectedText
= (String)patternList.getItemAt(d);
                  file://txtEditor.setText(selectedText);
                  file://((BasicComboBoxRenderer)patternList.getRenderer
()).setText(selectedText);

                  System.out.println("Typed Text: " + typedText);
                  System.out.println("Selected Text: " +
selectedText);

                  if (selectedText.substring(0, typedText.length
()).equals(typedText)) {
                        txtEditor.setCaretPosition(typedTextLength);
                        txtEditor.setSelectionStart(typedTextLength);
                        txtEditor.setSelectionEnd(selectedText.length
());

                        System.out.println("Select start: " +
typedTextLength);
                        System.out.println("Select End: " +
selectedText.length());
                  }
            }
      }

      public void keyTyped(KeyEvent e) {
      }
  });

      String tos = txtEditor.getText();
      txtEditor.setSelectionStart(0);
      txtEditor.setSelectionEnd(tos.length());
}


public int selectionForKey(char aKey) {
      int iCount = patternList.getItemCount();
      int iPatternLen = 0;
      int iSelected = -1;
      String sItem = txtEditor.getText();

      iPatternLen = sItem.length();
      for (int pos = 0; pos < iCount; pos++) {
            String curItem = (String)patternList.getItemAt(pos);
            if (curItem.length() >= iPatternLen) {
                  if (curItem.substring(0,
iPatternLen).equalsIgnoreCase(sItem)) {
                        iSelected = pos;
                        break;
                  }
            } else {

            }
      }

      return iSelected;
}*/


