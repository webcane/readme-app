package cane.brothers.tags;

/**
 * @author mniedre
 */
public record DummyTag(String value) implements TagView {

    public static final String DEFAULT_TAG = "test";

    public DummyTag() {
       this(DEFAULT_TAG);
    }

    @Override
    public String getValue() {
        return value;
    }
}
