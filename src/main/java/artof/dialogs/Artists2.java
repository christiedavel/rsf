package artof.dialogs;
import artof.database.*;
import artof.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.print.*;
import java.awt.font.*;
import java.awt.geom.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Artists2 extends JDialog implements DataFunctions, Printable {
  private JDialog thisDialog;
  private ArtofDB db_conn;
  private ArrayList artistList;
  private ButCreator butCreator;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel jLabel1 = new JLabel();
  private CustomTextField txtLastName = new CustomTextField();
  private JLabel jLabel2 = new JLabel();
  private CustomTextField txtFirstName = new CustomTextField();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel4 = new JLabel();
  private CustomTextField txtTitle = new CustomTextField();
  private CustomTextField txtDOB = new CustomTextField();
  private JLabel jLabel5 = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private CustomTextField txtOrigin = new CustomTextField();
  private CustomTextField txtProfile = new CustomTextField();
  private JLabel jLabel7 = new JLabel();
  private CustomTextField txtAdd1 = new CustomTextField();
  private CustomTextField txtAdd2 = new CustomTextField();
  private CustomTextField txtAdd3 = new CustomTextField();
  private CustomTextField txtAdd4 = new CustomTextField();
  private JLabel jLabel8 = new JLabel();
  private JLabel jLabel9 = new JLabel();
  private JLabel jLabel10 = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private CustomTextField txtCell = new CustomTextField();
  private CustomTextField txtTel = new CustomTextField();
  private CustomTextField txtFax = new CustomTextField();
  private CustomTextField txtEmail = new CustomTextField();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JLabel jLabel12 = new JLabel();
  private JTextArea txtWorks = new JTextArea();
  private JLabel jLabel13 = new JLabel();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JTextArea txtTraining = new JTextArea();
  private JLabel jLabel14 = new JLabel();
  private JLabel jLabel15 = new JLabel();
  private JLabel jLabel16 = new JLabel();
  private JScrollPane jScrollPane3 = new JScrollPane();
  private JTextArea txtExhibitions = new JTextArea();
  private JScrollPane jScrollPane4 = new JScrollPane();
  private JTextArea txtAwards = new JTextArea();
  private JScrollPane jScrollPane5 = new JScrollPane();
  private JTextArea txtSummary = new JTextArea();

  public Artists2(ArtofDB db) {
    thisDialog = this;
    db_conn = db;
    artistList = db_conn.getArtists(UserSettings.ARTIST_SORTER);

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
          db_conn.saveArtists(artistList);
      }
    });

    setSize(550, 470);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public Artists2(ArtofDB db, int artistID) {
    db_conn = db;
    artistList = db_conn.getArtists(UserSettings.ARTIST_SORTER);

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
          db_conn.saveArtists(artistList);
      }
    });

    setLocation(100, 100);
    setSize(550, 470);
    butCreator.findField("ArtistID", String.valueOf(artistID));
    setVisible(true);
  }

  public int getSelectedArtist() {
    return ((ArtistDets)butCreator.getCurrentObject()).getArtistID();
  }

  public boolean setData(Object obj) {
    if (obj != null) {
      ArtistDets dets = (ArtistDets)obj;
      dets.setTitle(txtTitle.getText());

      if (txtLastName.getText() == null || txtLastName.getText().equals("")) {
        String mes = "A last name muse be filled in";
        JOptionPane.showMessageDialog(thisDialog, mes, "Error", JOptionPane.ERROR_MESSAGE);
        return false;
      }
      dets.setSurname(txtLastName.getText());

      dets.setName(txtFirstName.getText());
      dets.setDOB(txtDOB.getText());
      dets.setOrigin(txtOrigin.getText());
      dets.setProfile(txtProfile.getText());
      dets.setAdd1(txtAdd1.getText());
      dets.setAdd2(txtAdd2.getText());
      dets.setAdd3(txtAdd3.getText());
      dets.setAdd4(txtAdd4.getText());
      dets.setTel(txtTel.getText());
      dets.setFax(txtFax.getText());
      dets.setEmail(txtEmail.getText());
      dets.setCel(txtCell.getText());
      dets.setWorks(txtWorks.getText());
      dets.setExhibitions(txtExhibitions.getText());
      dets.setTraining(txtTraining.getText());
      dets.setAwards(txtAwards.getText());
      dets.setSummary(txtSummary.getText());
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

      txtOrigin.setText("");
      txtOrigin.setEditable(false);

      txtProfile.setText("");
      txtProfile.setEditable(false);

      txtAdd1.setText("");
      txtAdd1.setEditable(false);

      txtAdd2.setText("");
      txtAdd2.setEditable(false);

      txtAdd3.setText("");
      txtAdd3.setEditable(false);

      txtAdd4.setText("");
      txtAdd4.setEditable(false);

      txtTel.setText("");
      txtTel.setEditable(false);

      txtFax.setText("");
      txtFax.setEditable(false);

      txtEmail.setText("");
      txtEmail.setEditable(false);

      txtCell.setText("");
      txtCell.setEditable(false);

      txtWorks.setText("");
      txtWorks.setEditable(false);

      txtExhibitions.setText("");
      txtExhibitions.setEditable(false);

      txtTraining.setText("");
      txtTraining.setEditable(false);

      txtAwards.setText("");
      txtAwards.setEditable(false);

      txtSummary.setText("");
      txtSummary.setEditable(false);
    }
    else {
      ArtistDets dets = (ArtistDets)obj;
      txtTitle.setText(dets.getTitle());
      txtTitle.setEditable(true);

      txtFirstName.setText(dets.getName());
      txtFirstName.setEditable(true);

      txtLastName.setText(dets.getSurname());
      txtLastName.setEditable(true);

      txtDOB.setText(dets.getDOB());
      txtDOB.setEditable(true);

      txtOrigin.setText(dets.getOrigin());
      txtOrigin.setEditable(true);

      txtProfile.setText(dets.getProfile());
      txtProfile.setEditable(true);

      txtAdd1.setText(dets.getAdd1());
      txtAdd1.setEditable(true);

      txtAdd2.setText(dets.getAdd2());
      txtAdd2.setEditable(true);

      txtAdd3.setText(dets.getAdd3());
      txtAdd3.setEditable(true);

      txtAdd4.setText(dets.getAdd4());
      txtAdd4.setEditable(true);

      txtTel.setText(dets.getTel());
      txtTel.setEditable(true);

      txtFax.setText(dets.getFax());
      txtFax.setEditable(true);

      txtEmail.setText(dets.getEmail());
      txtEmail.setEditable(true);

      txtCell.setText(dets.getCel());
      txtCell.setEditable(true);

      txtWorks.setText(dets.getWorks());
      txtWorks.setEditable(true);

      txtExhibitions.setText(dets.getExhibitions());
      txtExhibitions.setEditable(true);

      txtTraining.setText(dets.getTraining());
      txtTraining.setEditable(true);

      txtAwards.setText(dets.getAwards());
      txtAwards.setEditable(true);

      txtSummary.setText(dets.getSummary());
      txtSummary.setEditable(true);
    }
  }

  public ListIterator getMainIterator() {
    return artistList.listIterator();
  }

  public Object getNewItem() {
    return new ArtistDets();
  }

  public void saveMainList() {
    db_conn.saveArtists(artistList);
  }

  public void refreshMainList(String orderSQL) {
    if (orderSQL != null)
      UserSettings.ARTIST_SORTER = orderSQL;
    artistList = db_conn.getArtists(orderSQL);
  }

  public void closeMainList() {
    hide();
  }

  public void deleteItem(Object obj) {
    db_conn.deleteArtists(((ArtistDets)obj).getArtistID());
    artistList.remove(obj);
  }

  public void printDieFokker() {
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    Book book = new Book();
    int numPages = artistList.size() / 5;
    if (numPages * 5 < artistList.size()) numPages++;
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
    TextLayout layout = new TextLayout("Artists as at " + Utils.getDatumStr(Utils.getCurrentDate()), g2d.getFont(), frc);
    Rectangle2D bounds = layout.getBounds();
    curY += bounds.getHeight();
    layout.draw(g2d, (float)(pageWidth / 2 -  bounds.getWidth() / 2), (float)curY);

    curY += bounds.getHeight() * 2;
    String text = String.valueOf(pageIndex * 5 + 1) + " to " + Math.min(artistList.size(), (pageIndex + 1) * 5);
    text += " of " + artistList.size();
    layout = new TextLayout(text, g2d.getFont(), frc);
    bounds = layout.getBounds();
    curY += bounds.getHeight();
    layout.draw(g2d, (float)(pageWidth / 2 -  bounds.getWidth() / 2), (float)curY);

    g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 10));
    curY *= 2;
    for (int i = pageIndex * 5; i < (pageIndex + 1) * 5; i++) {
      ArtistDets artist;
      try {
        artist = (ArtistDets)artistList.get(i);
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
        layout = new TextLayout(artist.getSurname(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Name:", g2d.getFont(), frc);
      layout.draw(g2d, (float)pageWidth / 2, (float)curY);
      try {
        layout = new TextLayout(artist.getName(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Tweede reel
      curY += height * 1.5;

      layout = new TextLayout("Title:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(artist.getTitle(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("DOB:", g2d.getFont(), frc);
      layout.draw(g2d, (float)pageWidth / 2, (float)curY);
      try {
        layout = new TextLayout(artist.getDOB(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Derde reel
      curY += height * 1.5;

      layout = new TextLayout("Origin:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(artist.getOrigin(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Profile:", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(artist.getProfile(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
        layout = new TextLayout("None", g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      }

      // Vierde reel
      curY += height * 1.5;

      layout = new TextLayout("Address:", g2d.getFont(), frc);
      layout.draw(g2d, 0, (float)curY);
      try {
        layout = new TextLayout(artist.getAdd1(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Cell No:", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(artist.getCel(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Vyfde reel
      curY += height * 1.5;

      try {
        layout = new TextLayout(artist.getAdd2(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Tel No:", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(artist.getTel(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Sesde reel
      curY += height * 1.5;

      try {
        layout = new TextLayout(artist.getAdd3(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("Fax No:", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(artist.getFax(), g2d.getFont(), frc);
        layout.draw(g2d, (float)(indent + pageWidth / 2), (float)curY);
      } catch (IllegalArgumentException e) {
      }

      // Sewende reel
      curY += height * 1.5;

      try {
        layout = new TextLayout(artist.getAdd4(), g2d.getFont(), frc);
        layout.draw(g2d, (float)indent, (float)curY);
      } catch (IllegalArgumentException e) {
      }

      layout = new TextLayout("E-mail", g2d.getFont(), frc);
      layout.draw(g2d, (float)(pageWidth / 2), (float)curY);
      try {
        layout = new TextLayout(artist.getEmail(), g2d.getFont(), frc);
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
    String[] fromStrings = new String[22];
    fromStrings[0] = "Title";
    fromStrings[1] = "Firstname";
    fromStrings[2] = "Lastname";
    fromStrings[3] = "Date of birth";
    fromStrings[4] = "Profile";
    fromStrings[5] = "Origin";

    fromStrings[6] = "Addres line 1";
    fromStrings[7] = "Addres line 2";
    fromStrings[8] = "Addres line 3";
    fromStrings[9] = "Addres line 4";
    fromStrings[10] = "Postal code";
    fromStrings[11] = "Tel No";
    fromStrings[12] = "Fax No";
    fromStrings[13] = "E-mail";
    fromStrings[14] = "Cel No";

    return fromStrings;
  }

  public Hashtable getSortDBMap() {
    Hashtable table = new Hashtable();

    table.put("Title", "Title");
    table.put("Firstname", "Name");
    table.put("Lastname", "Surname");
    table.put("Date of birth", "DOB");
    table.put("Profile", "Profile");
    table.put("Origin", "Origin");

    table.put("Addres line 1", "Add1");
    table.put("Addres line 2", "Add2");
    table.put("Addres line 3", "Add3");
    table.put("Addres line 4", "Add4");
    table.put("Postal code", "PostalCode");
    table.put("Tel No", "Tel");
    table.put("Fax No", "Fax");
    table.put("E-mail", "Email");
    table.put("Cel No", "Cel");

    return table;
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    jLabel1.setText("Last Name");
    jLabel2.setText("First Name");
    jLabel3.setText("Title");
    jLabel4.setText("Date of Birth");
    jLabel5.setText("Origin");
    jLabel6.setText("Profile");
    jLabel7.setText("Address");
    jLabel8.setText("Cell No");
    jLabel9.setText("Tel No");
    jLabel10.setText("Fax No");
    jLabel11.setText("E-mail");
    jLabel12.setText("Works");
    jLabel13.setText("Training");
    txtTraining.setLineWrap(true);
    txtWorks.setLineWrap(true);
    jLabel14.setText("Summary");
    jLabel15.setText("Exhibitions");
    jLabel16.setText("Awards");
    txtExhibitions.setLineWrap(true);
    txtAwards.setLineWrap(true);
    txtSummary.setLineWrap(true);
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Artists");
    jScrollPane2.setPreferredSize(new Dimension(169, 50));
    jScrollPane1.setPreferredSize(new Dimension(169, 50));
    jScrollPane3.setPreferredSize(new Dimension(165, 50));
    jScrollPane4.setPreferredSize(new Dimension(165, 50));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jLabel1,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtLastName,    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(jLabel2,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(txtFirstName,    new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(jLabel3,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel4,   new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(txtTitle,    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtDOB,    new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(jLabel5,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel6,   new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(txtOrigin,    new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtProfile,    new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(jLabel7,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtAdd1,     new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtAdd2,    new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtAdd3,    new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtAdd4,    new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel8,   new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 10, 2, 5), 0, 0));
    jPanel1.add(jLabel9,   new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 10, 2, 5), 0, 0));
    jPanel1.add(jLabel10,   new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 10, 2, 5), 0, 0));
    jPanel1.add(jLabel11,   new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 10, 2, 5), 0, 0));
    jPanel1.add(txtCell,   new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtTel,   new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtFax,   new GridBagConstraints(3, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 2, 5), 0, 0));
    jPanel1.add(txtEmail,   new GridBagConstraints(3, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane1,      new GridBagConstraints(1, 7, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 5, 2, 5), 0, 0));
    jScrollPane1.getViewport().add(txtWorks, null);
    jPanel1.add(jLabel12,    new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel13,  new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jScrollPane2,       new GridBagConstraints(1, 8, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 5, 2, 5), 0, 0));
    jScrollPane2.getViewport().add(txtTraining, null);
    jPanel1.add(jLabel14,  new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel15,   new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel16,   new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 5), 0, 0));
    jPanel1.add(jScrollPane3,    new GridBagConstraints(3, 7, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 5, 2, 5), 0, 0));
    jScrollPane3.getViewport().add(txtExhibitions, null);
    jPanel1.add(jScrollPane4,   new GridBagConstraints(3, 8, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 5, 2, 5), 0, 0));
    jScrollPane4.getViewport().add(txtAwards, null);
    jPanel1.add(jScrollPane5,   new GridBagConstraints(1, 9, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 5, 2, 5), 0, 0));
    jScrollPane5.getViewport().add(txtSummary, null);
  }
}