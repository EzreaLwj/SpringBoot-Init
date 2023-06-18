package com.ezreal.autobi.controller;

import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    @GetMapping("/error")
    public BaseResponse<String> error() {
        return ResultsUtils.fail(Code.JwtCode.JWT_DECODE_ERROR.getCode(), Code.JwtCode.JWT_DECODE_ERROR.getMessage(), "please login again");
    }
}
