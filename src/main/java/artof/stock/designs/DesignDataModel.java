package artof.stock.designs;
import artof.materials.MaterialDets;
import artof.stock.StockItem;
import artof.utils.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DesignDataModel extends DefaultTableModel {
  private ArrayList itemList = new ArrayList();
  private DesignDialog dialog;

  public DesignDataModel(ArrayList items, DesignDialog d) {
    itemList = items;
    dialog = d;
  }

  public void makeSelections(ArrayList selectedList) {
    if (selectedList != null) {
      Iterator it = itemList.iterator();
      while (it.hasNext()) {
        StockItem item = (StockItem)it.next();
        Iterator it2 = selectedList.iterator();
        while (it2.hasNext()) {
          StockItem item2 = (StockItem)it2.next();
          if (item.getStockID() == item2.getStockID())
            item.setSelected(true);
        }
      }
    }
  }

  public void refreshItems(ArrayList items) {
    itemList = items;
    fireTableDataChanged();
  }

  public ArrayList getItemList() {
    return itemList;
  }

  public int getColumnCount() {
    return 7;
  }

  public String getColumnName(int column) {
    if (column == 0) return "Use";
    else if (column == 1) return "Stock ID";
    else if (column == 2) return "Item Code";
    else if (column == 3) return "Supplier";
    else if (column == 4) return "Type";
    else if (column == 5) return "Length";
    else if (column == 6) return "Width";
    else return null;
  }

  public int getRowCount() {
    try {
      return itemList.size();
    } catch (NullPointerException e) {
      return 0;
    }
  }

  public Object getValueAt(int row, int column) {
    StockItem item = (StockItem)itemList.get(row);

    if (column == 0) return new Boolean(item.getSelected());
    else if (column == 1) return String.valueOf(item.getStockID());
    else if (column == 2) return item.getItemCode();
    else if (column == 3) return item.getSupplier();
    else if (column == 4) return MaterialDets.getItemTypeString(item.getMatType());
    else if (column == 5) return Utils.getNumberFormat(item.getLength());
    else if (column == 6) return Utils.getNumberFormat(item.getWidth());
    else return null;
  }

  public void setValueAt(Object aValue, int row, int column) {
    try {
      StockItem item = (StockItem)itemList.get(row);

      if (column == 0) {
        item.setSelected(((Boolean)aValue).booleanValue());

      } /*else if (column == 1) {
        item.setItemCode((String)aValue);

      } else if (column == 2) {
        item.setSupplier((String)aValue);

      } else if (column == 3) {
        item.setMatType(MaterialDets.getItemTypeInt((String)aValue));

      } else if (column == 4) {
        item.setLength(Float.parseFloat((String)aValue));

      } else if (column == 5) {
        item.setWidth(Float.parseFloat((String)aValue));

      } else if (column == 6) {
        item.setCount(Integer.parseInt((String)aValue));

      }*/

    } catch (java.lang.NumberFormatException e) {
      String mes = "Invalid value was specified.";
      JOptionPane.showMessageDialog(dialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
    } catch (IndexOutOfBoundsException e) {
      //doen fokkol
    }
  }

  public void insertRow(int row, Object[] rowData) {
    StockItem item = new StockItem();
    itemList.add(item);
    fireTableDataChanged();
  }

  public void removeRow(int row) {
    try {
      /*StockItem item = (StockItem)itemList.get(row);
      if (item.getStockID() > -1) {
        String mes = "Are you sure you want to delete the item?";
        int res = JOptionPane.showConfirmDialog(dialog, mes, "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
          ArtofDB db = ArtofDB.getCurrentDB();
          Iterator it = item.getBlyListIterator();
          while (it.hasNext()) {
            db.deleteStockItem(((Integer)it.next()).intValue());
          }
          db.deleteStockItem(item.getStockID());
        }
      }*/
      itemList.remove(row);
    } catch (IndexOutOfBoundsException e) {
      //doen fokkol
    }
    fireTableDataChanged();
  }

  public boolean isCellEditable(int row, int column) {
    if (dialog.getSelectedDesignID() == -1)
      return false;
    else if (column == 0)
      return true;
    else
      return false;
  }

  public Class getColumnClass(int column) {
    Class dataType = super.getColumnClass(column);
    if (column == 0) {
      dataType = Boolean.class;
    }
    return dataType;
  }
}