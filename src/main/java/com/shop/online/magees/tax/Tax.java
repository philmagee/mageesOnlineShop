package com.shop.online.magees.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.shop.online.magees.item.Item;

public class Tax {

	public static final BigDecimal BASIC_SALES_TAX = new BigDecimal("0.10");
	public static final BigDecimal IMPORTED_SALES_TAX = new BigDecimal("0.05");	
	
	public static final BigDecimal TAX_ROUNDING_RATE = new BigDecimal("0.05");
	public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.UP;
	
	private static final String DEFAULT_ZERO_RETURN_VALUE = "0.00";
	
	private Tax() {}
	
	public static BigDecimal calculateTaxAmountRounded(BigDecimal amount, BigDecimal taxRate, BigDecimal roundToTheNearest, RoundingMode roundingMode) {
		if (Objects.isNull(taxRate) || taxRate.signum() == 0) {
			//In order to handle a scenario where the taxRate being passed in is null or 0, we will return the amount passed in as the result. 
			return amount;
		} else if  (Objects.isNull(amount)) {
			return new BigDecimal(0);
		} else {
			if (Objects.isNull(roundingMode)) {
				//If the rounding mode is null, we will default to round up. 
				roundingMode = DEFAULT_ROUNDING_MODE;
			}
			BigDecimal taxAmount = amount.multiply(taxRate);
	        BigDecimal divided = taxAmount.divide(roundToTheNearest, 0, roundingMode);
	        return divided.multiply(roundToTheNearest);
		}
	}
	
	public static BigDecimal calculateBasicSalesTaxOnItem(Item item, BigDecimal quantity) {
		if (!item.isExemptionStatus()) {
			return Tax.calculateTaxAmountRounded(item.getPrice().multiply(quantity), Tax.BASIC_SALES_TAX, Tax.TAX_ROUNDING_RATE, Tax.DEFAULT_ROUNDING_MODE);
		} else {
			return new BigDecimal(DEFAULT_ZERO_RETURN_VALUE);
		}
	}
	
	public static BigDecimal calculateImportedSalesTaxOnItem(Item item, BigDecimal quantity) {
		if (item.isImported()) {
			return Tax.calculateTaxAmountRounded(item.getPrice().multiply(quantity), Tax.IMPORTED_SALES_TAX, Tax.TAX_ROUNDING_RATE, Tax.DEFAULT_ROUNDING_MODE);
		} else {
			return new BigDecimal(DEFAULT_ZERO_RETURN_VALUE);
		}
	}
}