var employgg_manage_tool = null; 
$(function () { 
	initEmployggManageTool(); //建立Employgg管理对象
	employgg_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#employgg_manage").datagrid({
		url : 'Employgg/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "employggNo",
		sortOrder : "desc",
		toolbar : "#employgg_manage_tool",
		columns : [[
			{
				field : "employggNo",
				title : "编号",
				width : 140,
			},
			{
				field : "name",
				title : "姓名",
				width : 140,
			},
			{
				field : "employggDay",
				title : "发布日期",
				width : 140,
			},{
				field : "employggDesc",
				title : "公告",
				width : 140,
			},
		]],
	});

	$("#employggEditDiv").dialog({
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
				if ($("#employggEditForm").form("validate")) {
					//验证表单 
					if(!$("#employggEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#employggEditForm").form({
						    url:"Employgg/" + $("#employgg_employggNo_edit").val() + "/update",
						    onSubmit: function(){
								if($("#employggEditForm").form("validate"))  {
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
			                        $("#employggEditDiv").dialog("close");
			                        employgg_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#employggEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#employggEditDiv").dialog("close");
				$("#employggEditForm").form("reset"); 
			},
		}],
	});
});

function initEmployggManageTool() {
	employgg_manage_tool = {
		init: function() {
			$.ajax({
				url : "Position/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#positionObj_positionId_query").combobox({ 
					    valueField:"positionId",
					    textField:"positionName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{positionId:0,positionName:"不限制"});
					$("#positionObj_positionId_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#employgg_manage").datagrid("reload");
		},
		redo : function () {
			$("#employgg_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#employgg_manage").datagrid("options").queryParams;
			queryParams["employggNo"] = $("#employggNo").val();
			queryParams["positionObj.positionId"] = $("#positionObj_positionId_query").combobox("getValue");
			queryParams["name"] = $("#name").val();
			queryParams["employggDay"] = $("#employggDay").datebox("getValue"); 
			$("#employgg_manage").datagrid("options").queryParams=queryParams; 
			$("#employgg_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#employggQueryForm").form({
			    url:"Employgg/OutToExcel",
			});
			//提交表单
			$("#employggQueryForm").submit();
		},
		remove : function () {
			var rows = $("#employgg_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var employggNos = [];
						for (var i = 0; i < rows.length; i ++) {
							employggNos.push(rows[i].employggNo);
						}
						$.ajax({
							type : "POST",
							url : "Employgg/deletes",
							data : {
								employggNos : employggNos.join(","),
							},
							beforeSend : function () {
								$("#employgg_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#employgg_manage").datagrid("loaded");
									$("#employgg_manage").datagrid("load");
									$("#employgg_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#employgg_manage").datagrid("loaded");
									$("#employgg_manage").datagrid("load");
									$("#employgg_manage").datagrid("unselectAll");
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
			var rows = $("#employgg_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Employgg/" + rows[0].employggNo +  "/update",
					type : "get",
					data : {
						//employggNo : rows[0].employggNo,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (employgg, response, status) {
						$.messager.progress("close");
						if (employgg) { 
							$("#employggEditDiv").dialog("open");
							$("#employgg_employggNo_edit").val(employgg.employggNo);
							$("#employgg_employggNo_edit").validatebox({
								required : true,
								missingMessage : "请输入员工编号",
								editable: false
							});
							$("#employgg_positionObj_positionId_edit").combobox({
								url:"Position/listAll",
							    valueField:"positionId",
							    textField:"positionName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#employgg_positionObj_positionId_edit").combobox("select", employgg.positionObjPri);
									//var data = $("#employgg_positionObj_positionId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#employgg_positionObj_positionId_edit").combobox("select", data[0].positionId);
						            //}
								}
							});
							$("#employgg_name_edit").val(employgg.name);
							$("#employgg_name_edit").validatebox({
								required : true,
								missingMessage : "请输入姓名",
							});
							$("#employgg_sex_edit").val(employgg.sex);
							$("#employgg_sex_edit").validatebox({
								required : true,
								missingMessage : "请输入性别",
							});
							$("#employgg_employggPhoto").val(employgg.employggPhoto);
							$("#employgg_employggPhotoImg").attr("src", employgg.employggPhoto);
							$("#employgg_employggDay_edit").datebox({
								value: employgg.employggDay,
							    required: true,
							    showSeconds: true,
							});
							$("#employgg_schoolRecord_edit").val(employgg.schoolRecord);
							$("#employgg_schoolRecord_edit").validatebox({
								required : true,
								missingMessage : "请输入学历",
							});
							$("#employgg_employggDesc_edit").val(employgg.employggDesc);
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
