package com.sainsburys.grocery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * Enforces the store's refund policy with explicit guard clauses for readability and auditability.
 */
public class RefundService {
    private static final long STANDARD_RETURN_WINDOW_DAYS = 30L;
    private static final Set<String> PERISHABLE_KEYWORDS = Set.of(
        "milk", "bread", "cheese", "yogurt", "salad", "fruit", "vegetable",
        "meat", "fish", "fresh", "berries", "lettuce", "avocado", "tomato"
    );

    public RefundResult processRefund(RefundRequest request) {
        if (request == null) {
            return RefundResult.rejectedResult("Refund request is required.");
        }

        if (request.orderId() == null || request.orderId().isBlank()) {
            return RefundResult.rejectedResult("Order id is required.");
        }

        if (request.item() == null) {
            return RefundResult.rejectedResult("Item is required.");
        }

        if (request.purchaseDate() == null) {
            return RefundResult.rejectedResult("Purchase date is required.");
        }

        if (request.reason() == null) {
            return RefundResult.rejectedResult("Refund reason is required.");
        }

        if (isDefectiveRequest(request)) {
            return RefundResult.approvedResult();
        }

        if (isPerishableItem(request.item())) {
            return RefundResult.rejectedResult("Fresh or perishable goods are non-refundable unless defective.");
        }

        if (request.opened() && request.reason() == RefundReason.CHANGE_OF_MIND) {
            return RefundResult.rejectedResult("Opened items cannot be refunded for change of mind.");
        }

        long daysSincePurchase = ChronoUnit.DAYS.between(request.purchaseDate(), LocalDate.now());
        if (daysSincePurchase < 0) {
            return RefundResult.rejectedResult("Refund request cannot be dated in the future.");
        }

        if (daysSincePurchase > STANDARD_RETURN_WINDOW_DAYS) {
            return RefundResult.rejectedResult("Refund is outside the 30-day return window.");
        }

        return RefundResult.approvedResult();
    }

    private boolean isDefectiveRequest(RefundRequest request) {
        return request.reason() == RefundReason.DEFECTIVE;
    }

    private boolean isPerishableItem(Item item) {
        String normalizedName = item.name() == null ? "" : item.name().toLowerCase();
        return PERISHABLE_KEYWORDS.stream().anyMatch(normalizedName::contains);
    }
}
