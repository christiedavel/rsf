package artof.database;

import artof.utils.*;
import artof.materials.*;
import artof.stock.StockItem;
import artof.designitems.CodeMapper;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.io.*;
import sun.misc.*;
import java.awt.Color;
import artof.dialogs.ProgressDialog;
import com.sun.image.codec.jpeg.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ArtofDB {
  private static ArtofDB artofDB = null;
  private static Connection connection = null;

  private ArtofDB() throws Exception {
    try {
      if (connection == null) {
        String url;
        if (UserSettings2.USE_DATABASE_SERVER)
          url = "jdbc:hsqldb:hsql://" + UserSettings.DATABASE_URL;
        else
          url = "jdbc:hsqldb:rsfdb/data/rsfdb";

        Class.forName("org.hsqldb.jdbcDriver");
        connection = DriverManager.getConnection(url, "sa", "");
        artofDB = this;
      }
    } catch (SQLException e) {
      System.out.println(e.getLocalizedMessage());
    }

  }

  public synchronized static ArtofDB getCurrentDB() {
    try {
      if (artofDB == null) {
        artofDB = new ArtofDB();
      }
    } catch (Exception e) {
      artofDB = null;
    }

    return artofDB;
  }

  public boolean refreshNewConnection() {
    try {
      String url;
      if (UserSettings2.USE_DATABASE_SERVER)
        url = "jdbc:hsqldb:hsql://" + UserSettings.DATABASE_URL;
      else
        url = "jdbc:hsqldb:rsfdb/data/rsfdb";

      Class.forName("org.hsqldb.jdbcDriver");
      Connection newConn = DriverManager.getConnection(url, "sa", "");
      if (connection != null)
        connection.close();
      connection = newConn;
      return true;

    } catch (Exception e) {
      String mes = "Cannot connect to the database specified";
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

  public void closeConnection() {
    try {
      if (connection != null && !UserSettings2.USE_DATABASE_SERVER) {
        Statement s = connection.createStatement();
        s.execute("SHUTDOWN");
        s.close();

        connection.close();
        connection = null;
        artofDB = null;
      }

    } catch (Exception e) {
      String mes = "Error when closing database connection";
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public Connection getConnection() {
    return connection;
  }

  /*--------------------------------- STUFFERS --------------------------------*/

  public boolean checkStuffers() {
    try {
      int curDate = Utils.getCurrentDate();
      String sql = "select count(stuffid) from stuffers where stuffdatum > " + curDate;
      sql += " and regno = '" + UserSettings.registrationNo + "'";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int count = 0;
      if (rs.next()) {
        count = rs.getInt(1);
      }
      s.close();

      boolean checked;
      if (count > 0)
        checked = false;
      else
        checked = true;

      sql = "insert into stuffers (stuffdatum, checker, pccode, regno) values ";
      sql += "(?, ?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, curDate);

      if (checked)
        ps.setInt(2, 1);
      else
        ps.setInt(2, 0);

      ps.setInt(3, 83759);
      ps.setString(4, UserSettings.registrationNo);
      ps.execute();
      ps.close();

      sql = "select count(stuffid), max(stuffid) from stuffers";
      s = connection.createStatement();
      rs = s.executeQuery(sql);
      count = 0;
      int max = 0;
      if (rs.next()) {
        count = rs.getInt(1);
        max = rs.getInt(2);
      }
      s.close();
      if (count > 10000) {
        max -= 10000;
        sql = "delete from stuffers where stuffid < " + max;
        s = connection.createStatement();
        s.execute(sql);
        s.close();
      }

      return checked;

    } catch (SQLException e) {
      return true;
    }
  }

  public int countStuffers() {
    try {
      int curDate = Utils.getCurrentDate();
      String sql = "select count(stuffid) from stuffers";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int count = 0;
      if (rs.next()) {
        count = rs.getInt(1);
      }
      s.close();
      return count;

    } catch (SQLException e) {
      return 0;
    }
  }

  /*--------------------------- Business Preferences --------------------------*/

  // Kry lys van business preferences
  public ArrayList getBusPrefList() {
    ArrayList prefList = new ArrayList();
    try {
      String sql = "select PrevDate, DateCount from buspreferences order by PrevDate, DateCount";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        int intDate = rs.getInt("PrevDate");
        String prefDate;
        if (intDate == UserSettings.MAX_DATE)
          prefDate = "Latest";
        else
          prefDate = Utils.getDatumStr(intDate);
        int dateCount = rs.getInt("DateCount");
        if (dateCount > 1)
          prefDate += ", " + dateCount;
        prefList.add(prefDate);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Business Preferences.  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return prefList;
  }

  // Kry business preferences
  public BusPrefDets getBusPreferences() {
    return getBusPreferences("Latest");
  }

  // Kry business preferences (1)
  public BusPrefDets getBusPreferences(String dateString) {
    if (dateString == null || dateString.equals("Latest"))
      return getBusPreferences(UserSettings.MAX_DATE, 1);

    int k = dateString.indexOf(',');
    if (k > -1) {
      int dateCount = (new Integer(dateString.substring(k + 2))).intValue();
      return getBusPreferences(Utils.getDatumInt(dateString.substring(0, k)), dateCount);
    }
    else
      return getBusPreferences(Utils.getDatumInt(dateString), 1);
  }

  // Kry business preferences (2)
  public BusPrefDets getBusPreferences(int prefDate, int dateCount) {
    BusPrefDets pref = null;
    try {
      String sql = "select * from buspreferences where PrevDate = " + prefDate;
      if (dateCount >= 1)
        sql += " and DateCount = " + dateCount;
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        pref = new BusPrefDets(rs.getInt("BusPrev_ID"));
        pref.setPrefDate(rs.getInt("PrevDate"));
        pref.setDateCount(rs.getInt("DateCount"));
        pref.setVATPerc(rs.getFloat("VATPerc"));
        pref.setVATReg(rs.getString("VATREGISTERED"));
        pref.setVATCode(rs.getString("VATCode"));
        pref.setVATOwnItems(rs.getString("VATOwnItems"));
        pref.setMarkupBoards(rs.getFloat("MarkupBoards"));
        pref.setMarkupFrames(rs.getFloat("MarkupFrames"));
        pref.setMarkupGBs(rs.getFloat("MarkupGBs"));
        pref.setMarkupDecs(rs.getFloat("MarkupDecs"));
        pref.setMUDisc1(rs.getFloat("MUDISCOUNT1"));
        pref.setMUDisc2(rs.getFloat("MUDISCOUNT2"));
        pref.setMUDisc3(rs.getFloat("MUDISCOUNT3"));
        pref.setMUDisc4(rs.getFloat("MUDISCOUNT4"));
        pref.setMUDisc5(rs.getFloat("MUDISCOUNT5"));
        pref.setMUDisc6(rs.getFloat("MUDISCOUNT6"));
        //pref.setMUDiscSelected(rs.getInt("MUDISCOUNTSELECTED"));
        pref.setStretchMinUCM(rs.getFloat("STRETCHMINUCM"));
        pref.setStretchOtherUCM(rs.getFloat("STRETCHOTHERUCM"));
        pref.setStretchMinPrice(rs.getFloat("STRETCHMINPRICE"));
        pref.setStretchOtherPrice(rs.getFloat("STRETCHOTHERPRICE"));
        pref.setpastMinUCM(rs.getFloat("PASTMINUCM"));
        pref.setPastOtherUCM(rs.getFloat("PASTOTHERUCM"));
        pref.setPastMinPrice(rs.getFloat("PASTMINPRICE"));
        pref.setPastOtherPrice(rs.getFloat("PASTOTHERPRICE"));
        pref.setSundriesDisc(rs.getFloat("SUNDRIESDISCOUNT"));
        pref.setSundriesBasic(rs.getFloat("SUNDRIESBASIC"));
        pref.setSundriesLabour(rs.getFloat("SUNDRIESLABOUR"));
        pref.setModified(false);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Business Preferences.  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    if (pref == null && prefDate == UserSettings.MAX_DATE)
      pref = new BusPrefDets();

    return pref;
  }

  // Save business Prefernces
  public void saveBusPreferences(BusPrefDets pref) {
    if (!pref.isModified())
      return;

    Calendar cal = new GregorianCalendar();
    int curDate = 10000*cal.get(Calendar.YEAR) + 100*(cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);

    try {
      connection.setAutoCommit(false);
      connection.commit();

      int newID = 0;
      String sql = "select max(BUSPREV_ID) from buspreferences";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      if (rs.next()) {
        newID = rs.getInt(1);
      }
      s.close();
      newID++;

      int maxID = 0;
      sql = "select BUSPREV_ID from buspreferences where PrevDate = " + UserSettings.MAX_DATE;
      s = connection.createStatement();
      rs = s.executeQuery(sql);
      if (rs.next()) {
        maxID = rs.getInt(1);
      }
      s.close();

      int prevCount = 0;
      sql = "select count(BUSPREV_ID) from buspreferences where PrevDate = " + curDate;
      s = connection.createStatement();
      rs = s.executeQuery(sql);
      if (rs.next()) {
        prevCount = rs.getInt(1);
      }
      s.close();
      prevCount++;

      sql = "Update buspreferences set PrevDate = " + curDate + ", DateCount = " + prevCount;
      sql += " where BUSPREV_ID = " + maxID;
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();

      sql = "insert into buspreferences (BUSPREV_ID, PREVDATE, VATPERC, VATREGISTERED, VATCODE, VATOWNITEMS, ";
      sql += "MARKUPBOARDS, MARKUPFRAMES, MARKUPGBS, MARKUPDECS, MUDISCOUNT1, MUDISCOUNT2, MUDISCOUNT3, ";
      sql += "MUDISCOUNT4, MUDISCOUNT5, MUDISCOUNT6, MUDISCOUNTSELECTED, STRETCHMINUCM, STRETCHOTHERUCM, ";
      sql += "STRETCHMINPRICE, STRETCHOTHERPRICE, PASTMINUCM, PASTOTHERUCM, PASTMINPRICE, PASTOTHERPRICE, ";
      sql += "SUNDRIESDISCOUNT, SUNDRIESBASIC, SUNDRIESLABOUR, DATECOUNT) ";
      sql += "values (";
      sql += newID + ", " + UserSettings.MAX_DATE + ", " + pref.getVATPerc() + ", '" + pref.getVATReg() + "', '";
      sql += pref.getVATCode() + "', '" + pref.getVATOwnItems() + "', " + pref.getMarkupBoards() + ", " + pref.getMarkupFrames() + ", ";
      sql += pref.getMarkupGBs() + ", " + pref.getMarkupDecs() + ", " + pref.getMUDisc1() + ", " + pref.getMUDisc2() + ", ";
      sql += pref.getMUDisc3() + ", " + pref.getMUDisc4() + ", " + pref.getMUDisc5() + ", " + pref.getMUDisc6() + ", ";
      sql += pref.getMUDiscSelected() + ", " + pref.getStretchMinUCM() + ", " + pref.getStretchOtherUCM() + ", ";
      sql += pref.getStretchMinPrice() + ", " + pref.getStretchOtherPrice() + ", " + pref.getPastMinUCM() + ", ";
      sql += pref.getPastOtherUCM() + ", " + pref.getPastMinPrice() + ", " + pref.getPastOtherPrice() + ", ";
      sql += pref.getSundriesDisc() + ", " + pref.getSundriesBasic() + ", " + pref.getsundriesLabour() + ", ";
      sql += pref.getDateCount() + ")";
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();
      pref.setModified(false);
      connection.commit();
      connection.setAutoCommit(true);

    } catch (SQLException e) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException e2) {
        //groot fokop
      }
      String mes = "Error when trying to save business preferences.  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /*---------------------------------- CLIENTS ---------------------------------*/

  // Kry Client
  public ClientDets getClient(int clientID) {
    try {
      ClientDets det = null;
      String sql = "select * from clients where Client_ID = " + clientID;
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      if (rs.next()) {
        det = new ClientDets(rs.getInt("Client_ID"));
        det.setTitle(rs.getString("Title"));
        det.setName(rs.getString("Name"));
        det.setSurname(rs.getString("Surname"));
        det.setIDNo(rs.getString("IDNo"));
        det.setDOB(rs.getString("DOB"));
        det.setProfile(rs.getString("Profile"));
        det.setPostTo(rs.getInt("PostTo"));
        det.setAssociate(rs.getString("Associate"));
        det.setHomeAdd1(rs.getString("HomeAdd1"));
        det.setHomeAdd2(rs.getString("HomeAdd2"));
        det.setHomeAdd3(rs.getString("HomeAdd3"));
        det.setHomeAdd4(rs.getString("HomeAdd4"));
        det.setHomeCode(rs.getString("HomeCode"));
        det.setHomeCell(rs.getString("CelNo"));
        det.setHomeTel(rs.getString("HomeTel"));
        det.setHomeFax(rs.getString("HomeFax"));
        det.setHomeEmail(rs.getString("HomeEmail"));
        det.setWorkAdd1(rs.getString("WorkAdd1"));
        det.setWorkAdd2(rs.getString("WorkAdd2"));
        det.setWorkAdd3(rs.getString("WorkAdd3"));
        det.setWorkAdd4(rs.getString("WorkAdd4"));
        det.setWorkCode(rs.getString("WorkCode"));
        det.setWorkCell(rs.getString("WorkCellNo"));
        det.setWorkTel(rs.getString("WorkTel"));
        det.setWorkFax(rs.getString("WorkFax"));
        det.setWorkEmail(rs.getString("WorkEmail"));
        det.setModified(false);
      }
      s.close();
      return det;
    } catch (SQLException e) {
      return null;
    }
  }

  // Kry Clients
  public ArrayList getClients(String orderSQL) {
    ArrayList clientList = new ArrayList();
    try {
      String sql = "select * from clients";
      if (orderSQL != null)
        sql += orderSQL;
      else
        sql += " order by Name";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        ClientDets det = new ClientDets(rs.getInt("Client_ID"));
        det.setTitle(rs.getString("Title"));
        det.setName(rs.getString("Name"));
        det.setSurname(rs.getString("Surname"));
        det.setIDNo(rs.getString("IDNo"));
        det.setDOB(rs.getString("DOB"));
        det.setProfile(rs.getString("Profile"));
        det.setPostTo(rs.getInt("PostTo"));
        det.setAssociate(rs.getString("Associate"));
        det.setHomeAdd1(rs.getString("HomeAdd1"));
        det.setHomeAdd2(rs.getString("HomeAdd2"));
        det.setHomeAdd3(rs.getString("HomeAdd3"));
        det.setHomeAdd4(rs.getString("HomeAdd4"));
        det.setHomeCode(rs.getString("HomeCode"));
        det.setHomeCell(rs.getString("CelNo"));
        det.setHomeTel(rs.getString("HomeTel"));
        det.setHomeFax(rs.getString("HomeFax"));
        det.setHomeEmail(rs.getString("HomeEmail"));
        det.setWorkAdd1(rs.getString("WorkAdd1"));
        det.setWorkAdd2(rs.getString("WorkAdd2"));
        det.setWorkAdd3(rs.getString("WorkAdd3"));
        det.setWorkAdd4(rs.getString("WorkAdd4"));
        det.setWorkCode(rs.getString("WorkCode"));
        det.setWorkCell(rs.getString("WorkCellNo"));
        det.setWorkTel(rs.getString("WorkTel"));
        det.setWorkFax(rs.getString("WorkFax"));
        det.setWorkEmail(rs.getString("WorkEmail"));
        det.setModified(false);
        clientList.add(det);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Clients ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return clientList;
  }

  // Kry Client List vir designskerm
  public TreeMap getClients() {
    TreeMap clientList = new TreeMap();
    try {
      String sql = "select * from clients order by surname, name, idno";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        String key = rs.getString("Surname") + ", " + rs.getString("Name");
        key += " - " + rs.getString("IDNO");
        int clientID = rs.getInt("Client_ID");
        clientList.put(key, new Integer(clientID));
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Clients ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return clientList;
  }

  // Delete clients
  public void deleteClients(int clientID) {
    try {
      String sql = "delete from clients where Client_ID = " + clientID;
      Statement s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Clients ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // Save clients
  public void saveClients(ArrayList clientList) {
    Iterator it = clientList.iterator();
    while (it.hasNext()) {
      ClientDets det = (ClientDets)it.next();
      if (det.isModified())
        saveClient(det);
    }
  }

  // Save client
  public void saveClient(ClientDets det) {
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String sql;
      if (det.getClientID() == -1) {
        int newID = 0;
        sql = "select max(client_id) from clients";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
          newID = rs.getInt(1);
        }
        newID++;
        det.setClientID(newID);

        sql = "insert into clients (CLIENT_ID, TITLE, NAME, SURNAME, IDNO, DOB, ";
        sql += "PROFILE, POSTTO, ASSOCIATE, HOMEADD1, HOMEADD2, HOMEADD3, HOMEADD4, ";
        sql += "HOMECODE, HOMETEL, HOMEFAX, HOMEEMAIL, WORKADD1, WORKADD2, WORKADD3, ";
        sql += "WORKADD4, WORKCODE, WORKTEL, WORKFAX, WORKEMAIL, CELNO, WORKCELLNO) ";
        sql += " values (" + newID + ", ";
        sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ";
        sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ";
        sql += "?, ?, ?, ?, ?, ?)";

      } else {
        sql = "update clients set TITLE = ?, NAME = ?, SURNAME = ?, IDNO = ?, DOB = ?, ";
        sql += "PROFILE = ?, POSTTO = ?, ASSOCIATE = ?, HOMEADD1 = ?, HOMEADD2 = ?, HOMEADD3 = ?, HOMEADD4 = ?, ";
        sql += "HOMECODE = ?, HOMETEL = ?, HOMEFAX = ?, HOMEEMAIL = ?, WORKADD1 = ?, WORKADD2 = ?, WORKADD3 = ?, ";
        sql += "WORKADD4 = ?, WORKCODE = ?, WORKTEL = ?, WORKFAX = ?, WORKEMAIL = ?, CELNO = ?, WORKCELLNO = ? ";
        sql += " where CLIENT_ID = " + det.getClientID();
      }

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, det.getTitle());
      ps.setString(2, det.getName());
      ps.setString(3, det.getSurname());
      ps.setString(4, det.getIDNo());
      ps.setString(5, det.getDOB());
      ps.setString(6, det.getProfile());
      ps.setInt(7, det.getPostTo());
      ps.setString(8, det.getAssociate());
      ps.setString(9, det.getHomeAdd1());
      ps.setString(10, det.getHomeAdd2());
      ps.setString(11, det.getHomeAdd3());
      ps.setString(12, det.getHomeAdd4());
      ps.setString(13, det.getHomeCode());
      ps.setString(14, det.getHomeTel());
      ps.setString(15, det.getHomeFax());
      ps.setString(16, det.getHomeEmail());
      ps.setString(17, det.getWorkAdd1());
      ps.setString(18, det.getWorkAdd2());
      ps.setString(19, det.getWorkAdd3());
      ps.setString(20, det.getWorkAdd4());
      ps.setString(21, det.getWorkCode());
      ps.setString(22, det.getWorkTel());
      ps.setString(23, det.getWorkFax());
      ps.setString(24, det.getWorkEmail());
      ps.setString(25, det.getHomeCell());
      ps.setString(26, det.getWorkCell());

      ps.execute();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
      det.setModified(false);

    } catch (SQLException e) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException e2) {
        //groot fokop
      }

      String mes = "Error when trying to save: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void restoreClient(ClientDets det) {
    try {
      String sql = "select count(CLIENT_ID) from clients where CLIENT_ID = " + det.getClientID();
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int count = 0;
      if (rs.next()) {
        count = rs.getInt(1);
      }
      if (count == 0)
        det.setClientID(-1);

      s.close();
      saveClient(det);

    } catch (Exception e) {
      //doen fokkol
    }
  }

  /*---------------------------------- ARTISTS ---------------------------------*/

  // Kry Artist
  public ArtistDets getArtists(int artistID) {
    ArrayList artistList = new ArrayList();
    try {
      String sql = "select * from artists where artist_id = " + artistID;
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      ArtistDets det = null;
      if (rs.next()) {
        det = new ArtistDets(rs.getInt("Artist_ID"));
        det.setTitle(rs.getString("Title"));
        det.setName(rs.getString("Name"));
        det.setSurname(rs.getString("Surname"));
        det.setDOB(rs.getString("DOB"));
        det.setOrigin(rs.getString("Origin"));
        det.setProfile(rs.getString("Profile"));
        det.setAdd1(rs.getString("Add1"));
        det.setAdd2(rs.getString("Add2"));
        det.setAdd3(rs.getString("Add3"));
        det.setAdd4(rs.getString("Add4"));
        det.setPostalCode(rs.getString("PostalCode"));
        det.setTel(rs.getString("Tel"));
        det.setFax(rs.getString("Fax"));
        det.setCel(rs.getString("Cel"));
        det.setEmail(rs.getString("Email"));
        det.setWorks(rs.getString("Works"));
        det.setTraining(rs.getString("Training"));
        det.setExhibitions(rs.getString("Exhibitions"));
        det.setAwards(rs.getString("Awards"));
        det.setSummary(rs.getString("Summary"));
        det.setModified(false);
        artistList.add(det);
      }
      s.close();
      return det;
    } catch (SQLException e) {
      return null;
    }
  }

  // Kry Artists
  public ArrayList getArtists(String orderSQL) {
    ArrayList artistList = new ArrayList();
    try {
      String sql = "select * from artists";
      if (orderSQL != null)
        sql += orderSQL;
      else
        sql += " order by Name";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        ArtistDets det = new ArtistDets(rs.getInt("Artist_ID"));
        det.setTitle(rs.getString("Title"));
        det.setName(rs.getString("Name"));
        det.setSurname(rs.getString("Surname"));
        det.setDOB(rs.getString("DOB"));
        det.setOrigin(rs.getString("Origin"));
        det.setProfile(rs.getString("Profile"));
        det.setAdd1(rs.getString("Add1"));
        det.setAdd2(rs.getString("Add2"));
        det.setAdd3(rs.getString("Add3"));
        det.setAdd4(rs.getString("Add4"));
        det.setPostalCode(rs.getString("PostalCode"));
        det.setTel(rs.getString("Tel"));
        det.setFax(rs.getString("Fax"));
        det.setCel(rs.getString("Cel"));
        det.setEmail(rs.getString("Email"));
        det.setWorks(rs.getString("Works"));
        det.setTraining(rs.getString("Training"));
        det.setExhibitions(rs.getString("Exhibitions"));
        det.setAwards(rs.getString("Awards"));
        det.setSummary(rs.getString("Summary"));

        det.setModified(false);
        artistList.add(det);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Artists ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return artistList;
  }

  // Kry Artist List vir designskerm
  public TreeMap getArtists() {
    TreeMap artistList = new TreeMap();
    try {
      String sql = "select * from artists order by surname, name, dob";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        String key = rs.getString("Surname") + ", " + rs.getString("Name");
        key += " - " + rs.getString("DOB");
        int artistID = rs.getInt("Artist_ID");
        artistList.put(key, new Integer(artistID));
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Artists";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return artistList;
  }

  // Delete Artists
  public void deleteArtists(int artistsID) {
    try {
      String sql = "delete from artists where Artists_ID = " + artistsID;
      Statement s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to delete Artist ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // Save Artists
  public void saveArtists(ArrayList artistList) {
    Iterator it = artistList.iterator();
    while (it.hasNext()) {
      ArtistDets det = (ArtistDets)it.next();
      if (det.isModified())
        saveArtist(det);
    }
  }

  // Save Artist
  public void saveArtist(ArtistDets det) {
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String sql;
      if (det.getArtistID() == -1) {
        int newID = 0;
        sql = "select max(artist_id) from artists";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
          newID = rs.getInt(1);
        }
        newID++;
        det.setArtistID(newID);

        sql = "insert into artists (ARTIST_ID, TITLE, NAME, SURNAME, DOB, ";
        sql += "Origin, PROFILE, ADD1, ADD2, ADD3, ADD4, POSTALCODE, TEL, FAX, CEL, ";
        sql += "EMAIL, WORKS, TRAINING, EXHIBITIONS, AWARDS, SUMMARY) ";
        sql += " values (" + newID + ", ";
        sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ";
        sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      } else {
        sql = "update artists set ";
        sql += "TITLE = ?, NAME = ?, SURNAME = ?, DOB = ?, ";
        sql += "Origin = ?, PROFILE = ?, ADD1 = ?, ADD2 = ?, ADD3 = ?, ADD4 = ?, ";
        sql += "POSTALCODE = ?, TEL = ?, FAX = ?, CEL = ?, ";
        sql += "EMAIL = ?, WORKS = ?, TRAINING = ?, EXHIBITIONS = ?, ";
        sql += "AWARDS = ?, SUMMARY = ? where ARTIST_ID = " + det.getArtistID();
      }

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, det.getTitle());
      ps.setString(2, det.getName());
      ps.setString(3, det.getSurname());
      ps.setString(4, det.getDOB());
      ps.setString(5, det.getOrigin());
      ps.setString(6, det.getProfile());
      ps.setString(7, det.getAdd1());
      ps.setString(8, det.getAdd2());
      ps.setString(9, det.getAdd3());
      ps.setString(10, det.getAdd4());
      ps.setString(11, det.getPostalCode());
      ps.setString(12, det.getTel());
      ps.setString(13, det.getFax());
      ps.setString(14, det.getCel());
      ps.setString(15, det.getEmail());
      ps.setString(16, det.getWorks());
      ps.setString(17, det.getTraining());
      ps.setString(18, det.getExhibitions());
      ps.setString(19, det.getAwards());
      ps.setString(20, det.getSummary());

      ps.execute();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
      det.setModified(false);

    } catch (Exception e) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException e2) {
        //groot fokop
      }

      String mes = "Error when trying to save: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void restoreArtist(ArtistDets det) {
    try {
      String sql = "select count(ARTIST_ID) from ARTISTS where ARTIST_ID = " + det.getArtistID();
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int count = 0;
      if (rs.next()) {
        count = rs.getInt(1);
      }
      if (count == 0)
        det.setArtistID(-1);

      s.close();
      saveArtist(det);

    } catch (Exception e) {
      //doen fokkol
    }
  }

  /*---------------------------- METHOD PREFERENCESS ----------------------------*/

  // Kry lys van method preferences datums
  public ArrayList getMethodPrefList() {
    ArrayList prefList = new ArrayList();
    try {
      String sql = "select PrefDate, DateCount from methodpreferences order by PrefDate, DateCount";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        int intDate = rs.getInt("PrefDate");
        String prefDate;
        if (intDate == UserSettings.MAX_DATE)
          prefDate = "Latest";
        else
          prefDate = Utils.getDatumStr(intDate);
        int dateCount = rs.getInt("DateCount");
        if (dateCount > 1)
          prefDate += ", " + dateCount;
        prefList.add(prefDate);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Method Preferences.  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return prefList;
  }

  // Kry Method Preferences (1)
  public MethodPrefDets getMethodPreferences(String dateString) {
    if (dateString == null || dateString.equals("Latest"))
      return getMethodPreferences(UserSettings.MAX_DATE, 1);

    int k = dateString.indexOf(',');
    if (k > -1) {
      int dateCount = (new Integer(dateString.substring(k + 2))).intValue();
      return getMethodPreferences(Utils.getDatumInt(dateString.substring(0, k)), dateCount);
    }
    else
      return getMethodPreferences(Utils.getDatumInt(dateString), 1);
  }


  // Kry Method Preferences (2)
  public MethodPrefDets getMethodPreferences(int prefDate, int dateCount) {
    MethodPrefDets pref = null;
    try {
      String sql = "select * from methodpreferences where PrefDate = " + prefDate;
      if (dateCount >= 1)
        sql += " and DateCount = " + dateCount;
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        pref = new MethodPrefDets(rs.getInt("METHODPREF_ID"));
        pref.setPrefDate(rs.getInt("PREFDATE"));
        pref.setDateCount(rs.getInt("DATECOUNT"));
        pref.setMethodType(rs.getInt("METHODTYPE"));
        pref.setFullBottoms(rs.getInt("ISFULLORDER"));
        pref.setOverlapAdjFact(rs.getFloat("OVERLAPADJFACT"));
        pref.setFBOverlapWithSlip(rs.getFloat("FBOVERLAPWITHSLIP"));
        pref.setFBOverlapNoSlip(rs.getFloat("FBOVERLAPNOSLIP"));
        pref.setMinOverlap(rs.getFloat("MINIMUMSLIP"));
        pref.setResBoardLength(rs.getFloat("RESBOARDLENGTH"));
        pref.setResBoardWidth(rs.getFloat("RESBOARDWIDTH"));
        pref.setResBackLength(rs.getFloat("RESBACKLENGTH"));
        pref.setResBackWidth(rs.getFloat("RESBACKWIDTH"));
        pref.setResGlassLength(rs.getFloat("RESGLASSLENGTH"));
        pref.setResGlassWidth(rs.getFloat("RESGLASSWIDTH"));
        pref.setResPremLength(rs.getFloat("RESDECLENGTH"));
        pref.setResPremWidth(rs.getFloat("RESDECWIDTH"));
        pref.setFGIfValue(rs.getFloat("FGIFVALUE"));
        pref.setFGThenHeight(rs.getFloat("FGTHENHEIGHT"));
        pref.setFGThenWidth(rs.getFloat("FGTHENWIDTH"));
        pref.setFGElseHeight(rs.getFloat("FGELSEHEIGHT"));
        pref.setFGElseWidth(rs.getFloat("FGELSEWIDTH"));
        pref.setSunFirstMeasure(rs.getString("SUNFIRSTMEASURE"));
        pref.setSunNoSpecs(rs.getInt("SUNNOSPECS"));
        pref.setSunBackDefault(rs.getString("SUNBACKDEFAULT"));
        pref.setSunDays(rs.getInt("SUNDAYS"));
        pref.setModified(false);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Method Preferences.  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    if (pref == null && prefDate == UserSettings.MAX_DATE)
      pref = new MethodPrefDets();

    return pref;
  }

  // Save Method Preferences
  public void saveMethodPreferences(MethodPrefDets pref) {
    if (!pref.isModified())
      return;

    Calendar cal = new GregorianCalendar();
    int curDate = 10000*cal.get(Calendar.YEAR) + 100*(cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);

    try {
      connection.setAutoCommit(false);
      connection.commit();

      int newID = 0;
      String sql = "select max(METHODPREF_ID) from methodpreferences";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      if (rs.next()) {
        newID = rs.getInt(1);
      }
      s.close();
      newID++;

      int maxID = 0;
      sql = "select METHODPREF_ID from methodpreferences where PrefDate = " + UserSettings.MAX_DATE;
      s = connection.createStatement();
      rs = s.executeQuery(sql);
      if (rs.next()) {
        maxID = rs.getInt(1);
      }
      s.close();

      int prevCount = 0;
      sql = "select count(METHODPREF_ID) from methodpreferences where PrefDate = " + curDate;
      s = connection.createStatement();
      rs = s.executeQuery(sql);
      if (rs.next()) {
        prevCount = rs.getInt(1);
      }
      s.close();
      prevCount++;

      sql = "Update methodpreferences set PrefDate = " + curDate + ", DateCount = " + prevCount;
      sql += " where METHODPREF_ID = " + maxID;
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();

      sql = "insert into methodpreferences (METHODPREF_ID, PREFDATE, DATECOUNT, ";
      sql += "METHODTYPE, ISFULLORDER, OVERLAPADJFACT, FBOVERLAPWITHSLIP, FBOVERLAPNOSLIP, ";
      sql += "MINIMUMSLIP, RESBOARDLENGTH, RESBOARDWIDTH, RESBACKLENGTH, RESBACKWIDTH, ";
      sql += "RESGLASSLENGTH, RESGLASSWIDTH, RESDECLENGTH, RESDECWIDTH, RESSELECTED, ";
      sql += "FGIFVALUE, FGTHENHEIGHT, FGTHENWIDTH, FGELSEHEIGHT, FGELSEWIDTH, ";
      sql += "SUNFIRSTMEASURE, SUNNOSPECS, SUNBACKDEFAULT, SUNDAYS) ";
      sql += "values (";
      sql += newID + ", " + UserSettings.MAX_DATE + ", " + pref.getDateCount() + ", ";
      sql += pref.getMethodType() + ", " + pref.getFullBottoms() + ", " + pref.getOverlapAdjFact() + ", ";
      sql += pref.getFBOverlapWithSlip() + ", " + pref.getFBOverlapNoSlip() + ", ";
      sql += pref.getMinOverlap() + ", " + pref.getResBoardLength() + ", " + pref.getResBoardWidth() + ", ";
      sql += pref.getResBackLength() + ", " + pref.getResBackWidth() + ", " + pref.getResGlassLength() + ", ";
      sql += pref.getResGlassWidth() + ", " + pref.getResPremLength() + ", " + pref.getResPremWidth() + ", ";
      sql += pref.getResSelected() + ", " + pref.getFGIfValue() + ", " + pref.getFGThenHeight() + ", ";
      sql += pref.getFGThenWidth() + ", " + pref.getFGElseHeight() + ", " + pref.getFGElseWidth() + ", '";
      sql += pref.getSunFirstMeasure() + "', " + pref.getSunNoSpecs() + ", '" + pref.getSunBackDefault() + "', ";
      sql += pref.getSunDays() + ")";
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();
      pref.setModified(false);
      connection.commit();
      connection.setAutoCommit(true);

    } catch (SQLException e) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException e2) {
        //groot fokop
      }

      String mes = "Error when trying to save method preferences.  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

  }

  /*---------------------------------- DESIGNS ---------------------------------*/

  // Kry designs
  public ArrayList getDesigns(ArrayList openList) {
    ArrayList designList = new ArrayList();
    try {
      String sql = "select * from designs ";
      if (openList.size() == 0) return designList;
      sql += " where ";
      boolean firstTime = true;
      Iterator it = openList.iterator();
      while (it.hasNext()) {
        Integer code = (Integer)it.next();
        if (firstTime) firstTime = false;
        else sql += " or";
        sql += " DESIGN_ID = " + code.intValue();
      }
      sql += " order by DESIGN_ID";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        BusPrefDets busPrefs = (BusPrefDets)rs.getObject("BusPrefs");
        MethodPrefDets methodPrefs = (MethodPrefDets)rs.getObject("MethodPrefs");
        try {
          DesignDets det = new DesignDets(busPrefs, methodPrefs, rs.getString("Title"), this);
          det.setDesignID(rs.getInt("Design_ID"));
          det.setArtistID(rs.getInt("Artist_ID"));
          det.setClientID(rs.getInt("Client_ID"));
          det.setDate(rs.getInt("DesDate"));
          det.setStatus(rs.getString("Status"));
          det.setItemList((LinkedList)rs.getObject("ItemList"));
          det.setMaterialAdj(rs.getFloat("MATERIALADJ"));
          det.setLabourAdj(rs.getFloat("LABOURADJ"));
          det.setUseWeighting(rs.getInt("USEWEIGHTING"));
          det.setUseStretching(rs.getInt("USESTRETCHING"));
          det.setUsePasting(rs.getInt("USEPASTING"));

          det.setDeliveryDate(rs.getInt("DELIVERYDATE"));
          det.setPriceOne(rs.getFloat("PRICEONE"));
          det.setDiscountOne(rs.getFloat("DISCOUNTONE"));
          det.setNoOrdered(rs.getInt("NOORDERED"));
          det.setPriceOther(rs.getFloat("PRICEOTHER"));
          det.setDiscountOther(rs.getFloat("DISCOUNTOTHER"));

          det.setModified(false);
          designList.add(det);
        } catch (NullPointerException nx) {
          // balls op
        }
      }
      s.close();

    } catch (SQLException e) {
      String mes = "Error when trying to read Designs";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return designList;
  }

  // Kry lys van designs
  public ArrayList getDesignLys() {
    ArrayList designList = new ArrayList();
    try {
      String sql = "select DESIGN_ID, TITLE, DESDATE, STATUS ";
      sql += "from designs order by DESIGN_ID";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        try {
          Object[] rekord = new Object[5];
          rekord[0] = new Boolean(false);
          rekord[1] = new Integer(rs.getInt(1));
          rekord[2] = new String(rs.getString(2));
          rekord[3] = new Integer(rs.getInt(3));
          rekord[4] = new String(rs.getString(4));
          designList.add(rekord);
        } catch (Exception nx) {
          // balls op
        }
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Designs";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return designList;
  }

  // Kry al die designs
  public ArrayList getAllDesigns() {
    ArrayList designList = new ArrayList();
    try {
      String sql = "select * from designs ";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        BusPrefDets busPrefs = (BusPrefDets)rs.getObject("BusPrefs");
        MethodPrefDets methodPrefs = (MethodPrefDets)rs.getObject("MethodPrefs");
        try {
          DesignDets det = new DesignDets(busPrefs, methodPrefs, rs.getString("Title"), this);
          det.setDesignID(rs.getInt("Design_ID"));
          det.setArtistID(rs.getInt("Artist_ID"));
          det.setClientID(rs.getInt("Client_ID"));
          det.setDate(rs.getInt("DesDate"));
          det.setStatus(rs.getString("Status"));
          det.setItemList((LinkedList)rs.getObject("ItemList"));
          det.setMaterialAdj(rs.getFloat("MATERIALADJ"));
          det.setLabourAdj(rs.getFloat("LABOURADJ"));
          det.setUseWeighting(rs.getInt("USEWEIGHTING"));
          det.setUseStretching(rs.getInt("USESTRETCHING"));
          det.setUsePasting(rs.getInt("USEPASTING"));

          det.setDeliveryDate(rs.getInt("DELIVERYDATE"));
          det.setPriceOne(rs.getFloat("PRICEONE"));
          det.setDiscountOne(rs.getFloat("DISCOUNTONE"));
          det.setNoOrdered(rs.getInt("NOORDERED"));
          det.setPriceOther(rs.getFloat("PRICEOTHER"));
          det.setDiscountOther(rs.getFloat("DISCOUNTOTHER"));

          det.setModified(false);
          designList.add(det);
        } catch (NullPointerException nx) {
          // balls op
        }
      }
      s.close();

    } catch (SQLException e) {
      String mes = "Error when trying to read Designs";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return designList;
  }


  // Kry telefoonnommers van client
  public String[] getTelefoonNommers(int clientID) {
    String[] nommers = new String[3];
    try {
      String sql = "select HOMETEL, WORKTEL, CELNO ";
      sql += "from clients where CLIENT_ID  = " + clientID;
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      if (rs.next()) {
        nommers[0] = rs.getString(1);
        nommers[1] = rs.getString(2);
        nommers[2] = rs.getString(3);
      }
      s.close();
    } catch (SQLException e) {
      nommers[0] = "";
      nommers[1] = "";
      nommers[2] = "";
    }
    return nommers;
  }

  // Save designs
  public void saveDesigns(ArrayList designList) {
    Iterator it = designList.iterator();
    while (it.hasNext()) {
      DesignDets det = (DesignDets)it.next();
      if (det.isModified())
        saveDesign(det);
    }
  }

  // Save design
  public void saveDesign(DesignDets det) {
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String sql;
      if (det.getDesignID() == -1) {
        int newID = 0;
        sql = "select max(DESIGN_ID) from designs";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
          newID = rs.getInt(1);
        }
        newID++;
        det.setDesignID(newID);

        sql = "insert into designs (DESIGN_ID, TITLE, ARTIST_ID, CLIENT_ID, DESDATE, STATUS, ";
        sql += "BUSPREFS, METHODPREFS, ITEMLIST, MATERIALADJ, LABOURADJ, USEWEIGHTING, ";
        sql += "USESTRETCHING, USEPASTING, DELIVERYDATE, PRICEONE, DISCOUNTONE, ";
        sql += "NOORDERED, PRICEOTHER, DISCOUNTOTHER) ";
        sql += "values (" + newID + ", ";
        sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ";
        sql += "?, ?, ?, ?, ?, ?, ?, ?, ?)";

      } else {
        sql = "update designs set TITLE = ?, ARTIST_ID = ?, CLIENT_ID = ?, DESDATE = ?, STATUS = ?, ";
        sql += "BUSPREFS = ?, METHODPREFS = ?, ITEMLIST = ?, MATERIALADJ = ?, LABOURADJ = ?, USEWEIGHTING = ?, ";
        sql += "USESTRETCHING = ?, USEPASTING = ?, DELIVERYDATE = ?, PRICEONE = ?, DISCOUNTONE = ?, ";
        sql += "NOORDERED = ?, PRICEOTHER = ?, DISCOUNTOTHER = ? ";
        sql += "where DESIGN_ID = " + det.getDesignID();
      }

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, det.getTitle());
      ps.setInt(2, det.getArtistID());
      ps.setInt(3, det.getClientID());
      ps.setInt(4, det.getDate());
      ps.setString(5, det.getStatus());
      ps.setObject(6, det.getBusPrefs());
      ps.setObject(7, det.getMethodPrefs());
      ps.setObject(8, det.getItemList());
      ps.setFloat(9, det.getMaterialAdj());
      ps.setFloat(10, det.getLabourAdj());
      ps.setInt(11, det.getUseWeightingInt());
      ps.setInt(12, det.getUseStretchingInt());
      ps.setInt(13, det.getUsePastingInt());
      ps.setInt(14, det.getDeliveryDate());
      ps.setFloat(15, det.getPriceOne());
      ps.setFloat(16, det.getDiscountOne());
      ps.setInt(17, det.getNoOrdered());
      ps.setFloat(18, det.getPriceOther());
      ps.setFloat(19, det.getDiscountOther());

      ps.execute();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
      det.setModified(false);

    } catch (Exception e) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException e2) {
        //groot fokop
      }

      String mes = "Error when trying to save: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void restoreDesign(DesignDets det) {
    try {
      String sql = "select count(DESIGN_ID) from designs where DESIGN_ID = " + det.getDesignID();
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int count = 0;
      if (rs.next()) {
        count = rs.getInt(1);
      }
      if (count == 0)
        det.setDesignID(-1);

      s.close();
      saveDesign(det);

    } catch (Exception e) {
      //doen fokkol
    }
  }

  // Delete designs
  public void deleteDesigns(ArrayList designList) {
    try {
      String sql = "delete from designs where ";
      boolean firstTime = true;
      Iterator it = designList.iterator();
      while (it.hasNext()) {
        int code = ((Integer)it.next()).intValue();
        if (firstTime) firstTime = false;
        else sql += " or";
        sql += " Design_ID = " + code;
      }
      Statement s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();
    } catch (SQLException e) {
      // doen boggerol
    }
  }


  /*-------------------------------- MATERIALS --------------------------------*/

  // Kry material Item Codes
  public ArrayList getMaterialItemCodes(int type) {
    ArrayList itemList = new ArrayList();
    try {
      String sql = "select distinct ItemCode from materials ";
      if (type != MaterialDets.MAT_ALL)
        sql += " where MATERIALTYPE = " + type;
      sql += " order by ItemCode";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        itemList.add(rs.getString(1));
      }
      s.close();

    } catch (SQLException e) {
      String mes = "Error when trying to read Materials:  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return itemList;
  }

  // Kry spesifieke material
  public MaterialDets getMaterial(String itemCode) {
    MaterialDets det = null;
    try {
      String sql = "select * from materials where ItemCode = '" + itemCode + "'";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      if (rs.next()) {
        det = new MaterialDets();
        det.setMaterialID(rs.getInt("MaterialID"));
        det.setItemCode(rs.getString("ItemCode"));
        det.setMaterialType(rs.getInt("MaterialType"));
        det.setOwnCode(rs.getString("OwnCode"));
        det.setGroup(rs.getString("MATERIAL_GROUP"));
        det.setDescription(rs.getString("DESCRIPTION"));

        int red = rs.getInt("COLORRED");
        int green = rs.getInt("COLORGREEN");
        int blue = rs.getInt("COLORBLUE");
        int alpha = rs.getInt("COLORALPHA");
        det.setColor(new Color(red, green, blue, alpha));

        rs.close();
        s.close();

        sql = "Select * from materialdetails where MaterialID = " + det.getMaterialID();
        s = connection.createStatement();
        rs = s.executeQuery(sql);
        while (rs.next()) {
          MaterialValues val = new MaterialValues();
          //val.setValueID(rs.getInt("MaterialDetailID"));
          val.setStatus(rs.getString("STATUS"));
          val.setCost(rs.getFloat("COST"));
          val.setCompFactor(rs.getFloat("COMPFACTOR"));
          val.setExqFactor(rs.getFloat("EXQFACT"));
          val.setWidth(rs.getFloat("MATWIDTH"));
          val.setLength(rs.getFloat("MATLENGTH"));
          val.setThickness(rs.getFloat("THICKNESS"));
          val.setRebate(rs.getFloat("REBATE"));
          try {
            byte[] buf = rs.getBytes("PRICEARRAY");
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            val.setPriceArray((Object[])objectIn.readObject());
          } catch (Exception x) {
            val.setPriceArray(null);
          }
          val.setModified(false);
          det.addMaterialValues(val, rs.getString("SUPPLIER"));
        }
        rs.close();
        det.setModified(false);
      }
      s.close();

    } catch (SQLException e) {
      String mes = "Error when trying to read Materials:  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return det;
  }


  public int getMaterialCount(int type) {
    String sql;
    if (type == MaterialDets.MAT_ALL)
      sql = "select count(materialID) from materials";
    else
     sql = "select count(materialID) from materials where materialType = " + type;

    int count = 0;
    try {
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      if (rs.next()) {
        count = rs.getInt(1);
      }
      s.close();

    } catch (SQLException e) {
      // doen net mooi fokkol
    }
    return count;
  }


  public ArrayList getMaterialsProgress(int type, String orderSQL, ProgressDialog d) {
    String sql = "select * from materials ";

    if (type != MaterialDets.MAT_ALL)
      sql += "where MaterialType = " + type;

    if (orderSQL != null)
      sql += orderSQL;
    else
      sql += " order by ItemCode";

    ArrayList matList = new ArrayList();
    try {
      int plek = 0;
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        MaterialDets det = new MaterialDets();
        det.setMaterialID(rs.getInt("MaterialID"));
        det.setItemCode(rs.getString("ItemCode"));
        det.setMaterialType(rs.getInt("MaterialType"));
        det.setOwnCode(rs.getString("OwnCode"));
        det.setGroup(rs.getString("MATERIAL_GROUP"));
        det.setDescription(rs.getString("DESCRIPTION"));

        int red = rs.getInt("COLORRED");
        int green = rs.getInt("COLORGREEN");
        int blue = rs.getInt("COLORBLUE");
        int alpha = rs.getInt("COLORALPHA");
        det.setColor(new Color(red, green, blue, alpha));

        sql = "Select * from materialdetails where MaterialID = " + det.getMaterialID();
        Statement s2 = connection.createStatement();
        ResultSet rs2 = s2.executeQuery(sql);
        while (rs2.next()) {
          MaterialValues val = new MaterialValues();
          //val.setValueID(rs2.getInt("MaterialDetailID"));
          val.setStatus(rs2.getString("STATUS"));
          val.setCost(rs2.getFloat("COST"));
          val.setCompFactor(rs2.getFloat("COMPFACTOR"));
          val.setExqFactor(rs2.getFloat("EXQFACT"));
          val.setWidth(rs2.getFloat("MATWIDTH"));
          val.setLength(rs2.getFloat("MATLENGTH"));
          val.setThickness(rs2.getFloat("THICKNESS"));
          val.setRebate(rs2.getFloat("REBATE"));
          try {
            byte[] buf = rs2.getBytes("PRICEARRAY");
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            val.setPriceArray((Object[])objectIn.readObject());
          } catch (Exception x) {
            val.setPriceArray(null);
          }
          val.setModified(false);
          det.addMaterialValues(val, rs2.getString("SUPPLIER"));
        }
        rs2.close();
        s2.close();

        det.setModified(false);
        matList.add(det);
        d.setValue2(plek++);
      }
      rs.close();
      s.close();

    } catch (SQLException e) {
      String mes = "Error when trying to read Materials:  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return matList;
  }


  // Kry materials
  public ArrayList getMaterials(int type, String orderSQL) {
    String sql = "select * from materials where MaterialType = " + type;
    if (orderSQL != null)
      sql += orderSQL;
    else
      sql += " order by ItemCode";

    return getMaterials(sql);
  }

  // Kry al die materials
  public ArrayList getAllMaterials() {
    return getMaterials("select * from materials order by ItemCode");
  }

  private ArrayList getMaterials(String sql) {
    ArrayList matList = new ArrayList();
    try {
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        MaterialDets det = new MaterialDets();
        det.setMaterialID(rs.getInt("MaterialID"));
        det.setItemCode(rs.getString("ItemCode"));
        det.setMaterialType(rs.getInt("MaterialType"));
        det.setOwnCode(rs.getString("OwnCode"));
        det.setGroup(rs.getString("MATERIAL_GROUP"));
        det.setDescription(rs.getString("DESCRIPTION"));

        int red = rs.getInt("COLORRED");
        int green = rs.getInt("COLORGREEN");
        int blue = rs.getInt("COLORBLUE");
        int alpha = rs.getInt("COLORALPHA");
        det.setColor(new Color(red, green, blue, alpha));

        sql = "Select * from materialdetails where MaterialID = " + det.getMaterialID();
        Statement s2 = connection.createStatement();
        ResultSet rs2 = s2.executeQuery(sql);
        while (rs2.next()) {
          MaterialValues val = new MaterialValues();
          //val.setValueID(rs2.getInt("MaterialDetailID"));
          val.setStatus(rs2.getString("STATUS"));
          val.setCost(rs2.getFloat("COST"));
          val.setCompFactor(rs2.getFloat("COMPFACTOR"));
          val.setExqFactor(rs2.getFloat("EXQFACT"));
          val.setWidth(rs2.getFloat("MATWIDTH"));
          val.setLength(rs2.getFloat("MATLENGTH"));
          val.setThickness(rs2.getFloat("THICKNESS"));
          val.setRebate(rs2.getFloat("REBATE"));
          try {
            byte[] buf = rs2.getBytes("PRICEARRAY");
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            val.setPriceArray((Object[])objectIn.readObject());
          } catch (Exception x) {
            val.setPriceArray(null);
          }
          val.setModified(false);
          det.addMaterialValues(val, rs2.getString("SUPPLIER"));
        }
        rs2.close();
        s2.close();

        det.setModified(false);
        matList.add(det);
      }
      rs.close();
      s.close();

    } catch (SQLException e) {
      String mes = "Error when trying to read Materials:  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return matList;
  }

  // Kry materiaal se verskaffers
  public ArrayList getMaterialSuppliers(String itemCode) {
    ArrayList supList = new ArrayList();
    try {
      String sql = "select distinct Supplier from materialdetails, materials where materials.itemcode = '";
      sql += itemCode + "' and materials.materialid = materialdetails.materialid order by supplier";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        supList.add(rs.getString(1));
      }
      s.close();
      return supList;

    } catch (SQLException e) {
      //e.printStackTrace();
      //System.out.println(e.toString());
      return supList;
    }
  }

  //Delete Materialdetails
  public void deleteMaterialDetails(int materialID, String supplier) {
      try {
        connection.setAutoCommit(false);
        connection.commit();

        String itemCode = "";
        Statement s = connection.createStatement();


        String sql = "delete from materialdetails where materialID = " + materialID + " and ";
            sql += "supplier = '" + supplier + "'";
        s = connection.createStatement();
        s.executeUpdate(sql);
        s.close();


        connection.commit();
        connection.setAutoCommit(true);

      } catch (SQLException e) {
        try {
          connection.rollback();
          connection.setAutoCommit(true);
        } catch (Exception e2) {
          //groot fokop
        }
        String mes = "Error when trying to delete MaterialDetails:  ";
        mes += e.getMessage();
        JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
      }
    }

  // Delete materials
  public void deleteMaterial(int materialID) {
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String itemCode = "";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery("select itemCode from materials where materialID = " + materialID);
      if (rs.next()) {
        itemCode = rs.getString(1);
      }

      String sql = "delete from materialdetails where materialID = " + materialID;
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();

      sql = "delete from materials where materialID = " + materialID;
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();
      CodeMapper.deleteFromCodeMapper(materialID, itemCode);
      TypeMapper.getTypeMapper().deleteType(itemCode);

      connection.commit();
      connection.setAutoCommit(true);

    } catch (SQLException e) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (Exception e2) {
        //groot fokop
      }
      String mes = "Error when trying to delete Materials:  ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  //delete materials of given type from given supplier
  public void deleteMaterials(int materialType, String supplier) {

    TreeSet set = getMaterialIDForSpecicficType(materialType);
    for (Iterator iter = set.iterator(); iter.hasNext(); ) {
      Integer materialID = (Integer) iter.next();
      try {
        connection.setAutoCommit(false);
        connection.commit();
        String sql = "delete from materialdetails where materialdetails.supplier = ?";
        sql += " and materialdetails.materialid = " + materialID.intValue();

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, supplier);
        ps.executeUpdate();
        ps.close();
        connection.commit();
        connection.setAutoCommit(true);
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public TreeSet getMaterialIDForSpecicficType(int materialType) {
    TreeSet set = new TreeSet();
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "select materialid from materials where materialType = ?";


      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, materialType);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        set.add(new Integer(rs.getInt("materialid")));
      }
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return set;
  }

  //delete all materialdetails
  public void deleteAllMaterialDetails() {
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "delete from materialdetails";

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.executeUpdate();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {

    }
  }

  //delete all materials
  public void deleteAllMaterials() {
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "delete from materials";

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.executeUpdate();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {

    }
  }


  public boolean checkMaterialIDExist(int materialId) {
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "select * from materialdetails where materialid = ?";

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, materialId);
      ResultSet rs = ps.executeQuery();

      connection.commit();
      connection.setAutoCommit(true);
      if (rs.next()) {
        ps.close();
        return true;
      }
      else {
        ps.close();
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return true;
  }

  //delete Materials Of Specific Type For All Suppliers
  public void deleteMaterialsAllSuppliers(int materialType) {
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "delete from materials where materials.materialtype = ?";

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, materialType);
      ps.executeUpdate();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  //delete Materials from materials with given materialid
  public void deleteMaterialFromMaterialsOnly(int materialID) {
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "delete from materials where materialid = ?";

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setInt(1, materialID);
      ps.executeUpdate();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  //delete Materials Off AllType For Specific Supplier
  public void deleteMaterialsAllType(String supplier) {
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "delete from materialdetails where materialdetails.supplier = ?";

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, supplier);
      ps.executeUpdate();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  // Kry alle materialID's from materials
  public TreeSet getMaterialID() {
    TreeSet matSet = new TreeSet();
    try {
      connection.setAutoCommit(false);
      String sql = "select materialid from materials";
      PreparedStatement ps = connection.prepareStatement(sql);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        matSet.add(new Integer(rs.getInt("materialid")));
      }
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch(SQLException e) {
      e.printStackTrace();
    }
    return matSet;
  }



  // Save materials: word gebruik in artof self
  public void saveMaterials(ArrayList materialList) {
    Iterator it = materialList.iterator();
    while (it.hasNext()) {
      MaterialDets dets = (MaterialDets)it.next();
      saveMaterial(dets);
      if (dets.isImageModified())
        saveMaterialImage(dets);
    }
  }

  // Save material image
  public void saveMaterialImage(MaterialDets dets) {
    try {
      String path = "images/materials/" + dets.getItemCode().toLowerCase() + ".jpg";
      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(dets.getSampleImage());
      param.setQuality(1.0f, false);
      encoder.setJPEGEncodeParam(param);
      encoder.encode(dets.getSampleImage());
      out.flush();
      out.close();
      dets.setIsImageModified(false);

    } catch (Exception e ) {
      e.printStackTrace();
    }
  }

  // Save material
  private void saveMaterial(MaterialDets det) {
    try {
      if (!det.isModified()) return;
      connection.setAutoCommit(false);
      connection.commit();

      if (det.getMaterialID() == -1) {
        String sql = "insert into materials (ITEMCODE, MATERIALTYPE, OWNCODE, ";
        sql += "DESCRIPTION, MATERIAL_GROUP, COLORRED, COLORGREEN, COLORBLUE, COLORALPHA) ";
        sql += " values ('" + det.getItemCode() + "', " + det.getMaterialType() + ", '" + det.getOwnCode() + "', '";
        sql += det.getDescription() + "', '" + det.getGroup() + "', " + det.getColor().getRed() + ", ";
        sql += det.getColor().getGreen() + ", " + det.getColor().getBlue() + ", " + det.getColor().getAlpha( );
        sql += ")";
        Statement s = connection.createStatement();
        s.executeUpdate(sql);
        s.close();
        connection.commit();

        s = connection.createStatement();
        ResultSet rs = s.executeQuery("select materialID from materials where itemcode = '" + det.getItemCode() + "'");
        if (rs.next()) {
          det.setMaterialID(rs.getInt(1));
        }
        s.close();

        CodeMapper.addToCodeMapper(det);
        TypeMapper.getTypeMapper().addType(det.getItemCode(), det.getMaterialType());

      } else {
        String sql = "update materials set ITEMCODE = '" + det.getItemCode() + "'";
        sql += ", MATERIALTYPE = " + det.getMaterialType();
        sql += ", OWNCODE = '" + det.getOwnCode() + "', DESCRIPTION = '" + det.getDescription();
        sql += "', MATERIAL_GROUP = '" + det.getGroup() + "', COLORRED = " + det.getColor().getRed();
        sql += ", COLORGREEN = " + det.getColor().getGreen() + ", COLORBLUE = ";
        sql += det.getColor().getBlue() + ", COLORALPHA = " + det.getColor().getAlpha();
        sql += " where materialID = " + det.getMaterialID();
        Statement s = connection.createStatement();
        s.executeUpdate(sql);
        s.close();
        connection.commit();

        CodeMapper.updateCodeMapper(det);
      }

      // Save values
      Iterator it = det.getSuppliers().iterator();
      while (it.hasNext()) {
        String supplier = (String)it.next();
        saveMaterialValues(det.getMaterialID(), supplier, det.getMaterialValues(supplier));
        connection.commit();
      }

      connection.commit();
      connection.setAutoCommit(true);
      det.setModified(false);
    } catch (SQLException e2) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException x) {
        //major boggerop
      }
      String mes = "Error when trying to save material.  Check that Item Code " + det.getItemCode();
      mes += " or Own Code " + det.getOwnCode() + " does not already exist.";
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void saveMaterialValues(int materialID, String supplier, MaterialValues val) {
    try {

      try {
        String sql = "insert into materialdetails (MaterialID, SUPPLIER, STATUS, COST, COMPFACTOR, ";
        sql += "EXQFACT, MATWIDTH, MATLENGTH, THICKNESS, REBATE, PRICEARRAY) ";
        sql += "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, materialID);
        ps.setString(2, supplier);
        ps.setString(3, val.getStatus());
        ps.setFloat(4, val.getCost());
        ps.setFloat(5, val.getCompFactor());
        ps.setFloat(6, val.getExqFactor());
        ps.setFloat(7, val.getWidth());
        ps.setFloat(8, val.getLength());
        ps.setFloat(9, val.getThickness());
        ps.setFloat(10, val.getRebate());

        try {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ObjectOutputStream oout = new ObjectOutputStream(baos);
          oout.writeObject(val.getPriceArray());
          oout.close();
          ps.setBytes(11, baos.toByteArray());
        } catch (IOException e) {
          ps.setNull(11, Types.BLOB);
        }

        ps.execute();
        ps.close();

      } catch (SQLException e2) {
        String sql = "update materialdetails set STATUS = ?, COST = ?, COMPFACTOR = ?,";
        sql += " EXQFACT = ?, MATWIDTH = ?, MATLENGTH = ?, THICKNESS = ?, REBATE = ?, PRICEARRAY = ?";
        sql += " where materialID = ? AND SUPPLIER = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, val.getStatus());
        ps.setFloat(2, val.getCost());
        ps.setFloat(3, val.getCompFactor());
        ps.setFloat(4, val.getExqFactor());
        ps.setFloat(5, val.getWidth());
        ps.setFloat(6, val.getLength());
        ps.setFloat(7, val.getThickness());
        ps.setFloat(8, val.getRebate());
        try {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ObjectOutputStream oout = new ObjectOutputStream(baos);
          oout.writeObject(val.getPriceArray());
          oout.close();
          ps.setBytes(9, baos.toByteArray());
        } catch (IOException e) {
          ps.setNull(9, Types.BLOB);
        }

        ps.setInt(10, materialID);
        ps.setString(11, supplier);
        ps.execute();
        ps.close();
      }

      val.setModified(false);

    } catch (SQLException e) {
      String mes = "Error when trying to save material values: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void saveMaterialRebate(int materialID, float rebate) {
    try {
       String sql = "update materialdetails set REBATE = ?";
       sql += " where materialID = ?";
       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setFloat(1, rebate);
       ps.setInt(2, materialID);
       ps.execute();
       ps.close();
    } catch (SQLException e) {
      String mes = "Error when trying to save material values: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void updateMaterialColor(String itemCode, Color newColor) {
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "update materials set COLORRED = " + newColor.getRed();
      sql += ", COLORGREEN = " + newColor.getGreen() + ", COLORBLUE = ";
      sql += newColor.getBlue() + ", COLORALPHA = " + newColor.getAlpha();
      sql += " where ITEMCODE	= '" + itemCode + "'";
      Statement s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();
      connection.commit();
      connection.setAutoCommit(true);

    } catch (SQLException e) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException x) {
        //major boggerop
      }
      String mes = "Error when trying to save material color.";
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // vir stock type mapping
  public HashMap getMaterialItemTypeMap() {
    HashMap itemMap = new HashMap();
    try {
      String sql = "select ItemCode, materialtype from materials ";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        itemMap.put(rs.getString(1), new Integer(rs.getInt(2)));
      }
      s.close();

    } catch (SQLException e) {
      String mes = "Error when trying to build material type map";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return itemMap;
  }

  // Save material - vir importer - update nie sodat gebruiker se goed nie oorskryf word nie
  public String importMaterial(MaterialDets det) {
    try {
      connection.setAutoCommit(false);
      connection.commit();
      String sql = "insert into materials (ITEMCODE, MATERIALTYPE, OWNCODE, ";
      sql += "DESCRIPTION, MATERIAL_GROUP, COLORRED, COLORGREEN, COLORBLUE, COLORALPHA) ";
      sql += "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, det.getItemCode());
      ps.setInt(2, det.getMaterialType());
      ps.setString(3, det.getOwnCode());
      ps.setString(4, det.getDescription());
      ps.setString(5, det.getGroup());
      ps.setInt(6, det.getColor().getRed());
      ps.setInt(7, det.getColor().getGreen());
      ps.setInt(8, det.getColor().getBlue());
      ps.setInt(9, det.getColor().getAlpha());
      ps.executeUpdate();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
      //if (det.getSampleImage() != null) {
      //  saveMaterialImage(det);
      //}

      return "Inserted material: " + det.getItemCode();

    } catch (SQLException e) {
      try {
        connection.setAutoCommit(false);
        connection.commit();
        String sql = "update materials set MaterialType = ? ";
        if (UserSettings.matOwnCode) sql += ", OWNCODE = ? ";
        if (UserSettings.matDescription) sql += ", DESCRIPTION = ? ";
        if (UserSettings.matMatGroup) sql += ", MATERIAL_GROUP = ? ";
        if (UserSettings.matColour) {
          sql += ", COLORRED = ? ";
          sql += ", COLORGREEN = ? ";
          sql += ", COLORBLUE = ? ";
          sql += ", COLORALPHA = ? ";
        }
        sql += "where itemCode = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        int i = 1;
        ps.setInt(i++, det.getMaterialType());
        if (UserSettings.matOwnCode) ps.setString(i++, det.getOwnCode());
        if (UserSettings.matDescription) ps.setString(i++, det.getDescription());
        if (UserSettings.matMatGroup) ps.setString(i++, det.getGroup());
        if (UserSettings.matColour) {
          ps.setInt(i++, det.getColor().getRed());
          ps.setInt(i++, det.getColor().getGreen());
          ps.setInt(i++, det.getColor().getBlue());
          ps.setInt(i++, det.getColor().getAlpha());
        }
        ps.setString(i++, det.getItemCode());
        ps.executeUpdate();
        ps.close();
        connection.commit();
        connection.setAutoCommit(true);

        //if (UserSettings2.matImage && det.getSampleImage() != null) {
        //  saveMaterialImage(det);
        //}

        return "Updated material: " + det.getItemCode();

      } catch (SQLException e2) {
        try {
          connection.rollback();
          connection.setAutoCommit(true);
        } catch (SQLException x) {
          // groot fokop
        }
        return "Error updating material: " + det.getItemCode() + ".  Message: " + e.getMessage();
      }
    }
  }

  // Save material - vir importer - update nie sodat gebruiker se goed nie oorskryf word nie
  public String importMaterialValues(String itemCode, String supplier, MaterialValues val) {
    int materialID = -1;
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String sql = "select materialID from materials where itemCode = ?";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, itemCode);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        materialID = rs.getInt(1);
      }
      ps.close();

      if (materialID == -1) {
        try {
          connection.rollback();
          connection.setAutoCommit(true);
        } catch (SQLException x) {
          // groot fokop
        }
        return "Error when inserting material values: " + itemCode;
      }

      sql = "insert into materialdetails (materialID, SUPPLIER, STATUS, COST, COMPFACTOR, ";
      sql += "EXQFACT, MATWIDTH, MATLENGTH, THICKNESS, REBATE) ";
      sql += "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      ps = connection.prepareStatement(sql);
      ps.setInt(1, materialID);
      ps.setString(2, supplier);
      ps.setString(3, val.getStatus());
      ps.setFloat(4, val.getCost());
      ps.setFloat(5, 1.f);
      ps.setFloat(6, 1.f);
      ps.setFloat(7, val.getWidth());
      ps.setFloat(8, val.getLength());
      ps.setFloat(9, val.getThickness());
      ps.setFloat(10, val.getRebate());
      ps.execute();
      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
      return "Inserted material values: " + itemCode;

    } catch (SQLException e2) {
      try {
        String sql = "update materialdetails set STATUS = ? ";
        if (UserSettings.matCost) sql += ", COST = ? ";
        if (UserSettings.matWidth) sql += ", MATWIDTH = ? ";
        if (UserSettings.matLength) sql += ", MATLENGTH = ? ";
        if (UserSettings.matThickness) sql += ", THICKNESS = ? ";
        if (UserSettings.matRebate) sql += ", REBATE = ? ";
        sql += " where materialID = ? AND SUPPLIER = ? ";

        int i = 1;
        PreparedStatement ps = connection.prepareStatement(sql);
        if (UserSettings.matStatus) ps.setString(i++, val.getStatus());
        if (UserSettings.matCost) ps.setFloat(i++, val.getCost());
        if (UserSettings.matWidth) ps.setFloat(i++, val.getWidth());
        if (UserSettings.matLength) ps.setFloat(i++, val.getLength());
        if (UserSettings.matThickness) ps.setFloat(i++, val.getThickness());
        if (UserSettings.matRebate) ps.setFloat(i++, val.getRebate());
        ps.setInt(i++, materialID);
        ps.setString(i++, supplier);

        ps.executeUpdate();
        ps.close();

        connection.commit();
        connection.setAutoCommit(true);
        return "Updated material values: " + itemCode;

      } catch (SQLException e) {
        try {
          connection.rollback();
          connection.setAutoCommit(true);
        } catch (SQLException x) {
          // groot fokop
        }
        return "Error updating material values: " + itemCode + " Message: " + e.getMessage();
      }
    }
  }

  /*------------------------------- SUPPLIERS --------------------------------*/

  // Get Suppliers
  public ArrayList getSupplierNames() {
    ArrayList supList = new ArrayList();
    try {
      String sql = "select Supplier from suppliers order by Supplier";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        String supplier = (String)rs.getString(1);
        supList.add(supplier);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Suppliers ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return supList;
  }

  // Get Suppliers
  public ArrayList getSuppliers() {
    ArrayList supList = new ArrayList();
    try {
      String sql = "select * from suppliers order by Supplier";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        SupplierDets dets = new SupplierDets();
        dets.setName((String)rs.getString("SUPPLIER"));

        String[] data = new String[9];
        data[SupplierDets.SUPPLIER_PERSON] = rs.getString("PERSON");
        data[SupplierDets.SUPPLIER_TEL] = rs.getString("TEL");
        data[SupplierDets.SUPPLIER_FAX] = rs.getString("FAX");
        data[SupplierDets.SUPPLIER_CEL] = rs.getString("CEL");
        data[SupplierDets.SUPPLIER_AD1] = rs.getString("AD1");
        data[SupplierDets.SUPPLIER_AD2] = rs.getString("AD2");
        data[SupplierDets.SUPPLIER_AD3] = rs.getString("AD3");
        data[SupplierDets.SUPPLIER_CODE] = rs.getString("CODE");
        data[SupplierDets.SUPPLIER_EMAIL] = rs.getString("EMAIL");
        dets.setDataArray(data);

        dets.setModified(false);
        supList.add(dets);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Suppliers ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return supList;
  }

  public void saveSuppliers(ArrayList supList) {
    Iterator it = supList.iterator();
    while (it.hasNext()) {
      saveSupplier((SupplierDets)it.next());
    }
  }

  public static String getExpired() {
    try {
      //String mes = "Your registration has expired.  Please go to Settings | Registration ";
      //mes += "to renew your registration.";
      String mes = "WW91ciByZWdpc3RyYXRpb24gaGFzIGV4cGlyZWQuICBQbGVhc2UgZ28gdG8gU2V0dGluZ3MgfCBSZWdpc3RyYXRpb24gdG8gcmVuZXcgeW91ciByZWdpc3RyYXRpb24u";
      byte[] res = (new BASE64Decoder()).decodeBuffer(mes);
      return new String(res, "UTF-8");
    } catch (Exception e) {
      return "";
    }
  }

  public static String getExpiredo() {
    try {
      //  String mes =
      //      "Your registration will expire today.  Please go to Settings | Registration ";
      //  mes += "to renew your registration.";
      String mes = "WW91ciByZWdpc3RyYXRpb24gd2lsbCBleHBpcmUgdG9kYXkuICBQbGVhc2UgZ28gdG8gU2V0dGluZ3MgfCBSZWdpc3RyYXRpb24gdG8gcmVuZXcgeW91ciByZWdpc3RyYXRpb24u";
      byte[] res = (new BASE64Decoder()).decodeBuffer(mes);
      return new String(res, "UTF-8");
    } catch (Exception e) {
      return "";
    }
  }

  public static String getExpiredi() {
    try {
      //String mes = "Your registration will expire tommorrow.  Please go to Settings | Registration ";
      //mes += "to renew your registration.";
      String mes = "WW91ciByZWdpc3RyYXRpb24gd2lsbCBleHBpcmUgdG9tbW9ycm93LiAgUGxlYXNlIGdvIHRvIFNldHRpbmdzIHwgUmVnaXN0cmF0aW9uIHRvIHJlbmV3IHlvdXIgcmVnaXN0cmF0aW9uLg==";
      byte[] res = (new BASE64Decoder()).decodeBuffer(mes);
      return new String(res, "UTF-8");
    } catch (Exception e) {
      return "";
    }
  }

  public static String getExpiredx(int d) {
    try {
      //byte[] res = (new BASE64Decoder()).decodeBuffer(mes);
      //return new String(res, "UTF-8");
      String mes1 = "WW91ciByZWdpc3RyYXRpb24gd2lsbCBleHBpcmUgaW4g";
      byte[] res1 = (new BASE64Decoder()).decodeBuffer(mes1);
      String mes2 = "IGRheXMuICBQbGVhc2UgZ28gdG8gU2V0dGluZ3MgfCBSZWdpc3RyYXRpb24gdG8gcmVuZXcgeW91ciByZWdpc3RyYXRpb24u";
      byte[] res2 = (new BASE64Decoder()).decodeBuffer(mes2);
      return new String(res1, "UTF-8") + String.valueOf(d) + new String(res2, "UTF-8");
    } catch (Exception e) {
      return "";
    }
  }

  // Save Suppliers
  public boolean saveSupplier(SupplierDets dets) {
    String[] data = dets.getDataArray();
    if (data == null) {
      data = new String[9];
    }

    try {
      String sql = "insert into suppliers (Supplier, PERSON, TEL, FAX, CEL, AD1, ";
      sql += "AD2, AD3, CODE, EMAIL) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, dets.getName());
      ps.setString(2, data[SupplierDets.SUPPLIER_PERSON]);
      ps.setString(3, data[SupplierDets.SUPPLIER_TEL]);
      ps.setString(4, data[SupplierDets.SUPPLIER_FAX]);
      ps.setString(5, data[SupplierDets.SUPPLIER_CEL]);
      ps.setString(6, data[SupplierDets.SUPPLIER_AD1]);
      ps.setString(7, data[SupplierDets.SUPPLIER_AD2]);
      ps.setString(8, data[SupplierDets.SUPPLIER_AD3]);
      ps.setString(9, data[SupplierDets.SUPPLIER_CODE]);
      ps.setString(10, data[SupplierDets.SUPPLIER_EMAIL]);
      ps.execute();
      ps.close();
      dets.setModified(false);
      return true;

    } catch (SQLException e) {
      try {
        String sql = "update suppliers set PERSON = ?, TEL = ?, FAX = ?, CEL = ?, ";
        sql += "AD1 = ?, AD2 = ?, AD3 = ?, CODE = ?, EMAIL = ? where Supplier = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, data[SupplierDets.SUPPLIER_PERSON]);
        ps.setString(2, data[SupplierDets.SUPPLIER_TEL]);
        ps.setString(3, data[SupplierDets.SUPPLIER_FAX]);
        ps.setString(4, data[SupplierDets.SUPPLIER_CEL]);
        ps.setString(5, data[SupplierDets.SUPPLIER_AD1]);
        ps.setString(6, data[SupplierDets.SUPPLIER_AD2]);
        ps.setString(7, data[SupplierDets.SUPPLIER_AD3]);
        ps.setString(8, data[SupplierDets.SUPPLIER_CODE]);
        ps.setString(9, data[SupplierDets.SUPPLIER_EMAIL]);
        ps.setString(10, dets.getName());
        ps.execute();
        ps.close();
        dets.setModified(false);
        return true;

      } catch (SQLException sx) {
        return false;
      }
    }
  }

  // Save Suppliers vir importing
  public String importSupplier(SupplierDets dets) {
    if (saveSupplier(dets))
      return "Updated supplier: " + dets.getName();
    else
      return "Error updating supplier: " + dets.getName();
  }

  // Delete supplier
  public void deleteSupplier(String supplierName, JDialog d) {
    String mes = "All materials that belong to this supplier will be deleted.  ";
    mes += "Are you sure you want to continue?";
    if (JOptionPane.showConfirmDialog(d, mes, "Delete",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
      try {
        String sql = "delete from suppliers where Supplier = ?";
        PreparedStatement s = connection.prepareStatement(sql);
        s.setString(1, supplierName);
        s.executeUpdate();
        s.close();

      } catch (SQLException e) {
        mes = "Error when trying to delete Supplier ";
        mes += e.getMessage();
        JOptionPane.showMessageDialog(null, mes, "Error",JOptionPane.ERROR_MESSAGE);
      }
    }
  }


  /*--------------------------------- STOCK ----------------------------------*/

  // Kry active voorraad om te gebruik in designs
  public ArrayList getStockForDesigns(int matType, int designID) {
    String  sql = "select * from stock ";
    sql += "where (entryType = " + StockItem.STOCK_ORDER;
    sql += " or entryType = " + StockItem.STOCK_OFFCUT;
    sql += " or entryType = " + StockItem.STOCK_TAKE_COMPLETED + ")";

    if (matType != MaterialDets.MAT_ALL)
      sql += " and MatType = " + matType;

    if (designID > -1) {
      sql += " and ((DesignID = " + designID + " and exitType = " + StockItem.STOCK_DESIGN + ")";
      //sql += " or (designID = -1 and exitType = -1))";
      sql += " or (exitType = -1))";
    } else {
      sql += " and exitType = -1 ";
    }

    sql += " and stocktakeid = -1 order by ItemCode, STOCKLENGTH, STOCKHEIGHT";
    ArrayList stockList = getStockItems(sql);
    Iterator it = stockList.iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();
      if (item.getDesignID() > -1 && item.getExitType() == StockItem.STOCK_DESIGN)
        item.setSelected(true);
    }
    return stockList;
  }

  // Kry voorraad wat al vir 'n design geallokkeer is om die offcuts te bereken
  public ArrayList getStockAlreadyAllocatedToDesign(int designID) {
    if (designID == -1)
      return new ArrayList();

    String  sql = "select * from stock ";
    sql += "where (entryType = " + StockItem.STOCK_ORDER;
    sql += " or entryType = " + StockItem.STOCK_OFFCUT;
    sql += " or entryType = " + StockItem.STOCK_TAKE_COMPLETED + ")";
    sql += " and DesignID = " + designID + " and exitType = " + StockItem.STOCK_DESIGN;
    sql += " and stocktakeid = -1 order by ItemCode, STOCKLENGTH, STOCKHEIGHT";
    return getStockItems(sql);
  }

  // Kry al die active voorraad van 'n sekere item
  public ArrayList getCurrentStockPerItemCode(String itemCode) {
    String  sql = "select * from stock ";
    sql += "where (entryType = " + StockItem.STOCK_ORDER;
    sql += " or entryType = " + StockItem.STOCK_OFFCUT;
    sql += " or entryType = " + StockItem.STOCK_TAKE_COMPLETED + ")";
    sql += " and itemCode = '" + itemCode + "' ";
    sql += " and exitType = -1 ";
    sql += " and stocktakeid = -1 order by STOCKLENGTH, STOCKHEIGHT";
    return getStockItems(sql);
  }

  // Kry active voorraad vir verkope
  public ArrayList getStockForSale(int matType) {
    String  sql = "select * from stock ";
    sql += "where (entryType = " + StockItem.STOCK_ORDER;
    sql += " or entryType = " + StockItem.STOCK_OFFCUT;
    sql += " or entryType = " + StockItem.STOCK_TAKE_COMPLETED + ")";
    sql += " and exitType = -1";

    if (matType != MaterialDets.MAT_ALL)
      sql += " and MatType = " + matType;

    sql += " order by ItemCode, STOCKLENGTH, STOCKHEIGHT";
    return getStockItems(sql);
  }

  // Kry voorraad wat sover in huidge opname is
  public ArrayList getStockInStockTake(boolean groupItems) {
    String sql = "select * from stock ";
    sql += "where entryType = " + StockItem.STOCK_TAKE_IN_PROGRESS;
    sql += " order by ItemCode, STOCKLENGTH, STOCKHEIGHT";

    float delta = 0.01f;
    if (groupItems) {
      ArrayList groupList = new ArrayList();
      ArrayList itemList = getStockItems(sql);
      Iterator it = itemList.iterator();
      while (it.hasNext()) {
        StockItem item = (StockItem)it.next();
        boolean added = false;

        Iterator it2 = groupList.iterator();
        while (it2.hasNext()) {
          StockItem group = (StockItem)it2.next();
          if (item.getItemCode().equals(group.getItemCode()) &&
              item.getSupplier().equals(group.getSupplier()) &&
              Math.abs(item.getLength() - group.getLength()) < delta &&
              Math.abs(item.getWidth() - group.getWidth()) < delta) {
            group.addCountID(item.getStockID());
            group.setCount(group.getCountListSize());
            group.setModified(false);
            added = true;
            break;
          }
        }

        if (!added) {
          item.setCount(1);
          item.addCountID(item.getStockID());
          item.setModified(false);
          groupList.add(item);
        }
      }

      return groupList;

    } else {
      return getStockItems(sql);
    }
  }

  // Kry vorige voorraadopnames
  public ArrayList getPreviousStockTake(int stockTakeID, boolean groupItems) {
    String sql = "select * from stock ";
    sql += "where entryType = " + StockItem.STOCK_TAKE_COMPLETED;
    sql += " and stockTakeID = " + stockTakeID;
    sql += " order by ItemCode, mattype, STOCKLENGTH, STOCKHEIGHT";

    float delta = 0.01f;
    if (groupItems) {
      ArrayList groupList = new ArrayList();
      ArrayList itemList = getStockItems(sql);
      Iterator it = itemList.iterator();
      while (it.hasNext()) {
        StockItem item = (StockItem)it.next();
        boolean added = false;

        Iterator it2 = groupList.iterator();
        while (it2.hasNext()) {
          StockItem group = (StockItem)it2.next();
          if (item.getItemCode().equals(group.getItemCode()) &&
              item.getSupplier().equals(group.getSupplier()) &&
              Math.abs(item.getLength() - group.getLength()) < delta &&
              Math.abs(item.getWidth() - group.getWidth()) < delta) {
            group.addCountID(item.getStockID());
            group.setCount(group.getCountListSize());
            group.setModified(false);
            added = true;
            break;
          }
        }

        if (!added) {
          item.setCount(1);
          item.addCountID(item.getStockID());
          item.setModified(false);
          groupList.add(item);
        }
      }

      return groupList;

    } else {
      return getStockItems(sql);
    }
  }

  // Kry offcuts van sekere ontwerp
   public ArrayList getStockOffcuts(int designID) {
    if (designID == -1)
      return new ArrayList();

    String  sql = "select * from stock ";
    sql += "where entryType = " + StockItem.STOCK_OFFCUT;
    sql += " and designID = " + designID;
    sql += " and exitType = -1";
    sql += " order by ItemCode, Supplier, STOCKDATE ";

    return getStockItems(sql);
  }

  // Kry huidige voorraad
  public ArrayList getCurrentStock(int type, boolean groupItems) {
    int stockTakeID = -1;
    String sql = "select * from stock where ";

    if (type != MaterialDets.MAT_ALL)
      sql += " mattype = " + type + " and ";

    sql += " exittype = -1 ";
    sql += " and entrytype <> " + StockItem.STOCK_TAKE_IN_PROGRESS;
    sql += " and stocktakeid = -1";
    sql += " order by mattype, itemcode, supplier";

    float delta = 0.01f;
    if (groupItems) {
      ArrayList groupList = new ArrayList();
      ArrayList itemList = getStockItems(sql);
      Iterator it = itemList.iterator();
      while (it.hasNext()) {
        StockItem item = (StockItem)it.next();
        boolean added = false;

        Iterator it2 = groupList.iterator();
        while (it2.hasNext()) {
          StockItem group = (StockItem)it2.next();
          if (item.getItemCode().equals(group.getItemCode()) &&
              item.getSupplier().equals(group.getSupplier()) &&
              Math.abs(item.getLength() - group.getLength()) < delta &&
              Math.abs(item.getWidth() - group.getWidth()) < delta) {
            group.addCountID(item.getStockID());
            group.setCount(group.getCountListSize());
            group.setModified(false);
            added = true;
            break;
          }
        }

        if (!added) {
          item.setCount(1);
          item.addCountID(item.getStockID());
          item.setModified(false);
          groupList.add(item);
        }
      }

      return groupList;

    } else {
      return getStockItems(sql);
    }
  }

  // Kry voorraad
  public ArrayList getStockItems(String sql) {
    ArrayList itemList = new ArrayList();
    try {
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        StockItem item = new StockItem();
        item.setStockID(rs.getInt("STOCKID"));
        item.setItemCode(rs.getString("ITEMCODE"));
        item.setSupplier(rs.getString("SUPPLIER"));
        item.setDate(rs.getInt("STOCKDATE"));
        item.setLength(rs.getFloat("STOCKLENGTH"));
        item.setWidth(rs.getFloat("STOCKHEIGHT"));
        item.setCost(rs.getFloat("COSTPERUNIT"));
        item.setDesignID(rs.getInt("DESIGNID"));
        item.setOrderID(rs.getString("ORDERID"));
        item.setMatType(rs.getInt("MATTYPE"));
        item.setEntryType(rs.getInt("ENTRYTYPE"));
        item.setExitType(rs.getInt("EXITTYPE"));
        item.setShelf(rs.getString("SHELF"));
        item.setItemLengthsAllocated((float[])rs.getObject("ITEMLENGTHSALLOCATED"));
        /*try {
          byte[] buf = rs.getBytes("ITEMLENGTHSALLOCATED");
          ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
          item.setItemLengthsAllocated((float[])objectIn.readObject());
        } catch (Exception x) {
          // doen niks
        }*/

        item.setModified(false);
        itemList.add(item);
      }
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to read Stock Items ";
      mes += e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
    return itemList;
  }

  // Save voorraad
  public void updateStockItems(ArrayList itemList) {
    Iterator it = itemList.iterator();
    while (it.hasNext()) {
      StockItem item = (StockItem)it.next();
      if (item.isModified())
        saveStockItem(item);
    }
  }

  // Save vorraad
  public void saveStockItems(ArrayList itemList, boolean groupItems) {
    if (groupItems) {
      Iterator it = itemList.iterator();
      while (it.hasNext()) {
        StockItem item = (StockItem)it.next();

        if (item.isModified()) {
          // delete orige items
          Iterator it2 = item.getWaaiListIterator();
          while (it2.hasNext()) {
            deleteStockItem(((Integer)it2.next()).intValue());
          }

          // sit items in soos nodig
          ArrayList countList = item.getCountList();
          for (int i = 0; i < countList.size(); i++) {
            int stockID = ((Integer)countList.get(i)).intValue();
            item.setStockID(stockID);
            item.setModified(true);
            saveStockItem(item);
            countList.set(i, new Integer(item.getStockID()));
          }
        }
      }

    } else {
      Iterator it = itemList.iterator();
      while (it.hasNext()) {
        StockItem item = (StockItem)it.next();
        item.setCount(1);
        if (item.isModified())
          saveStockItem(item);
      }
    }
  }

  private void saveStockItem(StockItem item) {
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String sql;
      if (item.getStockID() == -1) {
        int newID = 1;
        sql = "select max(stockid) from stock";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
          newID = rs.getInt(1);
        }
        s.close();
        newID++;
        item.setStockID(newID);

        sql = "insert into stock (STOCKID, ITEMCODE, SUPPLIER, STOCKDATE, STOCKLENGTH, ";
        sql += "STOCKHEIGHT, COSTPERUNIT, DESIGNID, ORDERID, matType, ";
        sql += "EntryType, ExitType, stocktakeid, itemLengthsAllocated, lastDesignID) ";
        sql += " values (" + newID + ", ";
        sql += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ";
        sql += "?, -1, ?, -1)";

      } else {
        int lastDesignID = -1;
        sql = "select designID from stock where stockid = " + item.getStockID();
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
          lastDesignID = rs.getInt(1);
        }
        s.close();

        sql = "update stock set ITEMCODE = ?, SUPPLIER = ?, ";
        sql += "STOCKDATE = ?, STOCKLENGTH = ?, STOCKHEIGHT = ?, ";
        sql += "COSTPERUNIT = ?, DESIGNID = ?, ORDERID = ?, ";
        sql += "matType = ?, EntryType = ?, ExitType = ?, itemLengthsAllocated = ?, ";
        sql += "lastdesignid = " + lastDesignID;
        sql += " where STOCKID = " + item.getStockID();
      }

      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1, item.getItemCode());
      ps.setString(2, item.getSupplier());
      ps.setInt(3, item.getDate());
      ps.setFloat(4, item.getLength());
      ps.setFloat(5, item.getWidth());
      ps.setFloat(6, item.getCost());
      ps.setInt(7, item.getDesignID());
      ps.setString(8, item.getOrderID());
      ps.setInt(9, item.getMatType());
      ps.setInt(10, item.getEntryType());
      ps.setInt(11, item.getExitType());
      ps.setObject(12, item.getItemLengthsAllocated());
      ps.executeUpdate();
      ps.close();

      connection.commit();
      item.setModified(false);
      connection.setAutoCommit(true);

    } catch (SQLException e) {
     try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException x) {
        // groot fokop
      }
      String mes = "Error when trying to save: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void deleteStockItem(int stockID) {
    try {
      String sql = "delete from stock where stockid = " + stockID;
      Statement s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();
    } catch (SQLException e) {
      String mes = "Error when trying to delete item: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public boolean completeStockTake() {
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String sql = "select max(stocktakeid) from stock";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int stockTakeID = 0;
      if (rs.next()) {
        stockTakeID = rs.getInt(1);
      }
      s.close();
      stockTakeID++;

      sql = "update stock set exittype = " + StockItem.STOCK_TAKE_OLD;
      sql += ", stocktakeid = " + stockTakeID;
      sql += " where exittype = -1 and entryType <> " + StockItem.STOCK_TAKE_IN_PROGRESS;
      //sql += " and entryType <> " + StockItem.STOCK_TAKE_COMPLETED;
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();

      sql = "update stock set stocktakeid = " + stockTakeID;
      sql += " where stocktakeid = -1 and entryType <> " + StockItem.STOCK_TAKE_IN_PROGRESS;
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();

      sql = "update stock set entrytype = " + StockItem.STOCK_TAKE_COMPLETED;
      sql += " where entrytype = " + StockItem.STOCK_TAKE_IN_PROGRESS;
      s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();

      connection.commit();
      connection.setAutoCommit(true);
      return true;

    } catch (SQLException e2) {
      try {
        connection.rollback();
        connection.setAutoCommit(true);
      } catch (SQLException x) {
        //major boggerop
      }
      return false;
    }
  }

  public ArrayList getPreviousStockTakes(boolean descending) {
    ArrayList stockList = new ArrayList();
    try {
      String sql = "select distinct stocktakeid, stockdate from stock order by stocktakeid";
      if (descending) sql += " desc";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      String latest = null;
      while (rs.next()) {
        int id = rs.getInt(1);
        if (id == -1) {
          latest = "Current:  " + Utils.getDatumStr(rs.getInt(2));
        } else {
          String item = String.valueOf(rs.getInt(1));
          item += ". " + Utils.getDatumStr(rs.getInt(2));
          stockList.add(item);
        }
      }
      s.close();

      if (latest != null) {
        if (descending)
          stockList.add(0, latest);
        else
          stockList.add(latest);
      }
      return stockList;

    } catch (SQLException e2) {
      return stockList;
    }
  }

  public boolean isStockTakeInProgress() {
    try {
      String sql = "select count(entryType) from stock where entrytype = " + StockItem.STOCK_TAKE_IN_PROGRESS;
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int count = 0;
      if (rs.next()) {
        count = rs.getInt(1);
      }
      s.close();
      if (count > 0)
        return true;
      else
        return false;

    } catch (SQLException e2) {
      return false;
    }
  }

  public ArrayList getTotalStockInStockTake(int stockTakeID) {
    ArrayList stockList = new ArrayList();
    try {
      String sql = "select itemCode, supplier, mattype, sum(stocklength/1000) as totalunits, 'm' as unit ";
      sql += "from stock where (mattype = " + MaterialDets.MAT_FRAME;
      sql += " or mattype = " + MaterialDets.MAT_SLIP + " or mattype = " + MaterialDets.MAT_FOIL;
      sql += ") and entrytype = " + StockItem.STOCK_TAKE_COMPLETED + " and stocktakeid = " + stockTakeID;
      sql += " group by itemcode, supplier, mattype order by mattype, itemcode, supplier";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        String[] item = new String[5];
        if (rs.getString("itemCode") == null)
          continue;

        item[0] = rs.getString("itemCode");
        item[1] = rs.getString("supplier");
        item[2] = String.valueOf(rs.getInt("mattype"));
        item[3] = String.valueOf(rs.getFloat("totalunits"));
        item[4] = rs.getString("unit");
        stockList.add(item);
      }
      s.close();

      sql = "select itemCode, supplier, mattype, sum(stocklength*stockheight/1000000) as totalunits, 'm�' as unit ";
      sql += "from stock where (mattype = " + MaterialDets.MAT_BOARD;
      sql += " or mattype = " + MaterialDets.MAT_GB;
      sql += ") and entrytype = " + StockItem.STOCK_TAKE_COMPLETED + " and stocktakeid = " + stockTakeID;
      sql += " group by itemcode, supplier, mattype order by mattype, itemcode, supplier";
      s = connection.createStatement();
      rs = s.executeQuery(sql);
      while (rs.next()) {
        String[] item = new String[5];
        if (rs.getString("itemCode") == null)
          continue;

        item[0] = rs.getString("ItemCode");
        item[1] = rs.getString("supplier");
        item[2] = String.valueOf(rs.getInt("mattype"));
        item[3] = String.valueOf(rs.getFloat("totalunits"));
        item[4] = rs.getString("unit");
        stockList.add(item);
      }
      s.close();

      return stockList;

    } catch (SQLException e2) {
      e2.printStackTrace();
      return stockList;
    }
  }

  public ArrayList getTotalEstimatedStock(int stockTakeID) {
    return getTotalEstimatedStock(stockTakeID, MaterialDets.MAT_ALL);
  }

  public ArrayList getTotalEstimatedStock(int stockTakeID, int materialType) {
    ArrayList stockList = new ArrayList();
    try {
      if (stockTakeID == -1) {
        String sql = "select max(stockTakeID) from stock";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
          stockTakeID = rs.getInt(1);
        }
        s.close();

      } else if (stockTakeID == 0) {
        stockTakeID = -2;

      } else {
        stockTakeID--;
      }

      // materials in meter
      String sql = "select itemCode, supplier, mattype, sum(stocklength/1000) as totalunits, 'm' as unit ";
      sql += "from stock where ";

      if (materialType == MaterialDets.MAT_ALL) {
        sql += "(mattype = " + MaterialDets.MAT_FRAME;
        sql += " or mattype = " + MaterialDets.MAT_SLIP + " or mattype = " + MaterialDets.MAT_FOIL + ")";

      } else if (materialType == MaterialDets.MAT_FRAME || materialType == MaterialDets.MAT_SLIP || materialType == MaterialDets.MAT_FOIL) {
        sql += " mattype = " + materialType;

      } else {
        sql += " mattype = -1 ";
      }

      sql += " and exittype = " + StockItem.STOCK_TAKE_OLD;
      sql += " and entrytype <> " + StockItem.STOCK_TAKE_IN_PROGRESS;
      sql += " and stocktakeid = " + stockTakeID;
      sql += " group by itemcode, supplier, mattype order by mattype, itemcode, supplier";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        String[] item = new String[5];
        if (rs.getString("itemCode") == null)
          continue;

        item[0] = rs.getString("ItemCode");
        item[1] = rs.getString("supplier");
        item[2] = String.valueOf(rs.getInt("mattype"));
        item[3] = String.valueOf(rs.getFloat("totalunits"));
        item[4] = rs.getString("unit");
        stockList.add(item);
      }
      s.close();

      // materials in vierkante meter
      sql = "select itemCode, supplier, mattype, sum(stocklength*stockheight/1000000) as totalunits, 'm�' as unit ";
      sql += "from stock where ";

      if (materialType == MaterialDets.MAT_ALL) {
        sql += "(mattype = " + MaterialDets.MAT_BOARD + " or mattype = " + MaterialDets.MAT_GB + ")";

      } else if (materialType == MaterialDets.MAT_BOARD || materialType == MaterialDets.MAT_GB) {
        sql += "mattype = " + materialType;

      } else {
        sql += "mattype = -1 ";
      }

      sql += " and exittype = " + StockItem.STOCK_TAKE_OLD;
      sql += " and entrytype <> " + StockItem.STOCK_TAKE_IN_PROGRESS;
      sql += " and stocktakeid = " + stockTakeID;
      sql += " group by itemcode, supplier, mattype order by mattype, itemcode, supplier";
      s = connection.createStatement();
      rs = s.executeQuery(sql);
      while (rs.next()) {
        String[] item = new String[5];
        if (rs.getString("itemCode") == null)
          continue;

        item[0] = rs.getString("ItemCode");
        item[1] = rs.getString("supplier");
        item[2] = String.valueOf(rs.getInt("mattype"));
        item[3] = String.valueOf(rs.getFloat("totalunits"));
        item[4] = rs.getString("unit");
        stockList.add(item);
      }
      s.close();

      return stockList;

    } catch (SQLException e2) {
      return stockList;
    }
  }

  public void removeStockDesignAllocation(int designID) {
    try {
      // rollback al die stock wat na die design geallocate is
      String sql = "update stock set exittype = -1, designid = -1 ";
      sql += "where (entryType = " + StockItem.STOCK_ORDER;
      sql += " or entryType = " + StockItem.STOCK_OFFCUT;
      sql += " or entryType = " + StockItem.STOCK_TAKE_COMPLETED + ")";
      sql += " and exitType = " + StockItem.STOCK_DESIGN;
      sql += " and stocktakeid = -1 ";
      sql += " and designID = " + designID;
      Statement s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();

    } catch (SQLException e) {
      String mes = "Database Error: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void removeStockOffCutAllocation(int designID) {
    try {
      // delete al die offcuts van die design wat in die db is
      String sql = "delete from stock where designID = " + designID;
      sql += " and entryType = " + StockItem.STOCK_OFFCUT;
      sql += " and exitType = " + -1;
      sql += " and stocktakeid = -1 ";
      Statement s = connection.createStatement();
      s.executeUpdate(sql);
      s.close();

    } catch (SQLException e) {
      String mes = "Database Error: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public boolean isDesignsAllocated(int designID) {
    try {
      // check of die offcuts van die design in ander designs al gebruik is
      String sql = "select count(stockid) from stock where designID = " + designID;
      sql += " and exitType = " + StockItem.STOCK_DESIGN;
      sql += " and stocktakeid = -1 ";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int count = 0;
      if (rs.next()) {
        count = rs.getInt(1);
      }
      s.close();
      if (count == 0)
        return false;
      else
        return true;

    } catch (SQLException e) {
      String mes = "Database Error: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }


  public boolean canDeleteOffcuts(int designID) {
    try {
      // check of die offcuts van die design in ander designs al gebruik is
      String sql = "select count(stockid) from stock where lastdesignID = " + designID;
      sql += " and entryType = " + StockItem.STOCK_OFFCUT;
      sql += " and exitType > " + -1;
      sql += " and stocktakeid = -1 ";
      Statement s = connection.createStatement();
      ResultSet rs = s.executeQuery(sql);
      int count = 0;
      if (rs.next()) {
        count = rs.getInt(1);
      }
      s.close();
      if (count > 0)
        return false;
      else
        return true;

    } catch (SQLException e) {
      String mes = "Database Error: " + e.getMessage();
      JOptionPane.showMessageDialog(null, mes, "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

  public void saveShelf(long stockId,String shelf) {
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String sql = "update stock set shelf = ? where stockid = ?";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setString(1,shelf);
      ps.setLong(2,stockId);
      ps.executeUpdate();

      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {

    }
  }

  public String getShelf(long stockId) {
    String s = "";
    try {
      connection.setAutoCommit(false);
      connection.commit();

      String sql = "select shelf from stock where stockid = ?";
      PreparedStatement ps = connection.prepareStatement(sql);
      ps.setLong(1,stockId);
      ResultSet rs = ps.executeQuery();
      try {
        rs.next();
        s = rs.getString("shelf");
      } catch (NullPointerException e) {
        connection.commit();
        connection.setAutoCommit(true);
      }

      ps.close();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {

    }
    return s;
  }

  public void dropArtofdb() {
      try {
        connection.setAutoCommit(false);
        connection.commit();

        String sql = "drop database artofdb";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();

        ps.close();
        connection.commit();
        connection.setAutoCommit(true);
      } catch (SQLException e) {

      }
    }

    public void createArtofdb() {
      try {
        connection.setAutoCommit(false);
        connection.commit();
        String sql = "create database artofdb";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        connection.commit();
        connection.setAutoCommit(true);
      } catch (SQLException e) {

      }

    }

}
