package ShowAndGame.ShowAndGame.Persistence.Dto.GameDto;

import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.GetTagDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;

import java.util.List;
import java.util.stream.Collectors;

public class GetGameDto {
    private Long id;
    private String profileImage;
    private String backgroundImage;
    private String name;
    private String description;
    private Integer rating;
    private boolean isFollowed;
    private List<GetTagDto> tags;

    public GetGameDto(Game game, boolean isFollowed) {
        this.id = game.getId();
        this.name = game.getName();
        this.description = game.getDescription();
        this.profileImage = game.getProfileImage();
        this.backgroundImage = game.getBackgroundImage();
        this.tags = game.getTags().stream().map(GetTagDto::new).collect(Collectors.toList());
        this.rating = game.getRating();
        this.isFollowed = isFollowed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }

    public List<GetTagDto> getTags() {
        return tags;
    }

    public void setTags(List<GetTagDto> tags) {
        this.tags = tags;
    }
}
