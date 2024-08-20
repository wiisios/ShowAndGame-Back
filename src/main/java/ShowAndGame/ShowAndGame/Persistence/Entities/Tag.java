package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Game> games;

    public Tag() {
    }

    public Tag(String name, List<Game> games) {
        this.name = name;
        this.games = games;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
