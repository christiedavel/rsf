package artof.dialogs;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class CostPanel extends JPanel {
  JRadioButton rbtn1;
  JRadioButton rbtn2;
  JRadioButton rbtn3;
  JRadioButton rbtn4;
  JRadioButton rbtn5;
  ButtonGroup bg;

  JComboBox cbx1;
  JComboBox cbx2;
  JComboBox cbx3;
  JComboBox cbx4;
  JComboBox cbx5;

  CustomTextField txt1;
  CustomTextField txt2;
  CustomTextField txt3;
  CustomTextField txt4;
  CustomTextField txt5;

  public CostPanel() {
    super(new GridLayout(6, 3));

    EmptyBorder border = new EmptyBorder(0, 0, 0, 35);

    cbx1 = new JComboBox(Utils.getIntegerArray(100));
    cbx2 = new JComboBox(Utils.getIntegerArray(100));
    cbx3 = new JComboBox(Utils.getIntegerArray(100));
    cbx4 = new JComboBox(Utils.getIntegerArray(100));
    cbx5 = new JComboBox(Utils.getIntegerArray(100));
    cbx1.setBorder(border);
    cbx2.setBorder(border);
    cbx3.setBorder(border);
    cbx4.setBorder(border);
    cbx5.setBorder(border);
    cbx1.setFont(cbx1.getFont().deriveFont(Font.PLAIN));
    cbx2.setFont(cbx2.getFont().deriveFont(Font.PLAIN));
    cbx3.setFont(cbx3.getFont().deriveFont(Font.PLAIN));
    cbx4.setFont(cbx4.getFont().deriveFont(Font.PLAIN));
    cbx5.setFont(cbx5.getFont().deriveFont(Font.PLAIN));

    rbtn1 = new JRadioButton();
    rbtn2 = new JRadioButton();
    rbtn3 = new JRadioButton();
    rbtn4 = new JRadioButton();
    rbtn5 = new JRadioButton();
    bg = new ButtonGroup();
    bg.add(rbtn1);
    bg.add(rbtn2);
    bg.add(rbtn3);
    bg.add(rbtn4);
    bg.add(rbtn5);

    txt1 = new CustomTextField();
    txt2 = new CustomTextField();
    txt3 = new CustomTextField();
    txt4 = new CustomTextField();
    txt5 = new CustomTextField();
    txt1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    txt2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    txt3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    txt4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    txt5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

    add(new JLabel("Order"));
    add(new JLabel("Quantity"));
    add(new JLabel("Price/Unit"));
    add(rbtn1);
    add(cbx1);
    add(txt1);
    add(rbtn2);
    add(cbx2);
    add(txt2);
    add(rbtn3);
    add(cbx3);
    add(txt3);
    add(rbtn4);
    add(cbx4);
    add(txt4);
    add(rbtn5);
    add(cbx5);
    add(txt5);
  }

  public void setEditable(boolean value) {
    rbtn1.setEnabled(value);
    rbtn2.setEnabled(value);
    rbtn3.setEnabled(value);
    rbtn4.setEnabled(value);
    rbtn5.setEnabled(value);

    cbx1.setEditable(value);
    cbx2.setEditable(value);
    cbx3.setEditable(value);
    cbx4.setEditable(value);
    cbx5.setEditable(value);

    txt1.setEditable(value);
    txt2.setEditable(value);
    txt3.setEditable(value);
    txt4.setEditable(value);
    txt5.setEditable(value);
  }

  public void setPriceBoxes(int selected, ArrayList arr) {
    if (selected < 0 || selected > 4 || arr == null || arr.size() != 5) {
      cbx1.setSelectedItem(new Integer(0));
    txt1.setText(Utils.getCurrencyFormat(0));
    cbx2.setSelectedItem(new Integer(0));
    txt2.setText(Utils.getCurrencyFormat(0));
    cbx3.setSelectedItem(new Integer(0));
    txt3.setText(Utils.getCurrencyFormat(0));
    cbx4.setSelectedItem(new Integer(0));
    txt4.setText(Utils.getCurrencyFormat(0));
    cbx5.setSelectedItem(new Integer(0));
    txt5.setText(Utils.getCurrencyFormat(0));

    rbtn1.setSelected(true);
      return;
    }

    int[] getalle = new int[5];
    float[] pryse = new float[5];
    for (int i = 0; i < 5; i++) {
      Object[] obj = (Object[])arr.get(i);
      getalle[i] = ((Integer)obj[0]).intValue();
      pryse[i] = ((Float)obj[1]).floatValue();
    }
    cbx1.setSelectedItem(new Integer(getalle[0]));
    txt1.setText(Utils.getCurrencyFormat(pryse[0]));
    cbx2.setSelectedItem(new Integer(getalle[1]));
    txt2.setText(Utils.getCurrencyFormat(pryse[1]));
    cbx3.setSelectedItem(new Integer(getalle[2]));
    txt3.setText(Utils.getCurrencyFormat(pryse[2]));
    cbx4.setSelectedItem(new Integer(getalle[3]));
    txt4.setText(Utils.getCurrencyFormat(pryse[3]));
    cbx5.setSelectedItem(new Integer(getalle[4]));
    txt5.setText(Utils.getCurrencyFormat(pryse[4]));

    if (selected == 1)
      rbtn1.setSelected(true);
    else if (selected == 2)
      rbtn2.setSelected(true);
    else if (selected == 3)
      rbtn3.setSelected(true);
    else if (selected == 4)
      rbtn4.setSelected(true);
    else if (selected == 5)
      rbtn5.setSelected(true);
  }

  public int getPriceSelected() {
    if (rbtn2.isSelected())
      return 2;
    else if (rbtn3.isSelected())
      return 3;
    else if (rbtn3.isSelected())
      return 4;
    else if (rbtn3.isSelected())
      return 5;
    else
      return 1;
  }

  public ArrayList getPriceArray() {
    ArrayList arr = new ArrayList();

    Object[] obj = new Object[2];
    try {
      obj[0] = (Integer)cbx1.getSelectedItem();
    } catch (ClassCastException e) {
      obj[0] = new Integer((String)cbx1.getSelectedItem());
    }
    try {
      obj[1] = Utils.getFloatFromCurrency(txt1.getText());
    } catch (java.text.ParseException e) {
      obj[1] = new Float(0);
      String mes = "Invalid price was specified.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    arr.add(obj);

    obj = new Object[2];
    try {
      obj[0] = (Integer)cbx2.getSelectedItem();
    } catch (ClassCastException e) {
      obj[0] = new Integer((String)cbx2.getSelectedItem());
    }
    try {
      obj[1] = Utils.getFloatFromCurrency(txt2.getText());
    } catch (java.text.ParseException e) {
      obj[1] = new Float(0);
      String mes = "Invalid price was specified.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    arr.add(obj);

    obj = new Object[2];
    try {
      obj[0] = (Integer)cbx3.getSelectedItem();
    } catch (ClassCastException e) {
      obj[0] = new Integer((String)cbx3.getSelectedItem());
    }
    try {
      obj[1] = Utils.getFloatFromCurrency(txt3.getText());
    } catch (java.text.ParseException e) {
      obj[1] = new Float(0);
      String mes = "Invalid price was specified.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    arr.add(obj);

    obj = new Object[2];
    try {
      obj[0] = (Integer)cbx4.getSelectedItem();
    } catch (ClassCastException e) {
      obj[0] = new Integer((String)cbx4.getSelectedItem());
    }
    try {
      obj[1] = Utils.getFloatFromCurrency(txt4.getText());
    } catch (java.text.ParseException e) {
      obj[1] = new Float(0);
      String mes = "Invalid price was specified.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    arr.add(obj);

    obj = new Object[2];
    try {
      obj[0] = (Integer)cbx5.getSelectedItem();
    } catch (ClassCastException e) {
      obj[0] = new Integer((String)cbx5.getSelectedItem());
    }
    try {
      obj[1] = Utils.getFloatFromCurrency(txt5.getText());
    } catch (java.text.ParseException e) {
      obj[1] = new Float(0);
      String mes = "Invalid price was specified.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    arr.add(obj);

    return arr;
  }
}















