//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2004.11.02 at 10:24:27 GMT+02:00 
//


package mats.importer;


/**
 * Java content class for MaterialGroups complex type.
 *  <p>The following schema fragment specifies the expected content contained within this java content object.
 * <p>
 * <pre>
 * &lt;complexType name="MaterialGroups">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="materialGroups" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="groupID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="groupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="dateUpdated" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="selected" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="permission" type="{http://www.w3.org/2001/XMLSchema}integer"/>
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
public interface MaterialGroups {


    java.util.List getMaterialGroups();


    /**
     * Java content class for annonymous complex type.
     *  <p>The following schema fragment specifies the expected content contained within this java content object.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="groupID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="groupName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="dateUpdated" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="selected" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="permission" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface MaterialGroupsType {


        java.math.BigInteger getPermission();

        void setPermission(java.math.BigInteger value);

        java.math.BigInteger getDateUpdated();

        void setDateUpdated(java.math.BigInteger value);

        java.lang.String getDescription();

        void setDescription(java.lang.String value);

        java.math.BigInteger getSelected();

        void setSelected(java.math.BigInteger value);

        java.lang.String getGroupName();

        void setGroupName(java.lang.String value);

        java.math.BigInteger getGroupID();

        void setGroupID(java.math.BigInteger value);

    }

}
