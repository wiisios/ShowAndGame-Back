package ShowAndGame.ShowAndGame.Persistence.Dto.UserDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.UserRole;

public class GetUserForAdminUpdateDto {
    private String userName;
    private UserRole userRole;

    public GetUserForAdminUpdateDto() {
    }

    public GetUserForAdminUpdateDto( String userName, UserRole userRole) {
        this.userName = userName;
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
