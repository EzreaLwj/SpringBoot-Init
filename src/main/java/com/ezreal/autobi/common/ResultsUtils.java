package com.ezreal.autobi.common;

public class ResultsUtils {
    public static  <T> BaseResponse<T> success(int code , String message, T data) {
        return new BaseResponse<T>(code, data, message);
    }

    public static <T> BaseResponse<T> fail(int code, String message,T data) {
        return new BaseResponse<T>(code,data, message);
    }


    public static <T> BaseResponse<T> fail(int code, String message) {
        return new BaseResponse<T>(code, message);
    }
}
