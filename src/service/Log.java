package service;

public class Log {

    private static final Object LOCK = new Object();

    public static void print(String message) {
        synchronized (LOCK) {
            System.out.print(message);
        }
    }

    public static void println(String message) {
        synchronized (LOCK) {
            System.out.println(message);
        }
    }

    public static void printf(String format, Object... args) {
        synchronized (LOCK) {
            System.out.printf(format, args);
        }
    }
}
