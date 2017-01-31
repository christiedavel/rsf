package artof.dialogs;
import artof.database.ArtofDB;
import artof.database.ClientDets;
import artof.database.DataFunctions;
import artof.utils.CustomTextField;
import artof.utils.UserSettings;
import artof.utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Clients2 extends JDialog implements DataFunctions, Printable {
  private JDialog thisDialog;
  private ArtofDB db_conn;
  private ArrayList clientList;
  private ButCreator butCreator;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private CustomTextField txtFirstName = new CustomTextField();
  private JLabel jLabel2 = new JLabel();
  private CustomTextField txtLastName = new CustomTextField();
  private JLabel jLabel3 = new JLabel();
  private CustomTextField txtTitle = new CustomTextField();
  private JLabel jLabel4 = new JLabel();
  private CustomTextField txtDOB = new CustomTextField();
  private JLabel jLabel5 = new JLabel();
  private JComboBox cbxPostTo = new JComboBox();
  private JLabel jLabel6 = new JLabel();
  private CustomTextField txtProfile = new CustomTextField();
  private JLabel jLabel7 = new JLabel();
  private CustomTextField txtAssociate = new CustomTextField();
  private JLabel jLabel8 = new JLabel();
  private JLabel jLabel9 = new JLabel();
  private JPanel jPanel2 = new JPanel();
  private JLabel jLabel10 = new JLabel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private CustomTextField txtHomeAdd1 = new CustomTextField();
  private Border border1;
  private JPanel jPanel3 = new JPanel();
  private Border border2;
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private JLabel jLabel11 = new JLabel();
  private CustomTextField txtWorkAdd1 = new CustomTextField();
  private CustomTextField txtHomeAdd2 = new CustomTextField();
  private CustomTextField txtHomeAdd3 = new CustomTextField();
  private CustomTextField txtHomeAdd4 = new CustomTextField();
  private CustomTextField txtWorkAdd2 = new CustomTextField();
  private CustomTextField txtWorkAdd3 = new CustomTextField();
  private CustomTextField txtWorkAdd4 = new CustomTextField();
  private JLabel jLabel12 = new JLabel();
  private JLabel jLabel13 = new JLabel();
  private CustomTextField txtHomeCode = new CustomTextField();
  private CustomTextField txtWorkCode = new CustomTextField();
  private JLabel jLabel14 = new JLabel();
  private CustomTextField txtHomeCell = new CustomTextField();
  private JLabel jLabel15 = new JLabel();
  private CustomTextField txtWorkCell = new CustomTextField();
  private JLabel jLabel16 = new JLabel();
  private JLabel jLabel17 = new JLabel();
  private JLabel jLabel18 = new JLabel();
  private JLabel jLabel19 = new JLabel();
  private JLabel jLabel20 = new JLabel();
  private JLabel jLabel21 = new JLabel();
  private CustomTextField txtHomeTel = new CustomTextField();
  private CustomTextField txtHomeFax = new CustomTextField();
  private CustomTextField txtHomeEmail = new CustomTextField();
  private CustomTextField txtWorkTel = new CustomTextField();
  private CustomTextField txtWorkFax = new CustomTextField();
  private CustomTextField txtWorkEmail = new CustomTextField();

  public Clients2(ArtofDB db) {
    thisDialog = this;
    db_conn = db;
    clientList = db_conn.getClients(UserSettings.CLIENT_SORTER);
    cbxPostTo.addItem("Home");
    cbxPostTo.addItem("Work");

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    // Al die buttons
     butCreator = new ButCreator(this);
     getContentPane().add(butCreator.getBrowsePanel(), BorderLayout.SOUTH);
     getContentPane().add(butCreator.getDataPanel(), BorderLayout.NORTH);

     addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
         if (JOptionPane.showConfirmDialog(thisDialog, "Do you want to save changes", "Save",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
           db_conn.saveClients(clientList);
       }
     });

    setSize(570, 520);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public Clients2(ArtofDB db, int clientID) {
    db_conn = db;
    clientList = db_conn.getClients(UserSettings.CLIENT_SORTER);
    cbxPostTo.addItem("Home");
    cbxPostTo.addItem("Work");

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    // Al die buttons
     butCreator = new ButCreator(this);
     getContentPane().add(butCreator.getBrowsePanel(), BorderLayout.SOUTH);
     getContentPane().add(butCreator.getDataPanel(), BorderLayout.NORTH);

     addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
         if (JOptionPane.showConfirmDialog(thisDialog, "Do you want to save changes", "Save",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
           db_conn.saveClients(clientList);
       }
     });

    setLocation(100, 100);
    setSize(570, 520);
    butCreator.findField("ClientID", String.valueOf(clientID));
    setVisible(true);
  }

  public int getSelectedClient() {
    return ((ClientDets)butCreator.getCurrentObject()).getClientID();
  }

  public boolean setData(Object obj) {
    if (obj != null) {
      ClientDets dets = (ClientDets)obj;
      dets.setTitle(txtTitle.getText());

      if (txtFirstName.getText() == null || txtFirstName.getText().equals("")) {
        String mes = "A firstname muse be filled in";
        JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
        return false;
      }
      dets.setName(txtFirstName.getText());

      if (txtLastName.getText() == null || txtLastName.getText().equals("")) {
        String mes = "A surname muse be filled in";
        JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
        return false;
      }
      dets.setSurname(txtLastName.getText());

      dets.setDOB(txtDOB.getText());
      dets.setProfile(txtProfile.getText());
      dets.setAssociate(txtAssociate.getText());
      dets.setPostTo(cbxPostTo.getSelectedIndex());
      dets.setHomeAdd1(txtHomeAdd1.getText());
      dets.setHomeAdd2(txtHomeAdd2.getText());
      dets.setHomeAdd3(txtHomeAdd3.getText());
      dets.setHomeAdd4(txtHomeAdd4.getText());
      dets.setHomeCode(txtHomeCode.getText());
      dets.setHomeCell(txtHomeCell.getText());
      dets.setHomeTel(txtHomeTel.getText());
      dets.setHomeFax(txtHomeFax.getText());
      dets.setHomeEmail(txtHomeEmail.getText());
      dets.setWorkAdd1(txtWorkAdd1.getText());
      dets.setWorkAdd2(txtWorkAdd2.getText());
      dets.setWorkAdd3(txtWorkAdd3.getText());
      dets.setWorkAdd4(txtWorkAdd4.getText());
      dets.setWorkCode(txtWorkCode.getText());
      dets.setWorkCell(txtWorkCell.getText());
      dets.setWorkTel(txtWorkTel.getText());
      dets.setWorkFax(txtWorkFax.getText());
      dets.setWorkEmail(txtWorkEmail.getText());
    }
    return true;
  }

  public void updateData(Object obj) {
    if (obj == null) {
      txtTitle.setText("");
      txtTitle.setEditable(false);

      txtFirstName.setText("");
      txtFirstName.setEditable(false);

      txtLastName.setText("");
      txtLastName.setEditable(false);

      txtDOB.setText("");
      txtDOB.setEditable(false);

      txtProfile.setText("");
      txtProfile.setEditable(false);

      txtAssociate.setText("");
      txtAssociate.setEditable(false);

      cbxPostTo.setSelectedIndex(-1);
      cbxPostTo.setEditable(false);

      txtHomeAdd1.setText("");
      txtHomeAdd1.setEditable(false);

      txtHomeAdd2.setText("");
      txtHomeAdd2.setEditable(false);

      txtHomeAdd3.setText("");
      txtHomeAdd3.setEditable(false);

      txtHomeAdd4.setText("");
      txtHomeAdd4.setEditable(false);

      txtHomeCode.setText("");
      txtHomeCode.setEditable(false);

      txtHomeCell.setText("");
      txtHomeCell.setEditable(false);

      txtHomeTel.setText("");
      txtHomeTel.setEditable(false);

      txtHomeFax.setText("");
      txtHomeFax.setEditable(false);

      txtHomeEmail.setText("");
      txtHomeEmail.setEditable(false);

      txtWorkAdd1.setText("");
      txtWorkAdd1.setEditable(false);

      txtWorkAdd2.setText("");
      txtWorkAdd2.setEditable(false);

      txtWorkAdd3.setText("");
      txtWorkAdd3.setEditable(false);

      txtWorkAdd4.setText("");
      txtWorkAdd4.setEditable(false);

      txtWorkCode.setText("");
      txtWorkCode.setEditable(false);

      txtWorkCell.setText("");
      txtWorkCell.setEditable(false);

      txtWorkTel.setText("");
      txtWorkTel.setEditable(false);

      txtWorkFax.setText("");
      txtWorkFax.setEditable(false);

      txtWorkEmail.setText("");
      txtWorkEmail.setEditable(false);
    }
    else {
      ClientDets dets = (ClientDets)obj;
      txtTitle.setText(dets.getTitle());
      txtTitle.setEditable(true);

      txtFirstName.setText(dets.getName());
      txtFirstName.setEditable(true);

      txtLastName.setText(dets.getSurname());
      txtLastName.setEditable(true);

      txtDOB.setText(dets.getDOB());
      txtDOB.setEditable(true);

      txtProfile.setText(dets.getProfile());
      txtProfile.setEditable(true);

      txtAssociate.setText(dets.getAssociate());
      txtAssociate.setEditable(true);

      cbxPostTo.setEditable(true);
      try {
        cbxPostTo.setSelectedIndex(dets.getPostTo());
      } catch (IllegalArgumentException e) {
        cbxPostTo.setSelectedIndex(-1);
      }

      txtHomeAdd1.setText(dets.getHomeAdd1());
      txtHomeAdd1.setEditable(true);

      txtHomeAdd2.setText(dets.getHomeAdd2());
      txtHomeAdd2.setEditable(true);

      txtHomeAdd3.setText(dets.getHomeAdd3());
      txtHomeAdd3.setEditable(true);

      txtHomeAdd4.setText(dets.getHomeAdd4());
      txtHomeAdd4.setEditable(true);

      txtHomeCode.setText(dets.getHomeCode());
      txtHomeCode.setEditable(true);

      txtHomeCell.setText(dets.getHomeCell());
      txtHomeCell.setEditable(true);

      txtHomeTel.setText(dets.getHomeTel());
      txtHomeTel.setEditable(true);

      txtHomeFax.setText(dets.getHomeFax());
      txtHomeFax.setEditable(true);

      txtHomeEmail.setText(dets.getHomeEmail());
      txtHomeEmail.setEditable(true);

      txtWorkAdd1.setText(dets.getWorkAdd1());
      txtWorkAdd1.setEditable(true);

      txtWorkAdd2.setText(dets.getWorkAdd2());
      txtWorkAdd2.setEditable(true);

      txtWorkAdd3.setText(dets.getWorkAdd3());
      txtWorkAdd3.setEditable(true);

      txtWorkAdd4.setText(dets.getWorkAdd4());
      txtWorkAdd4.setEditable(true);

      txtWorkCode.setText(dets.getWorkCode());
      txtWorkCode.setEditable(true);

      txtWorkCell.setText(dets.getWorkCell());
      txtWorkCell.setEditable(true);

      txtWorkTel.setText(dets.getWorkTel());
      txtWorkTel.setEditable(true);

      txtWorkFax.setText(dets.getWorkFax());
      txtWorkFax.setEditable(true);

      txtWorkEmail.setText(dets.getWorkEmail());
      txtWorkEmail.setEditable(true);
    }
  }

  public ListIterator getMainIterator() {
    return clientList.listIterator();
  }

  public Object getNewItem() {
    return new ClientDets();
  }

  public void saveMainList() {
    db_conn.saveClients(clientList);
  }

  public void refreshMainList(String orderSQL) {
    if (orderSQL != null)
      UserSettings.CLIENT_SORTER = orderSQL;
    clientList = db_conn.getClients(orderSQL);
  }

  public void closeMainList() {
    hide();
  }

  public void deleteItem(Object obj) {
    db_conn.deleteClients(((ClientDets)obj).getClientID());
    clientList.remove(obj);
  }

  public void printDieFokker() {
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    Book book = new Book();
    int numPages = clientList.size() / 4;
    if (numPages * 4 < clientList.size()) numPages++;
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
    TextLayout layout = new TextLayout("Clients as at " + Utils.getDatumStr(Utils.getCurrentDate()), g2d.getFont(), frc);
    Rectangle2D bounds = layout.getBounds();
    curY += bounds.getHeight();
    layout.draw(g2d, (float)(pageWidth / 2 -  bounds.getWidth() / 2), (float)curY);

    curY += bounds.getHeight() * 2;
    String text = String.valueOf(pageIndex * 4 + 1) + " to " + Math.min(clientList.size(), (pageIndex + 1) * 4);
    text += " of " + clientList.size();
    layout = new TextLayout(text, g2d.getFont(), frc);
    bounds = layout.getBounds();
    curY += bounds.getHeight();
    layout.draw(g2d, (float)(pageWidth / 2 -  bounds.getWidth() / 2), (float)curY);

    g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
    curY *= 2;
    for (int i = pageIndex * 4; i < (pageIndex + 1) * 4; i++) {
      ClientDets client;
      try {
        client = (ClientDets)clientList.get(i);
      } catch (IndexOutOfBoundsException e) {
        break;
      }
      layout = new TextLayout("Home Address: ", g2d.getFont(), frc);
      double indent = layout.getBounds().getWidth() * 1.1;
      double height;

      // Eerste reel
      layout = new TextLayout("Surname:", g2d.getFont(), frc);
      height = layout.getBounds().getHeight();
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(client.getSurname(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Name:", g2d.getFont(), frc);
      layout.draw(g2d, (float)pageWidth / 2, (float)curY);
      try {
        layout = new TextLayout(client.getName(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Tweede reel
      curY += height * 1.5;

      layout = new TextLayout("Title:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(client.getTitle(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("DOB:", g2d.getFont(), frc);
      layout.draw(g2d, (float)pageWidth / 2, (float)curY);
      try {
        layout = new TextLayout(client.getDOB(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Derde reel
      curY += height * 1.5;

      layout = new TextLayout("Home Address:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(client.getHomeAdd1(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Work Address:", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(client.getWorkAdd1(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
        layout = new TextLayout("None", g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      }

      // Vierde reel
      curY += height * 1.5;

      try {
        layout = new TextLayout(client.getHomeAdd2(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      try {
        layout = new TextLayout(client.getWorkAdd2(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Vyfde reel
      curY += height * 1.5;

      try {
        layout = new TextLayout(client.getHomeAdd3(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      try {
        layout = new TextLayout(client.getWorkAdd3(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Sesde reel
      curY += height * 1.5;

      try {
        layout = new TextLayout(client.getHomeAdd4(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      try {
        layout = new TextLayout(client.getWorkAdd4(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Sewende reel
      curY += height * 1.5;

      layout = new TextLayout("Postal Code:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(client.getHomeCode(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Postal Code", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(client.getWorkCode(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Agste reel
      curY += height * 1.5;

      layout = new TextLayout("Home Cell:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(client.getHomeCell(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Work Cell", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(client.getWorkCell(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Neende reel
      curY += height * 1.5;

      layout = new TextLayout("Home Tel:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(client.getHomeTel(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Work Tel", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(client.getWorkTel(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Tiende reel
      curY += height * 1.5;

      layout = new TextLayout("Home Fax:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(client.getHomeFax(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Work Fax", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(client.getWorkFax(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Elfde reel
      curY += height * 1.5;

      layout = new TextLayout("Home Email:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(client.getHomeEmail(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Work Email", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(client.getWorkEmail(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      curY += bounds.getHeight() * 4;
      if (curY > pageHeight)
        break;
    }

    return Printable.PAGE_EXISTS;
  }


  public String[] getSortList() {
    //String[] fromStrings = new String[22];
    String[] fromStrings = new String[4];
    fromStrings[0] = "Title";
    fromStrings[1] = "Firstname";
    fromStrings[2] = "Lastname";
    fromStrings[3] = "Date of birth";
    /*fromStrings[4] = "Profile";
    fromStrings[5] = "Associate";

    fromStrings[6] = "Home addres line 1";
    fromStrings[7] = "Home addres line 2";
    fromStrings[8] = "Home addres line 3";
    fromStrings[9] = "Home addres line 4";
    fromStrings[10] = "Home postal code";
    fromStrings[11] = "Home Tel No";
    fromStrings[12] = "Home Fax No";
    fromStrings[13] = "Home e-mail";

    fromStrings[14] = "Work addres line 1";
    fromStrings[15] = "Work addres line 2";
    fromStrings[16] = "Work addres line 3";
    fromStrings[17] = "Work addres line 4";
    fromStrings[18] = "Work postal code";
    fromStrings[19] = "Work Tel No";
    fromStrings[20] = "Work Fax No";
    fromStrings[21] = "Work e-mail";*/

    return fromStrings;
  }

  public Hashtable getSortDBMap() {
    Hashtable table = new Hashtable();

    table.put("Title", "Title");
    table.put("Firstname", "Name");
    table.put("Lastname", "Surname");
    table.put("Date of birth", "DOB");
    /*table.put("Profile", "Profile");
    table.put("Associate", "Associate");

    table.put("Home addres line 1", "HomeAdd1");
    table.put("Home addres line 2", "HomeAdd2");
    table.put("Home addres line 3", "HomeAdd3");
    table.put("Home addres line 4", "HomeAdd4");
    table.put("Home postal code", "HomeCode");
    table.put("Home Tel No", "HomeTel");
    table.put("Home Fax No", "HomeFax");
    table.put("Home e-mail", "HomeEmail");

    table.put("Work addres line 1", "WorkAdd1");
    table.put("Work addres line 2", "WorkAdd2");
    table.put("Work addres line 3", "WorkAdd3");
    table.put("Work addres line 4", "WorkAdd4");
    table.put("Work postal code", "WorkCode");
    table.put("Work Tel No", "WorkTel");
    table.put("Work Fax No", "WorkFax");
    table.put("Work e-mail", "WorkEmail");*/

    return table;
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Clients");
    jLabel1.setText("Last Name");
    jLabel2.setText("First Name");
    jLabel3.setText("Title");
    jLabel4.setText("VAT No");
    jLabel5.setText("Post To");
    jLabel6.setText("Profile");
    cbxPostTo.setPreferredSize(new Dimension(4, 21));
    jLabel7.setText("Associate");
    jLabel8.setText("-Home-");
    jLabel9.setText("-Work-");
    jLabel10.setText("Address");
    jPanel2.setLayout(gridBagLayout2);
    jPanel2.setBorder(border1);
    jPanel3.setBorder(border2);
    jPanel3.setLayout(gridBagLayout3);
    jLabel11.setText("Address");
    jLabel12.setText("Code");
    jLabel13.setText("Code");
    jLabel14.setText("Cell No");
    jLabel15.setText("Cell No");
    jLabel16.setText("Tel No");
    jLabel17.setText("Fax No");
    jLabel18.setText("E-mail");
    jLabel19.setText("Tel No");
    jLabel20.setText("Fax No");
    jLabel21.setText("E-mail");
    txtWorkTel.setToolTipText("");
    this.getContentPane().add(jPanel1,  BorderLayout.CENTER);
    jPanel1.add(jLabel1,       new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel3,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtTitle,        new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel4,       new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(txtDOB,       new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel5,       new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel2,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(cbxPostTo,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel6,   new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(txtProfile,   new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel7,  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtAssociate,    new GridBagConstraints(1, 3, 3, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel8,    new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel9,   new GridBagConstraints(2, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    jPanel1.add(jPanel2,     new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 5, 2), 0, 0));
    jPanel2.add(jLabel10,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeAdd1,    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(jPanel3,  new GridBagConstraints(2, 5, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 2, 5, 5), 0, 0));
    jPanel3.add(jLabel11,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(txtWorkAdd1,    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeAdd2,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeAdd3,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeAdd4,  new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(jLabel12,  new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeCode,    new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 50), 0, 0));
    jPanel3.add(txtWorkAdd2,   new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(txtWorkAdd3,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(txtWorkAdd4,   new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(jLabel13,    new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(txtWorkCode,    new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 50), 0, 0));
    jPanel2.add(jLabel14,  new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeCell,   new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(jLabel15,   new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(txtWorkCell,    new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(jLabel16,  new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(jLabel17,   new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(jLabel18,   new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeTel,  new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeFax,  new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel2.add(txtHomeEmail,  new GridBagConstraints(1, 8, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(jLabel19,  new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(jLabel20,   new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(jLabel21,   new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(txtWorkTel,   new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(txtWorkFax,   new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel3.add(txtWorkEmail,   new GridBagConstraints(1, 8, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtFirstName, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtLastName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(5, 5, 5, 5), 0, 0));
  }
}


