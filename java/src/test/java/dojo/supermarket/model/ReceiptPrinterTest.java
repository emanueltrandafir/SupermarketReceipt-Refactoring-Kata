package dojo.supermarket.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dojo.supermarket.ReceiptPrinter;

public class ReceiptPrinterTest {

	
	private SupermarketCatalog catalog;
	private Teller teller;
	private Product toothbrush;
	private Product apples;
	private ReceiptPrinter receiptPrinter;
	
	@BeforeEach
	public void refresh() {
		catalog = new FakeCatalog();
		teller  = new Teller(catalog);
		
		toothbrush = new Product("toothbrush", ProductUnit.Each);
		apples = new Product("apples", ProductUnit.Kilo);
		
		catalog.addProduct(toothbrush, 0.99);
		catalog.addProduct(apples, 1.99);
		
		receiptPrinter = new ReceiptPrinter();
	}
	
	
	
	@Test
	public void whenPrintingBasicBehaviour() {
		ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        cart.addItemQuantity(toothbrush, 2);
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        
        // ACT
        String printReceipt = receiptPrinter.printReceipt(receipt);

        // ASSERT 
        
        String expectedReceipt = "apples                              4.98\n" + 
				        		 "  1.99 * 2.500\n" + 
				        		 "toothbrush                          1.98\n" + 
				        		 "  0.99 * 2\n" + 
				        		 "\n" + 
				        		 "Total:                              6.96\n";
        
        assertEquals(expectedReceipt,printReceipt);
	}
	
	@Test
	public void whenCheckoutDiscountBehaviour() {
		ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        cart.addItemQuantity(toothbrush, 2);
        teller.addSpecialOffer(new PercentageOffer(apples, 10.0));
        
        Receipt receipt = teller.checksOutArticlesFrom(cart);
        
        // ACT
        String printReceipt = receiptPrinter.printReceipt(receipt);

        // ASSERT 
        
        String expectedReceipt = "apples                              4.98\n" + 
					        		"  1.99 * 2.500\n" + 
					        		"toothbrush                          1.98\n" + 
					        		"  0.99 * 2\n" + 
					        		"10.0% off(apples)                  -0.50\n" + 
					        		"\n" + 
					        		"Total:                              6.46\n";
        
        assertEquals(expectedReceipt,printReceipt);
	}
	
}
