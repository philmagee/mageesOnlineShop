package com.shop.online.magees.receipt;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.shop.online.magees.item.Item;
import com.shop.online.magees.tax.Tax;

public class Receipt {

	private static Receipt receipt = null;
	private Map<Item, Integer> listOfItemsOnReceipt = new LinkedHashMap<>();
	private BigDecimal importedSalesTaxAmount = BigDecimal.valueOf(0.00);
	private BigDecimal basicSalesTaxAmount = BigDecimal.valueOf(0.00);
	private BigDecimal subTotalAmount = BigDecimal.valueOf(0.00);
	private BigDecimal totalAmount = BigDecimal.valueOf(0.00);
	
	private Receipt() {}
	
	public static synchronized Receipt getInstance() {
		if (receipt == null) {
			receipt = new Receipt();
		}
		return receipt;
	}

	public Map<Item, Integer> getListOfItemsOnReceipt() {
		return listOfItemsOnReceipt;
	}

	public void addListOfItemsOnReceipt(Item itemToAddToReceipt, int quantity) {
		this.listOfItemsOnReceipt.computeIfPresent(itemToAddToReceipt, (key, val) -> val + quantity);
		this.listOfItemsOnReceipt.putIfAbsent(itemToAddToReceipt, quantity); 
	}

	public BigDecimal getImportedSalesTaxAmount() {
		return importedSalesTaxAmount;
	}

	public void addImportedSalesTaxAmount(BigDecimal importedSalesTaxAmount) {
		this.importedSalesTaxAmount = this.importedSalesTaxAmount.add(importedSalesTaxAmount);
	}

	public BigDecimal getBasicSalesTaxAmount() {
		return basicSalesTaxAmount;
	}

	public void addBasicSalesTaxAmount(BigDecimal basicSalesTaxAmount) {
		this.basicSalesTaxAmount = this.basicSalesTaxAmount.add(basicSalesTaxAmount);
	}
	
	public BigDecimal getSubTotalAmount() {
		return subTotalAmount;
	}

	public void addSubTotalAmount(BigDecimal subTotalAmount) {
		this.subTotalAmount = this.subTotalAmount.add(subTotalAmount);
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	public static BigDecimal calculateItemTotalAmount(Item item, BigDecimal quantity) {
		BigDecimal subTotalPlusTaxes = item.getPrice().multiply(quantity);
		subTotalPlusTaxes = subTotalPlusTaxes.add(Tax.calculateBasicSalesTaxOnItem(item, quantity));
		subTotalPlusTaxes = subTotalPlusTaxes.add(Tax.calculateImportedSalesTaxOnItem(item, quantity));
		return subTotalPlusTaxes;
	}
	
	public void clear() {
		this.listOfItemsOnReceipt.clear();
		this.importedSalesTaxAmount = BigDecimal.valueOf(0.00);
		this.basicSalesTaxAmount = BigDecimal.valueOf(0.00);
		this.subTotalAmount = BigDecimal.valueOf(0.00);
		this.totalAmount = BigDecimal.valueOf(0.00);
	}
}
