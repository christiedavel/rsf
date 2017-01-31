package artof.materials.profiles;

import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.font.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.File;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ImagePanel extends JPanel {
  private boolean firstTime = true;
  private BufferedImage bi;
  private Graphics2D big;

  private BufferedImage srcPrent;
  private double centerX;
  private double centerY;
  private double border = 50;

  private Color selectorColor = Color.black;
  private boolean mousePressed = false;
  private double startX = 0;
  private double startY = 0;
  private double endX = 0;
  private double endY = 0;

  private double factor = 1;
  private double xOffset = 0;
  private double yOffset = 0;
  private double zoomFactor = 1;

  private SamplePanel pnlSample;
  private SamplePanel pnlTile;
  private EditDialog editDialog;

  public ImagePanel(SamplePanel p, SamplePanel t, EditDialog d) {
    pnlSample = p;
    pnlTile = t;
    editDialog = d;
    this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        bi = (BufferedImage)createImage(getWidth(), getHeight());
        big = bi.createGraphics();
        big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
      }
    });

    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        mousePressed = true;
        startX = e.getX();
        startY = e.getY();
        repaint();
      }

      public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        endX = e.getX();
        endY = e.getY();
        repaint();
        setSampleImage();
        startX = startY = endX = endY = 0;
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        if (mousePressed) {
          endX = e.getX();
          endY = e.getY();
          repaint();
        }
      }
    });
  }

  public void setImage(String path) throws Exception {
    MediaTracker tracker = new MediaTracker(this);
    Image image = this.getToolkit().createImage(path);
    tracker.addImage(image, 0);
    tracker.waitForAll();
    setImage(image);
  }

  public void setImage(Image image) throws Exception {
    srcPrent = new BufferedImage(image.getWidth(this), image.getHeight(this), BufferedImage.TYPE_INT_RGB);
    Graphics2D g = srcPrent.createGraphics();
    g.drawImage(image, 0, 0, this);

    double width = zoomFactor * srcPrent.getWidth() + 2.0 * border;
    double height = zoomFactor * srcPrent.getHeight() + 2.0 * border;
    setPreferredSize(new Dimension((int)width, (int)height));
    repaint();
  }

  public void setZoomFactor(double z) {
    if (z > 0) {
      zoomFactor = z;
      if (srcPrent != null) {
        double width = zoomFactor * srcPrent.getWidth() + 2.0 * border;
        double height = zoomFactor * srcPrent.getHeight() + 2.0 * border;
        setPreferredSize(new Dimension((int)width, (int)height));
        repaint();
      }
    }
  }

  private void setSampleImage() {
    if (srcPrent == null)
      return;

    double dw = zoomFactor * srcPrent.getWidth() / 2.0;
    double dh = zoomFactor * srcPrent.getHeight() / 2.0;
    int x1 = Math.max(0, (int)((startX - centerX + dw) / zoomFactor));
    int x2 = Math.min(srcPrent.getWidth(), Math.max(0, (int)((endX - centerX + dw) / zoomFactor)));
    int y1 = Math.max(0, (int)((startY - centerY + dh) / zoomFactor));
    int y2 = Math.min(srcPrent.getHeight(), Math.max(0, (int)((endY - centerY + dh) / zoomFactor)));

    if (x2 - x1 < 1 || y2 - y1 < 1) {
      pnlSample.setImage(null);
    } else {
      BufferedImage sampleImage = srcPrent.getSubimage(x1, y1, x2 - x1, y2 - y1);
      pnlSample.setImage(sampleImage);
      setTiledImage();
    }
  }

  public void setTiledImage() {
    BufferedImage sampleImage = pnlSample.getImage();
    BufferedImage tempImage;

    if (sampleImage == null)
      return;

    if (editDialog.rbnTop.isSelected()) {
      tempImage = new BufferedImage(sampleImage.getHeight(), sampleImage.getWidth(), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = tempImage.createGraphics();
      AffineTransform transform = new AffineTransform();
      transform.translate(sampleImage.getHeight(), 0);
      transform.rotate(Math.PI / 2.0);
      g.drawImage(sampleImage, transform, this);

    } else if (editDialog.rbnRight.isSelected()) {
      tempImage = new BufferedImage(sampleImage.getWidth(), sampleImage.getHeight(), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = tempImage.createGraphics();
      AffineTransform transform = new AffineTransform();
      g.drawImage(sampleImage, transform, this);

    } else if (editDialog.rbnBottom.isSelected()) {
      tempImage = new BufferedImage(sampleImage.getHeight(), sampleImage.getWidth(), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = tempImage.createGraphics();
      AffineTransform transform = new AffineTransform();
      transform.translate(0, sampleImage.getWidth());
      transform.rotate(3.0 * Math.PI / 2.0);
      g.drawImage(sampleImage, transform, this);

    } else {
      tempImage = new BufferedImage(sampleImage.getWidth(), sampleImage.getHeight(), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = tempImage.createGraphics();
      AffineTransform transform = new AffineTransform();
      transform.translate(sampleImage.getWidth(), sampleImage.getHeight());
      transform.rotate(Math.PI);
      g.drawImage(sampleImage, transform, this);
    }

    BufferedImage newImage = new BufferedImage(pnlTile.getWidth(), pnlTile.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D g = newImage.createGraphics();
    g.setBackground(Color.white);
    g.clearRect(0, 0, newImage.getWidth(), newImage.getHeight());

    int[] xPoints = { 0, pnlTile.getWidth(), pnlTile.getWidth(), 0 };
    int[] yPoints = { 0, 0, pnlTile.getHeight(), pnlTile.getHeight() };
    Polygon profile = new Polygon(xPoints, yPoints, 4);

    TexturePaint p = new TexturePaint(tempImage, new Rectangle(0, 0, pnlTile.getWidth(), tempImage.getHeight() * pnlTile.getWidth() / tempImage.getWidth()));
    g.setPaint(p);
    g.fill(profile);
    pnlTile.setImage(newImage);
  }

  public void setSelectorColor(Color c) {
    selectorColor = c;
    repaint();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    if (firstTime) {
      bi = (BufferedImage)createImage(getWidth(), getHeight());
      big = bi.createGraphics();
      big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      centerX = getWidth() / 2;
      centerY = getHeight() / 2;
      firstTime = false;
    }

    // Clears the rectangle that was previously drawn.
    big.setBackground(Color.white);
    big.clearRect(0, 0, getWidth(), getHeight());

    if (srcPrent != null) {
      xOffset = centerX - zoomFactor * (double)srcPrent.getWidth() / 2.0;
      yOffset = centerY - zoomFactor * (double)srcPrent.getHeight() / 2.0;
      AffineTransform transform = new AffineTransform(zoomFactor, 0, 0, zoomFactor, xOffset, yOffset);
      big.drawImage(srcPrent, transform, this);
    }

    if (mousePressed) {
      big.setColor(selectorColor);
      big.draw3DRect((int)startX, (int)startY, (int)(endX - startX), (int)(endY - startY), true);
    }

    g2.drawImage(bi, 0, 0, this);
  }
}