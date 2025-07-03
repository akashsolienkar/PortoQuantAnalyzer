package com.example.portoquant.analyticalmodels;

import java.util.List;

/**
 * Model providing a time-varying expected return value.
 * <p>
 * Returns expected return for a given time step; if the time step exceeds
 * the available data, it returns the last known expected return value.
 * </p>
 * 
 * Author: akashsolienkar
 */
public class TimeVaryingExpectedReturnModel implements ExpectedReturnModel {

    private final List<Double> expectedReturn;

    /**
     * Constructs a time-varying expected return model.
     * 
     * @param expectedReturn list of expected return values indexed by time step
     */
    public TimeVaryingExpectedReturnModel(List<Double> expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    /**
     * Gets the expected return value at the specified time step.
     * 
     * @param timeStep time step index (0-based)
     * @return expected return at the time step, or last value if index exceeds list size
     */
    @Override
    public double getValue(int timeStep) {
        if (timeStep >= expectedReturn.size()) {
            return expectedReturn.get(expectedReturn.size() - 1);
        }
        return expectedReturn.get(timeStep);
    }
}
