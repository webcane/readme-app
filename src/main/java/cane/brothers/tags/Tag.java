package cane.brothers.tags;

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
@Table(name = "TAG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long id;

    @Column
    private String value;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
