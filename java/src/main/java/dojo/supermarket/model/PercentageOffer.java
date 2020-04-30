package dojo.supermarket.model;

import java.util.Optional;

public class PercentageOffer extends AbstractOffer {

	private double value;

	public PercentageOffer(Product product, double value) {
		super(product);
		this.value = value;
	}
	
	@Override
	Optional<Discount>  calculateDiscount( double quantity , double unitPrice) {
		return Optional.of(new Discount(product, value + "% off", -quantity * unitPrice * value / 100.0));
	}

}
