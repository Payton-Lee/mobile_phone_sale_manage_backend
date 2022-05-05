package com.phoneshop.shop.entity;

import com.phoneshop.shop.entity.enums.ReturnCode;
import lombok.Data;

@Data
public class ResultData<T> {
    private int status;
    private String message;
    private T data;
    public static <T> ResultData<T> success(int code, String message, T data) {
        ResultData<T> resultData = new ResultData<>();
//        resultData.setStatus(ReturnCode.RC200.code);
//        resultData.setMessage(ReturnCode.RC200.message);
        resultData.setStatus(code);
        resultData.setMessage(message);
        resultData.setData(data);
        return resultData;
    }
    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }
}
