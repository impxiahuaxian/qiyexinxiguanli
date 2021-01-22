$(function () {
	$.ajax({
		url : "Employgg/" + $("#employgg_employggNo_edit").val() + "/update",
		type : "get",
		data : {
			//employggNo : $("#employgg_employggNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (employgg, response, status) {
			$.messager.progress("close");
			if (employgg) { 
				$("#employgg_employggNo_edit").val(employgg.employggNo);
				$("#employgg_employggNo_edit").validatebox({
					required : true,
					missingMessage : "请输入员工编号",
					editable: false
				});
				$("#employgg_positionObj_positionId_edit").combobox({
					url:"../Position/listAll",
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
				$("#employgg_employggPhotoImg").attr("src", "../" +employgg.employggPhoto);
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

	$("#employggModifyButton").click(function(){ 
		if ($("#employggEditForm").form("validate")) {
			$("#employggEditForm").form({
			    url:"Employgg/" +  $("#employgg_employggNo_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                    } 
			    }
			});
			//提交表单
			$("#employggEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		}
	});
});
