package artof.materials;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.*;
import java.awt.event.*;

public class RemoveDialog
    extends JDialog {
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboMaterial = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JComboBox jComboSupplier = new JComboBox();
  JPanel jPanel2 = new JPanel();
  JButton jButtonRemove = new JButton();
  JButton jButtonCancel = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel jPanel3 = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();

  public RemoveDialog() {
    try {
      jbInit();

      populateMaterialTypeCombo();
      populateSupplier();

      this.setTitle("Remove materials");
      this.setSize(400, 250);
      this.setLocation(200, 100);
      this.setVisible(true);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    this.setModal(true);
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    jLabel1.setText("Remove all materials of type");
    jLabel2.setText("from the following Supplier");
    jButtonRemove.setText("Remove");
    jButtonRemove.addActionListener(new
                                    RemoveDialog_jButtonRemove_actionAdapter(this));
    jButtonCancel.setText("Cancel");
    jButtonCancel.addActionListener(new
                                    RemoveDialog_jButtonCancel_actionAdapter(this));
    jPanel2.setLayout(gridBagLayout2);
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jPanel3.setLayout(gridBagLayout3);
    jLabel3.setFont(new java.awt.Font("Courier", 1, 20));
    jLabel3.setForeground(Color.red);
    jLabel3.setText("CAUTION:");
    jLabel4.setUI(new MultiLineLabelUI());
    String str = "You are about to make considerable changes ";
    str +=
        "to your \ndatabase.  It is advisable to make a backup before \nyou continue.";
    jLabel4.setText(str);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.NORTHWEST,
                                                GridBagConstraints.NONE,
                                                new Insets(30, 10, 0, 0), 0, 0));
    jPanel1.add(jComboMaterial, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
        , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
        new Insets(30, 5, 0, 5), 0, 0));
    jPanel1.add(jComboSupplier, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
        , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
        new Insets(0, 5, 0, 5), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.NORTHWEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 10, 0, 3), 0, 0));
    this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jButtonRemove, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(5, 5, 5, 50), 0, 0));
    jPanel2.add(jButtonCancel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(5, 50, 5, 10), 0, 0));
    this.getContentPane().add(jPanel3, BorderLayout.NORTH);
    jPanel3.add(jLabel3, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
                                                , GridBagConstraints.NORTHWEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 5, 0, 0), 0, 0));
    jPanel3.add(jLabel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0
                                                , GridBagConstraints.SOUTHEAST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 10, 5, 5), 0, 0));

  }

  void populateMaterialTypeCombo() {
    for (int i = 0; i < MaterialDets.MAT_TYPES.length; i++) {
      String item = (String) MaterialDets.MAT_TYPES[i];
      if (item.equals("All")) {
        item = "All Items";
      }
      jComboMaterial.addItem(item);
    }
  }

  void populateSupplier() {
    ArrayList supplierList = artof.database.ArtofDB.getCurrentDB().getSuppliers();
    for (Iterator iter = supplierList.iterator(); iter.hasNext(); ) {
      SupplierDets item = (SupplierDets) iter.next();
      jComboSupplier.addItem(item.getName());
    }
    jComboSupplier.addItem("All Suppliers");
  }

  void jButtonCancel_actionPerformed(ActionEvent e) {
    this.hide();
  }

  void jButtonRemove_actionPerformed(ActionEvent e) {

    if ( ( (String) jComboMaterial.getSelectedItem()).equals("All Items") &&
        ( (String) jComboSupplier.getSelectedItem()).equals("All Suppliers")) {
      if (JOptionPane.showConfirmDialog(this,
                                        "Are you sure you want to remove all materials from all suppliers?",
                                        "Delete",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE) ==
          JOptionPane.YES_OPTION) {
        artof.database.ArtofDB.getCurrentDB().deleteAllMaterials();
      }

    }
    else if ( ( (String) jComboMaterial.getSelectedItem()).equals("All Items")) {
      if (JOptionPane.showConfirmDialog(this,
                                        "Are you sure you want to remove all items from supplier " +
                                        (String)
                                        jComboSupplier.getSelectedItem() + "?",
                                        "Delete",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE) ==
          JOptionPane.YES_OPTION) {

        artof.database.ArtofDB.getCurrentDB().deleteMaterialsAllType( (String)
            jComboSupplier.getSelectedItem());
      }
    }
    else if ( ( (String) jComboSupplier.getSelectedItem()).equals(
        "All Suppliers")) {
      if (JOptionPane.showConfirmDialog(this,
                                        "Are you sure you want to remove all " +
                                        (String) jComboMaterial.
                                        getSelectedItem() +
                                        " from all suppliers?",
                                        "Delete",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE) ==
          JOptionPane.YES_OPTION) {

        artof.database.ArtofDB.getCurrentDB().deleteMaterialsAllSuppliers(
            MaterialDets.getItemTypeInt( (String) jComboMaterial.
                                        getSelectedItem()));
      }
    }
    else {
      if (JOptionPane.showConfirmDialog(this,
                                        "Are you sure you want to remove all " +
                                        (String) jComboMaterial.
                                        getSelectedItem() + " from supplier " +
                                        (String)jComboSupplier.getSelectedItem() + "?",
                                        "Delete",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE) ==
          JOptionPane.YES_OPTION) {

        artof.database.ArtofDB.getCurrentDB().deleteMaterials(MaterialDets.
            getItemTypeInt( (String) jComboMaterial.getSelectedItem()),
            (String) jComboSupplier.getSelectedItem());
      }
    }
    for (Iterator iter = artof.database.ArtofDB.getCurrentDB().getMaterialID().
         iterator(); iter.hasNext(); ) {
      Integer item = (Integer) iter.next();
      if (!artof.database.ArtofDB.getCurrentDB().checkMaterialIDExist(item.
          intValue())) {
        artof.database.ArtofDB.getCurrentDB().deleteMaterialFromMaterialsOnly(
            item.intValue());
      }
    }

  }
}

class RemoveDialog_jButtonCancel_actionAdapter
    implements java.awt.event.ActionListener {
  RemoveDialog adaptee;

  RemoveDialog_jButtonCancel_actionAdapter(RemoveDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonCancel_actionPerformed(e);
  }
}

class RemoveDialog_jButtonRemove_actionAdapter
    implements java.awt.event.ActionListener {
  RemoveDialog adaptee;

  RemoveDialog_jButtonRemove_actionAdapter(RemoveDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonRemove_actionPerformed(e);
  }
}
