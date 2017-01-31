package artof.stock.stocktake;
import artof.materials.MaterialDets;
import artof.stock.StockItem;
import artof.utils.Utils;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PrevStockTakeDataModel extends DefaultTableModel {
  private ArrayList itemList = new ArrayList();

  public PrevStockTakeDataModel(ArrayList items) {
    itemList = items;
  }

  public void refreshItems(ArrayList items) {
    itemList = items;
    fireTableDataChanged();
  }

  public ArrayList getItemList() {
    return itemList;
  }

  public int getColumnCount() {
    return 6;
  }

  public String getColumnName(int column) {
    if (column == 0) return "Item Code";
    else if (column == 1) return "Supplier";
    else if (column == 2) return "Type";
    else if (column == 3) return "Length";
    else if (column == 4) return "Width";
    else if (column == 5) return "No of Items";
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
    try {
      StockItem item = (StockItem)itemList.get(row);

      if (column == 0)
        return item.getItemCode();
      else if (column == 1)
        return item.getSupplier();
      else if (column == 2)
        return MaterialDets.getItemTypeString(item.getMatType());
      else if (column == 3)
        return Utils.getNumberFormat(item.getLength());
      else if (column == 4)
        return Utils.getNumberFormat(item.getWidth());
      else if (column == 5)
        return String.valueOf(item.getCount());
      else
        return null;

    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  public void setValueAt(Object aValue, int row, int column) {
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }
}