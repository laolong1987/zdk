<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/9
  Time: 18:50
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
    <title>分享收徒</title>
</head>
<body>
<header>
    <div class="z-top-tit">
        <span onclick="gopage(5)"></span>
        <p>分享收徒</p>
    </div>
</header>
<dl class="z-ewm">
    <dt><img src="${ctx}/images/weixincode.jpg" alt=""></dt>
    <dd>扫描二维码分享收徒,关注公众号后 在推荐人内填写用户ID:${sessionScope.user.id} 绑定师徒关系</dd>
</dl>
<!-- 底部 -->
<jsp:include page="footer.jsp" />
<!-- /底部 -->
</body>
</html>