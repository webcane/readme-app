package cane.brothers.article;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author mniedre
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ArticleControllerTest {

    @Mock
    private ArticleService svc;

    @InjectMocks
    private ArticleController api;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(api)
                .build();
    }

    @Test
    void test_when2ArticlesExist_thenHttp200_and2ArticlesReturned() throws Exception {
        Mockito.doReturn(DummyArticle.get2Articles()).when(svc).findAll();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/articles"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url", Matchers.is("http://www.example.com/dummy/")));
    }

    @Test
    void test_whenFilterArticlesByExistedTag_thenHttp200_andArticleReturned() throws Exception {
        final String tagName = "test";
        Collection<String> tagsToSearch = new LinkedHashSet<>();
        tagsToSearch.add(tagName);
        List<ArticleView> testArticles = new ArrayList<>();
        testArticles.add(DummyArticle.getArticle(true));
        Mockito.doReturn(testArticles).when(svc).findByTagNames(tagsToSearch);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/articles/findBy?tags=" + tagName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tags").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tags[0].value", Matchers.is(tagName)));
    }

    @Test
    void test_whenFilterArticlesByNotExistedTag_thenHttp404() throws Exception {
        final String tagName = "asd";
        Collection<String> tagsToSearch = new LinkedHashSet<>();
        tagsToSearch.add(tagName);
        List<ArticleView> testArticles = new ArrayList<>();
        Mockito.doReturn(testArticles).when(svc).findByTagNames(tagsToSearch);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/articles/findBy?tags=" + tagName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void test_whenFilterArticlesByEmptyTag_thenHttp400() throws Exception {
        final String tagName = "";

        this.mockMvc.perform(MockMvcRequestBuilders.get("/articles/findBy?tag=" + tagName))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }
}