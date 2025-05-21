// src/main/java/fr/univrouen/rss25SB/Model/Person.java
package fr.univrouen.rss25SB.Model;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    @XmlElement(namespace = "http://univ.fr/rss25")
    private String name;

    @XmlElement(namespace = "http://univ.fr/rss25")
    private String email;

    @XmlElement(namespace = "http://univ.fr/rss25")
    private String uri;

    // Le champ "role" n'est pas nécessaire pour la sérialisation XML
}