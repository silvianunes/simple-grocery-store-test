package com.sainsburys.grocery;

/**
 * Immutable product model representing a single grocery item.
 * Supports full price, optional Nectar loyalty pricing, and age restrictions.
 */
public record Item(
    String id,
    String name,
    double price,
    boolean isAgeRestricted,
    Double nectarPrice
) {
    /**
     * Constructor for a standard item without Nectar pricing or age restrictions.
     */
    public Item(String id, String name, double price) {
        this(id, name, price, false, null);
    }

    /**
     * Constructor for an item with Nectar pricing support.
     */
    public Item(String id, String name, double price, Double nectarPrice) {
        this(id, name, price, false, nectarPrice);
    }

    /**
     * Constructor for an age-restricted item (e.g., alcohol).
     */
    public Item(String id, String name, double price, boolean isAgeRestricted) {
        this(id, name, price, isAgeRestricted, null);
    }
}
