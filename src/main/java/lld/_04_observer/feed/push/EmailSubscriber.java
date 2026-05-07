package lld._04_observer.feed.push;

public class EmailSubscriber implements Subscriber {
    private final String email;

    public EmailSubscriber(String email) { this.email = email; }

    @Override
    public void update(String post) {
        System.out.println("  -> email to " + email + ": new post -- " + post);
    }
}
