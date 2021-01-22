$(function () {
	$("#mission_missionName").validatebox({
		required : true, 
		missingMessage : '请输入任务名',
	});

	$("#mission_missionPeople").validatebox({
		required : true, 
		missingMessage : '请输入负责人',
	});

	//单击添加按钮
	$("#missionAddButton").click(function () {
		//验证表单 
		if(!$("#missionAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		} else {
			$("#missionAddForm").form({
			    url:"Mission/add",
			    onSubmit: function(){
					if($("#missionAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $("#missionAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                    }
			    }
			});
			//提交表单
			$("#missionAddForm").submit();
		}
	});

	//单击清空按钮
	$("#missionClearButton").click(function () { 
		$("#missionAddForm").form("clear"); 
	});
});
