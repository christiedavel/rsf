package artof.stock.stocktake;
import artof.database.ArtofDB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PrevStockDialog extends JDialog {
  private DefaultListModel listModel = new DefaultListModel();

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JButton btnCancel = new JButton();
  private JButton btnOpen = new JButton();
  private JPanel jPanel2 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JList lstStocks = new JList(listModel);

  public PrevStockDialog() {
    ArtofDB db = ArtofDB.getCurrentDB();
    Iterator it = db.getPreviousStockTakes(false).iterator();
    while (it.hasNext()) {
      String item = (String)it.next();
      listModel.addElement(item);
    }

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    Dimension screenSize = getToolkit().getScreenSize();
    setSize(300, 300);
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Previous Stock Takings");
    this.getContentPane().setLayout(borderLayout1);
    btnCancel.setText("Close");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    btnOpen.setMaximumSize(new Dimension(73, 27));
    btnOpen.setMinimumSize(new Dimension(73, 27));
    btnOpen.setText("Open");
    btnOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOpen_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout1);
    jLabel1.setText("Select to open:");
    lstStocks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnCancel, null);
    jPanel1.add(btnOpen, null);
    this.getContentPane().add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jLabel1,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    jPanel2.add(jScrollPane1,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jScrollPane1.getViewport().add(lstStocks, null);
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    hide();
  }

  void btnOpen_actionPerformed(ActionEvent e) {
    int stockTakeID = -1;
    try {
      String id = ((String)lstStocks.getSelectedValue());
      id = id.substring(0, id.indexOf(". "));
      stockTakeID = Integer.parseInt(id);
    } catch (Exception x) {
      // doen fokkol: default na -1
    }
    PrevStockTakeDialog d = new PrevStockTakeDialog(stockTakeID);
  }
}