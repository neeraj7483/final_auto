<%@page import="com.service.model.Manager"%>
<%@page import="com.service.model.Dealer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="false" errorPage="ErrorPage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dealer</title>
<style type="text/css">
	html,body
	{
		height: 100%;
		width: 100%;
		background-color: #37474f;
		color: white;
		font-size: 30px;
	}
	
	.menu
	{
		height: 10%;
		width: 100%;
		background-color: #bf360c;
		margin-bottom: 20px;
		text-align: center;
		box-shadow: 0px 0px 4px 4px black;
		transition: all 1s;
		filter: opacity(70%);
	}
	
	.menu:hover
	{
		filter: opacity(100%);
	}
	
	#left
	{
		margin-top: 10%;
		float: left;
		width: 30%;
		height: 100%;
		//box-shadow: 0px 0px 4px 4px black;
	}
	
	#right
	{
		margin-top: 10%;
		float: right;
		width: 65%;
		height: 100%;
		//background-color: #00796b;
		box-shadow: 0px 0px 4px 4px black;
	}
	
	#top_bar
	{
		width: 100%;
		height: 12%;
		background-color: #bf360c;
		position: fixed;
		top: 0px;
		left: 0px;
		text-align: center;
		font-size: 40px;
		box-shadow: 0px 0px 4px 4px black;
		z-index:1000;
	}
	
	a
	{
		text-decoration: none;
	}
	
	a:visited
	{
		text-decoration: none;
		color: white;
	}
	
	#photo
	{
		margin-bottom: 10%;
		width: 100%;
		height: 30%;
		transition: all 2s;
		filter: opacity(0.8);
		box-shadow: 0px 0px 4px 4px black;
	}
	
	#main_container
	{
		height:100%;
		width:100%;
	}
	
</style>
</head>
<body>

<%
HttpSession session= request.getSession(false);
String managerName="";
if(session!=null)
{
	Manager manager=(Manager)session.getAttribute("manager");
}
else
{
	response.sendRedirect("Login.jsp");
}
%>
<div id="main_container">
	<div id="top_bar">Welcome <%=managerName%>
	<%@include file="Logout.html" %>
	</div>
	<div id="left">
		<a href="?var=InfoManager"><div id="photo"></div></a>
		<a href="?var=billDealer"><div class="menu">View Bills</div></a>
		<a href="?var=messageManager"><div class="menu">View Messages</div></a>
		<a href="?var=stockManager"><div class="menu">View Own Stock</div></a>
		<a href="?var=replyManager"><div class="menu">Reply</div></a>
		<a href="?var=supplyManager"><div class="menu">Supply</div></a>
		<a href="?var=requestToWareHouse"><div class="menu">Request To Warehouse Manager</div></a>
	</div>
	<div id="right">
		<%
			String a=request.getParameter("var");
		if(a!=null)
		{
			if(a.equals("InfoManager")){
				%>
				<%@include file="InfoManager.jsp" %>
				<%
			 }
			else if(a.equals("billDealerq")){
				%>
				<%@include file="billDealer.jsp" %>
				<%
			}
			else if(a.equals("messageManager")){
					%>
					<%@include file="messageManager.jsp" %>
					<%
				}
			else if(a.equals("stockManager")){
				%>
				<%@include file="stockManager.jsp" %>
				<%
			}
			else if(a.equals("replyManager")){
				%>
				<%@include file="replyManager.jsp" %>
				<%
			}
			else if(a.equals("supplyManager")){
				%>
				<%@include file="supplyManager.jsp" %>
				<%
			}
			else if(a.equals("requestToWareHouse")){
				%>
				 <%@include file="requestToWareHouse.jsp" %>
				<%
			}
		}
		%>
	</div>
</div>
</body>
</html>