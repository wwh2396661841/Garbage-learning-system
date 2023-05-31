package com.wwh.schoolWork.common.core.domain;


import com.wwh.schoolWork.common.core.constant.HttpStatus;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;



    public static Result<Object> fail(){
        Result<Object> result= new Result<Object>();
        result.setCode(HttpStatus.ERROR);
        result.setMsg("操作失败");
        return result;
    }

    public static Result<Object> fail(String msg){
        Result<Object> result= new Result<Object>();
        result.setCode(HttpStatus.ERROR);
        result.setMsg(msg);
        return result;
    }

    public static Result<Object> success(){
        Result<Object> result= new Result<Object>();
        result.setCode(HttpStatus.SUCCESS);
        result.setMsg("操作成功");
        return result;
    }

    public static Result<Object> success(String msg){
        Result<Object> result= new Result<Object>();
        result.setCode(HttpStatus.SUCCESS);
        result.setMsg(msg);
        return result;
    }


    public static <T> Result<T> success(T data){
        Result<T> result= new Result<T>();
        result.setCode(HttpStatus.SUCCESS);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String msg,T data){
        Result<T> result= new Result<T>();
        result.setCode(HttpStatus.SUCCESS);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
