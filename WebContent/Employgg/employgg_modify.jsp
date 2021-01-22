<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_user_logstate.jsp"/>
<!DOCTYPE html>
<html>
<head>
<title>修改页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employgg.css" />
</head>
<body style="margin:0px; font-size:14px; background-image:url(../images/bg.jpg); background-position:bottom; background-repeat:repeat;">
<div id="employgg_editDiv">
	<form id="employggEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employgg_employggNo_edit" name="employgg.employggNo" value="<%=request.getParameter("employggNo") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employgg_name_edit" name="employgg.name" style="width:200px" />

			</span>

		</div>
	
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employgg_employggDay_edit" name="employgg.employggDay" />

			</span>

		</div>
		
		<div>
			<span class="label">公告:</span>
			<span class="inputControl">
				<textarea id="employgg_employggDesc_edit" name="employgg.employggDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="employggModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" ></script>
<script src="${pageContext.request.contextPath}/Employgg/js/employgg_modify.js"></script> 
</body>
</html>
