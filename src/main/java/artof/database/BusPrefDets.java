package artof.database;
import artof.utils.*;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class BusPrefDets extends DBItem implements Externalizable {
  private int prefID = -1;
  private int prefDate = UserSettings.MAX_DATE;
  private int dateCount = 1;
  private float VATPerc = 14f;
  private String VATReg = "Yes";
  private String VATCode;
  private String VATOwnItems = "Yes";
  private float markupBoards = 3.5f;
  private float markupFrames = 3.2f;
  private float markupGBs = 3.f;
  private float markupDecs = 3.f;
  private static float muDisc1 = 0f;
  private static float muDisc2 = 5f;
  private static float muDisc3 = 10f;
  private static float muDisc4 = 15f;
  private static float muDisc5 = 20f;
  private static float muDisc6 = 25f;
  private static int muDiscSelected = 0;
  private float stretchMinUCM = 25.f;
  private float stretchOtherUCM = 180.f;
  private float stretchMinPrice = 12.f;
  private float stretchOtherPrice = 80.f;
  private float pastMinUCM = 25.f;
  private float pastOtherUCM = 180.f;
  private float pastMinPrice = 5.f;
  private float pastOtherPrice = 40.f;
  private float sundriesDisc = 0.f;
  private float sundriesBasic = 15.f;
  private float sundriesLabour = 0.f;

  public BusPrefDets() {
  }
  public BusPrefDets(int id) {
    prefID = id;
  }

  public boolean matchValue(String field, String value) {
    return false;
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(prefID);
    out.writeInt(prefDate);
    out.writeInt(dateCount);

    out.writeFloat(VATPerc);
    out.writeObject(VATReg);
    out.writeObject(VATCode);
    out.writeObject(VATOwnItems);

    out.writeFloat(markupBoards);
    out.writeFloat(markupFrames);
    out.writeFloat(markupGBs);
    out.writeFloat(markupDecs);

    out.writeFloat(stretchMinUCM);
    out.writeFloat(stretchOtherUCM);
    out.writeFloat(stretchMinPrice);
    out.writeFloat(stretchOtherPrice);

    out.writeFloat(pastMinUCM);
    out.writeFloat(pastOtherUCM);
    out.writeFloat(pastMinPrice);
    out.writeFloat(pastOtherPrice);

    out.writeFloat(sundriesDisc);
    out.writeFloat(sundriesBasic);
    out.writeFloat(sundriesLabour);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    prefID = in.readInt();
    prefDate = in.readInt();
    dateCount = in.readInt();

    VATPerc = in.readFloat();
    VATReg = (String)in.readObject();
    VATCode = (String)in.readObject();
    VATOwnItems = (String)in.readObject();

    markupBoards = in.readFloat();
    markupFrames = in.readFloat();
    markupGBs = in.readFloat();
    markupDecs = in.readFloat();

    stretchMinUCM = in.readFloat();
    stretchOtherUCM = in.readFloat();
    stretchMinPrice = in.readFloat();
    stretchOtherPrice = in.readFloat();

    pastMinUCM = in.readFloat();
    pastOtherUCM = in.readFloat();
    pastMinPrice = in.readFloat();
    pastOtherPrice = in.readFloat();

    sundriesDisc = in.readFloat();
    sundriesBasic = in.readFloat();
    sundriesLabour = in.readFloat();
  }

  /*------------------------------- Berekeninge --------------------------------*/

  public float getTotalBoardMarkup() {
    if (VATReg.equals("Yes"))
      return (1 + VATPerc / 100) * markupBoards;
    else
      return markupBoards;
  }
  public float getTotalFrameMarkup() {
    if (VATReg.equals("Yes"))
      return (1 + VATPerc / 100) * markupFrames;
    else
      return markupBoards;
  }
  public float getTotalGBMarkup() {
    if (VATReg.equals("Yes"))
      return (1 + VATPerc / 100) * markupGBs;
    else
      return markupBoards;
  }
  public float getTotalDecMarkup() {
    if (VATReg.equals("Yes"))
      return (1 + VATPerc / 100) * markupDecs;
    else
      return markupBoards;
  }

  public boolean isVATReg() {
    if (VATReg.equals("Yes"))
      return true;
    else
      return false;
  }

  public static float getMarkupDiscount() {
    if (muDiscSelected == 0)
      return 1 + muDisc1 / 100;
    else if (muDiscSelected == 1)
      return 1 + muDisc2 / 100;
    else if (muDiscSelected == 2)
      return 1 + muDisc3 / 100;
    else if (muDiscSelected == 3)
      return 1 + muDisc4 / 100;
    else if (muDiscSelected == 4)
      return 1 + muDisc5 / 100;
    else if (muDiscSelected == 5)
      return 1 + muDisc6 / 100;
    else
      return 1;
  }

  public float getStretchCharge(float width, float height) {
    float xk = width + height;
    float m = 1;
    
    if (Math.abs(stretchOtherPrice - stretchMinPrice) < 0.001) {
      m = 0;
      
    } else if (Math.abs(stretchOtherUCM - stretchMinUCM) > 0.001) {
      m = (stretchOtherPrice - stretchMinPrice) / (stretchOtherUCM - stretchMinUCM);
    }
    
    return stretchMinPrice + m * (xk - stretchMinUCM);
  }

  public float getPastCharge(float width, float height) {
    float xk = width + height;
    float m = 1;
    
    if (Math.abs(pastOtherPrice - pastMinPrice) < 0.001) {
      m = 0;
      
    } else if (Math.abs(pastOtherUCM - pastMinUCM) > 0.001) {
      m = (pastOtherPrice - pastMinPrice) / (pastOtherUCM - pastMinUCM);
    }    

    return pastMinPrice + m * (xk - pastMinUCM);
  }

  /*---------------------------- Getters en Setters ----------------------------*/

  public int getPrefID() {
    return prefID;
  }
  public void setPrefID(int id) {
    prefID = id;
  }

  public int getPrefDate() {
    return prefDate;
  }
  public void setPrefDate(int d) {
    if (prefDate != d) {
      prefDate = d;
      setModified(true);
    }
  }

  public int getDateCount() {
    return dateCount;
  }
  public void setDateCount(int d) {
    if (dateCount != d) {
      dateCount = d;
      setModified(true);
    }
  }

  public float getVATPerc() {
    return VATPerc;
  }
  public void setVATPerc(float perc) {
    if (VATPerc != perc) {
      VATPerc = perc;
      setModified(true);
    }
  }

  public String getVATReg() {
    if (VATReg == null || VATReg.equals("null"))
      return "";
    else
      return VATReg;
  }
  public void setVATReg(String reg) {
    if ((VATReg != null && !VATReg.equals(reg)) || (VATReg == null && reg != null)) {
      VATReg = reg;
      setModified(true);
    }
  }

  public String getVATCode() {
    if (VATCode == null || VATCode.equals("null"))
      return "";
    else
      return VATCode;
  }
  public void setVATCode(String code) {
    if ((VATCode != null && !VATCode.equals(code)) || (VATCode == null && code != null)) {
      VATCode = code;
      setModified(true);
    }
  }

  public String getVATOwnItems() {
    if (VATOwnItems == null || VATOwnItems.equals("null"))
      return "";
    else
      return VATOwnItems;
  }
  public void setVATOwnItems(String vat) {
    if ((VATOwnItems != null && !VATOwnItems.equals(vat)) || (VATOwnItems == null && vat != null)) {
      VATOwnItems = vat;
      setModified(true);
    }
  }

  public float getMarkupBoards() {
    return markupBoards;
  }
  public void setMarkupBoards(float mu) {
    if (markupBoards != mu) {
      markupBoards = mu;
      setModified(true);
    }
  }

  public float getMarkupFrames() {
    return markupFrames;
  }
  public void setMarkupFrames(float mu) {
    if (markupFrames != mu) {
      markupFrames = mu;
      setModified(true);
    }
  }

  public float getMarkupGBs() {
    return markupGBs;
  }
  public void setMarkupGBs(float mu) {
    if (markupGBs != mu) {
      markupGBs = mu;
      setModified(true);
    }
  }

  public float getMarkupDecs() {
    return markupDecs;
  }
  public void setMarkupDecs(float mu) {
    if (markupDecs != mu) {
      markupDecs = mu;
      setModified(true);
    }
  }

  public float getMUDisc1() {
    return muDisc1;
  }
  public void setMUDisc1(float disc) {
    if (muDisc1 != disc) {
      muDisc1 = disc;
      setModified(true);
    }
  }

  public float getMUDisc2() {
    return muDisc2;
  }
  public void setMUDisc2(float disc) {
    if (muDisc2 != disc) {
      muDisc2 = disc;
      setModified(true);
    }
  }

  public float getMUDisc3() {
    return muDisc3;
  }
  public void setMUDisc3(float disc) {
    if (muDisc3 != disc) {
      muDisc3 = disc;
      setModified(true);
    }
  }

  public float getMUDisc4() {
    return muDisc4;
  }
  public void setMUDisc4(float disc) {
    if (muDisc4 != disc) {
      muDisc4 = disc;
      setModified(true);
    }
  }

  public float getMUDisc5() {
    return muDisc5;
  }
  public void setMUDisc5(float disc) {
    if (muDisc5 != disc) {
      muDisc5 = disc;
      setModified(true);
    }
  }

  public float getMUDisc6() {
    return muDisc6;
  }
  public void setMUDisc6(float disc) {
    if (muDisc6 != disc) {
      muDisc6 = disc;
      setModified(true);
    }
  }

  public static int getMUDiscSelected() {
    return muDiscSelected;
  }
  public static void setMUDiscSelected(int sel) {
    muDiscSelected = sel;
  }

  public float getStretchMinUCM() {
    return stretchMinUCM;
  }
  public void setStretchMinUCM(float min) {
    if (stretchMinUCM != min) {
      stretchMinUCM = min;
      setModified(true);
    }
  }

  public float getStretchOtherUCM() {
    return stretchOtherUCM;
  }
  public void setStretchOtherUCM(float ucm) {
    if (stretchOtherUCM != ucm) {
      stretchOtherUCM = ucm;
      setModified(true);
    }
  }

  public float getStretchMinPrice() {
    return stretchMinPrice;
  }
  public void setStretchMinPrice(float p) {
    if (stretchMinPrice != p) {
      stretchMinPrice = p;
      setModified(true);
    }
  }

  public float getStretchOtherPrice() {
    return stretchOtherPrice;
  }
  public void setStretchOtherPrice(float p) {
    if (stretchOtherPrice != p) {
      stretchOtherPrice = p;
      setModified(true);
    }
  }

  public float getPastMinUCM() {
    return pastMinUCM;
  }
  public void setpastMinUCM(float ucm) {
    if (pastMinUCM != ucm) {
      pastMinUCM = ucm;
      setModified(true);
    }
  }

  public float getPastOtherUCM() {
    return pastOtherUCM;
  }
  public void setPastOtherUCM(float ucm) {
    if (pastOtherUCM != ucm) {
      pastOtherUCM = ucm;
      setModified(true);
    }
  }

  public float getPastMinPrice() {
    return pastMinPrice;
  }
  public void setPastMinPrice(float p) {
    if (pastMinPrice != p) {
      pastMinPrice = p;
      setModified(true);
    }
  }

  public float getPastOtherPrice() {
    return pastOtherPrice;
  }
  public void setPastOtherPrice(float p) {
    if (pastOtherPrice != p) {
      pastOtherPrice = p;
      setModified(true);
    }
  }

  public float getSundriesDisc() {
    return sundriesDisc;
  }
  public void setSundriesDisc(float disc) {
    if (sundriesDisc != disc) {
      sundriesDisc = disc;
      setModified(true);
    }
  }

  public float getSundriesBasic() {
    return sundriesBasic;
  }
  public void setSundriesBasic(float b) {
    if (sundriesBasic != b) {
      sundriesBasic = b;
      setModified(true);
    }
  }

  public float getsundriesLabour() {
    return sundriesLabour;
  }
  public void setSundriesLabour(float l) {
    if (sundriesLabour != l) {
      sundriesLabour = l;
      setModified(true);
    }
  }
}