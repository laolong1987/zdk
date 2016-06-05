<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/8
  Time: 21:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta content="telephone=no,email=no" name="format-detection">
    <title>个人中心</title>
</head>
<body>
<header>
    <div class="z-top-tit">
        <span onclick="gopage('5')"></span>
        <p>个人中心</p>
    </div>
</header>
<!-- 个人信息 -->
<div class="z-information z-gn-bg">
    <dl>
        <dt><img src="${sessionScope.user.headimgurl}" alt=""></dt>
        <dd>
            <p>${sessionScope.user.nick_name}</p>
            <span>ID:${sessionScope.user.id}</span>
            <span><fmt:formatNumber value="${sessionScope.user.current_money/100}" /></span>
        </dd>
        <dt class="z-payments">
            <a href="taskdetail"><span>收支明细</span><a>
        </dt>
    </dl>
</div>
<!-- /个人信息 -->
<div class="z-turn-meg z-alone-meg">
    <ul>
        <li>
            <a href="mytask">
            <p class="z-my-task">我的任务</p>
            </a>
        </li>
        <li class="z-li-border"></li>
        <li><a href="mydisciple">
            <p class="z-my-stu">我的徒弟</p>
            </a>
        </li>
    </ul>
</div>

<ul class="z-p-list">
    <li>
        <a href="cashup">
            <em class="z-icon-card"></em>
            <p>我要提现</p>
            <span></span>
        </a>
    </li>
    <%--<li>--%>
        <%--<a href="#">--%>
            <%--<em class="z-icon-meg"></em>--%>
            <%--<p>消息提醒</p>--%>
            <%--<span></span>--%>
        <%--</a>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<a href="#">--%>
            <%--<em class="z-icon-ques"></em>--%>
            <%--<p>常见问题</p>--%>
            <%--<span></span>--%>
        <%--</a>--%>
    <%--</li>--%>
    <%--<li>--%>
        <%--<a href="#">--%>
            <%--<em class="z-icon-idea"></em>--%>
            <%--<p>意见反馈</p>--%>
            <%--<span></span>--%>
        <%--</a>--%>
    <%--</li>--%>
    <li>
        <a href="referee">
            <em class="z-icon-idea"></em>
            <p>推荐人</p>
            <span></span>
        </a>
    </li>
</ul>
<!-- 底部 -->
<jsp:include page="footer.jsp" />
<!-- /底部 -->
</body>
</html>