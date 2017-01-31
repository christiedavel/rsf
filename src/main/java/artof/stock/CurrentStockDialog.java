package artof.stock;

import artof.database.ArtofDB;
import artof.materials.MaterialDets;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import artof.stock.printing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CurrentStockDialog
    extends JDialog {
  private ArtofDB artofDB;
  private CurrentStockDialog dialog;
  private boolean groupItems = true;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton btnClose = new JButton(new ImageIcon("images/Close.gif"));
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable tblStock; // = new JTable();
  private Border border1;
  JRadioButton rbnSingle = new JRadioButton();
  JRadioButton rbnGroup = new JRadioButton();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JButton btnPrint = new JButton(new ImageIcon("images/Print.gif"));
  JLabel jLabel1 = new JLabel();
  JComboBox cbxMaterial = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JLabel lblFoils = new JLabel();
  JLabel lblBoards = new JLabel();
  JLabel lblFrames = new JLabel();
  JLabel lblBacks = new JLabel();
  JLabel lblSlips = new JLabel();
  JTextField txtBoards = new JTextField();
  JTextField txtFrames = new JTextField();
  JTextField txtBacks = new JTextField();
  JTextField txtSlips = new JTextField();
  JTextField txtFoils = new JTextField();
  JLabel lblm1 = new JLabel();
  JLabel lblm3 = new JLabel();
  JLabel lblm5 = new JLabel();
  JLabel lblm4 = new JLabel();
  JLabel lblm2 = new JLabel();
  JScrollPane jScrollPane2 = new JScrollPane();
  JLabel jLabel3 = new JLabel();
  JList lstIDs = new JList();
  private int lastEditingRow = -1;
  JLabel jLabel4 = new JLabel();
  JTextField txtShelf = new JTextField();
  private long selStockId = -1;

  public CurrentStockDialog() {
    artofDB = ArtofDB.getCurrentDB();
    rbnGroup.setSelected(true);
    tblStock = new JTable(new CurrentStockDataModel(artofDB.getCurrentStock(
        MaterialDets.MAT_ALL, groupItems))) {
      public void valueChanged(ListSelectionEvent e) {
        try {
          lastEditingRow = tblStock.getSelectedRow();
          displayStockIDs(tblStock.getSelectedRow());

        }
        catch (NullPointerException x) {
          // doen fokkol
        }
      }
    };
    tblStock.getSelectionModel().setSelectionMode(ListSelectionModel.
                                                  SINGLE_SELECTION);

    TableColumnModel tcm = tblStock.getColumnModel();
    tcm.getColumn(3).setCellRenderer(new NumberRenderer());
    tcm.getColumn(4).setCellRenderer(new NumberRenderer());
    tcm.getColumn(5).setCellRenderer(new NumberRenderer());

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    for (int i = 0; i < MaterialDets.MAT_TYPES.length; i++) {
      cbxMaterial.addItem(MaterialDets.MAT_TYPES[i]);
    }
    cbxMaterial.setSelectedIndex(0);

    setTitle("Current Stock");
    Dimension screenSize = getToolkit().getScreenSize();
    setSize(750, 500);
    setLocation(screenSize.width / 2 - this.getWidth() / 2,
                screenSize.height / 2 - this.getHeight() / 2);
    setVisible(true);
  }

  private void displayStockIDs(int row) {
    java.util.ArrayList idList = ( (CurrentStockDataModel) tblStock.getModel()).
        getStockIDs(row, groupItems);
    lstIDs.setListData(idList.toArray());
    repaint();
    if (idList.size() == 1) {
      artofDB.saveShelf(selStockId,txtShelf.getText());
      lstIDs.setSelectedIndex(0);
      selStockId = (long) ((Integer)idList.get(0)).intValue();
      String shelfString = artofDB.getShelf(selStockId);
      txtShelf.setText(shelfString);
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    this.setModal(true);
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    btnClose.setPreferredSize(new Dimension(25, 25));
    btnClose.setMargin(new Insets(2, 2, 2, 2));
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
    btnPrint.setPreferredSize(new Dimension(25, 25));
    btnPrint.setMargin(new Insets(2, 2, 2, 2));
    btnPrint.setText("");
    btnPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrint_actionPerformed(e);
      }
    });

    jLabel1.setText("Material");
    cbxMaterial.setPreferredSize(new Dimension(150, 21));
    cbxMaterial.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        cbxMaterial_itemStateChanged(e);
      }
    });
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel2.setText("Totals:");
    lblFoils.setText("Foils");
    lblBoards.setText("Boards");
    lblFrames.setText("Frames");
    lblBacks.setText("Backs");
    lblSlips.setText("Slips");
    txtBoards.setMinimumSize(new Dimension(60, 21));
    txtBoards.setPreferredSize(new Dimension(60, 21));
    txtBoards.setEditable(false);
    txtBoards.setText("");
    txtBoards.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBacks.setMinimumSize(new Dimension(60, 21));
    txtBacks.setPreferredSize(new Dimension(60, 21));
    txtBacks.setEditable(false);
    txtBacks.setText("");
    txtBacks.setHorizontalAlignment(SwingConstants.RIGHT);
    txtSlips.setMinimumSize(new Dimension(60, 21));
    txtSlips.setPreferredSize(new Dimension(60, 21));
    txtSlips.setEditable(false);
    txtSlips.setText("");
    txtSlips.setHorizontalAlignment(SwingConstants.RIGHT);
    txtFrames.setMinimumSize(new Dimension(60, 21));
    txtFrames.setPreferredSize(new Dimension(60, 21));
    txtFrames.setEditable(false);
    txtFrames.setText("");
    txtFrames.setHorizontalAlignment(SwingConstants.RIGHT);
    txtFoils.setMinimumSize(new Dimension(60, 21));
    txtFoils.setPreferredSize(new Dimension(60, 21));
    txtFoils.setEditable(false);
    txtFoils.setText("");
    txtFoils.setHorizontalAlignment(SwingConstants.RIGHT);
    lblm1.setText("m�");
    lblm3.setText("m�");
    lblm5.setText("m");
    lblm4.setText("m");
    lblm2.setText("m");
    jLabel3.setText("Stock ID\'s");
    jScrollPane2.setAutoscrolls(true);
    jLabel4.setToolTipText("");
    jLabel4.setText("Location");
    txtShelf.setToolTipText("Location of StockItem");
    txtShelf.setText("");
    txtShelf.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtShelf_focusLost(e);
      }
    });
    lstIDs.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        lstIDs_mouseClicked(e);
      }
    });
    lstIDs.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        lstIDs_keyReleased(e);
      }
    });
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jScrollPane1, new GridBagConstraints(0, 2, 6, 9, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 5, 5, 5), 600, 0));
    jPanel1.add(btnClose, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(10, 5, 5, 0), 0, 0));
    jPanel1.add(rbnSingle, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(10, 20, 5, 5), 0,
                                                  0));
    jPanel1.add(rbnGroup, new GridBagConstraints(5, 0, 2, 1, 1.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(btnPrint, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(10, 20, 5, 5), 0, 0));
    jPanel1.add(cbxMaterial, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(6, 1, 2, 2, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(lblFoils, new GridBagConstraints(6, 7, 1, 2, 0.0, 0.0
                                                 , GridBagConstraints.NORTHWEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 0, 5), 0, 0));
    jPanel1.add(lblBoards, new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5, 5, 5, 0), 0, 0));
    jPanel1.add(lblFrames, new GridBagConstraints(6, 4, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5, 5, 5, 0), 0, 0));
    jPanel1.add(lblBacks, new GridBagConstraints(6, 5, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 0), 0, 0));
    jPanel1.add(lblSlips, new GridBagConstraints(6, 6, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtBoards, new GridBagConstraints(7, 3, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5, 5, 5, 5), 0, 0));
    jScrollPane1.getViewport().add(tblStock, null);
    buttonGroup1.add(rbnSingle);
    buttonGroup1.add(rbnGroup);
    jPanel1.add(txtFrames, new GridBagConstraints(7, 4, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtBacks, new GridBagConstraints(7, 5, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtSlips, new GridBagConstraints(7, 6, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtFoils, new GridBagConstraints(7, 7, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.NORTH,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 5, 0, 5), 0, 0));
    jPanel1.add(lblm1, new GridBagConstraints(8, 3, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(lblm3, new GridBagConstraints(8, 5, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(lblm5, new GridBagConstraints(8, 7, 1, 3, 0.0, 0.0
                                              , GridBagConstraints.NORTH,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(lblm4, new GridBagConstraints(8, 6, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(lblm2, new GridBagConstraints(8, 4, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane2, new GridBagConstraints(6, 8, 3, 1, 0.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
        new Insets(5, 5, 5, 5), 80, 120));
    jScrollPane2.getViewport().add(lstIDs, null);
    jPanel1.add(jLabel3, new GridBagConstraints(6, 7, 2, 1, 0.0, 0.0
                                                , GridBagConstraints.NORTHWEST,
                                                GridBagConstraints.NONE,
                                                new Insets(30, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel4, new GridBagConstraints(6, 9, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(txtShelf, new GridBagConstraints(6, 10, 2, 1, 1.0, 0.0
                                                 , GridBagConstraints.NORTHWEST,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(5, 5, 5, 5), 0, 0));
  }

  void btnClose_actionPerformed(ActionEvent e) {
    hide();
  }

  void btnPrint_actionPerformed(ActionEvent e) {
    if (rbnSingle.isSelected()) {
      groupItems = false;
      int type = MaterialDets.getItemTypeInt( (String) cbxMaterial.
                                             getSelectedItem());
      CurrentStockPrinter cSP = new CurrentStockPrinter(artofDB.getCurrentStock(
          type, groupItems), this);
    }
    else {
      groupItems = true;
      int type = MaterialDets.getItemTypeInt( (String) cbxMaterial.
                                             getSelectedItem());
      CurrentStockPrinter cSP = new CurrentStockPrinter(artofDB.getCurrentStock(
          type, groupItems), this);
    }

  }

  void rbnSingle_actionPerformed(ActionEvent e) {
    if (rbnSingle.isSelected()) {
      groupItems = false;
      int type = MaterialDets.getItemTypeInt( (String) cbxMaterial.
                                             getSelectedItem());
      ( (CurrentStockDataModel) tblStock.getModel()).refreshItems(artofDB.
          getCurrentStock(type, groupItems));
      txtShelf.setText("");
    }
  }

  void rbnGroup_actionPerformed(ActionEvent e) {
    if (rbnGroup.isSelected()) {
      groupItems = true;
      int type = MaterialDets.getItemTypeInt( (String) cbxMaterial.
                                             getSelectedItem());
      ( (CurrentStockDataModel) tblStock.getModel()).refreshItems(artofDB.
          getCurrentStock(type, groupItems));
      txtShelf.setText("");
    }
  }

  void cbxMaterial_itemStateChanged(ItemEvent e) {
    int type = MaterialDets.getItemTypeInt( (String) cbxMaterial.
                                           getSelectedItem());
    ( (CurrentStockDataModel) tblStock.getModel()).refreshItems(artofDB.
        getCurrentStock(type, groupItems));

    float[] totals = ( (CurrentStockDataModel) tblStock.getModel()).getTotals();
    if (type == MaterialDets.MAT_ALL) {
      lblm1.setEnabled(true);
      lblm2.setEnabled(true);
      lblm3.setEnabled(true);
      lblm4.setEnabled(true);
      lblm5.setEnabled(true);
      lblFrames.setEnabled(true);
      lblBacks.setEnabled(true);
      lblSlips.setEnabled(true);
      lblFoils.setEnabled(true);
      txtBoards.setText(Utils.getCurrencyFormat(totals[0]));
      txtBacks.setText(Utils.getCurrencyFormat(totals[1]));
      txtFrames.setText(Utils.getCurrencyFormat(totals[2]));
      txtSlips.setText(Utils.getCurrencyFormat(totals[3]));
      txtFoils.setText(Utils.getCurrencyFormat(totals[4]));

    }
    /*else if (type == MaterialDets.MAT_BOARD) {
          lblm1.setEnabled(true);
          lblm2.setEnabled(false);
          lblm3.setEnabled(false);
          lblm4.setEnabled(false);
          lblm5.setEnabled(false);
          lblBoards.setEnabled(true);
          lblFrames.setEnabled(false);
          lblBacks.setEnabled(false);
          lblSlips.setEnabled(false);
          lblFoils.setEnabled(false);
          txtBoards.setText(Utils.getCurrencyFormat(totals[0]));
          txtFrames.setText("");
          txtBacks.setText("");
          txtSlips.setText("");
          txtFoils.setText("");
        }*/
  }

  public String getTxtBoards() {
    return txtBoards.getText();
  }

  public String getTxtFrames() {
    return txtFrames.getText();
  }

  public String getTxtBacks() {
    return txtBacks.getText();
  }

  public String getTxtSlips() {
    return txtSlips.getText();
  }

  public String getTxtFoils() {
    return txtFoils.getText();
  }

  public boolean getRbnSinleValue() {
    return this.rbnSingle.isSelected();
  }

  void handleShelfTextField() {

  }

  void lstIDs_mouseClicked(MouseEvent e) {
    selStockId = (long) ( (Integer) lstIDs.getSelectedValue()).intValue();
    txtShelf.setText(artofDB.getShelf(selStockId));
  }

  void txtShelf_focusLost(FocusEvent e) {
    if (selStockId != -1) {
      String shelf = txtShelf.getText();
      artofDB.saveShelf(selStockId, shelf);
    }
  }

  void lstIDs_keyReleased(KeyEvent e) {
    selStockId = (long) ( (Integer) lstIDs.getSelectedValue()).intValue();
    txtShelf.setText(artofDB.getShelf(selStockId));
  }

}