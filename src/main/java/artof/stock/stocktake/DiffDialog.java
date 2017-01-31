package artof.stock.stocktake;

import artof.database.ArtofDB;
import artof.materials.MaterialDets;
import artof.utils.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;
import artof.stock.printing.DiscrepencyPrinter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DiffDialog
    extends JDialog {
  ArtofDB artofDB;

  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton btnClose = new JButton(new ImageIcon("images/Close.gif"));
  JPanel jPanel2 = new JPanel();
  JSplitPane jSplitPane1 = new JSplitPane();
  GridLayout gridLayout1 = new GridLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable tblStock1; // = new JTable();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTable tblStock2; // = new JTable();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton btnPrint = new JButton(new ImageIcon("images/Print.gif"));
  JLabel jLabel3 = new JLabel();
  JComboBox cbxPrev = new JComboBox();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JLabel jLabel4 = new JLabel();
  JTextField txtBoards1 = new JTextField();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JTextField txtFrames1 = new JTextField();
  JTextField txtBacks1 = new JTextField();
  JTextField txtSlips1 = new JTextField();
  JTextField txtFoils1 = new JTextField();
  JLabel jLabel10 = new JLabel();
  JLabel jLabel11 = new JLabel();
  JLabel jLabel12 = new JLabel();
  JLabel jLabel13 = new JLabel();
  JLabel jLabel14 = new JLabel();
  JLabel jLabel15 = new JLabel();
  JTextField txtBoards2 = new JTextField();
  JTextField txtFrames2 = new JTextField();
  JTextField txtBacks2 = new JTextField();
  JTextField txtSlips2 = new JTextField();
  JTextField txtFoils2 = new JTextField();
  JLabel jLabel16 = new JLabel();
  JLabel jLabel17 = new JLabel();
  JLabel jLabel18 = new JLabel();
  JLabel jLabel19 = new JLabel();
  JLabel jLabel20 = new JLabel();
  JLabel jLabel21 = new JLabel();
  JLabel jLabel22 = new JLabel();
  JLabel jLabel23 = new JLabel();
  JLabel jLabel24 = new JLabel();
  JLabel jLabel25 = new JLabel();
  ArrayList notInEstList;
  ArrayList notInStockList;

  public DiffDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);

    artofDB = ArtofDB.getCurrentDB();
    tblStock1 = new JTable(new DiffDataModel(this));
    tblStock2 = new JTable(new DiffDataModel(this));
    updateFields(-1);

    TableColumnModel tcm = tblStock1.getColumnModel();
    tcm.getColumn(3).setCellRenderer(new NumberRenderer());
    tcm.getColumn(4).setCellRenderer(new NumberRenderer(JTextField.CENTER));

    tcm = tblStock2.getColumnModel();
    tcm.getColumn(3).setCellRenderer(new NumberRenderer());
    tcm.getColumn(4).setCellRenderer(new NumberRenderer(JTextField.CENTER));

    Iterator it = artofDB.getPreviousStockTakes(true).iterator();
    while (it.hasNext()) {
      cbxPrev.addItem( (String) it.next());
    }
    try {
      cbxPrev.setSelectedIndex(0);
    }
    catch (IllegalArgumentException e) {
      //doen net fokkol
    }

    try {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    Dimension screenSize = getToolkit().getScreenSize();
    setSize(700, 500);
    setLocation(screenSize.width / 2 - this.getWidth() / 2,
                screenSize.height / 2 - this.getHeight() / 2);
    setVisible(true);
  }

  public DiffDialog() {
    this(null, "Discrepencies", true);
  }

  private void updateFields(int stockTakeID) {
    notInEstList = getItemsNotInStockList(artofDB.getTotalStockInStockTake(stockTakeID), artofDB.getTotalEstimatedStock(stockTakeID));
    notInStockList = getItemsNotInStockList(artofDB.getTotalEstimatedStock(stockTakeID), artofDB.getTotalStockInStockTake(stockTakeID));

    ((DiffDataModel)tblStock1.getModel()).refreshItems(notInEstList);
    ((DiffDataModel)tblStock2.getModel()).refreshItems(notInStockList);

    float boards = 0;
    float frames = 0;
    float backs = 0;
    float slips = 0;
    float foils = 0;
    Iterator it = notInEstList.iterator();
    while (it.hasNext()) {
      String[] item = (String[]) it.next();
      int type = Integer.parseInt(item[2]);
      float amount = Float.parseFloat(item[3]);
      if (type == MaterialDets.MAT_BOARD) {
        boards += amount;
      } else if (type == MaterialDets.MAT_FRAME) {
        frames += amount;
      } else if (type == MaterialDets.MAT_GB) {
        backs += amount;
      } else if (type == MaterialDets.MAT_SLIP) {
        slips += amount;
      } else if (type == MaterialDets.MAT_FOIL) {
        foils += amount;
      }
    }
    txtBoards1.setText(Utils.getCurrencyFormat(boards));
    txtFrames1.setText(Utils.getCurrencyFormat(frames));
    txtBacks1.setText(Utils.getCurrencyFormat(backs));
    txtSlips1.setText(Utils.getCurrencyFormat(slips));
    txtFoils1.setText(Utils.getCurrencyFormat(foils));

    boards = 0;
    frames = 0;
    backs = 0;
    slips = 0;
    foils = 0;
    it = notInStockList.iterator();
    while (it.hasNext()) {
      String[] item = (String[]) it.next();
      int type = Integer.parseInt(item[2]);
      float amount = Float.parseFloat(item[3]);
      if (type == MaterialDets.MAT_BOARD) {
        boards += amount;
      }
      else if (type == MaterialDets.MAT_FRAME) {
        frames += amount;
      }
      else if (type == MaterialDets.MAT_GB) {
        backs += amount;
      }
      else if (type == MaterialDets.MAT_SLIP) {
        slips += amount;
      }
      else if (type == MaterialDets.MAT_FOIL) {
        foils += amount;
      }
    }
    txtBoards2.setText(Utils.getCurrencyFormat(boards));
    txtFrames2.setText(Utils.getCurrencyFormat(frames));
    txtBacks2.setText(Utils.getCurrencyFormat(backs));
    txtSlips2.setText(Utils.getCurrencyFormat(slips));
    txtFoils2.setText(Utils.getCurrencyFormat(foils));
  }

  private ArrayList getItemsNotInStockList(ArrayList compList, ArrayList stockList) {
    Iterator itComp = compList.iterator();
    while (itComp.hasNext()) {
      String[] item = (String[]) itComp.next();

      Iterator itDiff = stockList.iterator();
      while (itDiff.hasNext()) {
        String[] diff = (String[]) itDiff.next();
        if (diff[0] != null && diff[0].equals(item[0]) && diff[1].equals(item[1])) {
          float diffLength = Float.parseFloat(diff[3]);
          float itemLength = Float.parseFloat(item[3]);
          float delta = diffLength - itemLength;
          if (delta <= 0) {
            itDiff.remove();
          }
          else {
            diff[3] = String.valueOf(delta);
          }
          break;
        }
      }
    }

    return stockList;
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    this.setResizable(true);
    jPanel2.setLayout(gridLayout1);
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jPanel3.setLayout(gridBagLayout1);
    jPanel4.setLayout(gridBagLayout2);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Items in stock not in stock take");
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel2.setText("Items in stock take not in stock");
    btnClose.setPreferredSize(new Dimension(25, 25));
    btnClose.setToolTipText("Close");
    btnClose.setText("");
    btnClose.addActionListener(new DiffDialog_btnClose_actionAdapter(this));
    jPanel1.setLayout(gridBagLayout3);
    btnPrint.setPreferredSize(new Dimension(25, 25));
    btnPrint.setText("");
    btnPrint.addActionListener(new DiffDialog_btnPrint_actionAdapter(this));
    jLabel3.setText("Previous Stock Takes:");
    cbxPrev.setPreferredSize(new Dimension(180, 21));
    cbxPrev.addItemListener(new DiffDialog_cbxPrev_itemAdapter(this));
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel4.setText("Totals:");
    jLabel5.setText("Boards:");
    txtBoards1.setMinimumSize(new Dimension(60, 21));
    txtBoards1.setPreferredSize(new Dimension(60, 21));
    txtBoards1.setEditable(false);
    txtBoards1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel6.setText("Frames:");
    jLabel7.setText("Foils:");
    jLabel8.setText("Backs:");
    jLabel9.setRequestFocusEnabled(true);
    jLabel9.setText("Slips:");
    txtFrames1.setMinimumSize(new Dimension(60, 21));
    txtFrames1.setPreferredSize(new Dimension(60, 21));
    txtFrames1.setEditable(false);
    txtFrames1.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBacks1.setMinimumSize(new Dimension(60, 21));
    txtBacks1.setPreferredSize(new Dimension(60, 21));
    txtBacks1.setEditable(false);
    txtBacks1.setHorizontalAlignment(SwingConstants.RIGHT);
    txtSlips1.setMinimumSize(new Dimension(60, 21));
    txtSlips1.setPreferredSize(new Dimension(60, 21));
    txtSlips1.setEditable(false);
    txtSlips1.setHorizontalAlignment(SwingConstants.RIGHT);
    txtFoils1.setMinimumSize(new Dimension(60, 21));
    txtFoils1.setPreferredSize(new Dimension(60, 21));
    txtFoils1.setEditable(false);
    txtFoils1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel10.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel10.setText("Totals:");
    jLabel11.setText("Boards:");
    jLabel12.setText("Foils:");
    jLabel13.setText("Frames:");
    jLabel14.setText("Backs:");
    jLabel15.setText("Slips:");
    txtBoards2.setMinimumSize(new Dimension(60, 21));
    txtBoards2.setPreferredSize(new Dimension(60, 21));
    txtBoards2.setEditable(false);
    txtBoards2.setHorizontalAlignment(SwingConstants.RIGHT);
    txtFrames2.setMinimumSize(new Dimension(60, 21));
    txtFrames2.setPreferredSize(new Dimension(60, 21));
    txtFrames2.setEditable(false);
    txtFrames2.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBacks2.setMinimumSize(new Dimension(60, 21));
    txtBacks2.setPreferredSize(new Dimension(60, 21));
    txtBacks2.setToolTipText("");
    txtBacks2.setEditable(false);
    txtBacks2.setHorizontalAlignment(SwingConstants.RIGHT);
    txtSlips2.setMinimumSize(new Dimension(60, 21));
    txtSlips2.setPreferredSize(new Dimension(60, 21));
    txtSlips2.setEditable(false);
    txtSlips2.setHorizontalAlignment(SwingConstants.RIGHT);
    txtFoils2.setMinimumSize(new Dimension(60, 21));
    txtFoils2.setPreferredSize(new Dimension(60, 21));
    txtFoils2.setEditable(false);
    txtFoils2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel16.setText("m�");
    jLabel17.setToolTipText("");
    jLabel17.setText("m");
    jLabel18.setText("m�");
    jLabel19.setText("m");
    jLabel20.setText("m");
    jLabel21.setText("m�");
    jLabel22.setText("m");
    jLabel23.setText("m");
    jLabel24.setText("m�");
    jLabel25.setText("m");
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(btnClose, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jSplitPane1, null);
    jSplitPane1.add(jPanel3, JSplitPane.LEFT);
    jPanel3.add(jLabel1, new GridBagConstraints(0, 0, 8, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(10, 5, 5, 5), 0, 0));
    jPanel3.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 6, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(5, 5, 5, 0), 0, 0));
    jPanel3.add(jLabel4, new GridBagConstraints(1, 1, 7, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 0, 5), 0, 0));
    jScrollPane1.getViewport().add(tblStock1, null);
    jSplitPane1.add(jPanel4, JSplitPane.RIGHT);
    jPanel4.add(jLabel2, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(10, 5, 5, 5), 0, 0));
    jPanel4.add(jScrollPane2, new GridBagConstraints(0, 1, 1, 6, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(5, 5, 5, 0), 0, 0));
    jPanel4.add(jLabel10, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel11, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel12, new GridBagConstraints(1, 6, 1, 1, 0.0, 1.0
                                                 , GridBagConstraints.NORTHWEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel13, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel14, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel15, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(txtBoards2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(5, 5, 0, 5), 0, 0));
    jScrollPane2.getViewport().add(tblStock2, null);
    jPanel1.add(btnPrint, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 20, 5, 5), 0, 0));
    jPanel1.add(cbxPrev, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtBoards1, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTH, GridBagConstraints.NONE,
        new Insets(5, 0, 0, 0), 0, 0));
    jPanel3.add(jLabel7, new GridBagConstraints(1, 6, 1, 1, 0.0, 1.0
                                                , GridBagConstraints.NORTHWEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel8, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel9, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(txtFrames1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 5, 0, 5), 0, 0));
    jPanel3.add(txtBacks1, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 5, 0, 5), 0, 0));
    jPanel3.add(txtSlips1, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 5, 0, 5), 0, 0));
    jPanel3.add(txtFoils1, new GridBagConstraints(2, 6, 1, 1, 0.0, 1.0
                                                  , GridBagConstraints.NORTH,
                                                  GridBagConstraints.NONE,
                                                  new Insets(2, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel16, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel17, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel18, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel19, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel3.add(jLabel20, new GridBagConstraints(3, 6, 1, 1, 0.0, 1.0
                                                 , GridBagConstraints.NORTHWEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(txtFrames2, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 5, 0, 5), 0, 0));
    jPanel4.add(txtBacks2, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 5, 0, 5), 0, 0));
    jPanel4.add(txtSlips2, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 5, 0, 5), 0, 0));
    jPanel4.add(txtFoils2, new GridBagConstraints(2, 6, 1, 1, 0.0, 1.0
                                                  , GridBagConstraints.NORTH,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel21, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel22, new GridBagConstraints(3, 6, 1, 1, 0.0, 1.0
                                                 , GridBagConstraints.NORTHWEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel23, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel24, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel4.add(jLabel25, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jSplitPane1.setDividerLocation(200);
  }

  void btnClose_actionPerformed(ActionEvent e) {
    hide();
  }

  void btnPrint_actionPerformed(ActionEvent e) {
    DiscrepencyPrinter discrepencyPrinter = new DiscrepencyPrinter(notInEstList,notInStockList,this);
  }

  void cbxPrev_itemStateChanged(ItemEvent e) {
    int stockTakeID = -1;
    try {
      String id = ( (String) cbxPrev.getSelectedItem());
      id = id.substring(0, id.indexOf(". "));
      stockTakeID = Integer.parseInt(id);
    }
    catch (Exception x) {
      // doen fokkol: default na -1
    }
    updateFields(stockTakeID);
  }
  /** Christie begin*/
  public String getCbxPrevSelectedValue() {
    return (String) cbxPrev.getSelectedItem();
  }

  public String getTxtBoards1() {
    return txtBoards1.getText();
  }

  public String getTxtBoards2() {
    return txtBoards2.getText();
  }

  public String getTxtFrames1() {
    return txtFrames1.getText();
  }

  public String getTxtFrames2() {
    return txtFrames2.getText();
  }

  public String getTxtBacks1() {
    return txtBacks1.getText();
  }

  public String getTxtBacks2() {
    return txtBacks2.getText();
  }

  public String getTxtSlips1() {
    return txtSlips1.getText();
  }

  public String getTxtSlips2() {
    return txtSlips2.getText();
  }

  public String getTxtFoils1() {
    return txtFoils1.getText();
  }

  public String getTxtFoils2() {
    return txtFoils2.getText();
  }

  public JTable getTableStock1() {
    return tblStock1;
  }

  //Christie Stop
}

class DiffDialog_btnClose_actionAdapter
    implements java.awt.event.ActionListener {
  DiffDialog adaptee;

  DiffDialog_btnClose_actionAdapter(DiffDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnClose_actionPerformed(e);
  }
}

class DiffDialog_btnPrint_actionAdapter
    implements java.awt.event.ActionListener {
  DiffDialog adaptee;

  DiffDialog_btnPrint_actionAdapter(DiffDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnPrint_actionPerformed(e);
  }
}

class DiffDialog_cbxPrev_itemAdapter
    implements java.awt.event.ItemListener {
  DiffDialog adaptee;

  DiffDialog_cbxPrev_itemAdapter(DiffDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.cbxPrev_itemStateChanged(e);
  }
}
