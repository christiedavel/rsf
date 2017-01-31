package artof.designitems;
import artof.database.*;
import artof.designer.Designer;
import java.io.*;
import artof.utils.*;
import artof.designitems.dialogs.DesignDialogArtWork;
import java.awt.*;
import java.util.*;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import artof.materials.MaterialDets;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DesignArtWork extends DesignItem2 implements Cloneable, Externalizable {
  static final long serialVersionUID = -5704090781865969129L;

  private String picture;
  private Image image;
  private final static String[] pictures = { "None" };
  private boolean newImage = false;

  boolean compensateRebate = false;
  double rebate = 0;

  public DesignArtWork() {
    //refreshItem();
    color = UserSettings.DEF_ARTWORK_COLOR;
    defColor = UserSettings.DEF_ARTWORK_COLOR;
  }

  public DesignArtWork(double limitWidth, double limitHeight) {
    super(limitWidth, limitHeight);
    refreshItem();
    color = UserSettings.DEF_ARTWORK_COLOR;
    defColor = UserSettings.DEF_ARTWORK_COLOR;
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    super.writeExternal(out);
    out.writeObject(picture);
    try {
      out.writeObject(defaultSupplier);
    }
      catch (java.io.OptionalDataException e) {
    }
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    super.readExternal(in);
    picture = (String)in.readObject();
    try {
      defaultSupplier = (String) in.readObject();
    } catch (java.io.OptionalDataException e) {
    }

  }

  public Object clone() {
    try {
      DesignArtWork clone = (DesignArtWork)super.clone();
      clone.picture = picture;
      return clone;
    } catch (NullPointerException e) {
      return null;
    }
  }

  public CodeMapper getCodeMapper() {
    return null;
  }

  public void refreshItem() {
    limitWidth = 0;
    limitHeight = 0;
    leftOffset = 0;
    rightOffset = 0;
    topOffset = 0;
    bottomOffset = 0;
    leftGap = 100;
    rightGap = 100;
    topGap = 100;
    bottomGap = 100;
  }

  public boolean showPropertyDialog(int titleType, int type) {
    String title;
    if (titleType == Designer.TITLE_ADD) title = "Add Artwork";
    else if (titleType == Designer.TITLE_INSERT) title = "Insert Artwork";
    else title = "Edit Artwork";

    DesignDialogArtWork dialog = new DesignDialogArtWork(title, this);
    return dialog.canceled();
  }

  public String getType() {
    return "Artwork";
  }

  public void setPicture(String a) {
    if ((picture == null && a != null) || (picture != null && !picture.equals(a))) {
      picture = a;
      newImage = true;
    }
  }
  public String getPicture() {
    return picture;
  }
  public String[] getPictures() {
    return pictures;
  }

  public ArrayList getArtists() {
    return ArtofDB.getCurrentDB().getArtists(UserSettings.ARTIST_SORTER);
  }

  public void drawSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    super.drawSimbool(big, centerX, centerY, scaleFactor);
  }

  public void drawSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor, Designer p) {
    super.drawSimbool(big, centerX, centerY, scaleFactor);
    if (picture.equals("None"))
      return;

    double width = getOuterWidth() * scaleFactor;// - 6;
    double height = getOuterHeight() * scaleFactor;// - 6;

    if (image == null || newImage) {
      String imagePath = "images/Artworks/" + picture;
      Toolkit t = Toolkit.getDefaultToolkit();
      image = t.createImage(imagePath);
      newImage = false;
    }
    double imageWidth = image.getWidth(p);
    double imageHeight = image.getHeight(p);
    double xFact = width / imageWidth;
    double yFact = height / imageHeight;
    double xOffset = centerX - imageWidth * xFact / 2;
    double yOffset = centerY - imageHeight* yFact / 2;
    AffineTransform transform = new AffineTransform(xFact, 0, 0, yFact, xOffset, yOffset);
    big.drawImage(image, transform, p);
  }

  public int getDesignType() {
    return Designer.ITEM_ARTWORK;
  }

  public void calcItemSize(JPanel parent, LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
    /*if (compensateRebate) {
      double adjH = methodPrefs.getGlassToFrameLineWidth((float)Math.max(getDesignHeight(), getDesignWidth()));
      double adjW = methodPrefs.getGlassToFrameLineHeight((float)Math.max(getDesignHeight(), getDesignWidth()));
      setDesignHeight(getOuterHeight() - adjH);
      setDesignWidth(getOuterWidth() - adjW);

    } else {
      setDesignHeight(getOuterHeight());
      setDesignWidth(getOuterWidth());
    }*/
    setDesignHeight(getRealOuterHeight());
    setDesignWidth(getRealOuterWidth());
  }

  public void checkItemSize(LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
  }

  public void calcItemPrice(MethodPrefDets methodPrefs, BusPrefDets busPrefs, boolean stretching, boolean pasting) {
    float price = 0;
    if (stretching)
      price += busPrefs.getStretchCharge((float)getOuterWidth(), (float)getOuterHeight());

    if (pasting)
      price += busPrefs.getPastCharge((float)getOuterWidth(), (float)getOuterHeight());

    setDesignPrice(price);
  }

  public boolean extractStock(artof.dialogs.ProgressDialog dialog, int progressBase, int designID) {
    dialog.setValue2(progressBase + 100);
    return true;
  }

  public ArrayList getOffCutList(ArrayList stockSelected) {
    return new ArrayList();
  }

  public boolean isOversized() {
    return false;
  }


  // moet rebate van grote aftek as net raam direk op artwork volg
  public void setRebateCompensation(boolean compensate, double rebate) {
    compensateRebate = compensate;
    this.rebate = rebate;
  }

  public double getOuterWidth() {
    if (compensateRebate) {
      return limitWidth + leftGap + rightGap + leftOffset + rightOffset - 2 * rebate;

    } else {
      return limitWidth + leftGap + rightGap + leftOffset + rightOffset;
    }
  }

  public double getOuterHeight() {
    if (compensateRebate) {
      return limitHeight + topGap + bottomGap + topOffset + bottomOffset - 2 * rebate;

    } else {
      return limitHeight + topGap + bottomGap + topOffset + bottomOffset;
    }
  }

  public double getRealOuterWidth() {
    return limitWidth + leftGap + rightGap + leftOffset + rightOffset;
  }

  public double getRealOuterHeight() {
    return limitHeight + topGap + bottomGap + topOffset + bottomOffset;
  }

  public void setLimitWidth(double d) {
    limitWidth = d;
  }

  public void setLimitHeight(double d) {
    limitHeight = d;
  }
  public void setOuterWidth(double width) {
    leftGap = (width - limitWidth - leftOffset - rightOffset) / 2;
    rightGap = leftGap;
  }

  public void setOuterHeight(double height) {
    topGap = (height - limitHeight - topOffset - bottomOffset) / 2;
    bottomGap = topGap;
  }

}









