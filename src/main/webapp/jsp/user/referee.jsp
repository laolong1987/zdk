<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/9
  Time: 19:25
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
    <title>推荐人</title>
</head>
<body>
<header>
    <div class="z-top-tit">
        <span onclick="gopage(5)"></span>
        <p>推荐人</p>
    </div>
</header>
<div class="z-divline">
    <c:if test="${0==isref}">
        <form action="addreferee" method="post" id="addreferee" >
            <input id="infoid" name="infoid" value="${infoid}" type="hidden">
            <ul>
                <li><p class="z-dp-line">推荐人ID<input type="text" id="referee_id" name="referee_id"></p></li>
            </ul>
            <a class="z-btn-green" href="#" onclick="tijiao()">立即提交</a>
        </form>
    </c:if>
    <c:if test="${1==isref}">
            <ul>
                <li><p class="z-dp-line">推荐人ID:${rid}</p></li>
                <li><p class="z-dp-line">推荐人昵称：${name}</p></li>
            </ul>
    </c:if>
</div>
<!-- 底部 -->
<jsp:include page="footer.jsp" />
<!-- /底部 -->
<script>
    function tijiao(){
        document.getElementById('addreferee').submit();
    }

</script>
</body>
</html>