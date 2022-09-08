package cane.brothers.article;

import cane.brothers.tags.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
public class Article implements Serializable, Persistable<Long> {

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
  @ManyToMany(fetch = FetchType.EAGER, targetEntity = Tag.class,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinTable(name = "ARTICLE_TAG",
      joinColumns = {@JoinColumn(name = "FK_ART_ID")},
      inverseJoinColumns = {@JoinColumn(name = "FK_TAG_ID")},
      uniqueConstraints = {@UniqueConstraint(columnNames = {"FK_ART_ID", "FK_TAG_ID"})})
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
    tag.getArticles().add(this);
  }

  public void removeTag(Tag tag) {
    tags.remove(tag);
    tag.getArticles().remove(this);
  }

  @Override
  public boolean isNew() {
    return id == null;
  }
}
