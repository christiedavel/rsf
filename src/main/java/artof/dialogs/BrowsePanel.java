package artof.dialogs;
import artof.database.DataFunctions;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BrowsePanel extends JPanel {
  private DataFunctions dataFunctions;
  private ButCreator butCreator;

  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JButton btnPrev = new JButton(new ImageIcon("images/PrevDes.gif"));
  private JButton btnFirst = new JButton(new ImageIcon("images/First.gif"));
  private JButton btnNext = new JButton(new ImageIcon("images/NextDes.gif"));
  private JButton btnLast = new JButton(new ImageIcon("images/Last.gif"));
  private Border border1;

  public BrowsePanel(DataFunctions dataFunctions, ButCreator butCreator) {
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
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(103, 101, 98),new Color(148, 145, 140)),BorderFactory.createEmptyBorder(0,5,0,5));
    btnPrev.setMargin(new Insets(2, 2, 2, 2));
    btnPrev.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnPrev_actionPerformed(e);
      }
    });
    this.setLayout(gridBagLayout1);
    btnFirst.setMargin(new Insets(2, 2, 2, 2));
    btnFirst.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnFirst_actionPerformed(e);
      }
    });
    btnNext.setMargin(new Insets(2, 2, 2, 2));
    btnNext.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNext_actionPerformed(e);
      }
    });
    btnLast.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnLast_actionPerformed(e);
      }
    });
    this.setBorder(border1);
    btnLast.setMargin(new Insets(2, 2, 2, 2));
    this.add(btnPrev,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnFirst,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnNext,  new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(btnLast,   new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
  }

  void btnFirst_actionPerformed(ActionEvent e) {
    if (!dataFunctions.setData(butCreator.getCurrentObject()))
      return;

    Object obj = null;
    while (butCreator.getIterator().hasPrevious())
      obj = butCreator.getIterator().previous();

    if (obj != null) {
      /*if (!butCreator.getIterator().hasPrevious()) {
        btnPrev.setEnabled(false);
        btnFirst.setEnabled(false);
      }
      btnNext.setEnabled(true);
      btnLast.setEnabled(true);*/
      dataFunctions.updateData(obj);
      butCreator.setCurrentObject(obj);
    }
  }

  void btnPrev_actionPerformed(ActionEvent e) {
    if (!dataFunctions.setData(butCreator.getCurrentObject()))
      return;

    if (butCreator.getIterator().hasPrevious()) {
      Object obj = butCreator.getIterator().previous();
      if (butCreator.getCurrentObject() == obj && butCreator.getIterator().hasPrevious())
        obj = butCreator.getIterator().previous();

      /*if (!butCreator.getIterator().hasPrevious()) {
        btnPrev.setEnabled(false);
        btnFirst.setEnabled(false);
      }
      btnNext.setEnabled(true);
      btnLast.setEnabled(true);*/
      dataFunctions.updateData(obj);
      butCreator.setCurrentObject(obj);
    }
  }

  void btnNext_actionPerformed(ActionEvent e) {
    if (!dataFunctions.setData(butCreator.getCurrentObject()))
      return;

    if (butCreator.getIterator().hasNext()) {
      Object obj = butCreator.getIterator().next();
      if (butCreator.getCurrentObject() == obj && butCreator.getIterator().hasNext())
        obj = butCreator.getIterator().next();

      /*if (!butCreator.getIterator().hasNext()) {
        btnNext.setEnabled(false);
        btnLast.setEnabled(false);
      }
      btnPrev.setEnabled(true);
      btnFirst.setEnabled(true);*/
      dataFunctions.updateData(obj);
      butCreator.setCurrentObject(obj);
    }
  }

  void btnLast_actionPerformed(ActionEvent e) {
    if (!dataFunctions.setData(butCreator.getCurrentObject()))
      return;

    Object obj = null;
    while (butCreator.getIterator().hasNext())
      obj = butCreator.getIterator().next();

    if (obj != null) {
      /*if (!butCreator.getIterator().hasNext()) {
        btnNext.setEnabled(false);
        btnLast.setEnabled(false);
      }
      btnPrev.setEnabled(true);
      btnFirst.setEnabled(true);*/
      dataFunctions.updateData(obj);
      butCreator.setCurrentObject(obj);
    }
  }

  public void refreshButtons() {
    if (!butCreator.getIterator().hasPrevious()) {
      btnPrev.setEnabled(false);
      btnFirst.setEnabled(false);
    } else {
      btnPrev.setEnabled(true);
      btnFirst.setEnabled(true);
    }

    if (!butCreator.getIterator().hasNext()) {
      btnNext.setEnabled(false);
      btnLast.setEnabled(false);
    } else {
      btnNext.setEnabled(true);
      btnLast.setEnabled(true);
    }
  }
}