package fr.univrouen.rss25SB.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private FeedEntity feed;

    @Column(nullable = false)
    private String guid;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    private List<String> categories; // Stocke les "term" des catégories

    private String published;
    private String updated;

    @Embedded
    private ImageEmbeddable image;

    @Embedded
    private ContentEmbeddable content;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "role = 'author'")
    private List<PersonEntity> authors;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "role = 'contributor'")
    private List<PersonEntity> contributors;


    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageEmbeddable {
        @Column(name = "image_type")
        private String type;
        private String href;
        private String alt;
        private Integer length;
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentEmbeddable {
        @Column(length = 4096)
        private String value;
        @Column(name = "content_type")
        private String type;
        private String src;
    }

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String email;
        private String uri;

        @ManyToOne
        @JoinColumn(name = "item_id")
        private ItemEntity item;

        private String role; // "author" ou "contributor"
    }
}

