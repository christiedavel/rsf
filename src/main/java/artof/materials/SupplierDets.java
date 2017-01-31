package artof.materials;
import artof.database.*;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class SupplierDets extends DBItem implements Serializable {
  public static int SUPPLIER_PERSON = 0;
  public static int SUPPLIER_TEL = 1;
  public static int SUPPLIER_FAX = 2;
  public static int SUPPLIER_CEL = 3;
  public static int SUPPLIER_AD1 = 4;
  public static int SUPPLIER_AD2 = 5;
  public static int SUPPLIER_AD3 = 6;
  public static int SUPPLIER_CODE = 7;
  public static int SUPPLIER_EMAIL = 8;

  private String name;
  private String[] data = new String[9];

  public SupplierDets() {
  }

  public boolean matchValue(String field, String value) {
    try {
      if (field.equalsIgnoreCase("Supplier") && value.equalsIgnoreCase(String.valueOf(name)))
        return true;
      else
        return false;
    } catch (NullPointerException e) {
      return false;
    }
  }

  public String getName() {
    if (name == null || name.equals("null"))
      return "";
    else
      return name;
  }
  public void setName(String t) {
    if ((name != null && !name.equals(t)) || (name == null && t != null)) {
      name = t;
      setModified(true);
    }
  }

  public String getDataValue(int index) {
    try {
      String value = data[index];
      if (value == null || value.equals("null"))
        return "";
      else
        return value;
    } catch (ArrayIndexOutOfBoundsException e) {
      return "";
    }
  }
  public void setDataValue(int index, String value) {
    try {
      if ((data[index] != null && !data[index].equals(value)) || (data[index] == null && value != null)) {
        data[index] = value;
        setModified(true);
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      // doen niks
    }
  }

  public void setDataArray(String[] values) {
    if (values != null && values.length == 9) {
      data = values;
      setModified(true);
    }
  }
  public String[] getDataArray() {
    return data;
  }

  public static String[] getDefaultSupplierList() {
    ArrayList supList = ArtofDB.getCurrentDB().getSupplierNames();
    String[] suppliers = new String[supList.size()];
    for (int i = 0; i < supList.size(); i++) {
      suppliers[i] = (String)supList.get(i);
    }
    return suppliers;
  }
}