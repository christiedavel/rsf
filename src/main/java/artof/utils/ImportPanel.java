package artof.utils;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ImportPanel extends JPanel {
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JCheckBox ckOwnCode = new JCheckBox();
  JCheckBox ckDescription = new JCheckBox();
  JCheckBox ckMatGroup = new JCheckBox();
  JCheckBox ckColour = new JCheckBox();
  JCheckBox ckCost = new JCheckBox();
  JCheckBox ckWidth = new JCheckBox();
  JCheckBox ckLength = new JCheckBox();
  JCheckBox ckThickness = new JCheckBox();
  JCheckBox ckRebate = new JCheckBox();
  JCheckBox ckStatus = new JCheckBox();
  JCheckBox ckImages = new JCheckBox();

  public ImportPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    ckOwnCode.setSelected(UserSettings.matOwnCode);
    ckDescription.setSelected(UserSettings.matDescription);
    ckMatGroup.setSelected(UserSettings.matMatGroup);
    ckColour.setSelected(UserSettings.matColour);
    ckCost.setSelected(UserSettings.matCost);
    ckWidth.setSelected(UserSettings.matWidth);
    ckLength.setSelected(UserSettings.matLength);
    ckThickness.setSelected(UserSettings.matThickness);
    ckRebate.setSelected(UserSettings.matRebate);
    ckStatus.setSelected(UserSettings.matStatus);
    ckImages.setSelected(UserSettings2.matImage);
  }

  public void doOK() {
    UserSettings.matOwnCode = ckOwnCode.isSelected();
    UserSettings.matDescription = ckDescription.isSelected();
    UserSettings.matMatGroup = ckMatGroup.isSelected();
    UserSettings.matColour = ckColour.isSelected();
    UserSettings.matCost = ckCost.isSelected();
    UserSettings.matWidth = ckWidth.isSelected();
    UserSettings.matLength = ckLength.isSelected();
    UserSettings.matThickness = ckThickness.isSelected();
    UserSettings.matRebate = ckRebate.isSelected();
    UserSettings.matStatus = ckStatus.isSelected();
    UserSettings2.matImage = ckImages.isSelected();
  }

  void jbInit() throws Exception {
    jLabel1.setText("Select fields to overwrite when importing materials:");
    this.setLayout(gridBagLayout1);
    ckOwnCode.setText("Own code");
    ckDescription.setText("Description");
    ckMatGroup.setText("Material group");
    ckColour.setText("Colour");
    ckCost.setText("Cost");
    ckWidth.setText("Width");
    ckLength.setText("Length");
    ckThickness.setText("Thickness");
    ckRebate.setText("Rebate");
    ckStatus.setText("Status");
    ckImages.setText("Images");
    this.add(jLabel1,      new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(20, 5, 5, 5), 0, 0));
    this.add(ckOwnCode,     new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    this.add(ckDescription,    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 5, 5), 0, 0));
    this.add(ckMatGroup,    new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckColour,    new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckCost,    new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckWidth,   new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckLength,    new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckThickness,   new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckRebate,    new GridBagConstraints(0, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    this.add(ckStatus,    new GridBagConstraints(1, 5, 1, 2, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(ckImages,      new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
  }
}