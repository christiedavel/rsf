package artof.materials;

import artof.database.ArtofDB;
import artof.utils.UserSettings;
import artof.utils.UserSettings2;
import artof.utils.Utils;

import artof.designitems.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.net.*;

import mats.importer.*;

import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.SAXParseException;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MaterialClient extends JDialog {
  private boolean done = false;
  private MaterialClient owner = this;
  private JPanel jPanel1 = new JPanel();
  JButton btnImport = new JButton();
  JButton btnOK = new JButton();
  private Border border1;
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea txtProgess = new JTextArea() {
    public void append(String s) {
      super.append(s);
      setCaretPosition(getText().length());
    }
  };
  Border border2;
  JButton btnUpload = new JButton();

  public MaterialClient() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    // Steek upload button weg as nie file kan kry nie
    try {
      BufferedReader in = new BufferedReader(new FileReader("rsfdb/uploader.dat"));
      btnUpload.setVisible(true);

    } catch (Exception e) {
      btnUpload.setVisible(false);
    }

    setSize(400, 400);
    Dimension screenSize = getToolkit().getScreenSize();
    setLocation(screenSize.width/2 - this.getWidth()/2, screenSize.height/2 - this.getHeight()/2);
    setVisible(true);
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    btnImport.setText("Import");
    btnImport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnImport_actionPerformed(e);
      }
    });
    btnOK.setPreferredSize(new Dimension(73, 27));
    btnOK.setText("OK");
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOK_actionPerformed(e);
      }
    });
    this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Import Materials");
    txtProgess.setEditable(false);
    txtProgess.setText("");
    txtProgess.setWrapStyleWord(true);
    jScrollPane1.setBorder(border2);
    btnUpload.setText("Upload");
    btnUpload.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnUpload_actionPerformed(e);
      }
    });
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(btnOK, null);
    jPanel1.add(btnImport, null);
    jPanel1.add(btnUpload, null);
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(txtProgess, null);
  }

  void btnOK_actionPerformed(ActionEvent e) {
    hide();
  }


  void btnImport_actionPerformed(ActionEvent e) {
    ArrayList groupList = getMaterialGroups();
    GroupDialog groupDialog = new GroupDialog(groupList);
    if (groupDialog.continueDownload()) {
      boolean atLeastOne = false;
      if (groupList != null) {
        Iterator it = groupList.iterator();
        while (it.hasNext()) {
          MaterialGroups.MaterialGroupsType group = (MaterialGroups.MaterialGroupsType)it.next();
          if (group.getSelected().intValue() == 1) {
            atLeastOne = true;
            break;
          }
        }
      }

      if (atLeastOne) {
        Importer importer = new Importer(groupList);
      } else {
        hide();
      }

    } else {
      hide();
    }
  }

  class Bullshitter implements Runnable {
    private Thread bullshitThread;
    private Random r = new Random();
    private int count = 1;

    public Bullshitter() {
      bullshitThread = new Thread(this);
      bullshitThread.start();
    }

    public void run() {
      while (!done) {
        try {
          bullshitThread.sleep(r.nextInt(10000));
        } catch (InterruptedException e) {
        }
        txtProgess.append("Reading data block " + count++ + "\n");
      }
    }
  }


  class Importer implements Runnable {
    private ArtofDB db = ArtofDB.getCurrentDB();
    private ArrayList groupList;
    private Thread xmlThread;

    public Importer(ArrayList groupList) {
      this.groupList = groupList;
      xmlThread = new Thread(this);
      xmlThread.start();
    }

    public void run() {
      try {
        btnImport.setEnabled(false);
        btnOK.setEnabled(false);

        ArrayList itemCodeList = readItemCodes();
        String itemCodeParameters = "";
        int counter = 0;
        Iterator it = itemCodeList.iterator();
        while (it.hasNext()) {
          String itemCode = (String)it.next();
          itemCodeParameters += "&itemcodes=" + URLEncoder.encode(itemCode, "UTF-8");

          if (counter >= 100 || !it.hasNext()) {
            String host = UserSettings.MATERIAL_SERVER;
            int port = UserSettings.MATERIAL_SERVER_PORT;
            String protocol = "http";
            String urlSuffix = "/rsf/retriever";
            URL dataURL = new URL(protocol, host, port, urlSuffix);
            URLConnection connection = dataURL.openConnection();
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.setDoOutput(true);

            // Write POST data into local buffer
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
            PrintWriter wout = new PrintWriter(byteStream, true);
            String postData = "clientID=" + URLEncoder.encode(Utils.encrypt(UserSettings.registrationNo), "UTF-8");
            postData += itemCodeParameters;
            wout.print(postData);
            wout.flush();

            // POST requests are required to have Content-Length
            String lengthString = String.valueOf(byteStream.size());
            connection.setRequestProperty("Content-Length", lengthString);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Write POST data to real output stream
            byteStream.writeTo(connection.getOutputStream());

            // read shit
            JAXBContext jc = JAXBContext.newInstance("mats.importer");
            Unmarshaller u = jc.createUnmarshaller();

            Bullshitter bullshitter = new Bullshitter();
            MaterialData data = (MaterialData)u.unmarshal(connection.getInputStream());
            done = true;

            // suppliers is klaar

            // import materials
            Materials materials = data.getMaterials();
            Iterator it2 = materials.getMaterial().iterator();
            while (it2.hasNext()) {
              Materials.MaterialType mat = (Materials.MaterialType)it2.next();
              MaterialDets det = new MaterialDets();
              txtProgess.append("Reading material:" + mat.getItemCode() + "\n");
              det.setItemCode(mat.getItemCode());
              det.setMaterialType(mat.getMatType().intValue());
              det.setOwnCode("~" + mat.getItemCode());
              det.setGroup(mat.getMatGroup());
              det.setDescription(mat.getMatDesc());
              det.setColor(new Color(mat.getColorRed().intValue(),
                                     mat.getColorGreen().intValue(),
                                     mat.getColorBlue().intValue(),
                                     mat.getColorAlpha().intValue()));

              //image kak
              byte[] imageBytes = mat.getMaterialImage();
              if (UserSettings2.matImage && imageBytes != null && imageBytes.length > 0) {
                try {
                  String path = "images/materials/" + mat.getItemCode().toLowerCase() + ".jpg";
                  DataInputStream imageIn = new DataInputStream(new ByteArrayInputStream(imageBytes));
                  DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
                  while (imageIn.available() != 0) {
                    out.write(imageIn.read());
                  }
                  imageIn.close();
                  out.close();
                  txtProgess.append("Imported image for " + det.getItemCode() + "\n");

                } catch (Exception x) {
                  txtProgess.append("Could not import image for " + det.getItemCode() + "\n");
                }
              }
              txtProgess.append(db.importMaterial(det) + "\n");
            }

            // import values
            Values values = data.getValues();
            it2 = values.getValue().iterator();
            while (it2.hasNext()) {
              Values.ValueType val = (Values.ValueType)it2.next();
              MaterialValues v = new MaterialValues();
              v.setCost(val.getCost().floatValue());
              v.setWidth(val.getWidth().floatValue());
              v.setLength(val.getLength().floatValue());
              v.setThickness(val.getThickness().floatValue());
              v.setRebate(val.getRebate().floatValue());
              v.setStatus(val.getStatus());
              txtProgess.append(db.importMaterialValues(val.getItemCode(), val.getSupplier(), v) + "\n");
            }

            counter = 0;
            itemCodeParameters = "";
          } else {
            counter ++;
          }
        }

        txtProgess.append("\n");
        txtProgess.append("Transfer completed" + "\n");

        // stel al die maps op van materials
        txtProgess.append("\n");
        txtProgess.append("Building material maps..." + "\n");
        try {
          DesignBoard.rebuildBoardMapper();
          DesignFrame.rebuildFrameMapper();
          DesignSlip.rebuildSlipMapper();
          DesignFoil.rebuildFoilMapper();
          DesignGlassAndBack.rebuildGBMapper();

          TypeMapper.rebuildTypeMapper();
          txtProgess.append("Material maps completed" + "\n");

        } catch (NullPointerException e) {
          txtProgess.append("Error when building material maps" + "\n");
        }

      } catch (JAXBException je) {
        done = true;
        String mes = "An error accurred during data transfer from the Material Server.  ";
        mes += "Please make sure you have permission to download the material data.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);

      } catch (IOException ioe) {
        done = true;
        String mes = "Could not connect to Material Server.  Please make sure you are connected to the internet.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);

      } catch (Exception x) {
        done = true;
        String mes = "Could not connect to Material Server.  Please make sure you are connected to the internet.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
      }

      done = true;
      btnImport.setEnabled(true);
      btnOK.setEnabled(true);
    }

    public ArrayList readItemCodes() throws JAXBException, IOException, Exception {
      txtProgess.append("\n");
      txtProgess.append("Reading item codes..." + "\n");

      Bullshitter bullshitter = new Bullshitter();
      String host = UserSettings.MATERIAL_SERVER;
      int port = UserSettings.MATERIAL_SERVER_PORT;
      String protocol = "http";
      String urlSuffix = "/rsf/itemcodereader";
      URL dataURL = new URL(protocol, host, port, urlSuffix);
      URLConnection connection = dataURL.openConnection();
      connection.setUseCaches(false);
      connection.setDefaultUseCaches(false);
      connection.setDoOutput(true);

      // Write POST data into local buffer
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
      PrintWriter wout = new PrintWriter(byteStream, true);
      String postData = "clientID=" + URLEncoder.encode(Utils.encrypt(UserSettings.registrationNo), "UTF-8");
      if (groupList != null) {
        Iterator it = groupList.iterator();
        while (it.hasNext()) {
          MaterialGroups.MaterialGroupsType group = (MaterialGroups.MaterialGroupsType)it.next();
          if (group.getSelected().intValue() == 1) {
            postData += "&groupID=" + URLEncoder.encode(String.valueOf(group.getGroupID().intValue()), "UTF-8");
          }
        }
      } else {
        postData += "&groupID=" + URLEncoder.encode(String.valueOf(-1), "UTF-8");
      }
      wout.print(postData);
      wout.flush();

      // POST requests are required to have Content-Length
      String lengthString = String.valueOf(byteStream.size());
      connection.setRequestProperty("Content-Length", lengthString);
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

      // Write POST data to real output stream
      byteStream.writeTo(connection.getOutputStream());

      // read available itemcodes
      JAXBContext jc = JAXBContext.newInstance("mats.importer");
      Unmarshaller u = jc.createUnmarshaller();
      MaterialData data = (MaterialData)u.unmarshal(connection.getInputStream());

      // import suppliers
      txtProgess.append("Suppliers:\n");
      Suppliers suppliers = data.getSuppliers();
      Iterator it = suppliers.getSupplier().iterator();
      while (it.hasNext()) {
        Suppliers.SupplierType sup = (Suppliers.SupplierType)it.next();
        SupplierDets dets = new SupplierDets();
        dets.setName(sup.getSupplierName());
        txtProgess.append(db.importSupplier(dets) + "\n");
      }

      Materials materials = data.getMaterials();
      it = materials.getMaterial().iterator();
      ArrayList itemCodeList = new ArrayList();
      while (it.hasNext()) {
        Materials.MaterialType mat = (Materials.MaterialType)it.next();
        itemCodeList.add(mat.getItemCode());
      }

      done = true;
      return itemCodeList;
    }
  }

  /*class GroupReader implements Runnable {
    private Thread xmlThread;

    public GroupReader() {
      xmlThread = new Thread(this);
      xmlThread.start();
    }

    public void run() {*/
  private ArrayList getMaterialGroups() {
      try {
        btnImport.setEnabled(false);
        btnOK.setEnabled(false);
        String host = UserSettings.MATERIAL_SERVER;
        //String host = "localhost";
        int port = UserSettings.MATERIAL_SERVER_PORT;
        String protocol = "http";
        String urlSuffix = "/rsf/groupreader";
        URL dataURL = new URL(protocol, host, port, urlSuffix);
        URLConnection connection = dataURL.openConnection();
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        connection.setDoOutput(true);

        // Write POST data into local buffer
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(512);
        PrintWriter wout = new PrintWriter(byteStream, true);
        //String postData = "clientID=" + URLEncoder.encode(Utils.encrypt(UserSettings.registrationNo), "UTF-8");
        String postData = "clientID=" + URLEncoder.encode(UserSettings.registrationNo, "UTF-8");
        wout.print(postData);
        wout.flush();

        // POST requests are required to have Content-Length
        String lengthString = String.valueOf(byteStream.size());
        connection.setRequestProperty("Content-Length", lengthString);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Write POST data to real output stream
        byteStream.writeTo(connection.getOutputStream());

        // read shit
        try {
          JAXBContext jc = JAXBContext.newInstance("mats.importer");
          Unmarshaller u = jc.createUnmarshaller();

          //Bullshitter bullshitter = new Bullshitter();
          MaterialData data = (MaterialData)u.unmarshal(connection.getInputStream());

          txtProgess.append("Reading material groups...\n");
          MaterialGroups materialGroups = data.getMaterialGroups();
          ArrayList groupList = new ArrayList();
          Iterator it = materialGroups.getMaterialGroups().iterator();
          while (it.hasNext()) {
            MaterialGroups.MaterialGroupsType group = (MaterialGroups.MaterialGroupsType)it.next();
            groupList.add(group);
          }
          done = true;
          return groupList;

        } catch (JAXBException je) {
          done = true;
          //je.printStackTrace();
          String mes = "An error accurred during group data transfer from the Material Server.  ";
          mes += "Please make sure you have permission to download the material data.";
          JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ioe) {
          done = true;
          String mes = "Could not connect to Material Server.  Please make sure you are connected to the internet.";
          JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
        }

      } catch (Exception x) {
        done = true;
        String mes = "Could not connect to Material Server.  Please make sure you are connected to the internet.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
      }

      return new ArrayList();
    //}
  }

  void btnUpload_actionPerformed(ActionEvent e) {
    UploadDialog uploader = new UploadDialog();
    if (uploader.continueUpload()) {
      if ("genis".equals(uploader.getPassword())) {
        try {
          Exporter exporter = new Exporter(this, uploader.getGroupID(), uploader.getPassword());
          exporter.export();

        } catch (Exception x) {
          String mes = "Export failed.  Please make sure you are connected to the internet.";
          JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
        }
      } else {
        String mes = "Invalid password.";
        JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
        hide();
      }
    } else {
      hide();
    }
  }
}