package artof.dialogs;

import artof.utils.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.net.URLEncoder;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class InternetRegDialog extends JDialog {
  private InternetRegDialog owner = this;
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton btnOK = new JButton();
  JButton btnRegister = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea txtProgress = new JTextArea();

  public InternetRegDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    setSize(300, 200);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  public InternetRegDialog() {
    this(null, "", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    btnOK.setPreferredSize(new Dimension(90, 25));
    btnOK.setText("OK");
    btnOK.addActionListener(new InternetRegDialog_btnOK_actionAdapter(this));
    btnRegister.setPreferredSize(new Dimension(90, 25));
    btnRegister.setText("Register");
    btnRegister.addActionListener(new InternetRegDialog_btnRegister_actionAdapter(this));
    txtProgress.setText("");
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    getContentPane().add(panel1);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(btnOK, null);
    jPanel1.add(btnRegister, null);
    panel1.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(txtProgress, null);
  }

  void btnRegister_actionPerformed(ActionEvent e) {
    Register r = new Register();
  }

  void btnOK_actionPerformed(ActionEvent e) {
    hide();
  }

  class Register implements Runnable {
    private Thread xmlThread;

    public Register() {
      xmlThread = new Thread(this);
      xmlThread.start();
    }

    public void run() {
      try {
        txtProgress.setText("Connecting to Registration Server...\n");
        btnRegister.setEnabled(false);
        btnOK.setEnabled(false);

        if (UserSettings.ownerName == null || UserSettings.ownerName.equals(""))
          throw new NullPointerException();

        if (UserSettings.ownerSurname == null || UserSettings.ownerSurname.equals(""))
          throw new NullPointerException();

        if (UserSettings.ownerCompany == null || UserSettings.ownerCompany.equals(""))
          throw new NullPointerException();

        if (UserSettings.ownerTel == null || UserSettings.ownerTel.equals(""))
          throw new NullPointerException();

        if (UserSettings.ownerAdd1 == null || UserSettings.ownerAdd1.equals(""))
          throw new NullPointerException();

        if (UserSettings.ownerCode == null || UserSettings.ownerCode.equals(""))
          throw new NullPointerException();

        String host = UserSettings.MATERIAL_SERVER;
        int port = UserSettings.MATERIAL_SERVER_PORT;
        String protocol = "http";
        String urlSuffix = "/rsf/registration";
        //String urlSuffix = "/matretriever/registration";
        URL dataURL = new URL(protocol, host, port, urlSuffix);
        URLConnection connection = dataURL.openConnection();

        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        connection.setDoOutput(true);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
        PrintWriter out = new PrintWriter(byteStream, true);
        String postData = "clientID=" + URLEncoder.encode(Utils.encrypt(UserSettings.registrationNo), "UTF-8") + "&";
        postData += "pcOS=" + URLEncoder.encode(System.getProperty("os.name"), "UTF-8") + "&";
        postData += "pcOSVersion=" + URLEncoder.encode(System.getProperty("os.version"), "UTF-8") + "&";
        postData += "pcOSArch=" + URLEncoder.encode(System.getProperty("os.arch"), "UTF-8") + "&";
        postData += "pcUser=" + URLEncoder.encode(System.getProperty("user.name"), "UTF-8") + "&";
        postData += "clientName=" + URLEncoder.encode(UserSettings.ownerName, "UTF-8") + "&";
        postData += "clientSurname=" + URLEncoder.encode(UserSettings.ownerSurname, "UTF-8") + "&";

        try {
          postData += "clientEmail=" + URLEncoder.encode(UserSettings.ownerEmail, "UTF-8") + "&";
        } catch (NullPointerException e) {
          postData += "clientEmail=" + URLEncoder.encode("none", "UTF-8") + "&";
        }

        postData += "clientCompany=" + URLEncoder.encode(UserSettings.ownerCompany, "UTF-8") + "&";
        postData += "clientTel=" + URLEncoder.encode(UserSettings.ownerTel, "UTF-8") + "&";

        try {
          postData += "clientFax=" + URLEncoder.encode(UserSettings.ownerFax, "UTF-8") + "&";
        } catch (NullPointerException e) {
          postData += "clientFax=" + URLEncoder.encode("none", "UTF-8") + "&";
        }

        try {
          postData += "clientCel=" + URLEncoder.encode(UserSettings.ownerCel, "UTF-8") + "&";
        } catch (NullPointerException e) {
          postData += "clientCel=" + URLEncoder.encode("none", "UTF-8") + "&";
        }

        postData += "clientAd1=" + URLEncoder.encode(UserSettings.ownerAdd1, "UTF-8") + "&";

        try {
          postData += "clientAd2=" + URLEncoder.encode(UserSettings.ownerAdd2, "UTF-8") + "&";
        } catch (NullPointerException e) {
          postData += "clientAd2=" + URLEncoder.encode("none", "UTF-8") + "&";
        }

        try {
          postData += "clientAd3=" + URLEncoder.encode(UserSettings.ownerAdd3, "UTF-8") + "&";
        } catch (NullPointerException e) {
          postData += "clientAd3=" + URLEncoder.encode("none", "UTF-8") + "&";
        }

        postData += "clientCode=" + URLEncoder.encode(UserSettings.ownerCode, "UTF-8") + "&";
        postData += "reqDate=" + URLEncoder.encode(String.valueOf(Utils.getCurrentDate()), "UTF-8");

        // Write POST data into local buffer
        out.print(postData);
        out.flush();

        // POST requests are required to have Content-Length
        String lengthString = String.valueOf(byteStream.size());
        connection.setRequestProperty("Content-Length", lengthString);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Write POST data to real output stream
        byteStream.writeTo(connection.getOutputStream());

        /*BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String s;
        while ((s = in.readLine()) != null)
          System.out.println(s);*/

        //System.out.println(UserSettings.expiryDate);

        ResultHandler result = new ResultHandler(connection.getInputStream());
        txtProgress.append("Connection established" + "\n");
        txtProgress.append("\n");
        txtProgress.append("Registering..." + "\n");
        String clientID = result.getClientID();
        int expDate = result.getExpDate();

        if (clientID != null && clientID.equals(UserSettings.registrationNo) && expDate >= UserSettings.expiryDate) {
          PCSec.getInstance().register();
          UserSettings.expiryDate = expDate;
          UserSettings.saveUserSettings();
          txtProgress.append("Registration successful" + "\n");
          txtProgress.setText("\n");
          txtProgress.setText("New expiry date:" + Utils.getDatumStr(expDate) + "\n");

        } else {
          String mes = "Invalid Registration information received.";
          JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
          txtProgress.append("Registration Failed" + "\n");
        }

      } catch (FileNotFoundException e) {
        String mes = "A registration error occurred.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
        txtProgress.append("Registration Failed" + "\n");

      } catch (IOException e) {
        String mes = "A registration error occurred.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
        txtProgress.append("Registration Failed" + "\n");

      } catch (NullPointerException e) {
        String mes = "Not all the owner details are completed.  Please go to Settings | Onwer details and complete all the required fields.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
        txtProgress.append("Registration Failed" + "\n");

      } catch (Exception x) {
        String mes = "Could not connect to Registration Server.  Please make sure you are connected to the internet.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
        txtProgress.append("Registration Failed" + "\n");
      }
      btnRegister.setEnabled(true);
      btnOK.setEnabled(true);
    }
  }

  class ResultHandler extends DefaultHandler {
    private final static String RESULT = "RESULT";
    private final static String CLIENTID = "CLIENTID";
    private final static String EXPDATE = "EXPDATE";

    private String clientID;
    private int expDate;

    public ResultHandler(InputStream in) {
      try {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(in, this);
      } catch (Exception x) {
        clientID = "Server error";
        expDate = 19000101;
      }
    }

    public String getClientID() {
      return clientID;
    }

    public int getExpDate() {
      return expDate;
    }

    public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
      String eName = lName;
      if ("".equals(eName))
        eName = qName;

      if (eName.toUpperCase().equals(RESULT)) {
        if (attrs != null) {
          for (int i = 0; i < attrs.getLength(); i++) {
            String aName = attrs.getLocalName(i);
            if ("".equals(aName))
              aName = attrs.getQName(i);

            if (aName.toUpperCase().equals(CLIENTID)) {
              clientID = attrs.getValue(i);

            } else if (aName.toUpperCase().equals(EXPDATE)) {
              try {
                expDate = Integer.parseInt(attrs.getValue(i));
              } catch (NumberFormatException e) {
                expDate = 19000101;
              }
            }
          }
        }
      }
    }
  }
}

class InternetRegDialog_btnRegister_actionAdapter implements java.awt.event.ActionListener {
  InternetRegDialog adaptee;

  InternetRegDialog_btnRegister_actionAdapter(InternetRegDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnRegister_actionPerformed(e);
  }
}

class InternetRegDialog_btnOK_actionAdapter implements java.awt.event.ActionListener {
  InternetRegDialog adaptee;

  InternetRegDialog_btnOK_actionAdapter(InternetRegDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnOK_actionPerformed(e);
  }
}