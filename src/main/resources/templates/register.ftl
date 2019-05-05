<#assign title="注册 - ">
<#include "header.ftl">
<div class="container-fluid">
    <div class="posts">
        <div class="text-content text-default">
        <h2>注册</h2>
        <form action="register_con" method="post">
            <div>用户名</div><input type="text" name="userName"><br>
            <div>密码</div><input type="password" name="password"><br>
            <input type="hidden" name="rememberMe" value="0">
            <div><input type="checkbox" name="rememberMe" value="1">记住用户</div>
            <button type="submit">注册</button>
        </form>
        </div>
    </div>
</div>
<#include "footer.ftl">