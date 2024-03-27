package com.example.seatingarrangement.dto;


import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseDto {

    private Object data;

    private String message;

    private HttpStatus httpStatus;


}
