package com.sainsburys.grocery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class RefundServiceTest {

    @Test
    void standardItemRefundOnDay30IsApproved() {
        RefundRequest request = new RefundRequest(
            "ORD-100",
            new Item("1", "Orange Juice", 3.99),
            LocalDate.now().minusDays(30),
            RefundReason.CHANGE_OF_MIND,
            false
        );

        RefundResult result = new RefundService().processRefund(request);

        assertTrue(result.approved());
    }

    @Test
    void standardItemRefundOnDay31IsRejected() {
        RefundRequest request = new RefundRequest(
            "ORD-101",
            new Item("2", "Cereal", 2.49),
            LocalDate.now().minusDays(31),
            RefundReason.CHANGE_OF_MIND,
            false
        );

        RefundResult result = new RefundService().processRefund(request);

        assertFalse(result.approved());
    }

    @Test
    void changeOfMindOnOpenedItemIsRejected() {
        RefundRequest request = new RefundRequest(
            "ORD-102",
            new Item("3", "Coffee Beans", 5.99),
            LocalDate.now().minusDays(10),
            RefundReason.CHANGE_OF_MIND,
            true
        );

        RefundResult result = new RefundService().processRefund(request);

        assertFalse(result.approved());
    }

    @Test
    void defectiveItemRemainsEligibleUnderTheExceptionPath() {
        RefundRequest request = new RefundRequest(
            "ORD-103",
            new Item("4", "Fresh Milk", 2.25),
            LocalDate.now().minusDays(45),
            RefundReason.DEFECTIVE,
            true
        );

        RefundResult result = new RefundService().processRefund(request);

        assertTrue(result.approved());
    }

    @Test
    void refundWithNectarPriceCalculatesExpectedClawback() {
        RefundRequest request = new RefundRequest(
            "ORD-104",
            new Item("5", "Bananas", 3.49, 2.99),
            LocalDate.now().minusDays(5),
            RefundReason.CHANGE_OF_MIND,
            false
        );

        RefundResult result = new RefundService().processRefund(request);

        assertTrue(result.approved());
        assertEquals(2.99, result.refundAmount(), 0.001);
        assertEquals(50L, result.pointsDeducted());
    }

    @Test
    void refundWithoutNectarPriceReturnsZeroClawback() {
        RefundRequest request = new RefundRequest(
            "ORD-105",
            new Item("6", "Cereal", 2.49),
            LocalDate.now().minusDays(8),
            RefundReason.CHANGE_OF_MIND,
            false
        );

        RefundResult result = new RefundService().processRefund(request);

        assertTrue(result.approved());
        assertEquals(2.49, result.refundAmount(), 0.001);
        assertEquals(0L, result.pointsDeducted());
    }

    @Test
    void futurePurchaseDateIsRejectedSafely() {
        RefundRequest request = new RefundRequest(
            "ORD-106",
            new Item("7", "Tea", 4.00),
            LocalDate.now().plusDays(1),
            RefundReason.CHANGE_OF_MIND,
            false
        );

        RefundResult result = new RefundService().processRefund(request);

        assertFalse(result.approved());
        assertEquals(0.0, result.refundAmount(), 0.001);
        assertEquals(0L, result.pointsDeducted());
    }

    @Test
    void approvedRefundAmountMatchesExpectedTotalForValidCase() {
        RefundRequest request = new RefundRequest(
            "ORD-107",
            new Item("8", "Coffee Beans", 5.99),
            LocalDate.now().minusDays(12),
            RefundReason.CHANGE_OF_MIND,
            false
        );

        RefundResult result = new RefundService().processRefund(request);

        assertTrue(result.approved());
        assertEquals(5.99, result.refundAmount(), 0.001);
    }
}
