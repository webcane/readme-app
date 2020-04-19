package cane.brothers.tags;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mniedre
 */
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository repo;

    @InjectMocks
    private TagService svc;

    @Test
    public void test_findAll() {
        TagTest testTag = new TagTest();
        List<TagView> tags = new ArrayList<>();
        tags.add(testTag);

        Mockito.when(repo.findAll(TagView.class)).thenReturn(tags);
        List<TagView> allTags = svc.findAll();

        assertThat(allTags).isNotNull();
        assertThat(allTags.size()).isEqualTo(1);
        assertThat(allTags.get(0).getValue()).isSameAs(testTag.getValue());
    }

    class TagTest implements TagView {

        @Override
        public String getValue() {
            return "test";
        }
    }
}