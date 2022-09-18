package cane.brothers.tags;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagFormToEntityConverter implements Converter<TagForm, TagEntity> {

  @Override
  public TagEntity convert(TagForm source) {
    TagEntity t = new TagEntity();
    t.setValue(source.getValue());
    return t;
  }
}
