package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.GetTagDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.TagForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.TagForUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;
import ShowAndGame.ShowAndGame.Persistence.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public GetTagDto GetById(Long id) {
        Optional<Tag> currentTag = tagRepository.findById(id);
        Tag tag = null;

        if (currentTag.isPresent())
            tag = currentTag.get();

        return new GetTagDto(tag);
    }

    public List<GetTagDto> GetAll() {
        List<Tag> allTags = tagRepository.findAll();

        return allTags.stream()
                .map(GetTagDto::new)
                .collect(Collectors.toList());
    }

    public List<GetTagDto> GetTagsByGameId(Long gameId) {
        List<Tag> tagsByGame = tagRepository.findByGames_Id(gameId);

        //returns List of GetTagDto to display on each game page and update form
        return tagsByGame.stream()
                .map(GetTagDto::new)
                .collect(Collectors.toList());
    }

    public void Create(TagForCreationDto newTag) {
        Tag tagToCreate = new Tag();

        tagToCreate.setName(newTag.getName());
        tagToCreate.setColor(newTag.getColor());
        tagToCreate.setGames(new ArrayList<>());

        tagRepository.save(tagToCreate);
    }

    public void Update(TagForUpdateDto tagToUpdate, Long tagId) {
        Optional<Tag> currentTag = tagRepository.findById(tagId);
        Tag tag = null;

        if(currentTag.isPresent()) {
            tag = currentTag.get();
            tag.setName(tagToUpdate.getName());
            tag.setColor(tagToUpdate.getColor());
            tagRepository.save(tag);
        }
    }

    public void Delete(Long id) {
        tagRepository.deleteById(id);
    }
}
