package lld._02_solid.isp;

// Split the fat interface into role-specific ones. A class implements
// only the contracts it actually fulfils. No empty bodies, no surprise
// exceptions, no client depends on methods it doesn't use.
public class Good {

    interface Workable { void work(); }
    interface Eatable  { void eat(); }
    interface Sleepable { void sleep(); }

    static class HumanWorker implements Workable, Eatable, Sleepable {
        public void work()  { System.out.println("human working"); }
        public void eat()   { System.out.println("human eating"); }
        public void sleep() { System.out.println("human sleeping"); }
    }

    static class RobotWorker implements Workable {
        public void work() { System.out.println("robot working"); }
    }

    // Code that only cares about working can take Workable -- and accept either.
    static void runShift(Workable w) { w.work(); }

    public static void main(String[] args) {
        runShift(new HumanWorker());
        runShift(new RobotWorker());
    }
}
