package com.ezreal.autobi.common;

public enum Code {
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    SYSTEM_BUSY(50002, "系统繁忙"),
    OPERATION_ERROR(50001, "操作失败");

    private final int code;
    private final String message;

    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static class ShiroCode {

        public final static String SALT = "ezreal";
        public final static int HASH_ITERATIONS = 3;
        public final static String MD5_ALGORITHM = "md5";
    }

    public static class Jwt {
        public final static long TIME = 1000 * 60 * 60 * 24 * 30L;
//        public final static long TIME = 1000 * 30L;
    }

    public enum ChartCode {

        CHART_UPLOAD_SUCCESS(201, "upload success"),
        CHART_UPLOAD_FAIL(501, "upload fail"),
        CHART_LENGTH_ERROR(502, "length error");


        private final int code;
        private final String message;

        ChartCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum FileCode {
        FILE_UPLOAD_SUCCESS(201, "upload success"),
        FILE_UPLOAD_FAIL(501, "upload fail");

        private final int code;
        private final String message;

        FileCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }

    public enum JwtCode {
        JWT_DECODE_ERROR(501, "jwt decode error");

        private final int code;
        private final String message;

        JwtCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum UserCode {
        USER_REGISTER_SUCCESS(201, "register success"),
        USER_LOGIN_SUCCESS(200, "login success"),
        USER_LOGIN_FAIL(500, "login fail"),
        USER_REGISTER_FAIL(501, "register fail"),
        USER_NOT_LOGIN(502, "not login"),
        USER_OUT_LOGIN(202, "out login"),
        USER_OUT_LOGIN_FAIL(503, "out login fail");

        private final int code;
        private final String message;

        UserCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
