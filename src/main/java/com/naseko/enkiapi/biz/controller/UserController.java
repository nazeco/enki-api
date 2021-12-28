package com.naseko.enkiapi.biz.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.naseko.enkiapi.biz.entity.User;
import com.naseko.enkiapi.biz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user/")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping("doLogin")
    public SaResult doLogin(String name, String pwd) {
        HashMap<String, Object> data = new HashMap<>();
        User user = userService.login(name, pwd);
        if(user != null) {
            StpUtil.login(user.getId());
            StpUtil.getSession().set("id", user.getId());
            StpUtil.getSession().set("name", user.getUsername());
            SaTokenInfo token = StpUtil.getTokenInfo();
            logger.info(user.getUsername()+"登陆了");

            data.put("token", token.getTokenValue());
            data.put("username", user.getUsername());
            data.put("settings", JSON.parseObject(user.getDataJson()));
            return SaResult.data(data);
        }
        return SaResult.error("登录失败");
    }

    @RequestMapping("register")
    public SaResult register(String name, String pwd) {
        if (userService.findByUsername(name) != null) {
            return SaResult.error("注册失败，用户名已被使用");
        }
        User user = new User();
        user.setUsername(name);
        user.setPassword(pwd);
        userService.register(user);
        if (userService.findByUsername(name) != null) {
            return SaResult.ok("注册成功");
        }
        return SaResult.error("注册失败");
    }

    @RequestMapping("isLogin")
    public SaResult isLogin() {
        if (StpUtil.isLogin()) {
            return SaResult.ok("已登陆");
        }
        return SaResult.error("未登录");
    }

    @SaCheckLogin
    @RequestMapping("logout")
    public SaResult logout() {
//        StpUtil.logout(StpUtil.getSession().getLong("id"));    // 退出对应 id 全部 session
        StpUtil.logout();    // 退出当前 session
        return SaResult.ok();
    }

    @SaCheckLogin
    @RequestMapping("info")
    public SaResult info() {
        User user = userService.findById(StpUtil.getSession().getLong("id"));
        HashMap<String, Object> data = new HashMap<>();
        SaTokenInfo token = StpUtil.getTokenInfo();
        data.put("token", token.getTokenValue());
        data.put("username", user.getUsername());
        data.put("settings", JSON.parseObject(user.getDataJson()));
        return SaResult.data(data);
    }

    @SaCheckLogin
    @RequestMapping("updateInfo")
    public SaResult updateInfo(@RequestBody JSONObject dataJson) {
        User user = userService.findById(StpUtil.getSession().getLong("id"));
        user.setDataJson(dataJson.toJSONString());
        userService.update(user);
        logger.info(dataJson.toJSONString());
        if (user != null) {
            return SaResult.ok("保存成功");
        }
        return SaResult.error("保存失败");
    }

    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() { return SaResult.data(StpUtil.getTokenInfo()); }

    @RequestMapping("sessionInfo")
    public SaResult sessionInfo() { return SaResult.data(StpUtil.getSession()); }
}