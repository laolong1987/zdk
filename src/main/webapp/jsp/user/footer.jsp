<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/9
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 底部 -->
<footer>
    <ul>
        <li class="${c1}" onclick="gopage('1')">
            <p class="z-icon-fast"></p>
            <span>快速任务</span>
        </li>
        <li class="${c2}" onclick="gopage('2')">
            <p class="z-icon-high"></p>
            <span>推广任务</span>
        </li>
        <li class="${c3}" onclick="gopage('3')">
            <p class="z-icon-share"></p>
            <span>分享收徒</span>
        </li>
        <li class="${c4}" onclick="gopage('4')">
            <p class="z-icon-center"></p>
            <span>个人中心</span>
        </li>
    </ul>
</footer>
<!-- /底部 -->
<script>
    function gopage(p){
        if(1==p){
            window.location.href="${ctx}/zdk/task1";
        }else if(2==p){
            window.location.href="${ctx}/zdk/task2";
        }else if(3==p){
            window.location.href="${ctx}/zdk/share";
        }else if(4==p){
            window.location.href="${ctx}/zdk/home";
        }else if(5==p){
            window.location.href="${ctx}/zdk/index";
        }
    }

</script>