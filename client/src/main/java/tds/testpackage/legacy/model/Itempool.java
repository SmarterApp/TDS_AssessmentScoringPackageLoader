
package tds.testpackage.legacy.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{}passage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}testitem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "passage",
    "testitem"
})
@XmlRootElement(name = "itempool")
public class Itempool {

    protected List<Passage> passage;
    protected List<Testitem> testitem;

    /**
     * Gets the value of the passage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the passage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPassage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Passage }
     * 
     * 
     */
    public List<Passage> getPassage() {
        if (passage == null) {
            passage = new ArrayList<Passage>();
        }
        return this.passage;
    }

    /**
     * Gets the value of the testitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the testitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTestitem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Testitem }
     * 
     * 
     */
    public List<Testitem> getTestitem() {
        if (testitem == null) {
            testitem = new ArrayList<Testitem>();
        }
        return this.testitem;
    }

}
