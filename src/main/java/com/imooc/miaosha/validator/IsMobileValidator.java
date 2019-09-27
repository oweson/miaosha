package com.imooc.miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.imooc.miaosha.util.ValidatorUtil;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    /**
     * 1 初始化方法拿到注解
     */
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //true说明是必须的！！！
        if (required) {
            return ValidatorUtil.isMobile(value);
        } else {
            // 允许为空
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                // 不为空就判断以下格式
                return ValidatorUtil.isMobile(value);
            }
        }
    }

}
