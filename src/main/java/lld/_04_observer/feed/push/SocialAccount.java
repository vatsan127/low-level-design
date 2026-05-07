package lld._04_observer.feed.push;

import java.util.ArrayList;
import java.util.List;

public class SocialAccount {
    private final String handle;
    private final List<Subscriber> subscribers = new ArrayList<>();

    public SocialAccount(String handle) { this.handle = handle; }

    public void subscribe(Subscriber s)   { subscribers.add(s); }
    public void unsubscribe(Subscriber s) { subscribers.remove(s); }

    public void publish(String post) {
        System.out.println("[" + handle + "] posted: " + post);
        for (Subscriber s : subscribers) {
            s.update(post); // push: hand the data straight to the observer
        }
    }
}
