public class Test1 {
    private static int cnt = 1;
    private final int counter;
    public Test1 () {
        cnt++;
        this.counter = cnt;

    }
    public static void foo () {
        System.out.println("foo");
    }

    public int getCounter() {
        return counter;
    }

    public static int getCnt() {
        return cnt;
    }
}


