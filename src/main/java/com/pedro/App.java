package com.pedro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        if (args.length != 1) {
            System.err.println("A properties file is needed. Please give the absolute path");
            System.exit(1);
        }

        try {
            var config = new Config(args[0]);

            ExecutorService executor = Executors.newFixedThreadPool(config.getConcurrentConnections());
            List<Future<Void>> futures = new ArrayList<>();

            config.getTablesConfig().forEach(c -> {
                Callable<Void> task = new TableGenerator(TableGenerator.randomString(), c.columns(), c.rows(), c.types(), config);
                Future<Void> future = executor.submit(task);
                futures.add(future);
            });

            // Wait for all tasks to complete
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("Failed loading database: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            executor.shutdown();

        } catch (Exception e) {
            System.err.println("Failed loading database: " + e.getMessage());
            System.exit(1);
        }
    }
}
