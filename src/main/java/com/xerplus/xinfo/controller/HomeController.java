package com.xerplus.xinfo.controller;

import com.xerplus.xinfo.model.EntityType;
import com.xerplus.xinfo.model.HostHolder;
import com.xerplus.xinfo.model.News;
import com.xerplus.xinfo.model.ViewObject;
import com.xerplus.xinfo.service.LikeService;
import com.xerplus.xinfo.service.NewsService;
import com.xerplus.xinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = {"/result"})
    public String result() {
        return "result";
    }

    @RequestMapping(path = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Model model) {
        List<News> newsList = newsService.getLastestNews(1);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<ViewObject>();

        for (News news: newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }

        model.addAttribute("vos", vos);
        return "index";
    }
}
