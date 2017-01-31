package artof.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.html.HTMLDocument;
import java.net.URL;
import java.io.*;
import javax.swing.event.*;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class HelpDialog extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton btnOK = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JEditorPane edHelp = new JEditorPane();

  public HelpDialog() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    try {
      //File f = new File("/home/jaco/Helptopics/unixcomm.html");
      File f = new File("doc/index.html");
      edHelp.setPage(f.toURL());

    } catch (Exception e) {
      e.printStackTrace();
    }

    class Hyperactive implements HyperlinkListener {
      public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
          JEditorPane pane = (JEditorPane) e.getSource();
          if (e instanceof HTMLFrameHyperlinkEvent) {
            HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
            HTMLDocument doc = (HTMLDocument)pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent(evt);

          } else {
            try {
              pane.setPage(e.getURL());
            } catch (Throwable t) {
              t.printStackTrace();
            }
          }
        }
      }
    }

    setSize(700, 600);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setModal(true);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    btnOK.setText("OK");
    btnOK.addActionListener(new HelpDialog_btnOK_actionAdapter(this));
    edHelp.setEditable(false);
    edHelp.setText("jEditorPane1");
    edHelp.setContentType("text/html");
    jScrollPane1.setViewportBorder(BorderFactory.createRaisedBevelBorder());
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnOK, null);
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(edHelp, null);
  }

  void btnOK_actionPerformed(ActionEvent e) {
    hide();
  }

}

class HelpDialog_btnOK_actionAdapter implements java.awt.event.ActionListener {
  HelpDialog adaptee;

  HelpDialog_btnOK_actionAdapter(HelpDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnOK_actionPerformed(e);
  }
}