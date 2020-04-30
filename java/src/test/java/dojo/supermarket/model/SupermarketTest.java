package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

	
	private SupermarketCatalog catalog;
	private Teller teller;
	private Product toothbrush;
	private Product apples;
	
	@BeforeEach
	public void refresh() {
		catalog = new FakeCatalog();
		teller  = new Teller(catalog);
		
		toothbrush = new Product("toothbrush", ProductUnit.Each);
		apples = new Product("apples", ProductUnit.Kilo);
		
		catalog.addProduct(toothbrush, 0.99);
		catalog.addProduct(apples, 1.99);
		
	}
	
	@Test
	public void whenCheckoutBasicBehaviour() {
		 ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice());
        assertEquals(2.5*1.99, receiptItem.getTotalPrice());
        assertEquals(2.5, receiptItem.getQuantity());
        assertEquals(1, receipt.getItems().size());
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
	}
	
    

	
    @Test
    public void tenPercentDiscount() {

        teller.addSpecialOffer(new PercentageOffer(apples, 10.0));
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.477, receipt.getTotalPrice(), 0.01);
    }	
   
    
    @Test
    public void threeForTwoDiscount() {
    	
        teller.addSpecialOffer(new AmountOffer(toothbrush, 3, 2));
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(0.99*2, receipt.getTotalPrice(), 0);
    }
	
     
    @Test
    public void fiveForAmountDiscount() {
        teller.addSpecialOffer(new BoxOffer(toothbrush, 5, 7.49));

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(7.49, receipt.getTotalPrice(), 0);
    }
	
    @Test
    public void twoForAmountDiscount() {
        teller.addSpecialOffer(new BoxOffer(toothbrush, 2, 4));

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 1);
        cart.addItem(toothbrush);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4, receipt.getTotalPrice(), 0);
    }
     

}
