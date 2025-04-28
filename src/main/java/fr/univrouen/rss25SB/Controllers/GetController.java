package fr.univrouen.rss25SB.Controllers;

import fr.univrouen.rss25SB.Model.Item;
import fr.univrouen.rss25SB.Model.TestRSS;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class GetController {
    @GetMapping("/resume")
    public String getListRSSinXML() {
        return "Envoi de la liste des flux RSS";
    }

    @GetMapping("/guid")
    public String getRSSinXML(@RequestParam(value = "guid") String texte)
    {
        return ("Détail du flux RSS demandé : " + texte);
    }

    @GetMapping("/test")
    public String getTest(
            @RequestParam(value = "nb") String guidText,
            @RequestParam(value = "search") String titleText
            )
    {
        return ("Test : \n " + "guid = " + guidText + "\ntitre = " + titleText);
    }

    @RequestMapping(value = "/testpost", method = RequestMethod.POST,  consumes = "application/xml")
    public String postTest(@RequestBody String flux) {
        return ("<result><response>Message reçu : </response>" + flux + "</result>");
    }

    @PostMapping(value = "/postrss", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody public String postRSS() {
        try {
            TestRSS rss = new TestRSS();
            return rss.loadFileXML();
        }catch(Exception e) {
            return "An error happened : " + e.getMessage();
        }
    }

    @RequestMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody Item getXML() {
        Item it = new Item("12345678","Test item","2022-05-01T11:22:33");
        return it;
    }
}
