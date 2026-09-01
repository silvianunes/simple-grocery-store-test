package com.sainsburys.grocery;

import java.time.LocalDate;

/**
 * Refund request submitted for a purchased item.
 */
public record RefundRequest(
    String orderId,
    Item item,
    LocalDate purchaseDate,
    RefundReason reason,
    boolean opened
) {
}
