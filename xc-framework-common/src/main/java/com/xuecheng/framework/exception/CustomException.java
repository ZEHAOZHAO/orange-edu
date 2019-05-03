package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 自定义异常
 * ${Author}: jason.zhao
 * 2019/5/3 15:49
 **/
public class CustomException extends RuntimeException {
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
