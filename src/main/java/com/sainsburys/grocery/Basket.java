package com.sainsburys.grocery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Shopping basket that accumulates items and calculates totals based on pricing rules.
 * Supports Nectar member discounts and validates age-restricted purchases.
 */
public class Basket {
    private final List<Item> items = new ArrayList<>();
    private final boolean isNectarMember;
    private final Integer customerAge;

    /**
     * Constructs a basket with optional Nectar membership and customer age.
     *
     * @param isNectarMember whether the customer is a Nectar loyalty member
     * @param customerAge    customer's age (required if basket contains age-restricted items)
     */
    public Basket(boolean isNectarMember, Integer customerAge) {
        this.isNectarMember = isNectarMember;
        this.customerAge = customerAge;
    }

    /**
     * Adds an item to the basket.
     */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Calculates the subtotal by applying pricing rules:
     * - Nectar members use discounted price if available
     * - All prices rounded to 2 decimal places using half-up rounding
     */
    public double calculateSubtotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Item item : items) {
            BigDecimal itemPrice = isNectarMember && item.nectarPrice() != null
                ? BigDecimal.valueOf(item.nectarPrice())
                : BigDecimal.valueOf(item.price());
            total = total.add(itemPrice);
        }

        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Validates age restrictions for items in the basket.
     * Returns false if basket contains age-restricted items and customer is under 18.
     */
    public boolean validateAgeRestrictions() {
        boolean hasRestrictedItem = items.stream().anyMatch(Item::isAgeRestricted);
        if (hasRestrictedItem) {
            return customerAge != null && customerAge >= 18;
        }
        return true;
    }

    /**
     * Returns an immutable copy of items in the basket.
     */
    public List<Item> getItems() {
        return List.copyOf(items);
    }
}
