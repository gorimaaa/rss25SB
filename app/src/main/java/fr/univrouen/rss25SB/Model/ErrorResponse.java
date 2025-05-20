package fr.univrouen.rss25SB.Model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {
    @XmlElement
    private Long id;
    @XmlElement
    private String status;

    public ErrorResponse() {}
    public ErrorResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}