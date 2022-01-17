package com.rzon.workflow.config;

import com.google.common.collect.Maps;
import com.rzon.workflow.constant.ErrorCode;
import com.rzon.workflow.constant.ErrorMessage;
import com.rzon.workflow.exception.InternalServerException;
import com.rzon.workflow.exception.PreconditionUnsatisfiedException;
import com.rzon.workflow.exception.ResourceNotFoundException;
import com.rzon.workflow.exception.ValidationException;
import com.rzon.workflow.utils.JacksonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartException;
import org.xml.sax.SAXParseException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_ERROR_MSG = "服务器内部异常，请联系管理员";

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFound(final ResourceNotFoundException e) {
        outputToTheConsole(e);
        return new ErrorMessage(ErrorCode.RESOURCE_NOT_FOUND, e.getMessage(), e.getDetail());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleHttpClientError(final HttpClientErrorException e) {
        outputToTheConsole(e);
        log.warn(e.getResponseBodyAsString(), e);
        final ErrorMessage em = JacksonUtil.deserialize(e.getResponseBodyAsString(), ErrorMessage.class);
        return new ResponseEntity<>(em, e.getStatusCode());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleHttpServerError(final HttpServerErrorException e) {
        outputToTheConsole(e);
        log.error(e.getResponseBodyAsString(), e);
        return new ErrorMessage(ErrorCode.INTERNAL_SERVER_ERROR, e.getResponseBodyAsString());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidation(final ValidationException e) {
        outputToTheConsole(e);
        return new ErrorMessage(ErrorCode.BAD_INPUT_PARAMETER, e.getMessage(), e.getDetail());
    }

    @ExceptionHandler(PreconditionUnsatisfiedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ErrorMessage handlePreconditionUnsatisfied(final PreconditionUnsatisfiedException e) {
        outputToTheConsole(e);
        return new ErrorMessage(ErrorCode.PRECONDITION_FAILED, e.getMessage(), e.getDetail());
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleInternalServerException(final InternalServerException e) {
        outputToTheConsole(e);
        log.error("服务器内部异常", e);
        return new ErrorMessage(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage(), e.getDetail());
    }

    @ExceptionHandler(SAXParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleSAXParse(final SAXParseException e) {
        final Map<String, Integer> maps = Maps.newHashMap();
        maps.put("ColumnNumber", e.getColumnNumber());
        maps.put("LineNumber", e.getLineNumber());
        outputToTheConsole(e);
        return new ErrorMessage(ErrorCode.BAD_INPUT_PARAMETER, e.getMessage(), maps);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMultipartException(final MultipartException e) {
        log.info("上传文件不是multipart/form-data", e);
        outputToTheConsole(e);
        return new ErrorMessage(ErrorCode.BAD_INPUT_PARAMETER, e.getMessage(), null);
    }

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleResourceAccessException(final ResourceAccessException e) {
        outputToTheConsole(e);
        log.error(e.getMessage(), e);
        return new ErrorMessage(ErrorCode.INTERNAL_SERVER_ERROR, "ROS服务连接异常");
    }

    public ErrorMessage handleUnknownThrowable(final Throwable e) {
        final String message = Optional.ofNullable(e.getMessage()).orElse(DEFAULT_ERROR_MSG);
        log.error(message, e);
        outputToTheConsole(e);
        return new ErrorMessage(ErrorCode.INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MSG);
    }

    /**
     * 输出到控制台
     *
     * @param throwable 异常
     */
    private void outputToTheConsole(final Throwable throwable) {
        final StackTraceElement stackTraceElement = throwable.getStackTrace()[0];
        log.error("[异常报错信息]:{} [异常文件]：{} [所在类]：{} [所在方法]：{} [行]：{}",
                throwable.getMessage(),
                stackTraceElement.getFileName(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                stackTraceElement.getLineNumber());
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public ErrorMessage handleThrowable(final Throwable e) {
        final Object errorMessage = Arrays.stream(this.getClass().getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
            .filter(method -> Arrays.stream(method.getDeclaredAnnotation(ExceptionHandler.class).value())
                .anyMatch(ex -> ex.isAssignableFrom(e.getClass())))
            .findAny()
            .orElse(this.getClass().getDeclaredMethod("handleUnknownThrowable", Throwable.class))
            .invoke(this, e);

        return errorMessage instanceof ResponseEntity
            ? ((ResponseEntity<ErrorMessage>) errorMessage).getBody()
            : (ErrorMessage) errorMessage;
    }

}
