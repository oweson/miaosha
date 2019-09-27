package com.imooc.miaosha.exception;

/**
 * the class is create by @Author:oweson
 *
 * @Date：2019/9/27 20:41
 */
public class IntegerTets extends Number {
    public IntegerTets(){
        System.out.println("call!!!!");
    }

    @Override
    public int intValue() {
        return 0;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    public static void main(String[] args) {
        IntegerTets integerTets = new IntegerTets();

        IntegerTets integerTets1=integerTets;


    }
}
