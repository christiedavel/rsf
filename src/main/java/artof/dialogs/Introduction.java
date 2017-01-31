package artof.dialogs;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Introduction extends JDialog {
  private Image image;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel panel = new JPanel();

  public Introduction() {
    panel = new JPanel() {
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (image == null) {
          String imagePath = "images/voorblad.jpg";
          Toolkit t = Toolkit.getDefaultToolkit();
          image = t.createImage(imagePath);
        }

        double imageWidth = image.getWidth(this);
        double imageHeight = image.getHeight(this);
        double xFact = panel.getWidth() / imageWidth;
        double yFact = panel.getHeight() / imageHeight;
        AffineTransform transform = new AffineTransform(xFact, 0, 0, yFact, 0, 0);
        g2.drawImage(image, transform, this);
      }
    };

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    setUndecorated(true);
    setSize(500, 400);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.getContentPane().add(panel, BorderLayout.CENTER);
  }
}