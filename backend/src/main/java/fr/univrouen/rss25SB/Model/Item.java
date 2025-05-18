package fr.univrouen.rss25SB.Model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Item {
    @XmlAttribute private String guid;
    @XmlElement private String title;
    @XmlElement private String published;
    public Item(String guid, String title, String published) {
        super();
        this.guid = guid;
        this.title = title;
        this.published = published;
    }

    public Item() {

    }

    @Override public String toString() {
        return ("Article : " + title + "\n(" + guid + ") Le = " + published );
    }
}

