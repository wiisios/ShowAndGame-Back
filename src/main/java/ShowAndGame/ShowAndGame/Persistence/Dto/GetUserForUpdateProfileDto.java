package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.*;

public class GetUserForUpdateProfileDto {

    private Long Id;
    private String profileImage;
    private String backgroundImage;

    public GetUserForUpdateProfileDto(User user) {
        this.Id = user.getId();
        this.profileImage = user.getProfileImage();
        this.backgroundImage = user.getBackgroundImage();
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

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}