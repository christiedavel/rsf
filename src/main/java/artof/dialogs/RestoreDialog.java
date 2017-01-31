package artof.dialogs;
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

public class RestoreDialog extends JDialog {
  private DefaultListModel itemList = new DefaultListModel();
  private String filename;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private JButton btnRestore = new JButton();
  private JPanel jPanel2 = new JPanel();
  private Border border1;
  private JButton btnCancel = new JButton();
  private JLabel jLabel1 = new JLabel();
  private JList lstFiles = new JList(itemList);
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private Border border2;
  JScrollPane jScrollPane1 = new JScrollPane();

  public RestoreDialog(String[] files) {
    for (int i = 0; i < files.length; i++)
      itemList.addElement(files[i]);

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    setSize(300, 300);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public String getFileSelected() {
    return filename;
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(124, 124, 124),new Color(178, 178, 178));
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Restore");
    this.getContentPane().setLayout(borderLayout1);
    btnRestore.setText("Restore");
    btnRestore.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnRestore_actionPerformed(e);
      }
    });
    jPanel1.setBorder(border1);
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    jLabel1.setText("Select file to restore");
    jPanel2.setLayout(gridBagLayout1);
    lstFiles.setBorder(border2);
    lstFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnRestore, null);
    this.getContentPane().add(jPanel2,  BorderLayout.CENTER);
    jPanel1.add(btnCancel, null);
    jPanel2.add(jLabel1,      new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    jPanel2.add(jScrollPane1,    new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jScrollPane1.getViewport().add(lstFiles, null);
  }

  void btnRestore_actionPerformed(ActionEvent e) {
    filename = (String)lstFiles.getSelectedValue();
    hide();
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    filename = null;
    hide();
  }
}