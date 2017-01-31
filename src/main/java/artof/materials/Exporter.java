package artof.materials;

import artof.database.ArtofDB;
import artof.utils.UserSettings;
import artof.utils.UserSettings2;

import javax.swing.JTextArea;
import java.util.*;
import java.sql.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;

import mats.importer.*;
import java.math.BigInteger;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.font.*;
import java.awt.geom.*;
import javax.imageio.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Exporter implements Runnable {
  private ArtofDB db = ArtofDB.getCurrentDB();
  private Thread exportThread;
  private MaterialClient owner;
  private String groupID;
  private String password;

  public Exporter(MaterialClient owner, String groupID, String password) throws Exception {
    this.owner = owner;
    this.groupID = groupID;
    this.password = password;
  }

  public void export() {
    exportThread = new Thread(this);
    exportThread.start();
  }

  public void run() {
    try {
      owner.btnOK.setEnabled(false);
      owner.btnImport.setEnabled(false);

      // suppliers
      owner.txtProgess.append("\n");
      owner.txtProgess.append("Starting to export suppliers \n");
      try {
        String host = UserSettings.MATERIAL_SERVER;
        int port = 80;
        String protocol = "http";
        String urlSuffix = "/rsf/supplierupdater";
        URL dataURL = new URL(protocol, host, port, urlSuffix);
        URLConnection connection = dataURL.openConnection();

        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        connection.setDoOutput(true);

        PrintWriter out = new PrintWriter(connection.getOutputStream());
        getSupplierXML(out);
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String s;
        while ((s = in.readLine()) != null) {
          owner.txtProgess.append("\t" +s + "\n");
        }
        in.close();

        owner.txtProgess.append("\n");
        owner.txtProgess.append("Export of suppliers completed" + "\n");

      } catch (IOException ioe) {
        owner.txtProgess.append("Export of suppliers failed" + "\n");
      }

      //materials
      ArrayList itemCodeList = db.getMaterialItemCodes(MaterialDets.MAT_ALL);
      int counter = 0;
      ArrayList exportList = new ArrayList();
      Iterator it = itemCodeList.iterator();
      while (it.hasNext()) {
        String itemCode = (String)it.next();
        owner.txtProgess.append("\n");
        owner.txtProgess.append("Starting to export " + itemCode + "\n");
        exportList.add(itemCode);
        counter++;
        if (counter > 100 || !it.hasNext()) {
          try {
            String host = UserSettings.MATERIAL_SERVER;
            int port = 80;
            String protocol = "http";
            String urlSuffix = "/rsf/supplierupdater";
            URL dataURL = new URL(protocol, host, port, urlSuffix);
            URLConnection connection = dataURL.openConnection();

            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.setDoOutput(true);

            PrintWriter out = new PrintWriter(connection.getOutputStream());
            getMaterialXML(out, exportList);
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s;
            while ((s = in.readLine()) != null) {
              owner.txtProgess.append(s + "\n");
            }
            in.close();

          } catch (IOException ioe) {
            owner.txtProgess.append("Export error occurred" + "\n");
          }

          counter = 0;
          exportList = new ArrayList();
        }

        owner.txtProgess.append("\n");
        owner.txtProgess.append("Export of " + itemCode + " completed" + "\n");
      }

      owner.txtProgess.append("\n");
      owner.txtProgess.append("Export completed dude" + "\n");

    } catch (Exception x) {
      String mes = "Could not connect to Material Server.  Please make sure you are connected to the internet.";
      JOptionPane.showMessageDialog(owner, mes, "Error", JOptionPane.ERROR_MESSAGE);
    }

    owner.btnImport.setEnabled(true);
    owner.btnOK.setEnabled(true);
  }

 /* public void getMaterialXML(PrintWriter out) throws JAXBException, SQLException {
    try {
      JAXBContext jc = JAXBContext.newInstance("mats.importer");
      ObjectFactory objFactory = new ObjectFactory();

      MaterialData materialData = objFactory.createMaterialData();

      // Groups
      MaterialGroups materialGroups = objFactory.createMaterialGroups();
      List groupList = materialGroups.getMaterialGroups();
      materialData.setMaterialGroups(materialGroups);

      // Suppliers
      Suppliers supplier = objFactory.createSuppliers();
      List supList = supplier.getSupplier();

      String sql = "Select * from suppliers";
      Statement s = db.getConnection().createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        String supplierName = (String) rs.getString(1);
        if (supplierName == null)
          continue;

        Suppliers.SupplierType sup = objFactory.createSuppliersSupplierType();
        sup.setSupplierName(supplierName);

        sup.setSupplierContact("");
        sup.setSupplierTel("");
        sup.setSupplierFax("");
        sup.setSupplierCel("");
        sup.setSupplierAd1("");
        sup.setSupplierAd2("");
        sup.setSupplierAd3("");
        sup.setSupplierCode("");

        try {
          byte[] buf = rs.getBytes(2);
          ObjectInputStream objectIn = new ObjectInputStream(new
              ByteArrayInputStream(buf));
          String[] data = (String[]) objectIn.readObject();

          if (data[0] != null)
            sup.setSupplierContact(data[0]);

          if (data[1] != null)
            sup.setSupplierTel(data[1]);

          if (data[2] != null)
            sup.setSupplierFax(data[2]);

          if (data[3] != null)
            sup.setSupplierCel(data[3]);

          if (data[4] != null)
            sup.setSupplierAd1(data[4]);

          if (data[5] != null)
            sup.setSupplierAd2(data[5]);

          if (data[6] != null)
            sup.setSupplierAd3(data[6]);

          if (data[7] != null)
            sup.setSupplierCode(data[7]);

        }
        catch (Exception x) {
          // doen niks
        }

        supList.add(sup);
      }
      s.close();

      materialData.setSuppliers(supplier);

      // Materials
      Materials materials = objFactory.createMaterials();
      List matList = materials.getMaterial();

      sql = "select * from materials";
      s = db.getConnection().createStatement();
      rs = s.executeQuery(sql);
      while (rs.next()) {
        String itemCode = rs.getString("ItemCode");
        if (itemCode == null)
          continue;

        Materials.MaterialType material = objFactory.createMaterialsMaterialType();
        material.setItemCode(itemCode);

        String ownCode = rs.getString("OwnCode");
        if (ownCode == null)
          material.setOwnCode("");
        else
          material.setOwnCode(ownCode);

        String group = rs.getString("MATERIAL_GROUP");
        if (group == null)
          material.setMatGroup("");
        else
          material.setMatGroup(group);

        String desc = rs.getString("DESCRIPTION");
        if (desc == null)
          material.setMatDesc("");
        else
          material.setMatDesc(desc);

        material.setMatType(new BigInteger(String.valueOf(rs.getInt("MATERIALTYPE"))));
        material.setColorRed(new BigInteger(String.valueOf(rs.getInt("COLORRED"))));
        material.setColorGreen(new BigInteger(String.valueOf(rs.getInt("COLORGREEN"))));
        material.setColorBlue(new BigInteger(String.valueOf(rs.getInt("COLORBLUE"))));
        material.setColorAlpha(new BigInteger(String.valueOf(rs.getInt("COLORALPHA"))));
        material.setGroupID(new BigInteger(groupID));

        // image kak
        try {
          String path = "images/materials/";
          path += itemCode.toLowerCase() + ".jpg";

          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
          DataOutputStream iout = new DataOutputStream(baos);
          while (in.available() != 0) {
            iout.write(in.read());
          }
          material.setMaterialImage(baos.toByteArray());
          in.close();
          iout.close();

        } catch (Exception e) {
          material.setMaterialImage(new byte[0]);
          //e.printStackTrace();
        }

        matList.add(material);
      }
      s.close();

      materialData.setMaterials(materials);

      // Values
      Values values = objFactory.createValues();
      List valList = values.getValue();

      sql = "select m.itemCode, d.supplier, d.cost, d.matlength, d.matwidth, ";
      sql += "d.thickness, d.rebate, d.status ";
      sql += "from materials m, materialdetails d ";
      sql += "where m.materialID = d.materialid ";
      s = db.getConnection().createStatement();
      rs = s.executeQuery(sql);
      while (rs.next()) {
        String itemCode = rs.getString("ITEMCODE");
        String supName = rs.getString("SUPPLIER");
        if (itemCode == null || supName == null)
          continue;

        Values.ValueType value = objFactory.createValuesValueType();
        value.setItemCode(itemCode);
        value.setSupplier(supName);

        value.setCost(new BigDecimal(String.valueOf(rs.getFloat("COST"))));
        value.setLength(new BigDecimal(String.valueOf(rs.getFloat("MATLENGTH"))));
        value.setWidth(new BigDecimal(String.valueOf(rs.getFloat("MATWIDTH"))));
        value.setThickness(new BigDecimal(String.valueOf(rs.getFloat("THICKNESS"))));
        value.setRebate(new BigDecimal(String.valueOf(rs.getFloat("REBATE"))));

        String status = rs.getString("STATUS");
        if (status == null)
          value.setStatus("Available");
        else
          value.setStatus(rs.getString("STATUS"));

        valList.add(value);
      }
      s.close();

      materialData.setValues(values);

      // Laaste kak
      Marshaller m = jc.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      m.marshal(materialData, out);

    } finally {

    }
  }*/


  public void getSupplierXML(PrintWriter out) throws JAXBException, SQLException {
    try {
      JAXBContext jc = JAXBContext.newInstance("mats.importer");
      ObjectFactory objFactory = new ObjectFactory();

      MaterialData materialData = objFactory.createMaterialData();

      // Groups
      MaterialGroups materialGroups = objFactory.createMaterialGroups();
      List groupList = materialGroups.getMaterialGroups();
      materialData.setMaterialGroups(materialGroups);

      // Suppliers
      Suppliers supplier = objFactory.createSuppliers();
      List supList = supplier.getSupplier();

      String sql = "Select * from suppliers";
      Statement s = db.getConnection().createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        String supplierName = (String) rs.getString(1);
        if (supplierName == null)
          continue;

        Suppliers.SupplierType sup = objFactory.createSuppliersSupplierType();
        sup.setSupplierName(supplierName);

        sup.setSupplierContact("");
        sup.setSupplierTel("");
        sup.setSupplierFax("");
        sup.setSupplierCel("");
        sup.setSupplierAd1("");
        sup.setSupplierAd2("");
        sup.setSupplierAd3("");
        sup.setSupplierCode("");

        try {
          byte[] buf = rs.getBytes(2);
          ObjectInputStream objectIn = new ObjectInputStream(new
              ByteArrayInputStream(buf));
          String[] data = (String[]) objectIn.readObject();

          if (data[0] != null)
            sup.setSupplierContact(data[0]);

          if (data[1] != null)
            sup.setSupplierTel(data[1]);

          if (data[2] != null)
            sup.setSupplierFax(data[2]);

          if (data[3] != null)
            sup.setSupplierCel(data[3]);

          if (data[4] != null)
            sup.setSupplierAd1(data[4]);

          if (data[5] != null)
            sup.setSupplierAd2(data[5]);

          if (data[6] != null)
            sup.setSupplierAd3(data[6]);

          if (data[7] != null)
            sup.setSupplierCode(data[7]);

        }
        catch (Exception x) {
          // doen niks
        }

        supList.add(sup);
      }
      s.close();

      materialData.setSuppliers(supplier);

      // Materials
      Materials materials = objFactory.createMaterials();
      List matList = materials.getMaterial();
      materialData.setMaterials(materials);

      // Values
      Values values = objFactory.createValues();
      List valList = values.getValue();
      materialData.setValues(values);

      // Laaste kak
      Marshaller m = jc.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      m.marshal(materialData, out);

    } finally {

    }
  }

  public void getMaterialXML(PrintWriter out, ArrayList itemCodeList) throws JAXBException, SQLException {
    try {
      JAXBContext jc = JAXBContext.newInstance("mats.importer");
      ObjectFactory objFactory = new ObjectFactory();

      MaterialData materialData = objFactory.createMaterialData();

      // Groups
      MaterialGroups materialGroups = objFactory.createMaterialGroups();
      List groupList = materialGroups.getMaterialGroups();
      materialData.setMaterialGroups(materialGroups);

      // Suppliers
      Suppliers supplier = objFactory.createSuppliers();
      List supList = supplier.getSupplier();
      materialData.setSuppliers(supplier);

      // Materials
      Materials materials = objFactory.createMaterials();
      List matList = materials.getMaterial();

      String sql = "select * from materials where itemCode in (";
      Iterator it = itemCodeList.iterator();
      boolean firstTime = true;
      while (it.hasNext()) {
        String itemCode = (String)it.next();
        if (!firstTime)
          sql += ", ";
        sql += "'" + itemCode + "'";
        firstTime = false;
      }
      sql += ")";
      Statement s = db.getConnection().createStatement();
      ResultSet rs = s.executeQuery(sql);
      while (rs.next()) {
        String itemCode = rs.getString("ItemCode");
        if (itemCode == null)
          continue;

        Materials.MaterialType material = objFactory.createMaterialsMaterialType();
        material.setItemCode(itemCode);

        String ownCode = rs.getString("OwnCode");
        if (ownCode == null)
          material.setOwnCode("");
        else
          material.setOwnCode(ownCode);

        String group = rs.getString("MATERIAL_GROUP");
        if (group == null)
          material.setMatGroup("");
        else
          material.setMatGroup(group);

        String desc = rs.getString("DESCRIPTION");
        if (desc == null)
          material.setMatDesc("");
        else
          material.setMatDesc(desc);

        material.setMatType(new BigInteger(String.valueOf(rs.getInt("MATERIALTYPE"))));
        material.setColorRed(new BigInteger(String.valueOf(rs.getInt("COLORRED"))));
        material.setColorGreen(new BigInteger(String.valueOf(rs.getInt("COLORGREEN"))));
        material.setColorBlue(new BigInteger(String.valueOf(rs.getInt("COLORBLUE"))));
        material.setColorAlpha(new BigInteger(String.valueOf(rs.getInt("COLORALPHA"))));
        material.setGroupID(new BigInteger(groupID));

        // image kak
        try {
          String path = "images/materials/";
          path += itemCode.toLowerCase() + ".jpg";

          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
          DataOutputStream iout = new DataOutputStream(baos);
          while (in.available() != 0) {
            iout.write(in.read());
          }
          material.setMaterialImage(baos.toByteArray());
          in.close();
          iout.close();

        } catch (Exception e) {
          material.setMaterialImage(new byte[0]);
          //e.printStackTrace();
        }

        matList.add(material);
      }
      s.close();

      materialData.setMaterials(materials);

      // Values
      Values values = objFactory.createValues();
      List valList = values.getValue();

      sql = "select m.itemCode, d.supplier, d.cost, d.matlength, d.matwidth, ";
      sql += "d.thickness, d.rebate, d.status ";
      sql += "from materials m, materialdetails d ";
      sql += "where m.materialID = d.materialid and m.itemcode in (";
      it = itemCodeList.iterator();
      firstTime = true;
      while (it.hasNext()) {
        String itemCode = (String)it.next();
        if (!firstTime)
          sql += ", ";
        sql += "'" + itemCode + "'";
        firstTime = false;
      }
      sql += ")";
      s = db.getConnection().createStatement();
      rs = s.executeQuery(sql);
      while (rs.next()) {
        String itemCode = rs.getString("ITEMCODE");
        String supName = rs.getString("SUPPLIER");
        if (itemCode == null || supName == null)
          continue;

        Values.ValueType value = objFactory.createValuesValueType();
        value.setItemCode(itemCode);
        value.setSupplier(supName);

        value.setCost(new BigDecimal(String.valueOf(rs.getFloat("COST"))));
        value.setLength(new BigDecimal(String.valueOf(rs.getFloat("MATLENGTH"))));
        value.setWidth(new BigDecimal(String.valueOf(rs.getFloat("MATWIDTH"))));
        value.setThickness(new BigDecimal(String.valueOf(rs.getFloat("THICKNESS"))));
        value.setRebate(new BigDecimal(String.valueOf(rs.getFloat("REBATE"))));

        String status = rs.getString("STATUS");
        if (status == null)
          value.setStatus("Available");
        else
          value.setStatus(rs.getString("STATUS"));

        valList.add(value);
      }
      s.close();

      materialData.setValues(values);

      // Laaste kak
      Marshaller m = jc.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      m.marshal(materialData, out);

    } finally {

    }
  }
}
