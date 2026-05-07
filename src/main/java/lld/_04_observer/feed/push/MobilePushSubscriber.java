package lld._04_observer.feed.push;

public class MobilePushSubscriber implements Subscriber {
    private final String deviceId;

    public MobilePushSubscriber(String deviceId) { this.deviceId = deviceId; }

    @Override
    public void update(String post) {
        System.out.println("  -> push to device " + deviceId + ": " + post);
    }
}
