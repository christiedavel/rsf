package artof.stock.stocktake;
import artof.materials.MaterialDets;
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

public class DiffDataModel extends DefaultTableModel {
  private ArrayList itemList = new ArrayList();
  private DiffDialog dialog;

  public DiffDataModel(DiffDialog d) {
    dialog = d;
  }

  public void refreshItems(ArrayList items) {
    itemList = items;
    fireTableDataChanged();
  }

  public ArrayList getItemList() {
    return itemList;
  }

  public int getColumnCount() {
    return 5;
  }

  public String getColumnName(int column) {
    if (column == 0) return "Item Code";
    else if (column == 1) return "Supplier";
    else if (column == 2) return "Type";
    else if (column == 3) return "Total";
    else if (column == 4) return "Unit";
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
      String[] item = (String[])itemList.get(row);

      if (column == 0)
        return item[0];
      else if (column == 1)
        return item[1];
      else if (column == 2)
        return MaterialDets.getItemTypeString(Integer.parseInt(item[2]));
      else if (column == 3)
        return Utils.getNumberFormat(Float.parseFloat(item[3]));
      else if (column == 4)
        return item[4];
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