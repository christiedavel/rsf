package artof.dialogs;
import artof.database.DataFunctions;
import artof.database.DBItem;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import artof.utils.CustomTextField;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

class FieldFinder extends JDialog {
  JComboBox cbxField = new JComboBox();
  CustomTextField txtValue = new CustomTextField();
  JButton btnOK = new JButton("OK");
  JButton btnCancel = new JButton("Cancel");
  private boolean canceled = false;

  public FieldFinder(final String[] aFields, final String[] fieldval/*, final String value*/) {
    getContentPane().setLayout(new BorderLayout());
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
    getContentPane().add(panel, BorderLayout.CENTER);

    GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.WEST;

    JLabel label = new JLabel("Select field");
    label.setBorder(new EmptyBorder(5, 0, 5, 30));
    panel.add(label, c);
    cbxField.setPreferredSize(new Dimension(150, 20));
    c.gridx = 1;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
    panel.add(cbxField, c);

    label = new JLabel("Value to find");
    label.setBorder(new EmptyBorder(5, 0, 5, 30));
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 0.0;
    panel.add(label, c);
    txtValue.setPreferredSize(new Dimension(150, 20));
    c.gridx = 1;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
    panel.add(txtValue, c);

    panel = new JPanel(new GridLayout(1, 3));
    panel.setBorder(new EmptyBorder(5, 25, 5, 25));
    btnCancel.setMargin(new Insets(0, 0, 0, 0));
    panel.add(btnCancel);
    panel.add(new JPanel());
    panel.add(btnOK);
    getContentPane().add(panel, BorderLayout.SOUTH);

    for (int i = 0; i < aFields.length; i++)
      cbxField.addItem(aFields[i]);

    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        canceled = false;
        fieldval[0] = (String)cbxField.getSelectedItem();
        fieldval[1] = txtValue.getText();
        hide();
      }
    });

    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        canceled = true;
        hide();
      }
    });

    setTitle("Find");
    setSize(270, 150);
    setResizable(false);
    setModal(true);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    getRootPane().setDefaultButton(btnOK);
    txtValue.requestFocusInWindow();
    setVisible(true);
  }

  public boolean getCanceled() {
    return canceled;
  }
}

public class ButCreator {
  private BrowsePanel browsePanel;
  private DataPanel dataPanel;
  private ListIterator mainIt = null;
  private Object curObj = null;
  private DataFunctions dataFunctions;

  public ButCreator(final DataFunctions functions) {
    dataFunctions = functions;
    mainIt = functions.getMainIterator();
    if (mainIt.hasNext()) {
      curObj = mainIt.next();
      functions.updateData(curObj);
    }
    else
      functions.updateData(null);

    browsePanel = new BrowsePanel(functions, this);
    dataPanel = new DataPanel(functions, this);
  }

  public JPanel getBrowsePanel() {
    return browsePanel;
  }

  public JPanel getDataPanel() {
    return dataPanel;
  }

  public void findField(String field, String value) {
    ListIterator it = dataFunctions.getMainIterator();
    while (it.hasNext()) {
      DBItem item = (DBItem)it.next();
      try {
        if (item.matchValue(field, value)) {
          mainIt = it;
          curObj = item;
          dataFunctions.updateData(curObj);
        }
      } catch (NullPointerException x) {
        return;
      }
    }
  }

  public Object getCurrentObject() {
    return curObj;
  }

  public void setCurrentObject(Object obj) {
    curObj = obj;
    browsePanel.refreshButtons();
  }

  public ListIterator getIterator() {
    return mainIt;
  }

  public void setIterator(ListIterator it) {
    mainIt = it;
  }
}