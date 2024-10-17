package ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Like;

public class GetLikeDto {
    private Long id;
    private boolean isLiked;

    public GetLikeDto(Like like) {
        this.id = like.getId();
        this.isLiked = like.isLiked();
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
