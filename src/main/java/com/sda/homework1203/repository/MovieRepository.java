package com.sda.homework1203.repository;

import com.sda.homework1203.model.Movie;
import com.sda.homework1203.model.Rating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByTitleIgnoreCase(String title, Pageable pageable);
    List<Movie> findAllByDirectorIgnoreCase(String director, Pageable pageable);
    List<Movie> findAllByGenreIgnoreCase(String genre, Pageable pageable);
    List<Movie> findAllByReleaseDateBetween(LocalDate from, LocalDate to, Pageable pageable);
    List<Movie> findAllByRatingIgnoreCase(Rating rating, Pageable pageable);
    Optional<Movie> findByTitleAndDirector (String title, String director);
}
