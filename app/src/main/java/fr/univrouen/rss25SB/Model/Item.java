package fr.univrouen.rss25SB.Model;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "item", namespace = "http://univ.fr/rss25")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    @XmlElement(name = "guid", namespace = "http://univ.fr/rss25", required = true)
    private String guid;

    @XmlElement(name = "title", namespace = "http://univ.fr/rss25", required = true)
    private String title;

    @XmlElement(name = "category", namespace = "http://univ.fr/rss25", required = true)
    private List<Category> categories;

    @XmlElement(name = "published", namespace = "http://univ.fr/rss25")
    private String published;

    @XmlElement(name = "updated", namespace = "http://univ.fr/rss25")
    private String updated;

    @XmlElement(name = "image", namespace = "http://univ.fr/rss25")
    private Image image;

    @XmlElement(name = "content", namespace = "http://univ.fr/rss25", required = true)
    private Content content;

    @XmlElement(name = "author", namespace = "http://univ.fr/rss25")
    private List<Person> authors;

    @XmlElement(name = "contributor", namespace = "http://univ.fr/rss25")
    private List<Person> contributors;
}