package cane.brothers.tags;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cane
 */
@RestController
@RequestMapping("/tags")
public class TagController implements TagApi {

  private TagService svc;

  @Autowired
  public TagController(TagService svc) {
    this.svc = svc;
  }

  @Override
  @GetMapping(produces = {"application/json"})
  public ResponseEntity<List<TagView>> getAllTags() {
    return ResponseEntity.ok(svc.findAll());
  }
}
