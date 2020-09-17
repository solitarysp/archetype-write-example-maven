package com.lethanh98.archdemo.utils;

import java.util.Arrays;
import java.util.List;

public class NIO {
    // chạy bất đồng bộ nhưng chờ chạy xong
    public static void runMultipleTaskSync(Runnable... runnables) {
        List<Runnable> runnableList = Arrays.asList(runnables);
        runMultipleTaskSync(runnableList);
    }
    // chạy bất đồng bộ không chờ chạy xong
    public static void runMultipleTaskASync(Runnable... runnables) {
        List<Runnable> runnableList = Arrays.asList(runnables);
        runMultipleTaskASync(runnableList);
    }

    public static void runMultipleTaskSync(List<Runnable> runnables) {
        runnables.parallelStream().forEach(Runnable::run);
    }
    public static void runMultipleTaskASync(List<Runnable> runnables) {
        runnables.parallelStream().forEach(runnable1 -> new Thread(runnable1).start());
    }
}
