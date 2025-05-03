package com.binarybrains.blogging.util.error;

import lombok.Getter;

@Getter
public class BlogException extends RuntimeException{
    private final ErrorInfo errorInfo;
    public BlogException(ErrorInfo errorInfo){
        super(errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }

}
