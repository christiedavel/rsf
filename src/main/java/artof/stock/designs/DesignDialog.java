package artof.stock.designs;
import artof.stock.StockItem;
import artof.database.ArtofDB;
import artof.materials.MaterialDets;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DesignDialog extends JDialog {
  private ArtofDB artofDB;
  private DesignDialog dialog = this;
  private int lastDesignID = -1;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblStock;// = new JTable();
  private JButton btnAllocate = new JButton(new ImageIcon("images/Add.gif"));
  private JPanel jPanel2 = new JPanel();
  private JButton btnClose = new JButton(new ImageIcon("images/Close.gif"));
  private JButton btnCancel = new JButton(new ImageIcon("images/Cancel.gif"));
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private Border border1;
  private JLabel jLabel3 = new JLabel();
  private int ouId = -1;
  JPanel jPanel3 = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JComboBox cbxType = new JComboBox();
  JComboBox cbxDesignID = new JComboBox();

  public DesignDialog() {
    this(null, -1);
  }

  public DesignDialog(ArrayList allocationList, int designID) {
    artofDB = ArtofDB.getCurrentDB();
    tblStock = new StockTable(new artof.stock.designs.DesignDataModel(artofDB.getStockForDesigns(MaterialDets.MAT_ALL, -1), this));

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if (isModified()) {
          String mes = "Do you want to update the new selection?";
          int i = JOptionPane.showConfirmDialog(dialog, mes, "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if (i == JOptionPane.YES_OPTION) {
            if (cbxDesignID.getSelectedItem() == null) {
              mes = "A valid design must be selected.  The selected items will not be allocated.";
              JOptionPane.showMessageDialog(dialog, mes, "Warning", JOptionPane.WARNING_MESSAGE);

            } else if (!artofDB.canDeleteOffcuts(((Integer)cbxDesignID.getSelectedItem()).intValue())) {
              mes = "Stock allocation of this design cannot be changed, because the offcuts of this design ";
              mes += "are already used in other designs.";
              JOptionPane.showMessageDialog(dialog, mes, "Warning", JOptionPane.WARNING_MESSAGE);

            } else{
              if (!allocateItems()) {
                mes = "Changes could not be saved.";
                JOptionPane.showMessageDialog(dialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
              }
            }
          }
        }
      }
    });

    ArrayList desList = artofDB.getDesignLys();
    Iterator it = desList.iterator();
    while (it.hasNext()) {
      Object[] rek = (Object[])it.next();
      cbxDesignID.insertItemAt(rek[1], 0);
    }
    cbxDesignID.setSelectedIndex(-1);

    for (int i = 0; i < MaterialDets.MAT_TYPES.length; i++) {
      cbxType.addItem(MaterialDets.MAT_TYPES[i]);
    }
    cbxType.setSelectedIndex(0);

    //Check of besig is met voorraadopname
    if (artofDB.isStockTakeInProgress()) {
      String mes = "A Stock Take is in progress.  You will not be able to make any modifications.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);

      btnAllocate.setEnabled(false);
      btnCancel.setEnabled(false);
      cbxType.setEnabled(false);
      cbxDesignID.setEnabled(false);
      tblStock.setEnabled(false);
    }

    cbxDesignID.setSelectedItem(new Integer(designID));
    lastDesignID = designID;
    ouId = designID;
    ((artof.stock.designs.DesignDataModel)tblStock.getModel()).makeSelections(allocationList);

    setTitle("Stock Items to use in Designs");
    Dimension screenSize = getToolkit().getScreenSize();
    setSize(700, 500);
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    this.setModal(true);
    this.getContentPane().setLayout(borderLayout1);
    jLabel1.setText("Material Type");
    jPanel1.setLayout(gridBagLayout1);
    btnClose.setMinimumSize(new Dimension(0, 0));
    btnClose.setPreferredSize(new Dimension(25, 25));
    btnClose.setToolTipText("Close");
    btnClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnClose_actionPerformed(e);
      }
    });
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    btnAllocate.setToolTipText("Allocates the items to the selected design");
    btnAllocate.setMargin(new Insets(2, 2, 2, 2));
    btnAllocate.setText("Allocate");
    btnAllocate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAllocate_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout2);
    btnCancel.setMinimumSize(new Dimension(0, 0));
    btnCancel.setPreferredSize(new Dimension(25, 25));
    btnCancel.setToolTipText("Cancel changes");
    jPanel2.setBorder(border1);
    jLabel3.setText("Design Code");
    jPanel3.setLayout(gridBagLayout3);
    cbxType.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        cbxType_actionPerformed(e);
      }
    });
    cbxDesignID.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        cbxDesignID_itemStateChanged(e);
      }
    });
    cbxType.setPreferredSize(new Dimension(150, 21));
    cbxDesignID.setPreferredSize(new Dimension(150, 21));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jScrollPane1,                     new GridBagConstraints(0, 1, 5, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btnAllocate,                      new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 5, 5, 10), 0, 0));
    jPanel1.add(jLabel1,            new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel3,           new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 20, 5, 0), 0, 0));
    jPanel1.add(cbxType,    new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(cbxDesignID,   new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jScrollPane1.getViewport().add(tblStock, null);
    this.getContentPane().add(jPanel2,  BorderLayout.NORTH);
    jPanel2.add(btnCancel,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(btnClose,         new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    this.getContentPane().add(jPanel3, BorderLayout.SOUTH);
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    cbxType.setSelectedIndex(0);
    cbxDesignID.setSelectedIndex(-1);
    ((DesignDataModel)tblStock.getModel()).refreshItems(artofDB.getStockForDesigns(MaterialDets.MAT_ALL, -1));
  }

  void btnAllocate_actionPerformed(ActionEvent e) {
    try {
      if (!artofDB.canDeleteOffcuts(((Integer)cbxDesignID.getSelectedItem()).intValue())) {
        String mes = "Stock allocation of this design cannot be changed, because the offcuts of this design ";
        mes += "are already used in other designs.";
        JOptionPane.showMessageDialog(dialog, mes, "Warning", JOptionPane.WARNING_MESSAGE);
        return;
      }

      if (allocateItems()) {
        String mes = "The selection was allocated successfully";
        JOptionPane.showMessageDialog(this, mes, "Allocate", JOptionPane.INFORMATION_MESSAGE);
      } else {
        String mes = "Error when allocating selection";
        JOptionPane.showMessageDialog(this, mes, "Allocate", JOptionPane.ERROR_MESSAGE);
      }
    } catch (NullPointerException x) {
      //fokkof
    }
  }

  private boolean allocateItems() {
    if (!isAllEntriesValid())
      return false;

    ArrayList items = ((DesignDataModel)tblStock.getModel()).getItemList();
    Iterator it = items.iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();
      if (item.getExitType() == -1 && item.getSelected()) {
        item.setDate(Utils.getCurrentDate());
        //item.setDesignID(((Integer)cbxDesignID.getSelectedItem()).intValue());
        item.setDesignID(ouId);
        item.setExitType(StockItem.STOCK_DESIGN);

      } else if (item.getExitType() != -1 && !item.getSelected()) {
        item.setDate(Utils.getCurrentDate());
        item.setDesignID(-1);
        item.setExitType(-1);
      }
    }
    artofDB.updateStockItems(items);
    return true;
  }

  void btnClose_actionPerformed(ActionEvent e) {
    if (isModified()) {
      String mes = "Do you want to update the new selection?";
      int i = JOptionPane.showConfirmDialog(this, mes, "Allocate", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (i == JOptionPane.YES_OPTION) {
        if (cbxDesignID.getSelectedItem() == null) {
          mes = "A valid design must be selected.";
          JOptionPane.showMessageDialog(dialog, mes, "Warning", JOptionPane.WARNING_MESSAGE);
          return;

        } else if (!artofDB.canDeleteOffcuts(((Integer)cbxDesignID.getSelectedItem()).intValue())) {
          mes = "Stock allocation of this design cannot be changed, because the offcuts of this design ";
          mes += "are already used in other designs.";
          JOptionPane.showMessageDialog(dialog, mes, "Warning", JOptionPane.WARNING_MESSAGE);

        } else {
          if (!allocateItems()) {
            mes = "Changes could not be saved.  Continue anyway?";
            i = JOptionPane.showConfirmDialog(this, mes, "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (i == JOptionPane.NO_OPTION)
              return;
          }
        }
      }
    }
    hide();
  }

  private boolean isModified() {
    Iterator it = ((DesignDataModel)tblStock.getModel()).getItemList().iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();
      if ((item.getExitType() == -1 && item.getSelected()) || (item.getExitType() != -1 && !item.getSelected()))
        return true;
    }
    return false;
  }

  private boolean isAllEntriesValid() {
    if (cbxDesignID.getSelectedIndex() == -1) {
      String mes = "Invalid Design Code specified.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  void cbxType_actionPerformed(ItemEvent e) {
    try {
      if (artofDB.canDeleteOffcuts(lastDesignID)) {
        if (isModified()) {
          String mes = "Do you want to update the new selection?";
          int res = JOptionPane.showConfirmDialog(this, mes, "Allocate", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if (res == JOptionPane.YES_OPTION) {
            allocateItems();
          }
        }
      }

      int designID = ((Integer)cbxDesignID.getSelectedItem()).intValue();
      ouId = designID;
      int type = MaterialDets.getItemTypeInt((String)cbxType.getSelectedItem());
      ((DesignDataModel)tblStock.getModel()).refreshItems(artofDB.getStockForDesigns(type, designID));
      lastDesignID = designID;

    } catch (NullPointerException x) {
      int type = MaterialDets.getItemTypeInt((String)cbxType.getSelectedItem());
      ((DesignDataModel)tblStock.getModel()).refreshItems(artofDB.getStockForDesigns(type, -1));
      lastDesignID = -1;
    }
  }

  void cbxDesignID_itemStateChanged(ItemEvent e) {
    cbxType_actionPerformed(e);
  }

  public int getSelectedDesignID() {
    try {
      return ((Integer)cbxDesignID.getSelectedItem()).intValue();
    } catch (Exception e) {
      return -1;
    }
  }
}