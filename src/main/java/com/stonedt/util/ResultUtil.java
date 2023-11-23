package com.stonedt.util;

/**
 * 自定义响应结构
 */
public class ResultUtil<T> {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private T data;

    public static <T> ResultUtil<T> build(Integer status, String msg, T data) {
        return new ResultUtil<T>(status, msg, data);
    }

    public static <T> ResultUtil<T> ok(T data) {
        return new ResultUtil<T>(data);
    }

    public static <T> ResultUtil<T> ok() {
        return new ResultUtil<T>(null);
    }

    public ResultUtil() {

    }

    public static ResultUtil build(Integer status, String msg) {
        return new ResultUtil(status, msg, null);
    }

    public ResultUtil(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultUtil(T data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
