package artof.dialogs;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import artof.utils.*;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class RegistrationDialog extends JDialog {
  private String idValue = UserSettings.registrationNo;
  private boolean canRenew = false;
  private int expiryDate = UserSettings.expiryDate;

  private RSA rsa = new RSA(50);

  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JButton btnOK = new JButton();
  private JButton btnRegister = new JButton();
  private Border border1;
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();
  CustomTextField txtAdd2 = new CustomTextField();
  CustomTextField txtTel = new CustomTextField();
  JLabel jLabel12 = new JLabel();
  CustomTextField txtEmail = new CustomTextField();
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  CustomTextField txtName = new CustomTextField();
  JLabel jLabel15 = new JLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  CustomTextField txtCel = new CustomTextField();
  CustomTextField txtAdd1 = new CustomTextField();
  JLabel jLabel16 = new JLabel();
  CustomTextField txtFax = new CustomTextField();
  CustomTextField txtSurname = new CustomTextField();
  JPanel jPanel3 = new JPanel();
  CustomTextField txtAdd3 = new CustomTextField();
  CustomTextField txtCode = new CustomTextField();
  CustomTextField txtCompany = new CustomTextField();
  JLabel jLabel17 = new JLabel();
  JLabel jLabel18 = new JLabel();
  JPanel jPanel4 = new JPanel();
  JLabel jLabel19 = new JLabel();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JTextField txtServerKey1 = new JTextField();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JTextField txtExpDate = new JTextField();
  JTextField txtClientID = new JTextField();
  JTextField txtClientKey1 = new JTextField();
  JLabel jLabel3 = new JLabel();
  JTextField txtServerKey2 = new JTextField();
  JLabel jLabel6 = new JLabel();
  JTextField txtClientKey2 = new JTextField();
  JButton btnClear = new JButton();

  public RegistrationDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    txtName.setText(UserSettings.ownerName);
    txtSurname.setText(UserSettings.ownerSurname);
    txtEmail.setText(UserSettings.ownerEmail);
    txtCompany.setText(UserSettings.ownerCompany);
    txtAdd1.setText(UserSettings.ownerAdd1);
    txtAdd2.setText(UserSettings.ownerAdd2);
    txtAdd3.setText(UserSettings.ownerAdd3);
    txtCode.setText(UserSettings.ownerCode);
    txtTel.setText(UserSettings.ownerTel);
    txtCel.setText(UserSettings.ownerCel);
    txtFax.setText(UserSettings.ownerFax);

    setSize(500, 500);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    panel1.setLayout(borderLayout1);
    btnOK.setPreferredSize(new Dimension(81, 27));
    btnOK.setText("OK");
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOK_actionPerformed(e);
      }
    });
    btnRegister.setText("Register");
    btnRegister.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnRegister_actionPerformed(e);
      }
    });
    jLabel10.setText("E-mail");
    jLabel11.setText("Fax No");
    jLabel12.setText("Name");
    jLabel13.setText("Company");
    jLabel14.setText("Tel No");
    jLabel15.setText("Surname");
    jLabel16.setText("Cel No");
    jPanel3.setBorder(border1);
    jPanel3.setLayout(gridBagLayout2);
    jLabel17.setText("Code");
    jLabel18.setText("Address");
    jLabel19.setText("Registration Process:");
    jLabel1.setText("1. Enter Base Code 1 provided by the telphone operator:");
    jPanel4.setLayout(gridBagLayout1);
    jLabel2.setText("3. Read the ID Number to the operator:");
    jLabel4.setText("6. Enter the Result Code provided by the operator:");
    jLabel5.setText("4. Read RSF Code 1 to the operator:");
    txtServerKey1.setText("");
    txtServerKey1.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtServerKey1_focusLost(e);
      }
    });
    txtClientID.setEnabled(true);
    txtClientID.setEditable(false);
    txtClientID.setText("");
    txtClientKey1.setEditable(false);
    txtClientKey1.setText("");
    txtExpDate.setText("");
    txtName.setEditable(false);
    txtEmail.setEditable(false);
    txtCompany.setEditable(false);
    txtCel.setEditable(false);
    txtTel.setEditable(false);
    txtFax.setEditable(false);
    txtCode.setEditable(false);
    txtAdd1.setEditable(false);
    txtAdd2.setEditable(false);
    txtAdd3.setEditable(false);
    txtSurname.setEditable(false);
    jLabel3.setText("2. Enter Base Code 2 provided by the telphone operator:");
    jLabel6.setText("5. Read RSF Code 2 to the operator:");
    txtClientKey2.setEditable(false);
    txtClientKey2.setText("");
    txtServerKey2.setText("");
    txtServerKey2.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtServerKey2_focusLost(e);
      }
    });
    btnClear.setPreferredSize(new Dimension(75, 25));
    btnClear.setText("Clear");
    btnClear.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnClear_actionPerformed(e);
      }
    });
    jPanel1.add(btnOK, null);
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(btnRegister, null);
    jPanel1.add(btnClear, null);
    panel1.add(jPanel3,  BorderLayout.CENTER);
    jPanel3.add(jLabel12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel15, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel3.add(txtSurname, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel16, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtCel, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel14, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtTel, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel11, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtFax, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel18, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel3.add(txtAdd2, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtAdd1, new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtAdd3, new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtCode, new GridBagConstraints(3, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel17, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel3.add(txtCompany, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel13, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel10, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtEmail, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jPanel4, BorderLayout.NORTH);
    jPanel4.add(jLabel19,          new GridBagConstraints(0, 0, 4, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel1,          new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    jPanel4.add(txtServerKey1,          new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel2,        new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel4,      new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel5,       new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(txtExpDate,      new GridBagConstraints(1, 6, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(txtClientID,     new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(txtClientKey1,      new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel3,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(txtServerKey2,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel6,  new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(txtClientKey2,  new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }

  void btnRegister_actionPerformed(ActionEvent e) {
    try {
      BigInteger code = new BigInteger(txtExpDate.getText());
      BigInteger date = rsa.decrypt(code);
      String zap = date.toString();
      int newExpDate = date.intValue();
      if (newExpDate < 19990101 || newExpDate > UserSettings.expiryDate + 500000)
        throw new NumberFormatException();
      else
        UserSettings.expiryDate = newExpDate;

      UserSettings.saveUserSettings();
      txtServerKey1.setText("");
      txtServerKey2.setText("");
      txtClientID.setText("");
      txtClientKey1.setText("");
      txtClientKey2.setText("");
      txtExpDate.setText("");
      rsa = new RSA(50);

      try {
        PCSec.getInstance().register();
        String mes = "You were successfully registered";
        JOptionPane.showMessageDialog(this, mes, "Registration", JOptionPane.INFORMATION_MESSAGE);

      } catch (Exception c) {
        String mes = "A registration error occurred";
        JOptionPane.showMessageDialog(this, mes, "Registration", JOptionPane.ERROR_MESSAGE);
      }

    } catch (NumberFormatException x) {
      String mes = "Invalid codes were entered";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
}
  }

  void btnOK_actionPerformed(ActionEvent e) {
    hide();
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    hide();
  }

  void btnClear_actionPerformed(ActionEvent e) {
    txtServerKey1.setText("");
    txtServerKey2.setText("");
    txtClientID.setText("");
    txtClientKey1.setText("");
    txtClientKey2.setText("");
    txtExpDate.setText("");
    rsa = new RSA(50);
  }

  void txtServerKey1_focusLost(FocusEvent e) {

  }

  void txtServerKey2_focusLost(FocusEvent e) {
    try {
      if (txtServerKey1.getText() == null || txtServerKey1.getText().equals(""))
        throw new NumberFormatException();

      if (txtServerKey2.getText() == null || txtServerKey2.getText().equals(""))
        throw new NumberFormatException();

      BigInteger ev = new BigInteger(txtServerKey1.getText());
      BigInteger nv = new BigInteger(txtServerKey2.getText());
      String zap = UserSettings.registrationNo;
      BigInteger code = rsa.encrypt(new BigInteger(UserSettings.registrationNo), ev, nv);
      txtClientID.setText(code.toString());

      txtClientKey1.setText(rsa.getEValue().toString());
      txtClientKey2.setText(rsa.getNValue().toString());

    } catch (NumberFormatException x) {
      String mes = "Invalid code entered";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

}