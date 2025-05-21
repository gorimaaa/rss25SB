package fr.univrouen.rss25SB.Mapper;

import fr.univrouen.rss25SB.Entity.FeedEntity;
import fr.univrouen.rss25SB.Entity.ItemEntity;
import fr.univrouen.rss25SB.Entity.LinkEntity;
import fr.univrouen.rss25SB.Model.*;

import java.util.ArrayList;
import java.util.List;

public class FeedMapper {
    public static FeedEntity mapFeedToEntity(Feed feed) {
        FeedEntity entity = new FeedEntity();
        entity.setTitle(feed.getTitle());
        entity.setPubDate(feed.getPubDate());
        entity.setCopyright(feed.getCopyright());
        entity.setLang(feed.getLang());
        entity.setVersion(feed.getVersion());

        // Liens
        List<LinkEntity> linkEntities = new ArrayList<>();
        if (feed.getLinks() != null) {
            for (Link link : feed.getLinks()) {
                LinkEntity linkEntity = new LinkEntity();
                linkEntity.setRel(link.getRel());
                linkEntity.setType(link.getType());
                linkEntity.setHref(link.getHref());
                linkEntity.setFeed(entity);
                linkEntities.add(linkEntity);
            }
        }
        entity.setLinks(linkEntities);


        // Items
        List<ItemEntity> itemEntities = new ArrayList<>();
        if (feed.getItems() != null) {
            for (Item item : feed.getItems()) {
                ItemEntity itemEntity = new ItemEntity();
                itemEntity.setFeed(entity);

                if (item.getGuid() == null || item.getGuid().isEmpty()) {
                    throw new IllegalArgumentException("L'élément <guid> est manquant ou vide pour un item.");
                }
                itemEntity.setGuid(item.getGuid());
                itemEntity.setTitle(item.getTitle());

                // Catégories
                List<String> categories = new ArrayList<>();
                if (item.getCategories() != null) {
                    for (Category cat : item.getCategories()) {
                        categories.add(cat.getTerm());
                    }
                }
                itemEntity.setCategories(categories);

                // Dates
                itemEntity.setPublished(item.getPublished());
                itemEntity.setUpdated(item.getUpdated());

                // Image
                if (item.getImage() != null) {
                    ItemEntity.ImageEmbeddable img = new ItemEntity.ImageEmbeddable();
                    img.setType(item.getImage().getType());
                    img.setHref(item.getImage().getHref());
                    img.setAlt(item.getImage().getAlt());
                    img.setLength(item.getImage().getLength());
                    itemEntity.setImage(img);
                }

                // Content
                if (item.getContent() != null) {
                    ItemEntity.ContentEmbeddable content = new ItemEntity.ContentEmbeddable();
                    content.setValue(item.getContent().getValue());
                    content.setType(item.getContent().getType());
                    content.setSrc(item.getContent().getSrc());
                    itemEntity.setContent(content);
                }

                // Dans FeedMapper.java, dans la boucle sur les items

                // Auteurs
                List<ItemEntity.PersonEntity> authors = new ArrayList<>();
                if (item.getAuthors() != null) {
                    for (Person p : item.getAuthors()) {
                        ItemEntity.PersonEntity personEntity = new ItemEntity.PersonEntity();
                        personEntity.setName(p.getName());
                        personEntity.setEmail(p.getEmail());
                        personEntity.setUri(p.getUri());
                        personEntity.setRole("author");
                        personEntity.setItem(itemEntity);
                        authors.add(personEntity);
                    }
                }
                itemEntity.setAuthors(authors);

                // Contributeurs
                List<ItemEntity.PersonEntity> contributors = new ArrayList<>();
                if (item.getContributors() != null) {
                    for (Person p : item.getContributors()) {
                        ItemEntity.PersonEntity personEntity = new ItemEntity.PersonEntity();
                        personEntity.setName(p.getName());
                        personEntity.setEmail(p.getEmail());
                        personEntity.setUri(p.getUri());
                        personEntity.setRole("contributor");
                        personEntity.setItem(itemEntity);
                        contributors.add(personEntity);
                    }
                }
                itemEntity.setContributors(contributors);

                itemEntities.add(itemEntity);
            }
        }
        entity.setItems(itemEntities);

        return entity;
    }
}