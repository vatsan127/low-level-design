package lld._02_solid.dip;

// Both high-level (NotificationService) and low-level (EmailSender,
// SmsSender, ...) depend on the abstraction (MessageSender). The
// concrete implementation is injected at construction time, so the
// service is decoupled from any specific transport.
public class Good {

    interface MessageSender {
        void send(String to, String body);
    }

    static class EmailSender implements MessageSender {
        public void send(String to, String body) {
            System.out.println("EMAIL -> " + to + ": " + body);
        }
    }

    static class SmsSender implements MessageSender {
        public void send(String to, String body) {
            System.out.println("SMS -> " + to + ": " + body);
        }
    }

    static class NotificationService {
        private final MessageSender sender;

        NotificationService(MessageSender sender) { // dependency injection
            this.sender = sender;
        }

        void notifyUser(String to, String message) {
            sender.send(to, message);
        }
    }

    public static void main(String[] args) {
        new NotificationService(new EmailSender()).notifyUser("alice@example.com", "Welcome!");
        new NotificationService(new SmsSender()).notifyUser("+15555550123", "Welcome!");
    }
}
