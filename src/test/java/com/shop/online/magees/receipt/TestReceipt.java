package com.shop.online.magees.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.shop.online.magees.item.Item;

public class TestReceipt {
	@Test
	public void calculateItemTotalAmountTest() {
		//The following test cases outline that the receipt calculation of the total amount is being performed correctly as per the Item specification and quantity.
		
		//This test case will result in an Item that has tax exemption status and no imported taxes
		Item item = new Item("Book", "BOOKS", new BigDecimal("10.96"), false);
		assertEquals(new BigDecimal("10.96"), Receipt.calculateItemTotalAmount(item, new BigDecimal("1")));
		
		//This test case will result in an Item that has tax exemption status and imported taxes
		item.setImported(Boolean.TRUE);
		assertEquals(new BigDecimal("11.51"), Receipt.calculateItemTotalAmount(item, new BigDecimal("1")));
		
		//This test case will result in an Item that does not have tax exemption status and imported taxes
		item.setCategory("GOODS");
		item.setExemptionStatus(item.calculateExemptionStatus());
		assertEquals(new BigDecimal("12.61"), Receipt.calculateItemTotalAmount(item, new BigDecimal("1")));
		
		//This test case will result in an Item that does not have tax exemption status and no imported taxes
		item.setImported(Boolean.FALSE);
		assertEquals(new BigDecimal("12.06"), Receipt.calculateItemTotalAmount(item, new BigDecimal("1")));
		
	}
}