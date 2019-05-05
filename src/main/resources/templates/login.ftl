<#assign title="登录 - ">
<#include "header.ftl">
<div class="container-fluid">
    <div class="posts">
    <div class="text-content text-default">
    <h2>登录</h2>
    <form action="login_con" method="post">
        <div class="default-font">用户名</div><input type="text" name="userName"><br>
        <div class="default-font">密码</div><input type="password" name="password"><br>
        <input type="hidden" name="rememberMe" value="0">
        <div class="default-font"><input type="checkbox" name="rememberMe" value="1">记住用户</div>
        <button type="submit" class="default-font">登录</button>
    </form>
    </div>
    </div>
</div>
<#include "footer.ftl">