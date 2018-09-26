<%@page import="com.service.model.Manager"%>
<%@page import="com.service.model.Item"%>
<%@page import="java.util.List"%>
<%@page import="com.service.model.Dealer"%>
<%@page import="com.service.dao.DAOOperationsImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false" errorPage="ErrorPage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
HttpSession sessionStock=request.getSession(false);
String managerId="";
if(sessionStock!=null)
{
	Manager manager=(Manager)sessionStock.getAttribute("manager");
	managerId=manager.getManagerId();
}
DAOOperationsImpl obj=new DAOOperationsImpl();
List<Item> itemList=obj.getUserStock(managerId);
%>
<table style="width: 100%;">
<tr>
<td>Item ID</td>
<td>Item Name</td>
<td>Item Cost</td>
<td>Item Quantity</td>
</tr>

<%
for(Item item:itemList)
{
%>
<tr>
<td><%=item.getItemId() %></td>
<td><%=item.getItemName() %></td>
<td><%=item.getCost() %></td>
<td><%=item.getQuantity() %></td>
</tr>
<%} %>
</table>
</body>
</html>