package lld._04_observer.feed.push;

// PUSH model: the subject pushes the data the observer needs into update().
// The observer receives everything it cares about via the parameter, so it
// has no need to query the subject for more data.
public interface Subscriber {
    void update(String post);
}
