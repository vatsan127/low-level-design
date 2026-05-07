package lld._02_solid.dip;

// DIP violation: NotificationService (high-level policy) directly
// instantiates EmailSender (low-level detail). To swap email for SMS,
// or to write a unit test without sending real emails, you must edit
// NotificationService itself.
public class Bad {

    static class EmailSender {
        void send(String to, String body) {
            System.out.println("EMAIL -> " + to + ": " + body);
        }
    }

    static class NotificationService {
        private final EmailSender sender = new EmailSender(); // hard-wired dependency

        void notifyUser(String to, String message) {
            sender.send(to, message);
        }
    }

    public static void main(String[] args) {
        new NotificationService().notifyUser("alice@example.com", "Welcome!");
    }
}
