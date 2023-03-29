package com.sda.homework1203.controller;

import com.sda.homework1203.errors.AddMovie;
import com.sda.homework1203.errors.ErrorType;
import com.sda.homework1203.errors.UpdateMovie;
import com.sda.homework1203.exceptions.MovieAlreadyExistsException;
import com.sda.homework1203.exceptions.NoMovieFoundException;
import com.sda.homework1203.model.Movie;
import com.sda.homework1203.model.Rating;
import com.sda.homework1203.service.MovieService;
import com.sda.homework1203.service.SortType;
import org.springframework.http.HttpStatus;
import com.sda.homework1203.errors.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//http://localhost:8080/swagger-ui/index.html
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Movie> getMovies(@RequestParam(required = false) String title,
                          @RequestParam(required = false) String director,
                          @RequestParam(required = false) String genre,
                          @RequestParam(required = false) LocalDate from,
                          @RequestParam(required = false) LocalDate to,
                          @RequestParam(required = false) Rating rating,
                          @RequestParam(required = false) SortType sortType,
                          @RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer size){
        return movieService.getRecipes(title, director, genre, from, to, rating,sortType,page,size);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Movie addMovie(@Validated(AddMovie.class) @RequestBody Movie movie){
        return movieService.addMovie(movie);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Movie deleteMovie(@PathVariable Long id){
        return movieService.deleteMovie(id);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Movie getMovieByID(@PathVariable Long id){
        return movieService.getMovieById(id);
    }
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Movie updateMovie(@PathVariable Long id, @Validated (UpdateMovie.class) @RequestBody Movie movie){
        return movieService.updateMovie(id,movie);
    }
    @ExceptionHandler(NoMovieFoundException.class)
    private ResponseEntity<Error<String>> mapNoSuchElementException(NoMovieFoundException ex) {
        return new ResponseEntity<>(
                new Error<>(HttpStatus.NOT_FOUND.value(),ex.getMessage() ), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MovieAlreadyExistsException.class)
    private ResponseEntity<Error<String>> mapRecipeAlreadyExistsException(MovieAlreadyExistsException ex){
        return new ResponseEntity<>(
                new Error<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<Error<Map<String,String>>> handleMethodArgumentNotValidException (MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(new Error(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                ErrorType.VALIDATION
        ), HttpStatus.BAD_REQUEST);
    }
}
