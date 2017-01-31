package artof.stock.orders;
import artof.stock.StockItem;
import artof.database.ArtofDB;
import artof.materials.MaterialDets;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class OrderInDialog extends JDialog implements TableSupplierComboBoxInterface {
  private ArtofDB artofDB;
  private JDialog thisDialog;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private CustomTextField txtDate = new CustomTextField();
  private JButton btnDate = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblStock;// = new JTable();
  private JButton btnAdd = new JButton(new ImageIcon("images/Add.gif"));
  private JButton btnDelete = new JButton(new ImageIcon("images/Delete2.gif"));
  private JPanel jPanel2 = new JPanel();
  private JButton btnClose = new JButton(new ImageIcon("images/Close.gif"));
  private JButton btnCancel = new JButton(new ImageIcon("images/Cancel.gif"));
  private JButton btnSave = new JButton(new ImageIcon("images/SaveDesign.gif"));
  private JButton btnNew = new JButton(new ImageIcon("images/NewDesign.gif"));
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private Border border1;
  private JLabel jLabel3 = new JLabel();
  private CustomTextField txtOrderNo = new CustomTextField();
  JPanel jPanel3 = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  JList lstIDs = new JList();
  JLabel jLabel2 = new JLabel();
  private int lastEditingRow = -1;

  public OrderInDialog() {
    artofDB = ArtofDB.getCurrentDB();
    txtDate.setText(Utils.getDatumStr(Utils.getCurrentDate()));
    TableItemCodeComboBox cbxItemCodes = new TableItemCodeComboBox();

    tblStock = new JTable(new OrderDataModel(this, cbxItemCodes)) {
      public void valueChanged(ListSelectionEvent e) {
        try {
          lastEditingRow = tblStock.getSelectedRow();
          displayStockIDs(tblStock.getSelectedRow());

        } catch (NullPointerException x) {
          // doen fokkol
        }
      }
    };
    tblStock.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    TableColumnModel tcm = tblStock.getColumnModel();
    TableEditorComboBox cbxMaterialTypes = new TableEditorComboBox(MaterialDets.MAT_TYPES);
    TableEditorComboBox cbxSuppliers = new TableEditorComboBox(artofDB.getSupplierNames());

    tcm.getColumn(0).setCellEditor(cbxMaterialTypes);
    tcm.getColumn(1).setCellEditor(cbxSuppliers);
    tcm.getColumn(2).setCellEditor(cbxItemCodes);

    tcm.getColumn(3).setCellEditor(new DefaultCellEditor(new CustomTextField()));
    tcm.getColumn(4).setCellEditor(new DefaultCellEditor(new CustomTextField()));
    tcm.getColumn(5).setCellEditor(new DefaultCellEditor(new CustomTextField()));

    tcm.getColumn(3).setCellRenderer(new NumberRenderer());
    tcm.getColumn(4).setCellRenderer(new NumberRenderer());
    tcm.getColumn(5).setCellRenderer(new NumberRenderer());

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if (isModified()) {
          String mes = "Do you want to save changes?";
          int i = JOptionPane.showConfirmDialog(thisDialog, mes, "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if (i == JOptionPane.YES_OPTION) {
            if (!saveItems()) {
              mes = "Changes could not be saved.";
              JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
            }
          }
        }
      }
    });

    //Check of besig is met voorraadopname
    if (artofDB.isStockTakeInProgress()) {
      String mes = "A Stock Take is in progress.  You will not be able to make any modifications.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);

      btnAdd.setEnabled(false);
      btnDelete.setEnabled(false);
      btnCancel.setEnabled(false);
      btnSave.setEnabled(false);
      btnNew.setEnabled(false);
      btnDate.setEnabled(false);
      txtOrderNo.setEnabled(false);
      tblStock.setEnabled(false);
    }

    setTitle("Orders In");
    Dimension screenSize = getToolkit().getScreenSize();
    setSize(730, 500);
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  private void displayStockIDs(int row) {
    ArrayList idList = ((OrderDataModel)tblStock.getModel()).getStockIDs(row);
    lstIDs.setListData(idList.toArray());
    repaint();
  }

  public String getItemCodeSelected(int row) {
    Object ding = ((OrderDataModel)tblStock.getModel()).getValueAt(row, 0);
    try {
      return (String)ding;
    } catch (ClassCastException e) {
      return null;
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    this.setModal(true);
    this.getContentPane().setLayout(borderLayout1);
    jLabel1.setText("Date");
    jPanel1.setLayout(gridBagLayout1);
    btnDate.setMargin(new Insets(0, 0, 0, 0));
    btnDate.setText("...");
    btnDate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDate_actionPerformed(e);
      }
    });
    txtDate.setMinimumSize(new Dimension(130, 21));
    txtDate.setPreferredSize(new Dimension(150, 21));
    txtDate.setEditable(false);
    btnAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAdd_actionPerformed(e);
      }
    });
    btnDelete.setToolTipText("Delete seleted stock item");
    btnDelete.setMargin(new Insets(2, 2, 2, 2));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });
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
    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });
    btnNew.setMinimumSize(new Dimension(0, 0));
    btnNew.setPreferredSize(new Dimension(25, 25));
    btnNew.setToolTipText("New Order In");
    btnNew.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNew_actionPerformed(e);
      }
    });
    btnAdd.setToolTipText("Add stock item");
    btnAdd.setMargin(new Insets(2, 2, 2, 2));
    jPanel2.setLayout(gridBagLayout2);
    btnSave.setMinimumSize(new Dimension(0, 0));
    btnSave.setPreferredSize(new Dimension(25, 25));
    btnSave.setToolTipText("Save items");
    btnSave.setMnemonic('0');
    btnCancel.setMinimumSize(new Dimension(0, 0));
    btnCancel.setPreferredSize(new Dimension(25, 25));
    btnCancel.setToolTipText("Cancel changes");
    jPanel2.setBorder(border1);
    jLabel3.setText("Doc No");
    txtOrderNo.setMinimumSize(new Dimension(130, 21));
    txtOrderNo.setPreferredSize(new Dimension(150, 21));
    jPanel3.setLayout(gridBagLayout3);
    jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jScrollPane2.setAlignmentY((float) 0.5);
    jScrollPane2.setAutoscrolls(true);
    jScrollPane1.setPreferredSize(new Dimension(800, 403));
    jLabel2.setText("Stock IDś");
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jScrollPane1,                     new GridBagConstraints(0, 1, 5, 4, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btnAdd,                      new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 5, 10), 0, 0));
    jPanel1.add(btnDelete,                     new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 10), 0, 0));
    jPanel1.add(txtDate,                  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 5, 0), 0, 0));
    jPanel1.add(btnDate,             new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel1,          new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel3,        new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 20, 5, 5), 0, 0));
    jPanel1.add(txtOrderNo,        new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane2,                    new GridBagConstraints(5, 3, 2, 1, 0.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(0, 5, 150, 5), 80, 150));
    jPanel1.add(jLabel2,  new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jScrollPane2.getViewport().add(lstIDs, null);
    jScrollPane1.getViewport().add(tblStock, null);
    this.getContentPane().add(jPanel2,  BorderLayout.NORTH);
    jPanel2.add(btnSave,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(btnCancel,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(btnNew,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(btnClose,         new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    this.getContentPane().add(jPanel3, BorderLayout.SOUTH);
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    ((OrderDataModel)tblStock.getModel()).refreshItems();
    txtOrderNo.setText("");
  }

  void btnNew_actionPerformed(ActionEvent e) {
    if (isModified()) {
      String mes = "Do you want to save changes?";
      int i = JOptionPane.showConfirmDialog(this, mes, "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (i == JOptionPane.YES_OPTION) {
        if (!saveItems()) {
          mes = "Changes could not be saved.  Continue anyway?";
          i = JOptionPane.showConfirmDialog(this, mes, "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
          if (i == JOptionPane.NO_OPTION)
            return;
        }
      }
    }

    try {
      txtDate.setText(Utils.getDatumStr(Utils.getCurrentDate()));
      txtOrderNo.setText("");
      //txtTotal.setText("");
      ((OrderDataModel)tblStock.getModel()).refreshItems();
    } catch (NullPointerException x) {
      // doen niks
    }
  }

  void btnAdd_actionPerformed(ActionEvent e) {
    if (txtDate.getText() != null && !txtDate.getText().equals(""))
      ((OrderDataModel)tblStock.getModel()).insertRow(tblStock.getSelectedRow(), new Object[0]);
  }

  void btnDelete_actionPerformed(ActionEvent e) {
    if (txtDate.getText() != null && !txtDate.getText().equals(""))
      ((OrderDataModel)tblStock.getModel()).removeRow(tblStock.getSelectedRow());
  }

  void btnSave_actionPerformed(ActionEvent e) {
    saveItems();
    displayStockIDs(lastEditingRow);
  }

  private boolean saveItems() {
    if (!isAllEntriesValid())
      return false;

    try {
      tblStock.getCellEditor().stopCellEditing();
    } catch (NullPointerException x) {
      // doen fokkol
    }
    ArrayList items = ((OrderDataModel)tblStock.getModel()).getItemList();
    Iterator it = items.iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();
      item.setDate(Utils.getDatumInt(txtDate.getText()));
      item.setEntryType(StockItem.STOCK_ORDER);
      item.setExitType(-1);
      item.setOrderID(txtOrderNo.getText());
    }
    artofDB.saveStockItems(items, true);
    return true;
  }


  void btnDate_actionPerformed(ActionEvent e) {
    DateDialog d = new DateDialog();
    if (d.getSelectedDate() != -1)
      txtDate.setText(Utils.getDatumStr(d.getSelectedDate()));
  }

  void btnClose_actionPerformed(ActionEvent e) {
    if (isModified()) {
      String mes = "Do you want to save changes?";
      int i = JOptionPane.showConfirmDialog(this, mes, "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (i == JOptionPane.YES_OPTION) {
        if (!saveItems()) {
          mes = "Changes could not be saved.  Continue anyway?";
          i = JOptionPane.showConfirmDialog(this, mes, "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
          if (i == JOptionPane.NO_OPTION)
            return;
        }
      }
    }
    hide();
  }

  private boolean isModified() {
    Iterator it = ((OrderDataModel)tblStock.getModel()).getItemList().iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();
      if (item.isModified() || item.getDate() != Utils.getDatumInt(txtDate.getText())
          || !txtOrderNo.getText().equals(String.valueOf(item.getOrderID())))
        return true;
    }
    return false;
  }

  private boolean isAllEntriesValid() {
    if (txtOrderNo.getText() == null || txtOrderNo.getText().equals("")) {
      String mes = "Invalid order no specified.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    Iterator it = ((OrderDataModel)tblStock.getModel()).getItemList().iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();
      String mes = null;
      if (item.getItemCode() == null || item.getItemCode().equals(""))
        mes = "A valid Item Code must be selected.";

      if (item.getSupplier() == null || item.getSupplier().equals(""))
        mes = "A valid Supplier must be selected.";

      else if (item.getLength() <= 0)
        mes = "A valid length must be selected.";

      else if ((item.getMatType() == MaterialDets.MAT_BOARD || item.getMatType() == MaterialDets.MAT_GB) && item.getWidth() <= 0)
        mes = "A valid width must be selected.";

      else if (item.getCount() <= 0)
        mes = "The number of items cannot be 0 or less.";
      //else if (item.getCost() <= 0)
      //  mes = "A valid cost must be specified.";

      if (mes != null) {
        JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
        return false;
      }
    }
    return true;
  }
}