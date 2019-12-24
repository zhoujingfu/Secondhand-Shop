package com.gjj.exceptions;


import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * Created by peng on 2017/8/14.
 */
@Service
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;
    private ErrorMessage errorMessage;

    public BusinessException() {
        super();
    }

    public BusinessException(ErrorCode errorCode, ErrorMessage errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode()
    {
        return errorCode.getCode();
    }
    public String getErrorMessage() {
        return errorMessage.getMessage();
    }
}
