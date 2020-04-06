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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID", unique = true)
    private Long id;
    @Column(unique = true)
    private String value;
    //@JsonIgnore
    //@JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    /**
     * Constructor
     *
     * @param value
     */
    public Tag(String value) {
        this.value = value;
    }

    /**
     * Constructor
     *
     * @param value
     * @param article
     */
    public Tag(String value, Article article) {
        this.value = value;
        this.article = article;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
