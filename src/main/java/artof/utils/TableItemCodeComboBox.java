package artof.utils;
import artof.database.ArtofDB;
import artof.materials.MaterialDets;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;
import javax.swing.event.*;
import java.util.EventObject;
import artof.dialogs.ProgressDialog;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TableItemCodeComboBox implements TableCellEditor, Runnable {
  protected ArrayList items;
  protected EventListenerList listenerList = new EventListenerList();
  protected ChangeEvent changeEvent = new ChangeEvent(this);

  protected HashMap boardMap = new HashMap();
  protected HashMap frameMap = new HashMap();
  protected HashMap gbMap = new HashMap();
  protected HashMap slipMap = new HashMap();
  protected HashMap foilMap = new HashMap();
  private JComboBox cbxCurrent;

  private ProgressDialog progressDialog;
  private Thread progressThread;

  public TableItemCodeComboBox() {
    ArtofDB artofDB = ArtofDB.getCurrentDB();
    progressDialog = new ProgressDialog("Reading materials");
    progressDialog.setLength(artofDB.getMaterialCount(MaterialDets.MAT_ALL));
    Thread progressThread = new Thread(this);
    progressThread.start();
    progressDialog.setTxtSrc("");
    progressDialog.setTxtTarget("");
    progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    progressDialog.setModal(true);
    progressDialog.setVisible(true);
  }

  public void run() {
    try {
      Thread.sleep(500);
    } catch (Exception e) {
      //haha
    }
    ArtofDB artofDB = ArtofDB.getCurrentDB();
    ArrayList suppliers = artofDB.getSuppliers();
//    ArrayList items = artofDB.getAllMaterials();
    ArrayList items = artofDB.getMaterialsProgress(MaterialDets.MAT_ALL, null, progressDialog);

    try {
      Iterator it = items.iterator();
      while (it.hasNext()) {
        MaterialDets item = (MaterialDets)it.next();
        Set supplierSet = item.getSuppliers();
        HashMap curMap = null;

        if (item.getMaterialType() == MaterialDets.MAT_BOARD)
          curMap = boardMap;
        else if (item.getMaterialType() == MaterialDets.MAT_FRAME)
          curMap = frameMap;
        else if (item.getMaterialType() == MaterialDets.MAT_GB)
          curMap = gbMap;
        else if (item.getMaterialType() == MaterialDets.MAT_SLIP)
          curMap = slipMap;
        else if (item.getMaterialType() == MaterialDets.MAT_FOIL)
          curMap = foilMap;

        if (curMap != null) {
          Iterator supIt = supplierSet.iterator();
          while (supIt.hasNext()) {
            String supplier = (String)supIt.next();
            JComboBox cbxItemCode = (JComboBox)curMap.get(supplier);
            if (cbxItemCode == null) {
              cbxItemCode = new JComboBox();
              curMap.put(supplier, cbxItemCode);
            }
            cbxItemCode.addItem(item.getItemCode());
          }
        }
      }

    } catch (NullPointerException e) {
      JOptionPane.showConfirmDialog(null, "Error when initializing material selections", "Error", JOptionPane.ERROR_MESSAGE);
    }

    progressDialog.hide();
  }

  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    try {
      int currentType = MaterialDets.getItemTypeInt((String)table.getValueAt(row, column - 2));
      String supplier = (String)table.getValueAt(row, column - 1);
      HashMap curMap = null;

      if (currentType == MaterialDets.MAT_BOARD)
        curMap = boardMap;
      else if (currentType == MaterialDets.MAT_FRAME)
        curMap = frameMap;
      else if (currentType == MaterialDets.MAT_GB)
        curMap = gbMap;
      else if (currentType == MaterialDets.MAT_SLIP)
        curMap = slipMap;
      else if (currentType == MaterialDets.MAT_FOIL)
        curMap = foilMap;

      JComboBox cbxItemCode = (JComboBox)curMap.get(supplier);
      cbxItemCode.setSelectedItem(value);
      cbxCurrent = cbxItemCode;
      return cbxItemCode;

    } catch (NullPointerException e) {
      return null;
    }
  }

  public void addCellEditorListener(CellEditorListener listener) {
    listenerList.add(CellEditorListener.class, listener);
  }

  public void removeCellEditorListener(CellEditorListener listener) {
    listenerList.remove(CellEditorListener.class, listener);
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

  public Object getCellEditorValue() {
    try {
      return cbxCurrent.getSelectedItem();
    } catch (NullPointerException e) {
      return null;
    }
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


}
