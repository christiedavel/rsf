package artof.utils;
import artof.database.ArtofDB;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;
import javax.swing.event.*;
import java.util.EventObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class TableSupplierComboBox implements TableCellEditor {
  private TableSupplierComboBoxInterface icGetter;
  private ArtofDB artofDB;
  protected EventListenerList listenerList = new EventListenerList();
  protected ChangeEvent changeEvent = new ChangeEvent(this);
  protected JComboBox cbxCode = new JComboBox();

  public TableSupplierComboBox(TableSupplierComboBoxInterface icGetter) {
    artofDB = ArtofDB.getCurrentDB();
    this.icGetter = icGetter;
  }

  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    try {
      cbxCode.removeAllItems();
      Iterator it = artofDB.getMaterialSuppliers(icGetter.getItemCodeSelected(row)).iterator();
      while (it.hasNext()) {
        cbxCode.addItem(it.next());
      }
      cbxCode.setSelectedItem(value);
      return cbxCode;
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
      return cbxCode.getSelectedItem();
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
