package ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;

import java.time.LocalDate;

public class GetFeedPostForUpdateDto {
    private String description;
    private String image;

    public GetFeedPostForUpdateDto(FeedPost feedPost) {

        this.description = feedPost.getDescription();
        this.image = feedPost.getImage();

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

