package artof.dialogs;

import artof.Designs;
import artof.utils.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;
import javax.swing.table.*;
import java.awt.Component;
import javax.swing.event.*;
import java.util.EventObject;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class OpenDesign extends JDialog {
  private DataModel dataModel;
  private ArrayList selectedList;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JButton btnOpen = new JButton();
  private JPanel jPanel2 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable designTable = new JTable();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private JButton btnCancel = new JButton();
  private Border border1;
  private JPanel jPanel3 = new JPanel();
  private JLabel jLabel1 = new JLabel();
  private JRadioButton rbnCode = new JRadioButton();
  private ButtonGroup buttonGroup1 = new ButtonGroup();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private JRadioButton rbnTitle = new JRadioButton();
  private JRadioButton rbnDate = new JRadioButton();
  private JLabel jLabel2 = new JLabel();
  private JCheckBox ckQuotation = new JCheckBox();
  private JCheckBox cInOrder = new JCheckBox();
  private JCheckBox ckCompleted = new JCheckBox();
  private Border border2;

  public OpenDesign(ArrayList designLys, String title) {
    dataModel = new DataModel(designLys, DataModel.ORDER_CODE);
    designTable.setModel(dataModel);
    TableColumnModel tcm = designTable.getColumnModel();
    tcm.getColumn(0).setCellEditor(new TableCheckBox());
    tcm.getColumn(0).setCellRenderer(new TableCheckBox());
    tcm.getColumn(0).setPreferredWidth(20);
    tcm.getColumn(1).setPreferredWidth(40);
    tcm.getColumn(2).setPreferredWidth(150);

    setSize(600, 400);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setModal(true);
    setTitle(title);

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    setVisible(true);
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),BorderFactory.createEmptyBorder(5,5,5,5));
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    this.getContentPane().setLayout(borderLayout1);
    btnOpen.setPreferredSize(new Dimension(80, 27));
    btnOpen.setText("Select");
    btnOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOpen_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout1);
    jPanel1.setLayout(gridBagLayout2);
    btnCancel.setPreferredSize(new Dimension(80, 27));
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    jPanel1.setBorder(border1);
    borderLayout1.setHgap(5);
    borderLayout1.setVgap(5);
    designTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    jLabel1.setText("Order by");
    rbnCode.setSelected(true);
    rbnCode.setText("Code");
    rbnCode.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rbnCode_actionPerformed(e);
      }
    });
    jPanel3.setLayout(gridBagLayout3);
    rbnTitle.setText("Title");
    rbnTitle.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rbnTitle_actionPerformed(e);
      }
    });
    rbnDate.setText("Date");
    rbnDate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rbnDate_actionPerformed(e);
      }
    });
    jLabel2.setText("Show only Status");
    ckQuotation.setSelected(true);
    ckQuotation.setText("Quotation");
    ckQuotation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ckQuotation_actionPerformed(e);
      }
    });
    cInOrder.setSelected(true);
    cInOrder.setText("In Order");
    cInOrder.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cInOrder_actionPerformed(e);
      }
    });
    ckCompleted.setSelected(true);
    ckCompleted.setText("Completed");
    ckCompleted.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ckCompleted_actionPerformed(e);
      }
    });
    jPanel3.setBorder(border2);
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(btnOpen,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jScrollPane1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(jPanel3,  BorderLayout.WEST);
    jPanel3.add(jLabel1,           new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    jPanel3.add(rbnCode,                new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jScrollPane1.getViewport().add(designTable, null);
    jPanel1.add(btnCancel,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    buttonGroup1.add(rbnCode);
    buttonGroup1.add(rbnDate);
    buttonGroup1.add(rbnTitle);
    jPanel3.add(rbnTitle,                new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(rbnDate,               new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(jLabel2,            new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    jPanel3.add(ckQuotation,          new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(cInOrder,       new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(ckCompleted,        new GridBagConstraints(0, 8, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    selectedList = null;
    hide();
  }

  void btnOpen_actionPerformed(ActionEvent e) {
    selectedList = dataModel.getSelectedDesigns();
    hide();
  }

  public ArrayList getSelectedDesigns() {
    return selectedList;
  }

  void rbnCode_actionPerformed(ActionEvent e) {
    dataModel.setNewOrder(DataModel.ORDER_CODE);
  }

  void rbnTitle_actionPerformed(ActionEvent e) {
    dataModel.setNewOrder(DataModel.ORDER_TITLE);
  }

  void rbnDate_actionPerformed(ActionEvent e) {
    dataModel.setNewOrder(DataModel.ORDER_DATE);
  }

  void ckQuotation_actionPerformed(ActionEvent e) {
    if (ckQuotation.isSelected())
      dataModel.setStatusValue(Designs.DESIGN_STATUS[Designs.DES_STATUS_QUOT], true);
    else
      dataModel.setStatusValue(Designs.DESIGN_STATUS[Designs.DES_STATUS_QUOT], false);
  }

  void cInOrder_actionPerformed(ActionEvent e) {
    if (cInOrder.isSelected())
      dataModel.setStatusValue(Designs.DESIGN_STATUS[Designs.DES_STATUS_ORDER], true);
    else
      dataModel.setStatusValue(Designs.DESIGN_STATUS[Designs.DES_STATUS_ORDER], false);
  }

  void ckCompleted_actionPerformed(ActionEvent e) {
    if (ckCompleted.isSelected())
      dataModel.setStatusValue(Designs.DESIGN_STATUS[Designs.DES_STATUS_COMP], true);
    else
      dataModel.setStatusValue(Designs.DESIGN_STATUS[Designs.DES_STATUS_COMP], false);
  }

  public int getFirstSelectedIndex() {
    return dataModel.getFirstSelectedItemIndex();
  }
}


/*--------------------------------- DataModel -----------------------------------*/

class DataModel extends DefaultTableModel {
  public static int ORDER_CODE = 1;
  public static int ORDER_TITLE = 2;
  public static int ORDER_DATE = 3;
  public static int ORDER_STATUS = 4;

  private ArrayList itemList;
  private ArrayList sourceList;
  private HashMap statusMap = new HashMap();
  private int curOrderNo;

  public DataModel(ArrayList items, int orderNo) {
    sourceList = items;
    for (int i = 0; i < Designs.DESIGN_STATUS.length; i++)
      statusMap.put(Designs.DESIGN_STATUS[i], new Boolean(true));
    setNewOrder(orderNo);
  }

  public ArrayList getSelectedDesigns() {
    ArrayList selectedItems = new ArrayList();
    Iterator it = itemList.iterator();
    while (it.hasNext()) {
      Object[] rekord = (Object[])it.next();
      if (((Boolean)rekord[0]).booleanValue())
        selectedItems.add(rekord[1]);
    }
    return selectedItems;
  }

  public int getFirstSelectedItemIndex() {
    Iterator it = itemList.iterator();
    while (it.hasNext()) {
      Object[] rekord = (Object[])it.next();
      if (((Boolean)rekord[0]).booleanValue())
        return itemList.indexOf(rekord);
    }
    return -1;
  }

  public String getColumnName(int column) {
    if (column == 1) return "Code";
    else if (column == 2) return "Title";
    else if (column == 3) return "Date";
    else if (column == 4) return "Status";
    else return "";
  }

  public void setValueAt(Object aValue, int row, int column) {
    try {
      Object[] rekord = (Object[])itemList.get(row);
      if (column == 0) {
        rekord[0] = aValue;
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      // doen fokkol
    } catch (NullPointerException e) {
      // doen fokkol
    }
  }

  public boolean isCellEditable(int row, int column) {
    if (column == 0) return true;
    else return false;
  }

  public Object getValueAt(int row, int column) {
    try {
      Object[] rekord = (Object[])itemList.get(row);
      if (column == 3)
        return Utils.getDatumStr(((Integer)rekord[column]).intValue());
      else
        return rekord[column];
    } catch (IndexOutOfBoundsException e) {
      return null;
    } catch (NullPointerException e) {
      return null;
    }
  }

  public int getColumnCount() {
    return 5;
  }

  public int getRowCount() {
    try {
      return itemList.size();
    } catch (NullPointerException e) {
      return 0;
    }
  }

  public boolean getStatusValue(String status) {
    try {
      return ((Boolean)statusMap.get(status)).booleanValue();
    } catch (NullPointerException e) {
      return false;
    }
  }
  public void setStatusValue(String status, boolean val) {
    statusMap.remove(status);
    statusMap.put(status, new Boolean(val));
    setNewOrder(curOrderNo);
  }

  public void setNewOrder(int orderNo) {
    if (orderNo < ORDER_CODE || orderNo > ORDER_DATE)
      orderNo = ORDER_CODE;

    curOrderNo = orderNo;
    itemList = new ArrayList();
    Iterator it = sourceList.iterator();
    while (it.hasNext()) {
      Object[] rekord = (Object[])it.next();
      if (!getStatusValue((String)rekord[ORDER_STATUS]))
        continue;

      int index = -1;
      for (int i = 0; i < itemList.size(); i++) {
        Object[] insertedRekord = (Object[])itemList.get(i);

        if ((orderNo == ORDER_CODE || orderNo == ORDER_DATE) &&
            ((Integer)rekord[orderNo]).intValue() > ((Integer)insertedRekord[orderNo]).intValue()) {
          index = i;
          break;
        } else if (orderNo == ORDER_TITLE && ((String)rekord[orderNo]).compareTo((String)insertedRekord[orderNo]) >= 0) {
          index = i;
          break;
        }
      }
      if (index == -1)
        index = itemList.size();

      itemList.add(index, rekord);
    }

    fireTableDataChanged();
  }

}


/*--------------------------------- CheckBox ------------------------------------*/

class TableCheckBox extends JCheckBox implements TableCellEditor, TableCellRenderer {
  protected EventListenerList listenerList = new EventListenerList();
  protected ChangeEvent changeEvent = new ChangeEvent(this);

  public TableCheckBox() {
    super();
    setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

  public Object getCellEditorValue() {
    return new Boolean(isSelected());
  }

  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public boolean stopCellEditing() {
    fireEditingStopped();
    return true;
  }

  public boolean isCellEditable(EventObject event) {
    return true;
  }

  public boolean shouldSelectCell(EventObject event) {
    return true;
  }

  public void addCellEditorListener(CellEditorListener listener) {
    listenerList.add(CellEditorListener.class, listener);
  }

  public void removeCellEditorListener(CellEditorListener listener) {
    listenerList.remove(CellEditorListener.class, listener);
  }

  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    if (value != null)
      setSelected(((Boolean)value).booleanValue());
    return this;
  }

  protected void fireEditingStopped() {
    CellEditorListener listener;
    Object[] listeners = listenerList.getListenerList();
    for (int i = 0; i < listeners.length; i++) {
      if (listeners[i] == CellEditorListener.class) {
        listener = (CellEditorListener) listeners[i + 1];
        listener.editingStopped(changeEvent);
      }
    }
  }

  protected void fireEditingCanceled() {
    CellEditorListener listener;
    Object[] listeners = listenerList.getListenerList();
    for (int i = 0; i < listeners.length; i++) {
      if (listeners[i] == CellEditorListener.class) {
        listener = (CellEditorListener) listeners[i + 1];
        listener.editingCanceled(changeEvent);
      }
    }
  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      super.setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(table.getBackground());
    }
    if (value == null)
      setSelected(false);
    else
      setSelected(((Boolean)value).booleanValue());
    return this;
  }
}

