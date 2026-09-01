package com.sainsburys.grocery;

/**
 * Supported refund reasons. The defective path is treated as an explicit exception
 * to the standard return-window and perishable-goods policy.
 */
public enum RefundReason {
    CHANGE_OF_MIND,
    DEFECTIVE
}
