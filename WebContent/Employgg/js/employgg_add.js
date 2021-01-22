$(function () {
	$("#employgg_employggNo").validatebox({
		required : true, 
		missingMessage : '请输入编号',
	});

	$("#employgg_name").validatebox({
		required : true, 
		missingMessage : '请输入姓名',
	});


	$("#employgg_employggDay").datebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});


	//单击添加按钮
	$("#employggAddButton").click(function () {
		//验证表单 
		if(!$("#employggAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
		} else {
			$("#employggAddForm").form({
			    url:"Employgg/add",
			    onSubmit: function(){
					if($("#employggAddForm").form("validate"))  { 
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
                        $("#employggAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                    }
			    }
			});
			//提交表单
			$("#employggAddForm").submit();
		}
	});

	//单击清空按钮
	$("#employggClearButton").click(function () { 
		$("#employggAddForm").form("clear"); 
	});
});
