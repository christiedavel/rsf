//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.11.02 at 10:24:27 GMT+02:00 
//


package mats.importer;


/**
 * Java content class for Suppliers complex type.
 *  <p>The following schema fragment specifies the expected content contained within this java content object.
 * <p>
 * <pre>
 * &lt;complexType name="Suppliers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplier" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="supplierName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="supplierContact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="supplierTel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="supplierFax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="supplierCel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="supplierAd1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="supplierAd2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="supplierAd3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="supplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface Suppliers {


    java.util.List getSupplier();


    /**
     * Java content class for annonymous complex type.
     *  <p>The following schema fragment specifies the expected content contained within this java content object.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="supplierName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="supplierContact" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="supplierTel" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="supplierFax" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="supplierCel" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="supplierAd1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="supplierAd2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="supplierAd3" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="supplierCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface SupplierType {


        java.lang.String getSupplierTel();

        void setSupplierTel(java.lang.String value);

        java.lang.String getSupplierCel();

        void setSupplierCel(java.lang.String value);

        java.lang.String getSupplierCode();

        void setSupplierCode(java.lang.String value);

        java.lang.String getSupplierName();

        void setSupplierName(java.lang.String value);

        java.lang.String getSupplierAd3();

        void setSupplierAd3(java.lang.String value);

        java.lang.String getSupplierContact();

        void setSupplierContact(java.lang.String value);

        java.lang.String getSupplierAd2();

        void setSupplierAd2(java.lang.String value);

        java.lang.String getSupplierAd1();

        void setSupplierAd1(java.lang.String value);

        java.lang.String getSupplierFax();

        void setSupplierFax(java.lang.String value);

    }

}
