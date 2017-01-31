package artof.invoice;
import javax.swing.*;
import java.awt.*;

public class InvoiceDialog extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel ButtonPanel = new JPanel();
  JPanel TablePanel = new JPanel();
  JPanel DetailsPanel = new JPanel();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JRadioButton jRadioButtonCurDesign = new JRadioButton();
  JRadioButton jRadioButtonMultDesign = new JRadioButton();
  JLabel jLabel1 = new JLabel();
  JComboBox jComboBoxClient = new JComboBox();
  JRadioButton jRadioButton3 = new JRadioButton();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JLabel jLabel2 = new JLabel();
  JLabel lblInvoice = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel lblDate = new JLabel();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  JButton jButton1 = new JButton();
  JButton jButtonCancel = new JButton();
  public InvoiceDialog() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    this.setVisible(true);
  }

  public static void main(String[] args) {
    InvoiceDialog invoiceDialog = new InvoiceDialog();
  }

  private void jbInit() throws Exception {
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Invoice Control");
    this.setSize(new Dimension(600, 500));
    this.getContentPane().setLayout(borderLayout1);

    this.setLocation(100,100);

    DetailsPanel.setLayout(gridBagLayout3);
    jPanel1.setLayout(gridBagLayout1);
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setMinimumSize(new Dimension(10, 10));
    jPanel1.setPreferredSize(new Dimension(110, 56));
    jPanel3.setLayout(gridBagLayout2);
    DetailsPanel.setBorder(BorderFactory.createRaisedBevelBorder());
    jPanel2.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.setMinimumSize(new Dimension(120, 43));
    jPanel2.setPreferredSize(new Dimension(120, 43));
    jPanel2.setLayout(gridBagLayout4);
    jRadioButtonCurDesign.setText("Current Design");
    jRadioButtonMultDesign.setText("Multiple Designs");
    jLabel1.setText("Client");
    jRadioButton3.setText("Show only completed designs");
    jLabel2.setText("Invoice Number:");
    lblInvoice.setText("xxxx");
    jLabel3.setText("Date:");
    lblDate.setText("xx/xx/xx");
    jPanel3.setBorder(BorderFactory.createEtchedBorder());
    jComboBoxClient.setPreferredSize(new Dimension(50, 24));
    TablePanel.setLayout(gridBagLayout5);
    ButtonPanel.setLayout(gridBagLayout6);
    jButton1.setText("jButton1");
    jButtonCancel.setText("Cancel");
    this.getContentPane().add(ButtonPanel, BorderLayout.SOUTH);
    ButtonPanel.add(jButton1,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    ButtonPanel.add(jButtonCancel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(TablePanel, BorderLayout.CENTER);
    this.getContentPane().add(DetailsPanel, BorderLayout.NORTH);
    DetailsPanel.add(jPanel1,    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel1.add(jRadioButtonCurDesign,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    jPanel1.add(jRadioButtonMultDesign,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    DetailsPanel.add(jPanel3,        new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel3.add(jLabel1,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 3, 0, 0), 0, 0));
    jPanel3.add(jComboBoxClient,         new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 100, 0));
    jPanel3.add(jRadioButton3,     new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    DetailsPanel.add(jPanel2,    new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(jLabel2,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(3, 3, 0, 0), 0, 0));
    jPanel2.add(lblInvoice,     new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(3, 3, 0, 3), 0, 0));
    jPanel2.add(jLabel3,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    jPanel2.add(lblDate,    new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(3, -10, 3, 3), 0, 0));
  }
}
