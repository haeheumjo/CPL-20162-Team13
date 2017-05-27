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
        <input id="textMessage" type="text">
        <!-- 송신 버튼 -->
        <input onclick="sendMessage()" value="Send" type="button">
        <!-- 종료 버튼 -->
        <input onclick="disconnect()" value="Disconnect" type="button">
    </form>
    <br />
    <!-- 결과 메시지 보여주는 창 -->
    <textarea id="messageTextArea" rows="10" cols="50"></textarea>
     
    <script type="text/javascript">
        //WebSocketEx는 프로젝트 이름
        //websocket 클래스 이름
        var webSocket = new WebSocket("ws://localhost:8080/SAMS/websocket");
        var messageTextArea = document.getElementById("messageTextArea");
        //웹 소켓이 연결되었을 때 호출되는 이벤트
        webSocket.onopen = function(message){
            messageTextArea.value += "Server connect...\n";
        };
        //웹 소켓이 닫혔을 때 호출되는 이벤트
        webSocket.onclose = function(message){
            messageTextArea.value += "Server Disconnect...\n";
        };
        //웹 소켓이 에러가 났을 때 호출되는 이벤트
        webSocket.onerror = function(message){
            messageTextArea.value += "error...\n";
        };
        //웹 소켓에서 메시지가 날라왔을 때 호출되는 이벤트
        webSocket.onmessage = function(message){
            messageTextArea.value += "Recieve From Server => "+message.data+"\n";
        };
        //Send 버튼을 누르면 실행되는 함수
        function sendMessage(){
            var message = document.getElementById("textMessage");
            messageTextArea.value += "Send to Server => "+message.value+"\n";
            //웹소켓으로 textMessage객체의 값을 보낸다.
            webSocket.send(message.value);
            //textMessage객체의 값 초기화
            message.value = "";
        }
        //웹소켓 종료
        function disconnect(){
            webSocket.close();
        }
    </script>
</body>
</html>


출처: http://nowonbun.tistory.com/285 [명월 일지]