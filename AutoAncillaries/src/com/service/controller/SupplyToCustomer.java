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

public class SupplyToCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SupplyToCustomer() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Login.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerId=request.getParameter("customerId");
		String orderId=request.getParameter("orderId");
		HttpSession session= request.getSession(false);
		if(session!=null)
		{
			Dealer dealer=(Dealer)session.getAttribute("dealer");
			String dealerId=dealer.getDealerId();
			DAOOperationsImpl obj=new DAOOperationsImpl();
			try {
				obj.CompleteOrder(orderId, dealerId, customerId);
				response.sendRedirect("DealerHomePage.jsp");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			response.sendRedirect("Login.jsp");
		}
	}

}
