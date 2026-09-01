package com.sainsburys.grocery;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Basket pricing logic and age restrictions.
 * Tests cover core business rules: Nectar discounts, subtotal calculations, and age-gating.
 */
class BasketTest {

    @Test
    void emptyBasketSubtotalIsZero() {
        Basket basket = new Basket(false, null);
        assertEquals(0.00, basket.calculateSubtotal());
    }

    @Test
    void nonMemberUsesStandardPrice() {
        Item coffee = new Item("1", "Sainsbury's Taste the Difference Coffee", 4.50);
        Basket basket = new Basket(false, null);
        basket.addItem(coffee);

        assertEquals(4.50, basket.calculateSubtotal());
    }

    @Test
    void nectarMemberAppliesDiscountedPrice() {
        Item coffee = new Item("1", "Sainsbury's Taste the Difference Coffee", 4.50, 3.50);
        Basket basket = new Basket(true, null);
        basket.addItem(coffee);

        assertEquals(3.50, basket.calculateSubtotal());
    }

    @Test
    void nectarMemberFallsBackToStandardPriceWhenNectarPriceIsNull() {
        Item coffee = new Item("1", "Sainsbury's Taste the Difference Coffee", 4.50, null);
        Basket basket = new Basket(true, null);
        basket.addItem(coffee);

        assertEquals(4.50, basket.calculateSubtotal());
    }

    @Test
    void subtotalRoundsTo2DecimalPlaces() {
        Item item1 = new Item("1", "Item 1", 1.335);
        Item item2 = new Item("2", "Item 2", 2.115);
        Basket basket = new Basket(false, null);
        basket.addItem(item1);
        basket.addItem(item2);

        double subtotal = basket.calculateSubtotal();
        assertEquals(3.45, subtotal);
    }

    @Test
    void singleItemWithNectarPriceIsStoredAndReadable() {
        Item coffee = new Item("1", "Sainsbury's Taste the Difference Coffee", 4.50, 3.50);
        Basket basket = new Basket(true, null);
        basket.addItem(coffee);

        assertEquals(1, basket.getItems().size());
        Item retrievedItem = basket.getItems().get(0);
        assertEquals("1", retrievedItem.id());
        assertEquals("Sainsbury's Taste the Difference Coffee", retrievedItem.name());
        assertEquals(4.50, retrievedItem.price());
        assertEquals(3.50, retrievedItem.nectarPrice());
        assertFalse(retrievedItem.isAgeRestricted());
    }

    @Test
    void ageRestrictedItemFailsUnder18() {
        Item wine = new Item("2", "Pinot Grigio", 8.00, true);
        Basket basket = new Basket(false, 17);
        basket.addItem(wine);

        assertFalse(basket.validateAgeRestrictions());
    }

    @Test
    void ageRestrictedItemPassesAt18() {
        Item wine = new Item("2", "Pinot Grigio", 8.00, true);
        Basket basket = new Basket(false, 18);
        basket.addItem(wine);

        assertTrue(basket.validateAgeRestrictions());
    }

    @Test
    void ageRestrictedItemPassesWithoutAgeCheck() {
        Item wine = new Item("2", "Pinot Grigio", 8.00, true);
        Basket basket = new Basket(false, null);
        basket.addItem(wine);

        assertFalse(basket.validateAgeRestrictions());
    }

    @Test
    void nonAgeRestrictedItemPassesWithoutAgeCheck() {
        Item coffee = new Item("1", "Sainsbury's Coffee", 4.50);
        Basket basket = new Basket(false, null);
        basket.addItem(coffee);

        assertTrue(basket.validateAgeRestrictions());
    }
}
