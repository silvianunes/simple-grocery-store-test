
# Sainsbury's Simple Grocery & Refund Rules (Java)

## Core Principles
- **Small Batch Diffs:** Generate strictly incremental changes. Do not refactor unrelated classes or introduce heavy design patterns for simple logic.
- **Defensibility:** Business logic must be explicit, easy to explain, and covered by JUnit 5 tests. The human developer must be able to explain all generated logic in under 30 seconds.
- **Domain Precision:** Pay close attention to monetary rounding, nullability for promotional prices, age-gating, and refund boundaries.

## Technical Context
- **Language:** Java 17+ (utilize records, pattern matching, and modern Streams where readable)
- **Test Framework:** JUnit 5 & AssertJ
- **Build Tool:** Maven

## Business & Refund Rules
- **Return Window:** Standard items can be refunded within 30 days of purchase.
- **Perishable Goods:** Fresh grocery and perishable items are non-refundable unless flagged as damaged or defective (`RefundReason.DEFECTIVE`).
- **Nectar Loyalty:** Refunds on items purchased at Nectar prices must calculate and apply point clawbacks based on the discount earned.
- **Age Restricted Items:** Unopened age-restricted goods require explicit age verification logs upon refund processing.

## Execution Constraints
- Always update or add corresponding unit tests in `src/test/java/` when introducing or altering business rules.
- Prefer explicit guard clauses over deeply nested `if-else` blocks.
- Keep diffs small and strictly focused on a single prompt requirement (under 50 lines changed per task).
