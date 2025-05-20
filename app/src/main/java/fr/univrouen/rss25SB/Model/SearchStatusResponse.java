package fr.univrouen.rss25SB.Model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchStatusResponse {
    private String status;
    public SearchStatusResponse() {}
    public SearchStatusResponse(String status) { this.status = status; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}