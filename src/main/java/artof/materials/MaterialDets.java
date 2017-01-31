package artof.materials;

import artof.database.DBItem;
import artof.utils.UserSettings;
import java.util.*;
import java.awt.Color;
import java.io.*;
import java.awt.image.BufferedImage;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.font.*;
import java.awt.geom.*;
import javax.imageio.*;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MaterialDets extends DBItem implements Externalizable {
//  static final long serialVersionUID = 1781717333114815516L;
  static final long serialVersionUID = 1781717333114815516L;

  public final static int MAT_ALL = 0;
  public final static int MAT_BOARD = 1;
  public final static int MAT_GB = 2;
  public final static int MAT_FRAME = 3;
  public final static int MAT_SLIP = 4;
  public final static int MAT_FOIL = 5;
  public final static int MAT_OTHER = 6;

  public final static String[] MAT_TYPES = { "All", "Boards", "Glass and Backs",
                                             "Frames", "Slips", "Foils", "Other" };

  //public final static String[] MAT_TYPES2 = { "Board", "Glass or Back",
  //                                           "Frame", "Slip", "Foil", "Other" };

  private int materialID = -1;
  private int materialType;
  private String itemCode;
  private String ownCode;
  private String group;
  private String description;
  private Color colour = UserSettings.DEF_COLOR;
  private BufferedImage sampleImage = null;

  private HashMap detailMap = new HashMap();

  private boolean isNewMaterial = false;

  private boolean imageModified = false;

  public MaterialDets() {
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(materialID);
    out.writeInt(materialType);
    out.writeObject(itemCode);
    out.writeObject(ownCode);
    out.writeObject(group);
    out.writeObject(colour);
    out.writeObject(detailMap);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    materialID = in.readInt();
    materialType = in.readInt();
    itemCode = (String)in.readObject();
    ownCode = (String)in.readObject();
    group = (String)in.readObject();
    colour = (Color)in.readObject();
    detailMap = (HashMap)in.readObject();
  }

  public boolean isNew() {
    return isNewMaterial;
  }

  public void setIsNew(boolean b) {
    isNewMaterial = b;
  }

  public boolean isImageAvailable() {
    if (getSampleImage() == null)
      return false;
    else
      return true;
  }

  public BufferedImage getSampleImage() {
    if (sampleImage != null)
      return sampleImage;

    try {
      String path = itemCode.toLowerCase() + ".jpg";
    //  if (isImageModified())
      //  path = "images/materials/temp/" + path;
      //else
        path = "images/materials/" + path;

      MediaTracker tracker = new MediaTracker(new Container());
      Image i = Toolkit.getDefaultToolkit().createImage(path);
      tracker.addImage(i, 0);
      tracker.waitForAll();

      BufferedImage sampleImage = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = sampleImage.createGraphics();
      g.drawImage(i, 0, 0, null);
      return sampleImage;

    } catch (Exception e) {
      return null;
    }
  }

  public void setSampleImage(BufferedImage i) {
    sampleImage = i;
    imageModified = true;

    /*try {
      String path = "images/materials/temp/" + getItemCode().toLowerCase() + ".jpg";
      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(dets.getSampleImage());
      param.setQuality(1.0f, false);
      encoder.setJPEGEncodeParam(param);
      encoder.encode(dets.getSampleImage());
      out.flush();
      out.close();
      dets.setIsImageModified(false);

    } catch (Exception e ) {
      e.printStackTrace();
    }*/

  }

  public boolean isImageModified() {
    return imageModified;
  }

  public void setIsImageModified(boolean m) {
    imageModified = m;
  }

  public BufferedImage getLeftImage(double scaleFactor) {
    Image image = getSampleImage();
    double frameWidth = getDefaultValuesWithInMaterialDets().getWidth();
    if (this.getMaterialType() == MaterialDets.MAT_SLIP)
      frameWidth -= getDefaultValuesWithInMaterialDets().getRebate();

    if (frameWidth == 0)
      frameWidth = image.getWidth(null);

    int imageWidth = (int)Math.round(frameWidth * scaleFactor);
    int imageHeight = (int)Math.round(image.getHeight(null) * imageWidth / image.getWidth(null));
    BufferedImage prent = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = prent.createGraphics();
    double widthShear = (double)prent.getWidth() / (double)image.getWidth(null);
    double heightShear = (double)prent.getHeight() / (double)image.getHeight(null);
    AffineTransform transform = new AffineTransform(widthShear, 0, 0, heightShear, 0, 0);
    g.drawImage(image, transform, null);

    return prent;
  }

  public BufferedImage getTopImage(double scaleFactor) {
    Image image = getSampleImage();
    double frameWidth = getDefaultValuesWithInMaterialDets().getWidth();
    if (this.getMaterialType() == MaterialDets.MAT_SLIP)
      frameWidth -= getDefaultValuesWithInMaterialDets().getRebate();

    if (frameWidth == 0)
      frameWidth = image.getWidth(null);

    int imageWidth = (int)Math.round(frameWidth * scaleFactor);
    int imageHeight = (int)Math.round(image.getHeight(null) * imageWidth / image.getWidth(null));
    BufferedImage prent = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = prent.createGraphics();
    double widthShear = (double)prent.getWidth() / (double)image.getHeight(null);
    double heightShear = (double)prent.getHeight() / (double)image.getWidth(null);
    AffineTransform transform = new AffineTransform();
    transform.translate(imageHeight, 0);
    transform.rotate(Math.PI / 2.0);
    transform.scale(widthShear, heightShear);
    g.drawImage(image, transform, null);

    return prent;
  }

  public BufferedImage getRightImage(double scaleFactor) {
    Image image = getSampleImage();
    double frameWidth = getDefaultValuesWithInMaterialDets().getWidth();
    if (this.getMaterialType() == MaterialDets.MAT_SLIP)
      frameWidth -= getDefaultValuesWithInMaterialDets().getRebate();

    if (frameWidth == 0)
      frameWidth = image.getWidth(null);

    int imageWidth = (int)Math.round(frameWidth * scaleFactor);
    int imageHeight = (int)Math.round(image.getHeight(null) * imageWidth / image.getWidth(null));
    BufferedImage prent = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = prent.createGraphics();
    double widthShear = (double)prent.getWidth() / (double)image.getWidth(null);
    double heightShear = (double)prent.getHeight() / (double)image.getHeight(null);
    AffineTransform transform = new AffineTransform();
    transform.translate(imageWidth, imageHeight);
    transform.rotate(Math.PI);
    transform.scale(widthShear, heightShear);
    g.drawImage(image, transform, null);

    return prent;
  }

  public BufferedImage getBottomImage(double scaleFactor) {
    Image image = getSampleImage();
    double frameWidth = getDefaultValuesWithInMaterialDets().getWidth();
    if (this.getMaterialType() == MaterialDets.MAT_SLIP)
      frameWidth -= getDefaultValuesWithInMaterialDets().getRebate();

    if (frameWidth == 0)
      frameWidth = image.getWidth(null);

    int imageWidth = (int)Math.round(frameWidth * scaleFactor);
    int imageHeight = (int)Math.round(image.getHeight(null) * imageWidth / image.getWidth(null));
    BufferedImage prent = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = prent.createGraphics();
    double widthShear = (double)prent.getWidth() / (double)image.getHeight(null);
    double heightShear = (double)prent.getHeight() / (double)image.getWidth(null);
    AffineTransform transform = new AffineTransform();
    transform.translate(0, imageWidth);
    transform.rotate(3.0 * Math.PI / 2.0);
    transform.scale(widthShear, heightShear);
    g.drawImage(image, transform, null);

    return prent;
  }

  public boolean isModified() {
    if (!modified) {
      Iterator it = detailMap.values().iterator();
      while (it.hasNext()) {
        //MaterialValues values = (MaterialValues)it.next();
        if (((MaterialValues)it.next()).isModified()) {
          modified = true;
          break;
        }
      }
    }
    return modified;
  }

  public static int getItemTypeInt(String name) {
    for (int i = 0; i < MAT_TYPES.length; i++) {
      if (MAT_TYPES[i].equals(name))
        return i;
    }
    return -1;
  }

  public static String getItemTypeString(int i) {
    try {
      return MAT_TYPES[i];
    } catch (IndexOutOfBoundsException e) {
      return "Unknown";
    }
  }

  /*public static int getItemTypeInt2(String name) {
    for (int i = 1; i < MAT_TYPES.length; i++) {
      if (MAT_TYPES[i].equals(name))
        return i;
    }
    return -1;
  }

  public static String getItemTypeString2(int i) {
    try {
      return MAT_TYPES[i];
    } catch (IndexOutOfBoundsException e) {
      return "Unknown";
    }
  }*/

  public boolean matchValue(String field, String value) {
      try {
        if (field.equalsIgnoreCase("Item Code") && value.equalsIgnoreCase(itemCode))
          return true;
        else if (field.equalsIgnoreCase("Own Code") && value.equalsIgnoreCase(ownCode))
          return true;
        else if (field.equalsIgnoreCase("Group") && value.equalsIgnoreCase(group))
          return true;
        else
          return false;
      } catch (NullPointerException e) {
        return false;
      }
  }

  public int getMaterialID() {
    return materialID;
  }
  public void setMaterialID(int id) {
    materialID = id;
  }

  public int getMaterialType() {
    return materialType;
  }
  public void setMaterialType(int type) {
    materialType = type;
  }

  public String getItemCode() {
    if (itemCode == null || itemCode.equals("null"))
      return "";
    else
      return itemCode;
  }
  public void setItemCode(String code) {
    if ((itemCode != null && !itemCode.equals(code)) || (itemCode == null && code != null)) {
      itemCode = code;
      setModified(true);
    }
  }

  public String getOwnCode() {
    if (ownCode == null || ownCode.equals("null"))
      return "";
    else
      return ownCode;
  }
  public void setOwnCode(String code) {
    if ((ownCode != null && !ownCode.equals(code)) || (ownCode == null && code != null)) {
      ownCode = code;
      setModified(true);
    }
  }

  public String getGroup() {
    if (group == null || group.equals("null"))
      return "";
    else
      return group;
  }
  public void setGroup(String g) {
    if ((group != null && !group.equals(g)) || (group == null && g != null)) {
      group = g;
      setModified(true);
    }
  }

  public String getDescription() {
    if (description == null || description.equals("null"))
      return "";
    else
      return description;
  }
  public void setDescription(String g) {
    if ((description != null && !description.equals(g)) || (description == null && g != null)) {
      description = g;
      setModified(true);
    }
  }

  public void setColor(Color col) {
    if (col != null && !colour.equals(col)) {
      colour = col;
      setModified(true);
    }
  }
  public Color getColor() {
    return colour;
  }

  /*---------------------------- Supplier stuff -------------------------------*/

  public Set getSuppliers() {
    try {
      return detailMap.keySet();
    } catch (NullPointerException e) {
      return new TreeSet();
    }
  }

  public MaterialValues getMaterialValues(String supplier) {
    return (MaterialValues)detailMap.get(supplier);
  }

  public String getDefaultSupplier() {
    String[] suppliers = SupplierDets.getDefaultSupplierList();
    for (int i = 0; i < suppliers.length; i++) {
      if (detailMap.containsKey(suppliers[i]))
        return suppliers[i];
    }
    return null;
  }

  public MaterialValues getDefaultValues(String supplier) {
    /*String[] suppliers = SupplierDets.getDefaultSupplierList();
    for (int i = 0; i < suppliers.length; i++) {
      if (detailMap.containsKey(suppliers[i]))
        return (MaterialValues)detailMap.get(suppliers[i]);
    }
    return new MaterialValues();*/
    return (MaterialValues)detailMap.get(supplier);
  }

  public MaterialValues getDefaultValuesWithInMaterialDets() {
    String[] suppliers = SupplierDets.getDefaultSupplierList();
    for (int i = 0; i < suppliers.length; i++) {
      if (detailMap.containsKey(suppliers[i]))
        return (MaterialValues)detailMap.get(suppliers[i]);
    }
    return new MaterialValues();
  }


  public boolean containsSupplier(String supplier) {
    return detailMap.containsKey(supplier);
  }

  public void addMaterialValues(MaterialValues mat, String supplier) {
    detailMap.put(supplier, mat);
    setModified(true);
  }

  public void removeSupplier(String supplier) {
    detailMap.remove(supplier);
  }
}
