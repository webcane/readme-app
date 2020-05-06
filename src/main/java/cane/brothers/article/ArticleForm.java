package cane.brothers.article;

import cane.brothers.tags.TagForm;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author mniedre
 */
@Data
@NoArgsConstructor
public class ArticleForm {

    @NotEmpty
    @Size(max = 255)
    private String url;

    @Size(max = 255)
    private String title;

    @Size(max = 1000)
    private String preamble;

    private Set<TagForm> tags;
}
