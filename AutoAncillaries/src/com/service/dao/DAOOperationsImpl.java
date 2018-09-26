package com.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.service.model.Item;
import com.service.model.Address;
import com.service.model.Customer;
import com.service.model.Dealer;
import com.service.model.Manager;
import com.service.utility.ConnectionProvider;

import sun.management.snmp.util.SnmpNamedListTableCache;

public class DAOOperationsImpl implements DAOOperations {

	public Customer checkCustomer(String id, String password) throws SQLException {
		Customer customer = null;
		Address address = new Address();
		String query = "select * from customers c,customeraddress ca "
				+ "where c.customerid=ca.userid and c.customerid=? and c.password=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			customer = new Customer();
			customer.setCustomerId(rs.getString(1));
			customer.setPassword(rs.getString(2));
			customer.setName(rs.getString(3));
			customer.setEmail(rs.getString(4));
			customer.setLocation(rs.getString(5));
			customer.setContactNo(rs.getString(6));
			address.setStreetAddress(rs.getString(8));
			address.setState(rs.getString(9));
			address.setPincode(rs.getString(10));
			customer.setAddress(address);
		}
		return customer;
	}

	public Dealer checkDealer(String id, String password) throws SQLException {
		Dealer dealer = null;
		Address address = new Address();
		String query = "select * from dealers d,dealeraddress da "
				+ "where d.dealerid=da.userid and d.dealerid=? and d.password=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			dealer = new Dealer();
			dealer.setDealerId(rs.getString(1));
			dealer.setPassword(rs.getString(2));
			dealer.setName(rs.getString(3));
			dealer.setEmail(rs.getString(4));
			dealer.setLocation(rs.getString(5));
			dealer.setContactNo(rs.getString(6));
			dealer.setRating(rs.getInt(7));
			address.setStreetAddress(rs.getString(9));
			address.setState(rs.getString(10));
			address.setPincode(rs.getString(11));
			dealer.setAddress(address);

		}
		return dealer;
	}

	public Manager checkManager(String id, String password) throws SQLException {
		Manager manager = null;
		String query = "select * from managers where managerId=? and password=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			manager = new Manager(rs.getString(1), rs.getString(3), rs.getString(2), rs.getString(4));
		}
		return manager;
	}

	public boolean registerDealer(Dealer dealer) throws SQLException {
		String query1 = "insert into dealers values(?,?,?,?,?,?,?)";
		String query2 = "insert into dealeraddress values(?,?,?,?)";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps1 = con.prepareStatement(query1);
		ps1.setString(1, dealer.getDealerId().toLowerCase());
		ps1.setString(2, dealer.getPassword());
		ps1.setString(3, dealer.getName());
		ps1.setString(4, dealer.getEmail());
		ps1.setString(5, dealer.getLocation());
		ps1.setString(6, dealer.getContactNo());
		ps1.setInt(7, 3);
		PreparedStatement ps2 = con.prepareStatement(query2);
		ps2.setString(1, dealer.getDealerId().toLowerCase());
		ps2.setString(2, dealer.getAddress().getStreetAddress());
		ps2.setString(3, dealer.getAddress().getState());
		ps2.setString(4, dealer.getAddress().getPincode());
		int i = ps1.executeUpdate();
		i = ps2.executeUpdate();
		if (i > 0)
			return true;
		else
			return false;
	}

	public boolean registerCustomer(Customer customer) throws SQLException {
		String query1 = "insert into customers values(?,?,?,?,?,?)";
		String query2 = "insert into customeraddress values(?,?,?,?)";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps1 = con.prepareStatement(query1);
		ps1.setString(1, customer.getCustomerId().toLowerCase());
		ps1.setString(2, customer.getPassword());
		ps1.setString(3, customer.getName());
		ps1.setString(4, customer.getEmail());
		ps1.setString(5, customer.getLocation());
		ps1.setString(6, customer.getContactNo());
		PreparedStatement ps2 = con.prepareStatement(query2);
		ps2.setString(1, customer.getCustomerId().toLowerCase());
		ps2.setString(2, customer.getAddress().getStreetAddress());
		ps2.setString(3, customer.getAddress().getState());
		ps2.setString(4, customer.getAddress().getPincode());
		int i = ps1.executeUpdate();
		i = ps2.executeUpdate();
		if (i > 0)
			return true;
		else
			return false;

	}

	public boolean checkUserId(String id) throws SQLException {
		String query1 = "select dealerId from dealers where dealerId=?";
		String query2 = "select customerId from customers where customerId=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps1 = con.prepareStatement(query1);
		ps1.setString(1, id);
		PreparedStatement ps2 = con.prepareStatement(query2);
		ps2.setString(1, id);
		ResultSet rs1 = ps1.executeQuery();
		ResultSet rs2 = ps2.executeQuery();
		if ((rs1.next() == true) || (rs2.next() == true)) {
			return false;
		} else
			return true;
	}

	public ResultSet getMessage(String id) throws SQLException {
		System.out.println(id);
		String query = "select * from inbox where inbox_user_id=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public List<Item> getUnsoldItems(String dealerId) throws SQLException {
		List<Item> itemList = new ArrayList<Item>();
		String query = "select i.itemid,i.name,i.price,oi.quantity from items i, "
				+ "order_item_quantity oi where i.itemId in(select itemId from order_item_quantity "
				+ "where orderId in(select orderId from orders where dealerid=?)) and i.itemid=oi.itemid";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, dealerId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Item item = new Item(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
			itemList.add(item);
		}
		return itemList;
	}

	public List<Item> getUserStock(String userId) throws SQLException {
		List<Item> itemList = new ArrayList<Item>();
		String query = "select  i.itemid,i.name,i.price,d.quantity from items i,inventorystock d "
				+ "where userid=? and i.itemid=d.itemid";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, userId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Item item = new Item(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
			itemList.add(item);
		}
		return itemList;
	}

	public List<Item> getOrderDetail(String orderId) throws SQLException {
		List<Item> itemList = new ArrayList<Item>();
		String query = "select oi.itemid,i.name,i.price,oi.quantity from order_item_quantity oi,items i "
				+ "where i.itemid=oi.itemid and oi.orderid=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, orderId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Item item = new Item(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
			itemList.add(item);
		}
		return itemList;
	}

	public ResultSet getIncompleteOrder(String dealerId) throws SQLException {
		String query = "select orderid,customerid,dealerId from orders where dealerid=? and status=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, dealerId);
		ps.setString(2, "0");
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet getCompleteOrder(String dealerId) throws SQLException {
		String query = "select orderid,customerid,cost from orders where dealerid=? and status=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, dealerId);
		ps.setString(2, "1");
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public void sendMessage(String message, String to, String from) throws SQLException {
		String query2 = "insert into inbox values(inbox_seq.nextval,?,?,?)";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps2 = con.prepareStatement(query2);
		ps2.setString(1, message);
		ps2.setString(2, to);
		ps2.setString(3, from);
		ps2.executeUpdate();
	}

	public void CompleteOrder(String orderId, String dealerId, String customerId) throws SQLException {
		String query3 = "select itemId,quantity from order_item_quantity where orderid=?";
		String query4 = "update inventorystock set quantity=quantity-? where UserId=? and itemId=?";
		String query5 = "update orders set status=? where orderId=?";
		String message = "Your order is completed it will be shipped in 4-5 working days";
		Connection con = ConnectionProvider.getConnection();

		sendMessage(message, customerId, dealerId);

		PreparedStatement ps3 = con.prepareStatement(query3);
		ps3.setString(1, orderId);
		ResultSet rs2 = ps3.executeQuery();

		PreparedStatement ps4 = con.prepareStatement(query4);
		while (rs2.next()) {
			ps4.setInt(1, rs2.getInt(2));
			ps4.setString(2, dealerId);
			ps4.setString(3, rs2.getString(1));
			ps4.executeUpdate();
		}

		PreparedStatement ps5 = con.prepareStatement(query5);
		ps5.setString(1, "1");
		ps5.setString(2, orderId);
		ps5.executeUpdate();

	}

	public boolean checkDealerStock(String UserId, String orderId) throws SQLException {
		int flag;
		List<Item> itemList1 = new ArrayList<Item>();
		List<Item> itemList2 = new ArrayList<Item>();
		String query1 = "select itemid,quantity from inventorystock where Userid=?";
		String query2 = "select itemid,quantity from order_item_quantity where orderid=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps1 = con.prepareStatement(query1);
		ps1.setString(1, UserId);
		PreparedStatement ps2 = con.prepareStatement(query2);
		ps2.setString(1, orderId);
		ResultSet rs1 = ps1.executeQuery();
		ResultSet rs2 = ps2.executeQuery();
		while (rs1.next()) {
			Item item = new Item();
			item.setItemId(rs1.getString(1));
			item.setQuantity(rs1.getInt(2));
			itemList1.add(item);
		}
		while (rs2.next()) {
			Item item = new Item();
			item.setItemId(rs2.getString(1));
			item.setQuantity(rs2.getInt(2));
			itemList2.add(item);
		}

		for (Item item1 : itemList1) {
			flag = 0;
			for (Item item2 : itemList2) {
				if (item1.getItemId().equals(item2.getItemId())) {
					flag = 1;
					if (item1.getQuantity() < item1.getQuantity()) {
						flag = 0;
					}
				}
			}
			if (flag == 0) {
				return false;
			}
		}
		return true;
	}

	public int getQuantityDifference(String userId, String itemId, int quantity) throws SQLException {
		int stockQuantity, difference = -1;
		String query = "select itemid,quantity from inventoryStock where userId=? and itemid=? ";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, userId);
		ps.setString(2, itemId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			stockQuantity = rs.getInt(2);
			if (quantity > stockQuantity) {
				difference = quantity - stockQuantity;
			}
		}
		return difference;
	}

	public void makeRequest(String to, String from, String orderId, String itemId[], String quantity[])
			throws SQLException {
		String query = "insert into requests values(requestid_seq.nextval,?,?,?,?)";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, orderId);
		ps.setString(2, from);
		ps.setString(3, to);
		ps.setString(4, "0");
		ps.executeUpdate();
		for (int i = 0; i < itemId.length; i++) {
			String query2 = "insert into request_info values(requestid_seq.currval,?,?,?)";
			PreparedStatement ps2 = con.prepareStatement(query2);
			ps2.setString(1, orderId);
			ps2.setString(2, itemId[i]);
			ps2.setString(3, quantity[i]);
			ps2.executeUpdate();
		}
		String query1 = "update orders set status=? where orderid=?";
		PreparedStatement ps1 = con.prepareStatement(query1);
		ps1.setString(1, "2");
		ps1.setString(2, orderId);
		ps1.executeUpdate();
		String message = "You have a request from " + from + " of orderID : " + orderId;
		sendMessage(message, to, from);
	}

	public String getManager(String location, String type) throws SQLException {
		String managerId = null;
		String query = "select managerId from warehouse where warehouseId in"
				+ "(select warehouseId from warehouse_location_coverage where coveredarea=?)";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, location);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			managerId = rs.getString(1);
		}
		return managerId;
	}

	public ResultSet getRequests(String managerId) throws SQLException {
		String query = "select * from requests where requestto=? and status=? order by orderid";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, managerId);
		ps.setString(2, "0");
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public boolean checkManagerStock() {
		return false;
	}

	public List<Item> getRequestDetails(String requestId) throws SQLException {
		List<Item> itemList=new ArrayList<Item>();
		String query="select i.itemid,i.name,i.price,ri.quantity from request_info ri,items i "
				+ "where i.itemid=ri.itemid and ri.orderid=?";
		Connection con = ConnectionProvider.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, requestId);
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			Item item=new Item(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
			itemList.add(item);
		}
		return itemList;
	}

}
