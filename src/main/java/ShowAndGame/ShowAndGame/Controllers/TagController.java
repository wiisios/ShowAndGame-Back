package ShowAndGame.ShowAndGame.Controllers;

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
    public ResponseEntity<List<Tag>> getAllTags() {return  ResponseEntity.ok(tagService.searchAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {

        Tag tag = tagService.search(id).orElse((null));

        return ResponseEntity.ok(tag);
    }


    @PostMapping()
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){

        return ResponseEntity.ok(tagService.create(tag));
    }

    @PutMapping()
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag){
        ResponseEntity<Tag> response = null;

        if (tag.getId() != null && tagService.search(tag.getId()).isPresent())
            response = ResponseEntity.ok(tagService.update(tag));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (tagService.search(id).isPresent()){
            tagService.delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
