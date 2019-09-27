package com.imooc.miaosha.vo;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import com.imooc.miaosha.validator.IsMobile;

@Getter
@Setter
@ToString
public class LoginVo {
    /**
     * jsr303校验
     */
    // 自定义注解
    @NotNull
    @IsMobile(message = "121212121212", required = false)
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;


}
