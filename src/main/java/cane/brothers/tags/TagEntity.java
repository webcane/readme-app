package cane.brothers.tags;

import cane.brothers.article.ArticleEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
public class TagEntity implements Serializable, Persistable<Long> {

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
  public TagEntity(String value) {
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
