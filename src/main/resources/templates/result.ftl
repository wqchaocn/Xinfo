<#include "header.ftl">
<div class="container-fluid">
    <div class="posts">
        <div class="text-default text-content">
        <div class="default-font">
        <#assign result>${result}</#assign>
            <#assign json=result?eval />
            ${json.msg} !<br>
            <a class="default-link" href="/">返回首页</a>
        </div>
        </div>
    </div>
</div>
<#include "footer.ftl">