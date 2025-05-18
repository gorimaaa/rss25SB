package fr.univrouen.rss25SB.Controllers;

import fr.univrouen.rss25SB.Entity.FeedEntity;
import fr.univrouen.rss25SB.Mapper.FeedMapper;
import fr.univrouen.rss25SB.Model.Feed;
import fr.univrouen.rss25SB.Model.InsertResponse;
import fr.univrouen.rss25SB.Model.TestRSS;
import fr.univrouen.rss25SB.Repository.FeedRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;

@Controller
@RequestMapping("/rss25SB")
public class AppController {
    private final FeedRepository feedRepository;

    public AppController(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public InsertResponse insertFeed(@RequestBody String xml) {
        try {
            // Charger le schéma XSD
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            var schema = sf.newSchema(new ClassPathResource("rss25sb.xsd").getFile());

            // Créer le contexte JAXB
            JAXBContext context = JAXBContext.newInstance(Feed.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema); // Active la validation

            // Désérialiser et valider
            Feed feed = (Feed) unmarshaller.unmarshal(new java.io.StringReader(xml));

            // Vérifier unicité
            if (feedRepository.findByTitleAndPubDate(feed.getTitle(), feed.getPubDate()).isPresent()) {
                System.out.println("Unicité erreur");
                return new InsertResponse("ERROR");
            }

            // Mapper Feed -> FeedEntity via FeedMapper
            FeedEntity entity = FeedMapper.mapFeedToEntity(feed);

            // Sauvegarder
            FeedEntity saved = feedRepository.save(entity);
            return new InsertResponse(saved.getId(), "INSERTED");
        } catch (Exception e) {
            System.out.println("Erreur de validation ou de désérialisation : " + e.getMessage());
            return new InsertResponse("ERROR");
        }
    }

    @PostMapping(value = "/postrss", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String postRSS() {
        try {
            TestRSS rss = new TestRSS();
            return rss.loadFileXML();
        }catch(Exception e) {
            return "An error happened : " + e.getMessage();
        }
    }


}