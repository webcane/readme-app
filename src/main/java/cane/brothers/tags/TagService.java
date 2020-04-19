package cane.brothers.tags;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cane
 */
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repo;

    public List<TagView> findAll() {
        return repo.findAll(TagView.class);
    }
}
