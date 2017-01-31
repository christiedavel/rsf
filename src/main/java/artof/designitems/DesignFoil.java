package artof.designitems;

import artof.database.*;
import artof.designitems.dialogs.DesignDialogFoil;
import artof.designer.Designer;
import artof.utils.*;
import artof.materials.*;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DesignFoil extends DesignItem2 implements Cloneable, Externalizable {
  static final long serialVersionUID = 1334598612898369393L;

  protected static CodeMapper foilMapper = null;

  public DesignFoil() {
    //refreshItem();
    defColor = UserSettings.DEF_FOIL_COLOR;
    //createFoilMapper();
  }

  public DesignFoil(double limitWidth, double limitHeight) {
    super(limitWidth, limitHeight);
    refreshItem();
    defColor = UserSettings.DEF_FOIL_COLOR;
    createFoilMapper();
  }

  public static void createFoilMapper() {
    if (foilMapper == null)
      foilMapper = new CodeMapper(MaterialDets.MAT_FOIL);
  }

  public static void rebuildFoilMapper() {
    foilMapper = new CodeMapper(MaterialDets.MAT_FOIL);
  }


  public CodeMapper getCodeMapper() {
    return foilMapper;
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
    return (DesignFoil)super.clone();
  }

  public void drawSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    if (UserSettings2.DES_USE_IMAGES && materialDets != null && materialDets.isImageAvailable()) {
      /*super.drawSimbool(big, centerX, centerY, scaleFactor);
      double baseX = centerX - getOuterWidth() * scaleFactor / 2;
      double baseY = centerY - getOuterHeight() * scaleFactor / 2;
      double inBaseX = centerX - getInnerWidth() * scaleFactor / 2;
      double inBaseY = centerY - getInnerHeight() * scaleFactor / 2;

      Rectangle2D.Double outerRect = new Rectangle2D.Double(baseX, baseY, getOuterWidth() * scaleFactor, getOuterHeight() * scaleFactor);
      Rectangle2D.Double innerRect = new Rectangle2D.Double(baseX + leftGap * scaleFactor, baseY + topGap * scaleFactor,
                                                          getInnerWidth() * scaleFactor, getInnerHeight() * scaleFactor);

      BufferedImage sampleImage = materialDets.getSampleImage();
      TexturePaint p = new TexturePaint(sampleImage, new Rectangle(0, 0, sampleImage.getWidth(), sampleImage.getHeight()));
      big.setPaint(p);
      big.fill(outerRect);
      big.setColor(Color.white);
      big.fill(innerRect);*/

      double baseX = centerX - getOuterWidth() * scaleFactor / 2;
      double baseY = centerY - getOuterHeight() * scaleFactor / 2;
      double inBaseX = centerX - getInnerWidth() * scaleFactor / 2;
      double inBaseY = centerY - getInnerHeight() * scaleFactor / 2;

        // linker deel
        int[] xPoints = { (int)baseX,
                (int)(baseX + getLeftGap() * scaleFactor),
                (int)(baseX + getLeftGap() * scaleFactor),
                (int)baseX };
        int[] yPoints = { (int)baseY,
                (int)(baseY + getTopGap() * scaleFactor),
                (int)(baseY + (getOuterHeight() - getBottomGap()) * scaleFactor),
                (int)(baseY + getOuterHeight() * scaleFactor) };
        Polygon profile = new Polygon(xPoints, yPoints, 4);
        BufferedImage sampleImage = materialDets.getLeftImage(scaleFactor);
        double offset = baseX % sampleImage.getWidth();
        TexturePaint p = new TexturePaint(sampleImage, new Rectangle((int)offset, 0, sampleImage.getWidth(), sampleImage.getHeight()));
        big.setPaint(p);
        big.fill(profile);

        // regter deel
        xPoints[0] = (int)(baseX + getOuterWidth() * scaleFactor);
        xPoints[1] = (int)(baseX + getOuterWidth() * scaleFactor);
        xPoints[2] = (int)(baseX + (getOuterWidth() - getRightGap()) * scaleFactor);
        xPoints[3] = (int)(baseX + (getOuterWidth() - getRightGap()) * scaleFactor);
        yPoints[0] = (int)baseY;
        yPoints[1] = (int)(baseY + getOuterHeight() * scaleFactor);
        yPoints[2] = (int)(baseY + (getOuterHeight() - getBottomGap()) * scaleFactor);
        yPoints[3] = (int)(baseY + getTopGap() * scaleFactor);
        profile = new Polygon(xPoints, yPoints, 4);
        sampleImage = materialDets.getRightImage(scaleFactor);
        offset = (baseX + (getOuterWidth() - getRightGap()) * scaleFactor) % sampleImage.getWidth();
        p = new TexturePaint(sampleImage, new Rectangle((int)offset, 0, sampleImage.getWidth(), sampleImage.getHeight()));
        big.setPaint(p);
        big.fill(profile);

        // boonste deel
        xPoints[0] = (int)baseX;
        xPoints[1] = (int)(baseX + getOuterWidth() * scaleFactor);
        xPoints[2] = (int)(baseX + (getOuterWidth() - getRightGap()) * scaleFactor);
        xPoints[3] = (int)(baseX + getRightGap() * scaleFactor);
        yPoints[0] = (int)baseY;
        yPoints[1] = (int)baseY;
        yPoints[2] = (int)(baseY + getTopGap() * scaleFactor);
        yPoints[3] = (int)(baseY + getTopGap() * scaleFactor);
        profile = new Polygon(xPoints, yPoints, 4);
        sampleImage = materialDets.getTopImage(scaleFactor);
        offset = baseY % sampleImage.getHeight();
        p = new TexturePaint(sampleImage, new Rectangle(0, (int)offset, sampleImage.getWidth(), sampleImage.getHeight()));
        big.setPaint(p);
        big.fill(profile);

        // onderste deel
        xPoints[0] = (int)(baseX + getLeftGap() * scaleFactor);
        xPoints[1] = (int)(baseX + (getOuterWidth() - getRightGap()) * scaleFactor);
        xPoints[2] = (int)(baseX + getOuterWidth() * scaleFactor);
        xPoints[3] = (int)baseX;
        yPoints[0] = (int)(baseY + (getOuterHeight() - getBottomGap()) * scaleFactor);
        yPoints[1] = (int)(baseY + (getOuterHeight() - getBottomGap()) * scaleFactor);
        yPoints[2] = (int)(baseY + getOuterHeight() * scaleFactor);
        yPoints[3] = (int)(baseY + getOuterHeight() * scaleFactor);
        profile = new Polygon(xPoints, yPoints, 4);
        sampleImage = materialDets.getBottomImage(scaleFactor);
        offset = (baseY + (getOuterHeight() - getBottomGap()) * scaleFactor) % sampleImage.getHeight();
        p = new TexturePaint(sampleImage, new Rectangle(0, (int)offset, sampleImage.getWidth(), sampleImage.getHeight()));
        big.setPaint(p);
        big.fill(profile);

        //super.drawSimbool(big, centerX, centerY, scaleFactor);

    }

    // moet nog steeds lyne teken as selected
    super.drawSimbool(big, centerX, centerY, scaleFactor);
  }

  public void refreshItem() {
    try {
      MaterialDets dets = ArtofDB.getCurrentDB().getMaterial(itemCode);
      setColor(dets.getColor());
      thickness = dets.getDefaultValues(this.getDefaultSupplier()).getThickness();
    } catch (NullPointerException e) {
      // doen niks
    }
  }

  public boolean showPropertyDialog(int titleType, int type) {
    String title;
    if (titleType == Designer.TITLE_ADD) title = "Add Foil";
    else if (titleType == Designer.TITLE_INSERT) title = "Insert Foil";
    else title = "Edit Foil";

    DesignDialogFoil dialog = new DesignDialogFoil(title, this);
    if (!dialog.canceled()) setMaterialDets(ArtofDB.getCurrentDB().getMaterial(itemCode));
    return dialog.canceled();
  }

  public String getType() {
    return "Foil";
  }

  public int getDesignType() {
    return Designer.ITEM_FOIL;
  }

  public void calcItemSize(JPanel parent, LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
    double border = getMaterialDets().getDefaultValues(this.getDefaultSupplier()).getWidth();
    double width = getTopGap();

    if (border < width) {
      String mes = "The face specified for foil " + getMaterialDets().getItemCode();
      mes += " is too much.  There will be no overlap.";
      JOptionPane.showMessageDialog(parent, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    setDesignLeftGap(border);
    setDesignRightGap(border);
    setDesignTopGap(border);
    setDesignBottomGap(border);
    setDesignWidth(getInnerWidth() + 2 * border);
    setDesignHeight(getInnerHeight() + 2 * border);

    /*double border = methodPrefs.getMinOverlap() + getTopGap();
    setDesignLeftGap(border);
    setDesignRightGap(border);
    setDesignTopGap(border);
    setDesignBottomGap(border);
    setDesignWidth(getInnerWidth() + 2 * border);
    setDesignHeight(getInnerHeight() + 2 * border);*/
  }

  public void checkItemSize(LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
  }

  public void calcItemPrice(MethodPrefDets methodPrefs, BusPrefDets busPrefs, boolean stretching, boolean pasting) {
    double totalLength = 2.0 * (getDesignWidth() + getDesignHeight()) / 1000.0;

    MaterialValues mats = getMaterialDets().getDefaultValues(this.getDefaultSupplier());
    double price = busPrefs.getMarkupDecs() * mats.getCost() * mats.getCompFactor();
    setDesignPrice(price * totalLength);

    /*double foilLength = getMaterialDets().getDefaultValues().getLength();
    double length1 = getDesignHeight();
    double length2 = getDesignWidth();

    int count;
    if (length1 > foilLength / 2)
      count = 2;
    else
      count = 1;

    if (length2 > foilLength / 2)
      count += 2;
    else
      count += 1;

    double width = count * getDesignTopGap();
    double area = width * foilLength / 1000000;

    MaterialValues mats = getMaterialDets().getDefaultValues();
    double price = busPrefs.getMarkupDecs() * mats.getCost() * mats.getCompFactor();
    setDesignPrice(price * area);*/
  }

  public boolean isOversized() {
    return false;
  }
}
