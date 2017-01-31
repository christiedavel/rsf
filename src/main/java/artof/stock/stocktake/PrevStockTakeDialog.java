package artof.stock.stocktake;
import artof.database.ArtofDB;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PrevStockTakeDialog extends JDialog {
  private ArtofDB artofDB;
  private StockTakeDialog dialog;
  private boolean groupItems = true;
  private int stockTakeID = -1;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton btnClose = new JButton(new ImageIcon("images/Close.gif"));
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblStock;// = new JTable();
  private Border border1;
  JRadioButton rbnSingle = new JRadioButton();
  JRadioButton rbnGroup = new JRadioButton();
  ButtonGroup buttonGroup1 = new ButtonGroup();

  public PrevStockTakeDialog(int stockTakeID) {
    artofDB = ArtofDB.getCurrentDB();
    this.stockTakeID = stockTakeID;
    rbnGroup.setSelected(true);
    tblStock = new JTable(new PrevStockTakeDataModel(artofDB.getPreviousStockTake(stockTakeID, groupItems)));

    TableColumnModel tcm = tblStock.getColumnModel();
    tcm.getColumn(3).setCellRenderer(new NumberRenderer());
    tcm.getColumn(4).setCellRenderer(new NumberRenderer());
    tcm.getColumn(5).setCellRenderer(new NumberRenderer());

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    setTitle("Stock Taking");
    Dimension screenSize = getToolkit().getScreenSize();
    setSize(700, 500);
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public String getItemCodeSelected(int row) {
    Object ding = ((StockTakeDataModel)tblStock.getModel()).getValueAt(row, 0);
    try {
      return (String)ding;
    } catch (ClassCastException e) {
      return null;
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    this.setModal(true);
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    btnClose.setPreferredSize(new Dimension(25, 25));
    btnClose.setMargin(new Insets(0, 0, 0, 0));
    btnClose.setText("");
    btnClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnClose_actionPerformed(e);
      }
    });
    rbnSingle.setText("Single Items");
    rbnSingle.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rbnSingle_actionPerformed(e);
      }
    });
    rbnGroup.setText("Group Items");
    rbnGroup.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rbnGroup_actionPerformed(e);
      }
    });
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jScrollPane1,                    new GridBagConstraints(0, 1, 5, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(btnClose,                new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 20), 0, 0));
    jPanel1.add(rbnSingle,    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 5, 5), 0, 0));
    jPanel1.add(rbnGroup,     new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jScrollPane1.getViewport().add(tblStock, null);
    buttonGroup1.add(rbnSingle);
    buttonGroup1.add(rbnGroup);
  }


  void btnClose_actionPerformed(ActionEvent e) {
    hide();
  }

  void rbnSingle_actionPerformed(ActionEvent e) {
    if (rbnSingle.isSelected()) {
      groupItems = false;
      ((PrevStockTakeDataModel)tblStock.getModel()).refreshItems(artofDB.getPreviousStockTake(stockTakeID, groupItems));
    }
  }

  void rbnGroup_actionPerformed(ActionEvent e) {
    if (rbnGroup.isSelected()) {
      groupItems = true;
      ((PrevStockTakeDataModel)tblStock.getModel()).refreshItems(artofDB.getPreviousStockTake(stockTakeID, groupItems));
    }
  }
}