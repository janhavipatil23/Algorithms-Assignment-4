package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much. TODO tidy it
 * up a bit.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Array Size - " + size);
        processArgs(args);

        for (int th = threads; th <= 64; th += th) {
            System.out.println("Threads - " + th);
            ForkJoinPool executer = new ForkJoinPool(th);
            System.out.println("Degree of Parallelism - " + executer.getParallelism());
            Random random = new Random();
            int[] array = new int[size];
            ArrayList<Long> timeList = new ArrayList<>();
            for (int j = 50; j < 100; j++) {
                ParSort.cutoff = 500 * (j + 1);
                // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                long time;
                long startTime = System.currentTimeMillis();
                for (int t = 0; t < 10; t++) {
                    for (int i = 0; i < array.length; i++)
                        array[i] = random.nextInt(10000000);
                    ParSort.sort(array, 0, array.length, executer);
                }
                long endTime = System.currentTimeMillis();
                time = (endTime - startTime);
                timeList.add(time);

                System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");

            }
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-"))
                xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N"))
            setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) // noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();
    public static Integer threads = 2;
    public static Integer size = 20000;
}