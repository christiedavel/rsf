package artof.designitems.dialogs;

import artof.designitems.DesignItem2;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import artof.materials.profiles.SamplePanel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DesignDialogFoil extends JDialog {
  private DesignItem2 itemToEdit;
  private boolean canceled = true;
  private JDialog thisDialog;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JButton btnOK = new JButton();
  private JLabel jLabel1 = new JLabel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JComboBox cbxItemCode = new JComboBox();
  private JLabel jLabel2 = new JLabel();
  private CustomTextField txtDepth = new CustomTextField();
  private JLabel jLabel3 = new JLabel();
  private JButton btnColor = new JButton();
  private Border border1;
  private JButton btnCancel = new JButton();
  private Border border2;
  private JLabel jLabel4 = new JLabel();
  private CustomTextField txtFace = new CustomTextField();
  JLabel jLabel5 = new JLabel();
  SamplePanel pnlSample = new SamplePanel();
  Border border3;
  JLabel jLabel6 = new JLabel();
  JComboBox jComboSupplier = new JComboBox();
  boolean isSupplierBeingAdded = false;

  public DesignDialogFoil(String title, DesignItem2 item) {
    thisDialog = this;
    itemToEdit = item;
    setSize(300, 200);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setResizable(false);
    setTitle(title);
    setModal(true);

    cbxItemCode = item.getCodeMapper().getItemCodeComboBox();

    cbxItemCode.setSelectedItem(itemToEdit.getItemCode());
    txtFace.setText(Utils.getCurrencyFormat(itemToEdit.getTopGap()));
    txtDepth.setText(Utils.getCurrencyFormat(itemToEdit.getThickness()));
    Color x = itemToEdit.getColor();
    btnColor.setBackground(x);
    pnlSample.setItemImage(itemToEdit.getItemCode());

    getRootPane().setDefaultButton(btnOK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    setVisible(true);
  }

  public boolean canceled() {
    return canceled;
  }

  void btnColor_actionPerformed(ActionEvent e) {
    if (cbxItemCode.getSelectedIndex() > -1) {
      Color newColor = new JColorChooser().showDialog(null, "Display color", btnColor.getBackground());
      if (!newColor.equals(btnColor.getBackground())) {
        btnColor.setBackground(newColor);
        artof.database.ArtofDB.getCurrentDB().updateMaterialColor((String)cbxItemCode.getSelectedItem(), newColor);
      }
    }
  }

  void btnOK_actionPerformed(ActionEvent e) {
    if (cbxItemCode.getSelectedIndex() == -1) {
      canceled = true;
      hide();
      return;
    }

    try {
      itemToEdit.setDefaultSupplier((String)jComboSupplier.getSelectedItem());
      double x = Double.parseDouble(txtFace.getText());
      itemToEdit.setTopGap(x);
      itemToEdit.setBottomGap(x);
      itemToEdit.setLeftGap(x);
      itemToEdit.setRightGap(x);
      itemToEdit.setColor(btnColor.getBackground());
      hide();
      canceled = false;
    } catch (NumberFormatException x) {
      String mes = "Invalid face value was specified";
      JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    hide();
    canceled = true;
  }

  void cbxItemCode_actionPerformed(ActionEvent e) {
    try {
      itemToEdit.setItemCode((String)cbxItemCode.getSelectedItem());
      populateSupplierCombo();
      itemToEdit.refreshItem();
      txtDepth.setText(Utils.getCurrencyFormat(itemToEdit.getThickness()));
      btnColor.setBackground(itemToEdit.getColor());
      pnlSample.setItemImage((String)cbxItemCode.getSelectedItem());

    } catch (NullPointerException x) {
      pnlSample.setItemImage(null);
    }
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),BorderFactory.createEmptyBorder(5,5,5,5));
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    this.getContentPane().setLayout(borderLayout1);
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOK_actionPerformed(e);
      }
    });
    jLabel1.setText("Item Code");
    jPanel2.setLayout(gridBagLayout1);
    jLabel2.setText("Thickness");
    jLabel3.setText("Color");
    btnColor.setBorder(border1);
    btnColor.setMaximumSize(new Dimension(21, 21));
    btnColor.setMinimumSize(new Dimension(21, 21));
    btnColor.setPreferredSize(new Dimension(21, 21));
    btnColor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnColor_actionPerformed(e);
      }
    });
    txtDepth.setEditable(false);
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    jPanel1.setBorder(border2);
    cbxItemCode.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbxItemCode_actionPerformed(e);
        populateSupplierCombo();
      }
    });
    jLabel4.setText("Face");
    jLabel5.setText("Sample");
    pnlSample.setBorder(border3);
    jLabel6.setText("Supplier");
    jComboSupplier.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboSupplier_actionPerformed(e);
      }
    });
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnOK, null);
    jPanel1.add(btnCancel, null);
    this.getContentPane().add(jPanel2,  BorderLayout.CENTER);
    jPanel2.add(jLabel1,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    jPanel2.add(cbxItemCode,      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
    jPanel2.add(jLabel2,      new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel4,      new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtDepth,    new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 30), 0, 0));
    jPanel2.add(txtFace,     new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 30), 0, 0));
    jPanel2.add(jLabel5,     new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel2.add(pnlSample,     new GridBagConstraints(3, 3, 1, 2, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 40, 40));
    jPanel2.add(jLabel6,    new GridBagConstraints(0, 1, 1, 2, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jComboSupplier,     new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel3, new GridBagConstraints(2, 0, 1, 3, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 12, 5, 3), 0, 0));
    jPanel2.add(btnColor, new GridBagConstraints(3, 0, 1, 3, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(3, 7, 7, 18), 0, 0));

  try {
      if (!((String)cbxItemCode.getSelectedItem()).equals("") &&
          !((String)cbxItemCode.getSelectedItem()).equals(null)) {
        populateSupplierCombo();
      }
    } catch (NullPointerException e) {
     // e.printStackTrace();
    }
  }

  void populateSupplierCombo() {
    isSupplierBeingAdded = true;
    jComboSupplier.removeAllItems();

    ArrayList supplierList = artof.database.ArtofDB.getCurrentDB().getMaterialSuppliers((String)this.cbxItemCode.getSelectedItem());
    for (Iterator iter = supplierList.iterator(); iter.hasNext(); ) {
      String item = (String)iter.next();
      jComboSupplier.addItem(item);
    }
    isSupplierBeingAdded = false;

    try {
      if (itemToEdit.getDefaultSupplier().equals(""))
        itemToEdit.setDefaultSupplier((String)jComboSupplier.getSelectedItem());
      else
        jComboSupplier.setSelectedItem(itemToEdit.getDefaultSupplier());
    } catch (NullPointerException e) {
    }
  }

  void jComboSupplier_actionPerformed(ActionEvent e) {
    setDefaultSupplier();
    itemToEdit.refreshItem();
  }

  void setDefaultSupplier() {
    if (isSupplierBeingAdded == false)
      itemToEdit.setDefaultSupplier((String)jComboSupplier.getSelectedItem());
  }


}
