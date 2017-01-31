package artof.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import artof.database.Archiver;
import artof.dialogs.*;
import java.io.*;
import artof.database.ArtofDB;
import artof.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AdvancedPanel extends JPanel {
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private AdvancedPanel panel = this;
  JLabel jLabel1 = new JLabel();
  JTextField txtDB = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextField txtMaterialServer = new JTextField();
  JLabel jLabel3 = new JLabel();
  JTextField txtMaterialServerPort = new JTextField();
  JLabel jLabel4 = new JLabel();
  JRadioButton rbnInProcess = new JRadioButton();
  JRadioButton rbnServer = new JRadioButton();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JButton btnConnect = new JButton();

  public AdvancedPanel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    txtDB.setText(UserSettings.DATABASE_URL);
    txtMaterialServer.setText(UserSettings.MATERIAL_SERVER);
    txtMaterialServerPort.setText(String.valueOf(UserSettings.MATERIAL_SERVER_PORT));

    if (UserSettings2.USE_DATABASE_SERVER) {
      rbnServer.setSelected(true);
      txtDB.setEnabled(true);
    } else {
      rbnInProcess.setSelected(true);
      txtDB.setEnabled(false);
    }
  }

  public void doOK() {
    if (rbnInProcess.isSelected())
      UserSettings2.USE_DATABASE_SERVER = false;
    else
      UserSettings2.USE_DATABASE_SERVER = true;

    UserSettings.DATABASE_URL = txtDB.getText();

    UserSettings.MATERIAL_SERVER = txtMaterialServer.getText();
    try {
      UserSettings.MATERIAL_SERVER_PORT = Integer.parseInt(txtMaterialServerPort.getText());
    } catch (NumberFormatException e) {
      //los net so
    }
  }

  private void jbInit() throws Exception {
    jLabel1.setText("Database Server");
    this.setLayout(gridBagLayout1);
    jLabel2.setToolTipText("");
    jLabel2.setText("Material Server");
    jLabel3.setText("Material Server Port");
    txtMaterialServerPort.setPreferredSize(new Dimension(100, 21));
    txtMaterialServerPort.addFocusListener(new
        AdvancedPanel_txtMaterialServerPort_focusAdapter(this));
    jLabel4.setText("Server Type");
    rbnInProcess.setText("Single User");
    rbnInProcess.addActionListener(new AdvancedPanel_rbnInProcess_actionAdapter(this));
    rbnServer.setText("Multiple User");
    rbnServer.addActionListener(new AdvancedPanel_rbnServer_actionAdapter(this));
    btnConnect.setText("Reconnect");
    btnConnect.addActionListener(new AdvancedPanel_btnConnect_actionAdapter(this));
    this.add(jLabel1,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    this.add(txtDB,       new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(jLabel2,      new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    this.add(txtMaterialServer,      new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(15, 5, 5, 5), 0, 0));
    this.add(jLabel3,      new GridBagConstraints(0, 4, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    this.add(txtMaterialServerPort,      new GridBagConstraints(1, 4, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(15, 5, 10, 5), 0, 0));
    this.add(jLabel4,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(rbnServer,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    this.add(rbnInProcess,   new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnConnect, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    buttonGroup1.add(rbnInProcess);
    buttonGroup1.add(rbnServer);
  }

  void txtMaterialServerPort_focusLost(FocusEvent e) {
    try {
      Integer.parseInt(txtMaterialServerPort.getText());
    }
    catch (NumberFormatException x) {
      String mes = "Invalid port number was specified";
      JOptionPane.showMessageDialog(this, mes, "Error",
                                    JOptionPane.ERROR_MESSAGE);
      txtMaterialServerPort.setText(String.valueOf(UserSettings.
          MATERIAL_SERVER_PORT));
    }
  }

  void rbnInProcess_actionPerformed(ActionEvent e) {
    txtDB.setEnabled(false);
    UserSettings2.USE_DATABASE_SERVER = false;
  }

  void rbnServer_actionPerformed(ActionEvent e) {
    txtDB.setEnabled(true);
    UserSettings2.USE_DATABASE_SERVER = true;
  }

  void btnConnect_actionPerformed(ActionEvent e) {
    ArtofDB.getCurrentDB().refreshNewConnection();
  }
}

class AdvancedPanel_txtMaterialServerPort_focusAdapter
    extends java.awt.event.FocusAdapter {
  AdvancedPanel adaptee;

  AdvancedPanel_txtMaterialServerPort_focusAdapter(AdvancedPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void focusLost(FocusEvent e) {
    adaptee.txtMaterialServerPort_focusLost(e);
  }
}

class AdvancedPanel_rbnInProcess_actionAdapter implements java.awt.event.ActionListener {
  AdvancedPanel adaptee;

  AdvancedPanel_rbnInProcess_actionAdapter(AdvancedPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.rbnInProcess_actionPerformed(e);
  }
}

class AdvancedPanel_rbnServer_actionAdapter implements java.awt.event.ActionListener {
  AdvancedPanel adaptee;

  AdvancedPanel_rbnServer_actionAdapter(AdvancedPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.rbnServer_actionPerformed(e);
  }
}

class AdvancedPanel_btnConnect_actionAdapter implements java.awt.event.ActionListener {
  AdvancedPanel adaptee;

  AdvancedPanel_btnConnect_actionAdapter(AdvancedPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnConnect_actionPerformed(e);
  }
}