package com.imooc.miaosha.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVo;

@Service
public class MiaoshaUserService {


    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = miaoshaUserDao.getById(id);
        if (user != null) {
            redisService.set(MiaoshaUserKey.getById, "" + id, user);
        }
        return user;
    }

    // http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        MiaoshaUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(MiaoshaUserKey.getById, "" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);
        return true;
    }


    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //1 延长有效期--容器的session时间：最后一次访问加上有效时间
        if (user != null) {
            // 2 重新写入cookie;
            addCookie(response, token, user);
        }
        return user;
    }


    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 1 判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            // 异常往外抛出，将被异常处理器处理，切面！！！
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 2 验证密码
        String dbPass = user.getPassword();
        // 3 数据库的盐
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        // 4 生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }
   // token不必每次都生成，复用！！！
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        // 写入缓存！前缀和token还有用户信息，根据token查找用户信息
        redisService.set(MiaoshaUserKey.token, token, user);
        // 生成cookie,k，v
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        // 和session保持一致，
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        // web的根目录
        cookie.setPath("/");
        // 值需要写入response就ok啦；
        response.addCookie(cookie);
    }

}
