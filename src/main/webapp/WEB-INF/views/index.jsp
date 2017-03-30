<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="header.jsp" %>
	<!-- *****************************************************************************************************************
	 HEADERWRAP
	 ***************************************************************************************************************** -->
	<div id="headerwrap">
	    <div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2">
					<h3>The #1 Storage Platform in Web in 2017</h3>
					<h1>RAINFALL</h1>
					<h5>Login below to store your files now!</h5>
					<button type="button" class="btn btn-theme" onclick="window.location.href='#service'">LOG IN / REGISTER</button>
				</div>
				<div class="col-lg-8 col-lg-offset-2 himg">
					<img src="assets/img/browser.png" class="img-responsive">
				</div>
			</div><!-- /row -->
	    </div> <!-- /container -->
	</div><!-- /headerwrap -->
	
	
	 <div id="service">
	 	<div class="container">
	 		<div class="col-lg-6">
	 			<h4>LOGIN:</h4>
	 			<div class="hline"></div>
		 			<p>Want to back up your files, old user?</p>
		 			<p id="w1" style="display:none"><strong>Error:</strong> Incorrect username/password.</p>
		 			<p id="w3" style="display:none"><strong>Error:</strong> There's an error in the server. Please try again.</p>
		 			<p id="w5" style="display:none"><strong>Error:</strong> Please fill up both fields.</p>
		 			<form role="form" action="" method="POST">
					  <div class="form-group">
					    <label for="lusername">Username:</label>
					    <input type="text" class="form-control" name="lusername" id="lusername">
					  </div>
					  <div class="form-group">
					  	<label for="lpassword">Password:</label>
					  	<input type="password" class="form-control" name="lpassword" id="lpassword">
					  </div>
					  <button type="submit" class="btn btn-theme" id="sign-in">Log me in!</button>
					</form>
			</div><! --/col-lg-8 -->
	 		
	 		<div class="col-lg-6">
	 			<h4>REGISTER:</h4>
	 			<div class="hline"></div>
		 			<p>Don't think twice and sign up! We're 99.9% up all the time!</p>
		 			<p id="w2" style="display:none"><strong>Error:</strong> Passwords do not match.</p>
		 			<p id="w4" style="display:none"><strong>Error:</strong> There's an error in the server. Please try again.</p>
		 			<p id="w6" style="display:none"><strong>Error:</strong> Please fill up all fields.</p>
		 			<form role="form" action="" method="POST">
					  <div class="form-group">
					    <label for="username">Username:</label>
					    <input type="email" class="form-control" name="username" id="username" required>
					  </div>
					  <div class="form-group">
					    <label for="password">Password: </label>
					    <input type="email" class="form-control" name="password" id="password" required>
					  </div>
					  <div class="form-group">
					    <label for="password2">Re-enter Password:</label>
					    <input type="email" class="form-control" name="password2" id="password2" required>
					  </div>
					  <button type="submit" class="btn btn-theme" id="sign-up">Sign me up!</button>
					</form>
			</div><! --/col-lg-8 -->
	 	</div><! --/container -->
	 </div><! --/service -->
	 
<%@ include file="footer.jsp" %>
<!-- AJAX -->
<script>
	// login
	$("#sign-in").click(function(e){
		e.preventDefault();
		
		var username = $("#lusername").val();
		var password = $("#lpassword").val();
		
		if (username == "" || password == "" ){
			$("#w1").hide();
			$("#w3").hide();			
			$("#w5").show();
		} else {
			$.ajax({
				url : "login",
				method : "post",
				dataType: 'json',
				data : { /* data is anong data ipapasa mo sa servlet */
					username : username,
					password : password
				},
				success : function(message){
					//console.log(message);
					if (message == false){
						$("#w1").show();
						$("#w3").hide();
						$("#w5").hide();
					}  else
						window.location.href = "panel.jsp";

				},
				error: function(){
					$("#w3").show();
					$("#w5").hide();
				}
			});				
		}
	}); 

	// register
	$("#sign-up").click(function(e){
		e.preventDefault();
		
		var username = $("#username").val();
		var password = $("#password").val();

		if ($("#password").val() != $("#password2").val()){
			$("#w2").show();
			$("#w6").hide();
		} else if ($("#password").val() == "" || $("#password2").val() == "" || $("#username").val() == ""){
			$("#w2").hide();
			$("#w6").show();
		} else {
			$.ajax({
				url : "register",
				method : "post",
				dataType: 'json',
				data : { /* data is anong data ipapasa mo sa servlet */
					username : username,
					password : password
				},
				success : function(message){				
					$("#w2").hide();
					$("#w4").hide();
					
					

				},
				error: function(){ // bat nappunta sa error?!
					$("#w4").show();
					window.location.href = "files";
				}
			});				
		}

	}); 	
</script>
</body>
</html>

