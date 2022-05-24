package com.mountain.sea.core.exception;

import com.mountain.sea.core.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 11:34
 */
@ControllerAdvice
public class HandlerException {
    private final Logger logger = LoggerFactory.getLogger(HandlerException.class);

    public HandlerException() {
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exception(Exception e) {
        e.printStackTrace();
        this.logger.error("{} {}", e.getClass(), e.getMessage());
        return this.failure(ExceptionEnum.HANDLER_SYSTEM_EXCEPTION);
    }

    @ExceptionHandler({RestApiException.class})
    @ResponseBody
    public Result handleRestApiException(RestApiException e) {
        return this.failure(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public Result handleNoHandlerFoundException(NoHandlerFoundException e) {
        return this.failure(ExceptionEnum.HANDLER_NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        String message = (String)errorList.parallelStream().map((obj) -> {
            return obj.getDefaultMessage();
        }).findFirst().orElse("");
        return this.failure(ExceptionEnum.PARAMETER_VALIDATION_EXCEPTION.getErrorCode(), message);
    }

//    @ExceptionHandler({ConstraintViolationException.class})
//    @ResponseBody
//    public Result handleConstraintViolationException(ConstraintViolationException e) {
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        String message = (String)violations.parallelStream().map((item) -> {
//            return item.getMessage();
//        }).findFirst().orElse("");
//        return this.failure(ExceptionEnum.PARAMETER_VALIDATION_EXCEPTION.getErrorCode(), message);
//    }

    @ExceptionHandler({MissingPathVariableException.class})
    @ResponseBody
    public Result handleMissingPathVariableException(MissingPathVariableException e) {
        String message = MessageFormat.format(ExceptionEnum.URL_PARAMETER_MESSING_EXCEPTION.getErrorMessage(), e.getVariableName(), e.getParameter().getParameterType().getSimpleName());
        return this.failure(ExceptionEnum.URL_PARAMETER_MESSING_EXCEPTION.getErrorCode(), message);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public Result handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return this.failure(ExceptionEnum.URL_PARAMETER_MISMATCH_EXCEPTION);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return this.failure(ExceptionEnum.URL_HTTPMESSAGE_READABLE_EXCEPTION);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return this.failure(ExceptionEnum.HANDLER_METHOD_NOT_SUPPORT_EXCEPTION);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return this.failure(ExceptionEnum.URL_PARAMETER_MESSING_EXCEPTION.getErrorCode(), "参数缺失");
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public Result BindExceptionHandler(BindException e) {
        String message = (String)e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().orElse("");
        return this.failure(ExceptionEnum.PARAMETER_VALIDATION_EXCEPTION.getErrorCode(), message);
    }

    private Result failure(ExceptionEnum exceptionInfoEnum) {
        return Result.builder().success(false).error(exceptionInfoEnum).build();
    }

    private Result failure(String code, String message) {
        return Result.builder().success(false).error(code, message).build();
    }
}
