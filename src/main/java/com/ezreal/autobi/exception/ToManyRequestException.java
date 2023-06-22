package com.ezreal.autobi.exception;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
public class ToManyRequestException extends RuntimeException{

    public ToManyRequestException(String message) {
        super(message);
    }
}
