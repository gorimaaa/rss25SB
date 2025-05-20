package fr.univrouen.rss25SB.Model;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "deleteResponse")
public class DeleteResponse {
    public Long id;
    public String status;

    public DeleteResponse() {}
    public DeleteResponse(Long id, String status) {
        this.id = id;
        this.status = status;
    }
    public DeleteResponse(String status) {
        this.status = status;
    }
}