package be.vdab.ToysForBoys.queryresults;

import java.math.BigDecimal;

public class OrderTable {
    private final String productName;
    private final BigDecimal priceEach;
    private final int quantity;
    private final BigDecimal totaal;
    private final int inStock;
    public OrderTable(String productName, BigDecimal priceEach, int quantity, BigDecimal totaal, int inStock) {
        this.productName = productName;
        this.priceEach = priceEach;
        this.quantity = quantity;
        this.totaal = totaal;
        this.inStock = inStock;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }

    public int getQuantity() {
        return quantity;
    }
    public BigDecimal getTotaal(){
        return totaal;
    }

    public int getInStock() {
        return inStock;
    }
}
