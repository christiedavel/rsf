package artof.designitems.dialogs.boards;
import artof.designitems.DesignItem2;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class DesignPanel2 extends JPanel implements DesignPanelFunctions {
  private DesignItem2 curItem;
  Border border1;
  ButtonGroup buttonGroup1 = new ButtonGroup();
  Border border2;
  Border border3;
  Border border4;
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  CustomTextField txtHeight = new CustomTextField();
  CustomTextField txtWidth = new CustomTextField();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel1 = new JLabel();
  Border border5;
  GridBagLayout gridBagLayout2 = new GridBagLayout();

  public DesignPanel2(DesignItem2 item) {
    try {
      jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }
    updateValues(item);
  }

  public void setValues() throws NumberFormatException {
    double width = Double.parseDouble(txtWidth.getText());
    double height = Double.parseDouble(txtHeight.getText());
    if (width <= 0 || height <= 0) throw new NumberFormatException();
    curItem.setLeftGap(width / 2);
    curItem.setRightGap(width / 2);
    curItem.setTopGap(height / 2);
    curItem.setBottomGap(height / 2);
    curItem.setLeftOffset(0);
    curItem.setRightOffset(0);
    curItem.setTopOffset(0);
    curItem.setBottomOffset(0);
  }

  public void updateValues(DesignItem2 newItem) {
    try {
      curItem = newItem;
      txtWidth.setText(Utils.getCurrencyFormat(curItem.getOuterWidth()));
      txtHeight.setText(Utils.getCurrencyFormat(curItem.getOuterHeight()));
    } catch (NullPointerException e) {
    }
  }

  public void showOffsets(boolean show) {
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEmptyBorder(5,5,5,5);
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(178, 178, 178));
    border3 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(124, 124, 124),new Color(178, 178, 178));
    border4 = BorderFactory.createEmptyBorder();
    border5 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    setLayout(gridBagLayout2);
    this.setBorder(border4);
    jPanel1.setLayout(gridBagLayout1);
    txtHeight.setBorder(BorderFactory.createLoweredBevelBorder());
    txtWidth.setBorder(BorderFactory.createLoweredBevelBorder());
    jLabel2.setText("Height");
    jLabel1.setText("Width");
    jPanel1.setBorder(border5);
    this.add(jPanel1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtHeight,     new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtWidth,    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel2,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel1,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
  }
}