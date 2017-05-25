<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Kyungpook National University</title>
<link href="css/style1.css" rel="stylesheet">
</head>
<script src="js/DateTime2.js" language="javascript">
</script>
<body>
 <%
	if(session.getAttribute("username")==null){
		out.println("<script language='javascript'>history.back();</script>");
	}
%>
</body>
</html>
