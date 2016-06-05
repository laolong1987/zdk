<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/9
  Time: 18:59
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
    <title>收支明细</title>
</head>
<body>
<header>
    <div class="z-top-tit">
        <span onclick="gopage(5)"></span>
        <p>收支明细</p>
    </div>
</header>
<ul class="z-pay-list">
    <li>
        <span>全部收入</span>
        <i><fmt:formatNumber value="${sessionScope.user.total_money/100}" />元</i>
    </li>
    <li>
        <span>任务收入</span>
        <i><fmt:formatNumber value="${sessionScope.user.task_money/100}" />元</i>
    </li>
    <li>
        <span>徒弟提成</span>
        <i><fmt:formatNumber value="${sessionScope.user.master_money/100}" />元</i>
    </li>
    <li>
        <span>成功兑换</span>
        <i><fmt:formatNumber value="${sessionScope.user.convert_money/100}" />元</i>
    </li>
</ul>
<dl class="z-stu-list z-pay-stlist">
    <%--<dd>--%>
        <%--<span>20160414</span>--%>
        <%--<span>回答问卷</span>--%>
        <%--<span>10元</span>--%>
    <%--</dd>--%>
    <%--<dd>--%>
        <%--<span>20160509</span>--%>
        <%--<span>注册</span>--%>
        <%--<span>20元</span>--%>
    <%--</dd>--%>
</dl>
<!-- 底部 -->
<jsp:include page="footer.jsp" />
<!-- /底部 -->
</body>
</html>