package artof.dialogs;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AboutDialog extends JDialog {
  private Image image = null;
  private JPanel jPanel1 = new JPanel();
  private JButton btnOK = new JButton();
  private JPanel jPanel2 = new JPanel();
  private Border border1;
  private BorderLayout borderLayout1 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  JLabel lblRegNo = new JLabel();
  JLabel jLabel2 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel lblExpDate = new JLabel();
  JPanel pnlImage = new JPanel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();

  public AboutDialog() {
    pnlImage = new JPanel() {
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (image == null) {
          String imagePath = "images/about.jpg";
          Toolkit t = Toolkit.getDefaultToolkit();
          image = t.createImage(imagePath);
        }

        double imageWidth = image.getWidth(this);
        double imageHeight = image.getHeight(this);
        double xFact = pnlImage.getWidth() / imageWidth;
        double yFact = pnlImage.getHeight() / imageHeight;
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

    lblRegNo.setText(artof.utils.UserSettings.registrationNo);

    if (artof.utils.UserSettings.expiryDate - artof.utils.Utils.getCurrentDate() > 10000) {
      lblExpDate.setText("Indefinite");
    } else {
      lblExpDate.setText(artof.utils.Utils.getDatumStr(artof.utils.UserSettings.expiryDate));
    }

    setSize(410, 330);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setModal(true);
    setVisible(true);
  }
  private void jbInit() throws Exception {
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),BorderFactory.createEmptyBorder(5,5,5,5));
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("About RSF");
    this.getContentPane().setLayout(borderLayout1);
    btnOK.setText("OK");
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOK_actionPerformed(e);
      }
    });
    jPanel2.setBorder(border1);
    jPanel2.setLayout(gridBagLayout1);
    borderLayout1.setHgap(5);
    borderLayout1.setVgap(5);
    jLabel1.setText("Registration no:");
    jLabel2.setText("Current expiry date:");
    lblExpDate.setText("none");
    lblRegNo.setText("none");
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 11));
    jLabel3.setText("Although care has been taken to ensure the accuracy of data, RockSpider");
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabel4.setText("RSF version 1.3.8");
    jLabel5.setText("");
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 11));
    jLabel6.setText("accepts no liability whatsoever for any use, interpretation or");
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 11));
    jLabel7.setText("Copyright 2003 RockSpider.  All rights reserved.");
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 11));
    jLabel8.setText("application of any results from RSF.");
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(btnOK, null);
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jLabel1,            new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel2.add(lblRegNo,            new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    jPanel2.add(jLabel2,            new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel2.add(lblExpDate,              new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(pnlImage,           new GridBagConstraints(0, 0, 1, 3, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jLabel3,         new GridBagConstraints(0, 3, 4, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel4,         new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
    jPanel2.add(jLabel5,      new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(jLabel6,        new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
    jPanel2.add(jLabel7,    new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel8,     new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 10, 5), 0, 0));
  }

  void btnOK_actionPerformed(ActionEvent e) {
    hide();
  }

}
