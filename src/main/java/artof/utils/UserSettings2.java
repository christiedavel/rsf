package artof.utils;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class UserSettings2 {
  // Material image stuff
  public static boolean DES_USE_IMAGES = true;
  public static String MATERIAL_IMAGE_PATH = "";

  // Importing van materials
  public static boolean matImage = true;

  // File paths
  public static String LAST_ARTWORK_PATH = "";
  public static String LAST_EXPORT_PATH = "";


  // DB Stuff
  public static boolean USE_DATABASE_SERVER = false;

  //LetterHead
  public static String LAST_LETTERHEAD = "";
  public static boolean USE_LETTERHEAD = false;

  // Stoor en lees na file
  public static void saveUserSettings() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("rsfdb/Settings2.dat")));

      out.writeBoolean(DES_USE_IMAGES);
      out.writeObject(MATERIAL_IMAGE_PATH);
      out.writeBoolean(matImage);
      out.writeObject(LAST_ARTWORK_PATH);
      out.writeObject(LAST_EXPORT_PATH);
      out.writeBoolean(USE_DATABASE_SERVER);
      out.writeObject(LAST_LETTERHEAD);
      out.writeBoolean(USE_LETTERHEAD);
      out.close();

    } catch(IOException e) {
      System.err.println("Error saving User Settings.");
    }
  }

  public static void readUserSettings() {
    try {
      ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("rsfdb/Settings2.dat")));

      DES_USE_IMAGES = in.readBoolean();
      MATERIAL_IMAGE_PATH = (String)in.readObject();
      matImage = in.readBoolean();
      LAST_ARTWORK_PATH = (String)in.readObject();
      LAST_EXPORT_PATH = (String)in.readObject();
      USE_DATABASE_SERVER = in.readBoolean();
      LAST_LETTERHEAD = (String)in.readObject();
      USE_LETTERHEAD = in.readBoolean();  
      in.close();

    } catch (Exception e) {
      System.err.println("Error reading User Settings.");
    }
  }
}