package artof.materials;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MaterialValues implements Externalizable, Serializable {
  static final long serialVersionUID = -7024816005682407656L;
  public static final String STATUS_AVAIL = "Available";
  public static final String STATUS_DISC = "Discontinued";

  public static final float TOLERANCE = 0.01f;

  //private int valueID = -1;
  private String status = STATUS_AVAIL;
  private float cost = 0;
  private float compFactor = 1;
  private float exqFact = 1;
  private float width = 0;
  private float length = 0;
  private float thickness = 0;
  private float rebate = 0;
  private Object[] priceArray;

  private boolean modified = true;

  public MaterialValues() {
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    //out.writeInt(valueID);
    out.writeObject(status);
    out.writeFloat(cost);
    out.writeFloat(compFactor);
    out.writeFloat(exqFact);
    out.writeFloat(width);
    out.writeFloat(length);
    out.writeFloat(thickness);
    out.writeFloat(rebate);
    out.writeObject(priceArray);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    //valueID = in.readInt();
    status = (String)in.readObject();
    cost = in.readFloat();
    compFactor = in.readFloat();
    exqFact = in.readFloat();
    width = in.readFloat();
    length = in.readFloat();
    thickness = in.readFloat();
    rebate = in.readFloat();
    priceArray = (Object[])in.readObject();
  }

  public boolean isModified() {
    return modified;
  }
  public void setModified(boolean mod) {
    modified = mod;
  }

  /*public int getValueID() {
    return valueID;
  }

  public void setValueID(int id) {
    valueID = id;
  }*/

  public String getStatus() {
    if (status == null || status.equals("null"))
      return "";
    else
      return status;
  }
  public void setStatus(String s) {
    if ((status != null && !status.equals(s)) || (status == null && s != null)) {
      status = s;
      setModified(true);
    }
  }

  public float getCost() {
    return cost;
  }
  public void setCost(float c) {
    if (Math.abs(cost - c) > TOLERANCE) {
      cost = c;
      setModified(true);
    }
  }

  public float getCompFactor() {
    return compFactor;
  }
  public void setCompFactor(float c) {
    if (compFactor != c) {
      compFactor = c;
      setModified(true);
    }
  }

  public float getExqFactor() {
    return exqFact;
  }
  public void setExqFactor(float c) {
    if (exqFact != c) {
      exqFact = c;
      setModified(true);
    }
  }

  public float getWidth() {
    return width;
  }
  public void setWidth(float d) {
    if (width != d) {
      width = d;
      setModified(true);
    }
  }

  public float getLength() {
    return length;
  }
  public void setLength(float d) {
    if (length != d) {
      length = d;
      setModified(true);
    }
  }

  public float getThickness() {
    return thickness;
  }
  public void setThickness(float d) {
    if (thickness != d) {
      thickness = d;
      setModified(true);
    }
  }

  public float getRebate() {
    return rebate;
  }
  public void setRebate(float d) {
    if (rebate != d) {
      rebate = d;
      setModified(true);
    }
  }

  public Object[] getPriceArray() {
    return priceArray;
  }
  public void setPriceArray(Object[] array) {
    priceArray = array;
  }
}
