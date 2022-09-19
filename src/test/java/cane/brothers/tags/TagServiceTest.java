package cane.brothers.tags;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

/**
 * @author mniedre
 */
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository repo;

    @Mock
    private TagFormToEntityConverter tagConverter;

    @InjectMocks
    private TagService svc = new TagServiceImpl();

    @Test
    @DisplayName("findAll test")
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
    @DisplayName("findTag positive test")
    void test_findTag() {
        TagForm testTag = new TagForm();
        testTag.setValue("test");

        TagEntity tagEntity = new TagEntity("test");
        TagEntity tagRepoEntity = new TagEntity("test");
        tagRepoEntity.setId(1L);

        Mockito.when(repo.findOne(Example.of(tagEntity))).thenReturn(Optional.of(tagRepoEntity));
        Mockito.when(tagConverter.convert(testTag)).thenReturn(tagEntity);

        TagEntity result = svc.findTag(testTag);

        assertThat(result).isNotNull();
        assertThat(result.getValue()).isSameAs(testTag.getValue());
        assertThat(result.getId()).isSameAs(tagRepoEntity.getId());
    }

    @Test
    @DisplayName("findTag negative test")
    void test_findTag2() {
        TagForm testTag = new TagForm();
        testTag.setValue("test2");

        TagEntity tagEntity = new TagEntity("test2");

        Mockito.when(repo.findOne(Example.of(tagEntity))).thenReturn(Optional.empty());
        Mockito.when(tagConverter.convert(testTag)).thenReturn(tagEntity);

        TagEntity result = svc.findTag(testTag);

        assertThat(result).isNotNull();
        assertThat(result.getValue()).isSameAs(testTag.getValue());
        assertThat(result.getId()).isNull();
    }

}