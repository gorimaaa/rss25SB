package fr.univrouen.rss25SB.Model;

import fr.univrouen.rss25SB.Model.Item;
import fr.univrouen.rss25SB.Model.Link;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "feed", namespace = "http://univ.fr/rss25")
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {
    @XmlElement(namespace = "http://univ.fr/rss25", required = true)
    private String title;

    @XmlElement(namespace = "http://univ.fr/rss25", required = true)
    private String pubDate;

    @XmlElement(namespace = "http://univ.fr/rss25", required = true)
    private String copyright;

    @XmlElement(name = "link", namespace = "http://univ.fr/rss25", required = true)
    private List<Link> links;

    @XmlElement(name = "item", namespace = "http://univ.fr/rss25", required = true)
    private List<Item> items;

    @XmlAttribute(name = "lang", required = true)
    private String lang;

    @XmlAttribute(name = "version", required = true)
    private String version;
}