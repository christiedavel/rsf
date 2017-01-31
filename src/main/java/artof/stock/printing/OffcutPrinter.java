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
import artof.stock.*;
import artof.database.*;
import artof.designitems.DesignItem2;
import artof.designer.Designer;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class OffcutPrinter
    implements Printable {
  private DesignDets designDets;
  private ArrayList itemList;
  private int pi1;
  private JScrollPane jScrollPane = null;
  private Graphics2D g2d;
  private Line2D.Double l2d;
  String designCode = "";

  public OffcutPrinter(ArrayList itemList, String designCode) {
    this.designCode = designCode;
    this.itemList = itemList;
    int ils = itemList.size();
    pi1 = (itemList.size() / 27) + 1;
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    Book book = new Book();
    int numPages = 0;

    if ( ( (float) (itemList.size()) / 27) - pi1 > 0) {
      pi1++;
    }

    numPages = (pi1);
    PageFormat format = new PageFormat();
    format.setOrientation(PageFormat.LANDSCAPE);
    book.append(this, format, numPages);
    printerJob.setPageable(book);
    //boolean doPrint = printerJob.printDialog();
    //if (doPrint) {
      try {
        printerJob.print();
      }
      catch (PrinterException exception) {
        JOptionPane.showMessageDialog(null,
            "Printer error.  The discrepency details cannot be printed.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    //}

  }

  public int print(Graphics g, PageFormat format, int pageIndex) throws
      PrinterException {
    printFok(g, format, pageIndex);
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

//Print title
    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 14));
    FontRenderContext frc = g2d.getFontRenderContext();
    TextLayout layout = new TextLayout(
        "Stock items for design:",
        g2d.getFont(), frc);
    Rectangle2D bounds = layout.getBounds();
    layout.draw(g2d, 0,
                (float) curY);

    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 14));
    frc = g2d.getFontRenderContext();
    layout = new TextLayout(
        designCode,
        g2d.getFont(), frc);
    bounds = layout.getBounds();
    layout.draw(g2d, 185,
                (float) curY);

    //Headings1
    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 12));

    layout = new TextLayout("012345678909878", g2d.getFont(), frc);
    bounds = layout.getBounds();
    double width1 = bounds.getWidth() + 2;
    layout = new TextLayout("01234567", g2d.getFont(), frc);
    bounds = layout.getBounds();
    double width2 = bounds.getWidth() + 5;
    double width3 = bounds.getWidth() + 9;
    double width4 = bounds.getWidth() + 7;
    curY += 25;
    double curX = addStringGap(g2d, frc, "Stock Code", curY, 0, width1, false);
    curX = addStringGap(g2d, frc, "Item Code", curY, curX, width1, false);
    curX = addStringGap(g2d, frc, "Shelf", curY, curX, width2, false);
    curX = addStringGap(g2d, frc, "Supplier", curY, curX, width3, false);
    curX = addStringGap(g2d, frc, "Type", curY, curX, width3, false);
    curX = addStringGap(g2d, frc, "Length", curY, curX, width4, false);
    curX = addStringGap(g2d, frc, "Width", curY, curX, width2, false);

//print lyn
    curY += 15;
    l2d = new Line2D.Double(0, curY, pageWidth, curY);
    g2d = (Graphics2D) g;
    g2d.draw(l2d);

    curY += 30;
    Iterator it = itemList.listIterator();
    g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 11));
    while (it.hasNext()) {
      StockItem item = (StockItem) it.next();
      curX = 0;
      curX =
          addStringGap(
          g2d,
          frc,
          String.valueOf(item.getStockID()),
          curY,
          curX,
          width1,
          false);
      curX =
          addStringGap(
          g2d,
          frc,
          item.getItemCode(),
          curY,
          curX,
          width1,
          false);
      curX =
          addStringGap(
          g2d,
          frc,
          item.getShelf(),
          curY,
          curX,
          width2,
          false);
      curX =
          addStringGap(
          g2d,
          frc,
          item.getSupplier(),
          curY,
          curX,
          width3,
          false);
      curX =
          addStringGap(
          g2d,
          frc,
          MaterialDets.getItemTypeString(item.getMatType()),
          curY,
          curX,
          width3,
          false);
      curX =
          addStringGap(
          g2d,
          frc,
          "............",
          curY,
          curX,
          width4,
          false);
      curX =
          addStringGap(
          g2d,
          frc,
          "............",
          curY,
          curX,
          width2,
          false);

      curY += 30;

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
        jScrollPane.repaint();
      }

    }

  }

}