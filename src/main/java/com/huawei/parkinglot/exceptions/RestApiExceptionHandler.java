package com.huawei.parkinglot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {RestApiRequestException.class})
    public ResponseEntity<Object> handleRestApiRequestException(RestApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        RestApiException restApiException = new RestApiException().builder()
                .message(e.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")))
                .errors(new ArrayList<>())
                .build();



        return new ResponseEntity<>(restApiException, badRequest);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        List<ObjectError> error = e.getBindingResult().getAllErrors();
        List<Map<String, String >> errorList = new ArrayList<>();
        for(ObjectError o : error){
            Map<String, String> errorHandle =new HashMap<>();
            errorHandle.put("field", ((FieldError)o).getField());
            errorHandle.put("message", o.getDefaultMessage());
            errorList.add(errorHandle);
        }

        RestApiException restApiException = new RestApiException().builder()
                .message("")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(ZonedDateTime.now(ZoneId.of("Europe/Istanbul")))
                .errors(errorList)
                .build();

        return new ResponseEntity<>(restApiException, badRequest);
    }

}
