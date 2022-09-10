package cane.brothers.tags;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author mniedre
 */
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository repo;

    @InjectMocks
    private TagServiceDefault svc;

    @Test
    void test_findAll() {
        DummyTag testTag = new DummyTag();
        List<TagView> tags = new ArrayList<>();
        tags.add(testTag);

        Mockito.when(repo.findAll(TagView.class)).thenReturn(tags);
        List<TagView> allTags = svc.findAll();

        assertThat(allTags).isNotNull().hasSize(1);
        assertThat(allTags.get(0).getValue()).isSameAs(testTag.getValue());
    }

    @Test
    @Disabled("tbi")
    void test_findTag() {
        Assertions.fail("tbi");
    }

}