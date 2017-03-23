<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
	<%@ include file="header.jsp" %>
  <body>
	 <div id="service">
	 	<div class="container">
	 		<c:if test="${empty files}"><h5>You don't have any files that has been stored here. Upload below!</h5></c:if>
	 		<c:if test="${!empty files}"><h3>My Files</h3>
		 		<div class="hline"></div>
		 		<div class="spacing"></div>
	
			  <table class="table table-hover">
			    <thead>
			      <tr>
			        <th>Filename</th>
			        <th>Timestamp</th>
			        <th></th>
			      </tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${files}" var="f">
				      <tr>
				        <td>${f.filename}</td>
				        <td>${f.timestamp}</td>
				        <td><button type="submit" class="btn btn-theme" id="${f.fileid}" class="delete">Delete</button></td>
				      </tr>
				    </c:forEach>
			    </tbody>
			  </table>	 		
		 	</div><! --/container -->
	 	</c:if>
	 	<div class="container">
	 		<h3>Upload my File</h3>
	 		<div class="hline"></div>
	 		<div class="spacing"></div>

	 		<form action="" method="post">
				<input class="btn btn-theme" type="file" name="upload" style="width: 100%">
				<div class="wrapper">
					<button type="submit" class="btn btn-theme" id="upload" class="upload" style="margin-left: 47%">Upload File</button>
				</div>
			</form>
			<div class="spacing"></div>
	 	</div>
	 </div><! --/service -->
 
<%@ include file="footer.jsp" %>
  </body>
</html>
