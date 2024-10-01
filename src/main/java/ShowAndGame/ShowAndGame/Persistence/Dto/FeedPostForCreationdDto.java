package ShowAndGame.ShowAndGame.Persistence.Dto;

public class FeedPostForCreationdDto {
    private String description;
    private String image;

    public FeedPostForCreationdDto(String description, String image) {
        this.description = description;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
