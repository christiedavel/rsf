package artof.designitems.dialogs;

import artof.designitems.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DesignDialogGBs extends JDialog {
  private JDialog thisDialog;
  private DesignGlassAndBack itemToEdit;
  private boolean canceled = true;
  private HashMap matchMap = new HashMap();

  JPanel jPanel1 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  Border border1;
  Border border2;
  Border border3;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JComboBox cbxItemCode = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JComboBox cbxOwnCode = new JComboBox();
  JComboBox cbxMatch = new JComboBox();
  JRadioButton rbnInside = new JRadioButton();
  JRadioButton rbnOutside = new JRadioButton();
  JPanel jPanel5 = new JPanel();
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  Border border4;
  ButtonGroup buttonGroup1 = new ButtonGroup();
  ButtonGroup buttonGroup2 = new ButtonGroup();
  private JLabel jLabel4 = new JLabel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private GridLayout gridLayout1 = new GridLayout();
  JLabel jLabel3 = new JLabel();
  JComboBox jComboSupplier = new JComboBox();
  boolean isSupplierBeingAdded = false;

  public DesignDialogGBs(String title, DesignItem2 item) {
    thisDialog = this;
    try {
      itemToEdit = (DesignGlassAndBack) item;
    }
    catch (ClassCastException e) {
      return;
    }

    try {
      cbxItemCode = item.getCodeMapper().getItemCodeComboBox();
      cbxOwnCode = item.getCodeMapper().getOwnCodeComboBox();

      int i = 0;
      Iterator it = itemToEdit.getItemListIterator();
      while (it.hasNext()) {
        DesignItem2 zapper = (DesignItem2) it.next();
        String zapnaam = zapper.getType();
        if (! (zapper instanceof DesignArtWork)) {
          zapnaam += ": " + zapper.getItemCode();
        }
        if (! (zapper instanceof DesignGlassAndBack)) {
          cbxMatch.addItem(zapnaam);
          matchMap.put(zapnaam, zapper);
        }
      }
    }
    catch (NullPointerException x) {
      // Doen niks
    }

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    updateValues();

    cbxItemCode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          itemToEdit.setItemCode( (String) cbxItemCode.getSelectedItem());
          updateValues();
          populateSupplierCombo();
          itemToEdit.refreshItem();
        }
        catch (NullPointerException x) {
          //doen niks
        }
      }
    });

    cbxOwnCode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          itemToEdit.setOwnCode( (String) cbxOwnCode.getSelectedItem());
          updateValues();
          populateSupplierCombo();
          itemToEdit.refreshItem();
        }
        catch (NullPointerException x) {
          //doen niks
        }
      }
    });

    cbxMatch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          DesignItem2 item = (DesignItem2) matchMap.get(cbxMatch.
              getSelectedItem());
          if (item instanceof DesignArtWork) {
            rbnOutside.setSelected(true);
          }
        }
        catch (NullPointerException x) {
          //doen niks
        }
      }
    });

    rbnInside.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (rbnInside.isSelected()) {
          DesignItem2 item = (DesignItem2) matchMap.get(cbxMatch.
              getSelectedItem());
          if (item instanceof DesignArtWork) {
            rbnOutside.setSelected(true);
          }
        }
      }
    });

    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (cbxItemCode.getSelectedIndex() == -1) {
          canceled = true;
          hide();
          return;
        }

        try {
          DesignItem2 item = (DesignItem2) matchMap.get(cbxMatch.
              getSelectedItem());
          itemToEdit.setDefaultSupplier((String)jComboSupplier.getSelectedItem());
          double width;
          double height;
          if (rbnOutside.isSelected()) {
            width = item.getOuterWidth();
            height = item.getOuterHeight();
            itemToEdit.setMatchOutside(true);
          }
          else {
            width = item.getInnerWidth();
            height = item.getInnerHeight();
            itemToEdit.setMatchOutside(false);
          }
          itemToEdit.setLeftGap(width / 2);
          itemToEdit.setRightGap(width / 2);
          itemToEdit.setTopGap(height / 2);
          itemToEdit.setBottomGap(height / 2);
          itemToEdit.setMatchToItem(item);
          hide();
          canceled = false;
        }
        catch (NumberFormatException x) {
          String mes = "Invalid value was specified";
          JOptionPane.showMessageDialog(thisDialog, mes, "Error",
                                        JOptionPane.ERROR_MESSAGE);
        }
        catch (NullPointerException x) {
          String mes = "An item must be selected.";
          JOptionPane.showMessageDialog(thisDialog, mes, "Error",
                                        JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        canceled = true;
        hide();
      }
    });

    try {
      if (! ( (String) cbxItemCode.getSelectedItem()).equals("") &&
          ! ( (String) cbxItemCode.getSelectedItem()).equals(null)) {

        populateSupplierCombo();
      }
    }
    catch (NullPointerException e) {
      // e.printStackTrace();
    }

    setTitle(title);
    setSize(300, 280);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width / 2 - this.getWidth() / 2,
                screenSize.height / 2 - this.getHeight() / 2);
    setVisible(true);
  }

  private void updateValues() {
    cbxItemCode.setSelectedItem(itemToEdit.getItemCode());
    cbxOwnCode.setSelectedItem(itemToEdit.getOwnCode());

    try {
      String zapnaam = itemToEdit.getMatchToItem().getType();
      if (! (itemToEdit.getMatchToItem() instanceof DesignArtWork)) {
        zapnaam += ": " + itemToEdit.getMatchToItem().getItemCode();
      }
      cbxMatch.setSelectedItem(zapnaam);
    }
    catch (NullPointerException e) {
      //cbxMatch.setSelectedIndex(-1);
      Iterator it = itemToEdit.getItemListIterator();
      while (it.hasNext()) {
        DesignItem2 zapper = (DesignItem2) it.next();
        if (zapper instanceof DesignFrame) {
          String zapnaam = zapper.getType() + ": " + zapper.getItemCode();
          cbxMatch.setSelectedItem(zapnaam);
          itemToEdit.setMatchOutside(false);
          break;
        }
      }
    }

    if (itemToEdit.getMatchOutside()) {
      rbnOutside.setSelected(true);
    }
    else {
      rbnInside.setSelected(true);
    }
  }

  public boolean canceled() {
    return canceled;
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white,
                               new Color(148, 145, 140));
    border2 = new EtchedBorder(EtchedBorder.RAISED, Color.white,
                               new Color(148, 145, 140));
    border3 = new EtchedBorder(EtchedBorder.RAISED, Color.white,
                               new Color(148, 145, 140));
    border4 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridLayout1);
    jPanel4.setBorder(border1);
    jPanel4.setMinimumSize(new Dimension(401, 70));
    jPanel4.setPreferredSize(new Dimension(225, 70));
    jPanel4.setToolTipText("");
    jPanel4.setLayout(gridBagLayout2);
    jPanel3.setBorder(border2);
    jPanel3.setMinimumSize(new Dimension(215, 115));
    jPanel3.setPreferredSize(new Dimension(215, 115));
    jPanel3.setLayout(gridBagLayout3);
    jLabel1.setText("Item Code");
    jLabel2.setText("Own Code");
    rbnInside.setSelected(true);
    rbnInside.setText("Inside");
    rbnOutside.setText("Outside");
    btnOK.setMaximumSize(new Dimension(73, 27));
    btnOK.setMinimumSize(new Dimension(73, 27));
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    btnCancel.setText("Cancel");
    jPanel5.setLayout(gridBagLayout5);
    this.setModal(true);
    this.setResizable(false);
    jPanel5.setBorder(border4);
    jLabel4.setText("Fit to item");
    gridLayout1.setColumns(1);
    gridLayout1.setHgap(5);
    gridLayout1.setRows(2);
    gridLayout1.setVgap(5);
    jLabel3.setText("Supplier");
    jComboSupplier.addActionListener(new
        DesignDialogGBs_jComboSupplier_actionAdapter(this));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel3.add(cbxMatch, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(rbnOutside, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(5, 0, 5, 0), 0, 0));
    jPanel3.add(rbnInside, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5, 0, 5, 0), 0, 0));
    jPanel1.add(jPanel4, null);
    jPanel4.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(cbxItemCode, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 0, 5), 0, 0));
    jPanel4.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(cbxOwnCode, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 0, 5), 0, 0));
    jPanel4.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.NORTHWEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jComboSupplier, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jPanel3, null);
    this.getContentPane().add(jPanel5, BorderLayout.SOUTH);
    jPanel5.add(btnOK, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 5, 5, 5), 0, 0));
    jPanel5.add(btnCancel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5, 5, 5, 5), 0, 0));
    buttonGroup2.add(rbnInside);
    buttonGroup2.add(rbnOutside);
  }

  void populateSupplierCombo() {
    isSupplierBeingAdded = true;
    jComboSupplier.removeAllItems();

    ArrayList supplierList = artof.database.ArtofDB.getCurrentDB().
        getMaterialSuppliers( (String)this.cbxItemCode.getSelectedItem());
    for (Iterator iter = supplierList.iterator(); iter.hasNext(); ) {
      String item = (String) iter.next();
      jComboSupplier.addItem(item);
    }
    isSupplierBeingAdded = false;

    try {
      if (itemToEdit.getDefaultSupplier().equals("")) {
        itemToEdit.setDefaultSupplier( (String) jComboSupplier.getSelectedItem());
      }
      else {
        jComboSupplier.setSelectedItem(itemToEdit.getDefaultSupplier());
      }
    }
    catch (NullPointerException e) {
    }
  }

  void jComboSupplier_actionPerformed(ActionEvent e) {
    setDefaultSupplier();
    itemToEdit.refreshItem();
  }

  void setDefaultSupplier() {
    if (isSupplierBeingAdded == false) {
      itemToEdit.setDefaultSupplier( (String) jComboSupplier.getSelectedItem());
    }
  }

}

class DesignDialogGBs_jComboSupplier_actionAdapter
    implements java.awt.event.ActionListener {
  DesignDialogGBs adaptee;

  DesignDialogGBs_jComboSupplier_actionAdapter(DesignDialogGBs adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jComboSupplier_actionPerformed(e);
  }
}
