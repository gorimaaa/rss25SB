// src/main/java/fr/univrouen/rss25SB/Model/InsertResponse.java
package fr.univrouen.rss25SB.Model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class InsertResponse {
    @XmlElement private Long id;
    @XmlElement private String status;

    public InsertResponse() {}
    public InsertResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }
    public InsertResponse(String status) {
        this.status = status;
    }
}