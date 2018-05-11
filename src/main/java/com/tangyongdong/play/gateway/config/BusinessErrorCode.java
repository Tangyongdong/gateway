package com.tangyongdong.play.gateway.config;

import com.tangyongdong.base.common.exception.ErrorCode;

/**
 * @author tangyongdong
 * @create 2018-05-02 16:56
 */
public enum BusinessErrorCode implements ErrorCode {

    USER_NO_LOGIN(1, "A0002", "请先登录"),
    PARAMS_ILLEGALITY_ERROR(2, "B0001", "参数非法"),
    PARAMS_EMPTY_ERROR(1, "B0002", "参数不能为空");

    private Integer errorStatus;
    private String errorCode;
    private String errorMessage;

    BusinessErrorCode(Integer errorStatus, String errorCode, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public Integer getStatus() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
