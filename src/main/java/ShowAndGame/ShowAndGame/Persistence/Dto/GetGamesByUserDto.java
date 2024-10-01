package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;

public class GetGamesByUserDto {

    private Long id;
    private String backgroundImage;
    private String name;

    public GetGamesByUserDto(Game game) {
        this.id = game.getId();
        this.backgroundImage = game.getBackgroundImage();
        this.name = game.getName();
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
}
