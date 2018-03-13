
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
 *         &lt;element ref="{}passageref" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}groupitem" maxOccurs="unbounded"/>
 *         &lt;element ref="{}property" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="formposition" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="maxresponses" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="maxitems" type="{http://www.w3.org/2001/XMLSchema}token" />
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
    "passageref",
    "groupitem",
    "property"
})
@XmlRootElement(name = "itemgroup")
public class Itemgroup {

    @XmlElement(required = true)
    protected Identifier identifier;
    protected List<Passageref> passageref;
    @XmlElement(required = true)
    protected List<Groupitem> groupitem;
    protected List<Property> property;
    @XmlAttribute(name = "formposition")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String formposition;
    @XmlAttribute(name = "maxresponses", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String maxresponses;
    @XmlAttribute(name = "maxitems")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String maxitems;

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
     * Gets the value of the passageref property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the passageref property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPassageref().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Passageref }
     * 
     * 
     */
    public List<Passageref> getPassageref() {
        if (passageref == null) {
            passageref = new ArrayList<Passageref>();
        }
        return this.passageref;
    }

    /**
     * Gets the value of the groupitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupitem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Groupitem }
     * 
     * 
     */
    public List<Groupitem> getGroupitem() {
        if (groupitem == null) {
            groupitem = new ArrayList<Groupitem>();
        }
        return this.groupitem;
    }

    /**
     * Gets the value of the property property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Property }
     * 
     * 
     */
    public List<Property> getProperty() {
        if (property == null) {
            property = new ArrayList<Property>();
        }
        return this.property;
    }

    /**
     * Gets the value of the formposition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormposition() {
        return formposition;
    }

    /**
     * Sets the value of the formposition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormposition(String value) {
        this.formposition = value;
    }

    /**
     * Gets the value of the maxresponses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxresponses() {
        return maxresponses;
    }

    /**
     * Sets the value of the maxresponses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxresponses(String value) {
        this.maxresponses = value;
    }

    /**
     * Gets the value of the maxitems property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxitems() {
        return maxitems;
    }

    /**
     * Sets the value of the maxitems property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxitems(String value) {
        this.maxitems = value;
    }

}
