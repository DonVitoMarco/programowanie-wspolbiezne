package pl.marek;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class App {

    private static final boolean PRINT = true;

    public static void main(String[] args) {
        boolean concurrency = args.length == 0;
        if (concurrency) {
            concurrency();
        } else {
            normal();
        }

    }

    public static void normal() {
        long start = System.currentTimeMillis();

        String seq = "1234567890";

        Set<String> permutations = new Permutation().permutation(seq);

        List<Set<String>> results = new ArrayList<>();
        for (String p : permutations) {
            results.add(new Finder(p).call());
        }

        int counter = 0;
        for (Set<String> result : results) {
            counter += result.size();
            if (PRINT) {
                result.forEach(System.out::println);
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("Took : " + ((end - start) / 1000) + " seconds");
        System.out.println("Result : " + counter);
    }

    public static void concurrency() {
        long start = System.currentTimeMillis();

        String seq = "1234567890";

        Set<String> permutations = new Permutation().permutation(seq);

        ForkJoinPool pool = ForkJoinPool.commonPool();
        List<Future<Set<String>>> results = new ArrayList<>();

        for (String p : permutations) {
            results.add(pool.submit(new Finder(p)));
        }

        ForkJoinPool.commonPool().awaitQuiescence(1L, TimeUnit.MINUTES);

        int counter = 0;
        for (Future<Set<String>> result : results) {
            try {
                counter += result.get().size();
                if (PRINT) {
                    result.get().forEach(System.out::println);
                }
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("Took : " + ((end - start) / 1000) + " seconds");
        System.out.println("Result : " + counter);
    }
}
