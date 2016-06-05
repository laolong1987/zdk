<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 2016/5/7
  Time: 13:33
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
    <title>高分/快速任务</title>
</head>
<body>
<header>
    <div class="z-top-tit">
        <span onclick="gopage(5)"></span>
        <p>${typename}</p>
    </div>
</header>
<div class="z-task-list">
    <ul>
        <c:forEach var="list" items="${tasklist}">


            <c:if test="${1==list.status || 2==list.status}">
                <li onclick="show('${list.id}','${list.name}','${list.logoimg}','${list.total}','-1','${list.imgfile}','${list.utid}')" >
            </c:if>
            <c:if test="${0==list.status}">
                <li onclick="show('${list.id}','${list.name}','${list.logoimg}','${list.total}','0','${list.imgfile}','${list.utid}')" >
            </c:if>
                <img src="${ctx}/images/taskimg/${list.logoimg}" alt="">
                <div class="z-t-content">
                    <strong>${list.name}</strong>
                    <%--<span class="z-gray">剩余：${list.total}份</span>--%>
                    <p class="z-gray">任务类型：${list.keyword}</p>
                    <p id="lidescription${list.id}" class="z-red">${list.description}</p>
                </div>
                <%--<div class="z-btn-right">--%>
                    <%--<span>进行中...</span>--%>
                    <%--<a class="z-btn-green" href="###" onclick="show('${list.id}','${list.name}','${list.logoimg}','${list.total}')" >赚取<fmt:formatNumber value="${list.unitprice/100}" type="currency"/>元</a>--%>
                <%--</div>--%>
                <div class="z-t-side">
                    <c:if test="${1==list.status}">
                        <span>审批中...</span>
                    </c:if>
                    <c:if test="${0==list.status}">
                        <span>进行中...</span>
                    </c:if>
                    <c:if test="${2==list.status}">
                        <span>已完成...</span>
                    </c:if>
                    <p><em>￥</em><fmt:formatNumber value="${list.unitprice/100}"/>元</p>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>
<!-- 底部 -->
<jsp:include page="footer.jsp" />
<!-- /底部 -->
<!-- 弹框 -->
<div class="w-black-bg"></div>
<div class="w-tk-code">
    <em class="w-tk-close">X</em>
    <dl>
        <dd>
            <img id="logoimg" src="" alt="">

        </dd>
        <dd class="w-dd-wth">
            <p id="name">众调网公众号</p>
            <span id="total" class="z-gray"></span>
            <p id="description" class="z-red"></p>
            <%--<span class="z-gray">2016-12-11&nbsp;&nbsp;12:00</span>--%>
        </dd>
    </dl>
    <div class="w-tk-from">
        <%--<div class="w-input-div">众调网地址：ssssss.com</div>--%>
        <div id="content" class="w-textare-div"></div>

                <form id="taskf" action="uptask" method="post" enctype="multipart/form-data" >
                    <input type="hidden" id="utid" name="utid">
                    <input type="hidden" id="taskid" name="taskid">
                    <input type="hidden" id="tasktype" name="tasktype" value="${tasktype}">
                    <div id="bd">
                    </div>
                    <div id="bd2">
                        <div id="newUpload2">
                            <input type="file" name="file">
                        </div>
                        <input type="button" id="btn_add2" value="增加一行" >
                    </div>
                </form>

        <a id="starttask" class="w-btn-longgreen01" href="###" onclick="start()">开始任务</a>
        <a id="submittask" class="w-btn-longgreen01" href="###" onclick="tijiao()">提交任务</a>
        <a class="w-btn-longgreen02" href="javascript:void(0);" onclick="closewindow()" >关闭</a>
    </div>
</div>
<!-- /弹框 -->
<script>
    var j = 1;
    $(function(){
        $('#starttask').hide();
        $('#submittask').hide();
        $('#bd2').hide();

        var tbtn = $('.z-btn-right').find('.z-btn-green');
        tbtn.click(function() {
            $('.w-black-bg').show();
            $('.w-tk-code').css({top: ($(window).height() - $('.w-tk-code').outerHeight()) / 2 + $(document).scrollTop()}).show();
        });
        $('.w-tk-close').click(function() {
            closewindow();
        });
        $("#btn_add2").click(function(){
            document.getElementById("newUpload2").innerHTML+='<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>';
            j = j + 1;
        });
    })

    function closewindow(){
        $('.w-black-bg').hide();
        $('.w-tk-code').hide();
        $('#starttask').hide();
        $('#submittask').hide();
        $('#bd2').hide();
        $("#content").html("");
        $("#taskid").val("");
    }

    function show(id,name,logoimg,total,status,imgfile,utid){
        $("#content").html("");
        $("#taskid").val(id);
        $("#utid").val(utid);
        $("#name").text(name);
        $("#total").text("剩余："+total+"份");
        $("#description").text($("#lidescription"+id).text());
        $("#ImgPr").attr('src','${ctx}/images/taskimg/'+logoimg);
        getcontent(id);

        //生成自动表单
        if('0'==status){
            $('#submittask').show();
            addtext(id);
            if(1==imgfile){
                $("#bd2").show();
            }
        }else{
//            $('#starttask').show();
//            $('#starttask').show();
        }

        $('.w-black-bg').show();
        $('.w-tk-code').css({top: ($(window).height() - $('.w-tk-code').outerHeight()) / 2 + $(document).scrollTop()}).show();
    }

    function getcontent(id) {
        $.ajax({
            type : "POST",
            url : "getcontent",
            data : {taskid:id},
            dataType : "text",
            success : function(result) {
               $("#content").html(result);
            }
        });
    }

    function start(){
        $.ajax({
            type : "POST",
            url : "starttask",
            data : {taskid: $("#taskid").val()},
            dataType : "text",
            success : function(result) {
//                closewindow();
                location.reload();
            }
        });
    }

    function addtext(taskid){
        var str='';
        $.ajax({
            type : "POST",
            url : "gettaskcheck",
            data : {taskid:taskid},
            dataType : "json",
            success : function(result) {
                $.each(result, function(index, content)
                {
                    if(content.type==1){
                        str +="<p>"+content.name+"</p><input type='text' id='b_"+content.id+"' name='b_"+content.id+"' max='"+content.maxlength+"'>";
                    }
                });

                $("#bd").html(str);
            }
        });

    }

    function tijiao(){
        document.getElementById('taskf').submit();
    }
    function del_2(o){
        document.getElementById("newUpload2").removeChild(document.getElementById("div_"+o));
    }

</script>
</body>
</html>