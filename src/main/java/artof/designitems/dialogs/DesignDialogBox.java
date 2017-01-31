package artof.designitems.dialogs;
import artof.designitems.*;
import artof.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import artof.utils.CustomTextField;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DesignDialogBox extends JDialog {
  private DesignBox itemToEdit;
  private boolean canceled = true;
  private HashMap matchMap = new HashMap();
  private JDialog thisDialog;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JButton btnOK = new JButton();
  private GridBagLayout gridBagLayout5 = new GridBagLayout();
  private JButton btnCancel = new JButton();
  private JPanel jPanel5 = new JPanel();
  private JComboBox cbxMatch = new JComboBox();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JRadioButton rbnOutside = new JRadioButton();
  private JLabel jLabel4 = new JLabel();
  private JComboBox cbxOwnCode = new JComboBox();
  private JLabel jLabel2 = new JLabel();
  private JPanel jPanel4 = new JPanel();
  private JRadioButton rbnInside = new JRadioButton();
  private JPanel jPanel3 = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private JComboBox cbxItemCode = new JComboBox();
  private Border border1;
  private Border border2;
  private Border border3;
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel3 = new JLabel();
  private CustomTextField txtDepth = new CustomTextField();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JLabel jLabel5 = new JLabel();
  JComboBox jComboSupplier = new JComboBox();
  boolean isSupplierBeingAdded = false;

  public DesignDialogBox(String title, DesignBox item) {
    itemToEdit = item;
    thisDialog = this;
    try {
      cbxItemCode = item.getCodeMapper().getItemCodeComboBox();
      cbxOwnCode = item.getCodeMapper().getOwnCodeComboBox();

      int i = 0;
      Iterator it = itemToEdit.getItemListIterator();
      while (it.hasNext()) {
        DesignItem2 zapper = (DesignItem2)it.next();
        String zapnaam = zapper.getType();
        if (!(zapper instanceof DesignArtWork)) zapnaam += ": " + zapper.getItemCode();
        if (!(zapper instanceof DesignBox)) {
          cbxMatch.addItem(zapnaam);
          matchMap.put(zapnaam, zapper);
        }
      }
    } catch (NullPointerException x) {
      // Doen niks
    }

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    updateValues();

    cbxItemCode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          itemToEdit.setItemCode((String)cbxItemCode.getSelectedItem());
          updateValues();
          populateSupplierCombo();
          itemToEdit.refreshItem();
        } catch (NullPointerException x) {}
      }
    });

    cbxOwnCode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          itemToEdit.setOwnCode((String)cbxOwnCode.getSelectedItem());
          populateSupplierCombo();
          updateValues();
          itemToEdit.refreshItem();
        } catch (NullPointerException x) {}
      }
    });

    rbnInside.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (rbnInside.isSelected()) {
          DesignItem2 item = (DesignItem2)matchMap.get(cbxMatch.getSelectedItem());
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
          itemToEdit.setDefaultSupplier((String)jComboSupplier.getSelectedItem());
          DesignItem2 item = (DesignItem2)matchMap.get(cbxMatch.getSelectedItem());
          double width;
          double height;
          if (rbnOutside.isSelected()) {
            width = item.getOuterWidth();
            height = item.getOuterHeight();
            itemToEdit.setMatchOutside(true);
          } else {
            width = item.getOuterWidth();
            height = item.getOuterHeight();
            itemToEdit.setMatchOutside(false);
          }
          itemToEdit.setLeftGap(width / 2);
          itemToEdit.setRightGap(width / 2);
          itemToEdit.setTopGap(height / 2);
          itemToEdit.setBottomGap(height / 2);
          itemToEdit.setBoxDepth(Double.parseDouble(txtDepth.getText()));
          itemToEdit.setMatchToItem(item);
          hide();
          canceled = false;
        } catch (NumberFormatException x) {
          String mes = "Invalid value was specified";
          JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NullPointerException x) {
          String mes = "An item must be selected.";
          JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
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
      if (!((String)cbxItemCode.getSelectedItem()).equals("") &&
          !((String)cbxItemCode.getSelectedItem()).equals(null)) {

        populateSupplierCombo();
      }
    } catch (NullPointerException e) {
     // e.printStackTrace();
    }

    Dimension screenSize = getToolkit().getScreenSize();
    setSize(300, 320);
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setTitle(title);
    setVisible(true);
  }

  private void updateValues() {
    cbxItemCode.setSelectedItem(itemToEdit.getItemCode());
    cbxOwnCode.setSelectedItem(itemToEdit.getOwnCode());
    txtDepth.setText(Utils.getCurrencyFormat(itemToEdit.getBoxDepth()));

    try {
      String zapnaam = itemToEdit.getMatchToItem().getType();
      if (!(itemToEdit.getMatchToItem() instanceof DesignArtWork))
        zapnaam += ": " + itemToEdit.getMatchToItem().getItemCode();
      cbxMatch.setSelectedItem(zapnaam);
    } catch (NullPointerException e) {
      cbxMatch.setSelectedIndex(-1);
    }

    if (itemToEdit.getMatchOutside())
      rbnOutside.setSelected(true);
    else
      rbnInside.setSelected(true);
  }

  public boolean canceled() {
    return canceled;
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),BorderFactory.createEmptyBorder(5,5,5,5));
    border2 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),BorderFactory.createEmptyBorder(5,5,5,5));
    border3 = BorderFactory.createEmptyBorder(5,5,5,5);
    this.getContentPane().setLayout(borderLayout1);
    btnOK.setMaximumSize(new Dimension(73, 27));
    btnOK.setMinimumSize(new Dimension(73, 27));
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    btnCancel.setText("Cancel");
    jPanel5.setLayout(gridBagLayout5);
    jPanel1.setLayout(gridBagLayout1);
    rbnOutside.setText("Outside");
    jLabel4.setText("Fit to item");
    jLabel2.setText("Own Code");
    jPanel4.setBorder(border2);
    jPanel4.setMinimumSize(new Dimension(401, 70));
    jPanel4.setPreferredSize(new Dimension(225, 70));
    jPanel4.setToolTipText("");
    jPanel4.setLayout(gridBagLayout2);
    rbnInside.setText("Inside");
    jPanel3.setBorder(border1);
    jPanel3.setMinimumSize(new Dimension(215, 115));
    jPanel3.setPreferredSize(new Dimension(215, 115));
    jPanel3.setLayout(gridBagLayout3);
    jLabel1.setText("Item Code");
    jPanel5.setBorder(border3);
    jLabel3.setText("Box Depth");
    this.setModal(true);
    this.setResizable(false);
    jLabel5.setText("Supplier");
    jComboSupplier.addActionListener(new DesignDialogBox_jComboSupplier_actionAdapter(this));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel4.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(cbxItemCode,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
    jPanel4.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(cbxOwnCode,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
    jPanel4.add(jLabel5,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jComboSupplier,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jPanel3,     new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(cbxMatch, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(rbnOutside, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    jPanel3.add(rbnInside, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    jPanel3.add(jLabel3,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtDepth,  new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jPanel4,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(jPanel5,  BorderLayout.SOUTH);
    jPanel5.add(btnOK, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel5.add(btnCancel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    buttonGroup1.add(rbnOutside);
    buttonGroup1.add(rbnInside);



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

class DesignDialogBox_jComboSupplier_actionAdapter implements java.awt.event.ActionListener {
  DesignDialogBox adaptee;

  DesignDialogBox_jComboSupplier_actionAdapter(DesignDialogBox adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jComboSupplier_actionPerformed(e);
  }
}

