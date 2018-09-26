package com.service.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.dao.DAOOperationsImpl;
import com.service.model.Dealer;

public class RequestToWarehouse extends HttpServlet {

    public RequestToWarehouse() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String quantity[]=request.getParameterValues("quantity");
		String itemId[]=request.getParameterValues("itemId");
		String orderId=request.getParameter("orderId");
		HttpSession session=request.getSession(false);
		Dealer dealer=(Dealer)session.getAttribute("dealer");
		String dealerId=dealer.getDealerId();
		DAOOperationsImpl obj=new DAOOperationsImpl();
		String managerId;
		try {
			managerId = obj.getManager(dealer.getLocation(),"dealer");
			obj.makeRequest(managerId, dealerId, orderId, itemId, quantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		

}
