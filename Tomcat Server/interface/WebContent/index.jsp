<!DOCTYPE html>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page import="JavaBeans.*"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Testing</title>

<%
	ClassInformations allClassInf = (ClassInformations)session.getAttribute("allClassInf");  
	Response rsp = (Response) request.getAttribute("rsp");

%>
<script type="text/javascript"> 
	function getSelectedClass(){
		var arr=[];
		<% 
			if(allClassInf!=null)
			for(int i = 0 ; i < allClassInf.getAllClassInf().size() ; ++i)
			{ %>
			
				var tempArr = [];
				tempArr.push("<option value='0'> </option>");
			<% for(int j = 0; j < allClassInf.getAllClassInf().get(i).getNameOfMethods().size();++j)
				{ %>
					tempArr.push("<option value='" + <%=j%> + "'>" + "<%= allClassInf.getAllClassInf().get(i).getNameOfMethods().get(j)%>" + "</option>");
					
			<%  } %>
			arr.push(tempArr);
			
			<%}
		
			%>
		
	
		var selectedValue = document.getElementById("class").value;


		document.getElementById('func').innerHTML = arr[selectedValue]
		return selectedValue;
	}
	function getSelectedFunc(){
	}
function getSelectedClass2(){
		
		var arr=[];
		<% 
			if(allClassInf!=null)
			for(int i = 0 ; i < allClassInf.getAllClassInf().size() ; ++i)
			{ %>
			
				var tempArr = [];
				tempArr.push("<option value='0'> </option>");
			<% for(int j = 0; j < allClassInf.getAllClassInf().get(i).getNameOfMethods().size();++j)
				{ %>
					tempArr.push("<option value='" + <%=j%> + "'>" + "<%= allClassInf.getAllClassInf().get(i).getNameOfMethods().get(j)%>" + "</option>");
					
			<%  } %>
			arr.push(tempArr);
			
			<%}
		
			%>
		
	
		var selectedValue = document.getElementById("class2").value;

		document.getElementById('func2').innerHTML = arr[selectedValue]
		return selectedValue;
	}

</script>
</head>
<body style="background: LightSkyBlue">
	<form action="" method="post">
		<fieldset>
			<h2>Testing Group</h2>
			<hr />
			<label >Class Name : </label>
			<c:if test="${  empty rsp}">
				<select	id="class" name="className" onClick="getSelectedClass()" style="width: 125px;" required>
					<option value="0"></option>
					<%
						if(allClassInf!=null)
	        			for(int i=0;i<allClassInf.getAllClassInf().size();++i){
	         				 %>
					<option value="<%=i%>"><%=allClassInf.getAllClassInf().get(i).getClassPath() %></option>
					<% 
	        			}
	      			%>
				</select>
			</c:if>
			
			<c:if test="${ not empty rsp}">
			<select id="class2" name="className" onchange="getSelectedClass2()">
			    <c:forEach var="item" items="${allClassInf.allClassInf}" varStatus="loop">
			        <option value="${loop.index}" ${item.classPath == rsp.selectedClassName ? 'selected="selected"' : ''}>${item.classPath}</option>
			        
			    </c:forEach>
 
			</select>
			</c:if>
	

			<hr />

			<c:if test="${ empty rsp}">
				<label style="width: 250px;"label2">Function Name:</label> <select
					id="func" name="funcName" onchange="getSelectedFunc();" required>
	
				</select>
			</c:if>
			<c:if test="${not empty rsp}">
				<label style="width: 250px;"label2">Function Name:</label> <select
					id="func2" name="funcName"  onchange="getSelectedFunc2()" required>
				<c:forEach var="item" items="${selectedFuncNames}" varStatus="loop">
			        <option value="${loop.index}" ${item == rsp.selectedFuncName ? 'selected="selected"' : ''}>${item}</option>
			    </c:forEach>
				</select>
			</c:if>
			
			<hr />
			<c:if test="${ empty rsp}">
				<table id="table" align="center">
					<tbody>
						<tr>
							<td>Parameters:</td>
							<td><input type="text" name="parameters" value="" size=50 required/></td>
							<td>Delimiter:</td>
							<td><input type="text" name="separator" value="" size=10 required/></td>
						</tr>
						<tr>
							<td>Expected Value:</td>
							<td><input type="text" name="expectedValue" value="" size=50 required/></td>
						</tr>
						<tr>
							<td><input style="margin-right: 135px; display: block;"
								type="submit" name="action" value="Test!" id="TestButton" /></td>
							<td><input type="button" value="Clear Page" onClick="window.location.href=window.location.href"></td>
							<td><input type="submit" name="action" value="Send Results"><td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<c:if test="${not empty rsp}">
				<table id="table" align="center">
					<tbody>
						<tr>
							<td>Parameters:</td>
							<td><input type="text" name="parameters" value="${rsp.parameters}" size=50 required/></td>							
							<td>Delimiter:</td>
							<td><input type="text" name="separator" value="${rsp.deliemeter}" size=10 required/></td>
						</tr>
						<tr>
							<td>Expected Value:</td>
							<td><input type="text" name="expectedValue" value="${rsp.expectedValue}" size=50 required/></td>
						</tr>
						<tr>
							<td><input style="margin-right: 135px; display: block;"
								type="submit" name="action" value="Test!" id="TestButton" /></td>
							<td><input type="button" value="Clear Page" onClick="window.location.href=window.location.href"></td>
							<td><input type="submit" name="action" value="Send Results"><td>
						</tr>
					</tbody>
				</table>
				<c:if test="${not empty status}">
				<font color="green" > 
			
				<h1 >${message}</h1>
				</font>
				</c:if>
				<c:if test="${empty status}">
				<font color="red" > 
			
				<h1 >${message}</h1>
				</font>
				</c:if>
			</c:if>



		</fieldset>
	</form>
</body>
</html>
