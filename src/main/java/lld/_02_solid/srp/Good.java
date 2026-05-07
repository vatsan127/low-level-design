package lld._02_solid.srp;

import java.util.List;

// Each class now has exactly ONE reason to change:
//   Invoice            -> business rule for the total
//   InvoiceRepository  -> persistence
//   InvoiceMailer      -> email transport / formatting
public class Good {

    static class Invoice {
        private final List<Double> lineItems;
        private final String customerEmail;

        Invoice(List<Double> lineItems, String customerEmail) {
            this.lineItems = lineItems;
            this.customerEmail = customerEmail;
        }

        double calculateTotal() {
            double total = 0;
            for (Double item : lineItems) total += item;
            return total;
        }

        String customerEmail() { return customerEmail; }
    }

    static class InvoiceRepository {
        void save(Invoice invoice) {
            System.out.println("INSERT INTO invoices VALUES (" + invoice.calculateTotal() + ")");
        }
    }

    static class InvoiceMailer {
        void send(Invoice invoice) {
            System.out.println("To: " + invoice.customerEmail()
                    + "\nSubject: Your invoice\nTotal due: " + invoice.calculateTotal());
        }
    }

    public static void main(String[] args) {
        Invoice inv = new Invoice(List.of(10.0, 20.0, 30.0), "alice@example.com");
        new InvoiceRepository().save(inv);
        new InvoiceMailer().send(inv);
    }
}
