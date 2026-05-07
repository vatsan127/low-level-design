package lld._02_solid.isp;

// ISP violation: a single fat interface forces every implementer to
// provide all methods, even ones that don't apply. RobotWorker has
// no business eating or sleeping, so it throws -- a code smell.
//
// The principle phrased precisely: clients should not be forced to
// depend on methods they do not use. Here a "client" is anything
// that holds a Worker reference -- e.g. a CafeteriaScheduler that
// only cares about eat() will still receive a RobotWorker and blow
// up if it ever calls eat() on it. The fat interface makes that
// failure compile cleanly.
public class Bad {

    interface Worker {
        void work();
        void eat();
        void sleep();
    }

    static class HumanWorker implements Worker {
        public void work()  { System.out.println("human working"); }
        public void eat()   { System.out.println("human eating"); }
        public void sleep() { System.out.println("human sleeping"); }
    }

    static class RobotWorker implements Worker {
        public void work() { System.out.println("robot working"); }
        // Forced to implement methods that make no sense.
        public void eat()   { throw new UnsupportedOperationException("robots do not eat"); }
        public void sleep() { throw new UnsupportedOperationException("robots do not sleep"); }
    }

    public static void main(String[] args) {
        new HumanWorker().work();
        new RobotWorker().work();
        // new RobotWorker().eat();   // would blow up at runtime
    }
}
