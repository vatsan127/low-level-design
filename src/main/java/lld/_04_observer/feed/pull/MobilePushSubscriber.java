package lld._04_observer.feed.pull;

// This subscriber wants different data than the email one -- handle and post count.
// In the push model, the subject would have to push everything every time.
// In pull, each observer queries only what it needs.
public class MobilePushSubscriber implements Subscriber {
    private final String deviceId;

    public MobilePushSubscriber(String deviceId) { this.deviceId = deviceId; }

    @Override
    public void update(SocialAccount account) {
        System.out.println("  -> push to " + deviceId
                + ": " + account.handle() + " has now made " + account.totalPosts() + " posts");
    }
}
