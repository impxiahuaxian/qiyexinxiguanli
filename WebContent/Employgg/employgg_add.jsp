<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employgg.css" />
<div id="employggAddDiv">
	<form id="employggAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employgg_employggNo" name="employgg.employggNo" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employgg_name" name="employgg.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">发布日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employgg_employggDay" name="employgg.employggDay" />

			</span>

		</div>
		<div>
			<span class="label">公告栏:</span>
			<span class="inputControl">
				<textarea id="employgg_employggDesc" name="employgg.employggDesc" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="employggAddButton" class="easyui-linkbutton">添加</a>
			<a id="employggClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Employgg/js/employgg_add.js"></script> 
