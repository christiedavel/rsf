package artof.dialogs;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class StringSelector2 extends JDialog {
  private DefaultListModel itemsFrom = new DefaultListModel();
  private DefaultListModel itemsTo = new DefaultListModel();
  private TreeSet resultSet = new TreeSet();
  private String[] fromStrings;
  private String[] toStrings;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JButton btnCancel = new JButton();
  private JLabel jLabel1 = new JLabel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel2 = new JLabel();
  private JButton btnAll = new JButton();
  private JButton btnBackAll = new JButton();
  private JButton btnOne = new JButton();
  private JButton btnBackOne = new JButton();
  private JButton btnOK = new JButton();
  private Border border1;
  private Border border2;
  private Border border3;
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JList lstFrom = new JList(itemsFrom);
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JList lstTo = new JList(itemsTo);

  public StringSelector2(String[] fromStrings, String title) {
    this.fromStrings = fromStrings;

    for (int i = 0; i < fromStrings.length; i++)
      itemsFrom.addElement(fromStrings[i]);

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    setSize(400, 300);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setTitle(title);
    setVisible(true);
  }

  public StringSelector2(String[] fromStrings, String title, String[] toStrings) {
    this.fromStrings = fromStrings;
    this.toStrings = toStrings;

    for (int i = 0; i < fromStrings.length; i++)
      itemsFrom.addElement(fromStrings[i]);

    for (int i = 0; i < toStrings.length; i++)
      itemsTo.addElement(toStrings[i]);

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    setSize(400, 300);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setTitle(title);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(178, 178, 178));
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(178, 178, 178));
    border3 = BorderFactory.createEmptyBorder(5,0,5,0);
    this.getContentPane().setLayout(borderLayout1);
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    jLabel1.setMaximumSize(new Dimension(90, 17));
    jLabel1.setMinimumSize(new Dimension(90, 17));
    jLabel1.setPreferredSize(new Dimension(90, 17));
    jLabel1.setText("Available Fields:");
    jPanel2.setLayout(gridBagLayout1);
    jLabel2.setMaximumSize(new Dimension(90, 17));
    jLabel2.setMinimumSize(new Dimension(90, 17));
    jLabel2.setPreferredSize(new Dimension(90, 17));
    jLabel2.setText("Selected Fields:");
    btnAll.setMargin(new Insets(2, 2, 2, 2));
    btnAll.setText(">>");
    btnAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAll_actionPerformed(e);
      }
    });
    btnBackAll.setPreferredSize(new Dimension(25, 27));
    btnBackAll.setMargin(new Insets(2, 2, 2, 2));
    btnBackAll.setText("<<");
    btnBackAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnBackAll_actionPerformed(e);
      }
    });
    btnOne.setMaximumSize(new Dimension(25, 27));
    btnOne.setMinimumSize(new Dimension(25, 27));
    btnOne.setPreferredSize(new Dimension(25, 27));
    btnOne.setMargin(new Insets(2, 2, 2, 2));
    btnOne.setText(">");
    btnOne.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOne_actionPerformed(e);
      }
    });
    btnBackOne.setMaximumSize(new Dimension(25, 27));
    btnBackOne.setMinimumSize(new Dimension(25, 27));
    btnBackOne.setPreferredSize(new Dimension(25, 27));
    btnBackOne.setMargin(new Insets(2, 2, 2, 2));
    btnBackOne.setText("<");
    btnBackOne.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnBackOne_actionPerformed(e);
      }
    });
    btnOK.setMaximumSize(new Dimension(73, 27));
    btnOK.setMinimumSize(new Dimension(73, 27));
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOK_actionPerformed(e);
      }
    });
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Select Fields");
    jPanel1.setBorder(border3);
    lstFrom.setBorder(border1);
    lstTo.setBorder(border2);
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnCancel, null);
    jPanel1.add(btnOK, null);
    this.getContentPane().add(jPanel2,  BorderLayout.CENTER);
    jPanel2.add(jLabel1,           new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
    jPanel2.add(jLabel2,          new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
    jPanel2.add(btnAll,           new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 5, 2, 5), 0, 0));
    jPanel2.add(btnBackAll,       new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(2, 5, 0, 5), 0, 0));
    jPanel2.add(btnOne,      new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(btnBackOne,     new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(jScrollPane1,    new GridBagConstraints(0, 1, 1, 4, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 5, 5, 5), 0, 0));
    jPanel2.add(jScrollPane2,  new GridBagConstraints(2, 1, 1, 4, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 5, 5, 5), 0, 0));
    jScrollPane2.getViewport().add(lstTo, null);
    jScrollPane1.getViewport().add(lstFrom, null);
  }

  void btnAll_actionPerformed(ActionEvent e) {
    itemsFrom.clear();
    itemsTo.clear();
    for (int i = 0; i < fromStrings.length; i++)
      itemsTo.addElement(fromStrings[i]);
  }

  void btnOne_actionPerformed(ActionEvent e) {
    Object[] sels = lstFrom.getSelectedValues();
    for (int i = 0; i < sels.length; i++) {
      String val = (String)sels[i];
      itemsTo.addElement(val);
      itemsFrom.remove(itemsFrom.indexOf(val));
    }
  }

  void btnBackOne_actionPerformed(ActionEvent e) {
    Object[] sels = lstTo.getSelectedValues();
    for (int i = 0; i < sels.length; i++) {
      String val = (String)sels[i];
      itemsFrom.add(0, val);
      itemsTo.remove(itemsTo.indexOf(val));
    }
  }

  void btnBackAll_actionPerformed(ActionEvent e) {
    itemsTo.clear();
    itemsFrom.clear();
    for (int i = 0; i < fromStrings.length; i++)
      itemsFrom.addElement(fromStrings[i]);
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    try {
      resultSet.clear();
      for (int i = 0; i < toStrings.length; i++)
        resultSet.add(toStrings[i]);
    } catch (NullPointerException x) {
    }
    hide();
  }

  void btnOK_actionPerformed(ActionEvent e) {
    try {
      resultSet.clear();
      for (int i = 0; i < lstTo.getModel().getSize(); i++)
        resultSet.add(lstTo.getModel().getElementAt(i));
    } catch (NullPointerException x) {
    }
    hide();
  }

  public TreeSet getSelectedFields() {
    return resultSet;
  }
}