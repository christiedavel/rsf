package artof.stock.printing;

import java.awt.print.Printable;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.print.*;
import java.awt.font.*;
import java.awt.geom.*;
import artof.utils.*;
import artof.database.ArtistDets;
import artof.stock.stocktake.*;
import artof.materials.MaterialDets;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DiscrepencyPrinter
    implements Printable {
  private JScrollPane jScrollPane = null;
  private ArrayList itemList1, itemList2;
  private int listSizes = 0;
  private String date;
  private DiffDialog diffDialog;
  private int pi1 = 0;
  private int pi2 = 0;
  private MaterialDets matDets = new MaterialDets();
  Graphics2D g2d;
  Line2D.Double l2d;

  public DiscrepencyPrinter(ArrayList itemList1, ArrayList itemList2,
                            DiffDialog diffDialog) {
    this.itemList1 = new ArrayList(itemList1);
    this.itemList2 = new ArrayList(itemList2);
    this.diffDialog = diffDialog;
    int ils1 = itemList1.size();
    int ils2 = itemList2.size();
    pi1 = (itemList1.size() / 27) + 1;
    pi2 = (itemList2.size() / 27) + 1;
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    Book book = new Book();
    int numPages = 0;

    if ( ( (float) (itemList1.size()) / 27) - pi1 > 0) {
      pi1++;
    }
    if ( ( (float) (itemList2.size()) / 27) - pi2 > 0) {
      pi2++;
    }
    numPages = (pi1 + pi2);

    book.append(this, new PageFormat(), numPages);
    printerJob.setPageable(book);
    boolean doPrint = printerJob.printDialog();
    if (doPrint) {
      try {
        printerJob.print();
      }
      catch (PrinterException exception) {
        JOptionPane.showMessageDialog(diffDialog,
            "Printer error.  The discrepency details cannot be printed.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public int print(Graphics g, PageFormat format, int pageIndex) throws
      PrinterException {
    printFok(g, format, pageIndex);printFok2(g, format, pageIndex);
    //PrintFrame pp = new PrintFrame(g, format, pageIndex);
    return Printable.PAGE_EXISTS;

  }

  public void printFok(Graphics g, PageFormat format, int pageIndex) {
    double x = format.getImageableX();
    double y = format.getImageableY();
    g2d = (Graphics2D) g;
    g2d.translate(format.getImageableX(), format.getImageableY());
    g2d.setPaint(Color.black);
    double pageWidth = format.getImageableWidth();
    double pageHeight = format.getImageableHeight();
    double curY = 15;
    double indent1 = 10;
    double indent2 = 20;
    double indent3 = 30;
    double indent4 = 60;
    double indent5 = 90;
    if (pageIndex <= pi1 - 1) {
      if (pageIndex == 0) {
//Print title
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 14));
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout layout = new TextLayout(
            "Stock discrepencies as for stocktake ~  ",
            g2d.getFont(), frc);
        Rectangle2D bounds = layout.getBounds();
        layout.draw(g2d, (float) indent1 * 1,
                    (float) curY);

        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 13));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getCbxPrevSelectedValue(),
                                g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (290),
                    (float) curY);

//print lyn
        curY += 15;
        l2d = new Line2D.Double(0 + 100, curY, pageWidth - 100,
                                curY);
        g2d = (Graphics2D) g;

        g2d.draw(l2d);

//Items in system not in stock
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 13));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(
            "Items in system calculated stock, but not in fisical stock: ",
            g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 20;
        layout.draw(g2d, (float) (indent1),
                    (float) curY);

//Totals1
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Totals", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (indent2),
                    (float) curY);

//Boards1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Boards: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Boards1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtBoards1() + " m²", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);

//Frames1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Frames: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Frames1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtFrames1() + " m", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);

//Backs1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Backs: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Backs1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtBacks1() + " m²", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);
//Slips1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Slips: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Slips1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtSlips1() + " m", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);
//Foils1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Foils: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Foils1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtFoils1() + " m", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);
      }

//  x to y of z
      FontRenderContext frc = g2d.getFontRenderContext();
      curY += 25;
      String text = String.valueOf(pageIndex * 27 + 1) + " to " +
          Math.min(itemList1.size(), (pageIndex + 1) * 27);
      text += " of " + itemList1.size();
      TextLayout layout = new TextLayout(text, g2d.getFont(), frc);
      Rectangle2D bounds = layout.getBounds();
      curY += bounds.getHeight();
      layout.draw(g2d, (float) (pageWidth / 2 - bounds.getWidth() / 2),
                  (float) curY);

      //Headings1
      g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 12));

      layout = new TextLayout("012345678909878", g2d.getFont(), frc);
      bounds = layout.getBounds();
      double width1 = bounds.getWidth() + 20;
      layout = new TextLayout("01234567", g2d.getFont(), frc);
      bounds = layout.getBounds();
      double width2 = bounds.getWidth() + 20;
      curY += 25;
      double curX = addStringGap(g2d, frc, "Item Code", curY, 0, width1, false);
      curX = addStringGap(g2d, frc, "Supplier", curY, curX, width2, false);
      curX = addStringGap(g2d, frc, "Type", curY, curX, width2, false);
      curX = addStringGap(g2d, frc, "Total", curY, curX, width2, false);
      curX = addStringGap(g2d, frc, "Unit", curY, curX, width2, false);

//print lyn
      curY += 15;
      l2d = new Line2D.Double(0, curY, pageWidth, curY);
      g2d = (Graphics2D) g;
      g2d.draw(l2d);

      String[] item = null;
      for (int i = pageIndex * 27; i < (pageIndex + 1) * 27; i++) {
        try {
          item = (String[]) itemList1.get(i);
        }
        catch (IndexOutOfBoundsException e) {
          break;
        }
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
        curY += 15;
        curX = 0;
        curX = addStringGap(g2d, frc, (String) item[0], curY, curX, width1, false);
        curX = addStringGap(g2d, frc, (String) item[1], curY, curX, width2, false);
        curX = addStringGap(g2d, frc,
                            MaterialDets.getItemTypeString(Integer.
            parseInt(item[2])), curY, curX, width2, false);
        curX = addStringGap(g2d, frc, (String) item[3], curY, curX, width2, false);
        curX = addStringGap(g2d, frc, (String) item[4], curY, curX, width2, false);

//      curY += bounds.getHeight() * 4;
        if (curY > pageHeight) {
          break;
        }
      }
    }
  }

  public void printFok2(Graphics g, PageFormat format, int pageIndex) {
    double x = format.getImageableX();
    double y = format.getImageableY();
    //Graphics2D g2d = (Graphics2D) g;
    //g2d.translate(format.getImageableX(), format.getImageableY());
    g2d.setPaint(Color.black);
    //Line2D.Double l2d;
    double pageWidth = format.getImageableWidth();
    double pageHeight = format.getImageableHeight();
    double curY = 15;
    double indent1 = 10;
    double indent2 = 20;
    double indent3 = 30;
    double indent4 = 60;
    double indent5 = 90;
    if (pageIndex >= pi1) {

      if (pageIndex == pi1) {
//Print title
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 14));
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout layout = new TextLayout(
            "Stock discrepencies as for stocktake ~  ",
            g2d.getFont(), frc);
        Rectangle2D bounds = layout.getBounds();
        layout.draw(g2d, (float) indent1 * 1,
                    (float) curY);

        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 13));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getCbxPrevSelectedValue(),
                                g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (290),
                    (float) curY);

//print lyn
        curY += 15;
        l2d = new Line2D.Double(0 + 100, curY, pageWidth - 100,
                                curY);
        g2d = (Graphics2D) g;

        g2d.draw(l2d);

//Items in system not in stock
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 13));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(
            "Items in fisical stock, but not in system calculated stock: ",
            g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 20;
        layout.draw(g2d, (float) (indent1),
                    (float) curY);

//Totals1
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Totals", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (indent2),
                    (float) curY);

//Boards1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Boards: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Boards1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtBoards2() + " m²", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);

//Frames1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Frames: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Frames1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtFrames2() + " m", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);

//Backs1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Backs: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Backs1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtBacks2() + " m²", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);
//Slips1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Slips: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Slips1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtSlips2() + " m", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);
//Foils1
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout("Foils: ", g2d.getFont(), frc);
        bounds = layout.getBounds();
        curY += bounds.getHeight() + 5;
        layout.draw(g2d, (float) (50),
                    (float) curY);

//Foils1 Waarde
        g2d.setFont(g2d.getFont().deriveFont(Font.ITALIC, 12));
        frc = g2d.getFontRenderContext();
        layout = new TextLayout(diffDialog.getTxtFoils2() + " m", g2d.getFont(),
                                frc);
        bounds = layout.getBounds();
        layout.draw(g2d, (float) (120),
                    (float) curY);
      }

//  x to y of z
      FontRenderContext frc = g2d.getFontRenderContext();
      curY += 25;
      String text = "0 to 0 of 0";;
      if (itemList2.size() != 0) {
        text = String.valueOf( (pageIndex - pi1) * 27 + 1) + " to " +
            Math.min(itemList2.size(), ( (pageIndex - pi1) + 1) * 27);
        text += " of " + itemList2.size();
      }
      TextLayout layout = new TextLayout(text, g2d.getFont(), frc);
      Rectangle2D bounds = layout.getBounds();
      curY += bounds.getHeight();
      layout.draw(g2d, (float) (pageWidth / 2 - bounds.getWidth() / 2),
                  (float) curY);

      //Headings1
      g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 12));

      layout = new TextLayout("012345678909878", g2d.getFont(), frc);
      bounds = layout.getBounds();
      double width1 = bounds.getWidth() + 20;
      layout = new TextLayout("01234567", g2d.getFont(), frc);
      bounds = layout.getBounds();
      double width2 = bounds.getWidth() + 20;
      curY += 25;
      double curX = addStringGap(g2d, frc, "Item Code", curY, 0, width1, false);
      curX = addStringGap(g2d, frc, "Supplier", curY, curX, width2, false);
      curX = addStringGap(g2d, frc, "Type", curY, curX, width2, false);
      curX = addStringGap(g2d, frc, "Total", curY, curX, width2, false);
      curX = addStringGap(g2d, frc, "Unit", curY, curX, width2, false);

//print lyn
      curY += 15;
      l2d = new Line2D.Double(0, curY, pageWidth, curY);
      g2d = (Graphics2D) g;
      g2d.draw(l2d);

      String[] item = null;
      for (int i = (pageIndex - pi1) * 27; i < ((pageIndex - pi1) + 1) * 27; i++) {
        try {
          item = (String[]) itemList2.get(i);
        }
        catch (IndexOutOfBoundsException e) {
          break;
        }
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
        curY += 15;
        curX = 0;
        curX = addStringGap(g2d, frc, (String) item[0], curY, curX, width1, false);
        curX = addStringGap(g2d, frc, (String) item[1], curY, curX, width2, false);
        curX = addStringGap(g2d, frc,
                            MaterialDets.getItemTypeString(Integer.
            parseInt(item[2])), curY, curX, width2, false);
        curX = addStringGap(g2d, frc, (String) item[3], curY, curX, width2, false);
        curX = addStringGap(g2d, frc, (String) item[4], curY, curX, width2, false);

//      curY += bounds.getHeight() * 4;
        if (curY > pageHeight) {
          break;
        }
      }
    }
  }

  private double addStringGap(Graphics2D g2d, FontRenderContext frc, String s,
                              double top, double left, double width,
                              boolean alignRight) {
    try {
      TextLayout layout = new TextLayout(s, g2d.getFont(), frc);
      Rectangle2D bounds = layout.getBounds();
      double w = bounds.getWidth();
      if (alignRight) {
        layout.draw(g2d, (float) (left + width - w), (float) top);
      }
      else {
        layout.draw(g2d, (float) left, (float) top);
      }
    }
    catch (IllegalArgumentException e) {
    }
    return left + width;
  }

  public class PrintFrame
      extends JFrame {
    PageFormat format;
    Graphics g;
    int pageIndex;
    public PrintFrame(Graphics g, PageFormat f, int pageIndex) {
      format = f;
      this.g = g;
      this.pageIndex = pageIndex;
      MyPanel myPan = new MyPanel();
      jScrollPane = new JScrollPane(myPan);
      jScrollPane.setVerticalScrollBarPolicy(
          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      jScrollPane.setWheelScrollingEnabled(true);
      this.setSize(new Double(f.getImageableWidth()).intValue() + 300,
                   new Double(f.getImageableHeight()).intValue());
      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(jScrollPane, BorderLayout.CENTER);
      this.setVisible(true);
    }

    public class MyPanel
        extends JPanel {

      public MyPanel() {
        this.setBackground(Color.WHITE);

        //this.setVisible(true);
      }

      public void clear(Graphics g) {
        super.paintComponent(g);
      }

      public void paintComponent(Graphics g) {
        clear(g);
        printFok(g, format, pageIndex);
        printFok2(g, format, pageIndex);
        jScrollPane.repaint();
      }

    }

  }

}