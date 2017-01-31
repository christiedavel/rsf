package artof.designer;

import artof.designitems.*;
import artof.utils.Utils;
import artof.database.BusPrefDets;
import artof.database.DesignDets;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DesignTable extends JTable {
  private LinkedList itemList;
  private Designer designer;

  public DesignTable(LinkedList list, Designer parent) {
    itemList = list;
    designer = parent;
    setModel(new DataModel());

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2) {
          DesignItem2 clonedItem = (DesignItem2)((DesignItem2)itemList.get(getSelectedRow())).clone();
          boolean canceled;
          if (getSelectedRow() == 0)
            canceled = clonedItem.showPropertyDialog(Designer.TITLE_EDIT, Designer.TYPE_MES);
          else
            canceled = clonedItem.showPropertyDialog(Designer.TITLE_EDIT, Designer.TYPE_GAP);

          if (!canceled) {
            itemList.set(getSelectedRow(), clonedItem);
            designer.setSelectedItem(clonedItem);
            designer.updateDrawingFromTable();
          }

        } else {
          designer.setSelectedItem(getSelectedRow());
          designer.repaint();
        }
      }
    });
  }

  public void fireDataChanged() {
    ((AbstractTableModel)dataModel).fireTableDataChanged();
  }

  public void setItemList(LinkedList itemList) {
    this.itemList = itemList;
    ((AbstractTableModel)dataModel).fireTableDataChanged();
  }

  public void setSelectedItem(int indeks) {
    try {
      setRowSelectionInterval(indeks, indeks);
    } catch (IllegalArgumentException e) {
      // doen niks
    }
  }


  /*------------------------------- DataModel -----------------------------------*/

  class DataModel extends AbstractTableModel {

    public String getColumnName(int column) {
      if (column == 0) return "Item";
      else if (column == 1) return "Item Code";
      else if (column == 2) return "Own Code";
      else if (column == 3) return "Width";
      else if (column == 4) return "Height";
      else if (column == 5) return "Top";
      else if (column == 6) return "Bottom";
      else if (column == 7) return "Left";
      else if (column == 8) return "Right";
      else if (column == 9) return "Amount";
      else return null;
    }

    public  boolean isCellEditable(int rowIndex, int columnIndex) {
      return false;
    }

    public Object getValueAt(int row, int col) {
      try {
        DesignItem2 item = (DesignItem2)itemList.get(row);
        if (col == 0) {
          if (item.isOversized())
            return "*" + item.getType();
          else
            return item.getType();

        } else if (col == 1) {
          return item.getItemCode();

        } else if (col == 2) {
          return item.getOwnCode();

        } else if (col == 3) {
          if (item.getDesignType() == Designer.ITEM_FRAME)
            //return Utils.getCurrencyFormat(item.getDesignWidth() - (item.getRightGap() + item.getLeftGap()) + 2*item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate());
            return Utils.getCurrencyFormat(item.getDesignWidth() - (item.getDesignRightGap() + item.getDesignLeftGap()) + 2*item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate());
          else if (item.getDesignType() == Designer.ITEM_SLIP)
            return Utils.getCurrencyFormat(item.getDesignWidth() -  2 * item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate());
          else
            return Utils.getCurrencyFormat(item.getDesignWidth());

        } else if (col == 4) {
          if (item.getDesignType() == Designer.ITEM_FRAME)
            //return Utils.getCurrencyFormat(item.getDesignHeight() - (item.getTopGap() + item.getBottomGap()) + 2*item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate());
            return Utils.getCurrencyFormat(item.getDesignHeight() - (item.getDesignTopGap() + item.getDesignBottomGap()) + 2*item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate());
          else if (item.getDesignType() == Designer.ITEM_SLIP)
            return Utils.getCurrencyFormat(item.getDesignHeight() -  2 * item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate() );
          else
            return Utils.getCurrencyFormat(item.getDesignHeight());
        }

        if (row == 0 || item instanceof DesignGlassAndBack && col != 9) {
          return null;

        } else if (col == 5) {
          return Utils.getCurrencyFormat(item.getTopGap());

        } else if (col == 6) {
          return Utils.getCurrencyFormat(item.getBottomGap());

        } else if (col == 7) {
          return Utils.getCurrencyFormat(item.getLeftGap());

        } else if (col == 8) {
          return Utils.getCurrencyFormat(item.getRightGap());

        } else if (col == 9) {
          try {
            double price = item.getDesignPrice();
            price *= BusPrefDets.getMarkupDiscount();

            DesignDets designDets = designer.getCurrentDesignDets();
            BusPrefDets busPrefs = designDets.getBusPrefs();
            if (busPrefs.getVATOwnItems().equals("Yes")) {
              price *= (1 + busPrefs.getVATPerc() / 100);
            }

            return "R " + Utils.getCurrencyFormat(price);

          } catch (Exception e) {
            return null;
          }

        } else {
          return null;
        }

      } catch (ArrayIndexOutOfBoundsException e) {
        return null;

      } catch (NullPointerException e) {
        return null;
      }
    }

    public int getColumnCount() {
      return 10;
    }

    public int getRowCount() {
      try {
        return itemList.size();
      } catch (NullPointerException e) {
        return 0;
      }
    }
  }
}
