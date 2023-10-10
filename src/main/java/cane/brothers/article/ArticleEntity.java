package cane.brothers.article;

import cane.brothers.tags.TagEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "ARTICLE")
public class ArticleEntity implements Serializable, Persistable<Long> {

  private static final long serialVersionUID = 1;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_gen")
  @SequenceGenerator(name = "article_gen", sequenceName = "article_seq")
  @Column(name = "ART_ID", unique = true)
  private Long id;

  @Column(name = "URL", unique = true, nullable = false)
  private String url;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "PREAMBLE", length = 1000)
  private String preamble;

  @JsonIgnoreProperties("articles")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(fetch = FetchType.EAGER, targetEntity = TagEntity.class,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinTable(name = "ARTICLE_TAG",
      joinColumns = {@JoinColumn(name = "FK_ART_ID")},
      inverseJoinColumns = {@JoinColumn(name = "FK_TAG_ID")},
      uniqueConstraints = {@UniqueConstraint(columnNames = {"FK_ART_ID", "FK_TAG_ID"})})
  private Set<TagEntity> tags = new HashSet<>();

  /**
   * Constructor
   *
   * @param url the article url
   */
  public ArticleEntity(String url) {
    this.url = url;
  }

  /**
   * Constructor
   *
   * @param url the article url
   * @param title the article title
   */
  public ArticleEntity(String url, String title) {
    this.url = url;
    this.title = title;
  }

  /**
   * Constructor
   *
   * @param url the article url
   * @param title the article title
   * @param preamble the article preamble
   */
  public ArticleEntity(String url, String title, String preamble) {
    this.url = url;
    this.title = title;
    this.preamble = preamble;
  }

  public void addTag(TagEntity tag) {
    tags.add(tag);
    tag.getArticles().add(this);
  }

  public void removeTag(TagEntity tag) {
    tags.remove(tag);
    tag.getArticles().remove(this);
  }

  @Override
  public boolean isNew() {
    return id == null;
  }
}
