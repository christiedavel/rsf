package artof.database;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public abstract class DBItem implements  Externalizable {
  protected boolean modified = false;

  public DBItem() {
  }

  public void writeExternal(ObjectOutput out) throws IOException {
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
  }

  public boolean isModified() {
    return modified;
  }
  public void setModified(boolean mod) {
    modified = mod;
  }

  public abstract boolean matchValue(String field, String value);
}
