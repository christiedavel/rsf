package artof.materials;
import artof.utils.*;
import artof.database.*;
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

public class CostPanelBoards extends JPanel implements CostPanelInterface {
  private BusPrefDets busPrefs;
  private MaterialValues curValues;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private Border border1;
  private JLabel jLabel1 = new JLabel();
  private CustomTextField txtCost = new CustomTextField();
  private JLabel jLabel2 = new JLabel();
  private CustomTextField txtCompFactor = new CustomTextField();
  private JLabel jLabel3 = new JLabel();
  private CustomTextField txtCostM2 = new CustomTextField();
  private JLabel jLabel4 = new JLabel();
  private CustomTextField txtPriceM2 = new CustomTextField();

  public CostPanelBoards() {
    busPrefs = ArtofDB.getCurrentDB().getBusPreferences();

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
      values.setCost((new Float(txtCostM2.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes = "Invalid Cost was specified";
      if (supplier != null) mes += " for " + supplier;
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    try {
      values.setCompFactor((new Float(txtCompFactor.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes = "Invalid Competition Factor was specified for " + supplier;
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void updateData(MaterialValues values) {
    curValues = values;
    if (values == null) {

      txtCost.setText("");
      txtCost.setEditable(false);

      txtCostM2.setText("");

      txtCompFactor.setText("");
      txtCompFactor.setEditable(false);

      txtPriceM2.setText("");

    } else {

      float costM2 = values.getCost();
      float area = values.getLength() * values.getWidth() / 1000000;

      txtCost.setText(Utils.getCurrencyFormat(costM2 * area));
      if (area > 0.0001)
        txtCost.setEditable(true);
      else
        txtCost.setEditable(false);

      txtCostM2.setText(Utils.getCurrencyFormat(costM2));

      txtCompFactor.setText(Utils.getCurrencyFormat(values.getCompFactor()));
      txtCompFactor.setEditable(true);

      float markup = busPrefs.getTotalBoardMarkup();
      float price = costM2 * values.getCompFactor() * markup;
      txtPriceM2.setText(Utils.getCurrencyFormat(price));
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEmptyBorder();
    jLabel1.setText("Cost");
    this.setBorder(border1);
    this.setMinimumSize(new Dimension(4, 4));
    this.setPreferredSize(new Dimension(4, 4));
    this.setLayout(gridBagLayout1);
    jLabel2.setText("Competition Factor");
    jLabel3.setText("Cost / m�");
    jLabel4.setText("Price / m�");
    txtPriceM2.setEditable(false);
    txtPriceM2.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtPriceM2_focusLost(e);
      }
    });
    txtCostM2.setEditable(false);
    txtCost.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtCost_focusLost(e);
      }
    });
    txtCompFactor.addFocusListener(new CostPanelBoards_txtCompFactor_focusAdapter(this));
    this.add(jLabel1,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtCost,      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtCostM2,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(jLabel3,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(jLabel4,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(jLabel2,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtPriceM2, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.add(txtCompFactor, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }

  void txtCost_focusLost(FocusEvent e) {
    if (!txtCost.isEditable()) return;
    try {
      float cost = (new Float(txtCost.getText())).floatValue();
      float area = curValues.getLength() * curValues.getWidth() / 1000000;
      float costM2 = cost / area;
      curValues.setCost((float)costM2);

      txtCost.setText(Utils.getCurrencyFormat(cost));
      txtCostM2.setText(Utils.getCurrencyFormat(costM2));

      float markup = busPrefs.getTotalBoardMarkup();
      float price = costM2 * curValues.getCompFactor() * markup;
      txtPriceM2.setText(Utils.getCurrencyFormat(price));

    } catch (NumberFormatException x) {
      String mes = "Invalid Cost was specified";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    //setData(curValues, null);
  }

  void txtPriceM2_focusLost(FocusEvent e) {
    if (!txtPriceM2.isEditable()) return;
    try {
      float compFactor = (new Float(txtCompFactor.getText())).floatValue();
      curValues.setCompFactor(compFactor);
      float markup = busPrefs.getTotalBoardMarkup();
      float price = curValues.getCost() * compFactor * markup;
      txtPriceM2.setText(Utils.getCurrencyFormat(price));

    } catch (NumberFormatException x) {
      String mes = "Invalid Competition Factor was specified";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  void txtCompFactor_focusLost(FocusEvent e) {
    if (!txtCompFactor.isEditable()) return;
    try {
      curValues.setCompFactor(Float.parseFloat(txtCompFactor.getText()));
      txtCompFactor.setText(Utils.getCurrencyFormat(curValues.getCompFactor()));
      float costM2 = curValues.getCost();

      float markup = busPrefs.getTotalBoardMarkup();
      float price = costM2 * curValues.getCompFactor() * markup;
      txtPriceM2.setText(Utils.getCurrencyFormat(price));

    } catch (NumberFormatException x) {
      String mes = "Invalid Cost was specified";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

  }
}

class CostPanelBoards_txtCompFactor_focusAdapter extends java.awt.event.FocusAdapter {
  CostPanelBoards adaptee;

  CostPanelBoards_txtCompFactor_focusAdapter(CostPanelBoards adaptee) {
    this.adaptee = adaptee;
  }
  public void focusLost(FocusEvent e) {
    adaptee.txtCompFactor_focusLost(e);
  }
}