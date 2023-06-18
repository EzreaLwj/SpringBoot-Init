package com.ezreal.autobi.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.security.model.AuthorizationExceptionResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 权限处理
     *
     * @param exception 权限异常
     * @return 结果
     */
    @ExceptionHandler(AuthorizationException.class)
    public BaseResponse<AuthorizationExceptionResp> authorizationException(AuthorizationException exception) {
        log.warn("GlobalExceptionHandler|authorizationException|权限不足, message: {}", exception.getMessage());
        return ResultsUtils.fail(Code.NO_AUTH_ERROR.getCode(), Code.NO_AUTH_ERROR.getMessage());
    }
}
