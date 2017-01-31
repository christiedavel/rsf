package artof.utils;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StockPanel extends JPanel {
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JCheckBox ckUse = new JCheckBox();
  JCheckBox ckShowOnPrint = new JCheckBox();
  JCheckBox ckWarnings = new JCheckBox();
  JCheckBox ckStockList = new JCheckBox();

  public StockPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    ckUse.setSelected(UserSettings.OPT_USE);
    ckShowOnPrint.setSelected(UserSettings.OPT_PRINT);
    ckWarnings.setSelected(UserSettings.OPT_WARNINGS);
    ckStockList.setSelected(UserSettings.OPT_STOCKLIST);
  }

  public void doOK() {
    UserSettings.OPT_USE = ckUse.isSelected();
    UserSettings.OPT_PRINT = ckShowOnPrint.isSelected();
    UserSettings.OPT_WARNINGS = ckWarnings.isSelected();
    UserSettings.OPT_STOCKLIST = ckStockList.isSelected();
  }

  void jbInit() throws Exception {
    jLabel1.setText("Stock optimization settings:");
    this.setLayout(gridBagLayout1);
    ckUse.setText("Use automatic optimization");
    ckShowOnPrint.setText("Display optimized selections on desing printout");
    ckWarnings.setText("Display warning message if item is not in stock");
    ckStockList.setText("Print stock list with cutting list");
    this.add(jLabel1,       new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    this.add(ckUse,       new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckShowOnPrint,       new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckWarnings,         new GridBagConstraints(0, 4, 2, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckStockList,       new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
  }
}
