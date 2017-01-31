package artof.dialogs;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.util.Random;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ProgressDialog extends JDialog implements Runnable {
  private int length;
  private JPanel jPanel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JProgressBar pbrTask;// = new JProgressBar();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private Border border1;
  JLabel txtSrc = new JLabel();
  JLabel txtTarget = new JLabel();
  private boolean continueBullshitting;

  public ProgressDialog(String title) {
    pbrTask = new JProgressBar(0, length);

    try {
      jbInit();
    } catch(Exception e) {
      e.printStackTrace();
    }

    Dimension screenSize = getToolkit().getScreenSize();
    setSize(400, 140);
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setTitle(title);
  }

  public int getValue() {
    return this.pbrTask.getValue();
  }

  public void setLength(int length) {
    this.length = length;
    pbrTask.setMaximum(length);
  }

  public void setValue(int value) {
    if (value >= length) {
      length *= 2;
      pbrTask.setMaximum(length);
    }
    pbrTask.setValue(value);
  }

  public void setValue2(int value) {
    pbrTask.setValue(Math.min(length, value));
  }

  public void setTxtSrc(String val) {
    this.txtSrc.setText(val);
  }

  public void setTxtTarget(String val) {
    this.txtTarget.setText(val);
  }

  public void startBulshitting() {
    continueBullshitting = true;
    setLength(100);
    Thread bulshitThread;
    bulshitThread = new Thread(this);
    bulshitThread.start();
  }

  public void stopBulshitting() {
    continueBullshitting = false;
    hide();
  }

  public void run() {
    Random barValue = new Random();
    Random timeValue = new Random();
    int trek = 1;
    while (continueBullshitting) {
      try {
        Thread.sleep(timeValue.nextInt(400));
      } catch (InterruptedException e) {
        // fok voort
      }

      trek += barValue.nextInt(30);
      if (trek > 90) {
        trek = barValue.nextInt(30);
      }
      setValue(trek);
    }
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(103, 101, 98),new Color(148, 145, 140)),BorderFactory.createEmptyBorder(5,5,5,5));
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("");
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    jPanel1.setBorder(border1);
    jPanel1.setMaximumSize(new Dimension(2147483647, 2147483647));
    borderLayout1.setHgap(5);
    borderLayout1.setVgap(5);
    this.getContentPane().add(jPanel1,  BorderLayout.CENTER);
    jPanel1.add(pbrTask,     new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(txtSrc,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 10, 5), 0, 0));
    jPanel1.add(txtTarget,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 20, 5), 0, 0));
  }
}