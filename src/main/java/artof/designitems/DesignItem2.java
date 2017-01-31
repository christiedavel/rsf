package artof.designitems;
import artof.database.*;
import artof.utils.*;
import artof.materials.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
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

public abstract class DesignItem2 implements Cloneable, Externalizable, Serializable {
  static final long serialVersionUID = -7069762963487406707L;
  //static final long serialVersionUID = 7024816005682407656L;
 // private static final int fuckStick = 10;
  protected String itemCode;
  protected String ownCode;
  protected double leftGap = 0, rightGap = 0, topGap = 0, bottomGap = 0;
  protected double leftOffset = 0, rightOffset = 0, topOffset = 0, bottomOffset = 0;
  protected double limitWidth = 0, limitHeight = 0;
  protected boolean matchGaps = true, matchOffsets = true;
  protected double thickness = 0;
  protected Color color = UserSettings.DEF_COLOR;
  protected Color defColor = UserSettings.DEF_COLOR;
  protected boolean selected;
  protected static ArtofDB db_conn;
  //protected HashMap itemOwnMap = new HashMap();
  //protected HashMap ownItemMap = new HashMap();
  //protected HashMap itemColorMap = new HashMap();
  protected MaterialDets materialDets = null;

  protected double designHeight = 0;
  protected double designWidth = 0;
  protected double designTopGap = 0;
  protected double designBottomGap = 0;
  protected double designLeftGap = 0;
  protected double designRightGap = 0;
  protected double designHeightLength = 0;
  protected double designWidthLength = 0;

  protected double designPrice = 0;
  protected String defaultSupplier = "";

  // vir serialization na db
  public DesignItem2() {
    this.limitWidth = 0;
    this.limitHeight = 0;
  }

  public DesignItem2(double limitWidth, double limitHeight) {

    this.limitWidth = limitWidth;
    this.limitHeight = limitHeight;
  }

  public abstract void refreshItem();
  public abstract boolean showPropertyDialog(int titleType, int type);
  public abstract String getType();
  public abstract int getDesignType();
  public abstract CodeMapper getCodeMapper();


  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeObject(itemCode);
    out.writeObject(ownCode);
    out.writeDouble(leftGap);
    out.writeDouble(rightGap);
    out.writeDouble(topGap);
    out.writeDouble(bottomGap);
    out.writeDouble(leftOffset);
    out.writeDouble(rightOffset);
    out.writeDouble(topOffset);
    out.writeDouble(bottomOffset);
    out.writeDouble(limitWidth);
    out.writeDouble(limitHeight);
    out.writeBoolean(matchGaps);
    out.writeBoolean(matchOffsets);
    out.writeDouble(thickness);
    out.writeObject(color);
    out.writeObject(defColor);
    out.writeObject(materialDets);

    out.writeDouble(designHeight);
    out.writeDouble(designWidth);
    out.writeDouble(designTopGap);
    out.writeDouble(designBottomGap);
    out.writeDouble(designLeftGap);
    out.writeDouble(designRightGap);
    out.writeDouble(designHeightLength);
    out.writeDouble(designWidthLength);
    out.writeDouble(designPrice);

  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    itemCode = (String)in.readObject();
    ownCode = (String)in.readObject();
    leftGap = in.readDouble();
    rightGap = in.readDouble();
    topGap = in.readDouble();
    bottomGap = in.readDouble();
    leftOffset = in.readDouble();
    rightOffset = in.readDouble();
    topOffset = in.readDouble();
    bottomOffset = in.readDouble();
    limitWidth = in.readDouble();
    limitHeight = in.readDouble();
    matchGaps = in.readBoolean();
    matchOffsets = in.readBoolean();
    thickness = in.readDouble();
    color = (Color)in.readObject();
    defColor = (Color)in.readObject();
    materialDets = (MaterialDets)in.readObject();

    designHeight = in.readDouble();
    designWidth = in.readDouble();
    designTopGap = in.readDouble();
    designBottomGap = in.readDouble();
    designLeftGap = in.readDouble();
    designRightGap = in.readDouble();
    designHeightLength = in.readDouble();
    designWidthLength = in.readDouble();
    designPrice = in.readDouble();

  }

  public Object clone() {
    try {
      DesignItem2 clone = (DesignItem2)super.clone();
      clone.itemCode = itemCode;
      clone.ownCode = ownCode;
      clone.leftGap = leftGap;
      clone.rightGap = rightGap;
      clone.topGap = topGap;
      clone.bottomGap = bottomGap;
      clone.leftOffset = leftOffset;
      clone.rightOffset = rightOffset;
      clone.topOffset = topOffset;
      clone.bottomOffset = bottomOffset;
      clone.limitWidth = limitWidth;
      clone.limitHeight = limitHeight;
      clone.matchGaps = matchGaps;
      clone.matchOffsets = matchOffsets;
      clone.thickness = thickness;
      clone.color = new Color(color.getRed(), color.getGreen(), color.getBlue());
      clone.defColor = UserSettings.DEF_COLOR;
      clone.selected = selected;

      clone.designHeight = designHeight;
      clone.designWidth = designWidth;
      clone.designTopGap = designTopGap;
      clone.designBottomGap = designBottomGap;
      clone.designLeftGap = designLeftGap;
      clone.designRightGap = designRightGap;
      clone.designHeightLength = designHeightLength;
      clone.designWidthLength = designWidthLength;

      clone.designPrice = designPrice;

      clone.defaultSupplier = defaultSupplier;
      return clone;
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  public boolean containsPoint(double x, double y, double centerX, double centerY, double scaleFactor) {
    double baseX = centerX - getOuterWidth() * scaleFactor / 2;
    double baseY = centerY - getOuterHeight() * scaleFactor / 2;
    Rectangle2D.Double outerRect = new Rectangle2D.Double(baseX, baseY, getOuterWidth() * scaleFactor, getOuterHeight() * scaleFactor);
    Rectangle2D.Double innerRect = new Rectangle2D.Double(baseX + leftGap * scaleFactor, baseY + topGap * scaleFactor,
                                                          getInnerWidth() * scaleFactor, getInnerHeight() * scaleFactor);
    if (outerRect.contains(x, y) && !innerRect.contains(x, y))
      return true;
    else
      return false;
  }

  public void drawSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    double shifter;
    double baseX = centerX - getOuterWidth() * scaleFactor / 2;
    double baseY = centerY - getOuterHeight() * scaleFactor / 2;

    if (UserSettings.DES_DRAW_BORDER) shifter = 2.0;
    else shifter = 1.0;

    if (!UserSettings2.DES_USE_IMAGES || materialDets == null || !materialDets.isImageAvailable()) {
      Color drawColor;
      if (UserSettings.DES_COLOR_MODE == UserSettings.DES_COLOR_MODE_DEF) drawColor = defColor;
      else if (UserSettings.DES_COLOR_MODE == UserSettings.DES_COLOR_MODE_OWN) drawColor = color;
      else drawColor = UserSettings.DEF_COLOR;

      Rectangle2D.Double outerRect = new Rectangle2D.Double(baseX, baseY, getOuterWidth() * scaleFactor, getOuterHeight() * scaleFactor);
      Rectangle2D.Double innerRect = new Rectangle2D.Double(baseX + leftGap * scaleFactor, baseY + topGap * scaleFactor,
                                                          getInnerWidth() * scaleFactor, getInnerHeight() * scaleFactor);

      big.setColor(drawColor);
      big.setStroke(new BasicStroke(1.0f));
      if (UserSettings.DES_FILL_COLORS) {
        big.fill(outerRect);
        big.setColor(Color.white);
        big.fill(innerRect);

      } else {
        outerRect.setRect(baseX + shifter, baseY + shifter,
                          getOuterWidth() * scaleFactor - 2*shifter,
                          getOuterHeight() * scaleFactor- 2*shifter);
        innerRect.setRect(baseX + leftGap * scaleFactor - shifter, baseY + topGap * scaleFactor - shifter,
                          getInnerWidth() * scaleFactor + 2*shifter, getInnerHeight() * scaleFactor + 2*shifter);
        big.draw(outerRect);
        big.draw(innerRect);
      }

      if (UserSettings.DES_DRAW_BORDER) {
        big.setColor(UserSettings.DEF_COLOR);
        shifter = 1.0;
        outerRect.setRect(baseX + shifter, baseY + shifter,
                          getOuterWidth() * scaleFactor - 2*shifter, getOuterHeight() * scaleFactor- 2*shifter);
        innerRect.setRect(baseX + leftGap * scaleFactor - shifter, baseY + topGap * scaleFactor - shifter,
                          getInnerWidth() * scaleFactor + 2*shifter, getInnerHeight() * scaleFactor + 2*shifter);
        big.draw(outerRect);
        big.draw(innerRect);
      }
    }

    if (isSelected()) {
      big.setColor(UserSettings.SELECTED_COLOR);
      baseX += shifter;
      baseY += shifter;
      double endX = baseX + scaleFactor * getOuterWidth() - shifter * 2;
      double endY = baseY + scaleFactor * getOuterHeight() - shifter * 2;
      double inBaseX = baseX + scaleFactor * leftGap - shifter * 2;
      double inBaseY = baseY + scaleFactor * topGap - shifter * 2;
      double inEndX = endX - scaleFactor * rightGap + shifter * 2;
      double inEndY = endY - scaleFactor * bottomGap + shifter * 2;
      int delta = UserSettings.SELECTED_GAP;
      while (delta < scaleFactor * (getOuterHeight() + getOuterWidth())) {
        //kry eers lys van punte
        double x, y;
        TreeMap xyMap = new TreeMap();

        // lyn: y = -x + k met k = baseX + baseY + delta
        double k = (baseX + baseY + delta);

        // lyn 1: x = baseX
        x = baseX;
        y = -baseX + k;
        if (y > baseY && y < endY)
          xyMap.put(new Double(x), new Double(y));

        // lyn 2: y = endY
        y = endY;
        x = -endY + k;
        if (x > baseX && x < endX)
          xyMap.put(new Double(x), new Double(y));

        // lyn 3: x = endX
        x = endX;
        y = -endX + k;
        if (y > baseY && y < endY)
          xyMap.put(new Double(x), new Double(y));

        // lyn 4: y = baseY
        y = baseY;
        x = -baseY + k;
        if (x > baseX && x < endX)
          xyMap.put(new Double(x), new Double(y));

        // lyn 5: x = inBaseX
        x = inBaseX;
        y = -inBaseX + k;
        if (y > inBaseY && y < inEndY)
          xyMap.put(new Double(x), new Double(y));

        // lyn 6: y = inEndY
        y = inEndY;
        x = -inEndY + k;
        if (x > inBaseX && x < inEndX)
          xyMap.put(new Double(x), new Double(y));

        // lyn 7: x = inEndX
        x = inEndX;
        y = -inEndX + k;
        if (y > inBaseY && y < inEndY)
          xyMap.put(new Double(x), new Double(y));

        // lyn 8: y = inBaseY
        y = inBaseY;
        x = -inBaseY + k;
        if (x > inBaseX && x < inEndX)
          xyMap.put(new Double(x), new Double(y));

        try {
          Object[] keys = xyMap.keySet().toArray();
          double x0 = ((Double)keys[0]).doubleValue();
          double y0 = ((Double)xyMap.get(keys[0])).doubleValue();
          double x1 = ((Double)keys[1]).doubleValue();
          double y1 = ((Double)xyMap.get(keys[1])).doubleValue();
          big.drawLine((int)x0, (int)y0, (int)x1, (int)y1);

          double x2 = ((Double)keys[2]).doubleValue();
          double y2 = ((Double)xyMap.get(keys[2])).doubleValue();
          double x3 = ((Double)keys[3]).doubleValue();
          double y3 = ((Double)xyMap.get(keys[3])).doubleValue();
          big.drawLine((int)x2, (int)y2, (int)x3, (int)y3);
        } catch (ArrayIndexOutOfBoundsException e) {
          //los
        }
        delta += UserSettings.SELECTED_GAP;
      }

    }
  }

  public void printSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    double shifter = 0.0;
    double baseX = centerX - getOuterWidth() * scaleFactor / 2;
    double baseY = centerY - getOuterHeight() * scaleFactor / 2;

    Color drawColor = Color.black;
    Rectangle2D.Double outerRect = new Rectangle2D.Double(baseX, baseY, getOuterWidth() * scaleFactor, getOuterHeight() * scaleFactor);
    Rectangle2D.Double innerRect = new Rectangle2D.Double(baseX + leftGap * scaleFactor, baseY + topGap * scaleFactor,
                                                          getInnerWidth() * scaleFactor, getInnerHeight() * scaleFactor);
    big.setColor(drawColor);
    //big.setStroke(new BasicStroke(10f));
    outerRect.setRect(baseX + shifter, baseY + shifter,
                          getOuterWidth() * scaleFactor - 2*shifter,
                          getOuterHeight() * scaleFactor- 2*shifter);
    innerRect.setRect(baseX + leftGap * scaleFactor - shifter, baseY + topGap * scaleFactor - shifter,
                          getInnerWidth() * scaleFactor + 2*shifter, getInnerHeight() * scaleFactor + 2*shifter);
    big.draw(outerRect);
    big.draw(innerRect);

    big.setColor(UserSettings.DEF_COLOR);
    shifter = 1.0;
    outerRect.setRect(baseX + shifter, baseY + shifter,
                      getOuterWidth() * scaleFactor - 2*shifter, getOuterHeight() * scaleFactor- 2*shifter);
    innerRect.setRect(baseX + leftGap * scaleFactor - shifter, baseY + topGap * scaleFactor - shifter,
                      getInnerWidth() * scaleFactor + 2*shifter, getInnerHeight() * scaleFactor + 2*shifter);
    big.draw(outerRect);
    big.draw(innerRect);
  }


  public boolean mustDrawMeasurements() {
    return true;
  }

  // Get en Set funksies
  public MaterialDets getMaterialDets() {
    return materialDets;
  }
  public void setMaterialDets(MaterialDets item) {
    materialDets = item;
  }

  public String getItemCode() {
    return itemCode;
  }
  public void setItemCode(String code) {
    try {
      ownCode = getCodeMapper().getOwnCodeFromItemCode(code);
      itemCode = code;
    } catch (NullPointerException e) {}
  }

  public String getOwnCode() {
    return ownCode;
  }
  public void setOwnCode(String code) {
    try {
      itemCode = getCodeMapper().getItemCodeFromOwnCode(code);
      ownCode = code;
    } catch (NullPointerException e) {}
  }

  public boolean isSelected() {
    return selected;
  }
  public void setSelected(boolean sel) {
    selected = sel;
  }

  public void setThickness(double d) {
    thickness = d;
  }
  public double getThickness() {
    return thickness;
  }

  public void setColor(Color c) {
    color = c;
  }
  public Color getColor() {
    return color;
  }

  // Gaps
  public void setLeftGap(double gap) {
    leftGap = gap;
  }
  public double getLeftGap() {
    return leftGap;
  }

  public void setRightGap(double gap) {
    rightGap = gap;
  }
  public double getRightGap() {
    return rightGap;
  }

  public void setTopGap(double gap) {
    topGap = gap;
  }
  public double getTopGap() {
    return topGap;
  }

  public void setBottomGap(double gap) {
    bottomGap = gap;
  }
  public double getBottomGap() {
    return bottomGap;
  }

  public void setMatchGaps(boolean b) {
    matchGaps = b;
  }
  public boolean getMatchGaps() {
    return matchGaps;
  }

  // Offsets
  public void setLeftOffset(double gap) {
    leftOffset = gap;
  }
  public double getLeftOffset() {
    return leftOffset;
  }

  public void setRightOffset(double gap) {
    rightOffset = gap;
  }
  public double getRightOffset() {
    return rightOffset;
  }

  public void setTopOffset(double gap) {
    topOffset = gap;
  }
  public double getTopOffset() {
    return topOffset;
  }

  public void setBottomOffset(double gap) {
    bottomOffset = gap;
  }
  public double getBottomOffset() {
    return bottomOffset;
  }

  public void setMatchOffsets(boolean b) {
    matchOffsets = b;
  }
  public boolean getMatchOffsets() {
    return matchOffsets;
  }

  // Afmetings
  public void setLimitWidth(double d) {
    limitWidth = d;
  }
  public double getLimitWidth() {
    return limitWidth;
  }

  public void setLimitHeight(double d) {
    limitHeight = d;
  }
  public double getLimitHeight() {
    return limitHeight;
  }

  public double getOuterWidth() {
    return limitWidth + leftGap + rightGap + leftOffset + rightOffset;
  }
  public void setOuterWidth(double width) {
    leftGap = (width - limitWidth - leftOffset - rightOffset) / 2;
    rightGap = leftGap;
  }

  public double getInnerWidth() {
    return limitWidth + leftOffset + rightOffset;
  }

  public double getOuterHeight() {
    return limitHeight + topGap + bottomGap + topOffset + bottomOffset;
  }
  public void setOuterHeight(double height) {
    topGap = (height - limitHeight - topOffset - bottomOffset) / 2;
    bottomGap = topGap;
  }

  public double getInnerHeight() {
    return limitHeight + topOffset + bottomOffset;
  }


  /*--------------------------- Design Calculations ----------------------------*/

  public abstract void calcItemSize(JPanel parent, LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs);
  public abstract void checkItemSize(LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs);
  public abstract void calcItemPrice(MethodPrefDets methodPrefs, BusPrefDets busPrefs, boolean stretching, boolean pasting);

  public double getDesignHeight() {
    return designHeight;
  }
  public void setDesignHeight(double d) {
    designHeight = d;
  }

  public double getDesignWidth() {
    return designWidth;
  }
  public void setDesignWidth(double d) {
    designWidth = d;
  }

  public double getDesignTopGap() {
    return designTopGap;
  }
  public void setDesignTopGap(double d) {
    designTopGap = d;
  }

  public double getDesignBottomGap() {
    return designBottomGap;
  }
  public void setDesignBottomGap(double d) {
    designBottomGap = d;
  }

  public double getDesignLeftGap() {
    return designLeftGap;
  }
  public void setDesignLeftGap(double d) {
    designLeftGap = d;
  }

  public double getDesignRightGap() {
    return designRightGap;
  }
  public void setDesignRightGap(double d) {
    designRightGap = d;
  }

  public double getDesignHeightLength() {
    return designHeightLength;
  }
  public void setDesignHeightLength(double d) {
    designHeightLength = d;
  }

  public double getDesignWidthLength() {
    return designWidthLength;
  }
  public void setDesignWidthLength(double d) {
    designWidthLength = d;
  }

  public double getDesignPrice() {
    return designPrice;
  }

  public String getDefaultSupplier() {
    return defaultSupplier;
  }

  public void setDesignPrice(double d) {
    designPrice = d;
  }

  public void setDefaultSupplier(String defaultSupplier) {
    this.defaultSupplier = defaultSupplier;
  }

  public boolean isOversized() {
    float matLength = getMaterialDets().getDefaultValues(getDefaultSupplier()).getLength();
    float matWidth = getMaterialDets().getDefaultValues(getDefaultSupplier()).getWidth();
    if ((designHeight <= matLength && designWidth <= matWidth) ||
        (designHeight <= matWidth && designWidth <= matLength)) {
      return false;
    } else {
      return true;
    }
  }

  public void setRebateCompensation(boolean compensate, double rebate) {
  }
}





