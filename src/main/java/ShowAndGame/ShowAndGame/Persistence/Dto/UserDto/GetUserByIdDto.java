package ShowAndGame.ShowAndGame.Persistence.Dto.UserDto;


import ShowAndGame.ShowAndGame.Persistence.Entities.*;

public class GetUserByIdDto {
    private Long id;
    private String userName;
    private String email;
    private String profileImage;
    private String backgroundImage;

    public GetUserByIdDto(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.backgroundImage = user.getBackgroundImage();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
