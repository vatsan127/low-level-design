package lld._02_solid.srp;

import java.util.List;

// SRP violation: this class has THREE reasons to change.
// 1. The way invoice totals are calculated changes (business rule).
// 2. The persistence layer changes (DB schema, file format).
// 3. The email format/transport changes (template, SMTP server).
//
// Any of those concerns evolving forces edits to this same class.
public class Bad {

    static class Invoice {
        private final List<Double> lineItems;
        private final String customerEmail;

        Invoice(List<Double> lineItems, String customerEmail) {
            this.lineItems = lineItems;
            this.customerEmail = customerEmail;
        }

        // Business rule
        double calculateTotal() {
            double total = 0;
            for (Double item : lineItems) total += item;
            return total;
        }

        // Persistence
        void saveToDatabase() {
            System.out.println("INSERT INTO invoices VALUES (" + calculateTotal() + ")");
        }

        // Transport / formatting
        void emailToCustomer() {
            System.out.println("To: " + customerEmail
                    + "\nSubject: Your invoice\nTotal due: " + calculateTotal());
        }
    }

    public static void main(String[] args) {
        Invoice inv = new Invoice(List.of(10.0, 20.0, 30.0), "alice@example.com");
        inv.saveToDatabase();
        inv.emailToCustomer();
    }
}
