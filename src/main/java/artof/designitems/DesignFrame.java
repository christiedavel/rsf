package artof.designitems;
import artof.database.*;
import artof.utils.*;
import artof.designer.Designer;
import artof.designitems.dialogs.DesignDialogFrames;
import artof.materials.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DesignFrame extends DesignItem2 implements Cloneable, Externalizable {
  static final long serialVersionUID = 536631154699809243L;
  protected static CodeMapper frameMapper = null;

  public DesignFrame() {
    //refreshItem();
    defColor = UserSettings.DEF_FRAME_COLOR;
    //createFrameMapper();
  }

  public DesignFrame(double limitWidth, double limitHeight) {
    super(limitWidth, limitHeight);
    refreshItem();
    defColor = UserSettings.DEF_FRAME_COLOR;
    createFrameMapper();
  }

  public static void createFrameMapper() {
    if (frameMapper == null)
      frameMapper = new CodeMapper(MaterialDets.MAT_FRAME);
  }

  public static void rebuildFrameMapper() {
    frameMapper = new CodeMapper(MaterialDets.MAT_FRAME);
  }


  public CodeMapper getCodeMapper() {
    return frameMapper;
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
    return (DesignFrame)super.clone();
  }

  public void refreshItem() {
    try {
      MaterialDets dets = ArtofDB.getCurrentDB().getMaterial(itemCode);
      setColor(dets.getColor());
      thickness = dets.getDefaultValues(this.getDefaultSupplier()).getThickness();
      leftGap = rightGap = topGap = bottomGap = dets.getDefaultValues(this.getDefaultSupplier()).getWidth();
    } catch (NullPointerException e) {
      // doen niks
    }
  }

  public boolean showPropertyDialog(int titleType, int type) {
    String title;
    if (titleType == Designer.TITLE_ADD) title = "Add Frame";
    else if (titleType == Designer.TITLE_INSERT) title = "Insert Frame";
    else title = "Edit Frame";

    DesignDialogFrames dialog = new DesignDialogFrames(title, type, this);
    if (!dialog.canceled()) setMaterialDets(ArtofDB.getCurrentDB().getMaterial(itemCode));
    return dialog.canceled();
  }

  public String getType() {
    return "Frame";
  }


  public void printSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    double baseX = centerX - getOuterWidth() * scaleFactor / 2;
    double baseY = centerY - getOuterHeight() * scaleFactor / 2;
    double inBaseX = centerX - getInnerWidth() * scaleFactor / 2;
    double inBaseY = centerY - getInnerHeight() * scaleFactor / 2;

    super.printSimbool(big, centerX, centerY, scaleFactor);
    big.setColor(color.black);
    //big.setStroke(new BasicStroke(0.5f));
    double shifter = 0.0;
    big.drawLine((int)(baseX + shifter),
                 (int)(baseY + shifter),
                 (int)(inBaseX - shifter),
                 (int)(inBaseY - shifter));

    big.drawLine((int)(baseX + getOuterWidth() * scaleFactor - shifter),
                 (int)(baseY + shifter),
                 (int)(inBaseX + getInnerWidth() * scaleFactor + shifter),
                 (int)(inBaseY - shifter));

    big.drawLine((int)(baseX + getOuterWidth() * scaleFactor - shifter),
                 (int)(baseY + getOuterHeight() * scaleFactor - shifter),
                 (int)(inBaseX + getInnerWidth() * scaleFactor + shifter),
                 (int)(inBaseY + getInnerHeight() * scaleFactor + shifter));

    big.drawLine((int)(baseX + shifter),
                 (int)(baseY + getOuterHeight() * scaleFactor - shifter),
                 (int)(inBaseX - shifter),
                 (int)(inBaseY + getInnerHeight() * scaleFactor + shifter));
  }


  public void drawSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    double baseX = centerX - getOuterWidth() * scaleFactor / 2;
    double baseY = centerY - getOuterHeight() * scaleFactor / 2;
    double inBaseX = centerX - getInnerWidth() * scaleFactor / 2;
    double inBaseY = centerY - getInnerHeight() * scaleFactor / 2;

    if (UserSettings.DES_DRAW_BORDER && !(UserSettings2.DES_USE_IMAGES && materialDets != null && materialDets.isImageAvailable())) {
      super.drawSimbool(big, centerX, centerY, scaleFactor);
      big.setColor(UserSettings.DEF_COLOR);
      double shifter = 2.0;
      big.drawLine((int)(baseX + shifter),
                   (int)(baseY + shifter),
                   (int)(inBaseX - shifter),
                   (int)(inBaseY - shifter));

      big.drawLine((int)(baseX + getOuterWidth() * scaleFactor - shifter),
                   (int)(baseY + shifter),
                   (int)(inBaseX + getInnerWidth() * scaleFactor + shifter),
                   (int)(inBaseY - shifter));

      big.drawLine((int)(baseX + getOuterWidth() * scaleFactor - shifter),
                   (int)(baseY + getOuterHeight() * scaleFactor - shifter),
                   (int)(inBaseX + getInnerWidth() * scaleFactor + shifter),
                   (int)(inBaseY + getInnerHeight() * scaleFactor + shifter));

      big.drawLine((int)(baseX + shifter),
                   (int)(baseY + getOuterHeight() * scaleFactor - shifter),
                   (int)(inBaseX - shifter),
                   (int)(inBaseY + getInnerHeight() * scaleFactor + shifter));

    }

    if (UserSettings2.DES_USE_IMAGES && materialDets != null && materialDets.isImageAvailable()) {
      //if (UserSettings2.DES_USE_IMAGES) {
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
      //}
    }

    super.drawSimbool(big, centerX, centerY, scaleFactor);
  }

  public int getDesignType() {
    return Designer.ITEM_FRAME;
  }

  public void calcItemSize(JPanel parent, LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
    double width = getMaterialDets().getDefaultValues(this.getDefaultSupplier()).getWidth();
    setDesignTopGap(width);
    setDesignBottomGap(width);
    setDesignLeftGap(width);
    setDesignRightGap(width);
    setDesignWidth(getInnerWidth() + 2 * width);
    setDesignHeight(getInnerHeight() + 2 * width);
  }

  public void checkItemSize(LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
  }

  public void calcItemPrice(MethodPrefDets methodPrefs, BusPrefDets busPrefs, boolean stretching, boolean pasting) {
    double totalLength = 2 * (getDesignWidth() + getDesignHeight()) / 1000;
    MaterialValues mats = getMaterialDets().getDefaultValues(this.getDefaultSupplier());
    double price = busPrefs.getMarkupFrames() * mats.getCost() * mats.getCompFactor() * mats.getExqFactor();
    setDesignPrice(price * totalLength);
  }

  public boolean isOversized() {
    return false;
  }
}

















