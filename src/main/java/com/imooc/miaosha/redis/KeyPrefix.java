package com.imooc.miaosha.redis;

/**
 * 作模块的区分
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();

}
