package com.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.service.model.Item;
import com.service.model.Customer;
import com.service.model.Dealer;
import com.service.model.Manager;

public interface DAOOperations {

	public Customer checkCustomer(String id,String password) throws SQLException;
	public Dealer checkDealer(String id,String password) throws SQLException;
	public Manager checkManager(String id,String password) throws SQLException;
	public boolean registerDealer(Dealer dealer) throws SQLException;
	public boolean registerCustomer(Customer customer) throws SQLException;
	public boolean checkUserId(String id) throws SQLException;
	public ResultSet getMessage(String id) throws SQLException;
	public List<Item> getUnsoldItems(String orderId) throws SQLException;
	public List<Item> getUserStock(String dealerId) throws SQLException;
	public ResultSet getIncompleteOrder(String orderId) throws SQLException;
	public List<Item> getOrderDetail(String orderId) throws SQLException;
	public void CompleteOrder(String orderId, String dealerId,String customerId) throws SQLException; 
	public ResultSet getCompleteOrder(String dealerId) throws SQLException;
	public boolean checkDealerStock(String dealerId,String orderId) throws SQLException;
	public int getQuantityDifference(String userId,String itemId,int quantity) throws SQLException;
	public void makeRequest(String to,String from,String orderId,String itemId[],String quantity[]) throws SQLException;
	public String getManager(String location,String type) throws SQLException;
	public ResultSet getRequests(String managerId) throws SQLException;
	public boolean checkManagerStock()throws SQLException;
	public List<Item> getRequestDetails(String requestId)throws SQLException;
}
