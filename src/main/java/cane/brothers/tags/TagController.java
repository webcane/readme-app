package cane.brothers.tags;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cane
 */
@RestController
@RequestMapping("/tags")
@Tag(name = "tag-controller", description = "The tags API")
public class TagController {

    private TagService svc;

    @Autowired
    public TagController(TagService svc) {
        this.svc = svc;
    }

    @GetMapping
    @Operation(summary = "get all tags", method = "GET", tags = {"article", "articles"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "There are all tags found",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TagView.class)))),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "401", description = "Authentication Failure",
                            content = @Content(schema = @Schema(hidden = true)))})
    public List<TagView> getAllTags() {
        return svc.findAll();
    }
}
