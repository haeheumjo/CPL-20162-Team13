<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Kyungpook National University</title>
</head>
<%
		//if(((String)session.getAttribute("username"))==null){
				//response.sendRedirect("index.html");
		//}
		//session.removeAttribute("kuest");
%>
<frameset rows="86,367" cols="*" frameborder="no" id="top">
		<frame src="top.jsp" scrolling="no" name="topframe">
  <frameset rows="*" cols="153,391" frameborder="yes" bordercolor="#31659C" id="bottom">
    <frameset rows="*" cols="*,10">
      <frame src="management.jsp" scrolling="no" noresize name="leftframe">
      <frame src="ctrl.htm" scrolling="no">
    </frameset>
    <frameset rows="*,48%" cols="*" framespacing="1" frameborder="yes" border="1" >
		<frame src="home.jsp" scrolling="yes" bordercolor="#33CCFF" name="bottomFrame">
		<frame src="foot.jsp" name="mainName" frameborder="yes" scrolling="yes">
	</frameset>
  </frameset>
</frameset>
<noframes><body>
</body></noframes>
</html>
