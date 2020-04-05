package cane.brothers.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cane
 */
@Service
public class TagService {

    @Autowired
    private TagRepository repo;

    public List<TagView> findAll() {
        return repo.findAll(TagView.class);
    }
}
