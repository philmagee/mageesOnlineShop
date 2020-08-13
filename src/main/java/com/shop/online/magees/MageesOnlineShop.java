package com.shop.online.magees;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.*;

import com.shop.online.magees.item.InventoryCache;
import com.shop.online.magees.item.Item;
import com.shop.online.magees.receipt.Receipt;
import com.shop.online.magees.tax.Tax;

public class MageesOnlineShop {
	
	private static JComboBox<Item> inventoryItemsComboBox = new JComboBox<>();
	private static JComboBox<BigDecimal> quantityComboBox = new JComboBox<>();
	private static JLabel receiptLabel = new JLabel();
	
	public static void main(String[] args) {
		generateMainScreen();
	}
	
	public static void generateMainScreen() {
		JFrame frame = generateMainJFrame();
		inventoryItemsComboBox = generateInventoryComboBox();
		quantityComboBox = generateQuantityComboBox(5);
        frame.getContentPane().add(BorderLayout.CENTER, receiptLabel);
        frame.getContentPane().add(BorderLayout.SOUTH, generateInputPanel());
        frame.setVisible(true);
	}

	private static JFrame generateMainJFrame() {
		JFrame frame = new JFrame("Magee's Online Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        return frame;
	}
	
	private static JComboBox<Item> generateInventoryComboBox() {
        JComboBox<Item> jComboBox = new JComboBox<>();
        
        for (Item item : InventoryCache.getInstance().getItemInventoryList()) {
        	jComboBox.addItem(item);
        }
        
        return jComboBox;
	}
	
	private static JComboBox<BigDecimal> generateQuantityComboBox(int maxQuantity) {
        JComboBox<BigDecimal> jComboBox = new JComboBox<>();
        for (int i = 1; i <= maxQuantity; i++) {
        	jComboBox.addItem(new BigDecimal(i));
        }
        
        return jComboBox;
	}
	
	private static JPanel generateInputPanel() {
        JPanel inputPanel = new JPanel();
        JLabel quantityLabel = new JLabel("Quantity");
        JButton addButton = generateAddButton();
        JButton resetButton = generateResetButton();
        inputPanel.add(inventoryItemsComboBox);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityComboBox);
        inputPanel.add(addButton);
        inputPanel.add(resetButton);

        return inputPanel;
	}

	private static JButton generateAddButton() {
		JButton addButton = new JButton("Add");
		
		addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	Item comboBoxItem = (Item) inventoryItemsComboBox.getSelectedItem();
            	BigDecimal quantity = (BigDecimal) quantityComboBox.getSelectedItem(); 
            	BigDecimal subTotal = quantity.multiply(comboBoxItem.getPrice());
            	Receipt receipt = Receipt.getInstance();
            	
            	receipt.addListOfItemsOnReceipt(comboBoxItem, quantity.intValue()); 
            	receipt.addBasicSalesTaxAmount(Tax.calculateBasicSalesTaxOnItem(comboBoxItem, quantity));
        		receipt.addImportedSalesTaxAmount(Tax.calculateImportedSalesTaxOnItem(comboBoxItem, quantity));
        		receipt.addSubTotalAmount(subTotal);

            	StringBuilder itemListAsString = new StringBuilder();
            	for (Map.Entry<Item, Integer> items : Receipt.getInstance().getListOfItemsOnReceipt().entrySet()) {
            		Item itemFromReceipt = items.getKey();            		
            		itemListAsString.append(items.getValue() + " " + itemFromReceipt.getName() + ": " + Receipt.calculateItemTotalAmount(itemFromReceipt, BigDecimal.valueOf(items.getValue())) + "<br>");
            	}
            	
            	BigDecimal totalTaxes = receipt.getBasicSalesTaxAmount().add(receipt.getImportedSalesTaxAmount());
            	receipt.setTotalAmount(receipt.getSubTotalAmount().add(totalTaxes));
            	itemListAsString.append("Sales Taxes: " + totalTaxes + " Total: " + receipt.getTotalAmount() + "<br>");

            	receiptLabel.setText("<html>" + itemListAsString + "</html>");
            }
        });
		
		return addButton;
	}
	
	private static JButton generateResetButton() {
		JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	Receipt.getInstance().clear();
            	receiptLabel.setText("");
            }
        });
		return resetButton;
	}

}
