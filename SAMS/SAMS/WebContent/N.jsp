<%@pagelanguage="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPEhtmlPUBLIC"-//W3C//DTDHTML4.01Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<metahttp-equiv="Content-Type" content="text/html;charset=UTF-8"><title>登录图书管理系统</title>
<styletype="text/css">
<!--
.STYLE1{
font-size:36px;
font-weight:bold;
}
-->
</style>
</head>
<body>
<formname="form1" method="post" action="valid.jsp">
<tablewidth="400" height="120" border="1"align="center">
<caption>
<spanclass="STYLE1">登录图书管理系统</span>
</caption>
<tr>
<tdwidth="166">用户名：</td>
<tdwidth="318"><inputname="username"type="text"id="username"></td></tr>
<tr>
<td>密码：</td>
<td><inputname="password"type="password"id="password"></td>
</tr>
<tr>
<tdcolspan="2"align="center"><inputtype="submit"name="Submit"value="登录">
<inputtype="reset"name="Submit2"value="取消"></td>
</tr>
</table>
</form>
</body>
</html>
