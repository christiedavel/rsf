package artof.designitems.dialogs.boards;
import artof.designitems.DesignItem2;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DesignPanel extends JPanel implements DesignPanelFunctions {
  private DesignItem2 curItem;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  Border border1;
  Border border2;
  JPanel jPanel3 = new JPanel();
  Border border3;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JLabel jLabel5 = new JLabel();
  CustomTextField txtLeftGap = new CustomTextField();
  JLabel jLabel6 = new JLabel();
  CustomTextField txtRightGap = new CustomTextField();
  JLabel jLabel7 = new JLabel();
  CustomTextField txtTopGap = new CustomTextField();
  JLabel jLabel8 = new JLabel();
  CustomTextField txtBottomGap = new CustomTextField();
  JCheckBox ckMatchGaps = new JCheckBox();
  CustomTextField txtTopOffset = new CustomTextField();
  CustomTextField txtRightOffset = new CustomTextField();
  JLabel lblBottomOffset = new JLabel();
  JLabel lblTopOffset = new JLabel();
  CustomTextField txtLeftOffset = new CustomTextField();
  JLabel lblRightOffset = new JLabel();
  JCheckBox ckMatchOffsets = new JCheckBox();
  JLabel lblLeftOffset = new JLabel();
  CustomTextField txtBottomOffset = new CustomTextField();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  Border border4;
  private BorderLayout borderLayout2 = new BorderLayout();

  public DesignPanel(DesignItem2 item) {
    curItem = item;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    updateValues(item);

    ckMatchGaps.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (ckMatchGaps.isSelected()) {
          txtLeftGap.setEnabled(false);
          txtRightGap.setEnabled(false);
          txtBottomGap.setEnabled(false);
          txtLeftGap.setText(null);
          txtRightGap.setText(null);
          txtBottomGap.setText(null);

          txtLeftGap.setBackground(Color.gray);
          txtRightGap.setBackground(Color.gray);
          txtBottomGap.setBackground(Color.gray);
        } else {
          txtLeftGap.setEnabled(true);
          txtRightGap.setEnabled(true);
          txtBottomGap.setEnabled(true);
          txtLeftGap.setText(Utils.getCurrencyFormat(curItem.getLeftGap()));
          txtRightGap.setText(Utils.getCurrencyFormat(curItem.getRightGap()));
          txtBottomGap.setText(Utils.getCurrencyFormat(curItem.getBottomGap()));

          txtLeftGap.setBackground(Color.white);
          txtRightGap.setBackground(Color.white);
          txtBottomGap.setBackground(Color.white);
        }
      }
    });

    ckMatchOffsets.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (ckMatchOffsets.isSelected()) {
          txtRightOffset.setEnabled(false);
          txtLeftOffset.setEnabled(false);
          txtBottomOffset.setEnabled(false);
          txtRightOffset.setText(null);
          txtLeftOffset.setText(null);
          txtBottomOffset.setText(null);

          txtRightOffset.setBackground(Color.gray);
          txtLeftOffset.setBackground(Color.gray);
          txtBottomOffset.setBackground(Color.gray);
        } else {
          txtRightOffset.setEnabled(true);
          txtLeftOffset.setEnabled(true);
          txtBottomOffset.setEnabled(true);
          txtRightOffset.setText(Utils.getCurrencyFormat(curItem.getRightOffset()));
          txtLeftOffset.setText(Utils.getCurrencyFormat(curItem.getLeftOffset()));
          txtBottomOffset.setText(Utils.getCurrencyFormat(curItem.getBottomOffset()));

          txtRightOffset.setBackground(Color.white);
          txtLeftOffset.setBackground(Color.white);
          txtBottomOffset.setBackground(Color.white);
        }
      }
    });
  }

  public void setValues() throws NumberFormatException {
    curItem.setTopGap(Double.parseDouble(txtTopGap.getText()));
    if (ckMatchGaps.isSelected()) {
      curItem.setMatchGaps(true);
      curItem.setRightGap(Double.parseDouble(txtTopGap.getText()));
      curItem.setLeftGap(Double.parseDouble(txtTopGap.getText()));
      curItem.setBottomGap(Double.parseDouble(txtTopGap.getText()));
    } else {
      curItem.setMatchGaps(false);
      curItem.setRightGap(Double.parseDouble(txtRightGap.getText()));
      curItem.setLeftGap(Double.parseDouble(txtLeftGap.getText()));
      curItem.setBottomGap(Double.parseDouble(txtBottomGap.getText()));
    }

    curItem.setTopOffset(Double.parseDouble(txtTopOffset.getText()));
    if (ckMatchOffsets.isSelected()) {
      curItem.setMatchOffsets(true);
      curItem.setRightOffset(Double.parseDouble(txtTopOffset.getText()));
      curItem.setLeftOffset(Double.parseDouble(txtTopOffset.getText()));
      curItem.setBottomOffset(Double.parseDouble(txtTopOffset.getText()));
    } else {
      curItem.setMatchOffsets(false);
      curItem.setRightOffset(Double.parseDouble(txtRightOffset.getText()));
      curItem.setLeftOffset(Double.parseDouble(txtLeftOffset.getText()));
      curItem.setBottomOffset(Double.parseDouble(txtBottomOffset.getText()));
    }
  }

  public void updateValues(DesignItem2 newItem) {
    txtTopGap.setText(Utils.getCurrencyFormat(curItem.getTopGap()));
    ckMatchGaps.setSelected(curItem.getMatchGaps());
    if (curItem.getMatchGaps()) {
      txtRightGap.setEnabled(false);
      txtLeftGap.setEnabled(false);
      txtBottomGap.setEnabled(false);
      txtRightGap.setText(null);
      txtLeftGap.setText(null);
      txtBottomGap.setText(null);
      txtRightGap.setBackground(Color.gray);
      txtLeftGap.setBackground(Color.gray);
      txtBottomGap.setBackground(Color.gray);
    } else {
      txtRightGap.setEnabled(true);
      txtLeftGap.setEnabled(true);
      txtBottomGap.setEnabled(true);
      txtRightGap.setText(Utils.getCurrencyFormat(curItem.getRightGap()));
      txtLeftGap.setText(Utils.getCurrencyFormat(curItem.getLeftGap()));
      txtBottomGap.setText(Utils.getCurrencyFormat(curItem.getBottomGap()));
      txtRightGap.setBackground(Color.white);
      txtLeftGap.setBackground(Color.white);
      txtBottomGap.setBackground(Color.white);
    }

    txtTopOffset.setText(Utils.getCurrencyFormat(curItem.getTopOffset()));
    ckMatchOffsets.setSelected(curItem.getMatchOffsets());
    if (curItem.getMatchOffsets()) {
      txtRightOffset.setEnabled(false);
      txtLeftOffset.setEnabled(false);
      txtBottomOffset.setEnabled(false);
      txtRightOffset.setText("");
      txtLeftOffset.setText("");
      txtBottomOffset.setText("");
      txtRightOffset.setBackground(Color.gray);
      txtLeftOffset.setBackground(Color.gray);
      txtBottomOffset.setBackground(Color.gray);
    } else {
      txtRightOffset.setEnabled(true);
      txtLeftOffset.setEnabled(true);
      txtBottomOffset.setEnabled(true);
      txtRightOffset.setText(Utils.getCurrencyFormat(curItem.getRightOffset()));
      txtLeftOffset.setText(Utils.getCurrencyFormat(curItem.getLeftOffset()));
      txtBottomOffset.setText(Utils.getCurrencyFormat(curItem.getBottomOffset()));
      txtRightOffset.setBackground(Color.white);
      txtLeftOffset.setBackground(Color.white);
      txtBottomOffset.setBackground(Color.white);
    }
  }

  public void showOffsets(boolean show) {
    if (show)
      jPanel1.add(jPanel3, BorderLayout.SOUTH);
    else
      jPanel1.remove(jPanel3);
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = BorderFactory.createEmptyBorder();
    border3 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border4 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    setLayout(borderLayout1);
    jPanel1.setLayout(borderLayout2);
    jPanel2.setBorder(border1);
    jPanel2.setLayout(gridBagLayout2);
    jPanel1.setBorder(border2);
    jPanel3.setBorder(border3);
    jPanel3.setLayout(gridBagLayout3);
    jLabel5.setText("Left Face");
    jLabel6.setText("Right Face");
    jLabel7.setText("Top Face");
    jLabel8.setText("Bottom Face");
    ckMatchGaps.setText("Match All");
    lblBottomOffset.setText("Bottom Offset");
    lblTopOffset.setText("Top Offset");
    lblRightOffset.setText("Right Offset");
    ckMatchOffsets.setText("Match All");
    lblLeftOffset.setText("Left Offset");
    add(jPanel1,  BorderLayout.CENTER);
    jPanel1.add(jPanel2,  BorderLayout.CENTER);
    //jPanel1.add(jPanel3,  BorderLayout.SOUTH);
    jPanel2.add(ckMatchGaps,          new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtTopGap, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtLeftGap, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel8, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtRightGap, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtBottomGap, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(ckMatchOffsets,         new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    jPanel3.add(lblTopOffset,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(lblLeftOffset,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtTopOffset,    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtLeftOffset,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(lblBottomOffset, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(lblRightOffset, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtRightOffset, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtBottomOffset, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }
}