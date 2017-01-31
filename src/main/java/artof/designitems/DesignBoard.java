package artof.designitems;

import artof.database.*;
import artof.designer.Designer;
import artof.utils.*;
import artof.designitems.dialogs.DesignBoardDialog;
import artof.materials.MaterialDets;
import artof.materials.MaterialValues;
import java.util.*;
import java.io.*;
import javax.swing.*;
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

public class DesignBoard extends DesignItem2 implements Cloneable, Externalizable {
  static final long serialVersionUID = -7205692381823858762L;

  protected boolean ovalBoard = false;
  protected boolean useCustomOverlaps = false;
  protected boolean matchOverlaps = true;
  protected double overlapTop = 0;
  protected double overlapBottom = 0;
  protected double overlapLeft = 0;
  protected double overlapRight = 0;

  protected static CodeMapper boardMapper = null;

  public DesignBoard() {
    //refreshItem();
    defColor = UserSettings.DEF_BOARD_COLOR;
    //createBoardMapper();
  }

  public DesignBoard(double limitWidth, double limitHeight) {
    super(limitWidth, limitHeight);
    refreshItem();
    defColor = UserSettings.DEF_BOARD_COLOR;
    createBoardMapper();
  }

  public static void createBoardMapper() {
    if (boardMapper == null)
      boardMapper = new CodeMapper(MaterialDets.MAT_BOARD);
  }

  public static void rebuildBoardMapper() {
    boardMapper = new CodeMapper(MaterialDets.MAT_BOARD);
  }


  public CodeMapper getCodeMapper() {
    return boardMapper;
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    super.writeExternal(out);
    out.writeBoolean(ovalBoard);
    out.writeBoolean(useCustomOverlaps);
    out.writeBoolean(matchOverlaps);
    out.writeDouble(overlapTop);
    out.writeDouble(overlapBottom);
    out.writeDouble(overlapLeft);
    out.writeDouble(overlapRight);

    try {
      out.writeObject(defaultSupplier);
    }
    catch (java.io.OptionalDataException e) {
    }

  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    super.readExternal(in);
    ovalBoard = in.readBoolean();
    useCustomOverlaps = in.readBoolean();
    matchOverlaps = in.readBoolean();
    overlapTop = in.readDouble();
    overlapBottom = in.readDouble();
    overlapLeft = in.readDouble();
    overlapRight = in.readDouble();
    try {
      defaultSupplier = (String) in.readObject();
    } catch (java.io.OptionalDataException e) {

    }
  }

  public Object clone() {
    return (DesignBoard)super.clone();
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
    if (titleType == Designer.TITLE_ADD) title = "Add Board";
    else if (titleType == Designer.TITLE_INSERT) title = "Insert Board";
    else title = "Edit Board";

    DesignBoardDialog dialog = new DesignBoardDialog(title, type, this);
    if (!dialog.canceled()) setMaterialDets(ArtofDB.getCurrentDB().getMaterial(itemCode));
    return dialog.canceled();
  }

  public String getType() {
    return "Board";
  }

  public int getDesignType() {
    return Designer.ITEM_BOARD;
  }

  public void drawSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    if (UserSettings2.DES_USE_IMAGES && materialDets != null && materialDets.isImageAvailable()) {
      //super.drawSimbool(big, centerX, centerY, scaleFactor);
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
      big.fill(innerRect);

    }

    // moet nog steeds lyne teken as selected
    super.drawSimbool(big, centerX, centerY, scaleFactor);
  }


  public boolean getOvalBoard() {
    return ovalBoard;
  }
  public void setOvalBoard(boolean b) {
    ovalBoard = b;
  }

  public boolean getUseCustomOverlaps() {
    return useCustomOverlaps;
  }
  public void setUseCustomOverlaps(boolean b) {
    useCustomOverlaps = b;
  }

  public boolean getMatchOverlaps() {
    return matchOverlaps;
  }
  public void setMatchOverlaps(boolean b) {
    matchOverlaps = b;
  }

  public double getOverlapTop() {
    return overlapTop;
  }
  public void setOverlapTop(double d) {
    overlapTop = d;
  }

  public double getOverlapBottom() {
    return overlapBottom;
  }
  public void setOverlapBottom(double d) {
    overlapBottom = d;
  }

  public double getOverlapLeft() {
    return overlapLeft;
  }
  public void setOverlapLeft(double d) {
    overlapLeft = d;
  }

  public double getOverlapRight() {
    return overlapRight;
  }
  public void setOverlapRight(double d) {
    overlapRight = d;
  }

  public void calcItemSize(JPanel parent, LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
    setDesignTopGap(getTopGap());
    setDesignBottomGap(getBottomGap());
    setDesignLeftGap(getLeftGap());
    setDesignRightGap(getRightGap());

    DesignItem2 nextItem;
    try {
      nextItem = (DesignItem2)itemList.get(nextIndex);

      // Moenie overlaps bereken vir sweefraam nie
      if (nextItem.getTopOffset() > 0 || nextItem.getBottomOffset() > 0 || nextItem.getLeftOffset() > 0 || nextItem.getRightOffset() > 0) {
        setDesignHeight(getInnerHeight() + getDesignTopGap() + getDesignBottomGap());
        setDesignWidth(getInnerWidth() + getDesignLeftGap() + getDesignRightGap());
        return;
      }

      // Moenie overlaps bereken as daar net 'n backing board volg nie
      if (nextItem.getDesignType() == Designer.ITEM_BACK || nextItem.getDesignType() == Designer.ITEM_GLASS) {
        setDesignHeight(getInnerHeight() + getDesignTopGap() + getDesignBottomGap());
        setDesignWidth(getInnerWidth() + getDesignLeftGap() + getDesignRightGap());
        return;
      }

      if (nextItem.getDesignType() == Designer.ITEM_FRAME) {

        setDesignTopGap(getDesignTopGap() + nextItem.getMaterialDets().getDefaultValues(nextItem.getDefaultSupplier()).getRebate());
        setDesignBottomGap(getDesignBottomGap() + nextItem.getMaterialDets().getDefaultValues(nextItem.getDefaultSupplier()).getRebate());
        setDesignLeftGap(getDesignLeftGap() + nextItem.getMaterialDets().getDefaultValues(nextItem.getDefaultSupplier()).getRebate());
        setDesignRightGap(getDesignRightGap() + nextItem.getMaterialDets().getDefaultValues(nextItem.getDefaultSupplier()).getRebate());

        // Check of die van toepassing is op boards ?????
        double adjH = methodPrefs.getGlassToFrameLineWidth((float)Math.max(getDesignHeight(), getDesignWidth()));
        double adjW = methodPrefs.getGlassToFrameLineHeight((float)Math.max(getDesignHeight(), getDesignWidth()));
        setDesignTopGap(getDesignTopGap() - adjH / 2);
        setDesignBottomGap(getDesignBottomGap() - adjH / 2);
        setDesignLeftGap(getDesignLeftGap() - adjW / 2);
        setDesignRightGap(getDesignRightGap() - adjW / 2);

        setDesignHeight(getInnerHeight() + getDesignTopGap() + getDesignBottomGap());
        setDesignWidth(getInnerWidth() + getDesignLeftGap() + getDesignRightGap());
        return;
      }
    } catch (IndexOutOfBoundsException e) {
      setDesignHeight(getInnerHeight() + getDesignTopGap() + getDesignBottomGap());
      setDesignWidth(getInnerWidth() + getDesignLeftGap() + getDesignRightGap());
      return;
    }

    if (getUseCustomOverlaps()) {
      setDesignTopGap(getDesignTopGap() + getOverlapTop());
      setDesignBottomGap(getDesignBottomGap() + getOverlapBottom());
      setDesignLeftGap(getDesignLeftGap() + getOverlapLeft());
      setDesignRightGap(getDesignRightGap() + getOverlapRight());

    } else if (methodPrefs.getMethodType() == MethodPrefDets.BORDER_FULL) {
      ListIterator it = itemList.listIterator(nextIndex);
      while (it.hasNext()) {
        DesignItem2 item = (DesignItem2)it.next();

        if (item.getTopOffset() > 0 || item.getBottomOffset() > 0 ||
            item.getLeftOffset() > 0 || item.getRightOffset() > 0) {
          break;

        } else if (item.getDesignType() == Designer.ITEM_BOARD ||
            item.getDesignType() == Designer.ITEM_SLIP ||
            item.getDesignType() == Designer.ITEM_FOIL) {
          setDesignTopGap(item.getTopGap() + getDesignTopGap());
          setDesignBottomGap(item.getBottomGap() + getDesignBottomGap());
          setDesignLeftGap(item.getLeftGap() + getDesignLeftGap());
          setDesignRightGap(item.getRightGap() + getDesignRightGap());

        } else if (item != null && item.getDesignType() == Designer.ITEM_FRAME) {
          double rebate = item.getMaterialDets().getDefaultValues(item.getDefaultSupplier()).getRebate();
          setDesignTopGap(getDesignTopGap() + rebate);
          setDesignBottomGap(getDesignBottomGap() + rebate);
          setDesignLeftGap(getDesignLeftGap() + rebate);
          setDesignRightGap(getDesignRightGap() + rebate);

          // Check of die van toepassing is op boards
          double adjH = methodPrefs.getGlassToFrameLineWidth((float)Math.max(getDesignHeight(), getDesignWidth()));
          double adjW = methodPrefs.getGlassToFrameLineHeight((float)Math.max(getDesignHeight(), getDesignWidth()));
          setDesignTopGap(getDesignTopGap() - adjH / 2);
          setDesignBottomGap(getDesignBottomGap() - adjH / 2);
          setDesignLeftGap(getDesignLeftGap() - adjW / 2);
          setDesignRightGap(getDesignRightGap() - adjW / 2);
          break;
        }
      }

    } else {

      if (methodPrefs.getMethodType() == MethodPrefDets.BORDER_CALC) {
        // check eers hoeveel plek beskikbaar is
        double overlapSpaceAvailable = 0;
        ListIterator it = itemList.listIterator(nextIndex);
        while (it.hasNext()) {
          DesignItem2 item = (DesignItem2)it.next();
          if (item.getDesignType() != Designer.ITEM_FRAME) {
            double minner = Math.min(item.getTopGap(), item.getBottomGap());
            minner = Math.min(item.getLeftGap(), minner);
            minner = Math.min(item.getRightGap(), minner);
            overlapSpaceAvailable += minner;

          } else {
            overlapSpaceAvailable += item.getMaterialDets().getDefaultValues(item.getDefaultSupplier()).getRebate();
            double adjH = methodPrefs.getGlassToFrameLineWidth((float)Math.max(getDesignHeight(), getDesignWidth()));
            overlapSpaceAvailable -= adjH * 2.0;
            break;
          }
        }

        double minOverlap;
        if (nextItem.getDesignType() == Designer.ITEM_SLIP) {
          minOverlap = methodPrefs.getMinOverlap() + Math.abs(methodPrefs.getFBOverlapWithSlip() - methodPrefs.getFBOverlapNoSlip());
        } else {
          minOverlap = methodPrefs.getMinOverlap();
        }

        double overlap = Math.sqrt(getOuterHeight() + getOuterWidth()) * methodPrefs.getOverlapAdjFact();
        if (overlap < minOverlap) {
          overlap =  minOverlap;

          if (overlap > overlapSpaceAvailable) {
            overlap = overlapSpaceAvailable;
          } else {
            // Moenie die boodskap wys nie.  Vat summier die minimum overlap
            //String mes = "The overlap for board " + getMaterialDets().getItemCode();
            //mes += " is smaller than the minimum overlap.  The minimum overlap will be used.";
            //JOptionPane.showMessageDialog(parent, mes, "Warning", JOptionPane.WARNING_MESSAGE);
          }
        }

        setDesignTopGap(overlap + getDesignTopGap());
        setDesignBottomGap(overlap + getDesignBottomGap());
        setDesignLeftGap(overlap + getDesignLeftGap());
        setDesignRightGap(overlap + getDesignRightGap());

      } else if (methodPrefs.getMethodType() == MethodPrefDets.BORDER_FIXED) {

        // Kyk of daar 'n slip in die design is
        double border;
        boolean hasSlip = false;
        Iterator it = itemList.iterator();
        while (it.hasNext()) {
          DesignItem2 item = (DesignItem2)it.next();
          if (item.getDesignType() == Designer.ITEM_SLIP) {
            hasSlip = true;
            break;
          }
        }
        if (hasSlip) border = methodPrefs.getFBOverlapWithSlip();
        else border = methodPrefs.getFBOverlapNoSlip();

        double minOverlap = methodPrefs.getMinOverlap();
        if (nextItem != null) {
          boolean warning = false;
          if (border < getDesignTopGap() + minOverlap) {
            warning = true;
            setDesignTopGap(minOverlap + getDesignTopGap());
          } else
            setDesignTopGap(border);

          if (border < getDesignBottomGap() + minOverlap) {
            warning = true;
            setDesignBottomGap(minOverlap + getDesignBottomGap());
          } else
            setDesignBottomGap(border);

          if (border < getDesignLeftGap() + minOverlap) {
            warning = true;
            setDesignLeftGap(minOverlap + getDesignLeftGap());
          } else
            setDesignLeftGap(border);

          if (border < getDesignRightGap() + minOverlap) {
            warning = true;
            setDesignRightGap(minOverlap + getDesignRightGap());
          } else
            setDesignRightGap(border);

          if (warning) {
            String mes = "The overlap for board " + getMaterialDets().getItemCode();
            mes += " is smaller than the minimum overlap.  The minimum overlap will be used.";
            JOptionPane.showMessageDialog(parent, mes, "Warning", JOptionPane.WARNING_MESSAGE);
          }
        }

      }


      if (nextItem != null && methodPrefs.getFullBottomsBool()) {
        setDesignBottomGap(getBottomGap());
        ListIterator it = itemList.listIterator(nextIndex);
        while (it.hasNext()) {
          DesignItem2 item = (DesignItem2)it.next();
          if (item.getDesignType() == Designer.ITEM_BOARD) {
            setDesignBottomGap(item.getBottomGap() + getDesignBottomGap());
          } else if (item.getDesignType() == Designer.ITEM_FRAME) {
            // Check of die van toepassing is op boards ???????
            double adjH = methodPrefs.getGlassToFrameLineWidth((float)Math.max(getDesignHeight(), getDesignWidth()));
            setDesignBottomGap(getDesignBottomGap() + item.getMaterialDets().getDefaultValues(item.getDefaultSupplier()).getRebate() - adjH / 2.0);
            break;
          }
        }
      }
    }

    setDesignHeight(getInnerHeight() + getDesignTopGap() + getDesignBottomGap());
    setDesignWidth(getInnerWidth() + getDesignLeftGap() + getDesignRightGap());
  }

  public void checkItemSize(LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
    try {
      DesignItem2 nextItem = (DesignItem2)itemList.get(nextIndex);
      if (nextItem.getDesignType() == Designer.ITEM_FRAME)
        return;

      double topSpace = 0;
      double bottomSpace = 0;
      double leftSpace = 0;
      double rightSpace = 0;
      ListIterator it = itemList.listIterator(nextIndex);
      while (it.hasNext()) {
        DesignItem2 item =(DesignItem2)it.next();
        topSpace += item.getDesignTopGap();
        bottomSpace += item.getDesignBottomGap();
        leftSpace += item.getDesignLeftGap();
        rightSpace += item.getDesignRightGap();
      }

      if (leftSpace < getDesignLeftGap() - getLeftGap())
        setDesignLeftGap(leftSpace + getLeftGap());

      if (rightSpace < getDesignRightGap() - getRightGap())
        setDesignRightGap(rightSpace + getRightGap());

      if (topSpace < getDesignTopGap() - getTopGap())
        setDesignTopGap(topSpace + getTopGap());

      if (bottomSpace < getDesignBottomGap() - getBottomGap())
        setDesignBottomGap(bottomSpace + getBottomGap());

      setDesignHeight(getInnerHeight() + getDesignTopGap() + getDesignBottomGap());
      setDesignWidth(getInnerWidth() + getDesignLeftGap() + getDesignRightGap());
    } catch (IndexOutOfBoundsException e) {
      return;
    }
  }

  public void calcItemPrice(MethodPrefDets methodPrefs, BusPrefDets busPrefs, boolean stretching, boolean pasting) {
    MaterialDets boardDets = getMaterialDets();
    double resUCM = methodPrefs.getResBoardLength();
    double matUCM = boardDets.getDefaultValues(this.getDefaultSupplier()).getLength() + boardDets.getDefaultValues(this.getDefaultSupplier()).getWidth();
    double boardUCM = getDesignWidth() + getDesignHeight();


    MaterialValues mats = getMaterialDets().getDefaultValues(this.
        getDefaultSupplier());
    double price = busPrefs.getMarkupBoards() * mats.getCost() *
        mats.getCompFactor();

    double totalArea;
    if (matUCM - boardUCM < resUCM)
      totalArea = boardDets.getDefaultValues(this.getDefaultSupplier()).
          getWidth() *
          boardDets.getDefaultValues(this.getDefaultSupplier()).getLength();
    else
      totalArea = getDesignWidth() * getDesignHeight();

    totalArea /= 1000000;
    price *= totalArea;
    setDesignPrice(price);


    /*MaterialDets boardDets = getMaterialDets();
    double resX = boardDets.getDefaultValues().getWidth() - methodPrefs.getResBoardWidth();
    double resY = boardDets.getDefaultValues().getLength() - methodPrefs.getResBoardLength();

    double price = busPrefs.getMarkupBoards() * boardDets.getDefaultValues().getCost();
    double totalArea;
    if ((getDesignWidth() > resX && getDesignHeight() > resY) ||
        (getDesignWidth() > resY && getDesignHeight() > resX))
      totalArea = boardDets.getDefaultValues().getWidth() * boardDets.getDefaultValues().getLength();
    else
      totalArea = getDesignWidth() * getDesignHeight();

    totalArea /= 1000000;
    price *= totalArea;
    setDesignPrice(price);*/
  }
}

















