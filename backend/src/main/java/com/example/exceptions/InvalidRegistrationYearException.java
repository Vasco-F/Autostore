package com.example.exceptions;

import java.time.Year;

public class InvalidRegistrationYearException extends RuntimeException {

    public InvalidRegistrationYearException(){
        super("Invalid registration year!\nValid year from 1886 to " + Year.now().getValue() + ".");
    }
}
