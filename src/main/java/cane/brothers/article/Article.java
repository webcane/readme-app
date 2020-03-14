package cane.brothers.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cane
 */
@Entity
@Table(name = "ARTICLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ART_ID")
    private Long id;

    @Column
    private String url;

    @Column(name = "DESCR")
    private String description;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
