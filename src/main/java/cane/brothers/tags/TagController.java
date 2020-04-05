package cane.brothers.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cane
 */
@RestController
public class TagController {

    private TagService svc;

    @Autowired
    public TagController(TagService svc) {
        this.svc = svc;
    }

    @RequestMapping("/tags")
    public List<TagView> getAllTags() {
        return svc.findAll();
    }
}
