package com.imooc.miaosha.result;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    /**
     * 1 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 2 失败时候的调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    // 1 私有构造器
    private Result(T data) {
        this.data = data;
    }
    // 2 私有构造器

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    // 3 私有构造器
    private Result(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }


}
