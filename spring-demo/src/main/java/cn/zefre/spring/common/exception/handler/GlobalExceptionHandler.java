package cn.zefre.spring.common.exception.handler;

import cn.zefre.spring.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author pujian
 * @date 2021/10/20 14:17
 */
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理controller层普通参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Response validationException(BindException e) {
        List<ObjectError> allErrors = e.getAllErrors();
        String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
        log.info("请求参数错误：{}", errorMsg);
        return Response.validationFailed(allErrors.get(0).getDefaultMessage());
    }

    /**
     * 处理controller层@ResponseBody参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response validationException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
        log.info("请求参数错误：{}", errorMsg);
        return Response.validationFailed(allErrors.get(0).getDefaultMessage());
    }

    /**
     * 除controller层以外的方法参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response methodValidationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String errorMsg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
        log.info("方法参数校验异常：{}", errorMsg);
        return Response.validationFailed(violations.iterator().next().getMessage());
    }

    /**
     * http请求常见错误处理
     */
    @ExceptionHandler(ServletException.class)
    public Response httpRequestException(ServletException e) {
        log.info(e.getMessage());
        return Response.error(e.getMessage());
    }

    /**
     * 全局异常
     */
    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        log.warn(e.getMessage());
        return Response.error();
    }
}
