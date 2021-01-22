<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employgg.css" /> 

<div id="employgg_manage"></div>
<div id="employgg_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="employgg_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="employgg_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="employgg_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="employgg_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="employgg_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="employggQueryForm" method="post">
			编号：<input type="text" class="textbox" id="employggNo" name="employggNo" style="width:110px" />
			姓名：<input type="text" class="textbox" id="name" name="name" style="width:110px" />
			发布日期：<input type="text" id="employggDay" name="employggDay" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="employgg_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="employggEditDiv">
	<form id="employggEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employgg_employggNo_edit" name="employgg.employggNo" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employgg_name_edit" name="employgg.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">发布日期:</span>
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
	</form>
</div>
<script type="text/javascript" src="Employgg/js/employgg_manage.js"></script> 
