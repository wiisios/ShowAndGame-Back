package ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.UserLike;

public class GetLikeDto {
    private Long id;
    private boolean isLiked;

    public GetLikeDto(UserLike userLike) {
        this.id = userLike.getId();
        this.isLiked = userLike.isLiked();
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
