<%@page import="java.sql.ResultSet"%>
<%@page import="com.service.dao.DAOOperationsImpl"%>
<%@page import="com.service.model.Dealer"%>
<%@page import="com.service.model.Item"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false" errorPage="ErrorPage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
html,body
{
height: 100%;
}
#txtArea
{
color: white;
background-color:transparent;
font-size:15px;
width: 80%;
margin-left: 2%;
text-align: left;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		HttpSession sessionSupply = request.getSession(false);
		String dealerId = "";
		Dealer dealer=new Dealer();
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
	<div style="overflow: scroll;width: 90%;margin-left: 4%">
		<form method="post" action="ReplyToUser">
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
					<td>Item Quantity</td>
				</tr>
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
		<textarea rows="4"  name="msg" id="txtArea" cols="50" readonly>
Reply from  : <%=dealer.getName()%>
Dear <%=customerId %>, your order with order ID : <%=orderId%> is in processing you will get update on
Date  :</textarea>
			<input type="hidden" name="orderId" value=<%=orderId%>> 
			<input type="hidden" name="userId" value=<%=customerId%>>
			<br>
			<div style="font-size: 20px;margin-left: 4%;">Enter Date of Update  : 
			<input style="background-color: transparent;margin-left: 20%;font-size: 15px;width:20%;color:white"  type="date"  name="date1" required>
			</div> <br>
			<input style="margin-left: 40%;height: 30px;font-size: 16px;" type="submit" value="Reply">
		</form>
		-------------------------------------------------------------------------
	</div>
	<%}} %>
</body>
</html>