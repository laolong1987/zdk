<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/9
  Time: 20:57
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
    <title>我要提现</title>
</head>
<body>
<header>
    <div class="z-top-tit">
        <span  onclick="gopage(5)"></span>
        <p>我要提现</p>
    </div>
</header>
<div class="z-divline">
    <ul>
        <li><p class="z-dp-line">余额:<fmt:formatNumber value="${sessionScope.user.current_money/100}" /></li>
        <li><p class="z-dp-line">提现金额:<input type="text" ></p></li>
    </ul>
    <a class="z-btn-green" href="#">立即提交</a>
</div>
<!-- 底部 -->
<jsp:include page="footer.jsp" />
<!-- /底部 -->
</body>
</html>