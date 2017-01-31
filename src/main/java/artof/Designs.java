package artof;

import artof.database.ArtofDB;
import artof.database.BusPrefDets;
import artof.database.DesignDets;
import artof.database.MethodPrefDets;
import artof.designer.DesignSettings;
import artof.designer.Designer;
import artof.designitems.DesignItem2;
import artof.dialogs.Artists2;
import artof.dialogs.Clients2;
import artof.dialogs.MethodPreferences;
import artof.dialogs.ProgressDialog;
import artof.dialogs.invoicing.InvoiceConfigureDialog;
import artof.materials.MaterialDets;
import artof.stock.StockItem;
import artof.stock.printing.OffcutPrinter;
import artof.utils.*;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import sun.misc.BASE64Decoder;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class Designs extends JPanel {
  public final static int DES_STATUS_QUOT = 0;
  public final static int DES_STATUS_ORDER = 1;
  public final static int DES_STATUS_COMP = 2;
  public final static String[] DESIGN_STATUS = {"Quotation", "In Order", "Completed"};

  private ArtofDB db_conn;
  private Designer designer;
  private ArrayList designList = new ArrayList();
  private int curDesignIndex = -1;

  // fok die nuwe allocations hier in
  private ArrayList allocationList;

  GraphicsScrollPane gras;
  boolean showpref;
  boolean showpnprice;
  boolean showpnbutton1;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pn2nd = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel pnclientdetails = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
//  JComboBox cbxClient = new JComboBox();
//  JComboBox cbxArtist = new JComboBox();
  JComboBox cbxClient = new SearchComboBox();
  JComboBox cbxArtist = new SearchComboBox();
  CustomTextField txtTitle = new CustomTextField();
  JButton btnClients = new JButton();
  JButton btnArtists = new JButton();
  JComboBox cbxStatus = new JComboBox();
  JButton btnpanelshow = new JButton();
  JLabel lblclient2 = new JLabel();
  JLabel lbldesigncodevalue1 = new JLabel();
  JLabel lblawtitle1 = new JLabel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JLabel lbldesigncode1 = new JLabel();
  JLabel lblDate2 = new JLabel();
  JLabel lbldate1 = new JLabel();
  JLabel lblartist1 = new JLabel();
  JLabel lblawtitletxt = new JLabel();
  JPanel pnmindetails = new JPanel();
  JButton btnpanelshow1 = new JButton();
  JLabel lblstatus1 = new JLabel();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JRadioButton rbnFullSize = new JRadioButton();
  JRadioButton rbnFixedBorder = new JRadioButton();
  JRadioButton rbnCalcOverlap = new JRadioButton();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JPanel pnoverlap = new JPanel();
  JPanel pnpreferences = new JPanel();
  TitledBorder titledBorder1;
  Border border1;
  TitledBorder titledBorder2;
  JPanel pnfixedborder = new JPanel();
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout7 = new GridBagLayout();
  CustomTextField txtFBWithSlip = new CustomTextField();
  JLabel jLabel4 = new JLabel();
  CustomTextField txtFBNoSlip = new CustomTextField();
  JPanel pnoperationalcosts = new JPanel();
  GridBagLayout gridBagLayout8 = new GridBagLayout();
  JLabel jLabel5 = new JLabel();
  Border border2;
  TitledBorder titledBorder3;
  CustomTextField txtMaterial = new CustomTextField();
  JLabel jLabel6 = new JLabel();
  CustomTextField txtLabour = new CustomTextField();
  JLabel jLabel7 = new JLabel();
  CustomTextField txtOLAdj = new CustomTextField();
  JButton btnshowpref = new JButton();
  JButton btnshowpref2 = new JButton();
  JPanel pnside = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel pnshower = new JPanel();
  JLabel jLabel13 = new JLabel();
  GridBagLayout gridBagLayout9 = new GridBagLayout();
  JLabel jLabel11 = new JLabel();
  JLabel jLabel10 = new JLabel();
  JLabel jLabel9 = new JLabel();
  JLabel jLabel8 = new JLabel();
  CustomTextField txtPriceOne = new CustomTextField();
  JPanel pnprice = new JPanel();
  CustomTextField txtDiscountOne = new CustomTextField();
  JLabel lblClient = new JLabel();
  JLabel lblStatus = new JLabel();
  JLabel lblArtist = new JLabel();
  Border border3;
  Border border4;
  Border border5;
  Border border6;
  Border border7;
  Border border8;
  Border border9;
  Border border10;
  Border border11;
  Border border12;
  Border border13;
  Border border14;
  private JLabel jLabel14 = new JLabel();
  private JLabel jLabel15 = new JLabel();
  private JLabel jLabel16 = new JLabel();
  private JLabel jLabel17 = new JLabel();
  private JLabel jLabel18 = new JLabel();
  private JLabel jLabel19 = new JLabel();
  private JLabel jLabel20 = new JLabel();
  private JLabel jLabel21 = new JLabel();
  private JLabel jLabel22 = new JLabel();
  private JLabel lblHomeTel = new JLabel();
  private JLabel lblWorkTel = new JLabel();
  private JLabel lblMobile = new JLabel();
  private JLabel lblDate = new JLabel();
  private JLabel lblDesignID = new JLabel();
  private JLabel lblDesignID2 = new JLabel();
  private JLabel lblTitle2 = new JLabel();
  private JCheckBox ckFullBottom = new JCheckBox();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private Border border15;
  private TitledBorder titledBorder4;
  private JCheckBox ckStretching = new JCheckBox();
  private JCheckBox ckPasting = new JCheckBox();
  private JPanel jPanel2 = new JPanel();
  private JButton btnPrefs = new JButton(new ImageIcon("images/Regs.gif"));
  private GridBagLayout gridBagLayout6 = new GridBagLayout();
  private CustomTextField txtVATOne = new CustomTextField();
  private CustomTextField txtTotalOne = new CustomTextField();
  private JLabel jLabel2 = new JLabel();
  private CustomTextField txtNoOrdered = new CustomTextField();
  private JLabel jLabel3 = new JLabel();
  private CustomTextField txtTotalNo = new CustomTextField();
  private JLabel jLabel12 = new JLabel();
  private JLabel jLabel23 = new JLabel();
  private CustomTextField txtPriceOther = new CustomTextField();
  private JLabel jLabel24 = new JLabel();
  private JLabel lblOtherVAT = new JLabel();
  private CustomTextField txtDiscountOther = new CustomTextField();
  private CustomTextField txtVATOther = new CustomTextField();
  private JLabel jLabel25 = new JLabel();
  private CustomTextField txtTotalAll = new CustomTextField();
  private Border border16;
  private BorderLayout borderLayout3 = new BorderLayout();
  private Border border17;
  private CustomTextField txtDelDate = new CustomTextField();
  private JPanel jPanel3 = new JPanel();
  private JButton btnNextDelDate = new JButton();
  private JButton btnPrevDelDate = new JButton();
  private GridBagLayout gridBagLayout10 = new GridBagLayout();
  private JPanel jPanel4 = new JPanel();
  private Border border18;
  JButton btnUpdateMethodPrefs = new JButton();

  public Designs(artof.database.ArtofDB db) {
    db_conn = db;
    designer = new Designer(db_conn, this);
    gras = new GraphicsScrollPane();
    refreshClients();
    refreshArtists();
    setCurrentDesign( -1);

    for (int i = 0; i < DESIGN_STATUS.length; i++) {
      cbxStatus.addItem(DESIGN_STATUS[i]);

    }
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    setSize(new Dimension(1000, 850));
    setVisible(true);
  }

  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
        white, new Color(148, 145, 140)), "Border Settings");
    border1 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    titledBorder2 = new TitledBorder(border1, "Overlap Settings");
    border2 = BorderFactory.createEtchedBorder(Color.white,
                                               new Color(148, 145, 140));
    titledBorder3 = new TitledBorder(border2, "Operational Costs");
    border3 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                              Color.white,
                                              new Color(148, 145, 140),
                                              new Color(103, 101, 98));
    border4 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                              Color.white,
                                              new Color(148, 145, 140),
                                              new Color(103, 101, 98));
    border5 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                              Color.white,
                                              new Color(148, 145, 140),
                                              new Color(103, 101, 98));
    border6 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                              Color.white,
                                              new Color(103, 101, 98),
                                              new Color(148, 145, 140));
    border7 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                              Color.white,
                                              new Color(148, 145, 140),
                                              new Color(103, 101, 98));
    border8 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                              Color.white,
                                              new Color(103, 101, 98),
                                              new Color(148, 145, 140));
    border9 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                              Color.white,
                                              new Color(148, 145, 140),
                                              new Color(103, 101, 98));
    border10 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                               Color.white,
                                               new Color(148, 145, 140),
                                               new Color(103, 101, 98));
    border11 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                               Color.white,
                                               new Color(148, 145, 140),
                                               new Color(103, 101, 98));
    border12 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                               Color.white,
                                               new Color(148, 145, 140),
                                               new Color(103, 101, 98));
    border13 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                               Color.white,
                                               new Color(148, 145, 140),
                                               new Color(103, 101, 98));
    border14 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white,
                                               Color.white,
                                               new Color(148, 145, 140),
                                               new Color(103, 101, 98));
    border15 = BorderFactory.createEtchedBorder(Color.white,
                                                new Color(148, 145, 140));
    titledBorder4 = new TitledBorder(border15, "Other");
    border16 = BorderFactory.createEtchedBorder(Color.white,
                                                new Color(148, 145, 140));
    border17 = BorderFactory.createEtchedBorder(Color.white,
                                                new Color(148, 145, 140));
    border18 = BorderFactory.createEtchedBorder(Color.white,
                                                new Color(148, 145, 140));
    setLayout(borderLayout1);
    pn2nd.setLayout(borderLayout2);
    pnclientdetails.setLayout(gridBagLayout1);
    pnclientdetails.setAlignmentX( (float) 0.0);
    pnclientdetails.setBorder(BorderFactory.createEtchedBorder());
    pnclientdetails.setPreferredSize(new Dimension(700, 90));
    btnClients.setBorder(null);
    btnClients.setIcon(new ImageIcon("images/ToClients.gif"));
    btnClients.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnClients_actionPerformed(e);
      }
    });
    btnArtists.setBorder(null);
    btnpanelshow.setBorder(null);
    btnpanelshow.setIcon(new ImageIcon("images/UpSwitch.gif"));
    btnpanelshow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnpanelshow_actionPerformed(e);
      }
    });
    btnArtists.setIcon(new ImageIcon("images/ToArtists.gif"));
    btnArtists.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnArtists_actionPerformed(e);
      }
    });
    btnshowpref.setIcon(new ImageIcon("images/UpSwitch.gif"));
    btnshowpref2.setIcon(new ImageIcon("images/UpSwitch.gif"));
    btnshowpref2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnshowpref_actionPerformed(e);
      }
    });
    btnshowpref.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnshowpref_actionPerformed(e);
      }
    });
    btnshowpref.setBorder(null);
    btnshowpref2.setBorder(null);
    lblclient2.setText("Client");
    lbldesigncodevalue1.setForeground(Color.blue);
    lbldesigncodevalue1.setFont(new java.awt.Font("DialogInput", 2, 12));
    lblawtitle1.setText("Artwork Title");
    lbldesigncode1.setText("Design Code");
    lblDate2.setForeground(Color.blue);
    lblDate2.setFont(new java.awt.Font("DialogInput", 2, 12));
    lbldate1.setText("Date");
    lblartist1.setText("Artist");
    lblawtitletxt.setFont(new java.awt.Font("Serif", 2, 12));
    lblawtitletxt.setForeground(Color.blue);
    lblawtitletxt.setText(txtTitle.getText());
    pnmindetails.setLayout(gridBagLayout3);
    pnmindetails.setBorder(BorderFactory.createEtchedBorder());
    btnpanelshow1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnpanelshow1_actionPerformed(e);
      }
    });
    btnpanelshow1.setIcon(new ImageIcon("images/DownSwitch.gif"));
    btnpanelshow1.setBorder(null);
    lblstatus1.setText("Status");
    rbnFullSize.setPreferredSize(new Dimension(71, 17));
    rbnFullSize.setText("Full Size");
    rbnFullSize.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rbnFullSize_actionPerformed(e);
      }
    });
    rbnFixedBorder.setPreferredSize(new Dimension(91, 17));
    rbnFixedBorder.setText("Fixed Border");
    rbnFixedBorder.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rbnFixedBorder_actionPerformed(e);
      }
    });
    rbnCalcOverlap.setPreferredSize(new Dimension(150, 17));
    rbnCalcOverlap.setText("Calculated OL");
    rbnCalcOverlap.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rbnCalcOverlap_actionPerformed(e);
      }
    });
    pnoverlap.setLayout(gridBagLayout5);
    pnoverlap.setBorder(titledBorder2);
    pnoverlap.setMinimumSize(new Dimension(120, 78));
    pnoverlap.setPreferredSize(new Dimension(183, 91));
    pnoverlap.setToolTipText("");
    pnpreferences.setLayout(gridBagLayout4);
    pnpreferences.setBorder(BorderFactory.createEtchedBorder());
    pnfixedborder.setBorder(titledBorder1);
    pnfixedborder.setPreferredSize(new Dimension(183, 103));
    pnfixedborder.setLayout(gridBagLayout7);
    jLabel1.setText("Fixed Border With Slip");
    jLabel4.setText("Fixed Border No Slip");
    pnoperationalcosts.setLayout(gridBagLayout8);
    jLabel5.setText("Material");
    pnoperationalcosts.setBorder(titledBorder3);
    pnoperationalcosts.setPreferredSize(new Dimension(103, 91));
    jLabel6.setText("Labour");
    jLabel7.setText("Overlap Adjustment");
    pnside.setLayout(borderLayout4);
    pnshower.setLayout(borderLayout3);
    jLabel13.setForeground(Color.blue);
    jLabel13.setText("Total");
    jLabel11.setText("+ VAT");
    jLabel10.setText("- discount");
    jLabel9.setText("Price");
    jLabel8.setText("Delivery Date");
    pnprice.setLayout(gridBagLayout9);
    pnprice.setBorder(BorderFactory.createEtchedBorder());
    pnprice.setMinimumSize(new Dimension(100, 137));
    pnprice.setPreferredSize(new Dimension(180, 350));

    lblClient.setFont(new java.awt.Font("Serif", 2, 12));
    lblClient.setForeground(Color.blue);
    lblStatus.setFont(new java.awt.Font("Serif", 2, 12));
    lblStatus.setForeground(Color.blue);
    lblArtist.setFont(new java.awt.Font("Serif", 2, 12));
    lblArtist.setForeground(Color.blue);
    jLabel14.setText("Client");
    jLabel15.setText("Artist");
    jLabel16.setText("Artwork Title");
    jLabel17.setText("Status");
    jLabel18.setText("Date");
    jLabel19.setText("Design Code");
    jLabel20.setText("Home Tel");
    jLabel21.setText("Work Tel");
    jLabel22.setText("Mobile");
    lblHomeTel.setPreferredSize(new Dimension(110, 17));
    lblWorkTel.setPreferredSize(new Dimension(110, 17));
    lblMobile.setPreferredSize(new Dimension(110, 17));
    cbxClient.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cbxClient_actionPerformed(e);
      }
    });
    lblDesignID2.setFont(new java.awt.Font("Serif", 2, 12));
    lblDesignID2.setForeground(Color.blue);
    lblTitle2.setFont(new java.awt.Font("Serif", 2, 12));
    lblTitle2.setForeground(Color.blue);
    ckFullBottom.setText("Full Bottom");
    ckFullBottom.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ckFullBottom_actionPerformed(e);
      }
    });
    jPanel1.setLayout(gridBagLayout2);
    jPanel1.setBorder(titledBorder4);
    ckStretching.setText("Stretching");
    ckStretching.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ckStretching_actionPerformed(e);
      }
    });
    ckPasting.setText("Pasting");
    ckPasting.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ckPasting_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout6);
    btnPrefs.setToolTipText("Open mthod preferences");
    btnPrefs.setMargin(new Insets(2, 2, 2, 2));
    btnPrefs.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrefs_actionPerformed(e);
      }
    });
    txtTotalOne.setForeground(Color.blue);
    txtTotalOne.setEditable(false);
    txtVATOne.setEditable(false);
    txtPriceOne.setEditable(false);
    pnside.setPreferredSize(new Dimension(180, 139));
    jLabel2.setText("No Ordered");
    jLabel3.setForeground(Color.blue);
    jLabel3.setText("Total");
    txtTotalNo.setForeground(Color.blue);
    txtTotalNo.setEditable(false);
    jLabel12.setText("Other Items");
    jLabel23.setText("Price");
    jLabel24.setText("- discount");
    lblOtherVAT.setText("+ VAT");
    jLabel25.setForeground(Color.red);
    jLabel25.setText("Total Price");
    txtTotalAll.setForeground(Color.red);
    txtTotalAll.setEditable(false);
    btnPrevDelDate.setPreferredSize(new Dimension(17, 17));
    btnPrevDelDate.setMargin(new Insets(2, 2, 2, 2));
    btnPrevDelDate.setText("<");
    btnPrevDelDate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrevDelDate_actionPerformed(e);
      }
    });
    btnNextDelDate.setPreferredSize(new Dimension(17, 17));
    btnNextDelDate.setMargin(new Insets(2, 2, 2, 2));
    btnNextDelDate.setText(">");
    btnNextDelDate.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNextDelDate_actionPerformed(e);
      }
    });
    jPanel3.setLayout(gridBagLayout10);
    txtDelDate.setEditable(false);
    txtVATOther.setEditable(false);
    txtDiscountOne.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtDiscountOne_focusLost(e);
      }
    });
    txtNoOrdered.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtNoOrdered_focusLost(e);
      }
    });
    txtPriceOther.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtPriceOther_focusLost(e);
      }
    });
    txtDiscountOther.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtDiscountOther_focusLost(e);
      }
    });
    //btnCalculate2.setIcon(new ImageIcon("images/Calculate.gif"));
//    btnQuote2.setIcon(new ImageIcon("images/PrintQuote.gif"));
    txtDiscountOne.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtDiscountOne_focusLost(e);
      }
    });
    txtMaterial.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtMaterial_focusLost(e);
      }
    });
    txtLabour.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtLabour_focusLost(e);
      }
    });
    btnUpdateMethodPrefs.setMargin(new Insets(2, 2, 2, 2));
    btnUpdateMethodPrefs.setText("");
    btnUpdateMethodPrefs.setToolTipText("Recalculate design with new preferences");
    btnUpdateMethodPrefs.setIcon(new ImageIcon("images/Calculator.gif"));
    btnUpdateMethodPrefs.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnUpdateMethodPrefs_actionPerformed(e);
      }
    });
    pnclientdetails.add(cbxClient, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(cbxArtist, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(txtTitle, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(btnClients, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(btnArtists, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(cbxStatus, new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(btnpanelshow,
                        new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.NONE,
                                               new Insets(2, 10, 2, 5), 0, 0));
    pnclientdetails.add(btnshowpref,
                        new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.NONE,
                                               new Insets(2, 10, 2, 5), 0, 0));
    pnclientdetails.add(jLabel14, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(jLabel15, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(jLabel16, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(jLabel17, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnclientdetails.add(jLabel18, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnclientdetails.add(jLabel19, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnclientdetails.add(jLabel20, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnclientdetails.add(jLabel21, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnclientdetails.add(jLabel22, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnclientdetails.add(lblHomeTel, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    add(pn2nd, BorderLayout.CENTER);
    //pn2nd.add(pnpreferences, BorderLayout.NORTH);
    showpref = false;
    pnoverlap.add(rbnCalcOverlap,  new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    pnoverlap.add(rbnFixedBorder,  new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    pnoverlap.add(rbnFullSize,  new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    add(pnclientdetails, BorderLayout.NORTH);
    pnmindetails.add(lblartist1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnmindetails.add(lblawtitletxt, new GridBagConstraints(6, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(0, 10, 0, 0), 0, 0));
    pnmindetails.add(lblawtitle1, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnmindetails.add(lblstatus1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnmindetails.add(lbldesigncode1,
                     new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.NONE,
                                            new Insets(2, 10, 2, 5), 0, 0));
    pnmindetails.add(lbldesigncodevalue1,
                     new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 10, 0, 0), 0, 0));
    pnmindetails.add(lblclient2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnmindetails.add(lblDate2, new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnmindetails.add(lbldate1, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnmindetails.add(btnpanelshow1, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnmindetails.add(btnshowpref2, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 5), 0, 0));
    pnmindetails.add(lblClient, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnmindetails.add(lblStatus, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnmindetails.add(lblArtist, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    buttonGroup1.add(rbnCalcOverlap);
    buttonGroup1.add(rbnFixedBorder);
    buttonGroup1.add(rbnFullSize);
    pnpreferences.add(pnoperationalcosts,
                        new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 30, 0));
    pnpreferences.add(pnfixedborder,
                            new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 60, 0));
    pnpreferences.add(pnoverlap,      new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 130, 0));
    pnfixedborder.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnfixedborder.add(txtFBWithSlip,
                      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.HORIZONTAL,
                                             new Insets(2, 5, 2, 5), 0, 0));
    pnfixedborder.add(jLabel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnfixedborder.add(txtFBNoSlip, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnfixedborder.add(jLabel7, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnfixedborder.add(txtOLAdj, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnoperationalcosts.add(jLabel5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnoperationalcosts.add(txtMaterial,
                           new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.NORTH,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(2, 5, 2, 5), 0, 0));
    pnoperationalcosts.add(jLabel6, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnoperationalcosts.add(txtLabour,
                           new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                                                  , GridBagConstraints.NORTH,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(2, 5, 2, 5), 0, 0));

    pn2nd.add(gras, BorderLayout.CENTER);

    pn2nd.add(pnside, BorderLayout.WEST);
    showpnprice = true;
    pnside.add(pnshower, BorderLayout.CENTER);
    pnshower.add(pnprice, BorderLayout.CENTER);
    pnprice.add(jLabel8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(2, 2, 2, 2), 0, 0));
    pnclientdetails.add(lblWorkTel, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(lblMobile, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(lblDate, new GridBagConstraints(4, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnclientdetails.add(lblDesignID,
                        new GridBagConstraints(4, 2, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(2, 5, 2, 5), 0, 0));
    pnmindetails.add(lblDesignID2, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnmindetails.add(lblTitle2, new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 5, 2, 5), 0, 0));
    pnoverlap.add(ckFullBottom,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 10, 2, 5), 0, 0));
    pnpreferences.add(jPanel1,   new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 10, 0));
    jPanel1.add(ckStretching,  new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(ckPasting,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    pnpreferences.add(jPanel2, new GridBagConstraints(5, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(2, 2, 2, 2), 0, 0));
    jPanel2.add(btnPrefs, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(2, 2, 2, 2), 0, 0));
    jPanel2.add(btnUpdateMethodPrefs,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnprice.add(txtDelDate, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(txtTotalOne, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(txtVATOne, new GridBagConstraints(1, 4, 2, 1, 1.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(txtDiscountOne, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(txtPriceOne, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(10, 2, 2, 2), 0, 0));
    pnprice.add(jLabel25, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(10, 2, 2, 2), 0, 0));
    pnprice.add(txtTotalNo, new GridBagConstraints(1, 7, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(txtNoOrdered, new GridBagConstraints(1, 6, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(10, 2, 2, 2), 0, 0));
    pnprice.add(lblOtherVAT, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 10, 2, 2), 0, 0));
    pnprice.add(jLabel24, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(2, 10, 2, 2), 0, 0));
    pnprice.add(jLabel23, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(jLabel12, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(10, 2, 2, 2), 0, 0));
    pnprice.add(jLabel3, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(jLabel2, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(10, 2, 2, 2), 0, 0));
    pnprice.add(jLabel13, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(5, 2, 0, 0), 0, 0));
    pnprice.add(jLabel11, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(2, 10, 2, 2), 0, 0));
    pnprice.add(jLabel10, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(2, 10, 2, 2), 0, 0));
    pnprice.add(jLabel9, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(10, 2, 2, 2), 0, 0));
    pnprice.add(txtTotalAll, new GridBagConstraints(1, 12, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(10, 2, 2, 2), 0, 0));
    pnprice.add(txtVATOther, new GridBagConstraints(1, 11, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(txtDiscountOther, new GridBagConstraints(1, 10, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(txtPriceOther, new GridBagConstraints(1, 9, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
    pnprice.add(jPanel3, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(btnPrevDelDate, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(btnNextDelDate, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    pnprice.add(jPanel4, new GridBagConstraints(0, 14, 2, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
  }

  void btnpanelshow_actionPerformed(ActionEvent e) {
    remove(pnclientdetails);
    lblClient.setText( (String) cbxClient.getSelectedItem());
    lblArtist.setText( (String) cbxArtist.getSelectedItem());
    add(pnmindetails, BorderLayout.NORTH);
    pnmindetails.updateUI();
  }

  void btnpanelshow1_actionPerformed(ActionEvent e) {
    remove(pnmindetails);
    add(pnclientdetails, BorderLayout.NORTH);
    pnclientdetails.updateUI();
  }

  void btnshowpref_actionPerformed(ActionEvent e) {
    if (showpref == false) {
      pn2nd.add(pnpreferences, BorderLayout.NORTH);
      showpref = true;
      btnshowpref.setIcon(new ImageIcon("images/DownSwitch.gif"));
      btnshowpref2.setIcon(new ImageIcon("images/DownSwitch.gif"));
      pnpreferences.updateUI();
    }
    else {
      pn2nd.remove(pnpreferences);
      showpref = false;
      btnshowpref.setIcon(new ImageIcon("images/UpSwitch.gif"));
      btnshowpref2.setIcon(new ImageIcon("images/UpSwitch.gif"));
      pn2nd.repaint();
      pn2nd.updateUI();
    }
  }

  public class GraphicsScrollPane
      extends JScrollPane {
    public GraphicsScrollPane() {
      Graphic graphic = new Graphic();
      setViewportView(graphic);
    }

    public Dimension getPreferredSize() {
      return new Dimension(200, 200);
    }

    public Dimension getMinimumSize() {
      return new Dimension(50, 50);
    }
  }

  public class Graphic
      extends JPanel {
    public Graphic() {
      designer.addShowPriceAction(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          designer.refreshDrawingSize();
          if (showpnprice == false) {
            showpnprice = true;
            designer.setShowPriceImage(new ImageIcon("images/Links.gif"));
            pn2nd.add(pnside, BorderLayout.WEST);
            pn2nd.updateUI();
          }
          else {
            showpnprice = false;
            designer.setShowPriceImage(new ImageIcon("images/Regs.gif"));
            pn2nd.remove(pnside);
            pn2nd.updateUI();
          }
        }
      });

      JScrollPane scroller = new JScrollPane(designer.getDesignTable());
      JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, designer,
                                            scroller);
      splitPane.setContinuousLayout(true);
      splitPane.setDividerLocation(400);
      splitPane.setOneTouchExpandable(true);
      setLayout(new BorderLayout());
      add(new JScrollPane(splitPane), BorderLayout.CENTER);
    }
  }

  private void refreshClients() {
    DesignDets.updateClientMaps(db_conn);
    try {
      Iterator it = DesignDets.getClientIterator();
      cbxClient.removeAllItems();
      while (it.hasNext()) {
        cbxClient.addItem( (String) it.next());
      }
    }
    catch (NullPointerException e) {
      // doen niks
    }
    try {
      DesignDets design = (DesignDets) designList.get(curDesignIndex);
      cbxClient.setSelectedItem(design.getClient());
    }
    catch (IndexOutOfBoundsException e) {
      cbxClient.setSelectedIndex( -1);
    }
  }

  private void refreshArtists() {
    DesignDets.updateArtistMaps(db_conn);
    try {
      Iterator it = DesignDets.getArtistIterator();
      cbxArtist.removeAllItems();
      while (it.hasNext()) {
        cbxArtist.addItem( (String) it.next());
      }
    }
    catch (NullPointerException e) {
      // doen niks
    }
    try {
      DesignDets design = (DesignDets) designList.get(curDesignIndex);
      cbxArtist.setSelectedItem(design.getArtist());
    }
    catch (IndexOutOfBoundsException e) {
      cbxArtist.setSelectedIndex( -1);
    }
  }

  public boolean addItem(int itemCode) {
    try {
      designer.addDesignItem(itemCode, (DesignDets)designList.get(curDesignIndex));
      return true;
    }
    catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  public void insertItem(int itemCode) {
    try {
      designer.insertDesignItem(itemCode);
    }
    catch (IndexOutOfBoundsException e) {
      // doen niks
    }
  }

  public void deleteItem() {
    designer.deleteDesignItem();
  }

  private void updateClientTelNommers(int clientID) {
    try {
      String[] nommers = db_conn.getTelefoonNommers(clientID);
      lblHomeTel.setText(nommers[0]);
      lblWorkTel.setText(nommers[1]);
      lblMobile.setText(nommers[2]);
    }
    catch (NullPointerException e) {
      // doen niks
    }
  }

  public void setCurrentDesign(int index) {
    curDesignIndex = index;
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      cbxClient.setSelectedItem(dets.getClient());
      cbxArtist.setSelectedItem(dets.getArtist());
      txtTitle.setText(dets.getTitle());
      cbxStatus.setSelectedItem(dets.getStatus());
      lblDesignID.setText(String.valueOf(dets.getDesignID()));
      lblDate.setText(Utils.getDatumStr(dets.getDate()));

      lblClient.setText( (String) cbxClient.getSelectedItem());
      lblArtist.setText( (String) cbxArtist.getSelectedItem());
      lblStatus.setText( (String) cbxStatus.getSelectedItem());
      lblDate2.setText(lblDate.getText());
      lblDesignID2.setText(lblDesignID.getText());
      lblTitle2.setText(txtTitle.getText());

      updateClientTelNommers(dets.getClientID());
      cbxClient.setEnabled(true);
      cbxArtist.setEnabled(true);
      txtTitle.setEnabled(true);
      cbxStatus.setEnabled(true);

      designer.setActive(true);
      designer.setItemList(dets.getItemList());

      // Preferences panel
      rbnFullSize.setEnabled(true);
      rbnFixedBorder.setEnabled(true);
      rbnCalcOverlap.setEnabled(true);

      ckFullBottom.setEnabled(true);
      //ckWeighting.setEnabled(true);
      ckStretching.setEnabled(true);
      ckPasting.setEnabled(true);

      txtFBWithSlip.setEnabled(true);
      txtFBNoSlip.setEnabled(true);
      txtOLAdj.setEnabled(true);
      txtMaterial.setEnabled(true);
      txtLabour.setEnabled(true);

      btnPrefs.setEnabled(true);
      btnUpdateMethodPrefs.setEnabled(true);
      updatePreferencePanel();

    }
    catch (IndexOutOfBoundsException e) {
      cbxClient.setSelectedIndex( -1);
      cbxArtist.setSelectedIndex( -1);
      txtTitle.setText("");
      cbxStatus.setSelectedIndex( -1);
      lblDesignID.setText("");
      lblDate.setText("");

      lblClient.setText("");
      lblArtist.setText("");
      lblStatus.setText("");
      lblDate2.setText("");
      lblDesignID2.setText("");
      lblTitle2.setText("");

      updateClientTelNommers( -1);
      cbxClient.setEnabled(false);
      cbxArtist.setEnabled(false);
      txtTitle.setEnabled(false);
      cbxStatus.setEnabled(false);

      designer.setActive(false);
      designer.setItemList(null);

      // Preferences panel
      rbnFullSize.setEnabled(false);
      rbnFixedBorder.setEnabled(false);
      rbnCalcOverlap.setEnabled(false);

      ckFullBottom.setEnabled(false);
      //ckWeighting.setEnabled(false);
      ckStretching.setEnabled(false);
      ckPasting.setEnabled(false);

      txtFBWithSlip.setEnabled(false);
      txtFBNoSlip.setEnabled(false);
      txtOLAdj.setEnabled(false);
      txtMaterial.setEnabled(false);
      txtLabour.setEnabled(false);

      btnUpdateMethodPrefs.setEnabled(false);
      btnPrefs.setEnabled(false);
    }
    designer.repaint();
  }

  private void setDesignValues() {
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      dets.setClient( (String) cbxClient.getSelectedItem());
      dets.setArtist( (String) cbxArtist.getSelectedItem());
      dets.setTitle(txtTitle.getText());
      dets.setStatus( (String) cbxStatus.getSelectedItem());
      dets.setItemList(designer.getItemList());
      setPreferenceObject();
    }
    catch (NullPointerException e) {
      // ignoreer
    }
    catch (IndexOutOfBoundsException e) {
      // ignoreer
    }
  }

  private void updatePreferencePanel() {
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      MethodPrefDets methodPrefs = dets.getMethodPrefs();
      BusPrefDets busPrefs = dets.getBusPrefs();

      if (methodPrefs.getMethodType() == MethodPrefDets.BORDER_FULL) {
        rbnFullSize.setSelected(true);
      }
      else if (methodPrefs.getMethodType() == MethodPrefDets.BORDER_FIXED) {
        rbnFixedBorder.setSelected(true);
      }
      else {
        rbnCalcOverlap.setSelected(true);

      }
      ckFullBottom.setSelected(methodPrefs.getFullBottomsBool());
      //ckWeighting.setSelected(dets.getUseWeighting());
      ckStretching.setSelected(dets.getUseStretching());
      ckPasting.setSelected(dets.getUsePasting());

      txtFBWithSlip.setText(Utils.getCurrencyFormat(methodPrefs.
          getFBOverlapWithSlip()));
      txtFBNoSlip.setText(Utils.getCurrencyFormat(methodPrefs.
                                                  getFBOverlapNoSlip()));
      txtOLAdj.setText(Utils.getCurrencyFormat(methodPrefs.getOverlapAdjFact()));
      txtMaterial.setText(Utils.getCurrencyFormat(busPrefs.getSundriesBasic()));
      txtLabour.setText(Utils.getCurrencyFormat(busPrefs.getsundriesLabour()));

      updatePricePanel();
    }
    catch (IndexOutOfBoundsException e) {
      // ignoreer
    }
  }

  private void updatePricePanel() {
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      MethodPrefDets methodPrefs = dets.getMethodPrefs();
      BusPrefDets businessPrefs = dets.getBusPrefs();

      txtDelDate.setText(Utils.getDatumStr(dets.getDeliveryDate()));
      txtPriceOne.setText(Utils.getCurrencyFormat(dets.getPriceOne()));
      txtDiscountOne.setText(Utils.getCurrencyFormat(dets.getDiscountOne()));
      float VAT = dets.getBusPrefs().getVATPerc();

      float totalOne = 0;
      if (businessPrefs.getVATReg().equals("Yes")) {
        totalOne = dets.getPriceOne() * (1 + VAT / 100) *
            (100 - dets.getDiscountOne()) / 100;
        txtVATOne.setText(Utils.getCurrencyFormat(VAT));
      }
      else {
        totalOne = dets.getPriceOne() *
            (100 - dets.getDiscountOne()) / 100;
        txtVATOne.setText("0.0");
      }

      txtTotalOne.setText(Utils.getCurrencyFormat(totalOne));
      txtNoOrdered.setText(String.valueOf(dets.getNoOrdered()));
      float totalNo = totalOne * dets.getNoOrdered();
      txtTotalNo.setText(Utils.getCurrencyFormat(totalNo));
      txtPriceOther.setText(Utils.getCurrencyFormat(dets.getPriceOther()));
      txtDiscountOther.setText(Utils.getCurrencyFormat(dets.getDiscountOther()));

      float totalAll = 0;
      if (businessPrefs.getVATReg().equals("Yes")) {
        totalAll = totalNo +
            dets.getPriceOther() * (1 + VAT / 100) *
            (100 - dets.getDiscountOther()) /
            100;
        txtVATOther.setText(Utils.getCurrencyFormat(VAT));
      }
      else {
        totalAll = dets.getPriceOther() * (100 - dets.getDiscountOther()) /
            100 + totalNo;
        txtVATOther.setText("0.0");
      }
      txtTotalAll.setText(Utils.getCurrencyFormat(totalAll));

    }
    catch (IndexOutOfBoundsException e) {
      // ignoreer
    }
  }

  private void setPreferenceObject() {
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      MethodPrefDets methodPrefs = dets.getMethodPrefs();
      BusPrefDets busPrefs = dets.getBusPrefs();

      if (rbnFullSize.isSelected()) {
        methodPrefs.setMethodType(MethodPrefDets.BORDER_FULL);
      }
      else if (rbnFixedBorder.isSelected()) {
        methodPrefs.setMethodType(MethodPrefDets.BORDER_FIXED);
      }
      else {
        methodPrefs.setMethodType(MethodPrefDets.BORDER_CALC);

      }
      methodPrefs.setFullBottoms(ckFullBottom.isSelected());
      //dets.setUseWeighting(ckWeighting.isSelected());
      dets.setUseStretching(ckStretching.isSelected());
      dets.setUsePasting(ckPasting.isSelected());

      try {
        methodPrefs.setFBOverlapWithSlip( (new Float(txtFBWithSlip.getText())).
                                         floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                                      "Invalid fixed border was specified",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }

      try {
        methodPrefs.setFBOverlapNoSlip( (new Float(txtFBNoSlip.getText())).
                                       floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                                      "Invalid fixed border was specified",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }

      try {
        methodPrefs.setOverlapAdjFact( (new Float(txtOLAdj.getText())).
                                      floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "Invalid overlap adjustment was specified", "Error",
            JOptionPane.ERROR_MESSAGE);
      }

      try {
        busPrefs.setSundriesBasic( (new Float(txtMaterial.getText())).floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "Invalid material adjustment was specified", "Error",
            JOptionPane.ERROR_MESSAGE);
      }

      try {
        busPrefs.setSundriesLabour((new Float(txtLabour.getText())).floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                                      "Invalid labour adjustment was specified",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }

      setPriceDetails();
    }
    catch (IndexOutOfBoundsException e) {
      // doen niks
    }
  }

  private void setPriceDetails() {
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);

      dets.setDeliveryDate(Utils.getDatumInt(txtDelDate.getText()));

      try {
        dets.setPriceOne( (new Float(txtPriceOne.getText())).floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error transforming price value",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }

      try {
        dets.setDiscountOne( (new Float(txtDiscountOne.getText())).floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid discount value", "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }

      try {
        dets.setNoOrdered( (new Float(txtNoOrdered.getText())).intValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid no orderer was specified",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }

      try {
        dets.setPriceOther( (new Float(txtPriceOther.getText())).floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid other items price value",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }

      try {
        dets.setDiscountOther( (new Float(txtDiscountOther.getText())).
                              floatValue());
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
                                      "Invalid other items discount value",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }

    }
    catch (IndexOutOfBoundsException e) {
      // doen niks
    }
  }

  private void btnPrefs_actionPerformed(ActionEvent e) {
    try {
      setPreferenceObject();
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      MethodPrefDets methodPrefs = dets.getMethodPrefs();
      MethodPreferences dialog = new MethodPreferences(methodPrefs);
      methodPrefs = dialog.getModifiedPrefs();
      dets.setMethodPrefs(methodPrefs);
      updatePreferencePanel();

      btnCalculate2_actionPerformed();
      designer.updateTableData();

    }
    catch (IndexOutOfBoundsException x) {
      // doen niks
    }
  }

  public void nextDesign() {
    if (curDesignIndex < designList.size() - 1) {
      setDesignValues();
      setCurrentDesign(++curDesignIndex);
    }
  }

  public void previousDesign() {
    if (curDesignIndex > 0) {
      setDesignValues();
      setCurrentDesign(--curDesignIndex);
    }
  }

  public ArrayList getDesignList() {
    return designList;
  }

  public int getCurrentDesignIndex() {
    return curDesignIndex;
  }

  public void saveDesign() {
    try {
      setDesignValues();
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      db_conn.saveDesign(dets);
      lblDesignID.setText(String.valueOf(dets.getDesignID()));
      lblDesignID2.setText(String.valueOf(dets.getDesignID()));
    }
    catch (IndexOutOfBoundsException e) {
      // doen niks
    }
  }

  public void exportDesign() {
    String filename = null;
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      filename = dets.getTitle() + ".jpg";

    } catch (Exception e) {
      return;
    }

    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileFilter() {
      public boolean accept(File f) {
        String name = f.getName().toLowerCase();
        if (name.indexOf(".jpg") > -1 || f.isDirectory())
          return true;
        else
          return false;
      }

      public String getDescription() {
        return "JPEG Image files";
      }
    });

    if (UserSettings2.LAST_EXPORT_PATH != null && !UserSettings2.LAST_EXPORT_PATH.equals("")) {
      chooser.setSelectedFile(new File(UserSettings2.LAST_EXPORT_PATH + "/" + filename));
    } else {
      chooser.setSelectedFile(new File(filename));
    }

    int returnVal = chooser.showSaveDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      try {
        File file = chooser.getSelectedFile();
        if (file.canRead()) {
          String mes = "The file already exist.  Do you want to overwrite it?";
          int res = JOptionPane.showConfirmDialog(this, mes, "Export Designs", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if (res != JOptionPane.YES_OPTION) {
            return;
          }
        }

        UserSettings2.LAST_EXPORT_PATH = file.getAbsolutePath();
        BufferedImage prent = new BufferedImage(designer.getWidth(), designer.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = prent.createGraphics();
        designer.paintComponent(g);

        g.setColor(UserSettings.DEF_COLOR);
        Font font = new Font("Times New Roman", Font.BOLD, 12);
        FontRenderContext frc = g.getFontRenderContext();
        TextLayout layout = new TextLayout(file.getName(), font, frc);
        Rectangle2D bounds = layout.getBounds();
        double y = bounds.getHeight();
        double x = prent.getWidth() / 2 - bounds.getWidth() / 2;
        layout.draw(g, (float)x, (float)y);

        font = new Font("Times New Roman", Font.PLAIN, 10);
        layout = new TextLayout("www.rs-f.com", font, frc);
        bounds = layout.getBounds();
        y = prent.getHeight() - 1.5 * bounds.getHeight();
        x = prent.getWidth() / 2 - bounds.getWidth() / 2;
        layout.draw(g, (float)x, (float)y);

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(prent);
        param.setQuality(1.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(prent);
        out.flush();
        out.close();

      } catch (Exception x) {
        JOptionPane.showMessageDialog(this, "The file specified cannot be read", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }


  public void deleteDesigns(ArrayList deleteList) {
    if (deleteList != null && deleteList.size() > 0) {
      String mes = "Are you sure you want to delete selected designs?";
      int delete = JOptionPane.showConfirmDialog(this, mes, "Delete Designs",
                                                 JOptionPane.YES_NO_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE);
      if (delete == JOptionPane.YES_OPTION) {
        db_conn.deleteDesigns(deleteList);
        closeDesigns(deleteList, false);
      }
    }
  }

  public void checkModifiedDesigns() {
    boolean modified = false;
    Iterator it = designList.iterator();
    while (it.hasNext()) {
      DesignDets design = (DesignDets) it.next();
      if (design.isModified()) {
        modified = true;
        break;
      }
    }

    if (modified) {
      String mes = "Some of the open designs was modifed.  Do " +
          "you want to save the changes?";
      int save = JOptionPane.showConfirmDialog(this, mes, "Save Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (save == JOptionPane.YES_OPTION) {
        it = designList.iterator();
        while (it.hasNext()) {
          DesignDets design = (DesignDets) it.next();
          if (design.isModified()) {
            db_conn.saveDesign(design);
          }
        }
      }
    }
  }

  public void closeDesigns(ArrayList closeList, boolean saveChanges) {
    HashMap designMap = new HashMap();
    Iterator it = designList.iterator();
    while (it.hasNext()) {
      DesignDets design = (DesignDets) it.next();
      designMap.put(new Integer(design.getDesignID()), design);
    }

    if (closeList != null) {
      it = closeList.iterator();
      while (it.hasNext()) {
        int code = ( (Integer) it.next()).intValue();
        DesignDets design = (DesignDets) designMap.get(new Integer(code));
        if (saveChanges && design.isModified()) {
          String mes = "Do you want to save changes to the design '";
          mes += design.getDesignID() + ": " + design.getTitle() + "'?";
          int save = JOptionPane.showConfirmDialog(this, mes, "Save Changes",
              JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
          if (save == JOptionPane.YES_OPTION) {
            db_conn.saveDesign(design);
          }
        }
        designList.remove(design);
      }
    }
    setCurrentDesign(0);
  }

  public void saveAllDesigns() {
    try {
      setDesignValues();
      db_conn.saveDesigns(designList);
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      lblDesignID.setText(String.valueOf(dets.getDesignID()));
      lblDesignID2.setText(String.valueOf(dets.getDesignID()));
    }
    catch (IndexOutOfBoundsException e) {
      // doen niks
    }
  }

  public void openDesigns(ArrayList openList) {
    ArrayList newList = db_conn.getDesigns(openList);
    TreeSet treeSet = new TreeSet(newList);
    treeSet.addAll(designList);
    designList = new ArrayList(treeSet);
    if (designList.size() > 0) {
      setCurrentDesign(0);
    }
  }

  public void createNewDesign() {
    // set eers values van huidige design
    setDesignValues();

    try {
      int i = 1;
      Iterator it = designList.iterator();
      while (it.hasNext()) {
        DesignDets designDets = (DesignDets) it.next();
        try {
          if (designDets.getTitle().substring(0, 10).equals("New Design")) {
            i++;
          }
        }
        catch (StringIndexOutOfBoundsException e) {
          //doen niks want string is te kort
        }
      }

      BusPrefDets busPrefs = db_conn.getBusPreferences("Latest");
      MethodPrefDets methodPrefs = db_conn.getMethodPreferences("Latest");
      String title = "New Design " + i;
      DesignDets designDets = new DesignDets(busPrefs, methodPrefs, title, db_conn);
      designDets.setDate(Utils.getCurrentDate());
      designDets.setStatus(DESIGN_STATUS[DES_STATUS_QUOT]);
      designDets.setDeliveryDate(Utils.addDays(Utils.getCurrentDate(), methodPrefs.getDateCount()));
      designList.add(designDets);
      setCurrentDesign(designList.size() - 1);
      addItem(Designer.ITEM_ARTWORK);
    }
    catch (NullPointerException e) {
      // doen niks
    }
  }

  void cbxClient_actionPerformed(ActionEvent e) {
    try {
      int clientID = DesignDets.getClientIDFromName( (String) cbxClient.
          getSelectedItem());
      updateClientTelNommers(clientID);
    }
    catch (NullPointerException x) {
      updateClientTelNommers( -1);
    }
  }

  void btnClients_actionPerformed(ActionEvent e) {
    try {
      DesignDets desser = (DesignDets) designList.get(curDesignIndex);
      int clientID = desser.getClientID();
      Clients2 clients = new Clients2(db_conn, clientID);
      refreshClients();
      desser.setClientID(clients.getSelectedClient());
      cbxClient.setSelectedItem(desser.getClient());
    }
    catch (IndexOutOfBoundsException x) {
      Clients2 clients = new Clients2(db_conn);
    }
    catch (NullPointerException x) {
      // doen fokkol
    }
  }

  void btnArtists_actionPerformed(ActionEvent e) {
    try {
      DesignDets desser = (DesignDets) designList.get(curDesignIndex);
      int artistID = desser.getArtistID();
      Artists2 art = new Artists2(db_conn, artistID);
      refreshArtists();
      desser.setArtistID(art.getSelectedArtist());
      cbxArtist.setSelectedItem(desser.getArtist());
    }
    catch (IndexOutOfBoundsException x) {
      Artists2 art = new Artists2(db_conn);
    }
    catch (NullPointerException x) {
      // doen fokkol
    }
  }

  public void showEditDesignSettings() {
    DesignSettings des = new DesignSettings();
    designer.repaint();
  }

      /*------------------------------ Design Calcs --------------------------------*/

  private void calcItemSizes() {
    setPreferenceObject();
    DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
    BusPrefDets busPrefs = designDets.getBusPrefs();
    MethodPrefDets methodPrefs = designDets.getMethodPrefs();
    LinkedList itemList = designer.getItemList();

    ListIterator it = itemList.listIterator();
    while (it.hasNext()) {
      DesignItem2 item = (DesignItem2) it.next();
      item.calcItemSize(this, itemList, it.nextIndex(), methodPrefs);
    }

    it = itemList.listIterator();
    while (it.hasNext()) {
      DesignItem2 item = (DesignItem2) it.next();
      item.checkItemSize(itemList, it.nextIndex(), methodPrefs);
    }
  }

  private void calcPrices() {
    DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
    BusPrefDets busPrefs = designDets.getBusPrefs();
    MethodPrefDets methodPrefs = designDets.getMethodPrefs();
    LinkedList itemList = designer.getItemList();

    double totalPrice = 0;
    ListIterator it = itemList.listIterator();
    while (it.hasNext()) {
      DesignItem2 item = (DesignItem2) it.next();
      item.calcItemPrice(methodPrefs, busPrefs, ckStretching.isSelected(), ckPasting.isSelected());
      totalPrice += item.getDesignPrice();
    }

    totalPrice  += busPrefs.getSundriesBasic() + busPrefs.getsundriesLabour();

    totalPrice *= BusPrefDets.getMarkupDiscount();
    if (busPrefs.getVATOwnItems().equals("Yes")) {
      totalPrice *= (1 + busPrefs.getVATPerc() / 100);
    }
    designDets.setPriceOne( (float) totalPrice);

    int date = Utils.getCurrentDate();
    designDets.setDeliveryDate(Utils.addDays(date, methodPrefs.getSunDays()));
    txtDelDate.setText(Utils.getDatumStr(designDets.getDeliveryDate()));

    if (UserSettings.DUMP_DESIGN_CALCS) {
      try {
        String file = "designdumps/" + designDets.getDesignID() + ". " + designDets.getTitle() + ".txt";
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        out.print("Design Code: " + designDets.getDesignID());
        out.print("       Title: " + designDets.getTitle());
        out.print("       Date: " + designDets.getDate());
        out.println();
        out.println();
        out.print(addStringGap("Item"));
        out.print(addStringGap("Item Code"));
        out.print(addStringGap("Own Code"));
        out.print(addStringGap("Height"));
        out.print(addStringGap("Width"));
        out.print(addStringGap("Top Face"));
        out.print(addStringGap("Bottom Face"));
        out.print(addStringGap("Left Face"));
        out.print(addStringGap("Right Face"));
        out.println();

        it = itemList.listIterator();
        while (it.hasNext()) {
          DesignItem2 item = (DesignItem2) it.next();
          String line = addStringGap(item.getType());
          line += addStringGap(item.getItemCode()) + addStringGap(item.getOwnCode());

          if (item.getDesignType() == Designer.ITEM_FRAME) {
            double length = item.getDesignHeight();
            length -= item.getMaterialDets().getDefaultValuesWithInMaterialDets().getWidth() + item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate();
            line += addStringGap(Utils.getCurrencyFormat(length));
            double width = item.getDesignWidth();
            width -= item.getMaterialDets().getDefaultValuesWithInMaterialDets().getWidth() + item.getMaterialDets().getDefaultValuesWithInMaterialDets().getRebate();
            line += addStringGap(Utils.getCurrencyFormat(width));
          }
          else {
            line += addStringGap(Utils.getCurrencyFormat(item.getDesignHeight()));
            line += addStringGap(Utils.getCurrencyFormat(item.getDesignWidth()));
          }

          line += addStringGap(Utils.getCurrencyFormat(item.getTopGap()));
          line += addStringGap(Utils.getCurrencyFormat(item.getBottomGap()));
          line += addStringGap(Utils.getCurrencyFormat(item.getLeftGap()));
          line += addStringGap(Utils.getCurrencyFormat(item.getRightGap()));
          out.println(line);
        }
        out.println();

        out.println();
        out.print(addStringGap("Item"));
        out.print(addStringGap("Item Code"));
        out.print(addStringGap("Top Border"));
        out.print(addStringGap("Bottom Border"));
        out.print(addStringGap("Left Border"));
        out.print(addStringGap("Right Border"));
        out.print(addStringGap("Cost"));
        out.print(addStringGap("Price"));
        out.println();

        it = itemList.listIterator();
        while (it.hasNext()) {
          DesignItem2 item = (DesignItem2) it.next();
          String line = addStringGap(item.getType());
          line += addStringGap(item.getItemCode());
          line += addStringGap(Utils.getCurrencyFormat(item.getDesignTopGap()));
          line += addStringGap(Utils.getCurrencyFormat(item.getDesignBottomGap()));
          line += addStringGap(Utils.getCurrencyFormat(item.getDesignLeftGap()));
          line += addStringGap(Utils.getCurrencyFormat(item.getDesignRightGap()));

          try {
            line += addStringGap(Utils.getCurrencyFormat(item.getMaterialDets().getDefaultValues(item.getDefaultSupplier()).getCost()));
            float price = busPrefs.getTotalBoardMarkup() * item.getMaterialDets().getDefaultValues(item.getDefaultSupplier()).getCost();
            line += addStringGap(Utils.getCurrencyFormat(price));
          }
          catch (NullPointerException e) {
            // doen niks
          }
          out.println(line);
        }
        out.println();

        out.close();
      }
      catch (IOException e) {
        System.err.println("write error");
      }
    }
  }

  private String addStringGap(String s) {
    int length = 15;
    try {
      int x = length - s.length();
      for (int i = 0; i < x; i++) {
        s += " ";
      }
      return s;
    }
    catch (NullPointerException e) {
      String y = "";
      for (int i = 0; i < length; i++) {
        y += " ";
      }
      return y;
    }
  }

  public void extractStock() {
    if (db_conn.isStockTakeInProgress()) {
      String mes = "A Stock Take is in progress.  The optimizer cannot run.";
      JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }


    try {
      if (UserSettings.OPT_USE) {
        if ( ( (DesignDets) designList.get(curDesignIndex)).getDesignID() == -1) {
          String mes = "The design needs to be saved before the stock selection can be made.";
          JOptionPane.showMessageDialog(this, mes, "Warning", JOptionPane.WARNING_MESSAGE);

        } else {
          int designID = ((DesignDets)designList.get(curDesignIndex)).getDesignID();

          if (db_conn.isDesignsAllocated(designID)) {
            String mes = "There is already stock allocated to this design.  ";
            mes += "Are you sure want to run the optimizer again?";
            int i = JOptionPane.showConfirmDialog(this, mes, "Optimizer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (i == JOptionPane.NO_OPTION) {
              artof.stock.designs.DesignDialog d = new artof.stock.designs.DesignDialog(allocationList, ((DesignDets)designList.get(curDesignIndex)).getDesignID());
              return;
            }
          }

          if (db_conn.canDeleteOffcuts(designID)) {
            db_conn.removeStockDesignAllocation(designID);
            db_conn.removeStockOffCutAllocation(designID);
            ProgressDialog dialog = new ProgressDialog("Optimizing stock selection");
            StockExtractor createBackup = new StockExtractor(dialog, designer.getItemList());
            dialog.setVisible(true);

          } else {
            String mes = "Updates to stock cannot be made because the offcuts of this design are already used in other designs.";
            JOptionPane.showMessageDialog(this, mes, "Warning", JOptionPane.WARNING_MESSAGE);
          }

          //if (UserSettings.OPT_SHOW_DIALOG) {
          artof.stock.designs.DesignDialog d = new artof.stock.designs.DesignDialog(allocationList, ((DesignDets)designList.get(curDesignIndex)).getDesignID());
          //}
        }
      }

    } catch (IndexOutOfBoundsException e) {
      // doen fokkol
    }
  }

  class StockExtractor extends Thread {
    private ProgressDialog dialog;
    private LinkedList itemList;

    public StockExtractor(ProgressDialog dialog, LinkedList itemList) {
      this.dialog = dialog;
      this.itemList = itemList;
      start();
    }

    public void run() {
      try {
        int noOfItems = itemList.size();
        dialog.setLength(100 * noOfItems);

        HashMap itemTypeMap = new HashMap();
        HashMap itemSideMap = new HashMap();
        ListIterator it = itemList.listIterator();
        while (it.hasNext()) {
          DesignItem2 item = (DesignItem2) it.next();
          ArrayList sideList = (ArrayList) itemSideMap.get(item.getItemCode());
          if (sideList == null) {
            sideList = new ArrayList();
            itemSideMap.put(item.getItemCode(), sideList);

            if (item.getDesignType() == Designer.ITEM_FRAME ||
                item.getDesignType() == Designer.ITEM_FOIL ||
                item.getDesignType() == Designer.ITEM_SLIP) {
              itemTypeMap.put(item.getItemCode(), new Boolean(true));
            }
            else if (item.getDesignType() == Designer.ITEM_BOARD ||
                     item.getDesignType() == Designer.ITEM_BACK ||
                     item.getDesignType() == Designer.ITEM_GLASS ||
                     item.getDesignType() == Designer.ITEM_BOX) {
              itemTypeMap.put(item.getItemCode(), new Boolean(false));
            }
          }

          if (item.getDesignType() == Designer.ITEM_FRAME ||
              item.getDesignType() == Designer.ITEM_FOIL ||
              item.getDesignType() == Designer.ITEM_SLIP) {
            float kortKant = (float) Math.min(item.getDesignHeight(),
                                              item.getDesignWidth());
            float langKant = (float) Math.max(item.getDesignHeight(),
                                              item.getDesignWidth());
            int kortSyIndex = 0;
            int langSyIndex = 0;
            for (int i = 0; i < sideList.size(); i++) {
              float side = ( (Float) sideList.get(i)).floatValue();
              if (side < kortKant) {
                kortSyIndex = i;

              }
              if (side < langKant) {
                langSyIndex = i;
              }
            }

            sideList.add(langSyIndex, new Float(langKant));
            sideList.add(langSyIndex, new Float(langKant));
            sideList.add(kortSyIndex, new Float(kortKant));
            sideList.add(kortSyIndex, new Float(kortKant));

          }
          else if (item.getDesignType() == Designer.ITEM_BOARD ||
                   item.getDesignType() == Designer.ITEM_BACK ||
                   item.getDesignType() == Designer.ITEM_GLASS ||
                   item.getDesignType() == Designer.ITEM_BOX) {
            float kortKant = (float) Math.min(item.getDesignHeight(),
                                              item.getDesignWidth());
            float langKant = (float) Math.max(item.getDesignHeight(),
                                              item.getDesignWidth());

            int index = 0;
            for (int i = 0; i < sideList.size(); i += 2) {
              float side = ( (Float) sideList.get(i)).floatValue();
              if (side < kortKant) {
                index = i;
              }
            }

            sideList.add(index, new Float(langKant));
            sideList.add(index, new Float(kortKant));
          }
        }

        allocationList = new ArrayList();
        int count = 0;
        Iterator keys = itemSideMap.keySet().iterator();
        while (keys.hasNext()) {
          String itemCode = (String) keys.next();
          ArrayList sideList = (ArrayList) itemSideMap.get(itemCode);

          boolean suc;
          try {
            if (((Boolean)itemTypeMap.get(itemCode)).booleanValue()) {
              suc = extractStockFrames(dialog, 100 * count++, itemCode, sideList, ((DesignDets)designList.get(curDesignIndex)).getDesignID());
            }
            else {
              suc = extractStockBoards(dialog, 100 * count++, itemCode, sideList, ((DesignDets)designList.get(curDesignIndex)).getDesignID());

            }
          }
          catch (NullPointerException e) {
            continue;
          }

          if (!suc && UserSettings.OPT_WARNINGS) {
            String mes = "There is not enough stock available of Item Code " +
                itemCode;
            javax.swing.JOptionPane.showMessageDialog(dialog, mes, "Allocate",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
          }
        }
        dialog.hide();

      }
      catch (Exception x) {
        dialog.hide();
        String mes = "An error occured during optimization.";
        JOptionPane.showMessageDialog(dialog, mes, "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public void updateCurrentDesignOffCuts() {
    try {
      int designID = ( (DesignDets) designList.get(curDesignIndex)).getDesignID();
      if (designID == -1) {
        throw new IndexOutOfBoundsException();
      }

      if (db_conn.canDeleteOffcuts(designID)) {
        // donner nou al die offcuts in die db in
        /*db_conn.removeStockOffCutAllocation(designID);
        ArrayList selectedStock = db_conn.getStockAlreadyAllocatedToDesign(
            designID);
        ArrayList offcutList = getOffCutList(selectedStock);
        db_conn.updateStockItems(offcutList);
*/
        artof.stock.offcuts.OffCutDialog d = new artof.stock.offcuts.
            OffCutDialog(designID);

      }
      else {
        String mes = "Updates to stock cannot be made because the offcuts of this design are already used in other designs.";
        JOptionPane.showMessageDialog(this, mes, "Warning",
                                      JOptionPane.WARNING_MESSAGE);
      }

    }
    catch (IndexOutOfBoundsException a) {
      artof.stock.offcuts.OffCutDialog d = new artof.stock.offcuts.OffCutDialog();
    }
  }

  public boolean extractStockBoards(artof.dialogs.ProgressDialog dialog,
                                    int progressBase, String itemCode,
                                    ArrayList sideList, int designID) {
    if (designID == -1) {
      return false;
    }

    ArrayList stockList = db_conn.getCurrentStockPerItemCode(itemCode);

    float dialogFactor = 100.f / stockList.size();
    int count = 0;

    ArrayList usedStockList = new ArrayList();
    for (int i = 0; i < sideList.size(); i += 2) {
      float kortKant = ( (Float) sideList.get(i)).floatValue();
      float langKant = ( (Float) sideList.get(i + 1)).floatValue();

      // kry kleinste stuk bord in voorraad wat sal werk
      StockItem bestItem = null;
      float bestKortKant = 1000000;
      float bestLangKant = 1000000;
      Iterator it = stockList.iterator();
      while (it.hasNext()) {
        StockItem stockItem = (StockItem) it.next();
        stockItem.setItemLengthsAllocated(null);
        float itemKortKant = Math.min(stockItem.getLength(), stockItem.getWidth());
        float itemLangKant = Math.max(stockItem.getLength(), stockItem.getWidth());

        if ( (itemKortKant >= kortKant && itemKortKant < bestKortKant &&
              itemLangKant >= langKant && itemLangKant < bestLangKant)) {
          bestItem = stockItem;
          bestKortKant = itemKortKant;
          bestLangKant = itemLangKant;
        }
      }

      if (bestItem == null) {
        return false;
      }

      // Kry nou offcuts
      float[] sides = {
          kortKant, langKant};
      bestItem.setItemLengthsAllocated(sides);
      ArrayList offCutList = getTempOffCutListBoards(bestItem, kortKant,
          langKant);
      stockList.addAll(offCutList);
      stockList.remove(bestItem);
      usedStockList.add(bestItem);
    }

    // Kry nou voorraad wat gebruik gaan word en update db
    ArrayList allocList = new ArrayList();
    HashMap allocMap = new HashMap();
    Iterator it = usedStockList.iterator();
    while (it.hasNext()) {
      StockItem stockItem = (StockItem) it.next();

      if (stockItem.getItemLengthsAllocated() != null) {
        float[] sides = stockItem.getItemLengthsAllocated();
        int id = stockItem.getStockID();
        StockItem mappedItem = (StockItem) allocMap.get(new Integer(id));

        if (mappedItem == null) {
          allocList.add(stockItem);
          allocMap.put(new Integer(id), stockItem);

        }
        else {
          float[] mappedSides = mappedItem.getItemLengthsAllocated();
          float[] newSides = new float[sides.length + mappedSides.length];
          for (int j = 0; j < sides.length; j++) {
            newSides[j] = sides[j];
          }
          for (int j = 0; j < mappedSides.length; j++) {
            newSides[sides.length + j] = mappedSides[j];
          }
          mappedItem.setItemLengthsAllocated(newSides);
        }
      }
    }

    it = allocList.iterator();
    while (it.hasNext()) {
      StockItem stockItem = (StockItem) it.next();
      stockItem.setDate(Utils.getCurrentDate());
      stockItem.setDesignID(designID);
      stockItem.setExitType(StockItem.STOCK_DESIGN);
    }
    //db_conn.updateStockItems(allocList);
    allocationList.addAll(allocList);
    return true;
  }

  public ArrayList getTempOffCutListBoards(StockItem bestItem, float kortKant,
                                           float langKant) {
    float bordKortKant = Math.min(bestItem.getLength(), bestItem.getWidth());
    float bordLangKant = Math.max(bestItem.getLength(), bestItem.getWidth());
    ArrayList offcutList = new ArrayList();

    if (bordKortKant >= langKant) {
      if (bordLangKant > kortKant) {
        StockItem offcut = (StockItem) bestItem.clone();
        offcut.setLength(bordLangKant - kortKant);
        offcut.setWidth(bordKortKant);
        offcut.setDate(Utils.getCurrentDate());
        offcut.setEntryType(StockItem.STOCK_OFFCUT);
        offcut.setExitType( -1);
        offcut.setCount(1);
        //offcut.setStockID( -1);
        offcut.setItemLengthsAllocated(null);
        offcutList.add(offcut);
      }

      if (bordKortKant > langKant) {
        StockItem offcut = (StockItem) bestItem.clone();
        offcut.setLength(kortKant);
        offcut.setWidth(bordKortKant - langKant);
        offcut.setDate(Utils.getCurrentDate());
        offcut.setEntryType(StockItem.STOCK_OFFCUT);
        offcut.setExitType( -1);
        offcut.setCount(1);
        //offcut.setStockID( -1);
        offcut.setItemLengthsAllocated(null);
        offcutList.add(offcut);
      }

    }
    else {
      if (bordLangKant > langKant) {
        StockItem offcut = (StockItem) bestItem.clone();
        offcut.setLength(bordLangKant - langKant);
        offcut.setWidth(bordKortKant);
        offcut.setDate(Utils.getCurrentDate());
        offcut.setEntryType(StockItem.STOCK_OFFCUT);
        offcut.setExitType( -1);
        offcut.setCount(1);
        //offcut.setStockID( -1);
        offcut.setItemLengthsAllocated(null);
        offcutList.add(offcut);
      }

      if (bordKortKant > kortKant) {
        StockItem offcut = (StockItem) bestItem.clone();
        offcut.setLength(langKant);
        offcut.setWidth(bordKortKant - kortKant);
        offcut.setDate(Utils.getCurrentDate());
        offcut.setEntryType(StockItem.STOCK_OFFCUT);
        offcut.setExitType( -1);
        offcut.setCount(1);
        //offcut.setStockID( -1);
        offcut.setItemLengthsAllocated(null);
        offcutList.add(offcut);
      }
    }

    /*StockItem offcut = (StockItem) bestItem.clone();
         offcut.setLength(innerHeight);
         offcut.setWidth(innerWidth);
         offcut.setDate(Utils.getCurrentDate());
         offcut.setEntryType(StockItem.STOCK_OFFCUT);
         offcut.setExitType( -1);
         offcut.setCount(1);
         //offcut.setStockID( -1);
         offcut.setItemLengthsAllocated(null);
         offcutList.add(offcut);*/

    return offcutList;
  }

  public boolean extractStockFrames(artof.dialogs.ProgressDialog dialog,
                                    int progressBase, String itemCode,
                                    ArrayList startSideList, int designID) {
    ArrayList stockList = db_conn.getCurrentStockPerItemCode(itemCode);
    if (stockList.size() == 0)
      return false;

    // Kry al die moontlike volgordes waarin die stock gebruik kan word
    ArrayList allSequences = getAllSequences(stockList, startSideList);
    if (allSequences.size() == 0)
      return false;

    ArrayList sideSequences = getAllSequences(startSideList);
    float dialogFactor = 100.f / allSequences.size();
    int count = 0;

    // Kry mees optimale volgorde
    double bestListStockProduct = 1000000;
    ArrayList bestList = new ArrayList();
    ArrayList bestSideList = new ArrayList();
    Iterator itSides = sideSequences.iterator();
    while (itSides.hasNext()) {
      ArrayList sideList = (ArrayList) itSides.next();

      Iterator it = allSequences.iterator();
      while (it.hasNext()) {
        ArrayList seqList = (ArrayList) it.next();
        ArrayList stockLeft = (ArrayList) stockList.clone();
        ArrayList remainingSides = (ArrayList) sideList.clone();

        float offCutProduct = 1;
        Iterator it2 = seqList.iterator();
        while (it2.hasNext()) {
          StockItem item = (StockItem) it2.next();
          stockLeft.remove(item);

          float stockLengthAvailable = (float) item.getLength();
          while (remainingSides.size() > 0) {
            float sideLength = ( (Float) remainingSides.get(0)).floatValue();
            if (stockLengthAvailable >= sideLength) {
              stockLengthAvailable -= sideLength;
              remainingSides.remove(0);
              if (stockLengthAvailable < sideLength ||
                  remainingSides.size() == 0) {
                offCutProduct *= stockLengthAvailable / 1000;
                break;
              }

            }
            else {
              offCutProduct *= stockLengthAvailable / 1000;
              break;
            }
          }
        }

        float stockProduct = 1;
        it2 = stockLeft.iterator();
        while (it2.hasNext()) {
          StockItem fokkenItem = (StockItem) it2.next();
          stockProduct *= fokkenItem.getLength() / 1000;
        }

        stockProduct *= offCutProduct;
        if (bestListStockProduct > stockProduct) {
          bestListStockProduct = stockProduct;
          bestList = seqList;
          bestSideList = sideList;
        }
      }
      dialog.setValue2(progressBase + (int) (++count * dialogFactor));
    }

    if (designID > -1) {
      ArrayList remainingSides = (ArrayList) bestSideList.clone();
      try {
        Iterator it = bestList.iterator();
        while (it.hasNext()) {
          StockItem item = (StockItem) it.next();
          item.setDate(Utils.getCurrentDate());
          item.setDesignID(designID);
          item.setExitType(StockItem.STOCK_DESIGN);

          ArrayList sidesCovered = new ArrayList();
          float stockLengthAvailable = (float) item.getLength();
          while (remainingSides.size() > 0) {
            float sideLength = ( (Float) remainingSides.get(0)).floatValue();
            if (stockLengthAvailable >= sideLength) {
              stockLengthAvailable -= sideLength;
              sidesCovered.add(new Float(sideLength));
              remainingSides.remove(0);
            }
            else {
              break;
            }
          }
          float[] sides = new float[sidesCovered.size()];
          for (int i = 0; i < sides.length; i++) {
            sides[i] = ( (Float) sidesCovered.get(i)).floatValue();
          }
          item.setItemLengthsAllocated(sides);

        }

        if (remainingSides.size() == 0) {
          //db_conn.updateStockItems(bestList);
          allocationList.addAll(bestList);
          return true;
        }

      }
      catch (NullPointerException e) {
        return false;
      }
    }
    return false;
  }

  private ArrayList getAllSequences(ArrayList elements) {
    ArrayList allSequences = new ArrayList();

    if (elements.size() == 1) {
      allSequences.add(elements);
      return allSequences;
    }

    for (int i = 0; i < elements.size(); i++) {
      Float item = (Float) elements.get(i);
      ArrayList clone = (ArrayList) elements.clone();
      clone.remove(i);

      ArrayList subs = getAllSequences(clone);
      for (int j = 0; j < subs.size(); j++) {
        ArrayList seqList = (ArrayList) subs.get(j);
        seqList.add(0, item);
        allSequences.add(seqList);
      }
    }
    return allSequences;
  }

  private ArrayList getAllSequences(ArrayList elements, ArrayList sideLengths) {
    ArrayList allSequences = new ArrayList();

    if (elements.size() == 0) {
      return allSequences;
    }

    for (int i = 0; i < elements.size(); i++) {
      StockItem stockItem = (StockItem) elements.get(i);
      ArrayList clone = (ArrayList) elements.clone();
      clone.remove(i);

      ArrayList remainingSides = (ArrayList) sideLengths.clone();
      float stockLengthAvailable = (float) stockItem.getLength();
      boolean used = false;
      while (remainingSides.size() > 0) {
        float sideLength = ( (Float) remainingSides.get(0)).floatValue();
        if (stockLengthAvailable > sideLength) {
          stockLengthAvailable -= sideLength;
          remainingSides.remove(0);
          used = true;
        }
        else {
          break;
        }
      }

      if (remainingSides.size() > 0) {
        ArrayList subs = getAllSequences(clone, remainingSides);
        for (int j = 0; j < subs.size(); j++) {
          ArrayList seqList = (ArrayList) subs.get(j);
          if (used) {
            seqList.add(0, stockItem);
          }
          allSequences.add(seqList);
        }
      }
      else {
        if (used) {
          ArrayList seqList = new ArrayList();
          seqList.add(stockItem);
          allSequences.add(seqList);
        }
      }
    }
    return allSequences;
  }

  public ArrayList getOffCutList(ArrayList stockSelected) {
    ArrayList offcutList = new ArrayList();
    Iterator it = stockSelected.iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem) it.next();
      float[] sidesCovered = item.getItemLengthsAllocated();
      if (sidesCovered == null) {
        continue;
      }

      if (item.getMatType() == MaterialDets.MAT_FRAME ||
          item.getMatType() == MaterialDets.MAT_SLIP ||
          item.getMatType() == MaterialDets.MAT_FOIL) {
        float stockLengthAvailable = (float) item.getLength();
        for (int i = 0; i < sidesCovered.length; i++) {
          stockLengthAvailable -= sidesCovered[i];

        }
        StockItem offcut = (StockItem) item.clone();
        offcut.setLength(stockLengthAvailable);
        offcut.setDate(Utils.getCurrentDate());
        offcut.setEntryType(StockItem.STOCK_OFFCUT);
        offcut.setExitType( -1);
        offcut.setCount(1);
        offcut.setStockID( -1);
        offcut.setModified(true);
        offcutList.add(offcut);

      }
      else if (item.getMatType() == MaterialDets.MAT_BOARD ||
               item.getMatType() == MaterialDets.MAT_GB) {
        float itemKortKant = Math.min(item.getLength(), item.getWidth());
        float itemLangKant = Math.max(item.getLength(), item.getWidth());

        // sorteer sides covered van groot na klein
        float[] sortedSidesCovered = new float[sidesCovered.length];
        for (int i = 0; i < sidesCovered.length; i += 2) {
          int grootsteIndex = i;
          for (int j = 0; j < sidesCovered.length; j += 2) {
            if (sidesCovered[j] > sidesCovered[i]) {
              grootsteIndex = j;
            }
          }
          sortedSidesCovered[i] = sidesCovered[grootsteIndex];
          sortedSidesCovered[i + 1] = sidesCovered[grootsteIndex + 1];
          sidesCovered[grootsteIndex] = -1;
          sidesCovered[grootsteIndex + 1] = -1;
        }

        // trek nou al die offcuts af
        offcutList.add(item);
        for (int i = 0; i < sortedSidesCovered.length; i += 2) {
          float kortKant = sortedSidesCovered[i];
          float langKant = sortedSidesCovered[i + 1];

          // kry kleinste stuk bord in offcutList wat sal werk
          StockItem bestItem = null;
          float bestKortKant = 0;
          float bestLangKant = 0;
          boolean eersteKeer = true;
          Iterator it2 = offcutList.iterator();
          while (it2.hasNext()) {
            StockItem stockItem = (StockItem) it2.next();
            float bestItemKortKant = Math.min(stockItem.getLength(),
                                              stockItem.getWidth());
            float bestItemLangKant = Math.max(stockItem.getLength(),
                                              stockItem.getWidth());

            if (eersteKeer ||
                (bestItemKortKant >= kortKant && itemKortKant < bestKortKant &&
                 bestItemLangKant >= langKant && itemLangKant < bestLangKant)) {
              bestItem = stockItem;
              bestKortKant = bestItemKortKant;
              bestLangKant = bestItemLangKant;
              eersteKeer = false;
            }
          }

          ArrayList offcuts = getTempOffCutListBoards(bestItem, kortKant,
              langKant);
          offcutList.remove(bestItem);
          offcutList.addAll(offcuts);
        }

        it = offcutList.iterator();
        while (it.hasNext()) {
          StockItem offcut = (StockItem) it.next();
          offcut.setDate(Utils.getCurrentDate());
          offcut.setEntryType(StockItem.STOCK_OFFCUT);
          offcut.setExitType( -1);
          offcut.setCount(1);
          offcut.setStockID( -1);
          offcut.setModified(true);
        }
      }
    }

    return offcutList;
  }

      /*------------------------------ Design Calcs --------------------------------*/

  void btnNextDelDate_actionPerformed(ActionEvent e) {
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      int date = dets.getDeliveryDate();
      dets.setDeliveryDate(Utils.getNextDate(date));
      txtDelDate.setText(Utils.getDatumStr(dets.getDeliveryDate()));
    }
    catch (IndexOutOfBoundsException a) {
      // doen niks
    }
  }

  void btnPrevDelDate_actionPerformed(ActionEvent e) {
    try {
      DesignDets dets = (DesignDets) designList.get(curDesignIndex);
      int date = dets.getDeliveryDate();
      dets.setDeliveryDate(Utils.getPrevDate(date));
      txtDelDate.setText(Utils.getDatumStr(dets.getDeliveryDate()));
    }
    catch (IndexOutOfBoundsException a) {
      // doen niks
    }
  }

  public static String getRegistrationNotValid() {
    try {
      String mes = "WW91ciBSU0YgcmVnaXN0cmF0aW9uIGlzIG5vdCB2YWxpZA==";//"Your RSF registration is not valid";
      byte[] res = (new BASE64Decoder()).decodeBuffer(mes);
      return new String(res, "UTF-8");
    } catch (Exception e) {
      return "";
    }
  }

  public static String getRegistration() {
    try {
      String mes = "UmVnaXN0cmF0aW9uIGlzIG5vdCB2YWxpZA==";//"Registration"
      byte[] res = (new BASE64Decoder()).decodeBuffer(mes);
      return new String(res, "UTF-8");
    } catch (Exception e) {
      return "";
    }
  }

  public void refreshMethodPreferences() {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
      if (designDets.getStatus().equals(DESIGN_STATUS[DES_STATUS_COMP])) {
        String mes =
            "The design is completed.  The Method Preferences cannot be updated.";
        JOptionPane.showMessageDialog(this, mes, "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
      else {
        designDets.setMethodPrefs(db_conn.getMethodPreferences("Latest"));
        btnCalculate2_actionPerformed();
        designer.updateTableData();
      }
    }
    catch (IndexOutOfBoundsException e) {
      // fok dit
    }
  }

  public void refreshBusinessPreferences() {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
      if (designDets.getStatus().equals(DESIGN_STATUS[DES_STATUS_COMP])) {
        String mes =
            "The design is completed.  The Business Preferences cannot be updated.";
        JOptionPane.showMessageDialog(this, mes, "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
      else {
        designDets.setBusPrefs(db_conn.getBusPreferences("Latest"));
        updatePreferencePanel();
        btnCalculate2_actionPerformed();
      }
    }
    catch (IndexOutOfBoundsException e) {
      // fok dit
    }
  }

  public void refreshMaterials() {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
      if (designDets.getStatus().equals(DESIGN_STATUS[DES_STATUS_COMP])) {
        String mes = "The design is completed.  The Materials cannot be updated.";
        JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);

      } else {
        Iterator it = designDets.getItemList().iterator();
        while (it.hasNext()) {
          DesignItem2 designItem = (DesignItem2)it.next();
          try {
            MaterialDets mat = ArtofDB.getCurrentDB().getMaterial(designItem.getItemCode());
            if (mat == null) {
              if (designItem.getItemCode() != null) {
                String mes = "The material with item code " + designItem.getItemCode();
                mes += " has been deleted from the database and cannot be refreshed";
                JOptionPane.showMessageDialog(this, mes, "Warning", JOptionPane.WARNING_MESSAGE);
              }

            } else {
              designItem.setMaterialDets(mat);
              designItem.setColor(mat.getColor());
              if (designItem.getDesignType() == Designer.ITEM_FRAME) {
                designItem.setTopGap(mat.getDefaultValuesWithInMaterialDets().getWidth());
                designItem.setBottomGap(mat.getDefaultValuesWithInMaterialDets().getWidth());
                designItem.setLeftGap(mat.getDefaultValuesWithInMaterialDets().getWidth());
                designItem.setRightGap(mat.getDefaultValuesWithInMaterialDets().getWidth());
              }
            }

          } catch (NullPointerException e) {
            // los want dis is artwork waarskynlik
          }
        }
        btnCalculate2_actionPerformed();
        designer.repaint();
      }

    } catch (IndexOutOfBoundsException e) {
      // fok dit
    }
  }

  void txtMaterial_focusLost(FocusEvent e) {
    btnCalculate2_actionPerformed();
  }

  void txtLabour_focusLost(FocusEvent e) {
    btnCalculate2_actionPerformed();
  }

  void txtDiscountOne_focusLost(FocusEvent e) {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
      try {
        designDets.setDiscountOne( (new Float(txtDiscountOne.getText())).
                                  floatValue());
      }
      catch (NumberFormatException x) {
        JOptionPane.showMessageDialog(this, "Invalid discount entered.",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }
      updatePricePanel();
    }
    catch (IndexOutOfBoundsException y) {
      // doen dan fokkol
    }
  }

  void txtNoOrdered_focusLost(FocusEvent e) {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
      try {
        designDets.setNoOrdered( (new Integer(txtNoOrdered.getText())).intValue());
      }
      catch (NumberFormatException x) {
        JOptionPane.showMessageDialog(this, "Invalid no ordered entered.",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }
      updatePricePanel();
    }
    catch (IndexOutOfBoundsException y) {
      // doen dan fokkol
    }
  }

  void txtPriceOther_focusLost(FocusEvent e) {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
      try {
        designDets.setPriceOther( (new Float(txtPriceOther.getText())).
                                 floatValue());
      }
      catch (NumberFormatException x) {
        JOptionPane.showMessageDialog(this,
                                      "Invalid price for other items entered.",
                                      "Error", JOptionPane.ERROR_MESSAGE);
      }
      updatePricePanel();
    }
    catch (IndexOutOfBoundsException y) {
      // doen dan fokkol
    }
  }

  void txtDiscountOther_focusLost(FocusEvent e) {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
      try {
        designDets.setDiscountOther( (new Float(txtDiscountOther.getText())).
                                    floatValue());
      }
      catch (NumberFormatException x) {
        JOptionPane.showMessageDialog(this,
            "Invalid discount for other items entered.", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
      updatePricePanel();
    }
    catch (IndexOutOfBoundsException y) {
      // doen dan fokkol
    }
  }

  public void btnCalculate2_actionPerformed() {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);
      if (designDets.getStatus().equals(DESIGN_STATUS[DES_STATUS_COMP])) {
        String mes = "The design is completed and cannot be calculated again.";
        JOptionPane.showMessageDialog(this, mes, "Error", JOptionPane.ERROR_MESSAGE);

      } else {
        calcItemSizes();
        calcPrices();
        updatePricePanel();
        //if (designDets.getStatus().equals(DESIGN_STATUS[DES_STATUS_ORDER])) {
        //  extractStock();
        //}
      }
    }
    catch (IndexOutOfBoundsException x) {
      // fok dit
    }
  }

  void btnCutlist2_actionPerformed(ActionEvent e) {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);

    }
    catch (IndexOutOfBoundsException y) {
      // doen dan fokkol
    }
  }

  public void btnPrint_actionPerformed() {
    try {
      DesignDets designDets = (DesignDets) designList.get(curDesignIndex);;
      int count = db_conn.getMethodPreferences("Latest").getSunNoSpecs();
      for (int i = 0; i < count; i++) {
        if (((String)cbxStatus.getSelectedItem()).equals("In Order")) {
          setDesignValues();
          boolean doPrint = designDets.printDesignQuotation();
          if (doPrint) {
            if (UserSettings.OPT_STOCKLIST) {
              artof.stock.printing.OffcutPrinter offcutPrinter = new OffcutPrinter(db_conn.getStockAlreadyAllocatedToDesign(Integer.valueOf(lblDesignID.getText()).intValue()), lblDesignID.getText());
            }
          }
        }
        else if (((String)cbxStatus.getSelectedItem()).equals("Quotation")) {
          setDesignValues();
          designDets.printDesignQuotation();
        }
				else if (((String)cbxStatus.getSelectedItem()).equals("Completed")) {
					setDesignValues();
					InvoiceConfigureDialog invoiceConfigureDialog = new InvoiceConfigureDialog(designDets);


				}
			}
    } catch (IndexOutOfBoundsException y) {
      // doen dan fokkol
    }
  }

  void ckStretching_actionPerformed(ActionEvent e) {
    btnCalculate2_actionPerformed();
  }

  void ckPasting_actionPerformed(ActionEvent e) {
    btnCalculate2_actionPerformed();
  }

  void rbnCalcOverlap_actionPerformed(ActionEvent e) {
    btnCalculate2_actionPerformed();
    designer.updateTableData();
  }

  void rbnFixedBorder_actionPerformed(ActionEvent e) {
    btnCalculate2_actionPerformed();
    designer.updateTableData();
  }

  void rbnFullSize_actionPerformed(ActionEvent e) {
    btnCalculate2_actionPerformed();
    designer.updateTableData();
  }

  void ckFullBottom_actionPerformed(ActionEvent e) {
    btnCalculate2_actionPerformed();
    designer.updateTableData();
  }

  void btnUpdateMethodPrefs_actionPerformed(ActionEvent e) {
    btnCalculate2_actionPerformed();
    designer.updateTableData();
  }

  public void updateTableData() {
    designer.updateTableData();
  }
}
