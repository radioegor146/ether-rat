package me.Eclipse.Utils;

public class Timer {
    private static long PrevMS;

    public Timer() {
        PrevMS = 0L;
    }

    public static boolean delay(float var0) {
        return (float) (getTime() - PrevMS) >= var0;
    }

    public static long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public static void reset() {
        PrevMS = getTime();
    }
}
