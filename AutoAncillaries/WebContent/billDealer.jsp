<%@page import="com.service.model.Item"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.service.dao.DAOOperationsImpl"%>
<%@page import="com.service.model.Dealer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  session="false" errorPage="ErrorPage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Bill</title>
</head>
<body>
<%
		HttpSession sessionSupply = request.getSession(false);
		String dealerId = "";
		if (sessionSupply != null) {
			Dealer dealer = (Dealer) sessionSupply.getAttribute("dealer");
			dealerId = dealer.getDealerId();
		}
		String customerId = "";
		String orderId = "";
		DAOOperationsImpl obj = new DAOOperationsImpl();
		ResultSet rs = obj.getCompleteOrder(dealerId);
		while (rs.next()) {
			orderId = rs.getString(1);
			customerId = rs.getString(2);
	%>
	<div>
		<div style="text-align: center;font-weight: bolder;">Order By : <%=customerId %></div>
		<div style="text-align: center;font-weight: bolder;">Order ID : <%=orderId %></div>
			<table style="width: 100%;">
				<tr>
					<td>Item ID</td>
					<td>Item Name</td>
					<td>Item Cost</td>
					<td>Item Quantity</td>
				</tr>
				<tr>Total Cost : <%=rs.getInt(3) %></tr>
				<%
					List<Item> itemList = obj.getOrderDetail(orderId);
						for (Item item : itemList) {
				%>
				<tr>
					<td><%=item.getItemId()%></td>
					<td><%=item.getItemName()%></td>
					<td><%=item.getCost()%></td>
					<td><%=item.getQuantity()%></td>
				</tr>
				<%
					}
				%>
			</table>
	</div>
	<%} %>
</body>
</html>