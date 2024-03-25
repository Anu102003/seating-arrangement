package com.example.seatingarrangement.exception;

public class AlreadyLogOutException extends RuntimeException{

    public AlreadyLogOutException(String error){
        super(error);
    }
}
