package com.shop.online.magees.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.shop.online.magees.item.ExemptItems;

public class TestExemptItems {

	@Test
	public void exemptItemsEnumTest() {
		assertTrue(Arrays.stream(ExemptItems.values()).anyMatch(t -> t.name().equals("BOOKS")));
		assertTrue(Arrays.stream(ExemptItems.values()).anyMatch(t -> t.name().equals("FOOD")));
		assertTrue(Arrays.stream(ExemptItems.values()).anyMatch(t -> t.name().equals("MEDICAL")));
		assertFalse(Arrays.stream(ExemptItems.values()).anyMatch(t -> t.name().equals("NOTAPPLICABLE")));
		assertFalse(Arrays.stream(ExemptItems.values()).anyMatch(t -> t.name().equals("BOOK")));
	}
}