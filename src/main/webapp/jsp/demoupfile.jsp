<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html ;charset=utf-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src='${ctx}/js/jquery.min.js'></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<html>
<head>
</head>
<script type="text/javascript">
    (function($){
        $.fn.serializeJSON=function(){
            var serializeObj={};
            $(this.serializeArray()).each(function(){
                serializeObj[this.name]=this.value;
            });
            return serializeObj;
        };
    })(jQuery);

    function upload(){

        $.ajaxFileUpload({
            url : "upload",//用于文件上传的服务器端请求地址
            secureuri : false,//一般设置为false
            data: $("#upform").serializeJSON(),
            fileElementId : 'file',//文件上传控件的id属性  <input type="file" id="file" name="file" />
            dataType : 'JSON',//返回值类型 一般设置为json

            success : function(msg, status) //服务器成功响应处理函数
            {
                //   alert(msg);
                var start = msg.indexOf(">");
                if (start != -1) {
                    var end = msg.indexOf("<", start + 1);
                    if (end != -1) {
                        msg = msg.substring(start + 1, end);
                    }
                }
                // alert(msg);
            },
            error : function(data, status, e)//服务器响应失败处理函数
            {

            }
        });
    }
</script>

<body>
<form id="upform" name="upform" action="upload" method="post" enctype="multipart/form-data">
    <input type="text" name="name" id="name" value="zhangsan"/>
    <input type="file" name="file" id="file"/>
    <input type="submit" value="upload"/>
    <input type="button" value="ajax提1交" onclick="upload()"/>
    <br>
    <input id="d11" type="text" readonly="readonly" onClick="WdatePicker()"  class="Wdate" />
</form>

<br />
<form id="goform" name="goform" action="http://114.80.203.124:8080/chevrolet/loginFromYB.do" method="post" >
    <input type="text" name="doss_id" id="doss_id" value="D2W9SE"/>
    <input type="text" name="timestamp" id="timestamp" value="1429687846746"/>
    <input type="text" name="sign" id="sign" value="5DC4C4859E72C21C6CC2A5133060EAF4"/>
    <input type="text" name="redirect_uri" id="redirect_uri" value="www.baidu.com"/>
    <input type="submit" value="跳转"/>
</form>
<br/>
多文件上传
<form id="upform2" name="upform2" action="upload2" method="post" enctype="multipart/form-data">
    <input type="file" name="file1" id="file1"/>
    <input type="file" name="file2" id="file2"/>
    <input type="submit" value="upload"/>
</form>

</body>
</html>
