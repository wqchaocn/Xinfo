<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; text/html; charset=utf-8" />
    <title>${title}Xinfo</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="/styles/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/styles/style.css">
    <link rel="stylesheet" type="text/css" href="/styles/style_o.css">
</head>
<body>

<nav class="navbar navbar-expand-sm fixed-top ">
    <div class="nav-center">
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link nav-logo" href="http://localhost:8080/">Xinfo</a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-hot-a" href="/like_sort">点赞热门</a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-hot-b" href="/comment_sort">评论热门</a>
        </li>
        <#if user?exists>
        <li class="nav-item">
            <a class="nav-link " href="/user/${user.id}">用户: ${user.name}</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/add">分享</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/logout">注销</a>
        </li>
        <#else>
        <li class="nav-item">
            <a class="nav-link" href="/login">登录</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/register">注册</a>
        </li>
        </#if>
    </ul>
    </div>
</nav>