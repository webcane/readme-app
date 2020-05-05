package cane.brothers.article;

import cane.brothers.tags.Tag;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cane
 */
@Entity
@Table(name = "ARTICLE")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Article implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ART_ID", unique = true)
    private Long id;

    @Column(name = "URL", unique = true, nullable = false)
    private String url;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PREAMBLE", length = 1000)
    private String preamble;

    //@JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    /**
     * Constructor
     *
     * @param url
     */
    public Article(String url) {
        this.url = url;
    }

    /**
     * Constructor
     *
     * @param url
     * @param title
     */
    public Article(String url, String title) {
        this.url = url;
        this.title = title;
    }

    /**
     * Constructor
     *
     * @param url
     * @param title
     * @param preamble
     */
    public Article(String url, String title, String preamble) {
        this.url = url;
        this.title = title;
        this.preamble = preamble;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.setArticle(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.setArticle(null);
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
