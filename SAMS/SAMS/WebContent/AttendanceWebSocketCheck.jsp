<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

    <form>
        <!-- 송신 메시지 작성하는 창 -->
        <!input id="textMessage" type="text">
        <!-- 송신 버튼 -->
        <!input onclick="sendMessage()" value="Send" type="button">
        <!-- 종료 버튼 -->
        <!input onclick="disconnect()" value="Disconnect" type="button">
    </form>
    <br />
    <!-- 결과 메시지 보여주는 창 -->
    <textarea id="messageTextArea" rows="10" cols="50"></textarea>
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
    <script type="text/javascript">
        //WebSocketEx는 프로젝트 이름
        //websocket 클래스 이름
    $(function () {  
        setInterval("refreshMsg()", 2000); //每隔5秒刷新点击量  
    });
    function refreshMsg(){
        var rid = "";
        var webSocket = new WebSocket("ws://localhost:8080/SAMS/AttendanceWebSocketCheck");
        var messageTextArea = document.getElementById("messageTextArea");
        $.ajax({  
            type: "GET",  
            url: "http://localhost:8080/SAMS/entry/attencecheck/getrecordno",  
            cache: true, 
            success: function(d){
            	rid = d;
            }
        });
        
        //웹 소켓이 연결되었을 때 호출되는 이벤트
        webSocket.onopen = function(message){
            messageTextArea.value = "Student Attendance System is Working...\n";
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
        };
        //웹 소켓이 닫혔을 때 호출되는 이벤트
        webSocket.onclose = function(message){
            messageTextArea.value += "Done...\n";
        };
        //웹 소켓이 에러가 났을 때 호출되는 이벤트
        webSocket.onerror = function(message){
            messageTextArea.value += "error...\n";
        };
        webSocket.onmessage = function(message){
        	out.println(message);
            
        };
        function sendMessage(){
        	webSocket.send(rid);
        }
        setInterval("sendMessage()", 2000);
        //Send 버튼을 누르면 실행되는 함수
        //웹소켓 종료
        function disconnect(){
            webSocket.close();
        }
        
    }
    </script>
</body>
</html>