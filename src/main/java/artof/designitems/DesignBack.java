package artof.designitems;
import artof.database.*;
import artof.designer.Designer;
import artof.materials.*;
import java.util.LinkedList;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class DesignBack extends DesignGlassAndBack implements Cloneable, Externalizable {

  public DesignBack() {
    title = "Back";
  }

  public DesignBack(double limitWidth, double limitHeight, LinkedList items) {
    super(limitWidth, limitHeight, items);
    title = "Back";
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    super.writeExternal(out);
    try {
      out.writeObject(defaultSupplier);
    }
    catch (java.io.OptionalDataException e) {
    }

  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    super.readExternal(in);
    try {
      defaultSupplier = (String) in.readObject();
    } catch (java.io.OptionalDataException e) {

    }

  }

  public Object clone() {
     return (DesignBack)super.clone();
  }

  public String getType() {
    return "Back";
  }

  public int getDesignType() {
    return Designer.ITEM_BACK;
  }

  public void calcItemPrice(MethodPrefDets methodPrefs, BusPrefDets busPrefs, boolean stretching, boolean pasting) {
    MaterialDets boardDets = getMaterialDets();
    double resUCM = methodPrefs.getResBoardLength();
    double matUCM = boardDets.getDefaultValues(this.getDefaultSupplier()).getLength() + boardDets.getDefaultValues(this.getDefaultSupplier()).getWidth();
    double boardUCM = getDesignWidth() + getDesignHeight();

    MaterialValues mats = getMaterialDets().getDefaultValues(this.getDefaultSupplier());
    double price = busPrefs.getMarkupBoards() * mats.getCost() * mats.getCompFactor();
    double totalArea;
    if (matUCM - boardUCM < resUCM)
      totalArea = boardDets.getDefaultValues(this.getDefaultSupplier()).getWidth() * boardDets.getDefaultValues(this.getDefaultSupplier()).getLength();
    else
      totalArea = getDesignWidth() * getDesignHeight();

    totalArea /= 1000000;
    price *= totalArea;
    setDesignPrice(price);


/*    MaterialDets dets = getMaterialDets();
    double resX = dets.getDefaultValues().getWidth() - methodPrefs.getResBackWidth();
    double resY = dets.getDefaultValues().getLength() - methodPrefs.getResBackLength();
    double price = busPrefs.getMarkupBoards() * dets.getDefaultValues().getCost();

    double totalArea;
    if ( (getDesignWidth() > resX && getDesignHeight() > resY) ||
          (getDesignWidth() > resY && getDesignHeight() > resX))
      totalArea = dets.getDefaultValues().getWidth() * dets.getDefaultValues().getLength();
    else
      totalArea = getDesignWidth() * getDesignHeight();

    totalArea /= 1000000;
    setDesignPrice(price * totalArea);*/
  }
}
