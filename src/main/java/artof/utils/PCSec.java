package artof.utils;

import java.util.Date;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;

import artof.utils.Utils;

public class PCSec {
  private static PCSec instance = null;

  public static PCSec getInstance() {
    if (instance == null)
      instance = new PCSec();

    return instance;
  }

  private PCSec() {
  }

  public synchronized boolean validate(boolean stuffers, boolean settings, int dbCount, String regNo) {
    if (stuffers && settings && dbCount > 0) {
      try {
        FileInfo fileInfo = readFile();
        if (Utils.encrypt(regNo).equals(fileInfo.getRegNo())) {
          long curDate = new Date().getTime();
          long creationDate = fileInfo.getCreationDate().getTime();
          if (!fileInfo.isRegistered() && (creationDate + 5 * 1000 * 60 * 60 * 24 < curDate)) {
            return false;
        	} else {
        	  return true;
        	}

        } else {
          return false;
        }

      } catch (Exception e) {
        return false;
      }

    } else if (!stuffers && !settings && dbCount == 0) {
      try {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setRegNo(Utils.encrypt(regNo));
        fileInfo.setRegistered(false);
        fileInfo.setCreationDate(new Date());
        writeFile(fileInfo);
        return true;

      } catch (Exception e) {
        return false;
      }

    } else {
      return false;
    }
  }

  public synchronized void register() throws FileNotFoundException, IOException, NullPointerException {
    FileInfo fileInfo = readFile();
    if (!fileInfo.isRegistered()) {
      fileInfo.setRegistered(true);
      writeFile(fileInfo);
    }
  }

  private synchronized void writeFile(FileInfo fileInfo) throws IOException {
    String s = fileInfo.getRegNo();
    s += Utils.encrypt("installdir");
    if (fileInfo.isRegistered()) {
      s += Utils.encrypt("winnt_system");
    } else {
      s += Utils.encrypt("winnt_temp");
    }
    s += Utils.encrypt("multi_user");
    s += String.valueOf(fileInfo.getCreationDate().getTime());

    String path = System.getProperty("java.home") + System.getProperty("file.separator") + "LICENSE_apxz_QW.rtf";
    FileWriter w = new FileWriter(path);
    w.write(s, 0, s.length());
    w.flush();
    w.close();
  }

  private synchronized FileInfo readFile() throws FileNotFoundException, IOException, NullPointerException {
    String path = System.getProperty("java.home") + System.getProperty("file.separator") + "LICENSE_apxz_QW.rtf";
    BufferedReader in = new BufferedReader(new FileReader(path));
    String data = in.readLine();
    String installDir = Utils.encrypt("installdir");
    String multiUser = Utils.encrypt("multi_user");
    int offset1 = data.indexOf(installDir);
    int offset2 = data.indexOf(multiUser);

    String regNo = data.substring(0, offset1);
    String registered = data.substring(offset1 + installDir.length(), offset2);
    String dateCreated = data.substring(offset2 + multiUser.length(), data.length());

    FileInfo fileInfo = new FileInfo();
    fileInfo.setRegNo(regNo);
    if (registered.equals(Utils.encrypt("winnt_system"))) {
      fileInfo.setRegistered(true);
    } else {
      fileInfo.setRegistered(false);
    }
    fileInfo.setCreationDate(new Date(Long.parseLong(dateCreated)));

    return fileInfo;
  }

  private class FileInfo {
    private String regNo;
    private boolean registered;
    private Date creationDate;

    public Date getCreationDate() {
      return creationDate;
    }

    public void setCreationDate(Date creationDate) {
      this.creationDate = creationDate;
    }

    public boolean isRegistered() {
      return registered;
    }

    public void setRegistered(boolean registered) {
      this.registered = registered;
    }

    public String getRegNo() {
      return regNo;
    }

    public void setRegNo(String regNo) {
      this.regNo = regNo;
    }
  }
}
