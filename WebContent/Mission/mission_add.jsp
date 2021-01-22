<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mission.css" />
<div id="missionAddDiv">
	<form id="missionAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">任务名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="mission_missionName" name="mission.missionName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">负责人:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="mission_missionPeople" name="mission.missionPeople" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="missionAddButton" class="easyui-linkbutton">添加</a>
			<a id="missionClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Mission/js/mission_add.js"></script> 
