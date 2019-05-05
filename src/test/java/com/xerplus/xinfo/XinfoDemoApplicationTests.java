package com.xerplus.xinfo;

import com.xerplus.xinfo.dao.CommentDAO;
import com.xerplus.xinfo.dao.LoginTicketDAO;
import com.xerplus.xinfo.dao.NewsDAO;
import com.xerplus.xinfo.dao.UserDAO;
import com.xerplus.xinfo.model.Comment;
import com.xerplus.xinfo.model.EntityType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XinfoDemoApplicationTests {
	@Autowired
	NewsDAO newsDAO;

	@Autowired
	UserDAO userDAO;

	@Autowired
	LoginTicketDAO loginTicketDAO;

	@Autowired
	CommentDAO commentDAO;

	@Test
	public void contextLoads() {
		for(int i = 1; i < 8; i++) {
			Comment comment = new Comment();
			comment.setUserId(i);
			comment.setEntityId(i);
			comment.setEntityType(EntityType.ENTITY_NEWS);
			comment.setContent("hello is " + i + "*");
			comment.setCreatedDate(new Date());
			comment.setStatus(0);
			commentDAO.addComment(comment);
		}

//		for (int i = 1; i < 8; i++) {
//			News news = new News();
//			news.setCommentCount(i);
//			Date date = new Date();
//			date.setTime(date.getTime() + (long)1000*3600*6*i);
//			news.setCreatedDate(date);
//			news.setImage("http://localhost:8080/images/default-avater.png");
//			news.setLikeCount(i + 1);
//			news.setLink("http://localhost:8080/images/default-avater.png");
//			news.setUserId(i);
//			news.setTitle("[学习] TITLE_" + i);
//
//			User user = new User();
//			user.setName(String.format("TESTNAME_%d", i));
//			user.setPassword(String.valueOf(i));
//			user.setSalt(String.valueOf(i));
//			user.setHeadUrl("http://localhost:8080/images/default-avater.png");
//
//			LoginTicket loginTicket = new LoginTicket();
//			date.setTime(date.getTime() + (long)1000*3600*6*i);
//			loginTicket.setUserId(i);
//			loginTicket.setTicket("TICKET" + i);
//			loginTicket.setExpired(date);
//			loginTicket.setStatus(0);
//
//			loginTicketDAO.addLoginTicket(loginTicket);
//			userDAO.addUser(user);
//			newsDAO.addNews(news);
//
//		}
	}

}
