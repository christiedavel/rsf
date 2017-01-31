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

public class ArtistDets extends DBItem implements Externalizable {
  private int artistID = -1;
  private String title;
  private String name;
  private String surname;
  private String dob;
  private String origin;
  private String profile;

  private String add1;
  private String add2;
  private String add3;
  private String add4;
  private String postalCode;
  private String tel;
  private String fax;
  private String email;
  private String cel;

  private String works;
  private String training;
  private String exhibitions;
  private String awards;
  private String summary;

  public ArtistDets() {
  }
  public ArtistDets(int id) {
    artistID = id;
  }

  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(artistID);
    out.writeObject(title);
    out.writeObject(name);
    out.writeObject(surname);
    out.writeObject(dob);
    out.writeObject(origin);
    out.writeObject(profile);

    out.writeObject(add1);
    out.writeObject(add2);
    out.writeObject(add3);
    out.writeObject(add4);
    out.writeObject(postalCode);
    out.writeObject(tel);
    out.writeObject(fax);
    out.writeObject(email);
    out.writeObject(cel);

    out.writeObject(works);
    out.writeObject(training);
    out.writeObject(exhibitions);
    out.writeObject(awards);
    out.writeObject(summary);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    artistID = in.readInt();
    title = (String)in.readObject();
    name = (String)in.readObject();
    surname = (String)in.readObject();
    dob = (String)in.readObject();
    origin = (String)in.readObject();
    profile = (String)in.readObject();

    add1 = (String)in.readObject();
    add2 = (String)in.readObject();
    add3 = (String)in.readObject();
    add4 = (String)in.readObject();
    postalCode = (String)in.readObject();
    tel = (String)in.readObject();
    fax = (String)in.readObject();
    email = (String)in.readObject();
    cel = (String)in.readObject();

    works = (String)in.readObject();
    training = (String)in.readObject();
    exhibitions = (String)in.readObject();
    awards = (String)in.readObject();
    summary = (String)in.readObject();
  }

  public boolean matchValue(String field, String value) {
    try {
      if (field.equalsIgnoreCase("ArtistID") && value.equalsIgnoreCase(String.valueOf(artistID)))
        return true;
      else if (field.equalsIgnoreCase("Title") && value.equalsIgnoreCase(title))
        return true;
      else if (field.equalsIgnoreCase("Name") && value.equalsIgnoreCase(name))
        return true;
      else if (field.equalsIgnoreCase("Surname") && value.equalsIgnoreCase(surname))
        return true;
      else if (field.equalsIgnoreCase("DOB") && value.equalsIgnoreCase(dob))
        return true;
      else if (field.equalsIgnoreCase("Profile") && value.equalsIgnoreCase(profile))
        return true;
      else if (field.equalsIgnoreCase("Origin") && value.equalsIgnoreCase(origin))
        return true;

      else if (field.equalsIgnoreCase("Add1") && value.equalsIgnoreCase(add1))
        return true;
      else if (field.equalsIgnoreCase("Add2") && value.equalsIgnoreCase(add2))
        return true;
      else if (field.equalsIgnoreCase("Add3") && value.equalsIgnoreCase(add3))
        return true;
      else if (field.equalsIgnoreCase("Add4") && value.equalsIgnoreCase(add4))
        return true;
      else if (field.equalsIgnoreCase("PostalCode") && value.equalsIgnoreCase(postalCode))
        return true;
      else if (field.equalsIgnoreCase("Tel") && value.equalsIgnoreCase(tel))
        return true;
      else if (field.equalsIgnoreCase("Fax") && value.equalsIgnoreCase(fax))
        return true;
      else if (field.equalsIgnoreCase("Email") && value.equalsIgnoreCase(email))
        return true;
      else if (field.equalsIgnoreCase("Cel") && value.equalsIgnoreCase(cel))
        return true;
      else
        return false;
    } catch (NullPointerException e) {
      return false;
    }
  }

  public int getArtistID() {
    return artistID;
  }
  public void setArtistID(int id) {
    artistID = id;
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

  public String getOrigin() {
    if (origin == null || origin.equals("null"))
      return "";
    else
      return origin;
  }
  public void setOrigin(String dat) {
    if ((origin != null && !origin.equals(dat)) || (origin == null && dat != null)) {
      origin = dat;
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

  public String getAdd1() {
    if (add1 == null || add1.equals("null"))
      return "";
    else
      return add1;
  }
  public void setAdd1(String dat) {
    if ((add1 != null && !add1.equals(dat)) || (add1 == null && dat != null)) {
      add1 = dat;
      setModified(true);
    }
  }

  public String getAdd2() {
    if (add2 == null || add2.equals("null"))
      return "";
    else
      return add2;
  }
  public void setAdd2(String dat) {
    if ((add2 != null && !add2.equals(dat)) || (add2 == null && dat != null)) {
      add2 = dat;
      setModified(true);
    }
  }

  public String getAdd3() {
    if (add3 == null || add1.equals("null"))
      return "";
    else
      return add3;
  }
  public void setAdd3(String dat) {
    if ((add3 != null && !add3.equals(dat)) || (add3 == null && dat != null)) {
      add3 = dat;
      setModified(true);
    }
  }

  public String getAdd4() {
    if (add4 == null || add4.equals("null"))
      return "";
    else
      return add4;
  }
  public void setAdd4(String dat) {
    if ((add4 != null && !add4.equals(dat)) || (add4 == null && dat != null)) {
      add4 = dat;
      setModified(true);
    }
  }

  public String getPostalCode() {
    if (postalCode == null || postalCode.equals("null"))
      return "";
    else
      return postalCode;
  }
  public void setPostalCode(String dat) {
    if ((postalCode != null && !postalCode.equals(dat)) || (postalCode == null && dat != null)) {
      postalCode = dat;
      setModified(true);
    }
  }

  public String getTel() {
    if (tel == null || tel.equals("null"))
      return "";
    else
      return tel;
  }
  public void setTel(String dat) {
    if ((tel != null && !tel.equals(dat)) || (tel == null && dat != null)) {
      tel = dat;
      setModified(true);
    }
  }

  public String getFax() {
    if (fax == null || fax.equals("null"))
      return "";
    else
      return fax;
  }
  public void setFax(String dat) {
    if ((fax != null && !fax.equals(dat)) || (fax == null && dat != null)) {
      fax = dat;
      setModified(true);
    }
  }

  public String getEmail() {
    if (email == null || email.equals("null"))
      return "";
    else
      return email;
  }
  public void setEmail(String dat) {
    if ((email != null && !email.equals(dat)) || (email == null && dat != null)) {
      email = dat;
      setModified(true);
    }
  }

  public String getCel() {
    if (cel == null || cel.equals("null"))
      return "";
    else
      return cel;
  }
  public void setCel(String dat) {
    if ((cel != null && !cel.equals(dat)) || (cel == null && dat != null)) {
      cel = dat;
      setModified(true);
    }
  }

  public String getWorks() {
    if (works == null || works.equals("null"))
      return "";
    else
      return works;
  }
  public void setWorks(String dat) {
    if ((works != null && !works.equals(dat)) || (works == null && dat != null)) {
      works = dat;
      setModified(true);
    }
  }

  public String getTraining() {
    if (training == null || training.equals("null"))
      return "";
    else
      return training;
  }
  public void setTraining(String dat) {
    if ((training != null && !training.equals(dat)) || (training == null && dat != null)) {
      training = dat;
      setModified(true);
    }
  }

  public String getExhibitions() {
    if (exhibitions == null || exhibitions.equals("null"))
      return "";
    else
      return exhibitions;
  }
  public void setExhibitions(String dat) {
    if ((exhibitions != null && !exhibitions.equals(dat)) || (exhibitions == null && dat != null)) {
      exhibitions = dat;
      setModified(true);
    }
  }

  public String getAwards() {
    if (awards == null || awards.equals("null"))
      return "";
    else
      return awards;
  }
  public void setAwards(String dat) {
    if ((awards != null && !cel.equals(dat)) || (awards == null && dat != null)) {
      awards = dat;
      setModified(true);
    }
  }

  public String getSummary() {
    if (summary == null || summary.equals("null"))
      return "";
    else
      return summary;
  }
  public void setSummary(String dat) {
    if ((summary != null && !summary.equals(dat)) || (summary == null && dat != null)) {
      summary = dat;
      setModified(true);
    }
  }
}