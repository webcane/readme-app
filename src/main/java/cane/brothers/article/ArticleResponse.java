package cane.brothers.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author mniedre
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

    private int total;

    private List<ArticleView> articles;
}
