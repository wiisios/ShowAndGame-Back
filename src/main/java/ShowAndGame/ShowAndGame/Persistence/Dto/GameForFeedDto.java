package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;

import java.util.List;

public class GameForFeedDto {

    private long id;
    private String name;
    private String profileImage;
    private List<Tag> tags;

    public GameForFeedDto(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.profileImage = game.getProfileImage();
        this.tags = game.getTags();
    }


}
