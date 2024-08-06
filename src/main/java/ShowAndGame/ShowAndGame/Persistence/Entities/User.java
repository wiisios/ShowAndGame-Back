package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false)
    private long id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String profileImage;
    @Column
    private String backgroundImage;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @OneToMany
    private List<Comment> comments;
    @ManyToMany(mappedBy = "followers")
    private List<Game> followedGames;
    @OneToMany(mappedBy = "owner")
    private List<Game> ownedGames;
    @OneToMany
    private List<Post> posts;


    public User(){
    };

    public User(String userName, String password, String email, String profileImage, String backgroundImage, UserRole userRole, List<Post> posts, List<Comment> comments, List<Game> ownedGames, List<Game> followedGames){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.userRole = userRole;
        this.comments = comments;
        this.posts = posts;
        this.ownedGames = ownedGames;
        this.followedGames = followedGames;
    }

    public User(String userName, String password, String email, String profileImage, String backgroundImage, UserRole userRole, List<Post> posts, List<Comment> comments, List<Game> followedGames){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.userRole = userRole;
        this.posts = posts;
        this.comments = comments;
        this.followedGames = followedGames;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name());

        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setProfileImage(String image) {
        this.profileImage = image;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String image) {
        this.backgroundImage = image;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Game> getFollowedGames() {
        return followedGames;
    }

    public void setFollowedGames(List<Game> followedGames) {
        this.followedGames = followedGames;
    }

    public List<Game> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(List<Game> ownedGames) {
        this.ownedGames = ownedGames;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
