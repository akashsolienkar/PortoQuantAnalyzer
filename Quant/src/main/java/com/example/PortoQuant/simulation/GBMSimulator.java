package com.example.portoquant.simulation;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import com.example.portoquant.analyticalmodels.ExpectedReturnModel;
import com.example.portoquant.analyticalmodels.VolatilityModel;
import com.example.portoquant.datamodels.SimulationResult;

/**
 * Utility class to simulate Geometric Brownian Motion (GBM) paths for asset prices.
 * <p>
 * GBM models asset prices with the formula:
 * S(t+1) = S(t) * exp[(μ - 0.5 * σ²) * Δt + σ * sqrt(Δt) * Z]
 * where Z is a standard normal random variable.
 * </p>
 * 
 * Author: akashsolienkar
 */
public class GBMSimulator implements SimulationRunner {

    private final Random random;

    /**
     * Constructs a GBMSimulator with a random seed.
     */
    public GBMSimulator() {
        this.random = new Random();
    }

    /**
     * Constructs a GBMSimulator with a specific seed for reproducibility.
     * 
     * @param seed random seed
     */
    public GBMSimulator(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Simulates a single GBM price path.
     * 
     * @param steps number of time steps
     * @param initialPrice starting price of the asset
     * @param mu expected return (annualized)
     * @param sigma volatility (annualized)
     * @param timeHorizon time horizon in years (e.g., 1.0 for one year)
     * @return array of simulated prices, length = steps + 1
     */
    @Override
	public double generate(Map<String,Object> params) 
    	{
    		int steps =(int) params.get("steps"); 
    		double initialPrice=(double) params.get("initialPrice");
    		ExpectedReturnModel mu=(ExpectedReturnModel) params.get("mu");
    		VolatilityModel sigma =(VolatilityModel) params.get("sigma");
    		double timeHorizon =(double) params.get("timeHorizon");
		
//    		 double[] prices = new double[steps + 1];
//    	        prices[0] = initialPrice;
    			double value = initialPrice;
    	        double dt = timeHorizon / steps;

    	        for (int i = 1; i <= steps; i++) {
//    	            double z = random.nextGaussian(); // Standard normal
//    	            double drift = (mu - 0.5 * sigma * sigma) * dt;
//    	            double diffusion = sigma * Math.sqrt(dt) * z;
    	            
                    double z = random.nextGaussian(); // Random shock
//                  // Apply GBM step
                  value *= Math.exp((mu.getValue(i) - 0.5 * sigma.getValue(i) * sigma.getValue(i)) * dt + sigma.getValue(i) * Math.sqrt(dt) * z);

//    	            prices[i] = prices[i - 1] * Math.exp(drift + diffusion);
    	        }

    	        return value;
	}
    
    /**
     * Calculates financial risk metrics from the simulated portfolio outcomes.
     *
     * @param outcomes        array of final simulated portfolio values
     * @param confidenceLevel confidence level for VaR and Expected Shortfall (e.g. 0.95)
     * @param S0              initial portfolio value
     */
    public SimulationResult computeRiskMetrics(double[] outcomes, double confidenceLevel, double S0) {
        Arrays.sort(outcomes); // Sort outcomes for percentile-based risk metrics

        // Final average value across all simulations
        double mean = Arrays.stream(outcomes).average().orElse(0.0);

        // Portfolio volatility (standard deviation)
        double stdDev = Math.sqrt(Arrays.stream(outcomes)
                .map(x -> Math.pow(x - mean, 2))
                .average().orElse(0.0));

        // Value at Risk (VaR): how much loss to expect in the worst (1 - confidence) % of cases
        double var = outcomes[(int) ((1 - confidenceLevel) * outcomes.length)];

        // Expected Shortfall: average of losses worse than the VaR
        double expectedShortfall = Arrays.stream(outcomes)
                .limit((int) ((1 - confidenceLevel) * outcomes.length))
                .average().orElse(0.0);

        // Probability of Bankruptcy: percentage of paths where portfolio went to zero or below
        long bankrupt = Arrays.stream(outcomes).filter(v -> v <= 0).count();

        // Print computed metrics
        System.out.println("\n Risk Metrics:");
        System.out.printf(" Final Mean Portfolio Value: ₹%.2f%n", mean);
        System.out.printf(" Portfolio Volatility: %.2f%%%n", (stdDev / mean) * 100);
        System.out.printf(" Value at Risk (%.0f%%): ₹%.2f%n", confidenceLevel * 100, S0 - var);
        System.out.printf(" Expected Shortfall: ₹%.2f%n", S0 - expectedShortfall);
        System.out.printf("️  Probability of Bankruptcy: %.4f%%%n", 100.0 * bankrupt / outcomes.length);
        
        return new SimulationResult(var, S0 - expectedShortfall,  100.0 * bankrupt / outcomes.length, (stdDev / mean) * 100);
    }
}
