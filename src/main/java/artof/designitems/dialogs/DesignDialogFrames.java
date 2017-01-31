package artof.designitems.dialogs;

import artof.designitems.DesignItem2;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import artof.database.*;
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

public class DesignDialogFrames extends JDialog {
  private JDialog thisDialog;
  private DesignItem2 itemToEdit;
  private boolean canceled = true;
  private GridBagLayout gridBagLayout5 = new GridBagLayout();
  private JPanel jPanel5 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JPanel jPanel1 = new JPanel();
  private JButton btnColor = new JButton();
  private JPanel pnlDetails = new JPanel();
  private JButton btnCancel = new JButton();
  private CustomTextField txtRebate = new CustomTextField();
  private JComboBox cbxOwnCode = new JComboBox();
  private JComboBox cbxItemCode = new JComboBox();
  private JCheckBox ckShowOffsets = new JCheckBox();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JButton btnOK = new JButton();
  private JLabel jLabel1 = new JLabel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private CustomTextField txtTopOffset = new CustomTextField();
  private JCheckBox ckMatchOffsets = new JCheckBox();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private JPanel jPanel2 = new JPanel();
  private CustomTextField txtRightOffset = new CustomTextField();
  private CustomTextField txtBottomOffset = new CustomTextField();
  private CustomTextField txtLeftOffset = new CustomTextField();
  private JLabel jLabel8 = new JLabel();
  private JLabel jLabel7 = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private Border border1;
  private Border border3;
  private Border border4;
  private ArtofDB db = null;
  JLabel jLabel9 = new JLabel();
  SamplePanel pnlSample = new SamplePanel();
  Border border2;
  JLabel jLabel10 = new JLabel();
  JComboBox jComboSupplier = new JComboBox();
  boolean isSupplierBeingAdded = false;


  public DesignDialogFrames(String title, int type, DesignItem2 item) {
    thisDialog = this;
    itemToEdit = item;
    db = ArtofDB.getCurrentDB();
    setSize(400, 215);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setResizable(false);
    setTitle(title);
    setModal(true);

    cbxItemCode = item.getCodeMapper().getItemCodeComboBox();
    cbxOwnCode = item.getCodeMapper().getOwnCodeComboBox();
    txtRebate.setText(Utils.getCurrencyFormat(itemToEdit.getThickness()));
    btnColor.setBackground(itemToEdit.getColor());
    pnlSample.setItemImage(itemToEdit.getItemCode());

    ckMatchOffsets.setSelected(itemToEdit.getMatchOffsets());
    ckMatchOffsets.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateOffsets();
      }
    });

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
          if (itemToEdit.getCodeMapper().getRebateFromItemCode(itemToEdit.getItemCode()) <= 0.5) {
            String mes = "This frame has not been used in RSF previously.\n Please measure the rebate of the frame and enter the correct measurement.\n This procedure is only required when frames are used for the first time.";
            JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          itemToEdit.setDefaultSupplier((String)jComboSupplier.getSelectedItem());
          itemToEdit.setThickness(Double.parseDouble(txtRebate.getText()));
          itemToEdit.setColor(btnColor.getBackground());
          setValues();
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

    txtRebate.addFocusListener(new FocusAdapter() {
      public void focusLost(FocusEvent e) {
        try {
          String itemCode = (String) cbxItemCode.getSelectedItem();
          itemToEdit.getCodeMapper().updateItemId(itemCode, itemToEdit.getCodeMapper().getIdFromItemCode(itemCode));
          db.saveMaterialRebate((itemToEdit.getCodeMapper().getIdFromItemCode(itemCode)).intValue(), Float.valueOf(txtRebate.getText()).floatValue());
          itemToEdit.getCodeMapper().updateItemRebate(itemCode, Float.valueOf(txtRebate.getText()));

        } catch(Exception ee) {
          //ee.printStackTrace();
          //???
        }
      }
    });

    getRootPane().setDefaultButton(btnOK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    try {
      if (!((String)cbxItemCode.getSelectedItem()).equals("") &&
          !((String)cbxItemCode.getSelectedItem()).equals(null)) {
        populateSupplierCombo();
      }
    } catch (NullPointerException e) {
     // e.printStackTrace();
    }

    setVisible(true);
  }

  public boolean canceled() {
      return canceled;
  }

  private void updateOffsets() {
    txtTopOffset.setText(Utils.getCurrencyFormat(itemToEdit.getLeftOffset()));
    if (ckMatchOffsets.isSelected()) {
      txtRightOffset.setEnabled(false);
      txtLeftOffset.setEnabled(false);
      txtBottomOffset.setEnabled(false);
      txtRightOffset.setText(null);
      txtLeftOffset.setText(null);
      txtBottomOffset.setText(null);

      txtRightOffset.setBackground(Color.gray);
      txtLeftOffset.setBackground(Color.gray);
      txtBottomOffset.setBackground(Color.gray);
    } else {
      txtRightOffset.setEnabled(true);
      txtLeftOffset.setEnabled(true);
      txtBottomOffset.setEnabled(true);
      txtRightOffset.setText(Utils.getCurrencyFormat(itemToEdit.getRightOffset()));
      txtLeftOffset.setText(Utils.getCurrencyFormat(itemToEdit.getTopOffset()));
      txtBottomOffset.setText(Utils.getCurrencyFormat(itemToEdit.getBottomOffset()));

      txtRightOffset.setBackground(Color.white);
      txtLeftOffset.setBackground(Color.white);
      txtBottomOffset.setBackground(Color.white);
    }
  }

  private void updateValues() {
    cbxItemCode.setSelectedItem(itemToEdit.getItemCode());
    cbxOwnCode.setSelectedItem(itemToEdit.getOwnCode());
    txtRebate.setText(Utils.getCurrencyFormat(itemToEdit.getCodeMapper().getRebateFromItemCode(itemToEdit.getItemCode())));
    btnColor.setBackground(itemToEdit.getColor());
    updateOffsets();
  }

  private void setValues() throws NumberFormatException {
    if (ckShowOffsets.isSelected()) {
      itemToEdit.setTopOffset(Double.parseDouble(txtTopOffset.getText()));
      if (ckMatchOffsets.isSelected()) {
        itemToEdit.setMatchOffsets(true);
        itemToEdit.setRightOffset(Double.parseDouble(txtTopOffset.getText()));
        itemToEdit.setLeftOffset(Double.parseDouble(txtTopOffset.getText()));
        itemToEdit.setBottomOffset(Double.parseDouble(txtTopOffset.getText()));
      } else {
        itemToEdit.setMatchOffsets(false);
        itemToEdit.setRightOffset(Double.parseDouble(txtRightOffset.getText()));
        itemToEdit.setLeftOffset(Double.parseDouble(txtLeftOffset.getText()));
        itemToEdit.setBottomOffset(Double.parseDouble(txtBottomOffset.getText()));
      }
    }
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
    border3 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border4 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    jPanel5.setLayout(gridBagLayout5);
    jPanel1.setLayout(gridBagLayout1);
    pnlDetails.setLayout(gridBagLayout2);
    btnCancel.setText("Cancel");
    ckShowOffsets.setText("Floating Frame");
    ckShowOffsets.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ckShowOffsets_actionPerformed(e);
      }
    });
    jLabel4.setText("Color");
    jLabel3.setText("Rebate");
    jLabel2.setText("Own Code");
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    jLabel1.setText("Item Code");
    ckMatchOffsets.setSelected(true);
    ckMatchOffsets.setText("Match All");
    jPanel2.setLayout(gridBagLayout3);
    jLabel8.setText("Bottom Offset");
    jLabel7.setText("Top Offset");
    jLabel6.setText("Right Offset");
    jLabel5.setText("Left Offset");
    pnlDetails.setBorder(border1);
    jPanel2.setBorder(border3);
    jLabel9.setText("Frame sample");
    pnlSample.setBorder(border2);
    jLabel10.setText("Supplier");
    jComboSupplier.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jComboSupplier_actionPerformed(e);
      }
    });
    this.getContentPane().add(jPanel5,  BorderLayout.SOUTH);
    jPanel5.add(btnOK, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel5.add(btnCancel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    this.getContentPane().add(jPanel1,  BorderLayout.CENTER);
    jPanel1.add(pnlDetails,         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(jLabel1,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(cbxItemCode,     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(jLabel2,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(cbxOwnCode,    new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(jLabel3,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(txtRebate,    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(jLabel4,    new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(btnColor,   new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 10));
    pnlDetails.add(ckShowOffsets,      new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
    pnlDetails.add(jLabel10,  new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(jLabel9, new GridBagConstraints(0, 2, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlDetails.add(pnlSample,   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 40, 40));
    pnlDetails.add(jComboSupplier,   new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
 //   jPanel1.add(jPanel2,    new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
 //           ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(ckMatchOffsets, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtTopOffset, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtLeftOffset, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel8, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtBottomOffset, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtRightOffset, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }

  void ckShowOffsets_actionPerformed(ActionEvent e) {
    if (ckShowOffsets.isSelected()) {
      jPanel1.add(jPanel2,    new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      setSize(400, 330);
      ckMatchOffsets.setSelected(itemToEdit.getMatchOffsets());
      updateOffsets();
    } else {
      jPanel1.remove(jPanel2);
      setSize(400, 215);
    }
    repaint();
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
