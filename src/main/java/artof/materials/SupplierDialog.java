package artof.materials;
import artof.database.*;
import artof.dialogs.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SupplierDialog extends JDialog implements DataFunctions {
  private ArtofDB db_conn;
  private ArrayList supplierList;
  private ButCreator butCreator;
  private JDialog thisDialog;

  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnlSouth = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel pnlNorth = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JTextField txtName = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextField txtPerson = new JTextField();
  JLabel jLabel3 = new JLabel();
  JTextField txtTel = new JTextField();
  JLabel jLabel4 = new JLabel();
  JTextField txtFax = new JTextField();
  JLabel jLabel5 = new JLabel();
  JTextField txtCel = new JTextField();
  JLabel jLabel6 = new JLabel();
  JTextField txtEmail = new JTextField();
  JLabel jLabel7 = new JLabel();
  JTextField txtAd1 = new JTextField();
  JTextField txtAd2 = new JTextField();
  JTextField txtAd3 = new JTextField();
  JLabel jLabel8 = new JLabel();
  JTextField txtCode = new JTextField();

  public SupplierDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    thisDialog = this;

    db_conn = ArtofDB.getCurrentDB();
    supplierList = db_conn.getSuppliers();

    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    // Al die buttons
     butCreator = new ButCreator(this);
     getContentPane().add(butCreator.getBrowsePanel(), BorderLayout.SOUTH);
     getContentPane().add(butCreator.getDataPanel(), BorderLayout.NORTH);

     addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
         if (JOptionPane.showConfirmDialog(thisDialog, "Do you want to save changes", "Save",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
           db_conn.saveSuppliers(supplierList);
       }
     });

    setSize(570, 350);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public SupplierDialog() {
    this(null, "Suppliers", true);
  }

  public boolean setData(Object obj) {
    if (obj != null) {
      SupplierDets dets = (SupplierDets)obj;
      dets.setName(txtName.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_PERSON, txtPerson.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_TEL, txtTel.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_FAX, txtFax.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_CEL, txtCel.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_EMAIL, txtEmail.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_AD1, txtAd1.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_AD2, txtAd2.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_AD3, txtAd3.getText());
      dets.setDataValue(SupplierDets.SUPPLIER_CODE, txtCode.getText());
    }
    return true;
  }

  public void updateData(Object obj) {
    if (obj == null) {
      txtName.setText("");
      txtName.setEditable(false);

      txtTel.setText("");
      txtTel.setEditable(false);

      txtFax.setText("");
      txtFax.setEditable(false);

      txtCel.setText("");
      txtCel.setEditable(false);

      txtEmail.setText("");
      txtEmail.setEditable(false);

      txtAd1.setText("");
      txtAd1.setEditable(false);

      txtAd2.setText("");
      txtAd2.setEditable(false);

      txtAd3.setText("");
      txtAd3.setEditable(false);

      txtCode.setText("");
      txtCode.setEditable(false);
    }
    else {
      SupplierDets dets = (SupplierDets)obj;
      txtName.setText(dets.getName());
      txtName.setEditable(true);

      txtPerson.setText(dets.getDataValue(SupplierDets.SUPPLIER_PERSON));
      txtPerson.setEditable(true);

      txtTel.setText(dets.getDataValue(SupplierDets.SUPPLIER_TEL));
      txtTel.setEditable(true);

      txtFax.setText(dets.getDataValue(SupplierDets.SUPPLIER_FAX));
      txtFax.setEditable(true);

      txtCel.setText(dets.getDataValue(SupplierDets.SUPPLIER_CEL));
      txtCel.setEditable(true);

      txtEmail.setText(dets.getDataValue(SupplierDets.SUPPLIER_EMAIL));
      txtEmail.setEditable(true);

      txtAd1.setText(dets.getDataValue(SupplierDets.SUPPLIER_AD1));
      txtAd1.setEditable(true);

      txtAd2.setText(dets.getDataValue(SupplierDets.SUPPLIER_AD2));
      txtAd2.setEditable(true);

      txtAd3.setText(dets.getDataValue(SupplierDets.SUPPLIER_AD3));
      txtAd3.setEditable(true);

      txtCode.setText(dets.getDataValue(SupplierDets.SUPPLIER_CODE));
      txtCode.setEditable(true);
    }
  }

  public ListIterator getMainIterator() {
    return supplierList.listIterator();
  }

  public Object getNewItem() {
    return new SupplierDets();
  }

  public void saveMainList() {
    db_conn.saveSuppliers(supplierList);
  }

  public void refreshMainList(String orderSQL) {
    supplierList = db_conn.getSuppliers();
  }

  public void closeMainList() {
    hide();
  }

  public void deleteItem(Object obj) {
    db_conn.deleteSupplier(((SupplierDets)obj).getName(), this);
    //clientList.remove(obj);
  }

  public void printDieFokker() {
    JOptionPane.showMessageDialog(thisDialog, "Printing function not available.", "Error", JOptionPane.ERROR_MESSAGE);
  }

  public String[] getSortList() {
    String[] fromStrings = new String[1];
    fromStrings[0] = "Supplier";
    return fromStrings;
  }

  public Hashtable getSortDBMap() {
    Hashtable table = new Hashtable();
    table.put("Supplier", "Supplier");
    return table;
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    jPanel2.setLayout(gridBagLayout1);
    jLabel1.setText("Supplier Name");
    jLabel2.setText("Contact Person");
    jLabel3.setText("Telephone No");
    jLabel4.setText("Fax No");
    jLabel5.setText("Cel No");
    jLabel6.setText("E-mail");
    jLabel7.setText("Address");
    jLabel8.setText("Postal Code");
    txtName.setText("");
    txtPerson.setText("");
    txtTel.setText("");
    txtCel.setText("");
    txtAd1.setText("");
    txtAd2.setText("");
    txtAd3.setText("");
    txtCode.setText("");
    txtFax.setText("");
    txtEmail.setText("");
    getContentPane().add(panel1);
    //panel1.add(pnlSouth, BorderLayout.SOUTH);
    panel1.add(jPanel2,  BorderLayout.CENTER);
    //panel1.add(pnlNorth, BorderLayout.NORTH);
    jPanel2.add(jLabel1,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtName,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel2,    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(txtPerson,   new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel3,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtTel,   new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel4,   new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(txtFax,   new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel5,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtCel,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel6,   new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(txtEmail,   new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel7,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtAd1,   new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtAd2,   new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtAd3,    new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel8,       new GridBagConstraints(0, 6, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtCode,    new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }
}