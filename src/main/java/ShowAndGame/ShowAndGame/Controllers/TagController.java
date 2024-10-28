package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.GetTagDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.TagForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.TagForUpdateDto;
import ShowAndGame.ShowAndGame.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping()
    public ResponseEntity<List<GetTagDto>> getAllTags() {
        return  ResponseEntity.ok(tagService.GetAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTagDto> getTag(@PathVariable Long id) {
        GetTagDto tag = tagService.GetById(id);

        if (tag != null) {
            return ResponseEntity.ok(tag);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<GetTagDto>> getTagsByGameId(@PathVariable Long gameId) {
        return ResponseEntity.ok(tagService.GetTagsByGameId(gameId));
    }

    @PostMapping()
    public ResponseEntity<TagForCreationDto> createTag(@RequestBody TagForCreationDto newTag) {
        tagService.Create(newTag);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagForUpdateDto> updateTag(@RequestBody TagForUpdateDto tagToUpdate, @PathVariable Long tagId) {
        ResponseEntity<TagForUpdateDto> response = null;

        if (tagId != null && tagService.GetById(tagId) != null) {
            tagService.Update(tagToUpdate, tagId);
            response = ResponseEntity.status(HttpStatus.OK).body(tagToUpdate);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        ResponseEntity<String> response = null;

        if (tagService.GetById(id) != null) {
            tagService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
