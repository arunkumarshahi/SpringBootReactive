package com.reactive.demo.web;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reactive.demo.exception.ErrorCode;
import com.reactive.demo.webdto.response.BaseWebResponse;

@RestControllerAdvice
public class ExceptionRestController {
@ExceptionHandler(EntityNotFoundException.class)
public ResponseEntity<BaseWebResponse> handleEntityNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseWebResponse.error(ErrorCode.ENTITY_NOT_FOUND));
}
}
