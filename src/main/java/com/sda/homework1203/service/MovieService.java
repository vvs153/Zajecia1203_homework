package com.sda.homework1203.service;

import com.sda.homework1203.exceptions.MovieAlreadyExistsException;
import com.sda.homework1203.exceptions.NoMovieFoundException;
import com.sda.homework1203.model.Movie;
import com.sda.homework1203.model.Rating;
import com.sda.homework1203.repository.MovieRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie) {
        String movieTitle = movie.getTitle();
        String movieDirector = movie.getDirector();
        checkIfRecipeNameIsUnique(movieTitle,movieDirector);
        return movieRepository.save(movie);
    }

    public Movie deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new NoMovieFoundException(id));
        movieRepository.delete(movie);
        return movie;
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(()->new NoMovieFoundException(id));
    }

    public Movie updateMovie(Long id, Movie movie) {
        Movie movieToUpdate = movieRepository.findById(id).orElseThrow(()-> new NoMovieFoundException(id));
        String movieName = movie.getTitle();
        if(movieName != null && !movieName.equals(movieToUpdate.getTitle())) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        String movieDirector = movie.getDirector();
        if(movieDirector != null && !movieDirector.equals(movieToUpdate.getDirector()) ){
            checkIfRecipeNameIsUnique(movieName,movieDirector);
            movieToUpdate.setDirector(movie.getDirector());
        }
        String movieGenre = movie.getGenre();
        if(movieGenre != null && !movieGenre.equals(movieToUpdate.getGenre())){
            movieToUpdate.setGenre(movie.getGenre());
        }
        LocalDate movieReleaseDate = movie.getReleaseDate();
        if(movieReleaseDate != null && !movieReleaseDate.equals(movieToUpdate.getReleaseDate())){
            movieToUpdate.setReleaseDate(movie.getReleaseDate());
        }
        Rating movieRating = movie.getRating();
        if(movieRating != null && !movieRating.equals(movieToUpdate.getRating())){
            movieToUpdate.setRating(movie.getRating());
        }
        return movieRepository.save(movieToUpdate);
    }
    public List<Movie> getRecipes(String title, String director, String genre, LocalDate from, LocalDate to, Rating rating, SortType sortType, Integer page, Integer size) {
        Pageable pageable = providePageable(page,size,sortType);
        if (title!=null){
            return movieRepository.findAllByTitleIgnoreCase(title, pageable);
        } else if (director!=null) {
            return movieRepository.findAllByDirectorIgnoreCase(director, pageable);
        } else if (genre!=null) {
            return movieRepository.findAllByGenreIgnoreCase(genre,pageable);
        } else if (from != null && to!=null) {
            return movieRepository.findAllByReleaseDateBetween(from, to, pageable);
        } else if (rating!= null) {
            return movieRepository.findAllByRatingIgnoreCase(rating, pageable);
        }
        return movieRepository.findAll(pageable).toList();
    }
    private void  checkIfRecipeNameIsUnique(String movieTitle, String movieDirector){
        movieRepository.findByTitleAndDirector(movieTitle, movieDirector)
                .ifPresent(r ->{throw new MovieAlreadyExistsException(movieTitle, movieDirector);
                });

    }
    Pageable providePageable(Integer page, Integer size, SortType sortType) {
        Sort.Direction direction = SortType.DESC == sortType ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "title");
        return PageRequest.of(
                page != null && size != null ? page : 0,
                page != null && size != null ? size : (int) movieRepository.count(), sort);
    }




}
