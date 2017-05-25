<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Kyungpook National University</title>
<link rel="stylesheet" type="text/css" href="css/a.css" />
<style>
div {
	margin-top: 40px;
	margin-left: 25px;
	color: #fff;
	width: 1200px;
	height: 40px;
}

input {
	width: 180px;
	height: 43px;
	margin-left: 20px;
	font-size: 30px;
	color: #fff;
	background: #000;
	border: none;
	outline: none;
}
</style>
</head>
<body>
	<center>
		<br> <br> <br> <br> <br> <br> <br>
		<div>
			<h1>00:00:00:000</h1>
		</div>
		<br>
		<br>
		<input type="button" value="start" class="but" id='add' />
		<br> 
		<br>
	</center>
</body>

</html>
<script type="text/javascript">
	//2.实现秒表功能，实现时 分 秒 毫秒 点击1开始计时，2结束计时；
	//var oDiv = document.getElementsByTagName('div')[0];
	//var Btn1 = document.getElementsByTagName('input')[0];
	//var Btn2 = document.getElementsByTagName('input')[1];
	/***
	var ms = 0;
	var sec = 0;
	var min = 0;
	var hour = 0;
	Btn1.onclick = function() {
		timer = setInterval(function() {
			var str_hour = hour;
			var str_min = min;
			var str_sec = sec;
			if (hour < 10) {
				str_hour = "0" + hour;
			}
			if (min < 10) {
				str_min = "0" + min;
			}
			if (sec < 10) {
				str_sec = "0" + sec;
			}
			var time = str_hour + ':' + str_min + ':' + str_sec + ':' + ms;
			oDiv.innerHTML = "<h1>" + time + "</h1>";
			ms = ms + 50;
			if (ms > 999) {
				ms = 0;
				sec++;
			}
			if (sec > 59) {
				sec = 0;
				min++;
			}
			if (min > 59) {
				min = 0;
				hour++;
			}
		}, 50);
	}
	Btn2.onclick = function() {
		clearInterval(timer);
	}**/
</script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script>
	$(function() {

		function ajax(p) {
			console.log("url = " + p.url);
			var xhr = new XMLHttpRequest(), pr = [];
			p = p || {};
			if (p.params) {
				for ( var key in p.params) {
					pr.push(key + "=" + p.params[key]);
				}
				pr = pr.join("&");
			}
			pr = pr || "";
			p.method = p.method || "GET";

			if (p.method == "GET") {
				if (pr) {
					p.url = [ p.url, pr ].join(p.url.indexOf("?") > -1 ? "&"
							: "?");
				}
			}

			xhr.onreadystatechange = p.onload;
			xhr.open(p.method, p.url, p.async);
			xhr.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			xhr.send(p.method == "POST" ? pr : "");
		}

		$('#add').click(function() {
			var oDiv = document.getElementsByTagName('div')[0];
			var Btn2 = document.getElementsByTagName('input')[1];
			var ms = 0;
			var sec = 0;
			var min = 0;
			var hour = 0;
			var Btn1 = document.getElementsByTagName('input')[0];
			if (Btn1.value == "start") {
				var data = "start socket!";
				$.ajax({
					type : "POST",
					url : "http://localhost:8080/SAMS/group/rpi/socket/",
					//url: "http://localhost:8080/SAMS",  
					data : data,
					cache : true,
					success : function(d) {
						alert("已经开启socket功能可以出席");
						timer = setInterval(function() {
							var str_hour = hour;
							var str_min = min;
							var str_sec = sec;
							if (hour < 10) {
								str_hour = "0" + hour;
							}
							if (min < 10) {
								str_min = "0" + min;
							}
							if (sec < 10) {
								str_sec = "0" + sec;
							}
							var time = str_hour + ':' + str_min + ':' + str_sec + ':' + ms;
							oDiv.innerHTML = "<h1>" + time + "</h1>";
							ms = ms + 50;
							if (ms > 999) {
								ms = 0;
								sec++;
							}
							if (sec > 59) {
								sec = 0;
								min++;
							}
							if (min > 59) {
								min = 0;
								hour++;
							}
						}, 50);
						Btn1.value = "stop";
					},
					error : function(e) {
						alert("出席socket开启失败请检查你的ip地址和端口号");

					}
				});
			} else {
				var data = "stop socket!";
				$.ajax({
					type : "POST",
					url : "http://localhost:8080/SAMS/group/rpi/socket/",
					//url: "http://localhost:8080/SAMS",  
					data : data,
					cache : true,
					success : function(d) {
						alert("已经关闭树莓派功能停止出席");
						clearInterval(timer);
						Btn1.value = "start";
						window.location.href = "AttendanceCheck.jsp";

					},
					error : function(e) {
						alert("出席socket关闭失败请检查你的代码和树莓派");
					}
				});
			}
		});

	});
</script>
