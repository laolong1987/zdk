<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/6/23
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>推广任务</title>
</head>
<style>
    .taskbox{
        background-color: white;margin: 10px;
    }
</style>
<body style="background-color:#f5f7f6">
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
            <li class="active" ><a href="tasklist">推广任务</a></li>
            <li ><a href="task">提交</a></li>
            <li ><a href="searchtask">查询</a></li>
            <li ><a href="showupdatepwd">修改密码</a></li>
        </ul>
    </div>

</nav>


<div class="container">
    <header>
        <h3>推广任务</h3>商务电话：15026660342
    </header>
    <div class="row">

<c:forEach items="${list}" var="list">
    <div class="col-sm-6 col-xs-12">
        <div class="row taskbox">
            <div class="col-sm-4 col-xs-4">
                <img src="${ctx}/images/taskimg/${list.logoimg}" style="width: 100%">
            </div>
            <div class="col-sm-8  col-xs-8">
                <p>${list.name}</p>
                <p>${list.keyword}</p>
                <p>${list.description}</p>
            </div>
        </div>
    </div>
</c:forEach>
</div>

</body>
</html>