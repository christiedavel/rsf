package artof.stock.designs;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import artof.utils.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StockTable extends JTable {
  public StockTable(TableModel model) {
    super(model);

    ToolTipManager.sharedInstance().unregisterComponent(this);
    ToolTipManager.sharedInstance().unregisterComponent(this.getTableHeader());

    TableColumnModel tcm = getColumnModel();
    //TableCheckBox ck = new TableCheckBox();
    //tcm.getColumn(4).setCellEditor(new DefaultCellEditor(new CustomTextField()));
    //tcm.getColumn(5).setCellEditor(new DefaultCellEditor(new CustomTextField()));

    tcm.getColumn(1).setCellRenderer(new NumberRenderer());
    tcm.getColumn(5).setCellRenderer(new NumberRenderer());
    tcm.getColumn(6).setCellRenderer(new NumberRenderer());

    tcm.getColumn(0).setPreferredWidth(35);
    tcm.getColumn(0).setMinWidth(35);
    tcm.getColumn(1).setPreferredWidth(50);
    tcm.getColumn(2).setPreferredWidth(100);
    tcm.getColumn(3).setPreferredWidth(100);
    tcm.getColumn(4).setPreferredWidth(100);

    autoCreateColumnsFromModel=false;
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  public Dimension getPreferredScrollableViewportSize() {
    return new Dimension (100,100);
  }
}