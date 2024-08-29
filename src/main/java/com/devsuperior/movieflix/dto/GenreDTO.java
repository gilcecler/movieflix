package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Genre;

public class GenreDTO {

    public GenreDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDTO(Genre entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }


    private Long id;
    private String name;

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
}
