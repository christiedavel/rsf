package artof.materials;
import java.util.HashMap;
import artof.database.ArtofDB;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TypeMapper {
  private static TypeMapper dude;
  private static HashMap itemTypeMap = null;

  private TypeMapper() {
    try {
      if (itemTypeMap == null) {
        ArtofDB db = ArtofDB.getCurrentDB();
        itemTypeMap = db.getMaterialItemTypeMap();
      }
    } catch (NullPointerException e) {
      // db fokkop
    }
  }

  public static TypeMapper getTypeMapper() {
    if (dude == null)
      dude = new TypeMapper();

    return dude;
  }

  public static void buildTypeMapper() {
    if (dude == null)
      dude = new TypeMapper();
  }

  public static void rebuildTypeMapper() {
    dude = new TypeMapper();
  }


  public int getType(String itemCode) {
    try {
      return ((Integer)itemTypeMap.get(itemCode)).intValue();
    } catch (NullPointerException e) {
      return -1;
    }
  }

  public void addType(String itemCode, int type) {
    itemTypeMap.put(itemCode, new Integer(type));
  }

  public void deleteType(String itemCode) {
    itemTypeMap.remove(itemCode);
  }
}