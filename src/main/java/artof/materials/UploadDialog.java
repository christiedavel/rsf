package artof.materials;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class UploadDialog extends JDialog {
  private boolean doUpload = false;
  private String groupID;
  private String password;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton btnUpload = new JButton();
  JButton btnCancel = new JButton();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JTextField txtGroupID = new JTextField();
  JTextField txtPassword = new JTextField();
  Border border1;

  public UploadDialog() {
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    setSize(300, 200);

    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public boolean continueUpload() {
    return doUpload;
  }

  public String getGroupID() {
    return groupID;
  }

  public String getPassword() {
    return password;
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEmptyBorder(5,5,5,5);
    panel1.setLayout(gridBagLayout1);
    this.getContentPane().setLayout(borderLayout2);
    btnUpload.setText("Upload");
    btnUpload.addActionListener(new UploadDialog_btnUpload_actionAdapter(this));
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new UploadDialog_btnCancel_actionAdapter(this));
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setBorder(border1);
    jLabel1.setText("Upload Materials");
    jLabel2.setText("Group ID");
    jLabel3.setText("Password");
    txtGroupID.setText("");
    txtPassword.setText("");
    this.setModal(true);
    getContentPane().add(panel1, BorderLayout.CENTER);
    panel1.add(jLabel2,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jLabel3,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(btnCancel, null);
    jPanel1.add(btnUpload, null);
    this.getContentPane().add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jLabel1, null);
    panel1.add(txtPassword,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(txtGroupID,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    doUpload = false;
    hide();
  }

  void btnUpload_actionPerformed(ActionEvent e) {
    try {
      int y = Integer.parseInt(txtGroupID.getText());
    } catch (Exception ex) {
      String mes = "The group ID must be an integer number.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
      doUpload = false;
      return;
    }

    groupID = txtGroupID.getText();
    password = txtPassword.getText();
    doUpload = true;
    hide();
  }
}

class UploadDialog_btnCancel_actionAdapter implements java.awt.event.ActionListener {
  UploadDialog adaptee;

  UploadDialog_btnCancel_actionAdapter(UploadDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnCancel_actionPerformed(e);
  }
}

class UploadDialog_btnUpload_actionAdapter implements java.awt.event.ActionListener {
  UploadDialog adaptee;

  UploadDialog_btnUpload_actionAdapter(UploadDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnUpload_actionPerformed(e);
  }
}