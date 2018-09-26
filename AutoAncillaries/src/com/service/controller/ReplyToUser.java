package com.service.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.service.dao.DAOOperationsImpl;
import com.service.model.Dealer;
import com.service.model.Manager;

public class ReplyToUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReplyToUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("Login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String from="";
		String message = request.getParameter("msg");
		String date = request.getParameter("date1");
		String userId = request.getParameter("userId");
		HttpSession session = request.getSession(false);
		Dealer dealer = (Dealer) session.getAttribute("dealer");
		if(dealer!=null)
		{
		from = dealer.getDealerId();
		}
		else
		{
			Manager manager=(Manager)session.getAttribute("manager");
			from=manager.getName();
		}
		message = message + date;
		System.out.println(message);
//		DAOOperationsImpl obj = new DAOOperationsImpl();
//		System.out.println(message);
//		System.out.println(date);
//		System.out.println(userId);
//		try {
//			obj.sendMessage(message, userId, from);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
