<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name=”viewport” content=”width=device-width, initial-scale=1, maximum-scale=1″>
<title>Kyungpook National University</title>
<link rel="stylesheet" type="text/css" href="css/login.css" />
<style>

</style>
</head>
<body  style="overflow:auto;">
<center>
	<frameset rows="20%,*" frameborder="yes" border="1" framespacing="1" style="width:100%; height:225px; overflow:auto;">
		<frame>
		<jsp:include page="Attendance.jsp"/>
		</frame>
		<frame>
		<jsp:include page="AttendancePartCheck.jsp"/>
		</frame>
</frameset> 

</center>
</body>
</html>
<script>
    var  a=screen.availWidth;
      var   b=screen.availHeight;
      window.moveTo(0,0)
      window.resizeTo(a,b)
 
</script>
<!jsp:include page="AttendanceCheck.jsp" flush="true">  		  	
<!jsp:param name="" value=""/>