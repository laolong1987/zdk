<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/5
  Time: 20:20
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
    <title>首页</title>
</head>
<body>
<header>
    <img class="z-logo" src="${ctx}/images/z-logo.png" alt="">
</header>
<!-- 个人信息 -->
<div class="z-information">
    <dl>
        <dt><img src="${sessionScope.user.headimgurl}" alt=""></dt>
        <dd>
            <p>${sessionScope.user.nick_name}</p>
            <span class="z-gray">累计收益</span>
            <span class="z-red"><fmt:formatNumber value="${sessionScope.user.total_money/100}" type="currency"/></span>
        </dd>
        <dd>
            <p>ID:${sessionScope.user.id}</p>
            <span class="z-gray">账户余额</span>
            <span class="z-red"><fmt:formatNumber value="${sessionScope.user.current_money/100}" type="currency"/></span>
        </dd>
    </dl>
</div>
<!-- /个人信息 -->
<div class="z-notice">公告：最新任务上线，快去参加。</div>
<!-- 价格状态展示 -->
<div class="z-turn-meg">
    <ul>
        <li>
            <p>ID:654209<span class="z-green">10秒前</span></p>
            <p>赚取<em class="z-red">0.8</em>元已到帐</p>
            <p>微信支付[<em class="z-red">8元</em>]已到帐</p>
        </li>
        <li class="z-li-border"></li>
        <li>
            <p>ID:654209<span class="z-green">10秒前</span></p>
            <p>赚取<em class="z-red">0.8</em>元已到帐</p>
            <p>微信支付[<em class="z-red">822元</em>]已到帐</p>
        </li>
    </ul>
</div>
<!-- /价格状态展示 -->
<div class="z-banner"><img src="${ctx}/images/z_banner01.jpg" alt=""></div>
<!-- 底部 -->
<jsp:include page="footer.jsp" />
<!-- /底部 -->
</body>
</html>