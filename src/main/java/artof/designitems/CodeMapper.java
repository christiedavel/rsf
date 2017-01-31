package artof.designitems;

import artof.database.ArtofDB;
import artof.materials.MaterialDets;
import artof.utils.SearchComboBox;
import java.util.*;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CodeMapper {
  private SearchComboBox cbxItemCode = new SearchComboBox();
  private SearchComboBox cbxOwnCode = new SearchComboBox();
  private HashMap itemOwnMap = new HashMap();
  private HashMap ownItemMap = new HashMap();
  private HashMap idItemMap = new HashMap();
  private HashMap itemIdMap = new HashMap();
  private HashMap itemRebateMap = new HashMap();

  public CodeMapper(int materialType) {
    try {
      ArtofDB db = ArtofDB.getCurrentDB();
      ArrayList itemList = db.getMaterials(materialType, null);
      Iterator it = itemList.iterator();
      while (it.hasNext()) {
        MaterialDets item = (MaterialDets)it.next();
        itemOwnMap.put(item.getItemCode(), item.getOwnCode());
        ownItemMap.put(item.getOwnCode(), item.getItemCode());
        cbxItemCode.addItem(item.getItemCode());
        idItemMap.put(new Integer(item.getMaterialID()), item.getItemCode());
        itemIdMap.put(item.getItemCode(), new Integer(item.getMaterialID()));
        try {
          itemRebateMap.put(item.getItemCode(),
                          new Float(item.getDefaultValuesWithInMaterialDets().getRebate()));
        } catch (NullPointerException e) {
          //doen niks
        }
      }

      Object[] ownCodes = itemOwnMap.values().toArray();
      Arrays.sort(ownCodes);
      for (int i = 0; i < ownCodes.length; i++) {
        cbxOwnCode.addItem((String)ownCodes[i]);
      }

    } catch (NullPointerException e) {
      //e.printStackTrace();
    }
  }

  public static void addToCodeMapper(MaterialDets material) {
    try {
      CodeMapper mapper = getMapper(material);
      mapper.addCodes(material);
    } catch (NullPointerException e) {
      // fok dit
    }
  }

  public static void updateCodeMapper(MaterialDets material) {
    try {
      CodeMapper mapper = getMapper(material);
      //String oldOwnCode = (String)mapper.getOwnCodeFromItemCode(material.getItemCode());
      //mapper.updateOwnCode(oldOwnCode, material.getOwnCode());
      mapper.updateCodes(material);
    } catch (NullPointerException e) {
      // fok dit
    }
  }

  public static void deleteFromCodeMapper(int materialID, String itemCode) {
    try {
      DesignBoard.boardMapper.removeCodes(materialID, itemCode);
      DesignFrame.frameMapper.removeCodes(materialID, itemCode);
      DesignFoil.foilMapper.removeCodes(materialID, itemCode);
      DesignGlassAndBack.gbMapper.removeCodes(materialID, itemCode);
      DesignSlip.slipMapper.removeCodes(materialID, itemCode);

    } catch (NullPointerException e) {
      // fok dit
    }
  }

  private static CodeMapper getMapper(MaterialDets material) {
    CodeMapper mapper = null;
    if (material.getMaterialType() == MaterialDets.MAT_BOARD) {
      mapper = DesignBoard.boardMapper;
    } else if (material.getMaterialType() == MaterialDets.MAT_FRAME) {
      mapper = DesignFrame.frameMapper;
    } else if (material.getMaterialType() == MaterialDets.MAT_FOIL) {
      mapper = DesignFoil.foilMapper;
    } else if (material.getMaterialType() == MaterialDets.MAT_GB) {
      mapper = DesignGlassAndBack.gbMapper;
    } else if (material.getMaterialType() == MaterialDets.MAT_SLIP) {
      mapper = DesignSlip.slipMapper;
    }

    return mapper;
  }

  public String getItemCodeFromOwnCode(String ownCode) {
    return (String)ownItemMap.get(ownCode);
  }

  public Integer getIdFromItemCode(String itemCode) {
      return (Integer)itemIdMap.get(itemCode);
  }

  public String getOwnCodeFromItemCode(String itemCode) {
    return (String)itemOwnMap.get(itemCode);
  }

  public float getRebateFromItemCode(String itemCode) {
      return ((Float)itemRebateMap.get(itemCode)).floatValue();
  }


  public JComboBox getItemCodeComboBox() {
    ActionListener[] listeners = cbxItemCode.getActionListeners();
    for (int i = 0; i < listeners.length; i++) {
      cbxItemCode.removeActionListener(listeners[i]);
    }
    return cbxItemCode;
  }

  public JComboBox getOwnCodeComboBox() {
    ActionListener[] listeners = cbxOwnCode.getActionListeners();
    for (int i = 0; i < listeners.length; i++) {
      cbxOwnCode.removeActionListener(listeners[i]);
    }
    return cbxOwnCode;
  }

  public void removeCodes(int materialID, String itemCode) {
    Integer id = new Integer(materialID);
    String ownCode = (String)itemOwnMap.get(itemCode);

    idItemMap.remove(id);
    itemIdMap.remove(itemCode);
    itemOwnMap.remove(itemCode);
    ownItemMap.remove(ownCode);

    getItemCodeComboBox().removeItem(itemCode);
    getOwnCodeComboBox().removeItem(ownCode);

    itemRebateMap.remove(itemCode);

  }

 /* public void updateItemCode(String oldCode, String newCode) {
    String ownCode = (String)itemOwnMap.get(oldCode);
    itemOwnMap.put(newCode, ownCode);
    itemOwnMap.remove(oldCode);

    ownItemMap.put(ownCode, newCode);

    cbxItemCode.removeItem(oldCode);
    int index = 0;
    Iterator it = itemOwnMap.keySet().iterator();
    while (it.hasNext()) {
      String code = (String)it.next();
      if (code.compareTo(newCode) > 0) {
        break;
      }
      index++;
    }
    cbxItemCode.insertItemAt(newCode, index);
  }*/

  /*public void updateOwnCode(String oldCode, String newCode) {
    String itemCode = (String)ownItemMap.get(oldCode);
    ownItemMap.put(newCode, itemCode);
    ownItemMap.remove(oldCode);

    itemOwnMap.put(itemCode, newCode);

    cbxOwnCode.removeItem(oldCode);
    int index = 0;
    Iterator it = ownItemMap.keySet().iterator();
    while (it.hasNext()) {
      String code = (String)it.next();
      if (code.compareTo(newCode) > 0) {
        break;
      }
      index++;
    }
    try {
      cbxOwnCode.insertItemAt(newCode, cbxOwnCode.getItemCount() - index);
    } catch (ArrayIndexOutOfBoundsException e) {
      cbxOwnCode.insertItemAt(newCode, 0);
    }
  }*/

  public void updateCodes(MaterialDets material) {
    Integer id = new Integer(material.getMaterialID());
    String itemCode = (String)idItemMap.get(id);
    String ownCode = (String)itemOwnMap.get(itemCode);

    idItemMap.remove(id);
    itemIdMap.remove(itemCode);
    itemOwnMap.remove(itemCode);
    ownItemMap.remove(ownCode);

    getItemCodeComboBox().removeItem(itemCode);
    getOwnCodeComboBox().removeItem(ownCode);

    itemRebateMap.remove(itemCode);

    addCodes(material);
  }


  public void addCodes(MaterialDets material) {
    Integer id = new Integer(material.getMaterialID());
    String itemCode = material.getItemCode();
    String ownCode = material.getOwnCode();

    if (itemCode == null || ownCode == null || itemCode.equals("") || ownCode.equals(""))
      return;

    itemOwnMap.put(itemCode, ownCode);
    ownItemMap.put(ownCode, itemCode);
    idItemMap.put(id, itemCode);
    itemIdMap.put(itemCode, id);
    try {
      itemRebateMap.put(itemCode,
                        new Float(material.getDefaultValuesWithInMaterialDets().getRebate()));
    } catch (NullPointerException ee) {
      //doen niks
    }

    boolean added = false;
    getItemCodeComboBox();  //clear all action listeners
    for (int i = 0; i < cbxItemCode.getItemCount(); i++) {
      String code = (String)cbxItemCode.getItemAt(i);
      if (code.compareTo(itemCode) > 0) {
        cbxItemCode.insertItemAt(itemCode, i);
        added = true;
        break;
      }
    }
    if (!added)
      cbxItemCode.addItem(itemCode);

    added = false;
    getOwnCodeComboBox();  //clear all the action listeners
    for (int i = 0; i < cbxOwnCode.getItemCount(); i++) {
      String code = (String)cbxOwnCode.getItemAt(i);
      if (code.compareTo(ownCode) > 0) {
        cbxOwnCode.insertItemAt(ownCode, i);
        added = true;
        break;
      }
    }
    if (!added)
      cbxOwnCode.addItem(ownCode);
  }

  public void updateItemId(String itemCode,Integer id) {
    itemIdMap.put(itemCode,id);
  }

  public void updateItemRebate(String itemCode,Float rebate) {
    itemRebateMap.put(itemCode,rebate);
  }

}





