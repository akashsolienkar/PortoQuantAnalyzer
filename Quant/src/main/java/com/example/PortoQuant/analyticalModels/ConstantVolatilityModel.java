package com.example.portoquant.analyticalmodels;

/**
 * A model that provides a fixed (constant) volatility value over time.
 * <p>
 * This is often used as a baseline or fallback model in simulations
 * like Geometric Brownian Motion (GBM), where volatility (σ) remains unchanged
 * across time steps.
 * </p>
 * 
 * Example use case: constant-volatility Monte Carlo simulations.
 * 
 * @author akashsolienkar
 */
public class ConstantVolatilityModel implements VolatilityModel {

    /**
     * The constant annualized volatility (σ) to be returned at each time step.
     */
    private final double volatility;

    /**
     * Constructs the model with a given constant volatility.
     *
     * @param volatility the annualized volatility value to return (e.g., 0.2 for 20%)
     */
    public ConstantVolatilityModel(double volatility) {
        this.volatility = volatility;
    }

    /**
     * Returns the same volatility value regardless of the simulation time step.
     *
     * @param timeStep the current step in the simulation (ignored here)
     * @return the constant volatility (σ)
     */
    @Override
    public double getValue(int timeStep) {
        return volatility;
    }
}
