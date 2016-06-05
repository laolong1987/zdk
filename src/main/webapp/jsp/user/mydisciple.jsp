<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/9
  Time: 20:41
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
    <title>我的徒弟</title>
</head>
<body>
<header>
    <div class="z-top-tit">
        <span onclick="gopage(5)"></span>
        <p>我的徒弟</p>
    </div>
</header>
<dl class="z-stu-list">
    <dt>
        <span>昵称</span>
        <span>关系</span>
        <span>收益</span>
    </dt>
    <c:forEach items="${list}" var="list">
        <dd>
        <span>${list.nick_name}</span>
        <span>徒弟</span>
        <span><i class="z-red">0</i>元</span>
        </dd>
    </c:forEach>
    <%--<dd>--%>
        <%--<span>张四</span>--%>
        <%--<span>徒孙(张三)</span>--%>
        <%--<span><i class="z-red">20</i>元</span>--%>
    <%--</dd>--%>
</dl>
<!-- 底部 -->
<jsp:include page="footer.jsp" />
<!-- /底部 -->
</body>
</html>