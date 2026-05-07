package lld._04_observer.feed.pull;

public class PullDemo {
    public static void main(String[] args) {
        SocialAccount creator = new SocialAccount("@srivatsan");

        Subscriber email  = new EmailSubscriber("alice@example.com");
        Subscriber mobile = new MobilePushSubscriber("device-42");

        creator.subscribe(email);
        creator.subscribe(mobile);

        creator.publish("Just learned the Observer pattern.");
        creator.publish("Now writing the Java example.");
    }
}
