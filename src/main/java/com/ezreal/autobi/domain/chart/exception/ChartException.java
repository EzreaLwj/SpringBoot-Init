package com.ezreal.autobi.domain.chart.exception;

import com.ezreal.autobi.common.Code;

/**
 * @author Ezreal
 * @Date 2023/6/20
 */
public class ChartException extends RuntimeException{

    private int code;

    public ChartException(String message) {
        super(message);
    }

    public ChartException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ChartException(Code.ChartCode code) {
        this(code.getMessage());
        this.code = code.getCode();
    }

    public int getCode() {
        return code;
    }
}
