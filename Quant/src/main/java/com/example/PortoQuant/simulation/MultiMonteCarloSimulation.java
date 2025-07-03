package com.example.portoquant.simulation;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Monte Carlo Simulator that runs multiple simulation paths in parallel
 * using the Fork/Join framework.
 * <p>
 * Supports user-defined simulation logic via the {@link SimulationRunner} interface
 * and allows dynamic parameter passing using a key-value {@code Map}.
 * </p>
 * <p>
 * This class is designed for financial simulations (e.g., Geometric Brownian Motion)
 * where each path is independent, making it embarrassingly parallel.
 * </p>
 * Example use case:
 * <ul>
 *   <li>Portfolio value simulation using GBM</li>
 *   <li>Risk estimation: VaR, Expected Shortfall, Volatility</li>
 * </ul>
 * @author akashsolienkar
 * @version 1.0
 */
public class MultiMonteCarloSimulation {

    /**
     * Performs the parallel Monte Carlo simulation using Fork/Join.
     *
     * @param numPaths     Number of simulation paths to run
     * @param simFunction  A functional interface defining the simulation logic
     * @param params       Simulation parameters (e.g., initial value, mu, sigma)
     * @return             Array of final simulation values (e.g., end prices)
     */
    public double[] simulate(int numPaths, SimulationRunner simFunction, Map<String, Object> params) {
        long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        MonteCarloTask rootTask = new MonteCarloTask(0, numPaths, simFunction, params);
        double[] finalOutcomes = pool.invoke(rootTask);

        long end = System.currentTimeMillis();
        System.out.printf("\nSimulated %,d paths using Fork/Join in %.2f seconds%n",
                numPaths, (end - start) / 1000.0);

        return finalOutcomes;
    }

    /**
     * A Fork/Join task that recursively splits simulation work
     * into smaller subtasks for parallel execution.
     */
    public static class MonteCarloTask extends RecursiveTask<double[]> {

        private static final int THRESHOLD = 100_000; // Task splitting threshold

        private final int start;
        private final int end;
        private final SimulationRunner simFunction;
        private final Map<String, Object> params;

        /**
         * Constructs a simulation task.
         *
         * @param start        Start index of the simulation batch
         * @param end          End index of the simulation batch
         * @param simFunction  Simulation logic to generate a single path outcome
         * @param params       Simulation parameters shared across all paths
         */
        public MonteCarloTask(int start, int end, SimulationRunner simFunction, Map<String, Object> params) {
            this.start = start;
            this.end = end;
            this.simFunction = simFunction;
            this.params = params;
        }

        /**
         * Computes the simulation batch. If the batch size is large, splits
         * into two subtasks. Otherwise, computes the simulation paths directly.
         *
         * @return An array of simulated final outcomes for this batch
         */
        @Override
        protected double[] compute() {
            int batchSize = end - start;

            if (batchSize <= THRESHOLD) {
                double[] outcomes = new double[batchSize];
                for (int i = 0; i < batchSize; i++) {
                    outcomes[i] = simFunction.generate(params);
                }
                return outcomes;

            } else {
                int mid = (start + end) / 2;
                MonteCarloTask left = new MonteCarloTask(start, mid, simFunction, params);
                MonteCarloTask right = new MonteCarloTask(mid, end, simFunction, params);
                invokeAll(left, right);

                double[] leftResult = left.join();
                double[] rightResult = right.join();

                double[] combined = new double[leftResult.length + rightResult.length];
                System.arraycopy(leftResult, 0, combined, 0, leftResult.length);
                System.arraycopy(rightResult, 0, combined, leftResult.length, rightResult.length);

                return combined;
            }
        }
    }
}
