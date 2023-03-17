package com.sda.homework1203.exceptions;

public class NoMovieFoundException extends RuntimeException{
    public NoMovieFoundException(Long id){
        super(String.format("Can't find movie with id %d", id));
    }
}
