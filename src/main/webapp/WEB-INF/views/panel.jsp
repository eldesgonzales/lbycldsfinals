<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
	<%@ include file="header.jsp" %>
  <body>
	 <div id="service">
	 		<c:if test="${empty files}"><h5 class="translate">You don't have any files that has been stored here. Upload below!</h5></c:if>
	 	<div class="container">
	 		
	 		<c:if test="${!empty files}"><h3 class="translate">My Files</h3>
		 		<div class="hline"></div>
		 		<div class="spacing"></div>
	
			  <table class="table table-hover">
			    <thead>
			      <tr>
			        <th class="translate">Filename</th>
			        <th></th>
			        <th></th>
			      </tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${files}" var="f">
				      <tr>
				        <td width="80%">${f}</td>
				        <td><button type="submit" class="btn btn-theme translate" onclick="window.location.href = 'download?id=${f}'">Download</button></td>
				        <td><button type="submit" class="btn btn-theme translate" onclick="window.location.href = 'delete?id=${f}'">Delete</button></td>
				      </tr>
				    </c:forEach>
			    </tbody>
			  </table>	 		
		 	</div><! --/container -->
	 	</c:if>
	 	<div class="container">
	 		<h3 class="translate">Upload my File</h3>
	 		<div class="hline"></div>
	 		<div class="spacing"></div>

	 		<form action="upload" method="post" enctype="multipart/form-data">
	 			<input class="btn btn-theme" style="width: 100%" type="file" name="file">
				<div class="wrapper">
					<button type="submit" class="btn btn-theme" id="upload" class="upload translate" style="margin-left: 47%">Upload File</button>
				</div>
			</form>
			<div class="spacing"></div>
	 	</div>
	 </div><! --/service -->
 
<%@ include file="footer.jsp" %>
  </body>
</html>
