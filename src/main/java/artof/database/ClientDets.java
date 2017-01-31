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

public class ClientDets extends DBItem implements Externalizable {
  private int clientID = -1;
  protected String title;
  private String name;
  private String surname;
  private String idno;
  private String dob;
  private String associate;
  private String profile;
  private int postTo = 0;

  private String homeAdd1;
  private String homeAdd2;
  private String homeAdd3;
  private String homeAdd4;
  private String homeCode;
  private String homeCell;
  private String homeTel;
  private String homeFax;
  private String homeEmail;

  private String workAdd1;
  private String workAdd2;
  private String workAdd3;
  private String workAdd4;
  private String workCode;
  private String workCell;
  private String workTel;
  private String workFax;
  private String workEmail;
  private String celNo;

  public ClientDets() {
  }
  public ClientDets(int id) {
    clientID = id;
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(clientID);
    out.writeObject(title);
    out.writeObject(name);
    out.writeObject(surname);
    out.writeObject(idno);
    out.writeObject(dob);
    out.writeObject(associate);
    out.writeObject(profile);
    out.writeInt(postTo);

    out.writeObject(homeAdd1);
    out.writeObject(homeAdd2);
    out.writeObject(homeAdd3);
    out.writeObject(homeAdd4);
    out.writeObject(homeCode);
    out.writeObject(homeTel);
    out.writeObject(homeCell);
    out.writeObject(homeFax);
    out.writeObject(homeEmail);

    out.writeObject(workAdd1);
    out.writeObject(workAdd2);
    out.writeObject(workAdd3);
    out.writeObject(workAdd4);
    out.writeObject(workCode);
    out.writeObject(workCell);
    out.writeObject(workTel);
    out.writeObject(workFax);
    out.writeObject(workEmail);
    out.writeObject(celNo);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    clientID = in.readInt();
    title = (String)in.readObject();
    name = (String)in.readObject();
    surname = (String)in.readObject();
    idno = (String)in.readObject();
    dob = (String)in.readObject();
    associate = (String)in.readObject();
    profile = (String)in.readObject();
    postTo = in.readInt();

    homeAdd1 = (String)in.readObject();
    homeAdd2 = (String)in.readObject();
    homeAdd3 = (String)in.readObject();
    homeAdd4 = (String)in.readObject();
    homeCode = (String)in.readObject();
    homeTel = (String)in.readObject();
    homeCell = (String)in.readObject();
    homeFax = (String)in.readObject();
    homeEmail = (String)in.readObject();

    workAdd1 = (String)in.readObject();
    workAdd2 = (String)in.readObject();
    workAdd3 = (String)in.readObject();
    workAdd4 = (String)in.readObject();
    workCode = (String)in.readObject();
    workCell = (String)in.readObject();
    workTel = (String)in.readObject();
    workFax = (String)in.readObject();
    workEmail = (String)in.readObject();
    celNo = (String)in.readObject();
  }

  public boolean matchValue(String field, String value) {
    try {
      if (field.equalsIgnoreCase("ClientID") && value.equalsIgnoreCase(String.valueOf(clientID)))
        return true;
      else if (field.equalsIgnoreCase("Title") && value.equalsIgnoreCase(title))
        return true;
      else if (field.equalsIgnoreCase("Firstname") && value.equalsIgnoreCase(name))
        return true;
      else if (field.equalsIgnoreCase("Lastname") && value.equalsIgnoreCase(surname))
        return true;
      else if (field.equalsIgnoreCase("IDNo") && value.equalsIgnoreCase(idno))
        return true;
      else if (field.equalsIgnoreCase("DOB") && value.equalsIgnoreCase(dob))
        return true;
      else if (field.equalsIgnoreCase("Associate") && value.equalsIgnoreCase(associate))
        return true;
      else if (field.equalsIgnoreCase("Profile") && value.equalsIgnoreCase(profile))
        return true;
      else if (field.equalsIgnoreCase("PostTo") && value.equalsIgnoreCase(String.valueOf(postTo)))
        return true;

      else if (field.equalsIgnoreCase("HomeAdd1") && value.equalsIgnoreCase(homeAdd1))
        return true;
      else if (field.equalsIgnoreCase("HomeAdd2") && value.equalsIgnoreCase(homeAdd2))
        return true;
      else if (field.equalsIgnoreCase("HomeAdd3") && value.equalsIgnoreCase(homeAdd3))
        return true;
      else if (field.equalsIgnoreCase("HomeAdd4") && value.equalsIgnoreCase(homeAdd4))
        return true;
      else if (field.equalsIgnoreCase("HomeCode") && value.equalsIgnoreCase(homeCode))
        return true;
      else if (field.equalsIgnoreCase("HomeTel") && value.equalsIgnoreCase(homeTel))
        return true;
      else if (field.equalsIgnoreCase("HomeFax") && value.equalsIgnoreCase(homeFax))
        return true;
      else if (field.equalsIgnoreCase("HomeEmail") && value.equalsIgnoreCase(homeEmail))
        return true;

      else if (field.equalsIgnoreCase("WorkAdd1") && value.equalsIgnoreCase(workAdd1))
        return true;
      else if (field.equalsIgnoreCase("WorkAdd2") && value.equalsIgnoreCase(workAdd2))
        return true;
      else if (field.equalsIgnoreCase("WorkAdd3") && value.equalsIgnoreCase(workAdd3))
        return true;
      else if (field.equalsIgnoreCase("WorkAdd4") && value.equalsIgnoreCase(workAdd4))
        return true;
      else if (field.equalsIgnoreCase("WorkCode") && value.equalsIgnoreCase(workCode))
        return true;
      else if (field.equalsIgnoreCase("WorkTel") && value.equalsIgnoreCase(workTel))
        return true;
      else if (field.equalsIgnoreCase("WorkFax") && value.equalsIgnoreCase(workFax))
        return true;
      else if (field.equalsIgnoreCase("WorkEmail") && value.equalsIgnoreCase(workEmail))
        return true;

      else if (field.equalsIgnoreCase("CelNo") && value.equalsIgnoreCase(celNo))
        return true;
      else
        return false;
    } catch (NullPointerException e) {
      return false;
    }
  }

  public int getClientID() {
    return clientID;
  }
  public void setClientID(int id) {
    clientID = id;
  }

  public String getTitle() {
    if (title == null || title.equals("null"))
      return "";
    else
      return title;
  }
  public void setTitle(String t) {
    if ((title != null && !title.equals(t)) || (title == null && t != null)) {
      title = t;
      setModified(true);
    }
  }

  public String getName() {
    if (name == null || name.equals("null"))
      return "";
    else
      return name;
  }
  public void setName(String n) {
    if ((name != null && !name.equals(n)) || (name == null && n != null)) {
      name = n;
      setModified(true);
    }
  }

  public String getSurname() {
    if (surname == null || surname.equals("null"))
      return "";
    else
      return surname;
  }
  public void setSurname(String n) {
    if ((surname != null && !surname.equals(n)) || (surname == null && n != null)) {
      surname = n;
      setModified(true);
    }
  }

  public String getIDNo() {
    if (idno == null || idno.equals("null"))
      return "";
    else
      return idno;
  }
  public void setIDNo(String id) {
    if ((idno != null && !idno.equals(id)) || (idno == null && id != null)) {
      idno = id;
      setModified(true);
    }
  }

  public String getDOB() {
    if (dob == null || dob.equals("null"))
      return "";
    else
      return dob;
  }
  public void setDOB(String dat) {
    if ((dob != null && !dob.equals(dat)) || (dob == null && dat != null)) {
      dob = dat;
      setModified(true);
    }
  }

  public String getAssociate() {
    if (associate == null || associate.equals("null"))
      return "";
    else
      return associate;
  }
  public void setAssociate(String dat) {
    if ((associate != null && !associate.equals(dat)) || (associate == null && dat != null)) {
      associate = dat;
      setModified(true);
    }
  }

  public String getProfile() {
    if (profile == null || profile.equals("null"))
      return "";
    else
      return profile;
  }
  public void setProfile(String dat) {
    if ((profile != null && !profile.equals(dat)) || (profile == null && dat != null)) {
      profile = dat;
      setModified(true);
    }
  }

  public int getPostTo() {
    return postTo;
  }
  public void setPostTo(int nr) {
    if (postTo != nr) {
      postTo = nr;
      setModified(true);
    }
  }

  public String getHomeAdd1() {
    if (homeAdd1 == null || homeAdd1.equals("null"))
      return "";
    else
      return homeAdd1;
  }
  public void setHomeAdd1(String dat) {
    if ((homeAdd1 != null && !homeAdd1.equals(dat)) || (homeAdd1 == null && dat != null)) {
      homeAdd1 = dat;
      setModified(true);
    }
  }

  public String getHomeAdd2() {
    if (homeAdd2 == null || homeAdd2.equals("null"))
      return "";
    else
      return homeAdd2;
  }
  public void setHomeAdd2(String dat) {
    if ((homeAdd2 != null && !homeAdd2.equals(dat)) || (homeAdd2 == null && dat != null)) {
      homeAdd2 = dat;
      setModified(true);
    }
  }

  public String getHomeAdd3() {
    if (homeAdd3 == null || homeAdd1.equals("null"))
      return "";
    else
      return homeAdd3;
  }
  public void setHomeAdd3(String dat) {
    if ((homeAdd3 != null && !homeAdd3.equals(dat)) || (homeAdd3 == null && dat != null)) {
      homeAdd3 = dat;
      setModified(true);
    }
  }

  public String getHomeAdd4() {
    if (homeAdd4 == null || homeAdd4.equals("null"))
      return "";
    else
      return homeAdd4;
  }
  public void setHomeAdd4(String dat) {
    if ((homeAdd4 != null && !homeAdd4.equals(dat)) || (homeAdd4 == null && dat != null)) {
      homeAdd4 = dat;
      setModified(true);
    }
  }

  public String getHomeCode() {
    if (homeCode == null || homeCode.equals("null"))
      return "";
    else
      return homeCode;
  }
  public void setHomeCode(String dat) {
    if ((homeCode != null && !homeCode.equals(dat)) || (homeCode == null && dat != null)) {
      homeCode = dat;
      setModified(true);
    }
  }

  public String getHomeCell() {
    if (homeCell == null || homeCell.equals("null"))
      return "";
    else
      return homeCell;
  }
  public void setHomeCell(String dat) {
    if ((homeCell != null && !homeCell.equals(dat)) || (homeCell == null && dat != null)) {
      homeCell = dat;
      setModified(true);
    }
  }

  public String getHomeTel() {
    if (homeTel == null || homeTel.equals("null"))
      return "";
    else
      return homeTel;
  }
  public void setHomeTel(String dat) {
    if ((homeTel != null && !homeTel.equals(dat)) || (homeTel == null && dat != null)) {
      homeTel = dat;
      setModified(true);
    }
  }

  public String getHomeFax() {
    if (homeFax == null || homeFax.equals("null"))
      return "";
    else
      return homeFax;
  }
  public void setHomeFax(String dat) {
    if ((homeFax != null && !homeFax.equals(dat)) || (homeFax == null && dat != null)) {
      homeFax = dat;
      setModified(true);
    }
  }

  public String getHomeEmail() {
    if (homeEmail == null || homeEmail.equals("null"))
      return "";
    else
      return homeEmail;
  }
  public void setHomeEmail(String dat) {
    if ((homeEmail != null && !homeEmail.equals(dat)) || (homeEmail == null && dat != null)) {
      homeEmail = dat;
      setModified(true);
    }
  }

  public String getWorkAdd1() {
    if (workAdd1 == null || workAdd1.equals("null"))
      return "";
    else
      return workAdd1;
  }
  public void setWorkAdd1(String dat) {
    if ((workAdd1 != null && !workAdd1.equals(dat)) || (workAdd1 == null && dat != null)) {
      workAdd1 = dat;
      setModified(true);
    }
  }

  public String getWorkAdd2() {
    if (workAdd2 == null || workAdd2.equals("null"))
      return "";
    else
      return workAdd2;
  }
  public void setWorkAdd2(String dat) {
    if ((workAdd2 != null && !workAdd2.equals(dat)) || (workAdd2 == null && dat != null)) {
      workAdd2 = dat;
      setModified(true);
    }
  }

  public String getWorkAdd3() {
    if (workAdd3 == null || workAdd3.equals("null"))
      return "";
    else
      return workAdd3;
  }
  public void setWorkAdd3(String dat) {
    if ((workAdd3 != null && !workAdd3.equals(dat)) || (workAdd3 == null && dat != null)) {
      workAdd3 = dat;
      setModified(true);
    }
  }

  public String getWorkAdd4() {
    if (workAdd4 == null || workAdd4.equals("null"))
      return "";
    else
      return workAdd4;
  }
  public void setWorkAdd4(String dat) {
    if ((workAdd4 != null && !workAdd4.equals(dat)) || (workAdd4 == null && dat != null)) {
      workAdd4 = dat;
      setModified(true);
    }
  }

  public String getWorkCode() {
    if (workCode == null || workCode.equals("null"))
      return "";
    else
      return workCode;
  }
  public void setWorkCode(String dat) {
    if ((workCode != null && !workCode.equals(dat)) || (workCode == null && dat != null)) {
      workCode = dat;
      setModified(true);
    }
  }

  public String getWorkCell() {
    if (workCell == null || workCell.equals("null"))
      return "";
    else
      return workCell;
  }
  public void setWorkCell(String dat) {
    if ((workCell != null && !workCell.equals(dat)) || (workCell == null && dat != null)) {
      workCell = dat;
      setModified(true);
    }
  }

  public String getWorkTel() {
    if (workTel == null || workTel.equals("null"))
      return "";
    else
      return workTel;
  }
  public void setWorkTel(String dat) {
    if ((workTel != null && !workTel.equals(dat)) || (workTel == null && dat != null)) {
      workTel = dat;
      setModified(true);
    }
  }

  public String getWorkFax() {
    if (workFax == null || workFax.equals("null"))
      return "";
    else
      return workFax;
  }
  public void setWorkFax(String dat) {
    if ((workFax != null && !workFax.equals(dat)) || (workFax == null && dat != null)) {
      workFax = dat;
      setModified(true);
    }
  }

  public String getWorkEmail() {
    if (workEmail == null || workEmail.equals("null"))
      return "";
    else
      return workEmail;
  }
  public void setWorkEmail(String dat) {
    if ((workEmail != null && !workEmail.equals(dat)) || (workEmail == null && dat != null)) {
      workEmail = dat;
      setModified(true);
    }
  }

  public String getCelNo() {
    if (celNo == null || celNo.equals("null"))
      return "";
    else
      return celNo;
  }
  public void setCelNo(String dat) {
    if ((celNo != null && !celNo.equals(dat)) || (celNo == null && dat != null)) {
      celNo = dat;
      setModified(true);
    }
  }
}