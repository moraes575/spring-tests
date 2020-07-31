package com.moraes.springtests.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String director;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date releasedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "movie")
    private Set<MovieRentals> rentals;

    public Movie() {
    }

    public Movie(Long id, String title, String director, Date releasedDate, Category category, Set<MovieRentals> rentals) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releasedDate = releasedDate;
        this.category = category;
        this.rentals = rentals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<MovieRentals> getRentals() {
        return rentals;
    }

    public void setRentals(Set<MovieRentals> rentals) {
        this.rentals = rentals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getId().equals(movie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
