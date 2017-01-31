package artof.utils;

import java.awt.*;
import javax.swing.*;
import java.util.*;

import com.toedter.calendar.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DateDialog extends JDialog {
  private int selectedDate;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JCalendar calender = new JCalendar();
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  Border border1;

  public DateDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    Dimension screenSize = getToolkit().getScreenSize();
    setSize(300, 250);
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public DateDialog() {
    this(null, "Choose date", true);
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    panel1.setLayout(borderLayout1);
    btnOK.setPreferredSize(new Dimension(75, 25));
    btnOK.setText("OK");
    btnOK.addActionListener(new DateDialog_btnOK_actionAdapter(this));
    btnCancel.setPreferredSize(new Dimension(75, 25));
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new DateDialog_btnCancel_actionAdapter(this));
    jPanel1.setBorder(border1);
    getContentPane().add(panel1);
    panel1.add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnOK, null);
    jPanel1.add(btnCancel, null);
    panel1.add(calender, BorderLayout.CENTER);
  }

  public int getSelectedDate() {
    return selectedDate;
  }

  void btnOK_actionPerformed(ActionEvent e) {
    Calendar cal = calender.getCalendar();
    int d = cal.get(Calendar.DAY_OF_MONTH);
    int m = cal.get(Calendar.MONTH) + 1;
    int j = cal.get(Calendar.YEAR);
    selectedDate = 10000*j + m*100 + d;
    hide();
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    selectedDate = -1;
    hide();
  }
}

class DateDialog_btnOK_actionAdapter implements java.awt.event.ActionListener {
  DateDialog adaptee;

  DateDialog_btnOK_actionAdapter(DateDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnOK_actionPerformed(e);
  }
}

class DateDialog_btnCancel_actionAdapter implements java.awt.event.ActionListener {
  DateDialog adaptee;

  DateDialog_btnCancel_actionAdapter(DateDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnCancel_actionPerformed(e);
  }
}