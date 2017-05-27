<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html lang="en">
<head>

<meta charset="UTF-8">
<title>Kyungpook National University</title>
<link rel="stylesheet" type="text/css" href="css/login.css" />
</head>
<body>
	<div id="login">
		<form>
			<button type="button" id='addd' class="but">Attend</button>
			<br> <br> <br> <br>
			<button type="button" id='a' class="but" onclick="toB()">Attend
				check</button>
		</form>
	</div>
</body>

</html>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script>
$(function() {
	$('#addd').click(function() {
			//var data = "start check!";
			//$.ajax({
				//type : "POST",
				//url : "http://localhost:8080/SAMS/group/rpi/check",
				//url: "http://localhost:8080/SAMS",  
				//data : data,
				//cache : true,
				//success : function(d) {
					window.location.href = "AttendanceView.jsp";
				//},
				//error : function(e) {
					//alert("error!");
				//}
			//});
		//});
});
});
function toB() {

		window.location.href = "AttendanceCheck.jsp";

	}
</script>