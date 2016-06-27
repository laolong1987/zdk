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

<div  class="container">
    <h3>申请推广员</h3>
    <form name="adduser" action="addspread" method="post" id="adduser">
        <div class="form-group">
            <label for="company">公司名称（非企业组织填写团队名称）</label>
            <input type="text" class="form-control" id="company" maxlength="50" name="company">
        </div>
        <div class="form-group">
            <label for="area">推广区域</label>
            <input type="text" class="form-control" id="area" maxlength="10" name="area">
        </div>
        <div class="form-group">
            <label for="name">联系人姓名</label>
            <input type="text" class="form-control" id="name" maxlength="20" name="name">
        </div>
        <div class="form-group">
            <label for="phone">联系人手机</label>
            <input type="text" class="form-control" id="phone" maxlength="11" name="phone">
        </div>
        <div class="form-group">
            <label for="weixin">联系人微信</label>
            <input type="text" class="form-control" id="weixin" maxlength="20" name="weixin">
        </div>
        <div class="form-group">
            <label for="email">联系人邮箱</label>
            <input type="text" class="form-control" id="email" maxlength="30" name="email">
        </div>
        <button type="button" class="btn btn-info" onclick="submitdata()">提交</button>
    </form>
</div>
</body>
<script type="text/javascript">
    function submitdata(){
        if(yanz()){
            document.getElementById('adduser').submit();
        }
    }

    function yanz(){
        var name=$("#name").val();
        var phone=$("#phone").val();
        var weixin=$("#weixin").val();
        var area=$("#area").val();
        var company=$("#company").val();
        var email=$("#email").val();
        if(""==name){
            alert("姓名不能为空！");
            return false;
        }else if(""==phone){
            alert("手机号不能为空！");
            return false;
        }else if(""==weixin){
            alert("微信号不能为空");
            return false;
        }else if(""==area){
            alert("区域不能为空");
            return false;
        }else if(""==company){
            alert("公司不能为空");
            return false;
        }else if(""==email){
            alert("邮箱不能为空");
            return false;
        }
        return true;
    }
</script>
</html>