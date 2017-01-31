package artof.materials;
import artof.utils.*;
import artof.database.*;
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

public class CostPanelFrames extends JPanel implements CostPanelInterface {
  private BusPrefDets busPrefs;
  private MaterialValues curValues;
  private int type;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel lblExqFact = new JLabel();
  private JLabel lblExqPrice = new JLabel();
  private CustomTextField txtCost = new CustomTextField();
  private CustomTextField txtCompFactor = new CustomTextField();
  private CustomTextField txtPrice = new CustomTextField();
  private CustomTextField txtExqFactor = new CustomTextField();
  private CustomTextField txtExqPrice = new CustomTextField();

  public CostPanelFrames(int type) {
    busPrefs = ArtofDB.getCurrentDB().getBusPreferences();
    this.type = type;

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setData(MaterialValues values, String supplier) {
    if (values == null) return;

    try {
      values.setCost((new Float(txtCost.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes = "Invalid Cost was specified for " + supplier;
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    try {
      values.setCompFactor((new Float(txtCompFactor.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes = "Invalid Competition Factor was specified for " + supplier;
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    if (type == MaterialDets.MAT_FRAME) {
      try {
        values.setExqFactor((new Float(txtExqFactor.getText())).floatValue());
      } catch (NumberFormatException e) {
        String mes = "Invalid Exquisite Factor was specified for " + supplier;
        JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public void updateData(MaterialValues values) {
    curValues = values;
    if (values == null) {

      txtCost.setText("");
      txtCost.setEditable(false);

      txtCompFactor.setText("");
      txtCompFactor.setEditable(false);

      txtPrice.setText("");

      txtExqFactor.setText("");
      txtExqFactor.setEditable(false);

      txtExqPrice.setText("");

    } else {

      txtCost.setText(Utils.getCurrencyFormat(values.getCost()));
      txtCost.setEditable(true);

      txtCompFactor.setText(Utils.getCurrencyFormat(values.getCompFactor()));
      txtCompFactor.setEditable(true);

      float markup = busPrefs.getTotalFrameMarkup();
      float price = values.getCost() * values.getCompFactor() * markup;
      txtPrice.setText(Utils.getCurrencyFormat(price));

      if (type == MaterialDets.MAT_FRAME) {
        txtExqFactor.setText(Utils.getCurrencyFormat(values.getExqFactor()));
        txtExqFactor.setEditable(true);

        txtExqPrice.setText(Utils.getCurrencyFormat(price * values.getExqFactor()));
      }
    }
  }

  private void jbInit() throws Exception {
    jLabel1.setText("Cost / m");
    this.setLayout(gridBagLayout1);
    jLabel2.setText("Competition Factor");
    jLabel3.setText("Price / m");
    lblExqFact.setText("Exquisite Factor");
    lblExqPrice.setText("Exquisite Price");
    txtExqFactor.setToolTipText("");
    txtExqFactor.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtExqFactor_focusLost(e);
      }
    });
    txtExqPrice.setEditable(false);
    txtPrice.setEditable(false);
    txtCost.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtCost_focusLost(e);
      }
    });
    txtCompFactor.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtCompFactor_focusLost(e);
      }
    });
    this.setMinimumSize(new Dimension(4, 4));
    this.setPreferredSize(new Dimension(4, 4));
    this.add(jLabel1,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(jLabel2,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(jLabel3,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

    if (type == MaterialDets.MAT_FRAME) {
      this.add(lblExqFact,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      this.add(lblExqPrice,   new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }

    this.add(txtCost,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtCompFactor,   new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtPrice,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    if (type == MaterialDets.MAT_FRAME) {
      this.add(txtExqFactor,   new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
      this.add(txtExqPrice,   new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    }
  }

  void txtCost_focusLost(FocusEvent e) {
    if (!txtCost.isEditable()) return;
    try {
      curValues.setCost((new Float(txtCost.getText())).floatValue());
      txtCost.setText(Utils.getCurrencyFormat(curValues.getCost()));

      float markup = busPrefs.getTotalFrameMarkup();
      float price = curValues.getCost() * curValues.getCompFactor() * markup;
      txtPrice.setText(Utils.getCurrencyFormat(price));

      if (type == MaterialDets.MAT_FRAME)
        txtExqPrice.setText(Utils.getCurrencyFormat(price * curValues.getExqFactor()));

    } catch (NumberFormatException x) {
      String mes = "Invalid Cost was specified";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  void txtCompFactor_focusLost(FocusEvent e) {
    if (!txtCompFactor.isEditable()) return;
    try {
      curValues.setCompFactor((new Float(txtCompFactor.getText())).floatValue());
      txtCompFactor.setText(Utils.getCurrencyFormat(curValues.getCompFactor()));

      float markup = busPrefs.getTotalFrameMarkup();
      float price = curValues.getCost() * curValues.getCompFactor() * markup;
      txtPrice.setText(Utils.getCurrencyFormat(price));

      if (type == MaterialDets.MAT_FRAME)
        txtExqPrice.setText(Utils.getCurrencyFormat(price * curValues.getExqFactor()));

    } catch (NumberFormatException x) {
      String mes = "Invalid Cost was specified";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  void txtExqFactor_focusLost(FocusEvent e) {
    if (type != MaterialDets.MAT_FRAME || !txtExqFactor.isEditable()) return;
    try {
      curValues.setExqFactor((new Float(txtExqFactor.getText())).floatValue());
      txtExqFactor.setText(Utils.getCurrencyFormat(curValues.getExqFactor()));

      float markup = busPrefs.getTotalFrameMarkup();
      float price = curValues.getCost() * curValues.getCompFactor() * markup;
      txtExqPrice.setText(Utils.getCurrencyFormat(price * curValues.getExqFactor()));

    } catch (NumberFormatException x) {
      String mes = "Invalid Cost was specified";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

}