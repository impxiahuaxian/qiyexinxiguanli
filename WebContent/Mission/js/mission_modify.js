$(function () {
	$.ajax({
		url : "Mission/" + $("#mission_missionName_edit").val() + "/update",
		type : "get",
		data : {
			//missionName : $("#mission_missionName_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (mission, response, status) {
			$.messager.progress("close");
			if (mission) { 
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

	$("#missionModifyButton").click(function(){ 
		if ($("#missionEditForm").form("validate")) {
			$("#missionEditForm").form({
			    url:"Mission/" +  $("#mission_missionName_edit").val() + "/update",
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
			$("#missionEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		}
	});
});
