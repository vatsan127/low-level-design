package lld._04_observer.feed.pull;

// PULL model: the subject only signals "something changed". The observer
// holds the subject reference and pulls whatever data it actually needs.
public interface Subscriber {
    void update(SocialAccount account);
}
