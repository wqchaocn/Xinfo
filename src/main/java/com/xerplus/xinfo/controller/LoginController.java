package com.xerplus.xinfo.controller;

import com.xerplus.xinfo.service.UserService;
import com.xerplus.xinfo.util.XinfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/login"})
    public String login() {
        return "login";
    }

    @RequestMapping(path = {"/register"})
    public String register() {
        return "register";
    }

    @RequestMapping(path = {"/register_con"}, method = {RequestMethod.POST})
    public String register_con(Model model,
                        @ModelAttribute("userName") String userName,
                        @ModelAttribute("password") String password,
                        @ModelAttribute("rememberMe") int rememberMe,
                        HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(userName, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberMe == 1) {
                    cookie.setMaxAge(3600*168);
                }
                response.addCookie(cookie);
                String result = XinfoUtil.getJSONString(0, "注册成功");
                model.addAttribute("result", result);
                return response.encodeRedirectURL("/result");
            } else {
                String result = XinfoUtil.getJSONString(1, map);
                model.addAttribute("result", result);
                return response.encodeRedirectURL("/result");
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            String result = XinfoUtil.getJSONString(1, "注册异常");
            model.addAttribute("result", result);
            return response.encodeRedirectURL("/result");
        }

    }

    @RequestMapping(path = {"/login_con"}, method = {RequestMethod.POST})
    public String login_con(Model model,
                       @ModelAttribute("userName") String userName,
                       @ModelAttribute("password") String password,
                       @ModelAttribute("rememberMe") int rememberMe,
                       HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(userName, password);

            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberMe == 1) {
                    cookie.setMaxAge(3600*168);
                }
                response.addCookie(cookie);
                String result = XinfoUtil.getJSONString(0, "登录成功");
                model.addAttribute("result", result);
                return response.encodeRedirectURL("/result");
            } else {
                String result = XinfoUtil.getJSONString(1, map);
                model.addAttribute("result", result);
                return response.encodeRedirectURL("/result");
            }
        } catch (Exception e) {
            logger.error("登录异常: " + e.getMessage());
            String result = XinfoUtil.getJSONString(1, "登录异常");
            model.addAttribute("result", result);
            return response.encodeRedirectURL("/result");
        }
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

    /*
    @RequestMapping(path = {"/login"}, method = {RequestMethod.POST})
    @ResponseBody
    public String reg(@RequestParam("userName") String userName,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rememberMe", defaultValue = "0") int rememberMe,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(userName, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberMe == 1) {
                    cookie.setMaxAge(3600*168);
                }
                response.addCookie(cookie);
                return XinfoUtil.getJSONString(0, "注册成功");
            } else {
                return XinfoUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return XinfoUtil.getJSONString(1, "注册异常");
        }
    }
*/
}
