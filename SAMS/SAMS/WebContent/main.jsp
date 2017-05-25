<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="UTF-8">
<title>Kyungpook National University</title>
<link rel="stylesheet" type="text/css" href="css/login.css" />
</head>
<body>
	<div id="login">
		<form>
			<button type="button" id='add' class="but" onclick="toA()">Attend</button>
			<br> <br> <br> <br>
			<button type="button" id='add' class="but" onclick="toB()">Attend
				check</button>
		</form>
	</div>
</body>

</html>
<script type="text/javascript">
	function toA() {
		
		window.location.href = "Attendance.jsp";

	}

	function toB() {

		window.location.href = "AttendanceCheck.jsp";

	}
</script>