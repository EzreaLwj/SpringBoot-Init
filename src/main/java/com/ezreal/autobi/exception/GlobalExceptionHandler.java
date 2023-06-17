package com.ezreal.autobi.exception;

import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<String> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultsUtils.fail(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
    }

}
