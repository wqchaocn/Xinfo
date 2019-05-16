package com.xerplus.xinfo.controller;

import com.xerplus.xinfo.model.EntityType;
import com.xerplus.xinfo.model.HostHolder;
import com.xerplus.xinfo.service.LikeService;
import com.xerplus.xinfo.service.NewsService;
import com.xerplus.xinfo.util.XinfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LikeController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private NewsService newsService;

    /**
     * 点赞
     * @param newsId
     * @return
     */
    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET})
    public String like(@RequestParam("newsId") int newsId) {
        if (hostHolder.getUser() == null) {
            return XinfoUtil.getJSONString(1, "未登录!");
        }
        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikeCount(newsId, (int) likeCount);

        return "redirect:/";
//        return XinfoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    /**
     * 点踩
     * @param newsId
     * @return
     */
    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET})
    public String dislike(@RequestParam("newsId") int newsId) {
        if (hostHolder.getUser() == null) {
            return XinfoUtil.getJSONString(1, "未登录!");
        }
        int userId = hostHolder.getUser().getId();
        likeService.dislike(userId, EntityType.ENTITY_NEWS, newsId);
        long likeCount = likeService.getLikeCount(userId, EntityType.ENTITY_NEWS, newsId);
        newsService.updateLikeCount(newsId, (int) likeCount);

        return "redirect:/";
//        return XinfoUtil.getJSONString(0, String.valueOf(dislikeCount));
    }
}
