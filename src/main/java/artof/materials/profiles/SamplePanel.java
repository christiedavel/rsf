package artof.materials.profiles;

import java.awt.*;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import javax.imageio.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SamplePanel extends JPanel {
  private boolean firstTime = true;
  private BufferedImage bi;
  private Graphics2D big;

  private double centerX;
  private double centerY;
  private BufferedImage samplePrent;

  public SamplePanel() {
  }

  public void setImage(BufferedImage prent) {
    samplePrent = prent;
    repaint();
  }

  public void setItemImage(String itemCode) {
    try {
      MediaTracker tracker = new MediaTracker(this);
      Image image = this.getToolkit().createImage("images/materials/" + itemCode.toLowerCase() + ".jpg");
      tracker.addImage(image, 0);
      tracker.waitForAll();
      BufferedImage srcPrent = new BufferedImage(image.getWidth(this), image.getHeight(this), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = srcPrent.createGraphics();
      g.drawImage(image, 0, 0, this);
      setImage(srcPrent);

    } catch (Exception e) {
      setImage(null);
    }
  }

  public BufferedImage getImage() {
    return samplePrent;
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

    if (samplePrent != null) {
      double factor = Math.min((double)getWidth() / (double)samplePrent.getWidth(), (double)getHeight() / (double)samplePrent.getHeight());
      double xOffset = centerX - factor * (double)samplePrent.getWidth() / 2.0;
      double yOffset = centerY - factor * (double)samplePrent.getHeight() / 2.0;
      AffineTransform transform = new AffineTransform(factor, 0, 0, factor, xOffset, yOffset);
      big.drawImage(samplePrent, transform, this);
    }

    g2.drawImage(bi, 0, 0, this);
  }
}