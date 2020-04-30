package dojo.supermarket.model;

import java.util.Optional;

public class BoxOffer extends AbstractOffer{

	private int itemsBuyed;
	private double price;

	public BoxOffer (Product product, int itemsBuyed, double price) {
		super(product);
		this.itemsBuyed = itemsBuyed;
		this.price = price;
	}
	
	@Override
	Optional<Discount> calculateDiscount(double quantity, double unitPrice) {
		int quantityAsInt = (int) quantity;
		if(quantityAsInt < itemsBuyed) {
			return Optional.empty();
		}
        int numberOfXs = quantityAsInt / itemsBuyed;
		double discountTotal = unitPrice * quantity - (price * numberOfXs + quantityAsInt % itemsBuyed * unitPrice);
		return Optional.of(new Discount(product, itemsBuyed + " for " + price, -discountTotal));
	}

	

}
