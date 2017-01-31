package artof.materials;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.math.BigInteger;

import java.util.ArrayList;

import mats.importer.MaterialGroups;

import java.awt.Component;
import javax.swing.event.*;
import java.util.EventObject;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

class GroupTableModel extends DefaultTableModel {
  private ArrayList groupList;

  public GroupTableModel(ArrayList groupList) {
    this.groupList = groupList;
  }

  public int getColumnCount() {
    return 4;
  }

  public String getColumnName(int column) {
    if (column == 0)
      return "Select";
    else if (column == 1)
      return "Group";
    else if (column == 2)
      return "Description";
    else if (column == 3)
      return "Last Updated";
    else
      return "";
  }

  public int getRowCount() {
    try {
      return groupList.size();

    } catch (NullPointerException e) {
      return 0;
    }
  }

  public Object getValueAt(int row, int column) {
    try {
      MaterialGroups.MaterialGroupsType group = (MaterialGroups.MaterialGroupsType)groupList.get(row);

      if (column == 0) {
        try {
          if (group.getSelected().intValue() == 1)
            return new Boolean(true);
          else
            return new Boolean(false);

        } catch (Exception e) {
          return new Boolean(false);
        }

      } else if (column == 1) {
        return group.getGroupName();

      } else if (column == 2) {
        return group.getDescription();

      } else if (column == 3) {
        return group.getDateUpdated();

      } else {
        return null;
      }

    } catch (Exception e) {
      return null;
    }
  }

  public void setValueAt(Object aValue, int row, int column) {
    try {
      if (column == 0) {
        MaterialGroups.MaterialGroupsType group = (MaterialGroups.MaterialGroupsType)groupList.get(row);
        if (((Boolean)aValue).booleanValue())
          group.setSelected(new BigInteger(String.valueOf(1)));
        else
          group.setSelected(new BigInteger(String.valueOf(0)));
      }

    } catch (Exception e) {
      // fokkof
    }
  }

  public boolean isCellEditable(int row, int column) {
    if (column == 0) {
      try {
          MaterialGroups.MaterialGroupsType group = (MaterialGroups.MaterialGroupsType)groupList.get(row);
          if (group.getPermission().intValue() == 1)
            return true;
          else
            return false;

      } catch (Exception e) {
        return false;
      }

    } else {
      return false;
    }
  }

  public Class getColumnClass(int col) {
    if (col == 0)
      return Boolean.class;
    else
      return String.class;
  }
}


class TableTextArea extends JScrollPane implements TableCellRenderer {
  protected EventListenerList listenerList = new EventListenerList();
  protected ChangeEvent changeEvent = new ChangeEvent(this);

  public TableTextArea() {
    super(new JTextArea());
  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    JTextArea textArea = (JTextArea)getViewport().getView();
    textArea.setLineWrap(true);
    if (isSelected) {
      textArea.setForeground(table.getSelectionForeground());
      textArea.setBackground(table.getSelectionBackground());

    } else {
      textArea.setForeground(table.getForeground());
      textArea.setBackground(table.getBackground());
    }

    textArea.setText((String)value);
    return this;
  }
}


public class GroupDialog extends JDialog {
  private boolean canContinue = false;

  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton btnCancel = new JButton();
  JButton btnDownload = new JButton();
  JLabel jLabel1 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable tblGroups = new JTable();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  Border border1;

  public GroupDialog(ArrayList groupList) {
    GroupTableModel groupTableModel = new GroupTableModel(groupList);
    tblGroups = new JTable(groupTableModel);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    TableColumnModel tcm = tblGroups.getColumnModel();
    tcm.getColumn(2).setCellRenderer(new TableTextArea());
    tcm.getColumn(0).setPreferredWidth(50);
    tcm.getColumn(1).setPreferredWidth(150);
    tcm.getColumn(2).setPreferredWidth(300);
    tcm.getColumn(3).setPreferredWidth(100);

    tblGroups.setRowHeight(25);

    setSize(600, 400);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public boolean continueDownload() {
    return canContinue;
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEmptyBorder(5,5,5,5);
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new GroupDialog_btnCancel_actionAdapter(this));
    btnDownload.setText("Download");
    btnDownload.addActionListener(new GroupDialog_btnDownload_actionAdapter(this));
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Select material groups to download");
    jPanel2.setLayout(gridBagLayout1);
    jPanel1.setBorder(border1);
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Download Groups");
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnCancel, null);
    jPanel1.add(btnDownload, null);
    this.getContentPane().add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jLabel1,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 228, 10, 228), 0, 0));
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(tblGroups, null);
  }

  void btnCancel_actionPerformed(ActionEvent e) {
    hide();
  }

  void btnDownload_actionPerformed(ActionEvent e) {
    canContinue = true;
    hide();
  }
}

class GroupDialog_btnCancel_actionAdapter implements java.awt.event.ActionListener {
  GroupDialog adaptee;

  GroupDialog_btnCancel_actionAdapter(GroupDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnCancel_actionPerformed(e);
  }
}

class GroupDialog_btnDownload_actionAdapter implements java.awt.event.ActionListener {
  GroupDialog adaptee;

  GroupDialog_btnDownload_actionAdapter(GroupDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnDownload_actionPerformed(e);
  }
}