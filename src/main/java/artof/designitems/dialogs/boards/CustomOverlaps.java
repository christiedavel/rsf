package artof.designitems.dialogs.boards;
import artof.designitems.DesignBoard;
import artof.utils.Utils;
import artof.utils.CustomTextField;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CustomOverlaps extends JPanel {
  private DesignBoard curItem;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private CustomTextField txtTopOL = new CustomTextField();
  private CustomTextField txtRightOL = new CustomTextField();
  private JLabel jLabel8 = new JLabel();
  private CustomTextField txtLeftOL = new CustomTextField();
  private JLabel jLabel7 = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private JCheckBox ckMatchOLs = new JCheckBox();
  private JLabel jLabel5 = new JLabel();
  private CustomTextField txtBottomOL = new CustomTextField();
  private Border border1;

  public CustomOverlaps(DesignBoard item) {
    curItem = item;
    updateValues(item);

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    ckMatchOLs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (ckMatchOLs.isSelected()) {
          txtLeftOL.setEnabled(false);
          txtRightOL.setEnabled(false);
          txtBottomOL.setEnabled(false);
          txtLeftOL.setText(null);
          txtRightOL.setText(null);
          txtBottomOL.setText(null);

          txtLeftOL.setBackground(Color.gray);
          txtRightOL.setBackground(Color.gray);
          txtBottomOL.setBackground(Color.gray);
        } else {
          txtLeftOL.setEnabled(true);
          txtRightOL.setEnabled(true);
          txtBottomOL.setEnabled(true);
          txtLeftOL.setText(Utils.getCurrencyFormat(curItem.getOverlapLeft()));
          txtRightOL.setText(Utils.getCurrencyFormat(curItem.getOverlapRight()));
          txtBottomOL.setText(Utils.getCurrencyFormat(curItem.getOverlapBottom()));

          txtLeftOL.setBackground(Color.white);
          txtRightOL.setBackground(Color.white);
          txtBottomOL.setBackground(Color.white);
        }
      }
    });
  }

  public void setValues() throws NumberFormatException {
    curItem.setOverlapTop(Double.parseDouble(txtTopOL.getText()));
    if (ckMatchOLs.isSelected()) {
      curItem.setMatchOverlaps(true);
      curItem.setOverlapRight(Double.parseDouble(txtTopOL.getText()));
      curItem.setOverlapLeft(Double.parseDouble(txtTopOL.getText()));
      curItem.setOverlapBottom(Double.parseDouble(txtTopOL.getText()));
    } else {
      curItem.setMatchOverlaps(false);
      curItem.setOverlapRight(Double.parseDouble(txtRightOL.getText()));
      curItem.setOverlapLeft(Double.parseDouble(txtLeftOL.getText()));
      curItem.setOverlapBottom(Double.parseDouble(txtBottomOL.getText()));
    }
  }

  public void updateValues(DesignBoard newItem) {
    txtTopOL.setText(Utils.getCurrencyFormat(curItem.getOverlapTop()));
    ckMatchOLs.setSelected(curItem.getMatchOverlaps());
    if (curItem.getMatchOverlaps()) {
      txtRightOL.setEnabled(false);
      txtLeftOL.setEnabled(false);
      txtBottomOL.setEnabled(false);
      txtRightOL.setText(null);
      txtLeftOL.setText(null);
      txtBottomOL.setText(null);
      txtRightOL.setBackground(Color.gray);
      txtLeftOL.setBackground(Color.gray);
      txtBottomOL.setBackground(Color.gray);
    } else {
      txtRightOL.setEnabled(true);
      txtLeftOL.setEnabled(true);
      txtBottomOL.setEnabled(true);
      txtRightOL.setText(Utils.getCurrencyFormat(curItem.getOverlapRight()));
      txtLeftOL.setText(Utils.getCurrencyFormat(curItem.getOverlapLeft()));
      txtBottomOL.setText(Utils.getCurrencyFormat(curItem.getOverlapBottom()));
      txtRightOL.setBackground(Color.white);
      txtLeftOL.setBackground(Color.white);
      txtBottomOL.setBackground(Color.white);
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140)),BorderFactory.createEmptyBorder(5,5,5,5));
    jLabel5.setText("Left Overlap");
    ckMatchOLs.setSelected(true);
    ckMatchOLs.setText("Match All");
    jLabel6.setText("Right Overlap");
    jLabel7.setText("Top Overlap");
    jLabel8.setText("Bottom Overlap");
    this.setLayout(gridBagLayout1);
    this.setBorder(border1);
    this.add(jLabel6,           new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    this.add(jLabel5,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(jLabel7,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckMatchOLs,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtLeftOL, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtBottomOL,  new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtTopOL,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(jLabel8,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    this.add(txtRightOL,  new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }
}