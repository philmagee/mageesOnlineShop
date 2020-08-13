package com.shop.online.magees.item;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

public class TestInventoryCache {

	@Test
	public void loadInventoryCacheTest() {
		assertNotNull(InventoryCache.getInstance());
	}
}