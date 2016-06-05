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
    <title>任务审批</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
</head>
<link href="${ctx}/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript">

    var gridManager = null;
    var saveForm = null;
    var saveWindow = null;
    var noteWindow = null;
    var um = null;
    var typeData = null;
    var icon = '${ctx}/ligerUI/skins/icons/pager.gif';
    var manager, g;
    $(function() {
        $("#searchbtn").ligerButton({
            click : function() {
                search();
            }
        });
        setGrid();
        $("#pageloading").hide();
        f_initGrid();
    });

    function setGrid(){
        //表格
        gridManager = $("#maingrid").ligerGrid({
            columns : [
                {
                    display: '操作',
                    isSort: false,
                    isExport: false,
                    width: 50,
                    align : 'center',
                    render: function (rowdata, rowindex, value)
                    {
                        return "<a href='#'  onclick='shownote("+JSON.stringify(rowdata)+");' >审核</a>";
                    }
                },
                {
                    display : '任务名',
                    name : 'name',
                    align : 'center',
                    width : 200,
                    minWidth : 30
                },{
                    display : '用户ID',
                    name : 'userid',
                    align : 'center',
                    width : 50,
                    minWidth : 60
                },{
                    display : '姓名',
                    name : 'nick_name',
                    align : 'center',
                    width : 100,
                    minWidth : 60
                },{
                    display : '提交时间',
                    name : 'updatetime',
                    align : 'center',
                    width : 200,
                    minWidth : 60
                },{
                    display : '状态',
                    name : 'status',
                    minWidth : 10,
                    align : 'center',
                    width : 50,
                    render : function(rowdata, rowindex, value) {
                        if(rowdata.status==1){
                            return "已提交"
                        }else if(rowdata.status==0){
                            return "进行中";
                        }else if(rowdata.status==2){
                            return "已审核";
                        }else if(rowdata.status==3){
                            return "已驳回";
                        }else{
                            return ""
                        }
                    }
                }
            ],
            pageSize : 15,
            url : "${ctx}/admin/task/searchchecklist",
            rownumbers : true,
            selectRowButtonOnly : true,
            isScroll : true
        });
    }

    function f_initGrid()
    {
        g = manager = $("#maingrid2").ligerGrid({
            columns: [
                { display: '名称', name: 'name',width:100,
                    render: function (item)
                    {
                        if (parseInt(item.check_id) != 0) return item.name;
                        else return '附件';
                    }
                },
                { display: '值', name: 'type',width:300,
                    render: function (item)
                    {
                        if (parseInt(item.check_id) != 0) return item.value;
                        else  return "<a href='${ctx}/images/taskimg/"+item.value+"' target='_parent' >下载</a>";
                    }
                }
            ],
            isScroll: false, checkbox:false,rownumbers:true,usePager:false,
            width:700
        });
    }

    function search(){
        var parms = $("#queryForm").serializeJson();
        gridManager.set("parms", parms);
        gridManager.loadData();
    }

    function closeWindow() {
        saveWindow.hide();
    }

    function shownote(data){

        if (noteWindow == null) {
            noteWindow = $.ligerDialog.open({
                title : "note",
                target : $("#noteWindow"),
                width : 600,
                height : 'auto',
                isResize : true
            });
        }
        $("#upnoteid").val(data.id);
        addtext(data.userid,data.taskid);
        noteWindow.show();
    }

    function addtext(userid,taskid){
       var manager = $("#maingrid2").ligerGetGridManager();
        manager.loadData();
        $.ajax({
            type : "POST",
            url : "getcheckdata",
            data : {userid:userid,taskid:taskid},
            dataType : "json",
            async:false,
            success : function(result) {
                $.each(result, function(index, content){
                    manager.addRow({
                        name: content.name,
                        value: content.value,
                        check_id : content.check_id
                    });
                });
            }
        });

    }

    function addnote(type){
        var upid = $("#upnoteid").val();
        var note = $("#note").val();

        $.ajax({
            type : "POST",
            url : "update",
            data : {userid:userid,taskid:taskid},
            dataType : "json",
            async:false,
            success : function(result) {
                $.each(result, function(index, content){
                    manager.addRow({
                        name: content.name,
                        value: content.value,
                        check_id : content.check_id
                    });
                });
            }
        });
        closenoteWindow();
    }

    function closenoteWindow() {
        noteWindow.hide();
        $("#note").val("");
        cleanFile();
    }

    function cleanFile(){
        var file = $("#file");
        file.after(file.clone().val(""));
        file.remove();
    }

</script>
</head>
<body style="padding: 5px;">
<div class="l-loading" style="display:block" id="pageloading"></div>
<form id="queryForm">
    <div class="l-panel-search">
        <div class="l-panel-search-item">推广员姓名</div>
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
<div id="noteWindow" style="width:99%; margin:3px; display:none;">
    <div class="l-dialog-body" style="width: 100%;">
        <div id="maingrid2" ></div>
        <input type="hidden" id="upnoteid" name="upnoteid" />
        <div class="l-dialog-buttons">
            <div class="l-dialog-buttons-inner">
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="closenoteWindow();">取消</div>
                </div>
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="addnote('3');">审核驳回</div>
                </div>
                <div class="l-dialog-btn">
                    <div class="l-dialog-btn-l"></div>
                    <div class="l-dialog-btn-r"></div>
                    <div class="l-dialog-btn-inner" onclick="addnote('2');">审核通过</div>
                </div>
                <div class="l-clear"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>