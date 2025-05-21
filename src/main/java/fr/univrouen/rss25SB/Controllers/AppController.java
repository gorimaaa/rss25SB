package fr.univrouen.rss25SB.Controllers;

import fr.univrouen.rss25SB.Entity.FeedEntity;
import fr.univrouen.rss25SB.Mapper.FeedMapper;
import fr.univrouen.rss25SB.Model.*;
import fr.univrouen.rss25SB.Repository.FeedRepository;
import fr.univrouen.rss25SB.Repository.ItemRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rss25SB")
public class AppController {
    private final FeedRepository feedRepository;
    private final ItemRepository itemRepository;

    public AppController(FeedRepository feedRepository,
                         ItemRepository itemRepository) {
        this.feedRepository = feedRepository;
        this.itemRepository = itemRepository;
    }

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public InsertResponse insertFeed(@RequestBody String xml) {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            var schema = sf.newSchema(new ClassPathResource("rss25sb.xsd").getFile());

            JAXBContext context = JAXBContext.newInstance(Feed.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema); 

            Feed feed = (Feed) unmarshaller.unmarshal(new java.io.StringReader(xml));

            if (feedRepository.findByTitleAndPubDate(feed.getTitle(), feed.getPubDate()).isPresent()) {
                System.out.println("Unicité erreur");
                return new InsertResponse("ERROR");
            }

            FeedEntity entity = FeedMapper.mapFeedToEntity(feed);

            FeedEntity saved = feedRepository.save(entity);
            return new InsertResponse(saved.getId(), "INSERTED");
        } catch (Exception e) {
            System.out.println("Erreur de validation ou de désérialisation : " + e.getMessage());
            return new InsertResponse("ERROR");
        }
    }

    @GetMapping(value = "/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ItemSummaryList getItemSummaries() {
        var items = itemRepository.findAll().stream()
                .map(item -> new ItemSummary(
                        item.getId(),
                        item.getPublished() != null ? item.getPublished() : item.getUpdated(),
                        item.getGuid()
                ))
                .toList();
        return new ItemSummaryList(items);
    }

    @GetMapping("/resume/html")
    public String getItemSummariesHtml(Model model) {
        var items = itemRepository.findAll().stream()
                .map(item -> new ItemSummary(
                        item.getId(),
                        item.getPublished() != null ? item.getPublished() : item.getUpdated(),
                        item.getGuid()
                ))
                .toList();
        model.addAttribute("items", items);
        return "resume";
    }

    @GetMapping(value = "/resume/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Object getItemDetailXml(@PathVariable Long id) {
        var opt = itemRepository.findById(id);
        if (opt.isPresent()) {
            var entity = opt.get();

            // Mapper les catégories
            List<Category> categories = null;
            if (entity.getCategories() != null && !entity.getCategories().isEmpty()) {
                categories = entity.getCategories().stream()
                        .map(Category::new)
                        .toList();
            }

            Image image = null;
            if (entity.getImage() != null) {
                var img = entity.getImage();
                image = new Image(img.getType(), img.getHref(), img.getAlt(), img.getLength());
            }

            Content content = null;
            if (entity.getContent() != null) {
                var c = entity.getContent();
                content = new Content(c.getValue(), c.getType(), c.getSrc());
            }

            List<Person> authors = null;
            if (entity.getAuthors() != null && !entity.getAuthors().isEmpty()) {
                authors = entity.getAuthors().stream()
                        .map(a -> new Person(a.getName(), a.getEmail(), a.getUri()))
                        .toList();
            }
            List<Person> contributors = null;
            if (entity.getContributors() != null && !entity.getContributors().isEmpty()) {
                contributors = entity.getContributors().stream()
                        .map(c -> new Person(c.getName(), c.getEmail(), c.getUri()))
                        .toList();
            }

            Item item = new Item(
                    entity.getGuid(),
                    entity.getTitle(),
                    categories,
                    entity.getPublished(),
                    entity.getUpdated(),
                    image,
                    content,
                    authors,
                    contributors
            );
            return item;
        } else {
            return new ErrorResponse(id, "ERROR");
        }
    }

    @GetMapping("/html/{id}")
    public String getItemDetailHtml(@PathVariable Long id, Model model) {
        var opt = itemRepository.findById(id);
        if (opt.isPresent()) {
            var entity = opt.get();

            List<String> categories = entity.getCategories();

            var image = entity.getImage();

            var content = entity.getContent();

            var authors = entity.getAuthors();

            var contributors = entity.getContributors();

            model.addAttribute("item", entity);
            model.addAttribute("categories", categories);
            model.addAttribute("publishedFormatted", formatDate(entity.getPublished()));
            model.addAttribute("updatedFormatted", formatDate(entity.getUpdated()));
            model.addAttribute("image", image);
            model.addAttribute("content", content);
            model.addAttribute("authors", authors);
            model.addAttribute("contributors", contributors);
            return "item_detail";
        } else {
            model.addAttribute("id", id);
            model.addAttribute("status", "ERROR");
            return "error";
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public DeleteResponse deleteItem(@PathVariable Long id) {
        var opt = itemRepository.findById(id);
        if (opt.isPresent()) {
            var item = opt.get();
            var feed = item.getFeed();
            itemRepository.deleteById(id);

            if (feed.getItems() != null && feed.getItems().isEmpty()) {
                feedRepository.delete(feed);
            }
            return new DeleteResponse(item.getId(), "DELETED");
        } else {
            return new DeleteResponse("ERROR");
        }
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Object searchItems(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String category) {
        try {
            var items = itemRepository.findAll().stream();

            if (date != null && !date.isEmpty()) {
                items = items.filter(item -> {
                    String itemDate = item.getPublished() != null ? item.getPublished() : item.getUpdated();
                    return itemDate != null && itemDate.compareTo(date) >= 0;
                });
            }

            if (category != null && !category.isEmpty()) {
                items = items.filter(item -> item.getCategories() != null && item.getCategories().contains(category));
            }

            var result = items
                    .map(item -> new ItemSummary(
                            item.getId(),
                            item.getPublished() != null ? item.getPublished() : item.getUpdated(),
                            item.getGuid()
                    ))
                    .toList();

            if (result.isEmpty()) {
                return new SearchStatusResponse("NONE");

            }
            return new ItemSummaryList(result);
        } catch (Exception e) {
            return new SearchStatusResponse("ERROR");

        }
    }


    private String formatDate(String isoDate) {
        if (isoDate == null) return null;
        OffsetDateTime odt = OffsetDateTime.parse(isoDate);
        return odt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }


}