package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 异常抛出类
 * ${Author}: jason.zhao
 * 2019/5/3 15:52
 **/
public class ExceptionCast {

    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
