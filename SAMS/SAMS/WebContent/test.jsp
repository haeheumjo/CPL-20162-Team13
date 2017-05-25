<!DOCTYPE html>  
<html lang="en">  
<head>  

    <meta charset="UTF-8">  
    <title>Kyungpook National University</title>  
    <link rel="stylesheet" type="text/css" href="css/login.css"/>  
</head>  
<body>  
    <div id="login">  
      <h1>Hello Management</h1>  
      			<br>
      						<br>
      									<br>
        <form >  
			<button type="button" id='add' class="but"  onclick="toA()">Student Management</button> 
			<br>
				<br>
					<br>
						<br>
            <button type="button" id='add' class="but" onclick="toB()">Professor Management</button>  
            	<br>
            		<br>
            			<br>
            				<br>
              <button type="button" id='add' class="but" onclick="toC()">Class Management</button>  
        </form>  
    </div>  
</body>  

</html>
<script type="text/javascript">
function toA()
{
	
	window.location.href="StudentManagement.jsp";
	
	}

function toB()
{
	
	window.location.href="ProfessorManagement.jsp";
	
	}
	
function toC()
{
	
	window.location.href="ClassMangement.jsp";
	
	}
</script>