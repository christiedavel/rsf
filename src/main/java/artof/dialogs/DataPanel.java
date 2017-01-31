package artof.dialogs;
import artof.database.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class DataPanel extends JPanel {
  private DataFunctions dataFunctions;
  private ButCreator butCreator;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton btnAdd = new JButton(new ImageIcon("images/OpenDesign.gif"));
  private JButton btnSave = new JButton(new ImageIcon("images/SaveDesign.gif"));
  private JButton btnCancel = new JButton(new ImageIcon("images/Cancel.gif"));
  private JButton btnDelete = new JButton(new ImageIcon("images/CloseDesign.gif"));
  private JButton btnSort = new JButton(new ImageIcon("images/Sort.gif"));
  private JButton btnFind = new JButton(new ImageIcon("images/Find.gif"));
  private JButton btnPrint = new JButton(new ImageIcon("images/Print.gif"));
  private JButton btnClose = new JButton(new ImageIcon("images/Close.gif"));
  private Border border1;

  public DataPanel(DataFunctions dataFunctions, ButCreator butCreator) {
    this.butCreator = butCreator;
    this.dataFunctions = dataFunctions;

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(103, 101, 98),new Color(148, 145, 140));
    btnAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnAdd_actionPerformed(e);
      }
    });
    this.setLayout(gridBagLayout1);
    btnSave.setToolTipText("Save current item");
    btnSave.setMargin(new Insets(0, 0, 0, 0));
    btnSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSave_actionPerformed(e);
      }
    });
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnCancel_actionPerformed(e);
      }
    });
    btnDelete.setToolTipText("Delete current item");
    btnDelete.setMargin(new Insets(0, 0, 0, 0));
    btnDelete.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDelete_actionPerformed(e);
      }
    });
    btnSort.setToolTipText("Sort items");
    btnSort.setMargin(new Insets(0, 0, 0, 0));
    btnSort.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSort_actionPerformed(e);
      }
    });
    btnFind.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFind_actionPerformed(e);
      }
    });
    btnPrint.setToolTipText("Print items");
    btnPrint.setMargin(new Insets(0, 0, 0, 0));
    btnPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrint_actionPerformed(e);
      }
    });
    btnClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnClose_actionPerformed(e);
      }
    });
    btnAdd.setToolTipText("Add new item");
    btnAdd.setMargin(new Insets(0, 0, 0, 0));
    btnCancel.setToolTipText("Cancel changes");
    btnCancel.setMargin(new Insets(0, 0, 0, 0));
    btnFind.setToolTipText("Find item");
    btnFind.setMargin(new Insets(0, 0, 0, 0));
    btnClose.setToolTipText("Close");
    btnClose.setMargin(new Insets(0, 0, 0, 0));
    this.setBorder(border1);
    this.add(btnAdd,        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnSave,       new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnCancel,      new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnDelete,      new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnSort,     new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    this.add(btnFind,    new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnPrint,    new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
    this.add(btnClose,   new GridBagConstraints(7, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 20, 5, 5), 0, 0));
  }

  void btnAdd_actionPerformed(ActionEvent e) {
    if (!dataFunctions.setData(butCreator.getCurrentObject()))
      return;

    Object obj = dataFunctions.getNewItem();
    butCreator.getIterator().add(obj);
    butCreator.setCurrentObject(butCreator.getIterator().previous());
    dataFunctions.updateData(butCreator.getCurrentObject());
  }

  void btnSave_actionPerformed(ActionEvent e) {
    if (dataFunctions.setData(butCreator.getCurrentObject()))
      dataFunctions.saveMainList();
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    dataFunctions.refreshMainList(null);
    ListIterator mainIt = dataFunctions.getMainIterator();
    if (mainIt.hasNext()) {
      butCreator.setCurrentObject(mainIt.next());
      dataFunctions.updateData(butCreator.getCurrentObject());
    } else {
      dataFunctions.updateData(null);
    }
    butCreator.setIterator(mainIt);
  }

  void btnDelete_actionPerformed(ActionEvent e) {
    if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete rhe current item?", "Delete",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
      try {
        butCreator.getIterator().remove();
        dataFunctions.deleteItem(butCreator.getCurrentObject());

        if (butCreator.getIterator().hasPrevious())
          butCreator.setCurrentObject(butCreator.getIterator().previous());
        else if (butCreator.getIterator().hasNext())
          butCreator.setCurrentObject(butCreator.getIterator().next());
        else
          butCreator.setCurrentObject(null);

        dataFunctions.updateData(butCreator.getCurrentObject());

      } catch (Exception x) {
        // ignore
      }
    }
  }

  void btnSort_actionPerformed(ActionEvent e) {
    if (!dataFunctions.setData(butCreator.getCurrentObject()))
      return;

    TreeSet toStrings;
    StringSelector2 sel = new StringSelector2(dataFunctions.getSortList(), "Select sort fields");
    toStrings = sel.getSelectedFields();

    Hashtable table = dataFunctions.getSortDBMap();
    if (toStrings.size() > 0) {
      Iterator it = toStrings.iterator();
      String sql = " order by " + (String)table.get(it.next());
      while (it.hasNext()) {
        sql += ", ";
        sql += (String)table.get(it.next());
      }
      dataFunctions.refreshMainList(sql);
      ListIterator mainIt = dataFunctions.getMainIterator();
      Object obj = null;
      if (mainIt.hasNext())
        obj = mainIt.next();

      butCreator.setCurrentObject(obj);
      butCreator.setIterator(mainIt);
      dataFunctions.updateData(obj);
      ((BrowsePanel)butCreator.getBrowsePanel()).refreshButtons();
    }
  }

  void btnFind_actionPerformed(ActionEvent e) {
    if (!dataFunctions.setData(butCreator.getCurrentObject()))
      return;

    String[] fieldval = new String[2];
    FieldFinder finder = new FieldFinder(dataFunctions.getSortList(), fieldval);
    if (finder.getCanceled())
      return;

    ListIterator it = dataFunctions.getMainIterator();
    boolean found = false;
    while (it.hasNext()) {
      DBItem item = (DBItem)it.next();
      try {
        if (item.matchValue(fieldval[0], fieldval[1])) {
          butCreator.setIterator(it);
          butCreator.setCurrentObject(item);
          dataFunctions.updateData(item);
          found = true;
        }
      } catch (NullPointerException x) {
        x.printStackTrace();
        return;
      }
    }
    ((BrowsePanel)butCreator.getBrowsePanel()).refreshButtons();

    if (!found) {
      JOptionPane.showMessageDialog(this, "The item could not be found", "Search", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  void btnPrint_actionPerformed(ActionEvent e) {
    dataFunctions.printDieFokker();
  }

  void btnClose_actionPerformed(ActionEvent e) {
    if (!dataFunctions.setData(butCreator.getCurrentObject()))
      return;

    ListIterator it = dataFunctions.getMainIterator();
    while (it.hasNext()) {
      DBItem item = (DBItem)it.next();
      if (item.isModified()) {
        int res = JOptionPane.showConfirmDialog(this, "Do you want to save changes", "Save",
           JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
          dataFunctions.saveMainList();
          dataFunctions.closeMainList();
          return;
        }
        else if (res == JOptionPane.NO_OPTION) {
          dataFunctions.closeMainList();
          return;
        }
        else {
          return;
        }
      }
    }
    dataFunctions.closeMainList();
  }
}