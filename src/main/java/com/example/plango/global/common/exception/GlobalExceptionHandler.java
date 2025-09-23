package com.example.plango.global.common.exception;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.example.plango.global.common.response.CommonResponse; // ← PlanGo 공통응답 경로로 변경
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Void>> handle(BusinessException ex) {
        CommonResponse<Void> response =
                CommonResponse.createFailure(ex.getStatusCode(), ex.getMessage());

        log.warn("BusinessException: ", ex);
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), ex.getStatusCode(), ex.getMessage());

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResponse<List<BindExceptionResponse>>> handle(BindException ex) {
        List<BindExceptionResponse> exceptionResponses = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            exceptionResponses.add(BindExceptionResponse.builder()
                    .message(fieldError.getDefaultMessage())
                    .data(fieldError.getRejectedValue())
                    .build());
        });

        CommonResponse<List<BindExceptionResponse>> response = CommonResponse.createFailure(
                ErrorCode.BIND_EXCEPTION.getStatusCode(),
                ErrorCode.BIND_EXCEPTION.getMessage(),
                exceptionResponses
        );

        log.warn("BindException: ", ex);
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(),
                ErrorCode.BIND_EXCEPTION.getStatusCode(), exceptionResponses);

        return ResponseEntity
                .status(ErrorCode.BIND_EXCEPTION.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse<Void>> handle(MethodArgumentTypeMismatchException ex) {

        CommonResponse<Void> response = CommonResponse.createFailure(
                ErrorCode.ARGUMENT_TYPE_MISMATCH_EXCEPTION.getStatusCode(),
                ErrorCode.ARGUMENT_TYPE_MISMATCH_EXCEPTION.getFormatted(ex.getName())
        );

        log.warn("MethodArgumentTypeMismatchException");
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(),
                ErrorCode.ARGUMENT_TYPE_MISMATCH_EXCEPTION.getStatusCode(), ex.getMessage());

        return ResponseEntity
                .status(ErrorCode.ARGUMENT_TYPE_MISMATCH_EXCEPTION.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<List<ArgumentNotValidExceptionResponse>>> handleValidation(
            MethodArgumentNotValidException ex) {

        List<ArgumentNotValidExceptionResponse> exceptionResponses = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(error -> ArgumentNotValidExceptionResponse.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .data(error.getRejectedValue())
                        .build())
                .toList();

        CommonResponse<List<ArgumentNotValidExceptionResponse>> response = CommonResponse.createFailure(
                ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.getStatusCode(),
                ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.getMessage(),
                exceptionResponses
        );

        log.warn("MethodArgumentNotValidException: ", ex);
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(),
                ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.getStatusCode(), exceptionResponses);

        return ResponseEntity
                .status(ErrorCode.ARGUMENT_NOT_VALID_EXCEPTION.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse<Void>> handle(HttpMessageNotReadableException ex) {
        // DTO 필드 타입 불일치
        if (ex.getCause() instanceof MismatchedInputException mismatched) {
            String fieldName = null;
            for (Reference ref : mismatched.getPath()) {
                fieldName = ref.getFieldName(); // 경로의 마지막 필드가 문제 필드
            }

            log.warn("MismatchedInputException");
            log.warn(LOG_FORMAT, ex.getClass().getSimpleName(),
                    ErrorCode.MISMATCHED_INPUT_EXCEPTION.getStatusCode(), ex.getMessage());

            CommonResponse<Void> response = CommonResponse.createFailure(
                    ErrorCode.MISMATCHED_INPUT_EXCEPTION.getStatusCode(),
                    ErrorCode.MISMATCHED_INPUT_EXCEPTION.getFormatted(fieldName)
            );
            return ResponseEntity
                    .status(ErrorCode.MISMATCHED_INPUT_EXCEPTION.getStatusCode())
                    .body(response);
        }

        log.warn("HttpMessageNotReadableException");
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(),
                ErrorCode.JSON_PARSE_EXCEPTION.getStatusCode(), ex.getMessage());

        CommonResponse<Void> response = CommonResponse.createFailure(
                ErrorCode.JSON_PARSE_EXCEPTION.getStatusCode(),
                ErrorCode.JSON_PARSE_EXCEPTION.getMessage()
        );

        return ResponseEntity
                .status(ErrorCode.JSON_PARSE_EXCEPTION.getStatusCode())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handle(Exception ex) {
        int statusCode = 500;

        if (ex instanceof ErrorResponse er) {
            statusCode = er.getStatusCode().value();
        }

        log.warn("기타 Exception: ", ex);
        log.warn(LOG_FORMAT, ex.getClass().getSimpleName(), statusCode, ex.getMessage());

        CommonResponse<Void> response = CommonResponse.createFailure(statusCode, ex.getMessage());

        return ResponseEntity.status(statusCode).body(response);
    }
}