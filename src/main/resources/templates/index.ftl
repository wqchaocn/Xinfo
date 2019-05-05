<#assign title="">
<#include "header.ftl">
    <div class="container-fluid">
            <div class="posts">
            <#list vos as vo>
            <div class="post">
                <div class="p-img"><img class="p-img" src="${vo.news.image}" alt=""></div>
                <div class="p-news">
                    <div class="p-title"><a target="_blank" href="http://localhost:8080/news/${vo.news.id}">${vo.news.title}</a></div>
                    <span class="p-info"><a href="/user/${vo.user.id}/">${vo.user.name}</a> - ${vo.news.createdDate?string('MM/dd')} - ${vo.news.commentCount}评</span>
                </div>
                <div class="p-like">
                    <#if user?exists && (vo.like!=1)>
                        <span class="p-like-btn-up">
                            <a href="http://localhost:8080/like?newsId=${vo.news.id}">
                                <i class="fa fa-caret-up fa-lg"></i>
                            </a>
                        </span>
                    <#elseif user?exists && (vo.like==1)>
                        <span class="p-like-btn-up">
                            <a href="http://localhost:8080/dislike?newsId=${vo.news.id}">
                                <i class="fa fa-caret-down fa-lg"></i>
                            </a>
                        </span>
                    <#else>
                        <span class="p-like-btn-up pressed">
                                <i class="fa fa-caret-up fa-lg"></i>
                        </span>
                    </#if>
                    <span class="p-like-count">${vo.news.likeCount}</span>
                </div>
            </div>
            </#list>
            </div>
    </div>
<#include "footer.ftl">