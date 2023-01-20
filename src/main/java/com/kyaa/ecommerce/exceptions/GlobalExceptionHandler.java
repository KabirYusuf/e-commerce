package com.kyaa.ecommerce.exceptions;

import com.kyaa.ecommerce.data.models.Product;
import com.kyaa.ecommerce.data.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

    @RestControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(UserException.class)
        public ResponseEntity<?> handleUserErrorEx(UserException ex, WebRequest request){
            ApiErrorDetail apiErrorDetail = new ApiErrorDetail(ex.getMessage(), request.getDescription(false));
            return new ResponseEntity<>(apiErrorDetail, HttpStatus.NOT_FOUND);
        }
        @ExceptionHandler(ProductException.class)
        public ResponseEntity<?> handleProductErrorEx(ProductException ex, WebRequest request){
            ApiErrorDetail apiErrorDetail = new ApiErrorDetail(ex.getMessage(), request.getDescription(false));
            return new ResponseEntity<>(apiErrorDetail, HttpStatus.NOT_FOUND);
        }
//        @ExceptionHandler(AuthorException.class)
//        public ResponseEntity<?> handleAuthorErrorEx(AuthorException ex, WebRequest request){
//            ApiErrorDetail apiErrorDetail = new ApiErrorDetail(ex.getMessage(), request.getDescription(false));
//            return new ResponseEntity<>(apiErrorDetail, HttpStatus.NOT_FOUND);
//        }
    }

