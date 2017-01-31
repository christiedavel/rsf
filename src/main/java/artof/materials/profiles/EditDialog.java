package artof.materials.profiles;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import artof.utils.UserSettings2;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EditDialog extends JDialog {
  private BufferedImage newImage = null;
  private static EditDialog editDialog = null;

  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton btnCancel = new JButton();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();

  JButton btnBrowse = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel2 = new JLabel();
  JRadioButton rbnTop = new JRadioButton();
  JRadioButton rbnRight = new JRadioButton();
  JRadioButton rbnBottom = new JRadioButton();
  JRadioButton rbnLeft = new JRadioButton();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JLabel jLabel3 = new JLabel();
  JButton btnColor = new JButton();
  Border border1;
  JButton btnSelect = new JButton();
  JLabel jLabel4 = new JLabel();
  SpinnerNumberModel spinnerModel = new SpinnerNumberModel(new Integer(100), new Integer(1), new Integer(1000), new Integer(10));
  JSpinner spnZoom = new JSpinner(spinnerModel);
  Border border2;
  JPanel jPanel3 = new JPanel();
  JLabel jLabel6 = new JLabel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  Border border3;
  Border border4;
  Border border5;

  //JPanel pnlSample = new JPanel();
  //JPanel pnlImage = new JPanel();
  //JPanel pnlTile = new JPanel();
  SamplePanel pnlSample = new SamplePanel();
  SamplePanel pnlTile = new SamplePanel();
  ImagePanel pnlImage = new ImagePanel(pnlSample, pnlTile, this);

  public static EditDialog getInstance() {
    if (editDialog == null)
      editDialog = new EditDialog(null, "Edit Profile Image", true);

    return editDialog;
  }

  private EditDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);

    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    spinnerModel.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        double z = spinnerModel.getNumber().intValue();
        pnlImage.setZoomFactor(z / 100.0);
      }
    });

    btnColor.setBackground(Color.cyan);
    pnlImage.setSelectorColor(btnColor.getBackground());

    rbnTop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pnlImage.setTiledImage();
      }
    });

    rbnBottom.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pnlImage.setTiledImage();
      }
    });

    rbnRight.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pnlImage.setTiledImage();
      }
    });

    rbnLeft.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pnlImage.setTiledImage();
      }
    });

    setSize(700, 600);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
  }

  public void setImage(BufferedImage i) {
    try {
      pnlImage.setImage(i);

    } catch (NullPointerException e) {
      // ignore

    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "The existing image file cannot be read", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    border3 = BorderFactory.createEmptyBorder();
    border4 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    border5 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
    panel1.setLayout(borderLayout1);
    this.getContentPane().setLayout(borderLayout2);
    btnCancel.setPreferredSize(new Dimension(80, 25));
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new EditDialog_btnCancel_actionAdapter(this));
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jLabel1.setText("Sample Image:");
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.setLayout(gridBagLayout1);
    btnBrowse.setMaximumSize(new Dimension(80, 25));
    btnBrowse.setMinimumSize(new Dimension(73, 25));
    btnBrowse.setPreferredSize(new Dimension(80, 25));
    btnBrowse.setText("Browse");
    btnBrowse.addActionListener(new EditDialog_btnBrowse_actionAdapter(this));
    jScrollPane1.setViewportBorder(BorderFactory.createLoweredBevelBorder());
    pnlSample.setBorder(BorderFactory.createEtchedBorder());
    pnlSample.setPreferredSize(new Dimension(40, 40));
    jLabel2.setText("Orientation:");
    rbnTop.setSelected(true);
    rbnTop.setText("Top");
    rbnRight.setText("Right");
    rbnBottom.setText("Bottom");
    rbnLeft.setText("Left");
    jLabel3.setText("Selector color:");
    btnColor.setBackground(new Color(79, 208, 200));
    btnColor.setBorder(border1);
    btnColor.setMaximumSize(new Dimension(11, 9));
    btnColor.setMargin(new Insets(2, 2, 2, 2));
    btnColor.setText("");
    btnColor.addActionListener(new EditDialog_btnColor_actionAdapter(this));
    btnSelect.setPreferredSize(new Dimension(80, 25));
    btnSelect.setText("Select");
    btnSelect.addActionListener(new EditDialog_btnSelect_actionAdapter(this));
    jLabel4.setText("Zoom:");
    spnZoom.setPreferredSize(new Dimension(50, 24));
    jLabel6.setBorder(border3);
    jLabel6.setText("Tiled Image:");
    jPanel3.setLayout(gridBagLayout2);
    jPanel3.setBorder(border4);
    pnlTile.setBorder(border5);
    jPanel1.add(btnBrowse, null);
    getContentPane().add(panel1, BorderLayout.CENTER);
    panel1.add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnCancel, null);
    panel1.add(jPanel2, BorderLayout.WEST);
    jPanel2.add(jLabel1,        new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(pnlSample,          new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 50, 50));
    panel1.add(jScrollPane1, BorderLayout.CENTER);
    panel1.add(jPanel3,  BorderLayout.EAST);
    jPanel3.add(jLabel6,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(pnlTile,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 40, 350));
    jScrollPane1.getViewport().add(pnlImage, null);
    jPanel2.add(jLabel2,        new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jPanel2.add(rbnTop,         new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    jPanel2.add(rbnRight,        new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    jPanel2.add(rbnBottom,        new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    jPanel2.add(rbnLeft,        new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
    jPanel2.add(jLabel3,        new GridBagConstraints(0, 8, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    jPanel2.add(btnColor,          new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 20, 20));
    buttonGroup1.add(rbnTop);
    buttonGroup1.add(rbnRight);
    buttonGroup1.add(rbnBottom);
    buttonGroup1.add(rbnLeft);
    jPanel1.add(btnSelect, null);
    jPanel2.add(jLabel4,        new GridBagConstraints(0, 10, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(18, 5, 5, 0), 0, 0));
    jPanel2.add(spnZoom,          new GridBagConstraints(1, 10, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
  }

  void btnBrowse_actionPerformed(ActionEvent e) {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileFilter() {
      public boolean accept(File f) {
        String name = f.getName().toLowerCase();
        if (f.isDirectory() || name.indexOf(".gif") > -1 || name.indexOf(".jpg") > -1 || name.indexOf(".png") > -1)
          return true;
        else
          return false;
      }

      public String getDescription() {
        return "Image files (JPEG, GIF, PNG)";
      }
    });

    if (UserSettings2.MATERIAL_IMAGE_PATH != null && !UserSettings2.MATERIAL_IMAGE_PATH.equals("")) {
      chooser.setSelectedFile(new File(UserSettings2.MATERIAL_IMAGE_PATH));
    }

    int returnVal = chooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      try {
        File file = chooser.getSelectedFile();
        pnlImage.setImage(file.getAbsolutePath());
        UserSettings2.MATERIAL_IMAGE_PATH = file.getAbsolutePath();
        jScrollPane1.getViewport().remove(pnlImage);
        jScrollPane1.getViewport().add(pnlImage, null);
        repaint();

      } catch (Exception x) {
        JOptionPane.showMessageDialog(this, "The file specified cannot be read", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  void btnColor_actionPerformed(ActionEvent e) {
    Color c = new JColorChooser().showDialog(this, "Selector color", btnColor.getBackground());
    if (c != null) {
      btnColor.setBackground(c);
      pnlImage.setSelectorColor(c);
    }
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    newImage = null;
    hide();
  }

  void btnSelect_actionPerformed(ActionEvent e) {
    BufferedImage image = pnlSample.getImage();
    if (image == null) {
      newImage = null;
      hide();
      return;
    }

    if (rbnTop.isSelected()) {
      newImage = new BufferedImage(image.getHeight(), image.getWidth(), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = newImage.createGraphics();
      AffineTransform transform = new AffineTransform();
      transform.translate(0, image.getWidth());
      transform.rotate(3.0 * Math.PI / 2.0);
      g.drawImage(image, transform, this);

    } else if (rbnRight.isSelected()) {
      newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = newImage.createGraphics();
      AffineTransform transform = new AffineTransform();
      transform.translate(image.getWidth(), image.getHeight());
      transform.rotate(Math.PI);
      g.drawImage(image, transform, this);

    } else if (rbnBottom.isSelected()) {
      newImage = new BufferedImage(image.getHeight(), image.getWidth(), BufferedImage.TYPE_INT_RGB);
      Graphics2D g = newImage.createGraphics();
      AffineTransform transform = new AffineTransform();
      transform.translate(image.getHeight(), 0);
      transform.rotate(Math.PI / 2.0);
      g.drawImage(image, transform, this);

    } else {
      newImage = image;
    }

    hide();
  }

  public BufferedImage getImage() {
    return newImage;
  }
}

class EditDialog_btnBrowse_actionAdapter implements java.awt.event.ActionListener {
  EditDialog adaptee;

  EditDialog_btnBrowse_actionAdapter(EditDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnBrowse_actionPerformed(e);
  }
}

class EditDialog_btnColor_actionAdapter implements java.awt.event.ActionListener {
  EditDialog adaptee;

  EditDialog_btnColor_actionAdapter(EditDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnColor_actionPerformed(e);
  }
}

class EditDialog_btnCancel_actionAdapter implements java.awt.event.ActionListener {
  EditDialog adaptee;

  EditDialog_btnCancel_actionAdapter(EditDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnCancel_actionPerformed(e);
  }
}

class EditDialog_btnSelect_actionAdapter implements java.awt.event.ActionListener {
  EditDialog adaptee;

  EditDialog_btnSelect_actionAdapter(EditDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnSelect_actionPerformed(e);
  }
}

