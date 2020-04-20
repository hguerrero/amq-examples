package com.redhat;

public class StockPrice {

    private String symbol;
    private Double price;

    public StockPrice() {}

    public StockPrice(String symbol, Double price) {
        this.symbol = symbol;
        this.price = price;
    }

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		try {
			return new com.fasterxml.jackson.databind.ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (com.fasterxml.jackson.core.JsonProcessingException e) {
			e.printStackTrace();
		}
		return super.toString();
	}
}