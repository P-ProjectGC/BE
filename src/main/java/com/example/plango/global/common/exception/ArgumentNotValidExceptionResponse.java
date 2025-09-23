package com.example.plango.global.common.exception;

import lombok.Builder;

@Builder
public record ArgumentNotValidExceptionResponse(
        String field,
        String message,
        Object data
) {}