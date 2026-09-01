package com.sainsburys.grocery;

/**
 * Contract representing whether a refund request is approved and the final settlement values.
 */
public record RefundResult(boolean approved, String message, double refundAmount, long pointsDeducted) {
    public static RefundResult approvedResult() {
        return new RefundResult(true, "Approved", 0.0, 0L);
    }

    public static RefundResult approvedResult(double refundAmount, long pointsDeducted) {
        return new RefundResult(true, "Approved", refundAmount, pointsDeducted);
    }

    public static RefundResult rejectedResult(String message) {
        return new RefundResult(false, message, 0.0, 0L);
    }
}
