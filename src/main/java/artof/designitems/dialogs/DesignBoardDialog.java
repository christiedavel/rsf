package artof.designitems.dialogs;
import artof.designitems.*;
import artof.designitems.dialogs.boards.*;
import artof.designer.*;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import artof.materials.profiles.SamplePanel;
import java.util.ArrayList;
import java.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DesignBoardDialog extends JDialog {
  private DesignItem2 itemToEdit;
  private DesignPanelFunctions designPanelFunctions;
  private CustomOverlaps overlapPanel;
  private boolean canceled = true;
  private int type;

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JComboBox cbxItemCode = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JComboBox cbxOwnCode = new JComboBox();
  JLabel jLabel3 = new JLabel();
  CustomTextField txtDepth = new CustomTextField();
  JLabel jLabel4 = new JLabel();
  JButton btnColor = new JButton();
  Border border1;
  Border border2;
  Border border3;
  Border border4;
  JPanel jPanel5 = new JPanel();
  JButton btnCancel = new JButton();
  JButton btnOK = new JButton();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JPanel pnlDetails = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  private JPanel jPanel2 = new JPanel();
  private JCheckBox ckShowOffsets = new JCheckBox();
  private JCheckBox ckCustomOLs = new JCheckBox();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private Border border5;
  JLabel jLabel5 = new JLabel();
  SamplePanel pnlSample = new SamplePanel();
  Border border6;
  JLabel jLabel6 = new JLabel();
  JComboBox jComboSupplier = new JComboBox();
  boolean isSupplierBeingAdded = false;
  private JDialog thisDialog;

  public DesignBoardDialog(String title, int type, DesignItem2 item) {
    itemToEdit = item;
    this.type = type;
    thisDialog = this;

    cbxItemCode = item.getCodeMapper().getItemCodeComboBox();
    cbxOwnCode = item.getCodeMapper().getOwnCodeComboBox();
    btnColor.setBackground(itemToEdit.getColor());
    txtDepth.setText(Utils.getCurrencyFormat(itemToEdit.getThickness()));
    pnlSample.setItemImage(itemToEdit.getItemCode());

    getRootPane().setDefaultButton(btnOK);
    try {
      jbInit();

    } catch(Exception e) {
      e.printStackTrace();
    }

    overlapPanel = new CustomOverlaps((DesignBoard)itemToEdit);
    if (type == Designer.TYPE_MES) {
      DesignPanel2 designPanel2 = new DesignPanel2(itemToEdit);
      designPanelFunctions = designPanel2;
      pnlDetails.add(designPanel2, BorderLayout.CENTER);
      ckShowOffsets.setVisible(false);
      ckCustomOLs.setVisible(false);
      setSize(400, 250);
    } else {
      DesignPanel designPanel = new DesignPanel(itemToEdit);
      designPanelFunctions = designPanel;
      pnlDetails.add(designPanel, BorderLayout.CENTER);
      setSize(400, 330);

      if (((DesignBoard)itemToEdit).getUseCustomOverlaps()) {
        ckCustomOLs.setSelected(true);
        pnlDetails.add(overlapPanel, BorderLayout.SOUTH);
        setSize(400, 430);
      }
    }

    cbxItemCode.setSelectedItem(itemToEdit.getItemCode());
    cbxItemCode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        itemCodeAction();
      }
    });

    cbxOwnCode.setSelectedItem(itemToEdit.getOwnCode());
    cbxOwnCode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ownCodeAction();
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
          itemToEdit.setDefaultSupplier((String)jComboSupplier.getSelectedItem());
          designPanelFunctions.setValues();
          itemToEdit.setThickness(Double.parseDouble(txtDepth.getText()));
          itemToEdit.setColor(btnColor.getBackground());
          ((DesignBoard)itemToEdit).setUseCustomOverlaps(ckCustomOLs.isSelected());
          overlapPanel.setValues();
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
        if (cbxItemCode.getSelectedIndex() > -1) {
          try {
            Color newColor = new JColorChooser().showDialog(null,
                "Display color", btnColor.getBackground());
            if (!newColor.equals(btnColor.getBackground())) {
              btnColor.setBackground(newColor);
              artof.database.ArtofDB.getCurrentDB().updateMaterialColor( (
                  String) cbxItemCode.getSelectedItem(), newColor);
            }
          } catch (NullPointerException ee) {
            //doen niks
          }
        }
      }
    });

    try {
      if (!((String)cbxItemCode.getSelectedItem()).equals("") &&
          !((String)cbxItemCode.getSelectedItem()).equals(null)) {
        populateSupplierCombo();
      }
    } catch (NullPointerException e) {
     // e.printStackTrace();
    }


    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setResizable(false);
    setTitle(title);
    setModal(true);
    setVisible(true);
  }

  public boolean canceled() {
    return canceled;
  }

  private void updateValues() {
    cbxItemCode.setSelectedItem(itemToEdit.getItemCode());
    cbxOwnCode.setSelectedItem(itemToEdit.getOwnCode());
    txtDepth.setText(Utils.getCurrencyFormat(itemToEdit.getThickness()));
    btnColor.setBackground(itemToEdit.getColor());
    designPanelFunctions.updateValues(itemToEdit);
    overlapPanel.updateValues((DesignBoard)itemToEdit);
  }

  // Interface funksies
  protected void itemCodeAction() {
    try {
      itemToEdit.setItemCode((String)cbxItemCode.getSelectedItem());
      updateValues();
      itemToEdit.refreshItem();
      populateSupplierCombo();
      pnlSample.setItemImage((String)cbxItemCode.getSelectedItem());

    } catch (NullPointerException e) {
      pnlSample.setItemImage(null);
    }
  }

  protected void ownCodeAction() {
    try {
      itemToEdit.setOwnCode((String)cbxOwnCode.getSelectedItem());
      updateValues();
      itemToEdit.refreshItem();
      populateSupplierCombo();
      pnlSample.setItemImage((String)cbxItemCode.getSelectedItem());

    } catch (NullPointerException e) {
      pnlSample.setItemImage(null);
    }
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = BorderFactory.createEmptyBorder();
    border3 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border4 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border5 = BorderFactory.createEmptyBorder(5,5,5,5);
    border6 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    jLabel1.setText("Item Code");
    jLabel2.setText("Own Code");
    jLabel3.setText("Thickness");
    jLabel4.setText("Color");
    jPanel1.setBorder(border2);
    btnCancel.setText("Cancel");
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    jPanel5.setLayout(gridBagLayout5);
    pnlDetails.setLayout(borderLayout2);
    txtDepth.setEditable(false);
    ckShowOffsets.setText("Floating Board");
    ckShowOffsets.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ckShowOffsets_actionPerformed(e);
      }
    });
    ckCustomOLs.setText("Custom Overlaps");
    ckCustomOLs.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ckCustomOLs_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout2);
    pnlDetails.setBorder(border5);
    jLabel5.setText("Board sample");
    pnlSample.setBorder(border6);
    jLabel6.setText("Supplier");
    jComboSupplier.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboSupplier_actionPerformed(e);
      }
    });
    this.getContentPane().add(jPanel1,  BorderLayout.CENTER);
    jPanel1.add(jLabel1,       new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(cbxItemCode,      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel2,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(cbxOwnCode,       new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel3,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    jPanel1.add(txtDepth,        new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel4,        new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btnColor,         new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 10));
    jPanel1.add(pnlDetails,       new GridBagConstraints(0, 4, 4, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jPanel2,    new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(ckShowOffsets,      new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(ckCustomOLs,      new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel5,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(pnlSample,   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 40, 40));
    jPanel1.add(jLabel6,  new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jComboSupplier,  new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(jPanel5, BorderLayout.SOUTH);
    jPanel5.add(btnOK,          new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel5.add(btnCancel,     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

  }

  void ckShowOffsets_actionPerformed(ActionEvent e) {
    if (ckShowOffsets.isSelected()) {
      designPanelFunctions.showOffsets(true);
      setSize(400, getHeight() + 140);
    }
    else {
      designPanelFunctions.showOffsets(false);
      setSize(400, getHeight() - 140);
    }
    this.repaint();
  }

  void ckCustomOLs_actionPerformed(ActionEvent e) {
    if (ckCustomOLs.isSelected()) {
      overlapPanel.updateValues((DesignBoard)itemToEdit);
      pnlDetails.add(overlapPanel, BorderLayout.SOUTH);
      setSize(400, getHeight() + 140);
    }
    else {
      pnlDetails.remove(overlapPanel);
      setSize(400, getHeight() - 140);
    }
    this.repaint();
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
    itemCodeAction();
  }

  void setDefaultSupplier() {
    if (isSupplierBeingAdded == false)
      itemToEdit.setDefaultSupplier((String)jComboSupplier.getSelectedItem());
  }
}
