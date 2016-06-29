<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/6/20
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>推广员注册</title>
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
            <li ><a href="tasklist">推广任务</a></li>
            <li ><a href="task">提交</a></li>
            <li ><a href="searchtask">查询</a></li>
            <li class="active"><a href="showupdatepwd">修改密码</a></li>
        </ul>
    </div>

</nav>
<div  class="container">
    <h3>密码修改</h3>
    <form name="upfrom" action="updatepwd" method="post" id="upfrom">
        <div class="form-group">
            <label for="pwd1">新密码</label>
            <input type="password" class="form-control" id="pwd1" maxlength="20" name="pwd1">
        </div>
        <div class="form-group">
            <label for="pwd2">再次确认新密码</label>
            <input type="password" class="form-control" id="pwd2" maxlength="20" name="pwd2">
        </div>
        <button type="button" class="btn btn-info" onclick="submitdata()">提交</button>
    </form>
</div>
</body>
<script type="text/javascript">
    function submitdata(){
        var pwd1=$("#pwd1").val();
        var pwd2=$("#pwd2").val();
        if(yanz()){
            $.ajax({
                type : "POST",
                url : "updatepwd",
                data : {pwd1:pwd1,pwd2:pwd2},
                dataType : "text",
                success : function(result) {
                    alert("修改密码成功，请重新登录！");
                    window.location.href="${ctx}/login/spreadlogin";
                }
            });
        }
    }

    function yanz(){
        var pwd1=$("#pwd1").val();
        var pwd2=$("#pwd2").val();
        if(""==pwd1){
            alert("密码不能为空！");
            return false;
        }else if(""==pwd2){
            alert("确认密码不能为空！");
            return false;
        }else if(pwd2!=pwd1){
            alert("2次密码不一致");
            return false;
        }else if(pwd1.length<6){
            alert("密码不能小于6位");
            return false;
        }else if(pwd2.length<6){
            alert("公司不能为空");
            return false;
        }
        return true;
    }
</script>
</html>