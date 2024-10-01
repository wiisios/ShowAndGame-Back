package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;

import java.util.List;

public class GetGameForExploreDto {

    private long id;
    private String name;
    private String backgroundImage;
    private List<Tag> tags;

    public GetGameForExploreDto(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.backgroundImage = game.getProfileImage();
        this.tags = game.getTags();
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

    public String getProfileImage() {
        return backgroundImage;
    }

    public void setProfileImage(String profileImage) {
        this.backgroundImage = profileImage;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
