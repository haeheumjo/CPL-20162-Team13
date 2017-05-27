<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//KR" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="refresh" content="3">
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />

<style type="text/css">
table.imagetable {
	font-family: verdana, arial, sans-serif;
	font-size: 14px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
	clear: both;
}

table.imagetable th {
	background: #A8D1E7;
	border-width: 1px;
	padding: 8px;
	border: 1px solid #ffffff;
}

table.imagetable td {
	background: #FFFFFF url('cell-grey.jpg');
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
	background: -webkit-gradient(linear, left top, left bottom, from(#00adee),
		to(#0078a5));
	background: -moz-linear-gradient(top, #00adee, #0078a5);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#00adee',
		endColorstr='#0078a5');
}

.button {
	border: none;
	color: white;
	padding: 10px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 17px;
	text-weight: bold;
	margin: 4px 2px;
	-webkit-transition-duration: 0.4s; /* Safari */
	transition-duration: 0.4s;
	cursor: pointer;
	width: 145px;
	height: 38px;
	line-height: 38px;
}

.button2 {
	-moz-box-shadow: inset 0px 1px 0px 0px #bbdaf7;
	-webkit-box-shadow: inset 0px 1px 0px 0px #bbdaf7;
	box-shadow: inset 0px 1px 0px 0px #bbdaf7;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #79bbff
		), color-stop(1, #378de5));
	background: -moz-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background: -webkit-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background: -o-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background: -ms-linear-gradient(top, #79bbff 5%, #378de5 100%);
	background: linear-gradient(to bottom, #79bbff 5%, #378de5 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#79bbff',
		endColorstr='#378de5', GradientType=0);
	background-color: #79bbff;
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	border: 1px solid #84bbf3;
	display: inline-block;
	cursor: pointer;
	color: #ffffff;
	font-family: Arial;
	font-size: 15px;
	font-weight: bold;
	padding: 6px 10px;
	text-decoration: none;
	text-shadow: 0px 1px 0px #528ecc;
}

.button2:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #378de5
		), color-stop(1, #79bbff));
	background: -moz-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background: -webkit-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background: -o-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background: -ms-linear-gradient(top, #378de5 5%, #79bbff 100%);
	background: linear-gradient(to bottom, #378de5 5%, #79bbff 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#378de5',
		endColorstr='#79bbff', GradientType=0);
	background-color: #378de5;
}

.button2:active {
	position: relative;
	top: 1px;
}
</style>


<title>Kyungpook National University</title>
<script src="/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script src="http://code.jquery.com/jquery-latest.min.js"
		type="text/javascript"></script>
	<script type="text/javascript"> 
//	$(function () {  
//        setInterval("SetAtd()", 3000); //每隔5秒刷新点击量  
//    });
//	function SetAtd() {
/*		$.ajax({  
		     type: "GET", 
		     url: "http://localhost:8080/SAMS/entry/attencecheck/all",  
		     cache: true, 
		     success: function(d){                    
		    	var s=d.indexOf("[");
		     	var e=d.indexOf("]");
		     	var hao=d.substring(s,e+1); 
		     	var objects=eval(hao);
		                      //1,获取上面id为cloneTr的tr元素  
		                          var tr = $("#cloneTr");  
		                       $.each(objects, function(index,item){                              
		                             //克隆tr，每次遍历都可以产生新的tr                              
		                               var clonedTr = tr.clone();  
		                               var _index = index;  
		                              
		                               //循环遍历cloneTr的每一个td元素，并赋值  
		                               clonedTr.children("td").each(function(inner_index){  
		                                  
		                                      //根据索引为每一个td赋值  
		                                            switch(inner_index){  
		                                                  case(0):   
		                                                     $(this).html(item.name);  
		                                                     break;  
		                                                  case(1):  
		                                                     $(this).html(item.id);  
		                                                     break;  
		                                                 case(2):  
		                                                     $(this).html(item.type);  
		                                                     break;  
		                                                
		                                                 
		                                
		                                           }//end switch                          
		                            });//end children.each  
		                          
		                           //把克隆好的tr追加原来的tr后面  
		                           clonedTr.insertAfter(tr);  
		                        });//end $each  
		                        $("#cloneTr").hide();//隐藏id=clone的tr，因为该tr中的td没有数据，不隐藏起来会在生成的table第一行显示一个空行  
		                        $("#generatedTable").show();  
		         }//end success  
		    });   
///	};*/
    </script>

</head>
<body>

<center>
	<div id="container" align="center">
		<br>
		<br>
		<h3>Attendance Check</h3>
	</div>
	<br>
	<div align="center">
		<table id="generatedTable" class="imagetable" width="100%"
			height="100%" style="" bordercolor="red" align="center" display:none;">
			<thead>
				<tr align="center">
					<th><b>NAME</b></th>
					<th><b>ID</b></th>
					<!--<th><b>Mac</b></th>  -->
					<th><b>TYPE</b></th>

				</tr>
			</thead>
			<tbody>
				<tr id="cloneTr" align="center">
					<td></td>
					<td></td>
					<td></td>


				</tr>
			</tbody>

		</table>
	</div>

	<br>
	</center>
<script>

 $.ajax({  
     type: "GET",  
     url: "http://localhost:8080/SAMS/entry/attencecheck/all",  
     cache: true, 
     success: function(d){                    
    	var s=d.indexOf("[");
     	var e=d.indexOf("]");
     	var hao=d.substring(s,e+1); 
     	var objects=eval(hao);

                      //1,获取上面id为cloneTr的tr元素  
                          var tr = $("#cloneTr");  
  
                       $.each(objects, function(index,item){                              
                             //克隆tr，每次遍历都可以产生新的tr                              
                               var clonedTr = tr.clone();  
                               var _index = index;  
                              
                               //循环遍历cloneTr的每一个td元素，并赋值  
                               clonedTr.children("td").each(function(inner_index){  
                                  
                                      //根据索引为每一个td赋值  
                                            switch(inner_index){  
                                                  case(0):   
                                                     $(this).html(item.name);  
                                                     break;  
                                                  case(1):  
                                                     $(this).html(item.id);  
                                                     break;  
                                                 case(2):  
                                                     $(this).html(item.type);  
                                                     break;  
                                                
                                                 
                                
                                           }//end switch                          
                            });//end children.each  
                          
                           //把克隆好的tr追加原来的tr后面  
                           clonedTr.insertAfter(tr);  
                        });//end $each  
                        $("#cloneTr").hide();//隐藏id=clone的tr，因为该tr中的td没有数据，不隐藏起来会在生成的table第一行显示一个空行  
                        $("#generatedTable").show();  
         }//end success  
    });   
    </script>
  	  
</body>
</html>