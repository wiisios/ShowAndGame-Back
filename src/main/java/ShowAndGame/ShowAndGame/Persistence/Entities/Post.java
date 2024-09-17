package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public abstract class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @Column
    private String description;

    @Column
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "game_id")
    private Game game;

    public Post() {
    }
    public Post(String description, User user, Game game, LocalDate date) {
        this.description = description;
        this.user = user;
        this.game = game;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user ;
    }

    public void setUser(User user) {
        this.user = user ;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
