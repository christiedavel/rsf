package artof.dialogs;
import artof.utils.CustomTextField;
import artof.utils.UserSettings;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class OwnerDialog extends JDialog {
  private JPanel jPanel1 = new JPanel();
  private JButton btnCancel = new JButton();
  private JButton btnOK = new JButton();
  private JPanel jPanel2 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private CustomTextField txtName = new CustomTextField();
  private JLabel jLabel2 = new JLabel();
  private CustomTextField txtSurname = new CustomTextField();
  private JLabel jLabel3 = new JLabel();
  private CustomTextField txtCompany = new CustomTextField();
  private JLabel jLabel4 = new JLabel();
  private CustomTextField txtEmail = new CustomTextField();
  private JLabel jLabel5 = new JLabel();
  private CustomTextField txtAdd1 = new CustomTextField();
  private JLabel jLabel6 = new JLabel();
  private CustomTextField txtCel = new CustomTextField();
  private JLabel jLabel7 = new JLabel();
  private CustomTextField txtTel = new CustomTextField();
  private JLabel jLabel8 = new JLabel();
  private CustomTextField txtFax = new CustomTextField();
  private CustomTextField txtAdd2 = new CustomTextField();
  private CustomTextField txtAdd3 = new CustomTextField();
  private CustomTextField txtCode = new CustomTextField();
  private JLabel jLabel9 = new JLabel();
  private Border border1;
  private BorderLayout borderLayout1 = new BorderLayout();
  JLabel jLabel10 = new JLabel();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel11 = new JLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JTextField accountNumberTextField = new JTextField();
  JLabel jLabel12 = new JLabel();
  JTextField accountHolderTextfield = new JTextField();
  Border border2 = BorderFactory.createEtchedBorder(Color.white,
      new Color(148, 145, 140));
  Border border3 = new TitledBorder(border2, "Bank Details");
  TitledBorder titledBorder1 = new TitledBorder("");
  Border border4 = BorderFactory.createEtchedBorder(Color.white,
      new Color(148, 145, 140));
  Border border5 = new TitledBorder(border4, "Owner Bank Details");
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  JTextField bankTextField = new JTextField();
  JTextField bankCodeTextField = new JTextField();
  public OwnerDialog() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

		txtName.setText(UserSettings.ownerName);
		txtSurname.setText(UserSettings.ownerSurname);
		txtEmail.setText(UserSettings.ownerEmail);
		txtCel.setText(UserSettings.ownerCel);
		txtCompany.setText(UserSettings.ownerCompany);
		txtTel.setText(UserSettings.ownerTel);
		txtFax.setText(UserSettings.ownerFax);
		txtAdd1.setText(UserSettings.ownerAdd1);
		txtAdd2.setText(UserSettings.ownerAdd2);
		txtAdd3.setText(UserSettings.ownerAdd3);
		txtCode.setText(UserSettings.ownerCode);
		accountHolderTextfield.setText(UserSettings.ACCOUNT_OWNER);
		accountNumberTextField.setText(UserSettings.ACCOUNT_NUMBER);
		bankTextField.setText(UserSettings.BANK_NAME);
		bankCodeTextField.setText(UserSettings.BANK_CODE);

    setSize(420, 400);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Owner Details");
    this.getContentPane().setLayout(borderLayout1);
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
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
    jPanel2.setLayout(gridBagLayout1);
    jLabel1.setText("Name*");
    jLabel2.setText("Surname*");
    jLabel3.setText("Company*");
    jLabel4.setText("E-mail");
    jLabel5.setText("Address*");
    jLabel6.setText("Cel No");
    jLabel7.setText("Tel No*");
    jLabel8.setText("Fax No");
    jLabel9.setText("Code*");
    jPanel2.setBorder(border1);
    borderLayout1.setHgap(5);
    borderLayout1.setVgap(5);
    jLabel10.setText("* Required fields");
    txtSurname.setText("");
    jLabel11.setText("Account Number");
    jPanel3.setLayout(gridBagLayout2);
    accountNumberTextField.setText("");
    jLabel12.setText("Account Holder");
    accountHolderTextfield.setText("");
    jPanel3.setBorder(border5);
    jLabel13.setText("Bank");
    jLabel14.setText("BankCode");
    jPanel1.add(btnCancel, null);
    jPanel1.add(btnOK, null);
    this.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
    this.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
    jPanel2.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(txtSurname, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel6, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtCel, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel7, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtTel, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel8, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 0, 5), 0, 0));
    jPanel2.add(txtFax, new GridBagConstraints(1, 5, 1, 2, 1.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel5, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(txtAdd1, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtAdd2, new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtAdd3, new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtCode, new GridBagConstraints(3, 5, 1, 2, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel9, new GridBagConstraints(2, 5, 1, 2, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(txtCompany, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtEmail, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jPanel3, new GridBagConstraints(0, 7, 4, 4, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jLabel10, new GridBagConstraints(0, 11, 2, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel11, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel14, new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(jLabel13, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(bankTextField, new GridBagConstraints(5, 0, 2, 1, 1.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.NORTHWEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(bankCodeTextField, new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(accountNumberTextField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 20), 0, 0));
    jPanel3.add(accountHolderTextfield, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 20), 0, 0));
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    hide();
  }

  void btnOK_actionPerformed(ActionEvent e) {
    UserSettings.ownerName = txtName.getText();
    UserSettings.ownerSurname = txtSurname.getText();
    UserSettings.ownerEmail = txtEmail.getText();
    UserSettings.ownerCel = txtCel.getText();
    UserSettings.ownerCompany = txtCompany.getText();
    UserSettings.ownerTel = txtTel.getText();
    UserSettings.ownerFax = txtFax.getText();
    UserSettings.ownerAdd1 = txtAdd1.getText();
    UserSettings.ownerAdd2 = txtAdd2.getText();
    UserSettings.ownerAdd3 = txtAdd3.getText();
    UserSettings.ownerCode = txtCode.getText();

		UserSettings.ACCOUNT_OWNER = accountHolderTextfield.getText();
		UserSettings.ACCOUNT_NUMBER = accountNumberTextField.getText();
		UserSettings.BANK_NAME = bankTextField.getText();
		UserSettings.BANK_CODE = bankCodeTextField.getText();
		
    UserSettings.saveUserSettings();
    hide();
  }

}
