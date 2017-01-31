package artof.stock;
import artof.database.DBItem;
import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class StockItem extends DBItem  implements Cloneable {
  public final static int STOCK_ORDER = 1;
  public final static int STOCK_OFFCUT = 2;
  public final static int STOCK_SALE = 3;
  public final static int STOCK_DESIGN = 4;
  public final static int STOCK_TAKE_IN_PROGRESS = 5;
  public final static int STOCK_TAKE_COMPLETED = 6;
  public final static int STOCK_TAKE_OLD = 7;

  private int stockID = -1;
  private String itemCode;
  private String supplier;
  private int date;
  private float length;
  private float width;
  private float cost;
  private int designID = -1;
  private String orderID;
  private int matType;
  private int entryType;
  private int exitType;
  private String shelf;

  private float[] itemLengthsAllocated;

  // moenie in db gestoor word nie
  private boolean selected = false;
  private int count = 0;
  public ArrayList countList = new ArrayList();

  public StockItem() {
  }

  public void writeExternal(ObjectOutput out) throws IOException {
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
  }

  public Object clone() {
    StockItem clone = null;
    try {
      clone = (StockItem)super.clone();
    } catch (CloneNotSupportedException e) {
      // fokop
    }

    clone.itemCode = itemCode;
    clone.supplier = supplier;
    clone.orderID = orderID;
    return clone;
  }

  public boolean matchValue(String field, String value) {
    return true;
  }

  public int getStockID() {
    return stockID;
  }
  public void setStockID(int id) {
    if (stockID != id) {
      stockID = id;
      setModified(true);
    }
  }

  public String getItemCode() {
    if (itemCode == null || itemCode.equals("null"))
      return "";
    else
      return itemCode;
  }
  public void setItemCode(String x) {
    if ((itemCode != null && !itemCode.equals(x)) || (itemCode == null && x != null)) {
      itemCode = x;
      setModified(true);
    }
  }

  public String getSupplier() {
    if (supplier == null || supplier.equals("null"))
      return "";
    else
      return supplier;
  }
  public void setSupplier(String x) {
    if ((supplier != null && !supplier.equals(x)) || (supplier == null && x != null)) {
      supplier = x;
      setModified(true);
    }
  }

  public int getDate() {
    return date;
  }
  public void setDate(int x) {
    if (date != x) {
      date = x;
      setModified(true);
    }
  }

  public float getLength() {
    return length;
  }
  public void setLength(float x) {
    if (length != x) {
      length = x;
      setModified(true);
    }
  }

  public float getWidth() {
    return width;
  }
  public void setWidth(float x) {
    if (width != x) {
      width = x;
      setModified(true);
    }
  }

  public float getCost() {
    return cost;
  }
  public void setCost(float x) {
    if (cost != x) {
      cost = x;
      setModified(true);
    }
  }

  public int getDesignID() {
    return designID;
  }
  public void setDesignID(int x) {
    if (designID != x) {
      designID = x;
      setModified(true);
    }
  }

  public String getOrderID() {
    if (orderID == null || orderID.equals("null"))
      return "";
    else
      return orderID;
  }
  public void setOrderID(String x) {
    if ((orderID != null && !orderID.equals(x)) || (orderID == null && x != null)) {
      orderID = x;
      setModified(true);
    }
  }

  public int getMatType() {
    return matType;
  }
  public void setMatType(int x) {
    if (matType != x) {
      matType = x;
      setModified(true);
    }
  }

  public int getEntryType() {
    return entryType;
  }
  public void setEntryType(int x) {
    if (entryType != x) {
      entryType = x;
      setModified(true);
    }
  }

  public int getExitType() {
    return exitType;
  }
  public void setExitType(int x) {
    if (exitType != x) {
      exitType = x;
      setModified(true);
    }
  }

  public float[] getItemLengthsAllocated() {
    return itemLengthsAllocated;
  }
  public void setItemLengthsAllocated(float[] lengths) {
    itemLengthsAllocated = lengths;
  }

  public void setShelf(String shelf) {
    this.shelf = shelf;
  }

  public String getShelf() {
    return this.shelf;
  }


  // moenie in db gestoor word nie
  public boolean getSelected() {
    return selected;
  }
  public void setSelected(boolean b) {
    selected = b;
  }

  public int getCount() {
    return count;
  }
  public void setCount(int x) {
    if (count != x) {
      count = x;
      setModified(true);

      try {
        if (count < countList.size())
          countList = new ArrayList(countList.subList(0, count - 1));
      } catch (NullPointerException e) {
        // los
      }
    }
  }

  public void addCountID(int id) {
    countList.add(new Integer(id));
  }

  public int getCountListSize() {
    return countList.size();
  }

  public Iterator getWaaiListIterator() {
    try {
      List waaiList = countList.subList(count, countList.size());
      return waaiList.iterator();

    } catch (Exception e) {
      return new ArrayList().iterator();
    }
  }

  public ArrayList getCountList() {
    try {
      int delta = count - countList.size();
      for (int i = 0; i < delta; i++) {
        countList.add(new Integer(-1));
      }
      return countList;

    } catch (Exception e) {
      return new ArrayList();
    }
  }


}