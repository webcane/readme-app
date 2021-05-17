package cane.brothers.tags;

/**
 * @author mniedre
 */
public class DummyTag implements TagView {

    private String value = "test";

    public DummyTag() {
    }

    public DummyTag(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
