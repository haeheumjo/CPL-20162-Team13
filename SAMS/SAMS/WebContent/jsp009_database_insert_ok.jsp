<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String s_id = request.getParameter("s_id");
	String s_name = request.getParameter("s_name");
	String year = request.getParameter("year");
	String dept = request.getParameter("dept");
	// 값이 하나라도 없으면 insert 화면으로 되돌아감
	if(s_id.equals("") || s_id == null || s_name.equals("") || s_name == null || year.equals("") || year == null|| dept.equals("") || dept == null )
	{
		response.sendRedirect("jsp009_database_insert.html");
		return;
	}
	
	//입력자료 검사를 try catch문으로 해결
	//입력된 su 나 dan 이 숫자가 아닐경우 오류가 나게되고 catch문을 실행하게됨
	
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try { 
		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/class","root","1q2w");

		//데이터 삽입을 위해 마지막 코드번호 가져오기
		pstmt = conn.prepareStatement("select from student");
		//rs = pstmt.executeQuery();
		//rs.next();
		//pstmt.close();
		//int code = rs.getInt(1)+1; //추가할 code번호
		
		//데이터 삽입
		pstmt = conn.prepareStatement("insert into student values(?,?,?,?)");
		pstmt.setString(1, s_id);
		pstmt.setString(2, s_name);
		pstmt.setString(3, year);	
		pstmt.setString(4, dept);
		pstmt.executeUpdate();
		response.sendRedirect("jsp009_database_list.jsp");//삽입 완료 시 리스트 페이지로 이동
		
	} catch(Exception e) {
		System.out.println("추가 오류 : " + e);
	} finally {
		if(rs!=null) rs.close();
		if(pstmt!=null)pstmt.close();
		if(conn!=null)conn.close();
	}
%>
