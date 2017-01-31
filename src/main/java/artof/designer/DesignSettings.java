package artof.designer;
import artof.utils.UserSettings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DesignSettings extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel jPanel3 = new JPanel();
  JCheckBox ckFillColors = new JCheckBox();
  JCheckBox ckDrawBorder = new JCheckBox();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JCheckBox ckShowMeasures = new JCheckBox();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JRadioButton rbnUseDefColor = new JRadioButton();
  JRadioButton rbnUseGroupColor = new JRadioButton();
  JRadioButton rbnUseItemColor = new JRadioButton();
  JPanel jPanel4 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton btnDefColor = new JButton();
  Border border1;
  JLabel jLabel1 = new JLabel();
  JButton btnSlipColor = new JButton();
  JButton btnFoilColor = new JButton();
  JButton btnFrameColor = new JButton();
  JButton btnBoardColor = new JButton();
  JButton btnArtColor = new JButton();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  Border border2;
  Border border3;
  Border border4;
  Border border5;
  Border border6;
  JButton btnCancel = new JButton();
  JButton btnOK = new JButton();
  Border border7;
  Border border8;
  Border border9;

  public DesignSettings() {
    setLocation(200, 200);
    setSize(350, 300);
    setTitle("Design settings");
    this.setResizable(false);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    btnDefColor.setBackground(UserSettings.DEF_COLOR);
    btnArtColor.setBackground(UserSettings.DEF_ARTWORK_COLOR);
    btnBoardColor.setBackground(UserSettings.DEF_BOARD_COLOR);
    btnFrameColor.setBackground(UserSettings.DEF_FRAME_COLOR);
    btnSlipColor.setBackground(UserSettings.DEF_SLIP_COLOR);
    btnFoilColor.setBackground(UserSettings.DEF_FOIL_COLOR);

    ckFillColors.setSelected(UserSettings.DES_FILL_COLORS);
    ckDrawBorder.setSelected(UserSettings.DES_DRAW_BORDER);
    ckShowMeasures.setSelected(UserSettings.DES_SHOW_GAPS);
    if (UserSettings.DES_COLOR_MODE == UserSettings.DES_COLOR_MODE_NONE) rbnUseDefColor.setSelected(true);
    else if (UserSettings.DES_COLOR_MODE == UserSettings.DES_COLOR_MODE_DEF) rbnUseGroupColor.setSelected(true);
    else if (UserSettings.DES_COLOR_MODE == UserSettings.DES_COLOR_MODE_OWN) rbnUseItemColor.setSelected(true);

    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UserSettings.DEF_COLOR = btnDefColor.getBackground();
        UserSettings.DEF_ARTWORK_COLOR = btnArtColor.getBackground();
        UserSettings.DEF_BOARD_COLOR = btnBoardColor.getBackground();
        UserSettings.DEF_FRAME_COLOR = btnFrameColor.getBackground();
        UserSettings.DEF_SLIP_COLOR = btnSlipColor.getBackground();
        UserSettings.DEF_FOIL_COLOR = btnFoilColor.getBackground();

        UserSettings.DES_FILL_COLORS = ckFillColors.isSelected();
        UserSettings.DES_DRAW_BORDER = ckDrawBorder.isSelected();
        UserSettings.DES_SHOW_GAPS = ckShowMeasures.isSelected();
        if (rbnUseGroupColor.isSelected()) UserSettings.DES_COLOR_MODE = UserSettings.DES_COLOR_MODE_DEF;
        else if (rbnUseItemColor.isSelected()) UserSettings.DES_COLOR_MODE = UserSettings.DES_COLOR_MODE_OWN;
        else UserSettings.DES_COLOR_MODE = UserSettings.DES_COLOR_MODE_NONE;

        hide();
      }
    });

    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hide();
      }
    });

    btnDefColor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDefColor.setBackground(new JColorChooser().showDialog(null, "Default display color", btnDefColor.getBackground()));
      }
    });

    btnArtColor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnArtColor.setBackground(new JColorChooser().showDialog(null, "Default Artwork display color", btnArtColor.getBackground()));
      }
    });

    btnBoardColor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnBoardColor.setBackground(new JColorChooser().showDialog(null, "Default Board display color", btnBoardColor.getBackground()));
      }
    });

    btnFrameColor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFrameColor.setBackground(new JColorChooser().showDialog(null, "Default Frame display color", btnFrameColor.getBackground()));
      }
    });

    btnSlipColor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSlipColor.setBackground(new JColorChooser().showDialog(null, "Default Slip display color", btnSlipColor.getBackground()));
      }
    });

    btnFoilColor.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFoilColor.setBackground(new JColorChooser().showDialog(null, "Default Foil display color", btnFoilColor.getBackground()));
      }
    });

    setModal(true);
    setVisible(true);
  }
  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border3 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border4 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border5 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border6 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border7 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border8 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border9 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    jPanel2.setLayout(gridBagLayout2);
    ckFillColors.setText("Fill Colors");
    ckDrawBorder.setBorder(border7);
    ckDrawBorder.setText("Draw Border");
    jPanel3.setLayout(gridBagLayout3);
    ckShowMeasures.setText("Show Measurements");
    rbnUseDefColor.setText("Use default color");
    rbnUseGroupColor.setText("Use group colors");
    rbnUseItemColor.setText("Use item colors");
    jPanel4.setLayout(gridBagLayout4);
    btnDefColor.setBorder(border1);
    btnDefColor.setPreferredSize(new Dimension(25, 25));
    jLabel1.setText("Default color");
    btnFrameColor.setBorder(border4);
    btnFrameColor.setPreferredSize(new Dimension(25, 25));
    btnArtColor.setBorder(border2);
    btnArtColor.setPreferredSize(new Dimension(25, 25));
    jLabel2.setText("Default Foil color");
    jLabel3.setText("Default Frame color");
    jLabel4.setText("Default Slip color");
    jLabel5.setText("Default Board color");
    jLabel6.setText("Default Artwork color");
    btnBoardColor.setBorder(border3);
    btnBoardColor.setPreferredSize(new Dimension(25, 25));
    btnFoilColor.setBorder(border5);
    btnFoilColor.setPreferredSize(new Dimension(25, 25));
    btnSlipColor.setBorder(border6);
    btnSlipColor.setPreferredSize(new Dimension(25, 25));
    btnCancel.setText("Cancel");
    btnOK.setText("OK");
    jPanel3.setBorder(border8);
    jPanel4.setBorder(border9);
    this.getContentPane().add(jPanel1,  BorderLayout.CENTER);
    this.getContentPane().add(jPanel2,  BorderLayout.SOUTH);
    jPanel1.add(jPanel3,          new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(ckDrawBorder,          new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
    jPanel3.add(ckFillColors,       new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
    jPanel3.add(ckShowMeasures,      new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
    jPanel3.add(rbnUseDefColor,    new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
    buttonGroup1.add(rbnUseDefColor);
    buttonGroup1.add(rbnUseGroupColor);
    buttonGroup1.add(rbnUseItemColor);
    jPanel3.add(rbnUseGroupColor,  new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
    jPanel3.add(rbnUseItemColor,  new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
    jPanel1.add(jPanel4,      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    jPanel4.add(btnDefColor,                  new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel1,               new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
    jPanel4.add(btnSlipColor,             new GridBagConstraints(2, 2, 1, 1, 0.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(btnFoilColor,            new GridBagConstraints(2, 1, 1, 1, 0.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(btnFrameColor,            new GridBagConstraints(2, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(btnBoardColor,           new GridBagConstraints(0, 2, 1, 1, 0.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(btnArtColor,          new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel2,        new GridBagConstraints(3, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
    jPanel4.add(jLabel3,        new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
    jPanel4.add(jLabel4,       new GridBagConstraints(3, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
    jPanel4.add(jLabel5,     new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
    jPanel4.add(jLabel6,    new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 10), 0, 0));
    jPanel2.add(btnCancel,     new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(btnOK,    new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
  }
}