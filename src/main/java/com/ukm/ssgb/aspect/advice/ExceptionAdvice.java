package com.ukm.ssgb.aspect.advice;


import com.ukm.ssgb.dto.ErrorResponse;
import com.ukm.ssgb.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    void traceLog(Throwable ex) {
        log.error("### {}", ex.getClass().getSimpleName(), ex);
    }


    /**
     * @Valid 에서 발생하는 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        traceLog(ex);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    /**
     * 검증 관련 예외 처리
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleValidationException(ValidationException ex) {
        traceLog(ex);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    /**
     * 파일 업로드 최대 사이즈 초과 예외 처리
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.OK)
    protected ErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        traceLog(ex);
        return new ErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), ex.getMessage());
    }

    /**
     * 나머지 모든 Exception
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleException(Throwable ex) {
        traceLog(ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부오류가 발생했습니다.");
    }
}