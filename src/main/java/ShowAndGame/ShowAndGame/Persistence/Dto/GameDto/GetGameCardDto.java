package ShowAndGame.ShowAndGame.Persistence.Dto.GameDto;

import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.GetTagDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;

import java.util.List;
import java.util.stream.Collectors;

public class GetGameCardDto {

    private long id;
    private String name;
    private String backgroundImage;
    private List<GetTagDto> tags;

    public GetGameCardDto(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.backgroundImage = game.getBackgroundImage();
        this.tags = game.getTags().stream().map(GetTagDto::new).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage  (String profileImage) {
        this.backgroundImage = profileImage;
    }

    public List<GetTagDto> getTags() {
        return tags;
    }

    public void setTags(List<GetTagDto> tags) {
        this.tags = tags;
    }
}
