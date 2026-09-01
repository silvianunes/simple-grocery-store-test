package com.sainsburys.grocery;

/**
 * Contract representing whether a refund request is approved.
 */
public record RefundResult(boolean approved, String message) {
    public static RefundResult approvedResult() {
        return new RefundResult(true, "Approved");
    }

    public static RefundResult rejectedResult(String message) {
        return new RefundResult(false, message);
    }
}
