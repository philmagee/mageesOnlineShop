package com.shop.online.magees.item;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class InventoryCache {

	private static InventoryCache inventoryCache = null;
	private static final String INVENTORY_FILE_PATH = "/src/main/resources/inventory.csv";
	private List<Item> itemInventoryList = new ArrayList<>();

	private InventoryCache() {

		try (InputStream inputStream = getClass().getResourceAsStream(INVENTORY_FILE_PATH);
				Reader targetReader = new InputStreamReader(inputStream);
				CSVParser csvParser = new CSVParser(targetReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			
			for (CSVRecord csvRecord : csvParser) {
				Item item = new Item(csvRecord.get("NAME"), csvRecord.get("CATEGORY"), new BigDecimal(csvRecord.get("PRICE")), Boolean.parseBoolean(csvRecord.get("IMPORTED")));
				itemInventoryList.add(item);
			}
		} catch (IOException e) {
			System.out.println("InventoryCache: An IOException has occurred during the loading of the Inventory Cache, the application will shut down for investigation. The error that occurred is: " + e.getMessage());
			System.exit(1);
		} catch (NumberFormatException nfe) {
			System.out.println("InventoryCache: A NumberFormatException has occurred during the loading of the Inventory Cache, most likely due to an incorrect pricing format located in the inventory file. The application will shut down for investigation. The error that occurred is: " + nfe.getCause());
			System.exit(1);
		}
	}

	public static synchronized InventoryCache getInstance() {
		if (inventoryCache == null) {
			inventoryCache = new InventoryCache();
		}
		return inventoryCache;
	}
	
	public List<Item> getItemInventoryList() {
		return this.itemInventoryList;
	}
}