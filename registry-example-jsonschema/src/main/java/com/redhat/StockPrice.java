package com.redhat;

public class StockPrice {

    private String symbol;
    // private String stock;
    private Double price;

    public StockPrice() {}

    public StockPrice(String symbol, Double price) {
        this.symbol = symbol;
        // this.stock = symbol;
        this.price = price;
    }

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	// public String getStock() {
	// 	return stock;
	// }
	// public void setStock(String symbol) {
	// 	this.stock = symbol;
	// }
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

}