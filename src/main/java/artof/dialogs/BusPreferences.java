package artof.dialogs;
import artof.database.ArtofDB;
import artof.database.BusPrefDets;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class BusPreferences extends JDialog {
  private JDialog thisDialog;
  private ArtofDB db_conn;
  private BusPrefDets curPref;

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel buspref = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel pnvat = new JPanel();
  JLabel lblvat = new JLabel();
  CustomTextField txtVAT = new CustomTextField();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JLabel txtregistered = new JLabel();
  JComboBox cbcVATReg = new JComboBox();
  JLabel lblcode = new JLabel();
  CustomTextField txtVATCode = new CustomTextField();
  JLabel lblsin2 = new JLabel();
  JComboBox cbxVATOwnItems = new JComboBox();
  JPanel pnmarkup = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  TitledBorder titledBorder5;
  TitledBorder titledBorder6;
  TitledBorder titledBorder7;
  JLabel lbltotalmarkup = new JLabel();
  JLabel lblbasicmarkup = new JLabel();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JPanel pnboards = new JPanel();
  JLabel lblboards = new JLabel();
  JLabel lblframes = new JLabel();
  JLabel lbltotalmarkup1 = new JLabel();
  JLabel lblbasicmarkup1 = new JLabel();
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  JPanel pnframes = new JPanel();
  JLabel lblTotBoardMU = new JLabel();
  JLabel lblTotFrameMU = new JLabel();
  GridBagLayout gridBagLayout7 = new GridBagLayout();
  JLabel lblgb = new JLabel();
  JPanel pnGB = new JPanel();
  JLabel lblbasicmarkup2 = new JLabel();
  JLabel lblTotGBMU = new JLabel();
  JLabel lbltotalmarkup2 = new JLabel();
  JLabel lbldecos = new JLabel();
  JLabel lbltotalmarkup3 = new JLabel();
  JLabel lblbasicmarkup3 = new JLabel();
  GridBagLayout gridBagLayout8 = new GridBagLayout();
  JLabel lblTotDecMU = new JLabel();
  JPanel pndecos = new JPanel();

  JPanel pndiscount = new JPanel();
  JPanel pnclass1 = new JPanel();
  JLabel lblclass1 = new JLabel();
  CustomTextField txtClass1 = new CustomTextField();
  GridBagLayout gridBagLayout9 = new GridBagLayout();
  GridBagLayout gridBagLayout10 = new GridBagLayout();
  GridBagLayout gridBagLayout11 = new GridBagLayout();
  JPanel pnclass2 = new JPanel();
  CustomTextField txtClass2 = new CustomTextField();
  JLabel lblclass2 = new JLabel();
  GridBagLayout gridBagLayout12 = new GridBagLayout();
  JPanel pnlClass3 = new JPanel();
  CustomTextField txtClass3 = new CustomTextField();
  JLabel lblclass3 = new JLabel();
  GridBagLayout gridBagLayout13 = new GridBagLayout();
  JPanel pnclass4 = new JPanel();
  CustomTextField txtClass4 = new CustomTextField();
  JLabel lblclass4 = new JLabel();
  GridBagLayout gridBagLayout14 = new GridBagLayout();
  JPanel pnclass5 = new JPanel();
  CustomTextField txtClass5 = new CustomTextField();
  JLabel lblClass5 = new JLabel();
  GridBagLayout gridBagLayout15 = new GridBagLayout();
  JPanel pnclass6 = new JPanel();
  CustomTextField txtClass6 = new CustomTextField();
  JLabel lblClass6 = new JLabel();
  GridBagLayout gridBagLayout16 = new GridBagLayout();
  JRadioButton rbnClass1 = new JRadioButton();
  JRadioButton rbnClass2 = new JRadioButton();
  JRadioButton rbnClass3 = new JRadioButton();
  JRadioButton rbnClass4 = new JRadioButton();
  JRadioButton rbnClass5 = new JRadioButton();
  JRadioButton rbnClass6 = new JRadioButton();
  ButtonGroup b = new ButtonGroup();
  JPanel pnstretching = new JPanel();
  GridBagLayout gridBagLayout17 = new GridBagLayout();
  JLabel lblminucm = new JLabel();
  JLabel lblotherucm = new JLabel();
  CustomTextField txtMinStretchUCM = new CustomTextField();
  CustomTextField txtOtherStretchUCM = new CustomTextField();
  JLabel lblpriceminucm = new JLabel();
  JLabel lblpriceotherucm = new JLabel();
  CustomTextField txtMinStretchPrice = new CustomTextField();
  CustomTextField txtOtherStretchPrice = new CustomTextField();
  JPanel pnpasting = new JPanel();
  JLabel lblpriceotherucm1 = new JLabel();
  CustomTextField txtOtherPastPrice = new CustomTextField();
  CustomTextField txtMinPastPrice = new CustomTextField();
  JLabel lblotherucm1 = new JLabel();
  CustomTextField txtMinPastUCM = new CustomTextField();
  JLabel lblminucm1 = new JLabel();
  JLabel lblpriceminucm1 = new JLabel();
  CustomTextField txtOtherPastUCM = new CustomTextField();
  GridBagLayout gridBagLayout18 = new GridBagLayout();
  JPanel pnsundries = new JPanel();
  JLabel lblmaterialdiscount = new JLabel();
  JLabel lblbasicmaterial = new JLabel();
  GridBagLayout gridBagLayout19 = new GridBagLayout();
  CustomTextField txtMaterial = new CustomTextField();
  CustomTextField txtMatDiscount = new CustomTextField();
  JLabel lblsin3 = new JLabel();
  JLabel lbllabour = new JLabel();
  CustomTextField txtLabour = new CustomTextField();
  JPanel pnbphist = new JPanel();
  JLabel lbldatecreated = new JLabel();
  GridBagLayout gridBagLayout20 = new GridBagLayout();
  JLabel lblprevioussettings = new JLabel();
  JComboBox cbxDates = new JComboBox();
  JLabel lblCreateDate = new JLabel();
  private Border border1;
  private Border border2;
  private Border border3;
  private Border border4;
  private CustomTextField txtBoardMarkup = new CustomTextField();
  private CustomTextField txtFrameMarkup = new CustomTextField();
  private CustomTextField txtGBMarkup = new CustomTextField();
  private CustomTextField txtDecMarkup = new CustomTextField();
  private Border border5;
  private Border border6;
  private Border border7;
  private Border border8;
  private Border border9;
  private Border border10;
  private Border border11;
  private JButton btnSave = new JButton();
  private JButton btnCancel = new JButton();
  private JButton btnClose = new JButton();

  public BusPreferences(ArtofDB db) {
    thisDialog = this;
    db_conn = db;
    curPref = db_conn.getBusPreferences("Latest");

    ArrayList dateList = db_conn.getBusPrefList();
    Iterator it = dateList.iterator();
    while (it.hasNext())
      cbxDates.insertItemAt((String)it.next(), 0);
    Calendar cal = new GregorianCalendar();
    int curDate = 10000*cal.get(Calendar.YEAR) + 100*(cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
    lblCreateDate.setText(Utils.getDatumStr(curDate));

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    updateData();

    cbxDates.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          setData();
          if (curPref.isModified()) {
            int res = JOptionPane.showConfirmDialog(thisDialog, "Do you want to save changes", "Save",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION)
              saveData();
          }
          curPref = db_conn.getBusPreferences((String)cbxDates.getSelectedItem());
          updateData();
        } catch (NullPointerException x) {
          // doen niks
        }
      }
    });

    btnSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveData();
      }
    });

    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        curPref = db_conn.getBusPreferences((String)cbxDates.getSelectedItem());
        updateData();
      }
    });

    btnClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setData();
        if (curPref.isModified()) {
          int res = JOptionPane.showConfirmDialog(thisDialog, "Do you want to save changes", "Save",
               JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if (res == JOptionPane.YES_OPTION)
            saveData();
        }
        hide();
      }
    });

    cbcVATReg.addItem("Yes");
    cbcVATReg.addItem("No");
    cbxVATOwnItems.addItem("Yes");
    cbxVATOwnItems.addItem("No");

    lblTotBoardMU.setText(Utils.getCurrencyFormat(curPref.getTotalBoardMarkup()));
    lblTotFrameMU.setText(Utils.getCurrencyFormat(curPref.getTotalFrameMarkup()));
    lblTotGBMU.setText(Utils.getCurrencyFormat(curPref.getTotalGBMarkup()));
    lblTotDecMU.setText(Utils.getCurrencyFormat(curPref.getTotalDecMarkup()));

    setSize(660, 530);
    setTitle("Business Preferences");
    setModal(true);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setResizable(false);
    setVisible(true);
  }

  private void saveData() {
    setData();
    if (!curPref.isModified() && curPref.getPrefDate() != UserSettings.MAX_DATE)
      curPref.setModified(true);
    db_conn.saveBusPreferences(curPref);

    curPref = null;
    cbxDates.removeAllItems();
    ArrayList dateList = db_conn.getBusPrefList();
    Iterator it = dateList.iterator();
    while (it.hasNext())
      cbxDates.insertItemAt((String)it.next(), 0);

    cbxDates.setSelectedItem("Latest");
    curPref = db_conn.getBusPreferences("Latest");
    updateData();
  }

  private void setData() {
    if (curPref != null) {
      try {
        curPref.setVATPerc((new Float(txtVAT.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid VAT Percentage was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      curPref.setVATReg((String)cbcVATReg.getSelectedItem());
      curPref.setVATCode(txtVATCode.getText());
      curPref.setVATOwnItems((String)cbxVATOwnItems.getSelectedItem());
      try {
        curPref.setMarkupBoards((new Float(txtBoardMarkup.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for boards was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMarkupFrames((new Float(txtFrameMarkup.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for frames was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMarkupGBs((new Float(txtGBMarkup.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for glass and backs was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMarkupDecs((new Float(txtDecMarkup.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for decorations was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMUDisc1((new Float(txtClass1.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for discount was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMUDisc2((new Float(txtClass2.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for discount was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMUDisc3((new Float(txtClass3.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for discount was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMUDisc4((new Float(txtClass4.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for discount was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMUDisc5((new Float(txtClass5.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for discount was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setMUDisc6((new Float(txtClass6.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid markup for discount was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      if (rbnClass2.isSelected())
        curPref.setMUDiscSelected(1);
      else if (rbnClass3.isSelected())
        curPref.setMUDiscSelected(2);
      else if (rbnClass4.isSelected())
        curPref.setMUDiscSelected(3);
      else if (rbnClass5.isSelected())
        curPref.setMUDiscSelected(4);
      else if (rbnClass6.isSelected())
        curPref.setMUDiscSelected(5);
      else
        curPref.setMUDiscSelected(0);

      try {
        curPref.setStretchMinUCM((new Float(txtMinStretchUCM.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid stretching ucm was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setStretchOtherUCM((new Float(txtOtherStretchUCM.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid stretching ucm was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setStretchMinPrice((new Float(txtMinStretchPrice.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid stretching price ucm was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setStretchOtherPrice((new Float(txtOtherStretchPrice.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid stretching price ucm was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setpastMinUCM((new Float(txtMinPastUCM.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid pasting ucm was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setPastOtherUCM((new Float(txtOtherPastUCM.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid pasting ucm was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setPastMinPrice((new Float(txtMinPastPrice.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid pasting price ucm was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setPastOtherPrice((new Float(txtOtherPastPrice.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid pasting price ucm was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setSundriesBasic((new Float(txtMaterial.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid basic price was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setSundriesDisc((new Float(txtMatDiscount.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid basic material discount was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
      try {
        curPref.setSundriesLabour((new Float(txtLabour.getText())).floatValue());
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(thisDialog, "Invalid labour price was specified", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void updateData() {
    if (curPref == null) {
      // Is nie veronderstel om ooit te gebeur nie.
    }
    else {
      txtVAT.setText(Utils.getCurrencyFormat(curPref.getVATPerc()));
      cbcVATReg.setSelectedItem(curPref.getVATReg());
      txtVATCode.setText(curPref.getVATCode());
      cbxVATOwnItems.setSelectedItem(curPref.getVATOwnItems());

      txtBoardMarkup.setText(Utils.getCurrencyFormat(curPref.getMarkupBoards()));
      lblTotBoardMU.setText(Utils.getCurrencyFormat(curPref.getTotalBoardMarkup()));
      txtFrameMarkup.setText(Utils.getCurrencyFormat(curPref.getMarkupFrames()));
      lblTotBoardMU.setText(Utils.getCurrencyFormat(curPref.getTotalFrameMarkup()));
      txtGBMarkup.setText(Utils.getCurrencyFormat(curPref.getMarkupGBs()));
      lblTotBoardMU.setText(Utils.getCurrencyFormat(curPref.getTotalGBMarkup()));
      txtDecMarkup.setText(Utils.getCurrencyFormat(curPref.getMarkupDecs()));
      lblTotBoardMU.setText(Utils.getCurrencyFormat(curPref.getTotalDecMarkup()));

      txtClass1.setText(Utils.getCurrencyFormat(curPref.getMUDisc1()));
      txtClass2.setText(Utils.getCurrencyFormat(curPref.getMUDisc2()));
      txtClass3.setText(Utils.getCurrencyFormat(curPref.getMUDisc3()));
      txtClass4.setText(Utils.getCurrencyFormat(curPref.getMUDisc4()));
      txtClass5.setText(Utils.getCurrencyFormat(curPref.getMUDisc5()));
      txtClass6.setText(Utils.getCurrencyFormat(curPref.getMUDisc6()));

      int sel = curPref.getMUDiscSelected();
      if (sel == 1)
        rbnClass2.setSelected(true);
      else if (sel == 2)
        rbnClass3.setSelected(true);
      else if (sel == 3)
        rbnClass4.setSelected(true);
      else if (sel == 4)
        rbnClass5.setSelected(true);
      else if (sel == 5)
        rbnClass6.setSelected(true);
      else
        rbnClass1.setSelected(true);

      txtMinStretchUCM.setText(Utils.getCurrencyFormat(curPref.getStretchMinUCM()));
      txtOtherStretchUCM.setText(Utils.getCurrencyFormat(curPref.getStretchOtherUCM()));
      txtMinStretchPrice.setText(Utils.getCurrencyFormat(curPref.getStretchMinPrice()));
      txtOtherStretchPrice.setText(Utils.getCurrencyFormat(curPref.getStretchOtherPrice()));
      txtMinPastUCM.setText(Utils.getCurrencyFormat(curPref.getPastMinUCM()));
      txtOtherPastUCM.setText(Utils.getCurrencyFormat(curPref.getPastOtherUCM()));
      txtMinPastPrice.setText(Utils.getCurrencyFormat(curPref.getPastMinPrice()));
      txtOtherPastPrice.setText(Utils.getCurrencyFormat(curPref.getPastOtherPrice()));
      txtMaterial.setText(Utils.getCurrencyFormat(curPref.getSundriesBasic()));
      txtMatDiscount.setText(Utils.getCurrencyFormat(curPref.getSundriesDisc()));
      txtLabour.setText(Utils.getCurrencyFormat(curPref.getsundriesLabour()));

      if (curPref.getPrefDate() != UserSettings.MAX_DATE)
        lblCreateDate.setText(Utils.getDatumStr(curPref.getPrefDate()));
      else
        lblCreateDate.setText("Current");
    }
  }

  private void jbInit() throws Exception {
    String bheading = "VAT";
    String mheading = "Markup";
    String dheading = "Markup for Discount";
    String sheading = "Stretching";
    String pheading = "Pasting";
    String sundriesheading = "Sundries";
    String histheading = "Business Preferences History";
    titledBorder1 = new TitledBorder(new EtchedBorder(), bheading);
    titledBorder2 = new TitledBorder(new EtchedBorder(), mheading);
    titledBorder3 = new TitledBorder(new EtchedBorder(), dheading);
    titledBorder4 = new TitledBorder(new EtchedBorder(), sheading);
    titledBorder5 = new TitledBorder(new EtchedBorder(), pheading);
    titledBorder6 = new TitledBorder(new EtchedBorder(), sundriesheading);
    titledBorder7 = new TitledBorder(new EtchedBorder(), histheading);
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border3 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border4 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border5 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border6 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border7 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border8 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border9 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border10 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border11 = BorderFactory.createEmptyBorder();
    this.getContentPane().setLayout(borderLayout1);
    buspref.setLayout(gridBagLayout1);
    buspref.setBorder(border11);
    buspref.setPreferredSize(new Dimension(200, 200));
    lblvat.setHorizontalAlignment(SwingConstants.LEFT);
    lblvat.setText("VAT %");
    txtVAT.setPreferredSize(new Dimension(50, 21));
    txtVAT.setBackground(SystemColor.activeCaptionText);
    txtVAT.setMinimumSize(new Dimension(20, 21));
    pnvat.setLayout(gridBagLayout3);
    txtregistered.setText("Registered");
    lblcode.setText("Code");
    lblsin2.setText("Take VAT on ordered stock");
    pnvat.setBorder(titledBorder1);
    pnmarkup.setLayout(gridBagLayout4);
    pnmarkup.setBorder(titledBorder2);
    lbltotalmarkup.setText("Total");
    lblbasicmarkup.setText("Basic");
    pnboards.setLayout(gridBagLayout5);
    pnboards.setBorder(border1);
    pnboards.setMinimumSize(new Dimension(76, 74));
    pnboards.setPreferredSize(new Dimension(76, 74));
    lblboards.setFont(new java.awt.Font("Serif", 2, 14));
    lblboards.setText("Boards");
    lblframes.setText("Frames");
    lblframes.setFont(new java.awt.Font("Serif", 2, 14));
    lbltotalmarkup1.setText("Total");
    lblbasicmarkup1.setText("Basic");
    pnframes.setBorder(border2);
    pnframes.setMinimumSize(new Dimension(76, 74));
    pnframes.setPreferredSize(new Dimension(76, 74));
    pnframes.setLayout(gridBagLayout6);
    lblTotFrameMU.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotFrameMU.setText("3.420");
    lblTotBoardMU.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotBoardMU.setText("5.120");
    lblgb.setText("Glass & Backs");
    lblgb.setFont(new java.awt.Font("Serif", 2, 14));
    pnGB.setBorder(border3);
    pnGB.setMinimumSize(new Dimension(76, 74));
    pnGB.setPreferredSize(new Dimension(76, 74));
    pnGB.setLayout(gridBagLayout7);
    lblbasicmarkup2.setText("Basic");
    lblTotGBMU.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotGBMU.setText("5.120");
    lbltotalmarkup2.setToolTipText("");
    lbltotalmarkup2.setText("Total");
    lbldecos.setText("Decorations");
    lbldecos.setFont(new java.awt.Font("Serif", 2, 14));
    lbltotalmarkup3.setText("Total");
    lblbasicmarkup3.setText("Basic");
    lblTotDecMU.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotDecMU.setText("5.120");
    pndecos.setBorder(border4);
    pndecos.setLayout(gridBagLayout8);
    pndiscount.setLayout(gridBagLayout16);
    pndiscount.setBorder(titledBorder3);
    pnclass1.setBorder(border5);
    pnclass1.setLayout(gridBagLayout9);
    lblclass1.setFont(new java.awt.Font("Serif", 2, 14));
    lblclass1.setText("Class 1");
    txtClass1.setBackground(SystemColor.textHighlightText);
    txtClass1.setMargin(new Insets(2, 2, 2, 2));
    txtClass1.setHorizontalAlignment(SwingConstants.RIGHT);
    pnclass2.setLayout(gridBagLayout11);
    pnclass2.setBorder(border6);
    txtClass2.setBackground(SystemColor.textHighlightText);
    txtClass2.setMargin(new Insets(2, 2, 2, 2));
    txtClass2.setHorizontalAlignment(SwingConstants.RIGHT);
    lblclass2.setFont(new java.awt.Font("Serif", 2, 14));
    lblclass2.setText("Class 2");
    pnlClass3.setBorder(border7);
    pnlClass3.setLayout(gridBagLayout12);
    txtClass3.setBackground(SystemColor.textHighlightText);
    txtClass3.setMargin(new Insets(2, 2, 2, 2));
    txtClass3.setHorizontalAlignment(SwingConstants.RIGHT);
    lblclass3.setFont(new java.awt.Font("Serif", 2, 14));
    lblclass3.setText("Class 3");
    pnclass4.setBorder(border8);
    pnclass4.setLayout(gridBagLayout13);
    txtClass4.setBackground(SystemColor.textHighlightText);
    txtClass4.setMargin(new Insets(2, 2, 2, 2));
    txtClass4.setHorizontalAlignment(SwingConstants.RIGHT);
    lblclass4.setFont(new java.awt.Font("Serif", 2, 14));
    lblclass4.setText("Class 4");
    pnclass5.setBorder(border9);
    pnclass5.setLayout(gridBagLayout14);
    txtClass5.setBackground(SystemColor.textHighlightText);
    txtClass5.setMargin(new Insets(2, 2, 2, 2));
    txtClass5.setHorizontalAlignment(SwingConstants.RIGHT);
    lblClass5.setFont(new java.awt.Font("Serif", 2, 14));
    lblClass5.setText("Class 5");
    pnclass6.setBorder(border10);
    pnclass6.setLayout(gridBagLayout15);
    txtClass6.setBackground(SystemColor.textHighlightText);
    txtClass6.setMargin(new Insets(2, 2, 2, 2));
    txtClass6.setHorizontalAlignment(SwingConstants.RIGHT);
    lblClass6.setFont(new java.awt.Font("Serif", 2, 14));
    lblClass6.setText("Class 6");
    txtVATCode.setBackground(SystemColor.activeCaptionText);
    pnstretching.setLayout(gridBagLayout17);
    pnstretching.setBorder(titledBorder4);
    lblminucm.setText("Minimum ucm");
    lblotherucm.setText("Any other ucm");
    txtMinStretchUCM.setBackground(SystemColor.textHighlightText);
    txtMinStretchUCM.setHorizontalAlignment(SwingConstants.RIGHT);
    txtOtherStretchUCM.setBackground(SystemColor.textHighlightText);
    txtOtherStretchUCM.setHorizontalAlignment(SwingConstants.RIGHT);
    lblpriceminucm.setText("Price at min");
    lblpriceotherucm.setText("Price at other");
    txtMinStretchPrice.setBackground(SystemColor.textHighlightText);
    txtMinStretchPrice.setHorizontalAlignment(SwingConstants.RIGHT);
    txtOtherStretchPrice.setBackground(SystemColor.textHighlightText);
    txtOtherStretchPrice.setHorizontalAlignment(SwingConstants.RIGHT);
    pnpasting.setBorder(titledBorder5);
    pnpasting.setLayout(gridBagLayout18);
    lblpriceotherucm1.setText("Price at other");
    txtOtherPastPrice.setBackground(SystemColor.textHighlightText);
    txtOtherPastPrice.setHorizontalAlignment(SwingConstants.RIGHT);
    txtMinPastPrice.setBackground(SystemColor.textHighlightText);
    txtMinPastPrice.setHorizontalAlignment(SwingConstants.RIGHT);
    lblotherucm1.setText("Any other ucm");
    txtMinPastUCM.setBackground(SystemColor.textHighlightText);
    txtMinPastUCM.setHorizontalAlignment(SwingConstants.RIGHT);
    lblminucm1.setText("Minimum ucm");
    lblpriceminucm1.setText("Price at min");
    txtOtherPastUCM.setBackground(SystemColor.textHighlightText);
    txtOtherPastUCM.setHorizontalAlignment(SwingConstants.RIGHT);
    pnsundries.setBorder(titledBorder6);
    pnsundries.setLayout(gridBagLayout19);
    lblmaterialdiscount.setText("Material Discount");
    lblbasicmaterial.setText("Basic Material  R");
    lblsin3.setText("(% - negative)");
    lbllabour.setText("Labour  R");
    txtMaterial.setBackground(SystemColor.activeCaptionText);
    txtMaterial.setHorizontalAlignment(SwingConstants.RIGHT);
    txtMatDiscount.setBackground(SystemColor.activeCaptionText);
    txtMatDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
    txtLabour.setBackground(SystemColor.activeCaptionText);
    txtLabour.setHorizontalAlignment(SwingConstants.RIGHT);
    pnbphist.setBorder(titledBorder7);
    pnbphist.setMinimumSize(new Dimension(189, 103));
    pnbphist.setPreferredSize(new Dimension(189, 103));
    pnbphist.setLayout(gridBagLayout20);
    lbldatecreated.setText("Date Created");
    lblprevioussettings.setText("Previous Settings");
    lblCreateDate.setText("19-06-1977");

    cbcVATReg.setMinimumSize(new Dimension(55, 21));
    cbcVATReg.setPreferredSize(new Dimension(55, 21));
    cbcVATReg.setEditable(true);
    cbxVATOwnItems.setAutoscrolls(false);
    cbxVATOwnItems.setMinimumSize(new Dimension(55, 21));
    cbxVATOwnItems.setPreferredSize(new Dimension(55, 21));
    cbxVATOwnItems.setEditable(true);
    txtBoardMarkup.setHorizontalAlignment(SwingConstants.RIGHT);
    txtBoardMarkup.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtBoardMarkup_focusLost(e);
      }
    });
    txtFrameMarkup.setHorizontalAlignment(SwingConstants.RIGHT);
    txtFrameMarkup.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtFrameMarkup_focusLost(e);
      }
    });
    txtGBMarkup.setHorizontalAlignment(SwingConstants.RIGHT);
    txtGBMarkup.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtGBMarkup_focusLost(e);
      }
    });
    txtDecMarkup.setHorizontalAlignment(SwingConstants.RIGHT);
    txtDecMarkup.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtDecMarkup_focusLost(e);
      }
    });
    btnSave.setMaximumSize(new Dimension(73, 27));
    btnSave.setMinimumSize(new Dimension(73, 27));
    btnSave.setPreferredSize(new Dimension(73, 27));
    btnSave.setText("Save");
    btnCancel.setText("Cancel");
    btnClose.setMaximumSize(new Dimension(73, 27));
    btnClose.setMinimumSize(new Dimension(73, 27));
    btnClose.setPreferredSize(new Dimension(73, 27));
    btnClose.setText("Close");
    pnvat.add(lblvat,                        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 5));
    pnvat.add(txtVAT,                    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 50, 0));
    pnvat.add(txtregistered,        new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    pnvat.add(cbcVATReg,       new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnvat.add(lblcode,      new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    pnvat.add(txtVATCode,     new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 80, 0));
    pnvat.add(lblsin2,    new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    pnvat.add(cbxVATOwnItems,       new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    buspref.add(pnbphist,         new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnpasting.add(lblminucm1,      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnpasting.add(txtMinPastUCM,    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnpasting.add(lblotherucm1,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnpasting.add(txtOtherPastUCM,    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnpasting.add(lblpriceminucm1,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 2), 0, 0));
    pnpasting.add(lblpriceotherucm1,     new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 2), 0, 0));
    pnpasting.add(txtMinPastPrice,    new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnpasting.add(txtOtherPastPrice,    new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    buspref.add(pnstretching,                                        new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnclass2.add(lblclass2,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnclass2.add(txtClass2,         new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 35, -5));
    pnclass2.add(rbnClass2,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), -3, 0));
    buspref.add(pnmarkup,   new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pndiscount.add(pnlClass3,         new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnlClass3.add(lblclass3,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 8));
    pnlClass3.add(txtClass3,        new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 35, -5));
    pnlClass3.add(rbnClass3,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), -3, 0));
    pndiscount.add(pnclass4,    new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnclass4.add(lblclass4,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnclass4.add(txtClass4,         new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 35, -5));
    pnclass4.add(rbnClass4,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), -3, 0));
    pndiscount.add(pnclass5,   new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnclass5.add(lblClass5,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnclass5.add(txtClass5,         new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 35, -5));
    pnclass5.add(rbnClass5,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), -3, 0));
    pndiscount.add(pnclass6,   new GridBagConstraints(5, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnclass6.add(lblClass6,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnclass6.add(txtClass6,         new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 35, -5));
    pnclass6.add(rbnClass6,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), -3, 0));
    pndiscount.add(pnclass1,          new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));


    pnboards.add(lblbasicmarkup,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnboards.add(lbltotalmarkup,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 3));
    pnboards.add(lblboards,      new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnboards.add(lblTotBoardMU,  new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnboards.add(txtBoardMarkup,    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 20, 2, 2), 0, 0));
    pnmarkup.add(pnframes,  new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

    pnframes.add(lblbasicmarkup1,       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnframes.add(lbltotalmarkup1,      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnframes.add(lblframes,       new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnframes.add(lblTotFrameMU,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    pnmarkup.add(pndecos,  new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnmarkup.add(pnboards,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnGB.add(lblbasicmarkup2,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnGB.add(lbltotalmarkup2,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnGB.add(lblgb,    new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnGB.add(lblTotGBMU,  new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    buspref.add(pnvat,    new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pndecos.add(lblbasicmarkup3,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pndecos.add(lbltotalmarkup3,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pndecos.add(lbldecos,   new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pndecos.add(lblTotDecMU,  new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnmarkup.add(pnGB,            new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnclass1.add(lblclass1,      new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnclass1.add(txtClass1,          new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 35, -5));
    pnclass1.add(rbnClass1,           new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), -3, 0));
    pndiscount.add(pnclass2,           new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

    b.add(rbnClass1);
    b.add(rbnClass2);
    b.add(rbnClass3);
    b.add(rbnClass4);
    b.add(rbnClass5);
    b.add(rbnClass6);
    pnstretching.add(lblminucm,                        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnstretching.add(txtMinStretchUCM,                    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnstretching.add(lblotherucm,                  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnstretching.add(txtOtherStretchUCM,                 new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnstretching.add(lblpriceminucm,           new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 2), 0, 0));
    pnstretching.add(lblpriceotherucm,       new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 2), 0, 0));
    pnstretching.add(txtMinStretchPrice,          new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnstretching.add(txtOtherStretchPrice,        new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    buspref.add(pndiscount,    new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 3, 0));
    pnsundries.add(lblmaterialdiscount,         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnsundries.add(lblbasicmaterial,       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnsundries.add(txtMaterial,      new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnsundries.add(txtMatDiscount,       new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnsundries.add(lblsin3,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnsundries.add(lbllabour,        new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnsundries.add(txtLabour,         new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    buspref.add(pnpasting,    new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    pnbphist.add(btnSave,         new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnbphist.add(btnCancel,         new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnbphist.add(btnClose,        new GridBagConstraints(2, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnbphist.add(lbldatecreated,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    pnbphist.add(lblprevioussettings,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 0, 2, 2), 0, 0));
    pnbphist.add(cbxDates,   new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    pnbphist.add(lblCreateDate,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 0), 0, 0));
    buspref.add(pnsundries,   new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    this.getContentPane().add(buspref, BorderLayout.CENTER);
    pnframes.add(txtFrameMarkup,   new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 20, 2, 2), 0, 0));
    pnGB.add(txtGBMarkup,   new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 20, 2, 2), 0, 0));
    pndecos.add(txtDecMarkup,   new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 20, 2, 2), 0, 0));
  }

  void txtBoardMarkup_focusLost(FocusEvent e) {
    setData();
    lblTotBoardMU.setText(Utils.getCurrencyFormat(curPref.getTotalBoardMarkup()));
  }

  void txtFrameMarkup_focusLost(FocusEvent e) {
    setData();
    lblTotFrameMU.setText(Utils.getCurrencyFormat(curPref.getTotalFrameMarkup()));
  }

  void txtGBMarkup_focusLost(FocusEvent e) {
    setData();
    lblTotGBMU.setText(Utils.getCurrencyFormat(curPref.getTotalGBMarkup()));
  }

  void txtDecMarkup_focusLost(FocusEvent e) {
    setData();
    lblTotDecMU.setText(Utils.getCurrencyFormat(curPref.getTotalDecMarkup()));
  }
}