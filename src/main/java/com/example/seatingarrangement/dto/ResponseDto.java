package com.example.seatingarrangement.dto;


import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.SimpleTimeZoneAwareLocaleContext;
import org.springframework.http.HttpStatus;

import java.net.http.HttpClient;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseDto {

    private Object Data;

    private String Message;

    private HttpStatus httpStatus;


}
