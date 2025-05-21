package fr.univrouen.rss25SB.Model;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Image {
    @XmlAttribute
    private String type;

    @XmlAttribute(required = true)
    private String href;

    @XmlAttribute(required = true)
    private String alt;

    @XmlAttribute
    private Integer length;
}