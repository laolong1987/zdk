<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/25
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>推广员数据提查询</title>
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src='${ctx}/js/jquery.min.js'></script>
    <script type="text/javascript" src='${ctx}/js/bootstrap.min.js'></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">众调客</a>
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>

    </div>
    <div class="collapse navbar-collapse" id="example-navbar-collapse">
        <ul class="nav navbar-nav">
            <li><a href="tasklist">推广任务</a></li>
            <li><a href="task">提交</a></li>
            <li class="active"><a href="searchtask">查询</a></li>
            <li ><a href="showupdatepwd">修改密码</a></li>
        </ul>
    </div>

</nav>
<div  class="container">
    <h3>推广员数据查询</h3>
    <form action="searchtask" method="post">
        <div class="form-group">
            <label for="name">推广员姓名(必填)</label>
            <input type="text" class="form-control" id="name" name="name">
        </div>
        <div class="form-group">
            <label for="phone">手机号(必填)</label>
            <input type="text" class="form-control" id="phone" name="phone">
        </div>
        <button type="submit" class="btn btn-default">查询</button>
    </form>
    <div class="table-responsive">
        <table class="table">
            <caption>数据列表</caption>
            <thead>
            <tr>
                <th>单子名称</th>
                <th>提交日期</th>
                <th>状态</th>
                <th>备注</th>
                <th>文件下载</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="list" >
                <tr>
                    <td>${list.taskname}</td>
                    <td><fmt:formatDate value="${list.create_time}" type="both"/></td>
                    <c:if test="${0==list.status}">
                    <td>未审核</td>
                    </c:if>
                    <c:if test="${1==list.status}">
                        <td>已审核</td>
                    </c:if>
                    <td>${list.remark}</td>
                    <td>
                        <c:if test="${!empty list.filename2}">
                            <a href="${ctx}/file/${list.filename2}">下载</a>
                       </c:if>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>