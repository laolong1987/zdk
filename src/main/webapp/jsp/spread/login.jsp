<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/6/20
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>推广员登录</title>
</head>
<script type="text/javascript">
    function loginValidate(){
        var username=$("#username").val();
        var pwd=$("#password").val();
        if(username=="" || username==null){
            alert("用户名不可为空");
            return false;
        }
        if(pwd==""|| pwd==null){
            alert("用户名不可为空");
            return false;
        }
        return true;
    }
</script>
<body>
<div class="top-content">
    <div class="container">
        <div class="row">
            <div class="col-sm-6 col-sm-offset-3 form-box">
                <div class="form-top">
                    <div class="form-top-left">
                        <img src="${ctx}/images/logo.png" style="width: 200px">
                        <!--<h3>众调客推广员登录</h3>-->
                        <!--<p>Enter your username and password to log on:</p>-->
                    </div>
                    <div class="form-top-right">
                        <i class="fa fa-key"></i>
                    </div>
                </div>
                <div class="form-bottom">
                    <form role="form" action="spreadlogin2" method="post" class="login-form">
                        <div class="form-group">
                            <label class="sr-only" for="username">Username</label>
                            <input type="text" name="username" placeholder="用户名" class="form-username form-control" id="username">
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="password">Password</label>
                            <input type="password" name="password" placeholder="密码" class="form-password form-control" id="password">
                        </div>
                        <div class="form-group">
                            <button type="submit" onclick="return loginValidate()" class="btn btn-info btn-lg btn-block" >登录</button>
                        </div>
                        <div class="form-group">
                            <a href="spreadregister">申请推广员</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

</body>

</html>