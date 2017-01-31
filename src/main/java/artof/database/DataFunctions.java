package artof.database;
import java.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public interface DataFunctions {
  public boolean setData(Object oldObj);
  public void updateData(Object obj);
  public ListIterator getMainIterator();

  public Object getNewItem();
  public void saveMainList();
  public void refreshMainList(String orderSQL);
  public void closeMainList();
  public void deleteItem(Object obj);
  public void printDieFokker();

  public String[] getSortList();
  public Hashtable getSortDBMap();
}