package artof.designitems.dialogs.boards;
import artof.designitems.DesignItem2;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface DesignPanelFunctions {
  public void setValues() throws NumberFormatException;
  public void updateValues(DesignItem2 newItem);
  public void showOffsets(boolean show);
}