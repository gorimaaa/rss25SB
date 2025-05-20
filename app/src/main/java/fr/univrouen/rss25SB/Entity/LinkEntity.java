package fr.univrouen.rss25SB.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rel;
    private String type;
    private String href;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    private FeedEntity feed;
}