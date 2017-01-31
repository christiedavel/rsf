package artof.designitems.dialogs;

import artof.designitems.DesignArtWork;
import artof.designitems.DesignItem2;
import artof.utils.*;
import artof.database.Archiver;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class DesignDialogArtWork extends JDialog {
  private DesignArtWork curItem;
  private boolean canceled = true;
  private JDialog thisDialog;

  BorderLayout borderLayout1 = new BorderLayout();
  Border border1;
  JPanel jPanel2 = new JPanel();
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel1 = new JPanel();
  CustomTextField txtDepth = new CustomTextField();
  CustomTextField txtHeight = new CustomTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel4 = new JLabel();
  CustomTextField txtWidth = new CustomTextField();
  JLabel jLabel3 = new JLabel();
  JComboBox cbxDrawing = new JComboBox();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  Border border2;
  JLabel jLabel7 = new JLabel();
  JButton btnColor = new JButton();
  Border border3;
  JButton btnBrowse = new JButton();

  public DesignDialogArtWork(String title, DesignItem2 item) {
    thisDialog = this;
    try {
      curItem = (DesignArtWork)item;
    } catch (ClassCastException e) {
      return;
    }

    File path = new File("images/Artworks");
    String[] list = path.list(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        String f = new File(name).getName();
        return f.indexOf(".gif") != -1 || f.indexOf(".GIF") != -1 || f.indexOf(".jpg") != -1 || f.indexOf(".JPEG") != -1;
      }
    });

    cbxDrawing.addItem("None");
    try {
      for (int i = 0; i < list.length; i++) cbxDrawing.addItem(list[i]);
    } catch (NullPointerException e) {
      // fok dit
    }
    if (curItem.getPicture() == null || curItem.getPicture().equals(""))
      cbxDrawing.setSelectedItem("None");
    else
      cbxDrawing.setSelectedItem(curItem.getPicture());

    txtDepth.setText(Utils.getCurrencyFormat(curItem.getThickness()));
    txtWidth.setText(Utils.getCurrencyFormat(curItem.getRealOuterWidth()));
    txtHeight.setText(Utils.getCurrencyFormat(curItem.getRealOuterHeight()));
    btnColor.setBackground(curItem.getColor());

    getRootPane().setDefaultButton(btnOK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          curItem.setThickness(Double.parseDouble(txtDepth.getText()));
          double width = Double.parseDouble(txtWidth.getText());
          double height = Double.parseDouble(txtHeight.getText());
          curItem.setLeftGap(width / 2);
          curItem.setRightGap(width / 2);
          curItem.setTopGap(height / 2);
          curItem.setBottomGap(height / 2);
          curItem.setPicture((String)cbxDrawing.getSelectedItem());
          curItem.setColor(btnColor.getBackground());
          hide();
          canceled = false;
        } catch (NumberFormatException x) {
          String mes = "Invalid value was specified";
          JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hide();
        canceled = true;
      }
    });

    btnColor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnColor.setBackground(new JColorChooser().showDialog(null, "Item display color", btnColor.getBackground()));
      }
    });

    setTitle(title);
    setSize(400, 200);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public boolean canceled() {
    return canceled;
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border3 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    this.getContentPane().setLayout(borderLayout1);
    this.setModal(true);
    this.setResizable(false);
    borderLayout1.setHgap(5);
    borderLayout1.setVgap(5);
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    btnCancel.setText("Cancel");
    jPanel2.setLayout(gridBagLayout2);
    jPanel1.setLayout(gridBagLayout1);
    txtDepth.setMinimumSize(new Dimension(121, 21));
    txtHeight.setMinimumSize(new Dimension(121, 21));
    txtHeight.setPreferredSize(new Dimension(125, 21));
    jLabel6.setText("Drawing");
    jLabel5.setText("Thickness");
    jLabel4.setText("Sight Line Height");
    txtWidth.setMinimumSize(new Dimension(121, 21));
    txtWidth.setPreferredSize(new Dimension(125, 21));
    jLabel3.setText("Sight Line Width");
    jPanel3.setLayout(gridBagLayout3);
    jPanel1.setBorder(border2);
    jLabel7.setText("Color");
    btnColor.setBorder(border3);
    btnBrowse.setMargin(new Insets(2, 5, 2, 5));
    btnBrowse.setText("...");
    btnBrowse.addActionListener(new DesignDialogArtWork_btnBrowse_actionAdapter(this));
    this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(btnOK,   new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(btnCancel,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jPanel1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel3,      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtWidth,      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel4,      new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(txtHeight,      new GridBagConstraints(3, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel5,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtDepth,     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel6,       new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(cbxDrawing,           new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));
    jPanel1.add(jLabel7,          new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(btnColor,              new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 25, 25));
    jPanel1.add(btnBrowse,      new GridBagConstraints(4, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
  }

  void btnBrowse_actionPerformed(ActionEvent e) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileFilter() {
      public boolean accept(File f) {
        String name = f.getName().toLowerCase();
        if (f.isDirectory() || name.indexOf(".gif") > -1 || name.indexOf(".jpg") > -1 || name.indexOf(".png") > -1)
          return true;
        else
          return false;
      }

      public String getDescription() {
        return "Image files (JPEG, GIF, PNG)";
      }
    });

    if (UserSettings2.LAST_ARTWORK_PATH != null && !UserSettings2.LAST_ARTWORK_PATH.equals("")) {
      chooser.setSelectedFile(new File(UserSettings2.LAST_ARTWORK_PATH));
    }

    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      try {
        File file = chooser.getSelectedFile();
        Archiver archiver = new Archiver();
        archiver.copy(file.getAbsolutePath(), "images/Artworks/" + file.getName());
        UserSettings2.LAST_ARTWORK_PATH = file.getAbsolutePath();
        cbxDrawing.addItem(file.getName());
        cbxDrawing.setSelectedItem(file.getName());

      } catch (Exception x) {
        JOptionPane.showMessageDialog(this, "The file specified cannot be read", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }

  }
}

class DesignDialogArtWork_btnBrowse_actionAdapter implements java.awt.event.ActionListener {
  DesignDialogArtWork adaptee;

  DesignDialogArtWork_btnBrowse_actionAdapter(DesignDialogArtWork adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnBrowse_actionPerformed(e);
  }
}