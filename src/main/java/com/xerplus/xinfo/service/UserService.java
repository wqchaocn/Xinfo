package com.xerplus.xinfo.service;

import com.xerplus.xinfo.dao.LoginTicketDAO;
import com.xerplus.xinfo.dao.UserDAO;
import com.xerplus.xinfo.model.LoginTicket;
import com.xerplus.xinfo.model.User;
import com.xerplus.xinfo.util.XinfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    private String deHeadUrl = "http://localhost:8080/images/default-avater.png";

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    // 注册
    public Map<String, Object> register(String userName, String password) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isBlank(userName)) {
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(userName);

        if (user != null) {
            map.put("msgname", "用户名已被注册");
            return map;
        }

        user = new User();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(0, 6));
        user.setHeadUrl(deHeadUrl);
        user.setPassword(XinfoUtil.MD5(password + user.getSalt()));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    // 登录
    public Map<String, Object> login(String userName, String password) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isBlank(userName)) {
            map.put("msglogin", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msglogin", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(userName);

        if (user == null | !XinfoUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msglogin", "用户名和密码不匹配");
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    // 退出
    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }

    // 添加ticket, 返回ticket值
    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + (long)1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addLoginTicket(ticket);
        return ticket.getTicket();
    }

    // 文件上传
    public String saveImage(MultipartFile file) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }

        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        if (!XinfoUtil.isImageFileAllowed(fileExt)) {
            return null;
        }

        String imageFileName = UUID.randomUUID().toString().replaceAll("-", "");
        Files.copy(file.getInputStream(), new File(XinfoUtil.IMAGE_DIR + imageFileName).toPath());

        return XinfoUtil.DOMAIN + "image?file=" + imageFileName;
    }
}
