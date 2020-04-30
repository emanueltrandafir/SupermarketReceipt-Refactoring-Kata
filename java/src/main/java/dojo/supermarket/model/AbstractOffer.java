package dojo.supermarket.model;

import java.util.Optional;

public abstract class AbstractOffer {

    protected final Product product;
    double argument;

    public AbstractOffer(Product product) {
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }
    
    abstract Optional<Discount>  calculateDiscount(double quantity, double unitPrice);
}
