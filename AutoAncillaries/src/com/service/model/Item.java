package com.service.model;

/**
 * @author Neeraj1.Jain
 *
 */
public class Item {
	private String itemId;
	private String itemName;
	private float cost;
	private int quantity;

	public Item() {
	}

	public Item(String itemId, String itemName, float cost, int quantity) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.cost = cost;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + ", cost=" + cost + "]";
	}

}
