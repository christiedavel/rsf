package artof.designitems;
import artof.database.*;
import artof.designitems.dialogs.DesignDialogGBs;
import artof.designer.Designer;
import artof.utils.*;
import artof.materials.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.JPanel;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public abstract class DesignGlassAndBack extends DesignItem2 implements Cloneable, Externalizable {
  static final long serialVersionUID = -7831677341880173267L;
  protected String title = "Glass and Back";
  private DesignItem2 matchToItem;
  private boolean matchOutside = true;
  private LinkedList itemList;
  protected static CodeMapper gbMapper = null;

  public DesignGlassAndBack() {
    //refreshItem();
    defColor = UserSettings.DEF_COLOR;
    itemList = new LinkedList();
    //createGBMapper();
  }

  public DesignGlassAndBack(double limitWidth, double limitHeight, LinkedList items) {
    super(limitWidth, limitHeight);
    refreshItem();
    defColor = UserSettings.DEF_COLOR;
    itemList = items;
    createGBMapper();
  }

  public static void createGBMapper() {
    if (gbMapper == null)
      gbMapper = new CodeMapper(MaterialDets.MAT_GB);
  }

  public static void rebuildGBMapper() {
    gbMapper = new CodeMapper(MaterialDets.MAT_GB);
  }


  public CodeMapper getCodeMapper() {
    return gbMapper;
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    super.writeExternal(out);
    out.writeObject(matchToItem);
    out.writeBoolean(matchOutside);
    out.writeObject(itemList);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    super.readExternal(in);
    matchToItem = (DesignItem2)in.readObject();
    matchOutside = in.readBoolean();
    itemList = (LinkedList)in.readObject();
  }

  public Object clone() {
    DesignGlassAndBack clone = (DesignGlassAndBack)super.clone();
    clone.matchToItem = matchToItem;
    clone.matchOutside = matchOutside;
    return clone;
  }

  public void refreshItem() {
    try {
      MaterialDets dets = ArtofDB.getCurrentDB().getMaterial(itemCode);
      thickness = dets.getDefaultValues(this.getDefaultSupplier()).getThickness();
    } catch (NullPointerException e) {
      // doen niks
    }
  }

  public boolean showPropertyDialog(int titleType, int type) {
    DesignDialogGBs dialog;
    if (titleType == Designer.TITLE_EDIT) dialog = new DesignDialogGBs("Edit " + title, this);
    else dialog = new DesignDialogGBs("Add " + title, this);

    if (!dialog.canceled()) setMaterialDets(ArtofDB.getCurrentDB().getMaterial(itemCode));
    return dialog.canceled();
  }

  public boolean containsPoint(double x, double y, double centerX, double centerY, double scaleFactor) {
    return false;
  }

  public void drawSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {}

  public boolean mustDrawMeasurements() {
    return false;
  }

  public DesignItem2 getMatchToItem() {
    return matchToItem;
  }
  public void setMatchToItem(DesignItem2 item) {
    matchToItem = item;
  }

  public boolean getMatchOutside() {
    return matchOutside;
  }
  public void setMatchOutside(boolean b) {
    matchOutside = b;
  }

  public Iterator getItemListIterator() {
    return itemList.iterator();
  }

  public void setLimitWidth(double d) {
    if (matchOutside) {
      leftGap = matchToItem.getOuterWidth() / 2;
      rightGap = matchToItem.getOuterWidth() / 2;
    } else {
      leftGap = matchToItem.getInnerWidth() / 2;
      rightGap = matchToItem.getInnerWidth() / 2;
    }
  }

  public void setLimitHeight(double d) {
    if (matchOutside) {
      topGap = matchToItem.getOuterHeight() / 2;
      bottomGap = matchToItem.getOuterHeight() / 2;
    } else {
      topGap = matchToItem.getInnerHeight() / 2;
      bottomGap = matchToItem.getInnerHeight() / 2;
    }
  }

  public void setLeftOffset(double gap) {}
  public void setRightOffset(double gap) {}
  public void setTopOffset(double gap) {}
  public void setBottomOffset(double gap) {}

  public void calcItemSize(JPanel parent, LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
    if (matchOutside) {
      setDesignHeight(matchToItem.getDesignHeight());
      setDesignWidth(matchToItem.getDesignWidth());

    } else {
      setDesignHeight(matchToItem.getInnerHeight());
      setDesignWidth(matchToItem.getInnerWidth());
      if (matchToItem.getDesignType() == Designer.ITEM_FRAME) {
        double adjH = methodPrefs.getGlassToFrameLineWidth((float)Math.max(getDesignHeight(), getDesignWidth()));
        double adjW = methodPrefs.getGlassToFrameLineHeight((float)Math.max(getDesignHeight(), getDesignWidth()));
        setDesignHeight(getDesignHeight() - adjH + 2*matchToItem.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate());
        setDesignWidth(getDesignWidth() - adjW + 2*matchToItem.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate());
      }
    }
  }

  public void checkItemSize(LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
  }

/*----------------------------- Stock Extraction ------------------------------*/

 /* public boolean extractStock(artof.dialogs.ProgressDialog dialog, int progressBase, int designID) {
    int kortKant = (int)Math.min(getDesignHeight(), getDesignWidth());
    int langKant = (int)Math.max(getDesignHeight(), getDesignWidth());

    // kry stockList en haal al die onnodige kak uit
    ArrayList stockList = db_conn.getCurrentStockPerItemCode(itemCode);

    float dialogFactor = 100.f / stockList.size();
    int count = 0;
    StockItem bestItem = null;
    Iterator it = stockList.iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();
      int itemKortKant = (int)Math.min(item.getLength(), item.getWidth());
      int itemLangKant = (int)Math.max(item.getLength(), item.getWidth());
      if (itemKortKant < kortKant || itemLangKant < langKant) {
        continue;
      }

      try {
        int bestArea = (int)(bestItem.getLength() * bestItem.getWidth());
        int itemArea = itemKortKant * itemLangKant;
        if (itemArea < bestArea)
          bestItem = item;
      } catch (NullPointerException e) {
        bestItem = item;
      }

      dialog.setValue2(progressBase + (int)(++count * dialogFactor));
    }

    if (designID > -1) {
      try {
        bestItem.setDate(Utils.getCurrentDate());
        bestItem.setDesignID(designID);
        bestItem.setExitType(StockItem.STOCK_DESIGN);

        float[] sidesCovered = new float[2];
        sidesCovered[0] = kortKant;
        sidesCovered[1] = langKant;
        bestItem.setItemLengthsAllocated(sidesCovered);

        ArrayList bestList = new ArrayList();
        bestList.add(bestItem);
        db_conn.updateStockItems(bestList);
        return true;

      } catch (NullPointerException e) {
        return false;
      }
    }
    return false;
  }

  public ArrayList getOffCutList(ArrayList stockSelected) {
    int kortKant = (int)Math.min(getDesignHeight(), getDesignWidth());
    int langKant = (int)Math.max(getDesignHeight(), getDesignWidth());

    ArrayList offcutList = new ArrayList();
    Iterator it = stockSelected.iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();

      if (item.getItemCode().equals(itemCode)) {
        float[] sidesCovered = item.getItemLengthsAllocated();
        if (sidesCovered.length == 0 || !(Math.abs(sidesCovered[0] - kortKant) < 0.01 &&
                                          Math.abs(sidesCovered[1] - langKant) < 0.01)) {
          continue;
        }

        float bordKortKant = Math.min(item.getLength(), item.getWidth());
        float bordLangKant = Math.max(item.getLength(), item.getWidth());

        if (bordKortKant >= langKant) {
          if (bordLangKant > kortKant) {
            StockItem offcut = (StockItem) item.clone();
            offcut.setLength(bordLangKant - kortKant);
            offcut.setWidth(bordKortKant);
            offcut.setDate(Utils.getCurrentDate());
            offcut.setEntryType(StockItem.STOCK_OFFCUT);
            offcut.setExitType( -1);
            offcut.setCount(1);
            offcut.setStockID( -1);
            offcutList.add(offcut);
          }

          if (bordKortKant > langKant) {
            StockItem offcut = (StockItem) item.clone();
            offcut.setLength(kortKant);
            offcut.setWidth(bordKortKant - langKant);
            offcut.setDate(Utils.getCurrentDate());
            offcut.setEntryType(StockItem.STOCK_OFFCUT);
            offcut.setExitType( -1);
            offcut.setCount(1);
            offcut.setStockID( -1);
            offcutList.add(offcut);
          }

        } else {
          if (bordLangKant > langKant) {
            StockItem offcut = (StockItem) item.clone();
            offcut.setLength(bordLangKant - langKant);
            offcut.setWidth(bordKortKant);
            offcut.setDate(Utils.getCurrentDate());
            offcut.setEntryType(StockItem.STOCK_OFFCUT);
            offcut.setExitType( -1);
            offcut.setCount(1);
            offcut.setStockID( -1);
            offcutList.add(offcut);
          }

          if (bordKortKant > kortKant) {
            StockItem offcut = (StockItem) item.clone();
            offcut.setLength(langKant);
            offcut.setWidth(bordKortKant - kortKant);
            offcut.setDate(Utils.getCurrentDate());
            offcut.setEntryType(StockItem.STOCK_OFFCUT);
            offcut.setExitType( -1);
            offcut.setCount(1);
            offcut.setStockID( -1);
            offcutList.add(offcut);
          }
        }
      }
    }

    return offcutList;
  }*/
}
