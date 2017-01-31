package artof.designer;

import artof.database.*;
import artof.designitems.*;
import artof.utils.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.font.*;
import java.awt.geom.*;
import artof.Designs;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class Designer extends JPanel implements MouseListener {
  public static int ITEM_ARTWORK = 0;
  public static int ITEM_BOARD = 1;
  public static int ITEM_FRAME = 2;
  public static int ITEM_BACK = 3;
  public static int ITEM_GLASS = 4;
  public static int ITEM_FOIL = 5;
  public static int ITEM_SLIP = 6;
  public static int ITEM_BOX = 7;

  public static final int TITLE_ADD = 0;
  public static final int TITLE_INSERT = 1;
  public static final int TITLE_EDIT = 2;

  public static final int TYPE_GAP = 0;
  public static final int TYPE_MES = 1;

  public static String[] ITEM_DESC = { "Artwork", "Board", "Frame", "Back", "Glass", "Foil", "Slip" };

  private ArtofDB db_conn;
  private BufferedImage bi;
  private Graphics2D big;
  private boolean firstTime = true;
  private boolean isActive = false;
  private LinkedList itemList;
  private JButton btnShowPrice = new JButton();
  private double drawGapX = 80.0, drawGapY = 80.0;
  private DesignItem2 selectedItem = null;
  private DesignTable designTable;
  private Designs designs;

  public Designer(ArtofDB db, Designs designs) {
    super(new BorderLayout());
    this.designs = designs;
    db_conn = db;
    itemList = new LinkedList();
    designTable = new DesignTable(itemList, this);
    setBackground(Color.white);
    addMouseListener(this);

    btnShowPrice.setIcon(new ImageIcon("images/Links.gif"));
    btnShowPrice.setBorder(null);
    Box hbox = Box.createHorizontalBox();
    hbox.add(btnShowPrice);
    hbox.add(Box.createHorizontalGlue());
    add(hbox, BorderLayout.NORTH);

    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        firstTime = true;
      }
    });
  }

  public DesignTable getDesignTable() {
    return designTable;
  }

  public void setActive(boolean act) {
    isActive = act;
  }

  public void setItemList(LinkedList itemList) {
    this.itemList = itemList;
    designTable.setItemList(itemList);
  }
  public LinkedList getItemList() {
    return itemList;
  }

  public void addDesignItem(int itemType, DesignDets designDets) {
    if (!isActive)
      return;

    boolean canceled;
    DesignItem2 newItem;
    if (itemType == ITEM_ARTWORK) {

      // kyk of daar ander artworks is
      boolean none = false;
      ListIterator it = itemList.listIterator();
      while (it.hasNext()) {
        if (it.next() instanceof DesignArtWork) {
          none = true;
          break;
        }
      }
      if (!none) {
        newItem = new DesignArtWork(0, 0);
        if (!newItem.showPropertyDialog(TITLE_ADD, TYPE_MES))
          itemList.addFirst(newItem);
      }

    } else if (itemType == ITEM_FRAME) {

      try {
        DesignItem2 lastItem = (DesignItem2)itemList.getLast();
        newItem = new DesignFrame(lastItem.getOuterWidth(), lastItem.getOuterHeight());
        canceled = newItem.showPropertyDialog(TITLE_ADD, TYPE_GAP);
      } catch (NoSuchElementException e) {
        return;
      }
      if (!canceled)
        itemList.addLast(newItem);

    } else if (itemType == ITEM_BOARD){

      try {
        DesignItem2 lastItem = (DesignItem2)itemList.getLast();
        newItem = new DesignBoard(lastItem.getOuterWidth(), lastItem.getOuterHeight());
        canceled = newItem.showPropertyDialog(TITLE_ADD, TYPE_GAP);

      } catch (NoSuchElementException e) {
        newItem = new DesignBoard(0, 0);
        canceled = newItem.showPropertyDialog(TITLE_ADD, TYPE_MES);
      }

      if (!canceled)
        itemList.addLast(newItem);

    } else if (itemType == ITEM_BACK) {

      if (itemList.size() > 0) {
        newItem = new DesignBack(0, 0, itemList);
        canceled = newItem.showPropertyDialog(TITLE_ADD, -1);
        if (!canceled)
          itemList.addLast(newItem);
      }

    } else if (itemType == ITEM_GLASS) {

      if (itemList.size() > 0) {
        newItem = new DesignGlass(0, 0, itemList);
        canceled = newItem.showPropertyDialog(TITLE_ADD, -1);
        if (!canceled)
          itemList.addLast(newItem);
      }

    } else if (itemType == ITEM_BOX) {

      if (itemList.size() > 0) {
        newItem = new DesignBox(0, 0, itemList);
        canceled = newItem.showPropertyDialog(TITLE_ADD, -1);
        if (!canceled)
          itemList.addLast(newItem);
      }

    }
    designTable.setItemList(itemList);
    updateDrawing();
  }

  public void insertDesignItem(int itemType) {
    if (!isActive || selectedItem == null || itemList.size() == 0)
      return;

    boolean canceled;
    DesignItem2 newItem;
    if (itemType == ITEM_FRAME) {

      int index;
      try {
        index = itemList.indexOf(selectedItem);
        if (index == 0) return;
        DesignItem2 atItem = (DesignItem2)itemList.get(index - 1);
        newItem = new DesignFrame(atItem.getOuterWidth(), atItem.getOuterHeight());
        canceled = newItem.showPropertyDialog(TITLE_INSERT, TYPE_GAP);
      } catch (IndexOutOfBoundsException e) {
        return;
      }
      if (!canceled) itemList.add(index, newItem);

    } else if (itemType == ITEM_BOARD){

      int index;
      try {
        index = itemList.indexOf(selectedItem);
        if (index == 0) return;
        DesignItem2 atItem = (DesignItem2)itemList.get(index - 1);
        newItem = new DesignBoard(atItem.getOuterWidth(), atItem.getOuterHeight());
        canceled = newItem.showPropertyDialog(TITLE_INSERT, TYPE_GAP);
      } catch (IndexOutOfBoundsException e) {
        return;
      }
      if (!canceled) itemList.add(index, newItem);

    } else if (itemType == ITEM_FOIL){

      int index;
      try {
        index = itemList.indexOf(selectedItem);
        if (index == 0) return;
        DesignItem2 atItem = (DesignItem2)itemList.get(index - 1);
        newItem = new DesignFoil(atItem.getOuterWidth(), atItem.getOuterHeight());
        canceled = newItem.showPropertyDialog(TITLE_INSERT, TYPE_GAP);
      } catch (IndexOutOfBoundsException e) {
        return;
      }
      if (!canceled) itemList.add(index, newItem);

    } else if (itemType == ITEM_SLIP) {

      int index;
      try {
        index = itemList.indexOf(selectedItem);
        if (index == 0) return;
        DesignItem2 atItem = (DesignItem2)itemList.get(index - 1);
        newItem = new DesignSlip(atItem.getOuterWidth(), atItem.getOuterHeight());
        canceled = newItem.showPropertyDialog(TITLE_INSERT, TYPE_GAP);
      } catch (IndexOutOfBoundsException e) {
        return;
      }
      if (!canceled) itemList.add(index, newItem);
    }

    designTable.setItemList(itemList);
    updateDrawing();
  }

  public void deleteDesignItem() {
    try {
      if (!isActive || selectedItem != null) {
        if (selectedItem instanceof DesignArtWork) {
          String mes = "If you remove the Artwork all items will be removed from this design.  ";
          mes += "Are you sure you want to continue?";
          int res = JOptionPane.showConfirmDialog(this, mes, "Delete all items", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
          if (res == JOptionPane.YES_OPTION)
            itemList.clear();

        } else if (selectedItem instanceof DesignBoard && itemList.indexOf(selectedItem) == 0) {
          String mes = "If you remove this board all items will be removed from this design.  ";
          mes += "Are you sure you want to continue?";
          int res = JOptionPane.showConfirmDialog(this, mes, "Delete all items", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
          if (res == JOptionPane.YES_OPTION)
            itemList.clear();

        } else {
          itemList.remove(selectedItem);
        }

        setSelectedItem(null);
        designTable.setItemList(itemList);
        updateDrawing();
      }

    } catch (NullPointerException e) {
      // doen niks
    }
  }

  public void addShowPriceAction(ActionListener action) {
    btnShowPrice.addActionListener(action);
  }

  public void setShowPriceImage(ImageIcon image) {
    btnShowPrice.setIcon(image);
  }

  public void setPreferedSize(Dimension dim) {
    updateDrawing();
    super.setPreferredSize(dim);
  }

  public void updateDrawingFromTable() {
    try {
      // Skuif al die glass en backs na agter
      ArrayList gbList = new ArrayList();
      ListIterator it = itemList.listIterator();
      while (it.hasNext()) {
        Object item = it.next();
        if (item instanceof DesignGlassAndBack) gbList.add(item);
      }
      it = gbList.listIterator();
      while (it.hasNext()) {
        Object item = it.next();
        itemList.remove(item);
        itemList.add(item);
      }

      // Check eers vir rebates van rame en slips en pas aan as nodig
      it = itemList.listIterator();
      while (it.hasNext()) {
        DesignItem2 item = (DesignItem2)it.next();
        try {
          DesignItem2 nextItem = (DesignItem2)itemList.get(it.nextIndex());
          if (item.getDesignType() == ITEM_FRAME && nextItem.getDesignType() == ITEM_FRAME) {
            double rebate = nextItem.getMaterialDets().getDefaultValuesWithInMaterialDets() .getRebate();
            double frameWidth = item.getMaterialDets().getDefaultValuesWithInMaterialDets().getWidth();
            item.setTopGap(frameWidth - rebate);
            item.setBottomGap(frameWidth - rebate);
            item.setLeftGap(frameWidth - rebate);
            item.setRightGap(frameWidth - rebate);

          } else if (item.getDesignType() == ITEM_SLIP && nextItem.getDesignType() == ITEM_FRAME) {
            double slipRebate = item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate();
            double frameRebate = nextItem.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate();
            if (slipRebate > frameRebate) {
              double slipFace = item.getMaterialDets().getDefaultValuesWithInMaterialDets().getWidth() - slipRebate;
              double delta = slipRebate - frameRebate;
              item.setTopGap(slipFace + delta);
              item.setBottomGap(slipFace + delta);
              item.setLeftGap(slipFace + delta);
              item.setRightGap(slipFace + delta);
            }

          } else if (item.getDesignType() == ITEM_ARTWORK && nextItem.getDesignType() == ITEM_FRAME) {
            if (!(item.getBottomOffset() > 0.f || item.getTopOffset() > 0.f ||
                  item.getRightOffset() > 0.f || item.getLeftOffset() > 0.f)) {
              double rebate = nextItem.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate();
              item.setRebateCompensation(true, rebate);

            } else {
              item.setRebateCompensation(false, 0);
            }

          } else {
            item.setRebateCompensation(false, 0);
          }

        } catch (IndexOutOfBoundsException e) {
          // doen niks
        }
      }

      // Stel al die mates
      double curX = ((DesignItem2)itemList.getFirst()).getInnerWidth();
      double curY = ((DesignItem2)itemList.getFirst()).getInnerHeight();
      it = itemList.listIterator();
      while (it.hasNext()) {
        DesignItem2 item = (DesignItem2)it.next();
        item.setLimitWidth(curX);
        item.setLimitHeight(curY);
        curX = item.getOuterWidth();
        curY = item.getOuterHeight();
      }

      repaint();

    } catch (NoSuchElementException e) {
      repaint();

    } catch (NullPointerException e) {
      // doen fokkol
    }

    designs.btnCalculate2_actionPerformed();
    updateTableData();
  }

  private void updateDrawing() {
    updateDrawingFromTable();
  }

  public void updateTableData() {
    designTable.fireDataChanged();
  }

  private double getScaleFactor() {
    try {
      DesignItem2 frame = (DesignItem2)itemList.getFirst();
      double curWidth = frame.getOuterWidth();
      double curHeight = frame.getOuterHeight();
      Iterator it = itemList.iterator();
      while (it.hasNext()) {
        DesignItem2 item = (DesignItem2)it.next();
        if (item.getOuterWidth() > curWidth && item.getOuterHeight() > curHeight) {
          curWidth = item.getOuterWidth();
          curHeight = item.getOuterHeight();
        }
      }
      double factX = (getWidth() - 2 * drawGapX) / curWidth;
      double factY = (getHeight() - 2 * drawGapY) / curHeight;
      return Math.min(factX, factY);
    } catch (NoSuchElementException e) {
      return 1.0;
    } catch (NullPointerException e) {
      return 1.0;
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    Dimension dim = getSize();
    int w = dim.width;
    int h = dim.height;

    if (firstTime) {
      bi = (BufferedImage)createImage(w, h);
      big = bi.createGraphics();
      big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      firstTime = false;
    }

    // Clears the rectangle that was previously drawn.
    if (isActive) big.setBackground(Color.white);
    else big.setBackground(Color.gray);
    big.clearRect(0, 0, w, h);

    drawSimText(big, "mm", 40, 10, 0);
    double scaleFactor = getScaleFactor();
    double centerX = getWidth() / 2;
    double centerY = getHeight() / 2;
    int offsetCountLeft = 0, offsetCountRight = 0, offsetCountTop = 0, offsetCountBottom = 0;
    double prevLeftCenter = 0, prevRightCenter = 0, prevTopCenter = 0, prevBottomCenter = 0;
    try {
      for (int i = itemList.size() - 1; i >= 0; i--) {
        DesignItem2 item = (DesignItem2)itemList.get(i);
        if (item instanceof DesignArtWork)
          ((DesignArtWork)item).drawSimbool(big, centerX, centerY, scaleFactor, this);
        else
          item.drawSimbool(big, centerX, centerY, scaleFactor);

        if (UserSettings.DES_SHOW_GAPS && item.mustDrawMeasurements()) {
          // teken afmetingwaardes
          big.setColor(UserSettings.DEF_COLOR);
          if (item.getInnerWidth() <= 1.0) {
            drawSimText(big, Utils.getNumberFormat(item.getOuterWidth()), centerX /*+ 5*/, 40, 3*Math.PI/2);
            drawSimText(big, Utils.getNumberFormat(item.getOuterHeight()), 40, centerY /*- 2*/, 0);
          }
          else {
            double leftDist = centerX/* + 5*/ - (item.getOuterWidth()/2 - item.getLeftGap()/2) * scaleFactor;
            if (15 > Math.abs(leftDist - prevLeftCenter)) offsetCountLeft++;
            else offsetCountLeft = 0;
            prevLeftCenter = leftDist;
            drawSimText(big, Utils.getNumberFormat(item.getLeftGap()), leftDist, 40 - 10*offsetCountLeft, 3*Math.PI/2);

            double rightDist = centerX/* + 5*/ + (item.getOuterWidth()/2 - item.getRightGap()/2) * scaleFactor;
            if (15 > Math.abs(rightDist - prevRightCenter)) offsetCountRight++;
            else offsetCountRight = 0;
            prevRightCenter = rightDist;
            drawSimText(big, Utils.getNumberFormat(item.getRightGap()), rightDist, 40 - 10*offsetCountRight, 3*Math.PI/2);

            double topDist = centerY /*- 2*/ - (item.getOuterHeight()/2 - item.getTopGap()/2) * scaleFactor;
            if (10 > Math.abs(topDist - prevTopCenter)) offsetCountTop++;
            else offsetCountTop = 0;
            prevTopCenter = topDist;
            drawSimText(big, Utils.getNumberFormat(item.getTopGap()), 40 - 15*offsetCountTop, topDist, 0);

            double bottomDist = centerY /*- 2*/ + (item.getOuterHeight()/2 - item.getBottomGap()/2) * scaleFactor;
            if (10 > Math.abs(bottomDist - prevBottomCenter)) offsetCountBottom++;
            else offsetCountBottom = 0;
            prevBottomCenter = bottomDist;
            drawSimText(big, Utils.getNumberFormat(item.getBottomGap()), 40 - 15*offsetCountBottom, bottomDist, 0);

            if (item.getLeftOffset() > 1)
              drawSimText(big, Utils.getNumberFormat(item.getLeftOffset()),
                          centerX /*+ 5*/ - (item.getOuterWidth()/2 - item.getLeftGap() - item.getLeftOffset()/2) * scaleFactor, 40, 3*Math.PI/2);

            if (item.getRightOffset() > 1)
              drawSimText(big, Utils.getNumberFormat(item.getRightOffset()),
                          centerX /*+ 5 */+ (item.getOuterWidth()/2 - item.getRightGap() - item.getRightOffset()/2) * scaleFactor, 40, 3*Math.PI/2);

            if (item.getTopOffset() > 1)
              drawSimText(big, Utils.getNumberFormat(item.getTopOffset()), 40,
                          centerY /*- 2*/ - (item.getOuterHeight()/2- item.getTopGap() - item.getTopOffset()/2) * scaleFactor, 0);

            if (item.getBottomOffset() > 1)
              drawSimText(big, Utils.getNumberFormat(item.getBottomOffset()), 40,
                          centerY /*- 2 */+ (item.getOuterHeight()/2 - item.getBottomGap() - item.getBottomOffset()/2) * scaleFactor, 0);
          }

          // teken afmetinglyne
          big.drawLine((int)(centerX - item.getOuterWidth() * scaleFactor / 2), 60,
                       (int)(centerX - item.getOuterWidth() * scaleFactor / 2), 50);
          big.drawLine((int)(centerX + item.getOuterWidth() * scaleFactor / 2), 60,
                       (int)(centerX + item.getOuterWidth() * scaleFactor / 2), 50);
          big.drawLine(60, (int)(centerY - item.getOuterHeight() * scaleFactor / 2),
                       50, (int)(centerY - item.getOuterHeight() * scaleFactor / 2));
          big.drawLine(60, (int)(centerY + item.getOuterHeight() * scaleFactor / 2),
                       50, (int)(centerY + item.getOuterHeight() * scaleFactor / 2));

          if (item.getLeftOffset() > 1)
            big.drawLine((int)(centerX - (item.getOuterWidth()/2 - item.getLeftGap()) * scaleFactor), 60,
                         (int)(centerX - (item.getOuterWidth()/2 - item.getLeftGap()) * scaleFactor), 50);
          if (item.getRightOffset() > 1)
            big.drawLine((int)(centerX + (item.getOuterWidth()/2 - item.getRightGap()) * scaleFactor), 60,
                         (int)(centerX + (item.getOuterWidth()/2 - item.getRightGap()) * scaleFactor), 50);
          if (item.getTopOffset() > 1)
            big.drawLine(60, (int)(centerY - (item.getOuterHeight()/2 - item.getTopGap()) * scaleFactor),
                         50, (int)(centerY - (item.getOuterHeight()/2 - item.getTopGap()) * scaleFactor));
          if (item.getBottomOffset() > 1)
            big.drawLine(60, (int)(centerY + (item.getOuterHeight()/2 - item.getBottomGap()) * scaleFactor),
                         50, (int)(centerY + (item.getOuterHeight()/2 - item.getBottomGap()) * scaleFactor));

        }

        centerX = centerX + (item.getLeftGap() + item.getLeftOffset() - item.getRightGap() - item.getRightOffset()) * scaleFactor / 2;
        centerY = centerY + (item.getTopGap() + item.getTopOffset() - item.getBottomGap() - item.getBottomOffset()) * scaleFactor / 2;

      }
    } catch (NullPointerException e) {
      // doen niks
    }

    g2.drawImage(bi, 0, 0, this);
  }

  public void refreshDrawingSize() {
    firstTime = true;
  }

  private void drawSimText(Graphics2D big, String value, double x, double y, double angle) {
    Font font = new Font("Times New Roman", Font.PLAIN, 9);
    FontRenderContext frc = big.getFontRenderContext();
    TextLayout layout = new TextLayout(value, font, frc);
    Rectangle2D bounds = layout.getBounds();
    y += bounds.getHeight() / 2;
    x -= bounds.getWidth() / 2;
    layout.draw(big, (float)x, (float)y);

    //big.setFont(new Font("Times New Roman", Font.PLAIN, 10));
    //big.drawString(value, (float)x, (float)y);
   /* try {
      big.setFont(new Font("Times New Roman", Font.PLAIN, 10));
      AttributedString string = new AttributedString(value, new Hashtable());
      AttributedCharacterIterator paragraph = string.getIterator();
      AffineTransform transform = new AffineTransform(1, 0, 0, 1, 0, 0);
      transform.rotate(angle);
      FontRenderContext frc = new FontRenderContext(transform, false, false);
      LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, frc);
      int paragraphStart = paragraph.getBeginIndex();
      int paragraphEnd = paragraph.getEndIndex();

      lineMeasurer.setPosition(paragraphStart);
      TextLayout layout = lineMeasurer.nextLayout(100);
      float drawPosY = (float)y + layout.getAscent() / 2;
      lineMeasurer.setPosition(paragraphStart);
      while (lineMeasurer.getPosition() < paragraphEnd) {
        layout = lineMeasurer.nextLayout(100);
        layout.draw(big, (float)x - layout.getAdvance() / 2, drawPosY);
        drawPosY += layout.getAscent();
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }*/
  }

  public void mouseClicked(MouseEvent e) {
    boolean foundSelected = false;
    double scaleFactor = getScaleFactor();
    double centerX = getWidth() / 2;
    double centerY = getHeight() / 2;

    if (itemList != null) {
      for (int i = itemList.size() - 1; i >= 0; i--) {
        DesignItem2 item = (DesignItem2)itemList.get(i);
        if (!foundSelected && item.containsPoint(e.getX(), e.getY(), centerX, centerY, scaleFactor)) {
          foundSelected = true;
          item.setSelected(true);
          setSelectedItem(item);
          if (e.getClickCount() >= 2) {
            DesignItem2 clonedItem = (DesignItem2)item.clone();
            boolean canceled;
            if (itemList.indexOf(item) == 0)
              canceled = clonedItem.showPropertyDialog(TITLE_EDIT, TYPE_MES);
            else
              canceled = clonedItem.showPropertyDialog(TITLE_EDIT, TYPE_GAP);

            if (!canceled) {
              itemList.set(i, clonedItem);
              item = clonedItem;
              updateDrawing();
            }
          }

        } else {
          item.setSelected(false);
        }

        centerX = centerX + (item.getLeftGap() + item.getLeftOffset() - item.getRightGap() - item.getRightOffset()) * scaleFactor / 2;
        centerY = centerY + (item.getTopGap() + item.getTopOffset() - item.getBottomGap() - item.getBottomOffset()) * scaleFactor / 2;
     }
    }

    if (!foundSelected && e.getClickCount() >= 2) {
      DesignSettings des = new DesignSettings();
    }
    repaint();
  }

  public void mouseExited(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {}

  public void setSelectedItem(int itemRow) {
    try {
      Iterator it = itemList.iterator();
      while (it.hasNext())
        ((DesignItem2)it.next()).setSelected(false);

      selectedItem = (DesignItem2)itemList.get(itemRow);
      selectedItem.setSelected(true);
      designTable.setSelectedItem(itemList.indexOf(selectedItem));

    } catch (IndexOutOfBoundsException e) {
      selectedItem = null;
      designTable.setSelectedItem(-1);
    }
  }

  public void setSelectedItem(DesignItem2 item) {
    selectedItem = item;
    designTable.setSelectedItem(itemList.indexOf(selectedItem));
  }

  public DesignItem2 getSelectedItem() {
    return selectedItem;
  }

  public DesignDets getCurrentDesignDets() {
    try {
      return (DesignDets)designs.getDesignList().get(designs.getCurrentDesignIndex());
    } catch (Exception e) {
      return null;
    }
  }
}
