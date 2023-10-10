package cane.brothers.article;

import cane.brothers.tags.TagForm;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

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
