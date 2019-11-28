package com.github.aracwong.commons.api;

/**
 * 描述：
 *
 * @author: zpwang
 * @time: 2019/12/18 20:50
 */
public enum ResultCode {

    /**
     * ok
     */
    OK("ES200", "OK"),

    /**
     * No Data
     */
    NO_DATA("ES204", "No Data"),

    /**
     * Unauthorized
     */
    UNAUTHORIZED("ES401", "Unauthorized"),

    /**
     * Forbidden
     */
    FORBIDDEN("ES403", "Forbidden"),

    ;
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
