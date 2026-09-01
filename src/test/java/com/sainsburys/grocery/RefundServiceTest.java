package com.sainsburys.grocery;

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
}
