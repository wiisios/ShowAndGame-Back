package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.GetTagDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.TagForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;
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
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        Tag tag = tagService.GetById(id).orElse(null);

        if (tag != null){
            return ResponseEntity.ok(tag);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<GetTagDto>> getTagsByGameId(@PathVariable Long gameId){

        return ResponseEntity.ok(tagService.GetTagsByGameId(gameId));
    }

    @PostMapping()
    public ResponseEntity<TagForCreationAndUpdateDto> createTag(@RequestBody TagForCreationAndUpdateDto newTag){

        tagService.Create(newTag);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
    }

    @PutMapping()
    public ResponseEntity<GetTagDto> updateTag(@RequestBody GetTagDto tagToUpdate){
        ResponseEntity<GetTagDto> response = null;

        if (tagToUpdate.getId() != null && tagService.GetById(tagToUpdate.getId()).isPresent()) {
            tagService.Update(tagToUpdate);
            response = ResponseEntity.status(HttpStatus.OK).body(tagToUpdate);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (tagService.GetById(id).isPresent()){
            tagService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
