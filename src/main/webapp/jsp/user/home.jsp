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
    <img class="z-logo" src="${ctx}/images/z-logo.png" alt="">
</header>
<!-- 个人信息 -->
<div class="z-information z-gn-bg">
    <dl>
        <dt><img src="${sessionScope.user.headimgurl}" alt=""></dt>
        <dd>
            <p>${sessionScope.user.nickname}</p>
            <span>ID:${sessionScope.user.id}</span>
            <span><fmt:formatNumber value="${sessionScope.user.current_money/100}" type="currency"/></span>
        </dd>
        <dt class="z-payments">
            <span>收支明细</span>
        </dt>
    </dl>
</div>
<!-- /个人信息 -->
<div class="z-turn-meg z-alone-meg">
    <ul>
        <li>
            <p class="z-my-task">我的任务</p>
        </li>
        <li class="z-li-border"></li>
        <li>
            <p class="z-my-stu">我的徒弟</p>
        </li>
    </ul>
</div>

<ul class="z-p-list">
    <li>
        <a href="#">
            <em class="z-icon-card"></em>
            <p>我要提现</p>
            <span></span>
        </a>
    </li>
    <li>
        <a href="#">
            <em class="z-icon-meg"></em>
            <p>消息提醒</p>
            <span></span>
        </a>
    </li>
    <li>
        <a href="#">
            <em class="z-icon-ques"></em>
            <p>常见问题</p>
            <span></span>
        </a>
    </li>
    <li>
        <a href="#">
            <em class="z-icon-idea"></em>
            <p>意见反馈</p>
            <span></span>
        </a>
    </li>
</ul>
<!-- 底部 -->
<footer>
    <ul>
        <li class="${c1}" onclick="gopage('1')">
            <p class="z-icon-fast"></p>
            <span>快速任务</span>
        </li>
        <li class="${c2}" onclick="gopage('2')">
            <p class="z-icon-high"></p>
            <span>高分任务</span>
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
</body>
</html>