package com.nt.sbeans;

public class OrderService {

	private double bill;
	private DiscountCalculator discount;
	
	public void setDiscount(DiscountCalculator discount) {
		this.discount=discount;
	}
	
	public void setBill(double bill) {
		this.bill=bill;
	}
	
	
	public double totalBill() {
		return bill-discount.getDiscountRate();
	}
}
