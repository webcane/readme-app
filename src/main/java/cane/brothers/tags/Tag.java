package cane.brothers.tags;

import cane.brothers.article.Article;
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
@Table(name = "TAG")
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tag implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_gen")
    @SequenceGenerator(name = "tag_gen", sequenceName = "tag_seq")
    @Column(name = "TAG_ID", unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String value;

    //@JsonIgnore
    //@JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags", targetEntity = Article.class)
    private Set<Article> articles = new HashSet<>();

    /**
     * Constructor
     *
     * @param value
     */
    public Tag(String value) {
        this.value = value;
    }

    public void addArticle(Article article) {
        articles.add(article);
        article.getTags().add(this);
    }

    public void removeArticle(Article article) {
        articles.remove(article);
        article.getTags().remove(this);
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
