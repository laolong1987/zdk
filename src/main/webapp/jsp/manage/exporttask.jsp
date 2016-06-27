<%--
  Created by IntelliJ IDEA.
  User: gaoyang
  Date: 16/3/6
  Time: 下午12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="head.jsp"%>
<html>
<head>
    <title>任务管理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<link href="${ctx}/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${ctx}/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/umeditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/js/uploadPreview.js"></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
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

    var gridManager = null;
    var saveForm = null;
    var saveWindow = null;
    var checktypelist=null;
    var icon = '${ctx}/ligerUI/skins/icons/pager.gif';
    var manager, g;
    var myDate = new Date();
    var t=myDate.getFullYear()+'-'+myDate.getMonth()+'-'+myDate.getDate()+' '+myDate.getHours()+':'+myDate.getMinutes();
    $(function() {
        $("#searchbtn").ligerButton({
            click : function() {
                search();
            }
        });
        setGrid();
        $("#pageloading").hide();
//        f_initGrid();
    });

    function setGrid(){
        //表格
        gridManager = $("#maingrid").ligerGrid({
            columns : [
                {
                    display: '操作',
                    isSort: false,
                    isExport: false,
                    width: 100,
                    align : 'center',
                    render: function (rowdata, rowindex, value)
                    {
                        var a="<a href='exporttaskdata?taskid="+rowdata.id+"&checktype="+rowdata.checktype+"' >导出</a>";
                        var b="<a href='#'  onclick='showWindow("+JSON.stringify(rowdata)+");' >导入</a>";
                        return a+"  "+b;
                    }
                },
                {
                    display : '任务名称',
                    name : 'name',
                    align : 'center',
                    width : 200,
                    minWidth : 30
                },{
                    display : '描述',
                    name : 'description',
                    align : 'left',
                    minWidth : 60
                }, {
                    display : '状态',
                    name : 'status',
                    minWidth : 10,
                    align : 'center',
                    width : 50,
                    render : function(rowdata, rowindex, value) {
                        if(rowdata.status==1){
                            return "有效"
                        }else if(rowdata.status==0){
                            return "无效";
                        }else{
                            return ""
                        }
                    }

                },
                {
                    display : '开始时间',
                    name : 'begintime',
                    align : 'center',
                    width : 150
                },  {
                    display : '结束时间',
                    name : 'endtime',
                    align : 'center',
                    width : 150
                }
            ],
            pageSize : 15,
            url : "${ctx}/admin/task/searchlist",
            rownumbers : true,
            checkbox : false,
            selectRowButtonOnly : true,
            isScroll : true
        });
    }



    function search(){
        var parms = $("#queryForm").serializeJson();
        gridManager.set("parms", parms);
        gridManager.loadData();
    }

    function renderDescription(rowdata, index, value) {
        if(value != null) {
            return "<div title='" + value + "'>" + value + "<div>";
        }
    }

    function showWindow(data){
        if (saveWindow == null) {
            saveWindow = $.ligerDialog.open({
                target : $("#addWindow"),
                width : 800
            });
        }
        saveWindow.show();
        $("#taskid").val(data.id);
    }

    function closeWindow() {
        saveWindow.hide();
    }

    function closewindow(){
        $.ligerDialog.hide();
    }

    function submitdata(){
        $.ajaxFileUpload( {
            url : "uptaskfile",
            secureuri : false,//一般设置为false
            data:$("#dataForm").serializeJSON(),
            fileElementId :"file",
            type : "POST",
            success : function(result) {
                search();
                $.ligerDialog.success("处理成功", "提示", function(){
                    $.ligerDialog.hide();
                });
            }
        });
    }
</script>
</head>
<body style="padding: 5px;">
<div class="l-loading" style="display:block" id="pageloading"></div>
<form id="queryForm">
    <div class="l-panel-search">
        <div class="l-panel-search-item">任务名称</div>
        <div class="l-panel-search-item">
            <input type="text" id="queryname" name="queryname"  class="liger-textbox" />
        </div>
        <div class="l-panel-search-item">
            <input type="button" id="searchbtn" value="查询" />
        </div>
    </div>
</form>
<!-- 表格 -->
<div id="maingrid" style="margin:0; padding:0"></div>
<div style="display:none;"></div>
<div id="addWindow" style="width:99%; margin:3px; display:none;">
    <div class="l-dialog-body" style="width: 100%;">
        <form id="dataForm" name="dataForm">
            <input id="taskid" name="taskid" type="hidden" />
            <table class="table_css">
                <tr>
                    <td class="hd" width="50">
                        上传图片
                    </td>
                    <td class="bd" width="auto" colspan="3">
                        <input type="file" id="file" name="file"/>
                    </td>
                </tr>
                <tr>
                    <td class="hd"></td>
                    <td class="bd" colspan="3">
                        <input type="button" value="提交" onclick="submitdata()"
                               class="l-button l-button-submit" />
                        <input type="button" value="关闭" onclick="closewindow()"
                               class="l-button l-button-submit" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>