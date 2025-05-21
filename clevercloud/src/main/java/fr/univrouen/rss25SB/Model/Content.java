package fr.univrouen.rss25SB.Model;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Content {
    @XmlValue
    private String value;

    @XmlAttribute(required = true)
    private String type;

    @XmlAttribute
    private String src;
}