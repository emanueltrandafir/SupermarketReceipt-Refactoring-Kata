package dojo.supermarket.model;

import java.util.Optional;

public class AmountOffer extends AbstractOffer {

	private int itemsBuyed;
	private int itemsPayed;

	public AmountOffer(Product product, int itemsBuyed, int itemsPayed ) {
		super(product);
		this.itemsBuyed = itemsBuyed;
		this.itemsPayed = itemsPayed; 
	}
	
	@Override
	Optional<Discount> calculateDiscount(double quantity, double unitPrice) {
		
		int quantityAsInt = (int) quantity;
		if(quantityAsInt <= itemsPayed) {
			return Optional.empty();
		}
        int numberOfXs = quantityAsInt / itemsBuyed;
		double discountAmount = quantity * unitPrice - ((numberOfXs * itemsPayed * unitPrice) + quantityAsInt % itemsBuyed * unitPrice);
		
		return Optional.of(new Discount(product, itemsBuyed + " for " + itemsPayed, -discountAmount));
	}

	
	
}
