package lld._04_observer.feed.push;

public class PushDemo {
    public static void main(String[] args) {
        SocialAccount creator = new SocialAccount("@srivatsan");

        Subscriber email  = new EmailSubscriber("alice@example.com");
        Subscriber mobile = new MobilePushSubscriber("device-42");

        creator.subscribe(email);
        creator.subscribe(mobile);

        creator.publish("Just learned the Observer pattern.");

        creator.unsubscribe(email);
        creator.publish("Now writing the Java example.");
    }
}
