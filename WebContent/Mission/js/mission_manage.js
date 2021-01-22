var mission_manage_tool = null; 
$(function () { 
	initMissionManageTool(); //建立mission管理对象
	mission_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#mission_manage").datagrid({
		url : 'Mission/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "missionName",
		sortOrder : "desc",
		toolbar : "#mission_manage_tool",
		columns : [[
			{
				field : "missionName",
				title : "任务名称",
				width : 140,
			},
			{
				field : "missionPeople",
				title : "负责人",
				width : 140,
			},
		]],
	});

	$("#missionEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#missionEditForm").form("validate")) {
					//验证表单 
					if(!$("#missionEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#missionEditForm").form({
						    url:"Mission/" + $("#mission_missionName_edit").val() + "/update",
						    onSubmit: function(){
								if($("#missionEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#missionEditDiv").dialog("close");
			                        mission_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#missionEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#missionEditDiv").dialog("close");
				$("#missionEditForm").form("reset"); 
			},
		}],
	});
});

function initMissionManageTool() {
	mission_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#mission_manage").datagrid("reload");
		},
		redo : function () {
			$("#mission_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#mission_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#missionQueryForm").form({
			    url:"Mission/OutToExcel",
			});
			//提交表单
			$("#missionQueryForm").submit();
		},
		remove : function () {
			var rows = $("#mission_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var missionNames = [];
						for (var i = 0; i < rows.length; i ++) {
							missionNames.push(rows[i].missionName);
						}
						$.ajax({
							type : "POST",
							url : "Mission/deletes",
							data : {
								missionNames : missionNames.join(","),
							},
							beforeSend : function () {
								$("#mission_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#mission_manage").datagrid("loaded");
									$("#mission_manage").datagrid("load");
									$("#mission_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#mission_manage").datagrid("loaded");
									$("#mission_manage").datagrid("load");
									$("#mission_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#mission_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Mission/" + rows[0].missionName +  "/update",
					type : "get",
					data : {
						//missionName : rows[0].missionName,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (mission, response, status) {
						$.messager.progress("close");
						if (mission) { 
							$("#missionEditDiv").dialog("open");
							$("#mission_missionName_edit").val(mission.missionName);
							$("#mission_missionName_edit").validatebox({
								required : true,
								missingMessage : "请输入任务名称",
								editable: false
							});
							$("#mission_missionPeople_edit").val(mission.missionPeople);
							$("#mission_missionPeople_edit").validatebox({
								required : true,
								missingMessage : "请输入负责人",
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
