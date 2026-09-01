# simple-grocery-store-test

This small grocery refund service enforces the store's return policy with explicit guard clauses and auditable settlement values.

- Standard refunds are allowed within 30 days of purchase.
- Fresh or perishable items remain non-refundable unless they are marked as defective.
- Nectar refunds apply a clawback based on the discount difference, and the final refund total is returned in the settlement result.
- Rejected requests return a zeroed settlement to keep downstream processing safe and reviewable.