
package tds.testpackage.legacy.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}identifier"/>
 *         &lt;element ref="{}conversiontuple" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="bpelementid" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="purpose" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="score"/>
 *             &lt;enumeration value="standarderror"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="measureid" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="formid" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "identifier",
    "conversiontuple"
})
@XmlRootElement(name = "conversiontable")
public class Conversiontable {

    @XmlElement(required = true)
    protected Identifier identifier;
    @XmlElement(required = true)
    protected List<Conversiontuple> conversiontuple;
    @XmlAttribute(name = "bpelementid", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String bpelementid;
    @XmlAttribute(name = "purpose", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String purpose;
    @XmlAttribute(name = "measureid", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String measureid;
    @XmlAttribute(name = "formid")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String formid;

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link Identifier }
     *     
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Identifier }
     *     
     */
    public void setIdentifier(Identifier value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the conversiontuple property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the conversiontuple property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConversiontuple().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Conversiontuple }
     * 
     * 
     */
    public List<Conversiontuple> getConversiontuple() {
        if (conversiontuple == null) {
            conversiontuple = new ArrayList<Conversiontuple>();
        }
        return this.conversiontuple;
    }

    /**
     * Gets the value of the bpelementid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBpelementid() {
        return bpelementid;
    }

    /**
     * Sets the value of the bpelementid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBpelementid(String value) {
        this.bpelementid = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the measureid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeasureid() {
        return measureid;
    }

    /**
     * Sets the value of the measureid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeasureid(String value) {
        this.measureid = value;
    }

    /**
     * Gets the value of the formid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormid() {
        return formid;
    }

    /**
     * Sets the value of the formid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormid(String value) {
        this.formid = value;
    }

}
