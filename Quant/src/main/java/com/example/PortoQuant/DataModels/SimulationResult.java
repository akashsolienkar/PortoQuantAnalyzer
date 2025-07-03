package com.example.portoquant.datamodels;

/**
 * Encapsulates the results from a financial simulation.
 * <p>
 * Includes key risk metrics such as Value at Risk (VaR),
 * Expected Shortfall, Probability of Bankruptcy, and volatility.
 * </p>
 * 
 * @author akashsolienkar
 */
public class SimulationResult {

    /**
     * The 5th percentile loss value (Value at Risk).
     */
    private double valueAtRisk;

    /**
     * Mean loss beyond the Value at Risk (Expected Shortfall).
     */
    private double expectedShortfall;

    /**
     * Probability of portfolio value falling below zero (bankruptcy).
     */
    private double probabilityOfBankruptcy;

    /**
     * Standard deviation of final portfolio values (volatility).
     */
    private double volatility;

    /**
     * Constructs a SimulationResult with specified risk metrics.
     * 
     * @param valueAtRisk the 5th percentile loss value
     * @param expectedShortfall the mean loss beyond VaR
     * @param probabilityOfBankruptcy probability of bankruptcy (fraction)
     * @param volatility standard deviation of final portfolio values
     */
    public SimulationResult(double valueAtRisk, double expectedShortfall, double probabilityOfBankruptcy, double volatility) {
        this.valueAtRisk = valueAtRisk;
        this.expectedShortfall = expectedShortfall;
        this.probabilityOfBankruptcy = probabilityOfBankruptcy;
        this.volatility = volatility;
    }

    public double getValueAtRisk() {
        return valueAtRisk;
    }

    public double getExpectedShortfall() {
        return expectedShortfall;
    }

    public double getProbabilityOfBankruptcy() {
        return probabilityOfBankruptcy;
    }

    public double getVolatility() {
        return volatility;
    }

    @Override
    public String toString() {
        return "SimulationResult{" +
                "valueAtRisk=" + valueAtRisk +
                ", expectedShortfall=" + expectedShortfall +
                ", probabilityOfBankruptcy=" + probabilityOfBankruptcy +
                ", volatility=" + volatility +
                '}';
    }
}
