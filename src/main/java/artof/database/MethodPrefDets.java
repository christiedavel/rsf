package artof.database;
import artof.utils.*;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class MethodPrefDets extends DBItem implements Externalizable, Cloneable {
  public final static int BORDER_CALC = 0;
  public final static int BORDER_FIXED = 1;
  public final static int BORDER_FULL = 2;
  public final static String[] BORDER_TYPE = { "Calcualted Border", "Fixed Border", "Full Bottoms" };

  private int prefID = -1;
  private int prefDate = UserSettings.MAX_DATE;
  private int dateCount = 1;

  private int methodType = 0;
  private int fullBottoms = 0;
  private float overlapAdjFact = 1.f;
  private float fbOverlapWithSlip = 30.f;
  private float fbOverlapNoSlip = 20.f;
  private float minOverlap = 20.f;

  private float resBoardLength = 0.f;
  private float resBoardWidth = 0.f;
  private float resBackLength = 0.f;
  private float resBackWidth = 0.f;
  private float resGlassLength = 0.f;
  private float resGlassWidth = 0.f;
  private float resPremLength = 0.f;
  private float resPremWidth = 0.f;
  private int resSelected = 0;

  private float fgIfValue = 1000.f;
  private float fgThenHeight = 0.5f;
  private float fgThenWidth = 0.5f;
  private float fgElseHeight = 1.f;
  private float fgElseWidth = 1.f;

  private String sunFirstMeasure = "height then width";
  private int sunNoSpecs = 0;
  private String sunBackDefault = "";
  private int sunDays = 0;

  public MethodPrefDets() {
  }
  public MethodPrefDets(int id) {
    prefID = id;
  }

  public boolean matchValue(String field, String value) {
    return false;
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(prefID);
    out.writeInt(prefDate);
    out.writeInt(dateCount);

    out.writeInt(methodType);
    out.writeInt(fullBottoms);
    out.writeFloat(overlapAdjFact);
    out.writeFloat(fbOverlapWithSlip);
    out.writeFloat(fbOverlapNoSlip);
    out.writeFloat(minOverlap);

    out.writeFloat(resBoardLength);
    out.writeFloat(resBoardWidth);
    out.writeFloat(resBackLength);
    out.writeFloat(resBackWidth);
    out.writeFloat(resGlassLength);
    out.writeFloat(resGlassWidth);
    out.writeFloat(resPremLength);
    out.writeFloat(resPremWidth);
    out.writeInt(resSelected);

    out.writeFloat(fgIfValue);
    out.writeFloat(fgThenHeight);
    out.writeFloat(fgThenWidth);
    out.writeFloat(fgElseHeight);
    out.writeFloat(fgElseWidth);

    out.writeObject(sunFirstMeasure);
    out.writeInt(sunNoSpecs);
    out.writeObject(sunBackDefault);
    out.writeInt(sunDays);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    prefID = in.readInt();
    prefDate = in.readInt();
    dateCount = in.readInt();

    methodType = in.readInt();
    fullBottoms = in.readInt();
    overlapAdjFact = in.readFloat();
    fbOverlapWithSlip = in.readFloat();
    fbOverlapNoSlip = in.readFloat();
    minOverlap = in.readFloat();

    resBoardLength = in.readFloat();
    resBoardWidth = in.readFloat();
    resBackLength = in.readFloat();
    resBackWidth = in.readFloat();
    resGlassLength = in.readFloat();
    resGlassWidth = in.readFloat();
    resPremLength = in.readFloat();
    resPremWidth = in.readFloat();
    resSelected = in.readInt();

    fgIfValue = in.readFloat();
    fgThenHeight = in.readFloat();
    fgThenWidth = in.readFloat();
    fgElseHeight = in.readFloat();
    fgElseWidth = in.readFloat();

    sunFirstMeasure = (String)in.readObject();
    sunNoSpecs = in.readInt();
    sunBackDefault = (String)in.readObject();
    sunDays = in.readInt();
  }

  public Object clone() {
    try {
      MethodPrefDets clone = (MethodPrefDets)super.clone();
      clone.prefID = prefID;
      clone.prefDate = prefDate;
      clone.dateCount = dateCount;

      clone.methodType = methodType;
      clone.fullBottoms = fullBottoms;
      clone.overlapAdjFact = overlapAdjFact;
      clone.fbOverlapWithSlip = fbOverlapWithSlip;
      clone.fbOverlapNoSlip = fbOverlapNoSlip;
      clone.minOverlap = minOverlap;

      clone.resBoardLength = resBoardLength;
      clone.resBoardWidth = resBoardWidth;
      clone.resBackLength = resBackLength;
      clone.resBackWidth = resBackWidth;
      clone.resGlassLength = resGlassLength;
      clone.resGlassWidth = resGlassWidth;
      clone.resPremLength = resPremLength;
      clone.resPremWidth = resPremWidth;
      clone.resSelected = resSelected;

      clone.fgIfValue = fgIfValue;
      clone.fgThenHeight = fgThenHeight;
      clone.fgThenWidth = fgThenWidth;
      clone.fgElseHeight = fgElseHeight;
      clone.fgElseWidth = fgElseWidth;

      clone.sunFirstMeasure = new String(sunFirstMeasure);
      clone.sunNoSpecs = sunNoSpecs;
      clone.sunBackDefault = new String(sunBackDefault);
      clone.sunDays = sunDays;
      return clone;
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  /*------------------------------- Berekeninge --------------------------------*/

  public float getGlassToFrameLineHeight(float d) {
    if (d <= fgIfValue) return fgThenHeight;
    else return fgElseHeight;
  }

  public float getGlassToFrameLineWidth(float d) {
    if (d <= fgIfValue) return fgThenWidth;
    else return fgElseWidth;
  }

  /*---------------------------- Getters en Setters ----------------------------*/

  public int getPrefID() {
    return prefID;
  }
  public void setPrefID(int id) {
    prefID = id;
  }

  public int getPrefDate() {
    return prefDate;
  }
  public void setPrefDate(int d) {
    if (prefDate != d) {
      prefDate = d;
      setModified(true);
    }
  }

  public int getDateCount() {
    return dateCount;
  }
  public void setDateCount(int d) {
    if (dateCount != d) {
      dateCount = d;
      setModified(true);
    }
  }

  public int getMethodType() {
    return methodType;
  }
  public void setMethodType(int d) {
    if (methodType != d) {
      methodType = d;
      setModified(true);
    }
  }

  public int getFullBottoms() {
    return fullBottoms;
  }
  public void setFullBottoms(int d) {
    if (fullBottoms != d) {
      fullBottoms = d;
      setModified(true);
    }
  }
  public boolean getFullBottomsBool() {
    if (fullBottoms == 1) return true;
    else return false;
  }
  public void setFullBottoms(boolean b) {
    int d;
    if (b) d = 1;
    else d = 0;
    if (fullBottoms != d) {
      fullBottoms = d;
      setModified(true);
    }
  }

  public float getOverlapAdjFact() {
    return overlapAdjFact;
  }
  public void setOverlapAdjFact(float fact) {
    if (overlapAdjFact != fact) {
      overlapAdjFact = fact;
      setModified(true);
    }
  }

  public float getFBOverlapWithSlip() {
    return fbOverlapWithSlip;
  }
  public void setFBOverlapWithSlip(float val) {
    if (fbOverlapWithSlip != val) {
      fbOverlapWithSlip = val;
      setModified(true);
    }
  }

  public float getFBOverlapNoSlip() {
    return fbOverlapNoSlip;
  }
  public void setFBOverlapNoSlip(float val) {
    if (fbOverlapNoSlip != val) {
      fbOverlapNoSlip = val;
      setModified(true);
    }
  }

  public float getMinOverlap() {
    return minOverlap;
  }
  public void setMinOverlap(float val) {
    if (minOverlap != val) {
      minOverlap = val;
      setModified(true);
    }
  }

  public float getResBoardLength() {
    return resBoardLength;
  }
  public void setResBoardLength(float val) {
    if (resBoardLength != val) {
      resBoardLength = val;
      setModified(true);
    }
  }

  public float getResBoardWidth() {
    return resBoardLength;
  }
  public void setResBoardWidth(float val) {
    if (resBoardWidth != val) {
      resBoardWidth = val;
      setModified(true);
    }
  }

  public float getResBackLength() {
    return resBackLength;
  }
  public void setResBackLength(float val) {
    if (resBackLength != val) {
      resBackLength = val;
      setModified(true);
    }
  }

  public float getResBackWidth() {
    return resBackWidth;
  }
  public void setResBackWidth(float val) {
    if (resBackWidth != val) {
      resBackWidth = val;
      setModified(true);
    }
  }

  public float getResGlassLength() {
    return resGlassLength;
  }
  public void setResGlassLength(float val) {
    if (resGlassLength != val) {
      resGlassLength = val;
      setModified(true);
    }
  }

  public float getResGlassWidth() {
    return resGlassWidth;
  }
  public void setResGlassWidth(float val) {
    if (resGlassWidth != val) {
      resGlassWidth = val;
      setModified(true);
    }
  }

  public float getResPremLength() {
    return resPremLength;
  }
  public void setResPremLength(float val) {
    if (resPremLength != val) {
      resPremLength = val;
      setModified(true);
    }
  }

  public float getResPremWidth() {
    return resPremWidth;
  }
  public void setResPremWidth(float val) {
    if (resPremWidth != val) {
      resPremWidth = val;
      setModified(true);
    }
  }

  public int getResSelected() {
    return resSelected;
  }
  public void setResSelected(int val) {
    if (resSelected != val) {
      resSelected = val;
      setModified(true);
    }
  }

  /*public boolean getResBoardsSelected() {
    if (((1 << 3) & resSelected) != 0)
      return true;
    else
      return false;
  }
  public void setResBoardsSelected(boolean val) {
    boolean cur;
    if (((1 << 3) & resSelected) != 0)
      cur = true;
    else
      cur = false;
    if (cur != val) {
      int setter = (val ? 1 : 0);
      resSelected = ((setter << 3) ^ resSelected);
      setModified(true);
    }
  }

  public boolean getResBacksSelected() {
    if (((1 << 2) & resSelected) != 0)
      return true;
    else
      return false;
  }
  public void setResBacksSelected(boolean val) {
    boolean cur;
    if (((1 << 2) & resSelected) != 0)
      cur = true;
    else
      cur = false;
    if (cur != val) {
      int setter = (val ? 1 : 0);
      resSelected = ((setter << 2) ^ resSelected);
      setModified(true);
    }
  }

  public boolean getResPremountsSelected() {
    if (((1 << 1) & resSelected) != 0)
      return true;
    else
      return false;
  }
  public void setResPremountsSelected(boolean val) {
    boolean cur;
    if (((1 << 1) & resSelected) != 0)
      cur = true;
    else
      cur = false;
    if (cur != val) {
      int setter = (val ? 1 : 0);
      resSelected = ((setter << 1) ^ resSelected);
      setModified(true);
    }
  }

  public boolean getResGlassSelected() {
    if (((1 << 0) & resSelected) != 0)
      return true;
    else
      return false;
  }
  public void setResGlassSelected(boolean val) {
    boolean cur;
    if (((1 << 0) & resSelected) != 0)
      cur = true;
    else
      cur = false;
    if (cur != val) {
      int setter = (val ? 1 : 0);
      resSelected = ((setter << 0) ^ resSelected);
      setModified(true);
    }
  }*/

  public float getFGIfValue() {
    return fgIfValue;
  }
  public void setFGIfValue(float val) {
    if (fgIfValue != val) {
      fgIfValue = val;
      setModified(true);
    }
  }

  public float getFGThenHeight() {
    return fgThenHeight;
  }
  public void setFGThenHeight(float val) {
    if (fgThenHeight != val) {
      fgThenHeight = val;
      setModified(true);
    }
  }

  public float getFGThenWidth() {
    return fgThenWidth;
  }
  public void setFGThenWidth(float val) {
    if (fgThenWidth != val) {
      fgThenWidth = val;
      setModified(true);
    }
  }

  public float getFGElseHeight() {
    return fgElseHeight;
  }
  public void setFGElseHeight(float val) {
    if (fgElseHeight != val) {
      fgElseHeight = val;
      setModified(true);
    }
  }

  public float getFGElseWidth() {
    return fgElseWidth;
  }
  public void setFGElseWidth(float val) {
    if (fgElseWidth != val) {
      fgElseWidth = val;
      setModified(true);
    }
  }

  public String getSunFirstMeasure() {
    if (sunFirstMeasure == null || sunFirstMeasure.equals("null"))
      return "";
    else
      return sunFirstMeasure;
  }
  public void setSunFirstMeasure(String val) {
    if ((sunFirstMeasure != null && !sunFirstMeasure.equals(val)) || (sunFirstMeasure == null && val != null)) {
      sunFirstMeasure = val;
      setModified(true);
    }
  }

  public int getSunNoSpecs() {
    return sunNoSpecs;
  }
  public void setSunNoSpecs(int val) {
    if (sunNoSpecs != val) {
      sunNoSpecs = val;
      setModified(true);
    }
  }

  public String getSunBackDefault() {
    if (sunBackDefault == null || sunBackDefault.equals("null"))
      return "";
    else
      return sunBackDefault;
  }
  public void setSunBackDefault(String val) {
    if ((sunBackDefault != null && !sunBackDefault.equals(val)) || (sunBackDefault == null && val != null)) {
      sunBackDefault = val;
      setModified(true);
    }
  }

  public int getSunDays() {
    return sunDays;
  }
  public void setSunDays(int val) {
    if (sunDays != val) {
      sunDays = val;
      setModified(true);
    }
  }
}