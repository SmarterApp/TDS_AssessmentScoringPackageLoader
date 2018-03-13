
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
 *         &lt;element ref="{}segmentblueprint"/>
 *         &lt;element ref="{}itemselector"/>
 *         &lt;choice>
 *           &lt;element ref="{}segmentpool"/>
 *           &lt;element ref="{}segmentform" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="segmentid" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="itemselection" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "segmentblueprint",
    "itemselector",
    "segmentpool",
    "segmentform"
})
@XmlRootElement(name = "adminsegment")
public class Adminsegment {

    @XmlElement(required = true)
    protected Segmentblueprint segmentblueprint;
    @XmlElement(required = true)
    protected Itemselector itemselector;
    protected Segmentpool segmentpool;
    protected List<Segmentform> segmentform;
    @XmlAttribute(name = "position", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String position;
    @XmlAttribute(name = "segmentid", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String segmentid;
    @XmlAttribute(name = "itemselection", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String itemselection;

    /**
     * Gets the value of the segmentblueprint property.
     * 
     * @return
     *     possible object is
     *     {@link Segmentblueprint }
     *     
     */
    public Segmentblueprint getSegmentblueprint() {
        return segmentblueprint;
    }

    /**
     * Sets the value of the segmentblueprint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Segmentblueprint }
     *     
     */
    public void setSegmentblueprint(Segmentblueprint value) {
        this.segmentblueprint = value;
    }

    /**
     * Gets the value of the itemselector property.
     * 
     * @return
     *     possible object is
     *     {@link Itemselector }
     *     
     */
    public Itemselector getItemselector() {
        return itemselector;
    }

    /**
     * Sets the value of the itemselector property.
     * 
     * @param value
     *     allowed object is
     *     {@link Itemselector }
     *     
     */
    public void setItemselector(Itemselector value) {
        this.itemselector = value;
    }

    /**
     * Gets the value of the segmentpool property.
     * 
     * @return
     *     possible object is
     *     {@link Segmentpool }
     *     
     */
    public Segmentpool getSegmentpool() {
        return segmentpool;
    }

    /**
     * Sets the value of the segmentpool property.
     * 
     * @param value
     *     allowed object is
     *     {@link Segmentpool }
     *     
     */
    public void setSegmentpool(Segmentpool value) {
        this.segmentpool = value;
    }

    /**
     * Gets the value of the segmentform property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the segmentform property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSegmentform().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Segmentform }
     * 
     * 
     */
    public List<Segmentform> getSegmentform() {
        if (segmentform == null) {
            segmentform = new ArrayList<Segmentform>();
        }
        return this.segmentform;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the segmentid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentid() {
        return segmentid;
    }

    /**
     * Sets the value of the segmentid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentid(String value) {
        this.segmentid = value;
    }

    /**
     * Gets the value of the itemselection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemselection() {
        return itemselection;
    }

    /**
     * Sets the value of the itemselection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemselection(String value) {
        this.itemselection = value;
    }

}
