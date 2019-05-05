package com.xerplus.xinfo.controller;

import com.xerplus.xinfo.model.*;
import com.xerplus.xinfo.service.*;
import com.xerplus.xinfo.util.XinfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    UserService userService;

    @Autowired
    NewsService newsService;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/add"})
    public String add() {
        return "add";
    }


    // 展示新闻
    @RequestMapping(path = {"/news/{newsId}"}, method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        if (news != null) {
            List<Comment> commentsList = commentService.getCommentByEntity(news.getId(), EntityType.ENTITY_NEWS);
            List<ViewObject> commentVOs = new ArrayList<>();
            for (Comment comment: commentsList) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user", userService.getUser(comment.getUserId()));
                commentVOs.add(vo);
            }
            model.addAttribute("commentVOs", commentVOs);
        }
        model.addAttribute("news",news);
        model.addAttribute("owner", userService.getUser(news.getUserId()));
        model.addAttribute("title", news.getTitle() + " - ");
        return "detail";
    }

    // 新建新闻
    @RequestMapping(path = {"/addNews_con"}, method = {RequestMethod.POST})
    public String addNews(Model model,
                          @RequestParam("file") MultipartFile file,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link,
                          HttpServletResponse response) {
        try {
            News news = new News();
            if (hostHolder.getUser() != null) {
                news.setUserId(hostHolder.getUser().getId());
            } else {
                logger.error("未登录!");
                String result = XinfoUtil.getJSONString(1, "未登录");
                model.addAttribute("result", result);
                return response.encodeRedirectURL("/result");
            }
            String fileUrl = qiniuService.saveImage(file);
            try {
                if (fileUrl == null) {
                    String result = XinfoUtil.getJSONString(1, "文件名错误导致上传失败");
                    model.addAttribute("result", result);
                    return response.encodeRedirectURL("/result");
                }
            } catch (Exception e) {
                logger.error("上传图片失败" + e.getMessage());
                String result = XinfoUtil.getJSONString(1, "上传失败");
                model.addAttribute("result", result);
                return response.encodeRedirectURL("/result");
            }
            news.setImage(fileUrl);
            news.setCreatedDate(new Date());
            news.setTitle(title);
            news.setLink(link);
            newsService.addNews(news);
            String result = XinfoUtil.getJSONString(0, "发布成功");
            model.addAttribute("result", result);
            return response.encodeRedirectURL("/result");
        } catch (Exception e) {
            logger.error("添加资讯错误" + e.getMessage());
            String result = XinfoUtil.getJSONString(1, "发布失败");
            model.addAttribute("result", result);
            return response.encodeRedirectURL("/result");
        }
    }

    // 添加评论
    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            commentService.addComment(comment);
            //更新news里的评论数量
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            newsService.updateCommentCount(comment.getEntityId(), count);
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/news/" + newsId;
    }


    // 上传图片
    @RequestMapping(path = {"/uploadImage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
//            String fileUrl = userService.saveImage(file);
            String fileUrl = qiniuService.saveImage(file);
            if (fileUrl == null) {
                return XinfoUtil.getJSONString(1, "文件名错误导致上传失败");
            }
            return XinfoUtil.getJSONString(0, fileUrl);
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage());
            return XinfoUtil.getJSONString(1, "上传失败");
        }
    }

    @RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("file") String fileName,
                         HttpServletResponse response) {
        try {
            response.setContentType("image");
            StreamUtils.copy(new FileInputStream(new File(XinfoUtil.IMAGE_DIR + fileName)),
                    response.getOutputStream());
        } catch (Exception e) {
            logger.error("加载图片失败:" + e.getMessage());
        }
    }

    /**
     * 按userId查找
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET})
    public String userDetail(@PathVariable("userId") int userId, Model model) {
        List<News> newsList = newsService.getUserNews(userId);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for (News news: newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(userId));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }

        model.addAttribute("vos", vos);
        model.addAttribute("owner", userService.getUser(userId).getName());
        return "user_detail";
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/like_sort"}, method = {RequestMethod.GET})
    public String likeSort(Model model) {
        List<News> newsList = newsService.getLikeNewsSort();
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
        model.addAttribute("field", "点赞数");
        model.addAttribute("title", "点赞排行 - ");
        return "sort_detail";
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(path = {"/comment_sort"}, method = {RequestMethod.GET})
    public String commentSort(Model model) {
        List<News> newsList = newsService.getCommentNewsSort();
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
        model.addAttribute("field", "评论数");
        model.addAttribute("title", "评论排行 - ");
        return "sort_detail";
    }
}
