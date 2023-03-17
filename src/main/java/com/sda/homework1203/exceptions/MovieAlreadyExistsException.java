package com.sda.homework1203.exceptions;

public class MovieAlreadyExistsException extends RuntimeException {
    public MovieAlreadyExistsException(String movieTitle,String movieDirector){
        super(String.format("Movie with title %s and director %s already exists!", movieTitle,movieDirector));
    }
}
