package com.shop.online.magees.tax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.shop.online.magees.item.Item;

public class TestTax {

	@Test
	public void calculateTaxAmountRoundedTest() {
		//The following test cases outline that the calculation of the taxed amount is being performed correctly and rounding to the correct level as per the rounding rate. 
		assertEquals(new BigDecimal("1.50"), Tax.calculateTaxAmountRounded(new BigDecimal("14.99"), Tax.BASIC_SALES_TAX, Tax.TAX_ROUNDING_RATE, Tax.DEFAULT_ROUNDING_MODE));
		assertEquals(new BigDecimal("0.50"), Tax.calculateTaxAmountRounded(new BigDecimal("10.00"), Tax.IMPORTED_SALES_TAX, Tax.TAX_ROUNDING_RATE, Tax.DEFAULT_ROUNDING_MODE));
		assertEquals(new BigDecimal("4.75"), Tax.calculateTaxAmountRounded(new BigDecimal("47.50"), Tax.BASIC_SALES_TAX, Tax.TAX_ROUNDING_RATE, Tax.DEFAULT_ROUNDING_MODE));
		assertEquals(new BigDecimal("2.40"), Tax.calculateTaxAmountRounded(new BigDecimal("47.50"), Tax.IMPORTED_SALES_TAX, Tax.TAX_ROUNDING_RATE, Tax.DEFAULT_ROUNDING_MODE));
		assertEquals(new BigDecimal("1.65"), Tax.calculateTaxAmountRounded(new BigDecimal("32.19"), Tax.IMPORTED_SALES_TAX, Tax.TAX_ROUNDING_RATE, Tax.DEFAULT_ROUNDING_MODE));
		assertEquals(new BigDecimal("3.25"), Tax.calculateTaxAmountRounded(new BigDecimal("32.19"), Tax.BASIC_SALES_TAX, Tax.TAX_ROUNDING_RATE, Tax.DEFAULT_ROUNDING_MODE));
	}
	
	@Test
	public void calculateBasicSalesTaxOnItemTest() {
		//The following test cases outline that the calculation of the basic taxed amount is being performed correctly as per the Item specification (isExemptionStatus) and rounding to the correct level as per the rounding rate. 
		Item item = new Item("Book", "BOOKS", new BigDecimal("10.96"), false);
		assertEquals(new BigDecimal("0.00"), Tax.calculateBasicSalesTaxOnItem(item, new BigDecimal("1")));

		item.setCategory("GOODS");
		item.setExemptionStatus(item.calculateExemptionStatus());
		assertEquals(new BigDecimal("1.10"), Tax.calculateBasicSalesTaxOnItem(item, new BigDecimal("1")));
	}
	
	@Test
	public void calculateImportedSalesTaxOnItem() {
		//The following test cases outline that the calculation of the imported taxed amount is being performed correctly as per the Item specification (isImported) and rounding to the correct level as per the rounding rate. 
		Item item = new Item("Book", "BOOKS", new BigDecimal("10.96"), Boolean.FALSE);
		assertEquals(new BigDecimal("0.00"), Tax.calculateImportedSalesTaxOnItem(item, new BigDecimal("1")));
		
		item.setImported(Boolean.TRUE);
		assertEquals(new BigDecimal("0.55"), Tax.calculateImportedSalesTaxOnItem(item, new BigDecimal("1")));
	}
}