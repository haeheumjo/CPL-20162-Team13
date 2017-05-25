<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"content="text/html;charset=UTF-8">
<meta http-equiv="refresh"content="3;URL=bookList.jsp">
<title>删除图书</title>
</head>
<body>
<%
//["3","4","6"]
String[] ids=request.getParameterValues("ids");
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/class","root","1q2w");
PreparedStatement ps=con.prepareStatement("delete from student where S_ID=?");

for(int i=0; i<ids.length; i++){
ps.setInt(1,Integer.parseInt(ids[i]));
ps.execute();
}
con.close();
%>
正在删除图书，3秒后自动跳转......
</body>
</html>