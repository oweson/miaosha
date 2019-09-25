package com.imooc.miaosha.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis")
@Setter
@Getter
public class RedisConfig {
    /**
     * 把配置文件里面的内容读进来
     */
    private String host;
    private int port;
    private int timeout;
    //秒
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;

    //秒

}
