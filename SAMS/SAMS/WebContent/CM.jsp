<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String pa = request.getParameter("pa"); //페이지 번호 얻기
	if(pa==null || pa.equals("")) pa = "1";
	
	try {
		//database 연결
		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sam_test","root","1q2w");
		
		//페이지작업을 위한 게시물 개수 출력
		pstmt = conn.prepareStatement("select count(*) from course");
		rs = pstmt.executeQuery();
		rs.next();
		
		int recTotal = rs.getInt(1); //전체 개시물 개수
		int pageSize = 10; //한 페이지당 출력할 게시물 개수
		int totalPage = recTotal / pageSize; //전체 페이지의 개수 계산
		int re = recTotal % pageSize; //페이지를 넘어가는 글이 있을 경우 전체 페이지 개수 +1
		if(re!=0) totalPage +=1;
		rs.close();
		pstmt.close();
		
		pstmt = conn.prepareStatement("select * from course");
		rs = pstmt.executeQuery();
		
		//각 페이지 마다 시작하는 게시물번호를 미리 게산
		int startNum = (Integer.parseInt(pa) - 1) * pageSize + 1; //1페이지부터 시작하기 때문에 +1
		for(int p = 1; p < startNum; p++) {
			rs.next();	// 레코드 포인터 위치 : 0, 3, 6
		}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />

<style type="text/css">

table.imagetable {
    font-family: verdana,arial,sans-serif;
    font-size:14px;
    color:#333333;
    border-width: 1px;
    border-color: #999999;
    border-collapse: collapse;
    clear:both;
}
table.imagetable th {
    background:#A8D1E7;
    border-width: 1px;
    padding: 8px;
  
    border: 1px solid #ffffff;
   
}
table.imagetable td {
    background:#FFFFFF url('cell-grey.jpg');
    border-width: 1px;
    padding: 8px;
   
     border: 1px solid #d2d2d2;
    
}
a {  
    color: #339;  
    text-decoration: none;  
}
.blue {  
    color: #d9eef7;  
    border: solid 1px #0076a3;  
    background: #0095cd;  
    background: -webkit-gradient(linear, left top, left bottom, from(#00adee), to(#0078a5));  
    background: -moz-linear-gradient(top,  #00adee,  #0078a5);  
    filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#00adee', endColorstr='#0078a5');  
}    

.button {
   
    border: none;
    color: white;
    padding : 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 17px;
   	text-weight:bold;
    margin: 4px 2px;
    -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
    cursor: pointer;
    width : 145px;
    height : 38px;
    line-height:38px;
}

.button2 {
	-moz-box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	-webkit-box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	box-shadow:inset 0px 1px 0px 0px #bbdaf7;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #79bbff), color-stop(1, #378de5));
	background:-moz-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-webkit-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-o-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:-ms-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background:linear-gradient(to bottom, #79bbff 5%, #378de5 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#79bbff', endColorstr='#378de5',GradientType=0);
	background-color:#79bbff;
	-moz-border-radius:6px;
	-webkit-border-radius:6px;
	border-radius:6px;
	border:1px solid #84bbf3;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:15px;
	font-weight:bold;
	padding:6px 10px;
	text-decoration:none;
	text-shadow:0px 1px 0px #528ecc;
}

.button2:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #378de5), color-stop(1, #79bbff));
	background:-moz-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-webkit-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-o-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:-ms-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background:linear-gradient(to bottom, #378de5 5%, #79bbff 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#378de5', endColorstr='#79bbff',GradientType=0);
	background-color:#378de5;
}
.button2:active {
	position:relative;
	top:1px;
}
</style>
<body>
	<div id="container" align="center"><br>
	<h2>** CLASS LIST **</h2>
		</div>
	<a class="button2" href="jsp009_database_insert.html">class add</a>
	<a class="button2" href="management.jsp">back</a><br/>
		<table id="generatedTable" class="imagetable" width="100%" height="100%" style ="border=2; border="1" bordercolor="red" 
align="center" display: none;">
			 <thead>  
			<tr align="center">
				<th><b>C_ID</b></th>
				<th><b>C_NAME</b></th>
				<!--<th><b>Mac</b></th>  -->
				<th><b>P_ID</b></th>
				<th><b>CR_NO</b></th>
				<th><b>SESSION</b></th>
				<th><b>STAR</b></th>
				<th><b>END</b></th>
				<th><b>DAY</b></th>
			
						
			</tr>
			</thead>
			<tbody>  
                <tr id="cloneTr" align="center">  
                   <td></td>  
                   <td></td>  
                   <td></td>
                   <td></td>
                   <td></td>
                   <td></td>
                   <td></td>
                   <td></td>
                 
                          
                 </tr>  
		<%
			int i = 1;
			while(rs.next() && i <= pageSize) {		 // pageSize 만큼 반복
		%>
		<tr>
			<td><%=rs.getString("c_id") %></td>
			<td><%=rs.getString("c_name") %></td>
			<td><%=rs.getString("p_id") %></td>
			<td><%=rs.getString("cr_no") %></td>
			<td><%=rs.getString("session") %></td>
			<td><%=rs.getString("start") %></td>
			<td><%=rs.getString("end") %></td>
			<td><%=rs.getString("day") %></td>
	
		</tr>
		<%		
			i++;
			}
		%>
		
		<!-- 페이지 번호 작업 -->
		<tr>
			<td colspan="9" style="text-align: center;">
				<%
					// 페이지 번호에 링크 걸기
					if(totalPage > 0) {
						for(int pageNo = 1; pageNo <= totalPage; pageNo++) {
				%>
					<!-- 페이지 번호를 누를경우 해당 페이지를 출력하기 위해 페이지 번호를 가지고 이동 -->
					[<a href="jsp009_database_list.jsp?pa=<%=pageNo %>"><%=pageNo %></a>]
				<%			
						}
					}
				%>
			</td>
		</tr>
	</table>
</body>
</html>

<%
} catch(Exception e) {
	System.out.println("처리 오류 : " + e);
} finally {
	if(rs != null) rs.close();
	if(pstmt != null) pstmt.close();
	if(conn != null) conn.close();
}
%>