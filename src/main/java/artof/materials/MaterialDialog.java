package artof.materials;

import artof.database.*;
import artof.dialogs.*;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import java.awt.print.*;
import java.awt.font.*;
import java.awt.geom.*;
import artof.dialogs.ProgressDialog;
import artof.materials.profiles.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MaterialDialog extends JDialog implements DataFunctions, Printable, Runnable {
  private JDialog thisDialog;
  private ArtofDB db_conn = ArtofDB.getCurrentDB();
  private int type;
  private ArrayList materialList;
  private ButCreator butCreator;
  private DefaultListModel listModel = new DefaultListModel();
 // private String lastSupplier;
  private CostPanelInterface costPanel;
  private int topHeight = 30;
  private int middleHeight = 30;
  private int bottomHeight = 30;
  //private HashMap imageMap = new HashMap();

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel pnlMain = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JPanel jPanel2 = new JPanel();
  private Border border1;
  private JPanel pnlMes = new JPanel();
  private Border border2;
  private JPanel pnlCost = new JPanel();
  private Border border3;
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private CustomTextField txtItemCode = new CustomTextField();
  private JLabel jLabel2 = new JLabel();
  private CustomTextField txtOwnCode = new CustomTextField();
  private JLabel jLabel3 = new JLabel();
  private JButton btnSuppliers = new JButton(new ImageIcon("images/Regs.gif"));
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private CustomTextField txtGroup = new CustomTextField();
  private JLabel jLabel7 = new JLabel();
  private JButton btnColor = new JButton();
  private Border border4;
  private Border border5;
  private Border border6;
  private Border border7;
  private Border border8;
  private JLabel lblLength = new JLabel();
  private CustomTextField txtLength = new CustomTextField();
  private JLabel jLabel9 = new JLabel();
  private JLabel lblRebate = new JLabel();
  private CustomTextField txtRebate = new CustomTextField();
  private CustomTextField txtWidth = new CustomTextField();
  private JLabel lblThickness = new JLabel();
  private CustomTextField txtThickness = new CustomTextField();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JPanel pnlProfile = new JPanel();
  private Border border9;
  private Border border10;
  private JPanel pnlSouth = new JPanel();
  private JPanel pnlNorth = new JPanel();
  private Border border11;
  private Border border12;
  private BorderLayout borderLayout3 = new BorderLayout();
  private BorderLayout borderLayout4 = new BorderLayout();
  private JList lstSuppliers = new JList(listModel);
  private Border border13;
  private JComboBox cbxStatus = new JComboBox();

  private JLabel jLabel6 = new JLabel();
  private CustomTextField txtDescription = new CustomTextField();

  private ProgressDialog progressDialog;
  private Thread progressThread;

  JLabel lblImage = new JLabel();
  SamplePanel pnlImage = new SamplePanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton btnProfile = new JButton();

  public MaterialDialog(int type) {
    thisDialog = this;
    this.type = type;
    progressDialog = new ProgressDialog("Reading materials");
    progressDialog.setLength(db_conn.getMaterialCount(type));
    Thread progressThread = new Thread(this);
    progressThread.start();
    progressDialog.setTxtSrc("");
    progressDialog.setTxtTarget("");
    progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    progressDialog.setModal(true);
    progressDialog.setVisible(true);

    if (type == MaterialDets.MAT_BOARD) {
      setSize(550, 460);
      setTitle("Boards");
      lblLength.setText("Height (mm)");
      lblRebate.setVisible(false);
      txtRebate.setVisible(false);
      topHeight = 100;
      middleHeight = 10;
      bottomHeight = 100;

    } else if (type == MaterialDets.MAT_GB) {
      setSize(550, 460);
      setTitle("Glass and Backs");
      lblLength.setText("Height (mm)");
      lblRebate.setVisible(false);
      txtRebate.setVisible(false);
      topHeight = 100;
      middleHeight = 10;
      bottomHeight = 100;

    } else if (type == MaterialDets.MAT_FRAME) {
      setSize(550, 500);
      setTitle("Frames");
      topHeight = 90;
      middleHeight = 20;
      bottomHeight = 110;

    } else if (type == MaterialDets.MAT_SLIP) {
      setSize(550, 450);
      lblRebate.setText("Overlap");
      setTitle("Slips");
      topHeight = 100;
      middleHeight = 30;
      bottomHeight = 70;

    } else if (type == MaterialDets.MAT_FOIL) {
      setSize(550, 430);
//      lblRebate.setText("Overlap");
      lblRebate.setVisible(false);
      txtRebate.setVisible(false);
      setTitle("Foils");
      topHeight = 100;
      middleHeight = 10;
      bottomHeight = 70;
    }

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    if (type == MaterialDets.MAT_BOARD || type == MaterialDets.MAT_GB) {
      CostPanelBoards panel = new CostPanelBoards();
      costPanel = panel;
      pnlCost.add(panel, BorderLayout.CENTER);
    } else {
      CostPanelFrames panel = new CostPanelFrames(type);
      costPanel = panel;
      pnlCost.add(panel, BorderLayout.CENTER);
    }

    // Moenie die opsie gee om images te kies vir glas en backs nie
    if (type == MaterialDets.MAT_GB) {
      lblImage.setVisible(false);
      pnlImage.setVisible(false);
      btnProfile.setVisible(false);
    }

    butCreator = new ButCreator(this);
    pnlNorth.add(butCreator.getDataPanel(), BorderLayout.CENTER);
    pnlSouth.add(butCreator.getBrowsePanel(), BorderLayout.CENTER);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        setData(butCreator.getCurrentObject());
        boolean modified = false;
        Iterator it = materialList.iterator();
        while (it.hasNext()) {
          MaterialDets dets = (MaterialDets)it.next();
          if (dets.isModified()) {
            modified = true;
            break;
          }
        }

        if (modified && JOptionPane.showConfirmDialog(thisDialog, "Do you want to save changes", "Save",
                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
          db_conn.saveMaterials(materialList);
      }
     });

    lstSuppliers.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        try {
          //setMaterialDetails(lastSupplier);
          //lastSupplier = (String) lstSuppliers.getSelectedValue();
          updateMaterialDetails( (MaterialDets) butCreator.getCurrentObject());
        } catch (ArrayIndexOutOfBoundsException x) {
          //doen niks
        }
      }
    });

    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }


  public void run() {
    try {
      Thread.sleep(500);
    } catch (Exception e) {
      //haha
    }
    materialList = db_conn.getMaterialsProgress(type, UserSettings.MATERIAL_SORTER, progressDialog);
    progressDialog.hide();
  }


  public boolean setData(Object obj) {
    boolean befok = true;
    if (obj != null) {
      MaterialDets dets = (MaterialDets)obj;

      if (txtItemCode.getText() == null || txtItemCode.getText().equals("")) {
        String mes = "A unique item code must be provided for each material item";
        JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
        return false;
      }
      dets.setItemCode(txtItemCode.getText());

      if (txtOwnCode.getText() == null || txtOwnCode.getText().equals(""))
        dets.setOwnCode("~" + txtItemCode.getText());
      else
        dets.setOwnCode(txtOwnCode.getText());

      dets.setGroup(txtGroup.getText());
      dets.setColor(btnColor.getBackground());
      dets.setDescription(txtDescription.getText());

      try {
        String supplier = (String) lstSuppliers.getSelectedValue();
        setMaterialDetails(supplier);
      } catch (ArrayIndexOutOfBoundsException e) {
        //doen niks
      }
    }
    return true;
  }

  private void setMaterialDetails(String supplier) {
    MaterialValues val;
    try {
      MaterialDets curDets = (MaterialDets)butCreator.getCurrentObject();
      val = curDets.getMaterialValues(supplier);
    } catch (Exception e) {
      return;
    }

    if (val == null) return;

    val.setStatus((String)cbxStatus.getSelectedItem());

    try {
      val.setLength((new Float(txtLength.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes;
      if (type == MaterialDets.MAT_BOARD || type == MaterialDets.MAT_GB)
        mes = "Invalid Height was specified for " + supplier;
      else
        mes = "Invalid Length was specified for " + supplier;
      JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    try {
      val.setWidth((new Float(txtWidth.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes = "Invalid Width was specified for " + supplier;
      JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    try {
      val.setThickness((new Float(txtThickness.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes;
      if (type == MaterialDets.MAT_FRAME)
        mes = "Invalid Depth was specified for " + supplier;
      else
        mes = "Invalid Thickness was specified for " + supplier;
      JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    try {
      val.setThickness((new Float(txtThickness.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes;
      if (type == MaterialDets.MAT_FRAME)
        mes = "Invalid Depth was specified for " + supplier;
      else
        mes = "Invalid Thickness was specified for " + supplier;
      JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    try {
      val.setRebate((new Float(txtRebate.getText())).floatValue());
    } catch (NumberFormatException e) {
      String mes;
      if (type == MaterialDets.MAT_FRAME)
        mes = "Invalid Rebate was specified for " + supplier;
      else
        mes = "Invalid Overlap was specified for " + supplier;
      JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    costPanel.setData(val, supplier);
  }

  public void updateData(Object obj) {
    if (obj == null) {

      txtItemCode.setText("");
      txtItemCode.setEditable(false);

      txtOwnCode.setText("");
      txtOwnCode.setEditable(false);

      listModel.clear();
      lstSuppliers.setEnabled(false);
      btnSuppliers.setEnabled(false);

      txtGroup.setText("");
      txtGroup.setEditable(false);

      txtDescription.setText("");
      txtDescription.setEditable(false);

      btnColor.setBackground(UserSettings.DEF_COLOR);
      btnColor.setEnabled(false);

      updateMaterialDetails(null);

    } else {

      MaterialDets dets = (MaterialDets)obj;

      txtItemCode.setText(dets.getItemCode());
      txtItemCode.setEditable(true);

      txtOwnCode.setText(dets.getOwnCode());
      txtOwnCode.setEditable(true);

      lstSuppliers.setEnabled(true);
      listModel.clear();
      Iterator it = dets.getSuppliers().iterator();
      while (it.hasNext()) listModel.addElement(it.next());
      if (listModel.size() > 0 && lstSuppliers.getSelectedIndex() == -1)
        lstSuppliers.setSelectedIndex(0);
      btnSuppliers.setEnabled(true);


      txtGroup.setText(dets.getGroup());
      txtGroup.setEditable(true);

      txtDescription.setText(dets.getDescription());
      txtDescription.setEditable(true);

      btnColor.setBackground(dets.getColor());
      btnColor.setEnabled(true);

      updateMaterialDetails(dets);

      // get die fokken image
      pnlImage.setImage(getCurrentImage(dets));
    }
  }

  private void updateMaterialDetails(MaterialDets dets) {
    try {

      String supplier = (String)lstSuppliers.getSelectedValue();
      MaterialValues val = dets.getMaterialValues(supplier);

      cbxStatus.setSelectedItem(val.getStatus());
      cbxStatus.setEnabled(true);

      txtLength.setText(Utils.getCurrencyFormat(val.getLength()));
      txtLength.setEditable(true);

      txtWidth.setText(Utils.getCurrencyFormat(val.getWidth()));
      txtWidth.setEditable(true);

      txtThickness.setText(Utils.getCurrencyFormat(val.getThickness()));
      txtThickness.setEditable(true);

      txtRebate.setText(Utils.getCurrencyFormat(val.getRebate()));
      txtRebate.setEditable(true);

      costPanel.updateData(val);

    } catch (Exception e) {

      cbxStatus.setSelectedIndex(-1);
      cbxStatus.setEnabled(false);

      txtLength.setText("");
      txtLength.setEditable(false);

      txtWidth.setText("");
      txtWidth.setEditable(false);

      txtThickness.setText("");
      txtThickness.setEditable(false);

      txtRebate.setText("");
      txtRebate.setEditable(false);

      costPanel.updateData(null);
    }
  }

  public ListIterator getMainIterator() {
    return materialList.listIterator();
  }

  public Object getNewItem() {
    MaterialDets newMaterial = new MaterialDets();
    newMaterial.setMaterialType(type);
    newMaterial.setIsNew(true);
    return newMaterial;
  }

  public void saveMainList() {
    db_conn.saveMaterials(materialList);

    // save images
    /*Iterator it = imageMap.keySet().iterator();
    while (it.hasNext()) {
      String itemCode = (String)it.next();
      saveImage(itemCode);
    }*/
  }

  public void refreshMainList(String orderSQL) {
    if (orderSQL != null)
      UserSettings.MATERIAL_SORTER = orderSQL;

    progressDialog = new ProgressDialog("Reading materials");
    progressDialog.setLength(db_conn.getMaterialCount(type));
    Thread progressThread = new Thread(this);
    progressThread.start();
    progressDialog.setTxtSrc("");
    progressDialog.setTxtTarget("");
    progressDialog.setModal(true);
    progressDialog.setVisible(true);
  }

  public void closeMainList() {
    hide();
  }

  public void deleteItem(Object obj) {
    try {
      db_conn.deleteMaterial(((MaterialDets)obj).getMaterialID());
//      db_conn.deleteMaterial(((MaterialDets)obj).getItemCode());
      //lastSupplier = null;
    } catch (NullPointerException e) {
      // doen niks
    }
  }

  public void printDieFokker() {
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    Book book = new Book();
    int numPages = materialList.size() / 6;
    if (numPages * 6 < materialList.size()) numPages++;
    book.append(this, new PageFormat(), numPages);
    printerJob.setPageable(book);
    boolean doPrint = printerJob.printDialog();
    if (doPrint) {
      try {
        printerJob.print();
      } catch (PrinterException exception) {
        JOptionPane.showMessageDialog(thisDialog, "Printer error.  The clients details cannot be printed.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public int print(Graphics g, PageFormat format, int pageIndex) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.translate(format.getImageableX(), format.getImageableY());
    g2d.setPaint(Color.black);
    double pageWidth = format.getImageableWidth();
    double pageHeight = format.getImageableHeight();
    double curY = 0;

    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 12));
    FontRenderContext frc = g2d.getFontRenderContext();
    String text = MaterialDets.MAT_TYPES[type] + " as at " + Utils.getDatumStr(Utils.getCurrentDate());
    TextLayout layout = new TextLayout(text, g2d.getFont(), frc);
    Rectangle2D bounds = layout.getBounds();
    curY += bounds.getHeight();
    layout.draw(g2d, (float)(pageWidth / 2 -  bounds.getWidth() / 2), (float)curY);

    curY += bounds.getHeight() * 2;
    text = String.valueOf(pageIndex * 6 + 1) + " to " + Math.min(materialList.size(), (pageIndex + 1) * 6);
    text += " of " + materialList.size();
    layout = new TextLayout(text, g2d.getFont(), frc);
    bounds = layout.getBounds();
    curY += bounds.getHeight();
    layout.draw(g2d, (float)(pageWidth / 2 -  bounds.getWidth() / 2), (float)curY);

    g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
    curY *= 2;
    for (int i = pageIndex * 6; i < (pageIndex + 1) * 6; i++) {
      MaterialDets item;
      try {
        item = (MaterialDets)materialList.get(i);
      } catch (IndexOutOfBoundsException e) {
        break;
      }
      layout = new TextLayout("0123456789012", g2d.getFont(), frc);
      double indent = layout.getBounds().getWidth() * 1.1;
      double height;

      // Eerste reel
      layout = new TextLayout("Item Code:", g2d.getFont(), frc);
      height = layout.getBounds().getHeight();
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(item.getItemCode(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Own Code:", g2d.getFont(), frc);
      layout.draw(g2d, (float)pageWidth / 2, (float)curY);
      try {
        layout = new TextLayout(item.getOwnCode(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Tweede reel
      curY += height * 1.5;

      layout = new TextLayout("Supplier:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(item.getDefaultSupplier(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Status:", g2d.getFont(), frc);
      layout.draw(g2d, (float)pageWidth / 2, (float)curY);
      try {
        layout = new TextLayout(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getStatus(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Derde reel
      curY += height * 1.5;

      layout = new TextLayout("Group:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(item.getGroup(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Description:", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(item.getDescription(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
        layout = new TextLayout("None", g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      }

      // Vierde reel
      curY += height * 1.5;

      if (type == MaterialDets.MAT_BOARD || type == MaterialDets.MAT_GB)
        layout = new TextLayout("Height:", g2d.getFont(), frc);
      else
        layout = new TextLayout("Length:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(Utils.getCurrencyFormat(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getLength()), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Width:", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(Utils.getCurrencyFormat(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getWidth()), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Vyfde reel
      curY += height * 1.5;

      layout = new TextLayout("Thickness:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(Utils.getCurrencyFormat(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getThickness()), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      if (type != MaterialDets.MAT_BOARD && type != MaterialDets.MAT_GB) {
        layout = new TextLayout("Rebate:", g2d.getFont(), frc);
        layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
        try {
          layout = new TextLayout(Utils.getCurrencyFormat(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getRebate()), g2d.getFont(), frc);
          layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
        } catch (IllegalArgumentException e) {
        }
      }

      // Sesde reel
      curY += height * 1.5;

      if (type == MaterialDets.MAT_BOARD || type == MaterialDets.MAT_GB) {
        layout = new TextLayout("Cost:", g2d.getFont(), frc);
        layout.draw(g2d, 0, (float)curY);
        try {
          float costM2 = item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCost();
          float area = item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getLength() * item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getWidth() / 1000000;
          layout = new TextLayout(Utils.getCurrencyFormat(costM2 * area), g2d.getFont(), frc);
          layout.draw(g2d, (float)indent, (float)curY);
        } catch (IllegalArgumentException e) {
        }

        layout = new TextLayout("Cost / m�:", g2d.getFont(), frc);
        layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
        try {
          layout = new TextLayout(Utils.getCurrencyFormat(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCost()), g2d.getFont(), frc);
          layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
        } catch (IllegalArgumentException e) {
        }

      } else {
        layout = new TextLayout("Cost / m:", g2d.getFont(), frc);
        layout.draw(g2d, 0, (float)curY);
        try {
          layout = new TextLayout(Utils.getCurrencyFormat(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCost()), g2d.getFont(), frc);
          layout.draw(g2d, (float)indent, (float)curY);
        } catch (IllegalArgumentException e) {
        }

        layout = new TextLayout("Comp Factor:", g2d.getFont(), frc);
        layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
        try {
          layout = new TextLayout(Utils.getCurrencyFormat(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCompFactor()), g2d.getFont(), frc);
          layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
        } catch (IllegalArgumentException e) {
        }
      }

      // Sewende reel
      curY += height * 1.5;

      if (type == MaterialDets.MAT_BOARD || type == MaterialDets.MAT_GB) {
        layout = new TextLayout("Comp Factor:", g2d.getFont(), frc);
        layout.draw(g2d, 0, (float)curY);

        try {
          layout = new TextLayout(Utils.getCurrencyFormat(item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCompFactor()), g2d.getFont(), frc);
          layout.draw(g2d, (float)indent, (float)curY);
        } catch (IllegalArgumentException e) {
        }

        layout = new TextLayout("Price / m�:", g2d.getFont(), frc);
        layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
        try {
          float costM2 = item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCost();
          float markup = ArtofDB.getCurrentDB().getBusPreferences().getTotalBoardMarkup();
          float price = costM2 * item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCompFactor() * markup;
          layout = new TextLayout(Utils.getCurrencyFormat(price), g2d.getFont(), frc);
          layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
        } catch (IllegalArgumentException e) {
        }

      } else {
        layout = new TextLayout("Price / m:", g2d.getFont(), frc);
        layout.draw(g2d, 0, (float)curY);
        try {
          float markup = ArtofDB.getCurrentDB().getBusPreferences().getTotalFrameMarkup();
          float price = item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCost() * item.getDefaultValues((String)lstSuppliers.getSelectedValue()).getCompFactor() * markup;
          layout = new TextLayout(Utils.getCurrencyFormat(price), g2d.getFont(), frc);
          layout.draw(g2d, (float)indent, (float)curY);
        } catch (IllegalArgumentException e) {
        }
      }

      curY += bounds.getHeight() * 4;
      if (curY > pageHeight)
        break;
    }

    return Printable.PAGE_EXISTS;
  }

  public String[] getSortList() {
    String[] fromStrings = new String[4];
    fromStrings[0] = "Item Code";
    fromStrings[1] = "Own Code";
    //fromStrings[2] = "Status";
    fromStrings[2] = "Group";
    fromStrings[3] = "Description";
    return fromStrings;
  }

  public Hashtable getSortDBMap() {
    Hashtable table = new Hashtable();
    table.put("Item Code", "ITEMCODE");
    table.put("Own Code", "OWNCODE");
    //table.put("Status", "STATUS");
    table.put("Group", "MATERIAL_GROUP");
    table.put("Description", "DESCRIPTION");
    return table;
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border3 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border4 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border5 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border6 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border7 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border8 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border9 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border10 = BorderFactory.createEmptyBorder();
    border11 = BorderFactory.createEmptyBorder(5,5,0,5);
    border12 = BorderFactory.createEmptyBorder(0,5,5,5);
    border13 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(178, 178, 178));
    this.getContentPane().setLayout(borderLayout1);
    pnlMain.setLayout(gridBagLayout1);
    jPanel2.setBorder(border1);
    jPanel2.setMinimumSize(new Dimension(4, 4));
    jPanel2.setPreferredSize(new Dimension(4, 4));
    jPanel2.setLayout(gridBagLayout2);
    pnlMes.setBorder(border2);
    pnlMes.setMinimumSize(new Dimension(4, 4));
    pnlMes.setPreferredSize(new Dimension(4, 4));
    pnlMes.setLayout(gridBagLayout3);
    pnlCost.setBorder(border3);
    pnlCost.setPreferredSize(new Dimension(4, 4));
    pnlCost.setLayout(borderLayout2);
    jLabel1.setText("Item Code");
    jLabel2.setText("Own Code");
    jLabel3.setText("Supplier");
    btnSuppliers.setBorder(border10);
    btnSuppliers.setMargin(new Insets(0, 0, 0, 0));
    btnSuppliers.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSuppliers_actionPerformed(e);
      }
    });
    jLabel4.setText("Status");
    jLabel5.setText("Group");
    jLabel7.setText("Colour");
    btnColor.setBorder(border4);
    btnColor.setMaximumSize(new Dimension(20, 20));
    btnColor.setMinimumSize(new Dimension(20, 20));
    btnColor.setPreferredSize(new Dimension(20, 20));
    btnColor.setMargin(new Insets(0, 0, 0, 0));
    btnColor.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnColor_actionPerformed(e);
      }
    });
    lblLength.setText("Length (mm)");
    jLabel9.setText("Width (mm)");
    lblRebate.setText("Rebate (mm)");
    lblThickness.setText("Thickness (mm)");
    pnlProfile.setBorder(border9);
    pnlProfile.setMinimumSize(new Dimension(4, 4));
    pnlProfile.setPreferredSize(new Dimension(4, 4));
    pnlProfile.setLayout(gridBagLayout4);
    this.setModal(true);
    this.setResizable(false);
    borderLayout1.setHgap(5);
    borderLayout1.setVgap(5);
    pnlNorth.setBorder(border11);
    pnlNorth.setLayout(borderLayout3);
    pnlSouth.setBorder(border12);
    pnlSouth.setLayout(borderLayout4);
    lstSuppliers.setBorder(border13);
    lstSuppliers.setPreferredSize(new Dimension(4, 100));
    lstSuppliers.setFixedCellWidth(40);
    cbxStatus.setMinimumSize(new Dimension(4, 21));
    cbxStatus.setPreferredSize(new Dimension(4, 100));
    cbxStatus.addItem(MaterialValues.STATUS_AVAIL);
    cbxStatus.addItem(MaterialValues.STATUS_DISC);
    txtLength.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtLength_focusLost(e);
      }
    });
    txtWidth.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        txtWidth_focusLost(e);
      }
    });
    jLabel6.setText("Description");
    txtDescription.setPreferredSize(new Dimension(4, 100));
    txtLength.setSelectionStart(25);
    lblImage.setText("Image:");
    pnlImage.setBorder(BorderFactory.createEtchedBorder());
    btnProfile.setMargin(new Insets(2, 5, 2, 5));
    btnProfile.setText("...");
    btnProfile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnProfile_actionPerformed(e);
      }
    });
    txtItemCode.setAlignmentY((float) 0.5);
    txtItemCode.setPreferredSize(new Dimension(6, 100));
    txtOwnCode.setPreferredSize(new Dimension(6, 100));
    txtGroup.setPreferredSize(new Dimension(6, 100));
    this.getContentPane().add(pnlMain,  BorderLayout.CENTER);
    pnlMain.add(pnlCost,                     new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, bottomHeight));
    pnlMain.add(pnlMes,                  new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, middleHeight));
    pnlMain.add(jPanel2,                       new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, topHeight));
    jPanel2.add(jLabel1,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtItemCode,       new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 100, 0));
    jPanel2.add(jLabel2,   new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(txtOwnCode,      new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 100, 0));
    jPanel2.add(btnSuppliers,      new GridBagConstraints(2, 2, 1, 2, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel2.add(jLabel3,     new GridBagConstraints(0, 2, 1, 2, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jLabel4,      new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(lstSuppliers,       new GridBagConstraints(1, 2, 1, 2, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 100, 30));
    jPanel2.add(jLabel5,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(txtGroup,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 100, 0));
    jPanel2.add(jLabel7,      new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(btnColor,    new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    jPanel2.add(cbxStatus,       new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 100, 0));
    jPanel2.add(jLabel6,   new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    jPanel2.add(txtDescription,    new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 100, 0));
    pnlMain.add(pnlProfile,    new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));
    pnlProfile.add(lblImage,   new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlProfile.add(pnlImage,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 40, 40));
    pnlProfile.add(btnProfile,  new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(15, 5, 5, 5), 0, 0));
    this.getContentPane().add(pnlNorth, BorderLayout.NORTH);
    this.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
    pnlMes.add(lblLength,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlMes.add(txtLength,    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    pnlMes.add(jLabel9,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    pnlMes.add(txtWidth,    new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    pnlMes.add(lblThickness,     new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    pnlMes.add(txtThickness,     new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    pnlMes.add(lblRebate,        new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    pnlMes.add(txtRebate,      new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
  }

  void btnSuppliers_actionPerformed(ActionEvent e) {
    MaterialDets curDets = (MaterialDets)butCreator.getCurrentObject();
    try {
      if (lstSuppliers.getSelectedIndex() > -1)
        setMaterialDetails( (String) lstSuppliers.getSelectedValue());
    } catch (ArrayIndexOutOfBoundsException ex) {
      //doen niks
    }

    // Kry klaar selected list
    Object[] toObjects = curDets.getSuppliers().toArray();
    String[] toStrings = new String[curDets.getSuppliers().size()];
    for (int i = 0; i < toObjects.length; i++)
      toStrings[i] = (String)toObjects[i];

    // Kry available list
    ArrayList supList = db_conn.getSupplierNames();
    for (int i = 0; i < toStrings.length; i++) supList.remove(toStrings[i]);
    String[] fromStrings = new String[supList.size()];
    for (int i = 0; i < supList.size(); i++)
      fromStrings[i] = (String)supList.get(i);

    StringSelector2 selector = new StringSelector2(fromStrings, "Select Suppliers", toStrings);
    TreeSet toSet = selector.getSelectedFields();

    // remove suppliers wat nie gekies is nie
    Iterator it = curDets.getSuppliers().iterator();
    while (it.hasNext()) {
      String supplier = (String)it.next();
      if (!toSet.contains(supplier)) {
        String mes = "Do you want to remove the details of " + supplier + "?";
        if (JOptionPane.showConfirmDialog(thisDialog, mes, "Delete Supplier", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
          listModel.remove(listModel.indexOf(supplier));
          it.remove();
          MaterialDets matDets = ArtofDB.getCurrentDB().getMaterial(txtItemCode.getText());
          ArtofDB.getCurrentDB().deleteMaterialDetails(matDets.getMaterialID(), supplier);
        }
      }
    }

    // skep nuwe suppliers
    it = toSet.iterator();
    while (it.hasNext()) {
      String supplier = (String)it.next();
      if (!curDets.containsSupplier(supplier)) {
        MaterialValues val = new MaterialValues();
        curDets.addMaterialValues(val, supplier);
        listModel.addElement(supplier);
      }
    }

    try {
      //lastSupplier = null;
      lstSuppliers.setSelectedIndex(0);
    } catch (ArrayIndexOutOfBoundsException x) {
      updateMaterialDetails(null);
    }
  }

  void btnColor_actionPerformed(ActionEvent e) {
    btnColor.setBackground(new JColorChooser().showDialog(null, "Color", btnColor.getBackground()));
  }

  void txtLength_focusLost(FocusEvent e) {
    if (type == MaterialDets.MAT_BOARD || type == MaterialDets.MAT_GB) {
      try {
        MaterialDets curDets = (MaterialDets)butCreator.getCurrentObject();
        MaterialValues val = curDets.getMaterialValues((String)lstSuppliers.getSelectedValue());

        try {
          float newLength = (new Float(txtLength.getText())).floatValue();
          float newArea = newLength * val.getWidth() / 1000000;
          if (val.getCost() > 0 && newArea < 0.001)
            throw new NumberFormatException();

          float oldArea = val.getLength() * val.getWidth() / 1000000;
          float cost = val.getCost() * oldArea;
          val.setLength(newLength);
          float costM2 = cost / newArea;
          val.setCost((float)costM2);

        } catch (NumberFormatException x) {
          String mes = "Invalid Height was specified";
          JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateMaterialDetails(curDets);

      } catch (NullPointerException nx) {
        return;
      }
    }
  }

  void txtWidth_focusLost(FocusEvent e) {
    if (type == MaterialDets.MAT_BOARD || type == MaterialDets.MAT_GB) {
      try {
        MaterialDets curDets = (MaterialDets)butCreator.getCurrentObject();
        MaterialValues val = curDets.getMaterialValues((String)lstSuppliers.getSelectedValue());

        try {
          float area = val.getLength() * val.getWidth() / 1000000;
          float cost = val.getCost() * area;
          val.setWidth((new Float(txtWidth.getText())).floatValue());
          area = val.getLength() * val.getWidth() / 1000000;
          float costM2 = cost / area;
          val.setCost((float)costM2);
          //costPanel.setData(val, (String)lstSuppliers.getSelectedValue());

        } catch (NumberFormatException x) {
          String mes = "Invalid Width was specified";
          JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateMaterialDetails(curDets);

      } catch (NullPointerException nx) {
        return;
      }
    }
  }

  void btnProfile_actionPerformed(ActionEvent e) {
    MaterialDets curDets = (MaterialDets)butCreator.getCurrentObject();
    if (curDets != null) {
      EditDialog d = EditDialog.getInstance();
      d.setImage(getCurrentImage());
      d.setVisible(true);
      BufferedImage image = d.getImage();
      if (image != null) {
        pnlImage.setImage(image);
        curDets.setSampleImage(image);
      }
    }
  }

  private BufferedImage getCurrentImage(MaterialDets curDets) {
    try {
      BufferedImage image = curDets.getSampleImage();
      return image;

    } catch (NullPointerException e) {
      return null;
    }
  }

  private BufferedImage getCurrentImage() {
    try {
      MaterialDets curDets = (MaterialDets)butCreator.getCurrentObject();
      return getCurrentImage(curDets);

    } catch (NullPointerException e) {
      return null;
    }
  }
}
