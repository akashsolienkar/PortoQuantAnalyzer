package com.example.portoquant.analyticalmodels;

/**
 * Interface representing a model for retrieving volatility values
 * at different simulation time steps.
 * <p>
 * This can be used for both constant and time-varying volatility implementations
 * in financial simulations such as Monte Carlo risk forecasting.
 * </p>
 * 
 * @author akashsolienkar
 */
public interface VolatilityModel {

    /**
     * Returns the volatility value at the specified time step.
     *
     * @param timeStep the simulation step index
     * @return the volatility at the given time step
     */
    double getValue(int timeStep);
}
