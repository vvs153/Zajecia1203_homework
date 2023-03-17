package com.sda.homework1203.model;

import com.sda.homework1203.errors.AddMovie;
import com.sda.homework1203.errors.UpdateMovie;
import com.sda.homework1203.validation.NullOrNotBlank;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(groups = AddMovie.class)
    @NullOrNotBlank(groups = {AddMovie.class, UpdateMovie.class})
    @Max(value =40,message = "Title is too long!", groups =  {AddMovie.class, UpdateMovie.class})
    private String title;
    @NotNull(groups = AddMovie.class)
    @NotEmpty(groups = {AddMovie.class, UpdateMovie.class})
    //@Pattern(regexp= "[a-zA-Z0-9] [a-zA-Z0-9]",message = "Must contain name and surname!")
    private String director;
    @NotNull(groups = AddMovie.class)
    private String genre;
    @PastOrPresent
    private LocalDate releaseDate;
    @NotNull(groups = AddMovie.class)
    private Rating rating;
    interface AddMovie {}
    interface UpdateMovie {}
}
