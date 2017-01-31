package artof.utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class SettingDialog extends JDialog {
  private DesignSettingsPanel designPanel = new DesignSettingsPanel();
  private AdvancedPanel advancedPanel = new AdvancedPanel();
  private StockPanel stockPanel = new StockPanel();
  private ImportPanel importPanel = new ImportPanel();

  private JPanel jPanel1 = new JPanel();
  private JButton btnCancel = new JButton();
  private JButton btnOK = new JButton();
  private JTabbedPane tabPane = new JTabbedPane();

  public SettingDialog() {

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    tabPane.addTab("Design Settings", designPanel);
    tabPane.addTab("Stock Optimization", stockPanel);
    tabPane.addTab("Import", importPanel);
    tabPane.addTab("Advanced", advancedPanel);

    setSize(500, 400);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    btnOK.setMaximumSize(new Dimension(73, 27));
    btnOK.setMinimumSize(new Dimension(73, 27));
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOK_actionPerformed(e);
      }
    });
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Settings");
    this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(btnCancel, null);
    jPanel1.add(btnOK, null);
    this.getContentPane().add(tabPane,  BorderLayout.CENTER);
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    hide();
  }

  void btnOK_actionPerformed(ActionEvent e) {
    designPanel.doOK();
    advancedPanel.doOK();
    stockPanel.doOK();
    importPanel.doOK();
    UserSettings.saveUserSettings();
    hide();
  }
}