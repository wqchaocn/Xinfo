<#assign title="分享 - ">
<#include "header.ftl">
<div class="container-fluid">
    <div class="posts">
        <div class="text-default text-content-a">
            <h2>分享</h2>
            <#if user?exists>
                <form action="addNews_con" method="post" enctype="multipart/form-data">
                    <div class="text-content">标题</div><textarea rows="1" cols="40" name="title"></textarea><br>
                    <div class="text-content">链接</div><textarea rows="1" cols="40"  name="link"></textarea><br>
                    <div class="text-content">图像</div><input type="file" name="file"><br>
                    <button type="submit" class="default-font">确定</button>
                </form>
            <#else>
                <div class="default-font">未登录!</div>
            </#if>

        </div>
    </div>
</div>
<#include "footer.ftl">