package artof.utils;
import java.awt.*;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class UserSettings {
  public static String BOARD_SORTER = null;
  public static String FRAME_SORTER = null;
  public static String GLASS_AND_BACK_SORTER = null;
  public static String DECORATION_SORTER = null;
  public static String CLIENT_SORTER = null;
  public static String ARTIST_SORTER = null;
  public static String MATERIAL_SORTER = null;
	//Banking
	public static String ACCOUNT_OWNER = "";
	public static String ACCOUNT_NUMBER = "";
	public static String BANK_NAME = "";
	public static String BANK_CODE = "";
	public static int INVOICE_NUMBER = 1;

  public static final int MAX_DATE = 99991231;

  //***************************************************************************

  // Design settings

  public static boolean DES_DRAW_BORDER = true;
  public static boolean DES_FILL_COLORS = false;
  public static boolean DES_SHOW_GAPS = true;

  // Teken modes
  public static int DES_COLOR_MODE = 2;
  public static final int DES_COLOR_MODE_NONE = 0;
  public static final int DES_COLOR_MODE_DEF = 1;
  public static final int DES_COLOR_MODE_OWN = 2;

  // Kleure waarmee items geteken word
  public static Color DEF_COLOR = Color.black;
  public static Color DEF_ARTWORK_COLOR = Color.red;
  public static Color DEF_BOARD_COLOR = Color.blue;
  public static Color DEF_FRAME_COLOR = Color.yellow;
  public static Color DEF_FOIL_COLOR = Color.pink;
  public static Color DEF_SLIP_COLOR = Color.green;

  // Selected Item settings
  public static Color SELECTED_COLOR = Color.black;
  public static int SELECTED_GAP = 20;

  // Ander kak
  public static String registrationNo = "845732610057";
//  public static int pcCode = 123456;
  public static int expiryDate = 20030201;

  public static boolean DUMP_DESIGN_CALCS = true;
  public static String DATABASE_URL = "localhost";

  public static String MATERIAL_SERVER = "www.rs-f.com";
  public static int MATERIAL_SERVER_PORT = 80;

  // Owner Details
  public static String ownerName;
  public static String ownerSurname;
  public static String ownerEmail;
  public static String ownerCompany;
  public static String ownerTel;
  public static String ownerCel;
  public static String ownerFax;
  public static String ownerAdd1;
  public static String ownerAdd2;
  public static String ownerAdd3;
  public static String ownerCode;

  // Stock Optimization
  public static boolean OPT_USE = true;
  //public static boolean OPT_SHOW_DIALOG = true;
  public static boolean OPT_PRINT = true;
  public static boolean OPT_WARNINGS = true;
  public static boolean OPT_STOCKLIST = true;
  //public static boolean OPT_OFFCUTS = true;

  // Importing van materials
  public static boolean matOwnCode = true;
  public static boolean matStatus = true;
  public static boolean matDescription = true;
  public static boolean matMatGroup = true;
  public static boolean matColour = false;
  public static boolean matCost = true;
  public static boolean matWidth = true;
  public static boolean matLength = true;
  public static boolean matThickness = true;
  public static boolean matRebate = false;


  // Stoor en lees na file
  public static void saveUserSettings() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("rsfdb/Settings.dat")));

      out.writeObject(BOARD_SORTER);
      out.writeObject(FRAME_SORTER);
      out.writeObject(GLASS_AND_BACK_SORTER);
      out.writeObject(DECORATION_SORTER);
      out.writeObject(CLIENT_SORTER);
      out.writeObject(ARTIST_SORTER);
      out.writeObject(MATERIAL_SORTER);

      out.writeBoolean(DES_DRAW_BORDER);
      out.writeBoolean(DES_FILL_COLORS);
      out.writeBoolean(DES_SHOW_GAPS);

      out.writeInt(DES_COLOR_MODE);

      out.writeObject(DEF_COLOR);
      out.writeObject(DEF_ARTWORK_COLOR);
      out.writeObject(DEF_BOARD_COLOR);
      out.writeObject(DEF_FRAME_COLOR);
      out.writeObject(DEF_FOIL_COLOR);
      out.writeObject(DEF_SLIP_COLOR);

      out.writeObject(SELECTED_COLOR);
      out.writeInt(SELECTED_GAP);

      out.writeBoolean(DUMP_DESIGN_CALCS);
      out.writeObject(DATABASE_URL);

      out.writeObject(MATERIAL_SERVER);
      out.writeInt(MATERIAL_SERVER_PORT);

      out.writeObject(ownerName);
      out.writeObject(ownerSurname);
      out.writeObject(ownerEmail);
      out.writeObject(ownerCompany);
      out.writeObject(ownerTel);
      out.writeObject(ownerCel);
      out.writeObject(ownerFax);
      out.writeObject(ownerAdd1);
      out.writeObject(ownerAdd2);
      out.writeObject(ownerAdd3);
      out.writeObject(ownerCode);

      out.writeObject(registrationNo);
      out.writeInt(expiryDate);

      out.writeBoolean(OPT_USE);
      //out.writeBoolean(OPT_SHOW_DIALOG);
      out.writeBoolean(OPT_PRINT);
      out.writeBoolean(OPT_WARNINGS);
      //out.writeBoolean(OPT_OFFCUTS);

      out.writeBoolean(matOwnCode);
      out.writeBoolean(matStatus);
      out.writeBoolean(matDescription);
      out.writeBoolean(matMatGroup);
      out.writeBoolean(matColour);
      out.writeBoolean(matCost);
      out.writeBoolean(matWidth);
      out.writeBoolean(matLength);
      out.writeBoolean(matThickness);
      out.writeBoolean(matRebate);
			//banking
			out.writeObject(ACCOUNT_OWNER);
			out.writeObject(ACCOUNT_NUMBER);
			out.writeObject(BANK_NAME);
			out.writeObject(BANK_CODE);
			out.writeInt(INVOICE_NUMBER);

      try {
        out.writeBoolean(OPT_STOCKLIST);
      } catch (Exception e) {

      }

      out.close();

    } catch(IOException e) {
      System.err.println("Error saving User Settings.");
    }
  }

  public static boolean readUserSettings() {
    try {
      ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("rsfdb/Settings.dat")));

      BOARD_SORTER = (String)in.readObject();
      FRAME_SORTER = (String)in.readObject();
      GLASS_AND_BACK_SORTER = (String)in.readObject();
      DECORATION_SORTER = (String)in.readObject();
      CLIENT_SORTER = (String)in.readObject();
      ARTIST_SORTER = (String)in.readObject();
      MATERIAL_SORTER = (String)in.readObject();

      DES_DRAW_BORDER = in.readBoolean();
      DES_FILL_COLORS = in.readBoolean();
      DES_SHOW_GAPS = in.readBoolean();

      DES_COLOR_MODE = in.readInt();

      DEF_COLOR = (Color)in.readObject();
      DEF_ARTWORK_COLOR = (Color)in.readObject();
      DEF_BOARD_COLOR = (Color)in.readObject();
      DEF_FRAME_COLOR = (Color)in.readObject();
      DEF_FOIL_COLOR = (Color)in.readObject();
      DEF_SLIP_COLOR = (Color)in.readObject();

      SELECTED_COLOR = (Color)in.readObject();
      SELECTED_GAP = in.readInt();

      DUMP_DESIGN_CALCS = in.readBoolean();
      DATABASE_URL = (String)in.readObject();

      MATERIAL_SERVER = (String)in.readObject();
      MATERIAL_SERVER_PORT = in.readInt();

      ownerName = (String)in.readObject();
      ownerSurname = (String)in.readObject();
      ownerEmail = (String)in.readObject();
      ownerCompany = (String)in.readObject();
      ownerTel = (String)in.readObject();
      ownerCel = (String)in.readObject();
      ownerFax = (String)in.readObject();
      ownerAdd1 = (String)in.readObject();
      ownerAdd2 = (String)in.readObject();
      ownerAdd3 = (String)in.readObject();
      ownerCode = (String)in.readObject();

      registrationNo = (String)in.readObject();
      expiryDate = in.readInt();

      OPT_USE = in.readBoolean();
      //OPT_SHOW_DIALOG = in.readBoolean();
      OPT_PRINT = in.readBoolean();
      OPT_WARNINGS = in.readBoolean();
      //OPT_OFFCUTS = in.readBoolean();

      matOwnCode = in.readBoolean();
      matStatus = in.readBoolean();
      matDescription = in.readBoolean();
      matMatGroup = in.readBoolean();
      matColour = in.readBoolean();
      matCost = in.readBoolean();
      matWidth = in.readBoolean();
      matLength = in.readBoolean();
      matThickness = in.readBoolean();
      matRebate = in.readBoolean();
			//banking
			ACCOUNT_OWNER = (String) in.readObject();
			ACCOUNT_NUMBER = (String) in.readObject();
			BANK_NAME = (String) in.readObject();
			BANK_CODE = (String) in.readObject();
			INVOICE_NUMBER = in.readInt();
      try {
        OPT_STOCKLIST = in.readBoolean();
      }
      catch (Exception ex) {

      }

      in.close();
      return true;

    } catch (Exception e) {
      return false;
    }
  }
}
