package com.shop.online.magees.item;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class Item {

	private String name;
	private String category;
	private boolean exemptionStatus;
	private boolean imported;
	private BigDecimal price;
	
	public Item(String name, String category, BigDecimal price, boolean imported) {
		this.setName(name);
		this.setCategory(category);
		this.setPrice(price);
		this.setImported(imported);
		this.setExemptionStatus(calculateExemptionStatus());
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public boolean isExemptionStatus() {
		return exemptionStatus;
	}
	
	public void setExemptionStatus(boolean exemptionStatus) {
		this.exemptionStatus = exemptionStatus;
	}
	
	public boolean isImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public boolean calculateExemptionStatus() {
		return Arrays.stream(ExemptItems.values()).anyMatch(t -> t.name().equals(this.getCategory()));
	}
	
    @Override
    public String toString() {
        return  this.getName() + " - " +
        		this.getPrice();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return Boolean.TRUE; 
        }
        
        if (Objects.isNull(other)) {
            return Boolean.FALSE;
        }
        
        if (!(other instanceof Item)) {
            return Boolean.FALSE;
        }
        
        Item castOther = (Item) other;
        
        return (Objects.equals(this.getName(), castOther.getName()) &&
        		Objects.equals(this.getCategory(), castOther.getCategory()) &&
        		Objects.equals(this.getPrice(), castOther.getPrice()) &&
        		Objects.equals(this.isExemptionStatus(), castOther.isExemptionStatus()) &&
        		Objects.equals(this.isImported(), castOther.isImported()));
    }

    @Override
    public int hashCode() {
    	return Objects.hash(this.getName(), this.getCategory(), this.getPrice(), this.isExemptionStatus(), this.isImported());
    }
}
