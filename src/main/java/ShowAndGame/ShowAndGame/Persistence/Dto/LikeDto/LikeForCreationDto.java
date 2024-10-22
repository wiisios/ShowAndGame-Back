package ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto;

public class LikeForCreationDto {
    private Long userId;
    private Long feedPostId;

    public LikeForCreationDto(Long userId, Long feedPostId) {
        this.userId = userId;
        this.feedPostId = feedPostId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFeedPostId() {
        return feedPostId;
    }

    public void setFeedPostId(Long feedPostId) {
        this.feedPostId = feedPostId;
    }
}
