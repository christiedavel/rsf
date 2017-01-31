package artof.designitems;
import artof.designer.Designer;
import artof.designitems.dialogs.DesignDialogBox;
import artof.utils.*;
import artof.database.*;
import artof.materials.*;
import java.util.*;
import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.text.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.JPanel;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DesignBox extends DesignGlassAndBack implements Cloneable, Externalizable {
  private double boxDepth = 0;

  public DesignBox() {
    super();
    title = "Box";
  }

  public DesignBox(double limitWidth, double limitHeight, LinkedList items) {
    super(limitWidth, limitHeight, items);
    title = "Box";
  }

  public Object clone() {
    DesignBox clone = (DesignBox)super.clone();
    clone.boxDepth = boxDepth;
    return clone;
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    super.writeExternal(out);
    out.writeDouble(boxDepth);
    try {
      out.writeObject(defaultSupplier);
    }
    catch (java.io.OptionalDataException e) {
    }

  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    super.readExternal(in);
    boxDepth = in.readDouble();
    try {
      defaultSupplier = (String) in.readObject();
    } catch (java.io.OptionalDataException e) {

    }

  }

  public String getType() {
    return "Back";
  }

  public int getDesignType() {
    return Designer.ITEM_BACK;
  }

  public double getBoxDepth() {
    return boxDepth;
  }
  public void setBoxDepth(double d) {
    boxDepth = d;
  }

  public void printSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    String text = "Box Depth = " + Utils.getCurrencyFormat(getBoxDepth());
    Font font = new Font("Times New Roman", Font.PLAIN, 9);
    FontRenderContext frc = big.getFontRenderContext();
    TextLayout layout = new TextLayout(text, font, frc);
    //Rectangle2D bounds = layout.getBounds();
    //int y += bounds.getHeight() / 2;
    //int x -= bounds.getWidth() / 2;
    layout.draw(big, (float)(centerX * 2 - 70), (float)centerY);

    /*drawSimbool(big, centerX, centerY, scaleFactor);*/
  }

  public void drawSimbool(Graphics2D big, double centerX, double centerY, double scaleFactor) {
    String text = "Box Depth = " + Utils.getCurrencyFormat(getBoxDepth());

    try {
      AttributedString string = new AttributedString(text, new Hashtable());
      AttributedCharacterIterator paragraph = string.getIterator();
      AffineTransform transform = new AffineTransform(0.75, 0, 0, 0.75, 0, 0);
      FontRenderContext frc = new FontRenderContext(transform, false, false);
      LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, frc);
      int paragraphStart = paragraph.getBeginIndex();
      int paragraphEnd = paragraph.getEndIndex();

      lineMeasurer.setPosition(paragraphStart);
      TextLayout layout = lineMeasurer.nextLayout(300);
      float drawPosY = (float)(2 * centerY - 10) + layout.getAscent() / 2;
      lineMeasurer.setPosition(paragraphStart);
      while (lineMeasurer.getPosition() < paragraphEnd) {
        layout = lineMeasurer.nextLayout(300);
        layout.draw(big, 300.f - layout.getAdvance() / 2, drawPosY);
        drawPosY += layout.getAscent();
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
  }

  public boolean showPropertyDialog(int titleType, int type) {
    DesignDialogBox dialog;
    if (titleType == Designer.TITLE_EDIT) dialog = new DesignDialogBox("Edit " + title, this);
    else dialog = new DesignDialogBox("Add " + title, this);

    if (!dialog.canceled()) setMaterialDets(ArtofDB.getCurrentDB().getMaterial(itemCode));
    return dialog.canceled();
  }

  public void calcItemSize(JPanel parent, LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
    super.calcItemSize(parent, itemList, nextIndex, methodPrefs);
    setDesignHeight(getDesignHeight() + 2 * getBoxDepth());
    setDesignWidth(getDesignWidth() + 2 * getBoxDepth());
  }

  public void calcItemPrice(MethodPrefDets methodPrefs, BusPrefDets busPrefs, boolean stretching, boolean pasting) {
    MaterialDets dets = getMaterialDets();
    double resX = dets.getDefaultValues(this.getDefaultSupplier()).getWidth() - methodPrefs.getResBackWidth();
    double resY = dets.getDefaultValues(this.getDefaultSupplier()).getLength() - methodPrefs.getResBackLength();

    MaterialValues mats = getMaterialDets().getDefaultValues(this.getDefaultSupplier());
    double price = busPrefs.getMarkupBoards() * mats.getCost() * mats.getCompFactor();
    double totalArea;
    if ((getDesignWidth() > resX && getDesignHeight() > resY) ||
        (getDesignWidth() > resY && getDesignHeight() > resX))
      totalArea = dets.getDefaultValues(this.getDefaultSupplier()).getWidth() * dets.getDefaultValues(this.getDefaultSupplier()).getLength();
    else
      totalArea = getDesignWidth() * getDesignHeight();

    totalArea /= 1000000;
    setDesignPrice(price * totalArea);
  }

  public void checkItemSize(LinkedList itemList, int nextIndex, MethodPrefDets methodPrefs) {
  }
}
