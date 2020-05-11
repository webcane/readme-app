package cane.brothers.article;

import cane.brothers.tags.Tag;
import cane.brothers.tags.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author mniedre
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ArticlesIT extends Assertions {

    @Value("${articleTagNewJson}")
    String articleTagNewJson;

    @Value("${articleNoTagJson}")
    String articleNoTagJson;

    @Value("${tagJson}")
    String tagJson;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository artRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_profile_test_emptyRepo() {
        assertThat(tagRepo.findAll()).isEmpty();
    }

    @Test
    void test_transactional_persist_articles_with_tag_pos() throws Exception {
        printStatus();

        mockMvc.perform(MockMvcRequestBuilders.post("/articles")
                .contentType("application/json")
                .content(articleTagNewJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertThat(artRepo.findAll()).hasSize(1);
        assertThat(tagRepo.findAll()).hasSize(1);
    }

    @Test
    void test_transactional_persist_same_articles_neg() throws Exception {
        printStatus();

        Article articleNoTag = objectMapper.readValue(articleNoTagJson, Article.class);
        artRepo.saveAndFlush(articleNoTag);

        mockMvc.perform(MockMvcRequestBuilders.post("/articles")
                .contentType("application/json")
                .content(articleTagNewJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertThat(artRepo.findAll()).hasSize(1);
    }

    @Test
    void test_transactional_persist_article_tag_exist() throws Exception {
        printStatus();

        log.info("save tag {}", tagJson);
        Tag tag = objectMapper.readValue(tagJson, Tag.class);
        tagRepo.saveAndFlush(tag);

        mockMvc.perform(MockMvcRequestBuilders.post("/articles")
                .contentType("application/json")
                .content(articleTagNewJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertThat(tagRepo.findAll()).hasSize(1);
    }

    private void printStatus() {
        log.info("articles {}", artRepo.findAll());
        log.info("tags {}", tagRepo.findAll());
    }
}
