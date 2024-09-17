package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;

public class GetTagDto {

    private Long id;
    private String name;
    private String color;

    public GetTagDto(Tag tag){
        this.id = tag.getId();
        this.name = tag.getName();
        this.color = tag.getColor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
