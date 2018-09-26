<%@page import="java.sql.ResultSet"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.service.model.Item"%>
<%@page import="com.service.model.Dealer"%>
<%@page import="java.util.List"%>
<%@page import="com.service.dao.DAOOperationsImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false" errorPage="ErrorPage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
html, body {
	height: 100%;
}

form {
	margin-top: 2%;
	margin-left: 5%;
}

.form-container {
	margin-top: 10%;
	margin-left: 4%;
	height: 80%;
	width: 90%;
	border: solid 2px grey;
	overflow: scroll;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="form-container">
		<%
			HttpSession sessionSupply = request.getSession(false);
			String dealerId = "";
			Dealer dealer = new Dealer();
			if (sessionSupply != null) {
				dealer = (Dealer) sessionSupply.getAttribute("dealer");
				dealerId = dealer.getDealerId();
			}
			String customerId = "";
			String orderId = "";
			DAOOperationsImpl obj = new DAOOperationsImpl();
			ResultSet rs = obj.getIncompleteOrder(dealerId);
			while (rs.next()) {
				orderId = rs.getString(1);
				customerId = rs.getString(2);
				if (!obj.checkDealerStock(dealerId, orderId)) {
		%>
		<form method="post" action="RequestToWarehouse">
			<div style="text-align: center; font-weight: bolder;">
				Order By :
				<%=customerId%></div>
			<div style="text-align: center; font-weight: bolder;">
				Order ID :
				<%=orderId%></div>
			<table style="width: 100%;">
				<tr>
					<td>Item ID</td>
					<td>Item Name</td>
					<td>Item Cost</td>
					<td>Order Quantity</td>
					<td>Quantity to Request</td>
				</tr>
				<%
					List<Item> itemList = obj.getOrderDetail(orderId);
							for (Item item : itemList) {
								int diffrence = obj.getQuantityDifference(dealerId, item.getItemId(), item.getQuantity());
								if (diffrence != -1) {
				%>
				<tr>
					<td><%=item.getItemId()%></td>
					<td><%=item.getItemName()%></td>
					<td><%=item.getCost()%></td>
					<td><%=item.getQuantity()%></td>
					<td><%=diffrence%></td>
				</tr>
				<input type="hidden" name="itemId" value=<%=item.getItemId()%>> 
				<input type="hidden" name="quantity" value=<%=diffrence%>> 
				<%
					}
							}
				%>
			</table>
			<input type="hidden" name="orderId" value=<%=orderId%>> 
			<input type="hidden" name="" value=<%=orderId%>>
			
			]<input style="margin-left: 30%; height: 30px; font-size: 16px;"
				type="submit" value="Request To WareHouse">
		</form>
		-------------------------------------------------------------------------
		<%}} %>
	</div>
</body>
</html>