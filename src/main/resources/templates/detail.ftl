<#include "header.ftl">
    <div class="container-fluid">
        <div class="posts ">
        <div class="post">
            <div class="p-img"><img class="p-img" src="${news.image}" alt=""></div>
            <div class="p-news">
                <div class="p-title"><a target="_blank" href="http://localhost:8080/news/${news.id}">${news.title}</a></div>
                <span class="p-info"><a href="/user/${owner.id}/">${owner.name}</a> - ${news.createdDate?string('MM/dd')} - ${news.commentCount}评 - ${news.likeCount}赞</span>
            </div>
        </div>
        <div class="text-default text-content-a">
        <#if user?exists>
            <div class="alert alert-dark">
                欢迎发表观点!
            </div>
            <form method="post" action="/addComment">
                <input name="newsId" value="${news.id}" type="hidden">
                <textarea rows="1" cols="65" name="content"></textarea><br>
                <input type="submit" value="提交评论">
            </form>
        <#else>
            <div class="alert alert-dark">
                未登录仅能浏览评论!
            </div>
        </#if>
        <#assign num=0>
        <#list commentVOs as vo>
            <#assign num++>
            <div class="default-font">
                [${num}楼] ${vo.user.name} : ${vo.comment.content} - ${vo.comment.createdDate?string('MM/dd HH:mm')}
            </div>
        </#list>
        </div>
        </div>
    </div>
<#include "footer.ftl">