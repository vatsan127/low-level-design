package lld._04_observer.feed.pull;

import java.util.ArrayList;
import java.util.List;

public class SocialAccount {
    private final String handle;
    private final List<Subscriber> subscribers = new ArrayList<>();
    private String latestPost;
    private int totalPosts;

    public SocialAccount(String handle) { this.handle = handle; }

    public void subscribe(Subscriber s)   { subscribers.add(s); }
    public void unsubscribe(Subscriber s) { subscribers.remove(s); }

    public String handle()        { return handle; }
    public String latestPost()    { return latestPost; }
    public int totalPosts()       { return totalPosts; }

    public void publish(String post) {
        this.latestPost = post;
        this.totalPosts++;
        System.out.println("[" + handle + "] posted: " + post);
        for (Subscriber s : subscribers) {
            s.update(this); // pull: pass self so the observer can query whatever it cares about
        }
    }
}
