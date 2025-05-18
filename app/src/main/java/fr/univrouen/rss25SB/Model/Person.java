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
    @XmlElement(required = true)
    private String name;

    @XmlElement
    private String email;

    @XmlElement
    private String uri;

    @XmlTransient
    private String role; // à remplir lors du mapping (author/contributor)
}