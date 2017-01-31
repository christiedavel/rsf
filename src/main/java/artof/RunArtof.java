package artof;

import artof.ArtofMain;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class RunArtof {

  public static void main(String[] args) {
    ArtofMain artof = new ArtofMain();

    /*ArrayList sideList = new ArrayList();
    sideList.add(new Double(20));
    sideList.add(new Double(20));
    sideList.add(new Double(30));
    sideList.add(new Double(30));

    ArrayList stockList = new ArrayList();
    stockList.add(new Double(25));   // K
    stockList.add(new Double(27));   // K
    stockList.add(new Double(32));   // L
    stockList.add(new Double(35));   // L
    stockList.add(new Double(44));   // K + K
    stockList.add(new Double(53));   // K + L
    stockList.add(new Double(55));   // K + L
    stockList.add(new Double(61));   // L + L
    stockList.add(new Double(73));   // L + K + K
    stockList.add(new Double(77));   // L + K + K
    stockList.add(new Double(86));   // L + L + K
    stockList.add(new Double(88));   // L + L + K
    stockList.add(new Double(103));  // L + L + K + K

    ArrayList seqList = getAllSequences(stockList, sideList);

    Iterator it = seqList.iterator();
    while (it.hasNext()) {
      ArrayList list = (ArrayList)it.next();
      Iterator it2 = list.iterator();
      while (it2.hasNext()) {
        System.out.print(((Double)it2.next()).intValue() + ", ");
      }
      System.out.println();
    }*/

    /*ArrayList sideList = new ArrayList();
    sideList.add(new Double(20));
    sideList.add(new Double(30));
    sideList.add(new Double(40));
    sideList.add(new Double(50));

    ArrayList seqList = getAllSequences(sideList);

    Iterator it = seqList.iterator();
    while (it.hasNext()) {
      ArrayList list = (ArrayList)it.next();
      Iterator it2 = list.iterator();
      while (it2.hasNext()) {
        System.out.print(((Double)it2.next()).intValue() + ", ");
      }
      System.out.println();
    }*/
  }

  private static ArrayList getAllSequences(ArrayList elements) {
    ArrayList allSequences = new ArrayList();

    if (elements.size() == 1) {
      allSequences.add(elements);
      return allSequences;
    }

    for (int i = 0; i < elements.size(); i++) {
      Double item = (Double)elements.get(i);
      ArrayList clone = (ArrayList)elements.clone();
      clone.remove(i);

      ArrayList subs = getAllSequences(clone);
      for (int j = 0; j < subs.size(); j++) {
        ArrayList seqList = (ArrayList) subs.get(j);
        seqList.add(0, item);
        allSequences.add(seqList);
      }
    }
    return allSequences;
  }

  private static ArrayList getAllSequences(ArrayList elements, ArrayList sideLengths) {
    ArrayList allSequences = new ArrayList();

    if (elements.size() == 0) {
      return allSequences;
    }

    for (int i = 0; i < elements.size(); i++) {
      Double firstOne = (Double)elements.get(i);
      ArrayList clone = (ArrayList)elements.clone();
      clone.remove(i);

      ArrayList remainingSides = (ArrayList)sideLengths.clone();
      int stockLengthAvailable = (int)firstOne.intValue();
      boolean used = false;
      while (remainingSides.size() > 0) {
        int sideLength = ((Double)remainingSides.get(0)).intValue();
        if (stockLengthAvailable > sideLength) {
          stockLengthAvailable -= sideLength;
          remainingSides.remove(0);
          used = true;
        } else {
          break;
        }
      }

      if (remainingSides.size() > 0) {
        ArrayList subs = getAllSequences(clone, remainingSides);
        for (int j = 0; j < subs.size(); j++) {
          ArrayList seqList = (ArrayList)subs.get(j);
          seqList.add(0, firstOne);
          allSequences.add(seqList);
        }
      } else {
        if (used) {
          ArrayList seqList = new ArrayList();
          seqList.add(firstOne);
          allSequences.add(seqList);
        }
      }
    }
    return allSequences;
  }
}




