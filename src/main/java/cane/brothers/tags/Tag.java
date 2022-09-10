package cane.brothers.tags;

import cane.brothers.article.ArticleEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

/**
 * Created by cane
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "TAG")
public class Tag implements Serializable, Persistable<Long> {

  private static final long serialVersionUID = 1;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_gen")
  @SequenceGenerator(name = "tag_gen", sequenceName = "tag_seq")
  @Column(name = "TAG_ID", unique = true, updatable = false, nullable = false)
  private Long id;

  @Column(unique = true)
  private String value;

  @JsonIgnoreProperties("tags")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags", targetEntity = ArticleEntity.class)
  private Set<ArticleEntity> articles = new HashSet<>();

  /**
   * Constructor
   *
   * @param value tag name
   */
  public Tag(String value) {
    this.value = value;
  }

  public void addArticle(ArticleEntity article) {
    articles.add(article);
    article.getTags().add(this);
  }

  public void removeArticle(ArticleEntity article) {
    articles.remove(article);
    article.getTags().remove(this);
  }

  @Override
  public boolean isNew() {
    return id == null;
  }
}
