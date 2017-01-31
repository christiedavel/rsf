package artof.utils;

import java.text.*;
import java.util.*;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class Utils {
  public static final short MND_JAN = 0;
  public static final short MND_FEB = 1;
  public static final short MND_MRT = 2;
  public static final short MND_APR = 3;
  public static final short MND_MEI = 4;
  public static final short MND_JUN = 5;
  public static final short MND_JUL = 6;
  public static final short MND_AUG = 7;
  public static final short MND_SEP = 8;
  public static final short MND_OKT = 9;
  public static final short MND_NOV = 10;
  public static final short MND_DES = 11;

  public static final String[] MND_STRINGS = { "January", "February", "March", "April", "May", "June",
                                               "July", "August", "September", "October", "November", "December" };

  public Utils() {
  }

  /*public static String[] getSuppliers() {
    String[] sups = { "MFI", "Supreme", "Fokkers" };
    return sups;
  }

  public static String[] getStatusses() {
    String[] s = { "Goed", "Available", "Kak", "Befok" };
    return s;
  }*/

  public static String getCurrencyFormat(double amt) {
    DecimalFormat formatter = (DecimalFormat)NumberFormat.getNumberInstance();
    formatter.applyLocalizedPattern("#0.00;-#0.00" );
    return formatter.format(amt);
  }

  public static String getNumberFormat(double amt) {
    DecimalFormat formatter = (DecimalFormat)NumberFormat.getNumberInstance();
    formatter.applyLocalizedPattern("#0;-#0" );
    return formatter.format(amt);
  }

  public static Float getFloatFromCurrency(String currency) throws ParseException {
    DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance();
    try {
      Double rate = (Double)nf.parse(currency, new ParsePosition(0));
      return new Float(rate.floatValue());
    } catch (ClassCastException e) {
      try {
        Long rate = (Long)nf.parse(currency, new ParsePosition(0));
        return new Float(rate.floatValue());
      } catch (ClassCastException e2) {
        return new Float(0.f);
      }
    }
  }

  public static Integer[] getIntegerArray(int cnt) {
    Integer[] amts = new Integer[100];
    for (int i = 0; i < 100; amts[i++] = new Integer(i));
    return amts;
  }

  public static String getDatumStr(int dat) {
    Calendar cal = new GregorianCalendar(Utils.getJaar(dat), Utils.getMaand(dat) - 1, Utils.getDag(dat));
    SimpleDateFormat formatter = new SimpleDateFormat ("dd MMM yyyy");
    return formatter.format(cal.getTime());
  }

  public static int getDatumInt(String dat) throws NullPointerException {
    if (dat == null) throw new NullPointerException();
    SimpleDateFormat formatter = new SimpleDateFormat ("dd MMM yyyy");
    ParsePosition pos = new ParsePosition(0);
    Calendar cal = new GregorianCalendar();
    cal.setTime(formatter.parse(dat, pos));
    return (10000*cal.get(Calendar.YEAR) + 100*(cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH));
  }

  public static int getCurrentDate() {
    Calendar cal = Calendar.getInstance();
    int d = cal.get(Calendar.DAY_OF_MONTH);
    int m = cal.get(Calendar.MONTH) + 1;
    int j = cal.get(Calendar.YEAR);
    return 10000*j + m*100 + d;
  }

  public static int getCurrentTime() {
    Calendar cal = Calendar.getInstance();
    int s = cal.get(Calendar.SECOND);
    int m = cal.get(Calendar.MINUTE);
    int h = cal.get(Calendar.HOUR);
    return 10000*h + m*100 + s;
  }

  public static int getMaand(String m) {
    for (int i = 0; i < MND_STRINGS.length; i++) {
      if (m.equals(MND_STRINGS[i]))
        return i + 1;
    }
    return 0;
  }

  public static int getDag(int dat) {
    if (dat < 19000101 || dat > 99999999)
      return 0;
    else
      return (dat - getJaar(dat)*10000 - getMaand(dat)*100);
  }

  public static int getMaand(int dat) {
    int t = (dat - (getJaar(dat)*10000)) / 100;

    if (dat < 19000101 || dat > 99999999)
      return 0;
    else
      return (dat - (getJaar(dat)*10000)) / 100;
  }

  public static int getJaar(int dat) {
    if (dat < 19000101 || dat > 99999999)
      return 0;
    else
      return dat / 10000;
  }

  public static int getEersteVervalDatum() {
    int curDate = getCurrentDate();
    return addDays(curDate, 5);
    /*int dag = getDag(curDate);
    int maand = getMaand(curDate);
    int jaar = getJaar(curDate);

    if (dag <= 15) {
      if (maand == 12) {
        maand = 1;
        jaar++;
      } else {
        maand++;
      }

    } else {
      if (maand == 12) {
        maand = 2;
        jaar++;
      } else if (maand == 11) {
        maand = 1;
        jaar++;
      } else {
        maand += 2;
      }
    }

    return jaar * 10000 + maand * 100 + 5;*/
  }

  public static int getPrevDate(int dat) {
    Calendar cal = new GregorianCalendar(getJaar(dat), getMaand(dat) - 1, getDag(dat));
    cal.roll(Calendar.DAY_OF_YEAR, false);
    int dag = cal.get(Calendar.DAY_OF_MONTH);
    int maand = cal.get(Calendar.MONTH) + 1;
    int jaar = cal.get(Calendar.YEAR);
    return 10000*jaar + 100*maand + dag;
  }

  public static int getNextDate(int dat) {
    Calendar cal = new GregorianCalendar(getJaar(dat), getMaand(dat) - 1, getDag(dat));
    cal.roll(Calendar.DAY_OF_YEAR, true);
    int dag = cal.get(Calendar.DAY_OF_MONTH);
    int maand = cal.get(Calendar.MONTH) + 1;
    int jaar = cal.get(Calendar.YEAR);
    return 10000*jaar + 100*maand + dag;
  }

  public static int addDays(int dat, int dae) {
    Calendar cal = new GregorianCalendar(getJaar(dat), getMaand(dat) - 1, getDag(dat));
    cal.add(Calendar.DAY_OF_MONTH, dae);
    int dag = cal.get(Calendar.DAY_OF_MONTH);
    int maand = cal.get(Calendar.MONTH) + 1;
    int jaar = cal.get(Calendar.YEAR);
    return 10000*jaar + 100*maand + dag;
  }

  public static String encrypt(String iets) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA");
      md.update(iets.getBytes("UTF-8"));
      byte[] raw = md.digest();
      String hash = (new BASE64Encoder()).encode(raw);
      return hash;

    } catch (Exception e) {
      return iets;
    }
  }
}