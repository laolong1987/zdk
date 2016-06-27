<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/25
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>推广员数据提交表单</title>
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src='${ctx}/js/jquery.min.js'></script>
    <script type="text/javascript" src='${ctx}/js/bootstrap.min.js'></script>
    <script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
    <script type="text/javascript">
        (function($){
            $.fn.serializeJSON=function()
                var serializeObj={};
                $(this.serializeArray()).each(function(){
                    serializeObj[this.name]=this.value;
                });
                return serializeObj;
            };
        })(jQuery);


        function submitdata(){
            if(yanz()){
                $('#myModal').modal('show');
            $.ajaxFileUpload( {
                url : "submit_task",
                secureuri : false,//一般设置为false
                data:$("#dataForm").serializeJSON(),
                fileElementId :"file",
                type : "POST",
                success : function(result) {
                    $('#myModal').modal('hide');
                     alert("提交成功");
                }
            });
            }
        }

        function yanz(){
            var name=$("#name").val();
            var phone=$("#phone").val();
            var s=document.dataForm.file.value;
            if(""==name){
                alert("推广员姓名不能为空！");
                return false;
            }else if(""==phone){
                alert("手机号不能为空！");
                return false;
            }else if(""==s){
                alert("请选择上传文件");
                return false;
            }
            return true;
        }
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
            <li  ><a href="tasklist">推广任务</a></li>
            <li class="active"><a href="task">提交</a></li>
            <li ><a href="searchtask">查询</a></li>
        </ul>
    </div>

</nav>
<div  class="container">
    <h3>推广员数据提交表单</h3>
    <form id="dataForm" name="dataForm">
        <input type="hidden" name="token" value="${token}">
        <div class="form-group">
            <label for="name">推广员姓名（必填）</label>
            <input type="text" class="form-control" id="name" name="name">
        </div>
        <div class="form-group">
            <label for="phone">手机号（必填）</label>
            <input type="text" class="form-control" id="phone" name="phone">
        </div>
        <div class="form-group">
            <label for="teamname">公司/团队名称</label>
            <input type="text" class="form-control" id="teamname" name="teamname">
        </div>
        <div class="form-group">
            <label for="alipay">支付宝号</label>
            <input type="text" class="form-control" id="alipay" name="alipay">
        </div>
        <div class="form-group">
            <label for="qq">QQ</label>
            <input type="text" class="form-control" id="qq" name="qq">
        </div>
        <div class="form-group">
            <label for="taskname">单子名称</label>
            <input type="text" class="form-control" id="taskname" name="taskname">
        </div>
        <div class="form-group">
            <label for="file">上传文件（必选）</label>
            <input type="file" id="file" name="file">
            <!--<p class="help-block">Example block-level help text here.</p>-->
        </div>
        <button type="button" class="btn btn-default" onclick="submitdata()">提交</button>
    </form>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body" style="text-align: center;height: 50px">
                提交中...
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

</body>
</html>