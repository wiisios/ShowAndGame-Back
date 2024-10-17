package ShowAndGame.ShowAndGame.Persistence.Dto.FollowDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Follow;

public class GetFollowDto {
    private Long id;
    private boolean isFollowed;

    public GetFollowDto(Follow follow) {
        this.id = follow.getId();
        this.isFollowed = follow.isFollowed();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }
}
