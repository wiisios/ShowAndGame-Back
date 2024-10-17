package ShowAndGame.ShowAndGame.Persistence.Dto.GameDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;

import java.util.List;

public class GetGamesForDeveloperDto {

    private Long id;
    private String profileImage;
    private String backgroundImage;
    private String name;
    private String description;
    private List<Tag> tags;

    public GetGamesForDeveloperDto(Game game) {
        this.id = game.getId();
        this.profileImage = game.getProfileImage();
        this.backgroundImage = game.getBackgroundImage();
        this.name = game.getName();
        this.description = game.getDescription();
        this.tags = game.getTags();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}