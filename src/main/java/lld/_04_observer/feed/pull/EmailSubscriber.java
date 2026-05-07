package lld._04_observer.feed.pull;

// Email subscriber only cares about the latest post text.
public class EmailSubscriber implements Subscriber {
    private final String email;

    public EmailSubscriber(String email) { this.email = email; }

    @Override
    public void update(SocialAccount account) {
        System.out.println("  -> email to " + email + ": " + account.latestPost());
    }
}
