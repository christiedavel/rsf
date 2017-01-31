package artof.dialogs;
import artof.database.ArtofDB;
import artof.database.MethodPrefDets;
import artof.utils.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class MethodPreferences extends JDialog {
  private JDialog thisDialog;
  private ArtofDB db_conn;
  private MethodPrefDets curPref;
  private MethodPrefDets singlePref;
  private boolean singleObject = false;

  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel pnoverlap = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JLabel lbltype = new JLabel();
  Border border1;
  TitledBorder titledBorder1;
  JRadioButton rbnFixed = new JRadioButton();
  JRadioButton rbnFullSize = new JRadioButton();
  JRadioButton rbnCalculate = new JRadioButton();
  ButtonGroup bgoverlap = new ButtonGroup();
  JLabel lblcalculateol = new JLabel();
  CustomTextField txtOverlapAdj = new CustomTextField();
  JLabel lblnoslip = new JLabel();
  CustomTextField txtFBNoSlip = new CustomTextField();
  JLabel lblslip = new JLabel();
  CustomTextField txtFBSlip = new CustomTextField();
  JLabel lblminoverlap = new JLabel();
  CustomTextField txtMinOverlap = new CustomTextField();
  JPanel pnframetoglass = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JLabel lblframetoglass = new JLabel();
  CustomTextField txtFGIF = new CustomTextField();
  JLabel lblthen = new JLabel();
  JLabel lblthen2 = new JLabel();
  CustomTextField txtFGThenHeight = new CustomTextField();
  CustomTextField txtFGThenWidth = new CustomTextField();
  JLabel lblelse1 = new JLabel();
  JLabel lblelse = new JLabel();
  CustomTextField txtFGElseHeight = new CustomTextField();
  CustomTextField txtFGElseWidth = new CustomTextField();
  JPanel pnresidue = new JPanel();
  Border border2;
  TitledBorder titledBorder2;
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JLabel lblsin1 = new JLabel();
  JLabel lbllength = new JLabel();
  CustomTextField txtResBoardLength = new CustomTextField();
  JPanel pnsundries = new JPanel();
  GridBagLayout gridBagLayout10 = new GridBagLayout();
  Border border3;
  TitledBorder titledBorder3;
  JLabel lblspecs = new JLabel();
  CustomTextField txtNoOfSpecs = new CustomTextField();
  JLabel lbldelivery1 = new JLabel();
  CustomTextField txtAddDays = new CustomTextField();
  JLabel lbldelivery2 = new JLabel();
  JComboBox cbxDates = new JComboBox();
  JButton btnCancel = new JButton();
  JPanel pnmphist = new JPanel();
  JLabel lblCreateDate = new JLabel();
  GridBagLayout gridBagLayout20 = new GridBagLayout();
  JPanel pnknoppe = new JPanel();
  JLabel lblprevioussettings = new JLabel();
  JLabel lbldatecreated = new JLabel();
  JButton btnSave = new JButton();
  Border border4;
  TitledBorder titledBorder4;
  private JCheckBox ckFullBottom = new JCheckBox();
  private Border border5;
  private JButton btnClose = new JButton();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private Border border6;
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private CustomTextField txtResBackLength = new CustomTextField();
  private CustomTextField txtResGlassLength = new CustomTextField();

  public MethodPreferences(MethodPrefDets dets) {
    thisDialog = this;
    try {
      curPref = (MethodPrefDets)dets.clone();
    } catch (NullPointerException e) {
      return;
    }
    singlePref = dets;
    singleObject = true;

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    lblCreateDate.setText("");

    updateData();
    cbxDates.setEnabled(false);

    setTitle("Method Preferences");
    setResizable(false);
    setSize(new Dimension(700, 500));
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setModal(true);
    setVisible(true);
  }

  public MethodPreferences(ArtofDB db) {
    db_conn = db;
    curPref = db_conn.getMethodPreferences("Latest");

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    ArrayList dateList = db_conn.getMethodPrefList();
    Iterator it = dateList.iterator();
    while (it.hasNext())
      cbxDates.insertItemAt((String)it.next(), 0);
    Calendar cal = new GregorianCalendar();
    int curDate = 10000*cal.get(Calendar.YEAR) + 100*(cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
    lblCreateDate.setText(Utils.getDatumStr(curDate));

    updateData();

    setTitle("Method Preferences");
    setResizable(false);
    setSize(new Dimension(650, 500));
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setModal(true);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    border4 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder4 = new TitledBorder(border4,"Methods Preferences History");
    border5 = BorderFactory.createEmptyBorder(5,5,5,5);
    border6 = BorderFactory.createEmptyBorder();
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"Frameline to Glassline Settings");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(border2,"Residue Compensation");
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder3 = new TitledBorder(border3,"Sundries");
    this.getContentPane().setLayout(gridBagLayout1);
    pnoverlap.setLayout(gridBagLayout2);
    pnoverlap.setBorder(titledBorder1);
    lbltype.setFont(new java.awt.Font("Serif", 0, 14));
    lbltype.setText("Type");
    rbnFixed.setFont(new java.awt.Font("Serif", 2, 12));
    rbnFixed.setText("Fixed Border");
    rbnFullSize.setFont(new java.awt.Font("Serif", 2, 12));
    rbnFullSize.setText("Full Size");
    rbnCalculate.setFont(new java.awt.Font("Serif", 2, 12));
    rbnCalculate.setText("Calculated Overlap");
    this.setResizable(false);
    lblcalculateol.setFont(new java.awt.Font("Serif", 2, 12));
    lblcalculateol.setText("Calculated Overlap Adjustment Factor");
    lblnoslip.setFont(new java.awt.Font("Serif", 2, 12));
    lblnoslip.setText("Fixed Border Overlap without Slip");
    lblslip.setFont(new java.awt.Font("Serif", 2, 12));
    lblslip.setText("Fixed Border Overlap with Slip");
    lblminoverlap.setFont(new java.awt.Font("Serif", 2, 12));
    lblminoverlap.setText("Minimum Overlap");
    pnframetoglass.setLayout(gridBagLayout4);
    pnframetoglass.setBorder(titledBorder1);
    lblframetoglass.setFont(new java.awt.Font("Serif", 2, 12));
    lblframetoglass.setText("IF Frameline or Glassline height or width <");
    lblthen.setFont(new java.awt.Font("Serif", 2, 12));
    lblthen.setText("THEN Glassline to Frameline height = ");
    lblthen2.setFont(new java.awt.Font("Serif", 2, 12));
    lblthen2.setText("Glassline to Frameline width = ");
    lblelse1.setFont(new java.awt.Font("Serif", 2, 12));
    lblelse1.setText("ELSE Glassline to Frameline height = ");
    lblelse.setFont(new java.awt.Font("Serif", 2, 12));
    lblelse.setText("Glassline to Frameline width = ");
    pnresidue.setBorder(titledBorder2);
    pnresidue.setMinimumSize(new Dimension(291, 170));
    pnresidue.setPreferredSize(new Dimension(291, 170));
    pnresidue.setLayout(gridBagLayout5);
    lblsin1.setFont(new java.awt.Font("Serif", 2, 12));
    lblsin1.setText("Charge for full item if ucm less than:");
    pnsundries.setLayout(gridBagLayout10);
    pnsundries.setBorder(titledBorder3);
    pnsundries.setMinimumSize(new Dimension(295, 105));
    pnsundries.setPreferredSize(new Dimension(291, 105));
    lblspecs.setFont(new java.awt.Font("Serif", 2, 12));
    lblspecs.setText("No of spesifications to be printed");
    lbldelivery1.setFont(new java.awt.Font("Serif", 2, 12));
    lbldelivery1.setText("Add");
    lbldelivery2.setFont(new java.awt.Font("Serif", 2, 12));
    lbldelivery2.setText("days for delivery date");
    btnCancel.setText("Cancel");
    pnmphist.setLayout(gridBagLayout20);
    lblCreateDate.setText("19-06-1977");
    pnknoppe.setLayout(gridBagLayout3);
    pnknoppe.setBorder(border6);
    lblprevioussettings.setText("Previous Settings");
    lbldatecreated.setText("Date Created");
    btnSave.setText("Save");
    pnmphist.setBorder(titledBorder4);

    btnSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (singleObject) {
          setData();
          curPref.setModified(false);
          singlePref = curPref;
        } else {
          saveData();
          curPref = db_conn.getMethodPreferences((String)cbxDates.getSelectedItem());
          updateData();
        }
      }
    });

    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (singleObject)
          curPref = (MethodPrefDets)singlePref.clone();
        else {
          try {
            curPref = db_conn.getMethodPreferences((String)cbxDates.getSelectedItem());
          } catch (NullPointerException x) {
            curPref = db_conn.getMethodPreferences("Latest");
          }
        }
        updateData();
      }
    });

    cbxDates.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (!singleObject) {
            setData();
            if (curPref.isModified()) {
              int res = JOptionPane.showConfirmDialog(thisDialog, "Do you want to save changes", "Save",
                  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
              if (res == JOptionPane.YES_OPTION)
                saveData();
            }
            curPref = db_conn.getMethodPreferences((String)cbxDates.getSelectedItem());
            updateData();
          }
        } catch (NullPointerException x) {
          //cbxDates.setSelectedIndex(-1);
        }
      }
    });

    ckFullBottom.setFont(new java.awt.Font("Serif", 2, 12));
    ckFullBottom.setText("Use Full Bottom");
    btnClose.setText("Close");
    btnClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnClose_actionPerformed(e);
      }
    });
    jLabel1.setText("Backs");
    jLabel2.setText("Boards");
    jLabel3.setText("Glass");
    lbllength.setText("");
    this.getContentPane().add(pnoverlap,                  new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 150, 0));
    pnoverlap.add(lblcalculateol,                        new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(pnframetoglass,                new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    bgoverlap.add(rbnCalculate);
    bgoverlap.add(rbnFixed);
    bgoverlap.add(rbnFullSize);
    pnoverlap.add(lblnoslip,            new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnoverlap.add(lblslip,           new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnoverlap.add(lblminoverlap,           new GridBagConstraints(0, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnoverlap.add(txtMinOverlap,           new GridBagConstraints(2, 7, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnoverlap.add(lbltype,          new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 15), 0, 0));
    pnoverlap.add(rbnCalculate,         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 2, 5), 0, 0));
    pnoverlap.add(rbnFixed,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    pnoverlap.add(rbnFullSize,     new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    pnoverlap.add(txtOverlapAdj,        new GridBagConstraints(2, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnoverlap.add(txtFBNoSlip,    new GridBagConstraints(2, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnoverlap.add(txtFBSlip,     new GridBagConstraints(2, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnoverlap.add(ckFullBottom,         new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnframetoglass.add(lblframetoglass,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    pnframetoglass.add(txtFGIF,     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    pnframetoglass.add(txtFGThenHeight,    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    pnframetoglass.add(txtFGThenWidth,      new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    pnframetoglass.add(txtFGElseHeight,   new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    pnframetoglass.add(txtFGElseWidth,      new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    pnresidue.add(lblsin1,            new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 2, 5), 0, 0));
    pnresidue.add(txtResBoardLength,              new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 20, 2, 20), 50, 0));
    pnresidue.add(lbllength,    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    pnresidue.add(jLabel1,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    pnresidue.add(jLabel2,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 30), 0, 0));
    pnresidue.add(jLabel3,    new GridBagConstraints(0, 4, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    pnresidue.add(txtResBackLength,    new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 20, 2, 20), 50, 0));
    pnresidue.add(txtResGlassLength,    new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(2, 20, 2, 20), 50, 0));
    this.getContentPane().add(pnsundries,                new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    pnsundries.add(lblspecs,         new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnsundries.add(txtNoOfSpecs,          new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 36, 0));
    pnsundries.add(lbldelivery1,        new GridBagConstraints(0, 2, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnsundries.add(txtAddDays,          new GridBagConstraints(1, 2, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 37, 0));
    pnsundries.add(lbldelivery2,        new GridBagConstraints(2, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(pnmphist,     new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    pnmphist.add(lblprevioussettings,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnmphist.add(lbldatecreated,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnmphist.add(lblCreateDate,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnmphist.add(pnknoppe,   new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnknoppe.add(btnSave,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnknoppe.add(btnCancel,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    pnknoppe.add(btnClose,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(pnresidue,      new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 30, 0));
    pnmphist.add(cbxDates,    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 2), 0, 0));
    pnframetoglass.add(lblthen,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    pnframetoglass.add(lblthen2,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnframetoglass.add(lblelse,  new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    pnframetoglass.add(lblelse1,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
  }

  private void saveData() {
    setData();
    //if (!curPref.isModified() && curPref.getPrefDate() != UserSettings.MAX_DATE)
      curPref.setModified(true);
    db_conn.saveMethodPreferences(curPref);

    curPref = null;
    cbxDates.removeAllItems();
    ArrayList dateList = db_conn.getMethodPrefList();
    Iterator it = dateList.iterator();
    while (it.hasNext())
      cbxDates.insertItemAt((String)it.next(), 0);

    cbxDates.setSelectedItem("Latest");
    curPref = db_conn.getMethodPreferences("Latest");
    updateData();
  }

  private void setData() {
    if (curPref != null) {
      if (rbnCalculate.isSelected())
        curPref.setMethodType(MethodPrefDets.BORDER_CALC);
      else if (rbnFixed.isSelected())
        curPref.setMethodType(MethodPrefDets.BORDER_FIXED);
      else
        curPref.setMethodType(MethodPrefDets.BORDER_FULL);

      curPref.setFullBottoms(ckFullBottom.isSelected());

      try {
        curPref.setOverlapAdjFact((new Float(txtOverlapAdj.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid overlap adjustment factor was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setFBOverlapNoSlip((new Float(txtFBNoSlip.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid overlap without slip was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setFBOverlapWithSlip((new Float(txtFBSlip.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid overlap with slip was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMinOverlap((new Float(txtMinOverlap.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid minimum overlap was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }

      try {
        curPref.setFGIfValue((new Float(txtFGIF.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid frame or glassline condition was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setFGThenHeight((new Float(txtFGThenHeight.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid frame or glassline was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setFGThenWidth((new Float(txtFGThenWidth.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid frame or glassline was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setFGElseHeight((new Float(txtFGElseHeight.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid frame or glassline was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setFGElseWidth((new Float(txtFGElseWidth.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid frame or glassline was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }

      try {
        curPref.setResBoardLength((new Float(txtResBoardLength.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid board residue length was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      /*try {
        curPref.setResBoardWidth((new Float(txtResBoardWidth.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid board residue width was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }*/

      try {
        curPref.setResBackLength((new Float(txtResBackLength.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid back residue length was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      /*try {
        curPref.setResBackWidth((new Float(txtResBackWidth.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid back residue width was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }*/

      try {
        curPref.setResGlassLength((new Float(txtResGlassLength.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid glass residue length was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      /*try {
        curPref.setResGlassWidth((new Float(txtResGlassWidth.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid glass residue width was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }*/

      /*try {
        curPref.setResPremLength((new Float(txtResPremLength.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid premount residue length was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }*/
     /* try {
        curPref.setResPremWidth((new Float(txtResPremWidth.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid premount residue width was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }*/

      try {
        curPref.setSunNoSpecs((new Integer(txtNoOfSpecs.getText())).intValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid no of specifications to be printed was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setSunDays((new Integer(txtAddDays.getText())).intValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid no of delivery days was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void updateData() {
    if (curPref == null) {
      // Is nie veronderstel om ooit te gebeur nie.
    }
    else {
      if (curPref.getMethodType() == MethodPrefDets.BORDER_CALC)
        rbnCalculate.setSelected(true);
      else if (curPref.getMethodType() == MethodPrefDets.BORDER_FIXED)
        rbnFixed.setSelected(true);
      else
        rbnFullSize.setSelected(true);

      ckFullBottom.setSelected(curPref.getFullBottomsBool());

      txtOverlapAdj.setText(Utils.getCurrencyFormat(curPref.getOverlapAdjFact()));
      txtFBNoSlip.setText(Utils.getCurrencyFormat(curPref.getFBOverlapNoSlip()));
      txtFBSlip.setText(Utils.getCurrencyFormat(curPref.getFBOverlapWithSlip()));
      txtMinOverlap.setText(Utils.getCurrencyFormat(curPref.getMinOverlap()));

      txtFGIF.setText(Utils.getCurrencyFormat(curPref.getFGIfValue()));
      txtFGThenHeight.setText(Utils.getCurrencyFormat(curPref.getFGThenHeight()));
      txtFGThenWidth.setText(Utils.getCurrencyFormat(curPref.getFGThenWidth()));
      txtFGElseHeight.setText(Utils.getCurrencyFormat(curPref.getFGElseHeight()));
      txtFGElseWidth.setText(Utils.getCurrencyFormat(curPref.getFGElseWidth()));

      txtResBoardLength.setText(Utils.getCurrencyFormat(curPref.getResBoardLength()));
      //txtResBoardWidth.setText(Utils.getCurrencyFormat(curPref.getResBoardWidth()));
      txtResBackLength.setText(Utils.getCurrencyFormat(curPref.getResBackLength()));
      //txtResBackWidth.setText(Utils.getCurrencyFormat(curPref.getResBackWidth()));
      txtResGlassLength.setText(Utils.getCurrencyFormat(curPref.getResGlassLength()));
      //txtResGlassWidth.setText(Utils.getCurrencyFormat(curPref.getResGlassWidth()));
      //txtResPremLength.setText(Utils.getCurrencyFormat(curPref.getResPremLength()));
      //txtResPremWidth.setText(Utils.getCurrencyFormat(curPref.getResPremWidth()));

      txtNoOfSpecs.setText(String.valueOf(curPref.getSunNoSpecs()));
      txtAddDays.setText(String.valueOf(curPref.getSunDays()));

      if (curPref.getPrefDate() != UserSettings.MAX_DATE)
        lblCreateDate.setText(Utils.getDatumStr(curPref.getPrefDate()));
      else
        lblCreateDate.setText("Current");
    }
  }

  void btnClose_actionPerformed(ActionEvent e) {
    setData();
    if (curPref.isModified()) {
      int res = JOptionPane.showConfirmDialog(thisDialog, "Do you want to save changes", "Save",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (res == JOptionPane.YES_OPTION) {
        if (singleObject) singlePref = curPref;
        else saveData();
      }
    }
    hide();
  }

  public MethodPrefDets getModifiedPrefs() {
    return singlePref;
  }
}