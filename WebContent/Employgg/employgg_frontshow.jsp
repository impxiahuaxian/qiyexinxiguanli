﻿<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.shuangyulin.po.Employgg" %>
<%@ page import="com.shuangyulin.po.Position" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的positionObj信息
    List<Position> positionList = (List<Position>)request.getAttribute("positionList");
    Employgg employgg = (Employgg)request.getAttribute("employgg");

%>
<!DOCTYPE html>
<html>
<head><TITLE>查看</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:14px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</head>
<body>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding="10px" width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>编号:</td>
    <td width=70%><%=employgg.getEmployggNo() %></td>
  </tr>
  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><%=employgg.getName() %></td>
  </tr>

  <tr>
    <td width=30%>发布日期:</td>
    <td width=70%><%=employgg.getEmployggDay() %></td>
  </tr>

  <tr>
    <td width=30%>公告:</td>
    <td width=70%><%=employgg.getEmployggDesc() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</body>
</html>
