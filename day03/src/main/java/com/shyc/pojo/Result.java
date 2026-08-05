package com.shyc.pojo;

import lombok.Data;

/**
 * @author shiyc
 * @date 2026/7/31 21:16
 */
@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public static Result success(Object data){
        Result result = new Result();
        result.setCode(1);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static Result success(){
        Result result = new Result();
        result.code = 1;
        result.message = "操作成功";
        return result;
    }

    public static Result error(String message){
        Result result = new Result();
        result.setCode(0);
        result.setMessage(message);
        return result;
    }
}
