package artof;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class RunRSF {

  public static void main(String[] args) {
    //createDB();
    latloop();
  }

  private static void latloop() {
    ArtofMain artof = new ArtofMain();
  }

  /*private static void createDB() {
    Connection connection = null;
    try {
      String url = "jdbc:hsqldb:rsfdb/data/rsfdb";
      Class.forName("org.hsqldb.jdbcDriver");
      connection = DriverManager.getConnection(url, "sa", "");

      Statement s = connection.createStatement();
      String sql = "CREATE TABLE artists (" +
          "ARTIST_ID integer NOT NULL, " +
          "TITLE varchar(40), " +
          "NAME varchar(40), " +
          "SURNAME varchar(40), " +
          "DOB varchar(40), " +
          "PROFILE varchar(40), " +
          "ADD1 varchar(40), " +
          "ADD2 varchar(40), " +
          "ADD3 varchar(40), " +
          "ADD4 varchar(40), " +
          "POSTALCODE varchar(30), " +
          "TEL varchar(30), " +
          "FAX varchar(30), " +
          "CEL varchar(30), " +
          "EMAIL varchar(50), " +
          "WORKS varchar(2048), " +
          "TRAINING varchar(2048), " +
          "EXHIBITIONS varchar(2048), " +
          "AWARDS varchar(2048), " +
          "SUMMARY varchar(2048), " +
          "ORIGIN varchar(40), " +
          "PRIMARY KEY  (ARTIST_ID) " +
          ")";
      s.execute(sql);

      sql = "CREATE TABLE buspreferences (" +
          "BUSPREV_ID int(11) NOT NULL, " +
          "PREVDATE int(11) default NULL, " +
          "VATPERC float default NULL, " +
          "VATREGISTERED varchar(10) default NULL, " +
          "VATCODE varchar(40) default NULL, " +
          "VATOWNITEMS varchar(10) default NULL, " +
          "MARKUPBOARDS float default NULL, " +
          "MARKUPFRAMES float default NULL, " +
          "MARKUPGBS float default NULL, " +
          "MARKUPDECS float default NULL, " +
          "MUDISCOUNT1 float default NULL, " +
          "MUDISCOUNT2 float default NULL, " +
          "MUDISCOUNT3 float default NULL, " +
          "MUDISCOUNT4 float default NULL, " +
          "MUDISCOUNT5 float default NULL, " +
          "MUDISCOUNT6 float default NULL, " +
          "MUDISCOUNTSELECTED int(11) default NULL, " +
          "STRETCHMINUCM float default NULL, " +
          "STRETCHOTHERUCM float default NULL, " +
          "STRETCHMINPRICE float default NULL, " +
          "STRETCHOTHERPRICE float default NULL, " +
          "PASTMINUCM float default NULL, " +
          "PASTOTHERUCM float default NULL, " +
          "PASTMINPRICE float default NULL, " +
          "PASTOTHERPRICE float default NULL, " +
          "SUNDRIESDISCOUNT float default NULL, " +
          "SUNDRIESBASIC float default NULL, " +
          "SUNDRIESLABOUR float default NULL, " +
          "DATECOUNT int(11) default NULL, " +
          "PRIMARY KEY  (BUSPREV_ID) " +
          ");";
      s.execute(sql);

      sql = "CREATE TABLE clients (" +
          "CLIENT_ID int(11) NOT NULL, " +
          "TITLE varchar(15) default NULL, " +
          "NAME varchar(40) default NULL, " +
          "SURNAME varchar(40) default NULL, " +
          "IDNO varchar(30) default NULL, " +
          "DOB varchar(30) default NULL, " +
          "PROFILE varchar(40) default NULL, " +
          "POSTTO int(11) default NULL, " +
          "ASSOCIATE varchar(240) default NULL, " +
          "HOMEADD1 varchar(40) default NULL, " +
          "HOMEADD2 varchar(40) default NULL, " +
          "HOMEADD3 varchar(40) default NULL, " +
          "HOMEADD4 varchar(40) default NULL, " +
          "HOMECODE varchar(40) default NULL, " +
          "HOMETEL varchar(40) default NULL, " +
          "HOMEFAX varchar(40) default NULL, " +
          "HOMEEMAIL varchar(50) default NULL, " +
          "WORKADD1 varchar(40) default NULL, " +
          "WORKADD2 varchar(40) default NULL, " +
          "WORKADD3 varchar(40) default NULL, " +
          "WORKADD4 varchar(40) default NULL, " +
          "WORKCODE varchar(40) default NULL, " +
          "WORKTEL varchar(40) default NULL, " +
          "WORKFAX varchar(40) default NULL, " +
          "WORKEMAIL varchar(50) default NULL, " +
          "CELNO varchar(30) default NULL, " +
          "WORKCELLNO varchar(30) default NULL, " +
          "PRIMARY KEY  (CLIENT_ID) " +
          ");";
      s.execute(sql);

      sql = "CREATE TABLE designs (" +
          "DESIGN_ID int(11) NOT NULL, " +
          "TITLE varchar(50) default NULL, " +
          "ARTIST_ID int(11) default NULL, " +
          "CLIENT_ID int(11) default NULL, " +
          "BUSPREFS object, " +
          "METHODPREFS object, " +
          "ITEMLIST object, " +
          "DESDATE int(11) default NULL, " +
          "STATUS varchar(40) default NULL, " +
          "MATERIALADJ float default NULL, " +
          "LABOURADJ float default NULL, " +
          "USEWEIGHTING int(11) default NULL, " +
          "USESTRETCHING int(11) default NULL, " +
          "USEPASTING int(11) default NULL, " +
          "DELIVERYDATE int(11) default NULL, " +
          "PRICEONE float default NULL, " +
          "DISCOUNTONE float default NULL, " +
          "NOORDERED int(11) default NULL, " +
          "PRICEOTHER float default NULL, " +
          "DISCOUNTOTHER float default NULL, " +
          "PRIMARY KEY  (DESIGN_ID) " +
          ");";
      s.execute(sql);

      sql = "CREATE TABLE suppliers (" +
          "SUPPLIER varchar(40) NOT NULL, " +
          "PERSON varchar(40), " +
          "TEL varchar(40), " +
          "FAX varchar(40), " +
          "CEL varchar(40), " +
          "AD1 varchar(40), " +
          "AD2 varchar(40), " +
          "AD3 varchar(40), " +
          "CODE varchar(40), " +
          "EMAIL varchar(80), " +
          "PRIMARY KEY (SUPPLIER) " +
          ")";
      s.execute(sql);

      sql = "CREATE TABLE materials (" +
          "MaterialID int(11) NOT NULL identity, " +
          "ITEMCODE varchar(40) NOT NULL, " +
          "MATERIALTYPE int(11) NOT NULL, " +
          "OWNCODE varchar(40) NOT NULL, " +
          "MATERIAL_GROUP varchar(40) default NULL, " +
          "DESCRIPTION varchar(150) default NULL, " +
          "COLORRED int(11) default NULL, " +
          "COLORGREEN int(11) default NULL, " +
          "COLORBLUE int(11) default NULL, " +
          "COLORALPHA int(11) default NULL, " +
          "UNIQUE (MATERIALTYPE,OWNCODE), " +
          "UNIQUE (ITEMCODE) " +
          ");";
      s.execute(sql);

      sql = "CREATE TABLE materialdetails (" +
          "MaterialID int(11) NOT NULL, " +
          "SUPPLIER varchar(40) NOT NULL, " +
          "COST float default NULL, " +
          "COMPFACTOR float default NULL, " +
          "EXQFACT float default NULL, " +
          "MATWIDTH float default NULL, " +
          "MATLENGTH float default NULL, " +
          "THICKNESS float default NULL, " +
          "REBATE float default NULL, " +
          "PRICEARRAY object, " +
          "STATUS varchar(40) default NULL, " +
          "PRIMARY KEY  (MaterialID,SUPPLIER), " +
          "FOREIGN KEY (MaterialID) REFERENCES materials (MaterialID) ON DELETE CASCADE, " +
          "FOREIGN KEY (SUPPLIER) REFERENCES suppliers (SUPPLIER) ON DELETE CASCADE " +
          ");";
      s.execute(sql);

      sql = "CREATE TABLE methodpreferences (" +
          "METHODPREF_ID int(11) NOT NULL, " +
          "PREFDATE int(11) default NULL, " +
          "DATECOUNT int(11) default NULL, " +
          "METHODTYPE int(11) default NULL, " +
          "OVERLAPADJFACT float default NULL, " +
          "FBOVERLAPWITHSLIP float default NULL, " +
          "FBOVERLAPNOSLIP float default NULL, " +
          "MINIMUMSLIP float default NULL, " +
          "RESSELECTED int(11) default NULL, " +
          "FGIFVALUE float default NULL, " +
          "FGTHENHEIGHT float default NULL, " +
          "FGTHENWIDTH float default NULL, " +
          "FGELSEHEIGHT float default NULL, " +
          "FGELSEWIDTH float default NULL, " +
          "SUNFIRSTMEASURE varchar(30) default NULL, " +
          "SUNNOSPECS int(11) default NULL, " +
          "SUNBACKDEFAULT varchar(40) default NULL, " +
          "SUNDAYS int(11) default NULL, " +
          "RESBOARDLENGTH float default NULL, " +
          "RESBOARDWIDTH float default NULL, " +
          "RESBACKLENGTH float default NULL, " +
          "RESBACKWIDTH float default NULL, " +
          "RESGLASSLENGTH float default NULL, " +
          "RESGLASSWIDTH float default NULL, " +
          "RESDECLENGTH float default NULL, " +
          "RESDECWIDTH float default NULL, " +
          "ISFULLORDER int(11) default '0', " +
          "PRIMARY KEY  (METHODPREF_ID) " +
          ");";
      s.execute(sql);

      sql = "CREATE TABLE stock (" +
          "STOCKID int(11) NOT NULL, " +
          "ITEMCODE varchar(40) default NULL, " +
          "SUPPLIER varchar(40) default NULL, " +
          "STOCKDATE int(11) default NULL, " +
          "STOCKLENGTH float default NULL, " +
          "STOCKHEIGHT float default NULL, " +
          "COSTPERUNIT float default NULL, " +
          "DESIGNID int(11) default NULL, " +
          "orderid varchar(20) default NULL, " +
          "matType int(11) default NULL, " +
          "EntryType int(11) default NULL, " +
          "ExitType int(11) default NULL, " +
          "stocktakeid int(11) default NULL, " +
          "itemLengthsAllocated object, " +
          "lastDesignID int(11) default NULL, " +
          "shelf varchar(40) default NULL, " +
          "PRIMARY KEY  (STOCKID) " +
          ");";
      s.execute(sql);

      sql = "CREATE TABLE stuffers (" +
          "stuffID int(11) NOT NULL identity, " +
          "stuffdatum int(11) default NULL, " +
          "checker int(11) default NULL, " +
          "pccode int(11) default NULL, " +
          "regno varchar(15) default NULL " +
          ");";
      s.execute(sql);

      s.close();

    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      try {
        connection.close();
      } catch (Exception e2) {
        //los
      }
    }
  }*/
}




