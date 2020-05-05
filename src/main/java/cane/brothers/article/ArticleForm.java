package cane.brothers.article;

import cane.brothers.tags.TagForm;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author mniedre
 */
@Data
@NoArgsConstructor
public class ArticleForm {

    @NotEmpty
    private String url;

    private String title;

    private String preamble;

    private Set<TagForm> tags;
}
