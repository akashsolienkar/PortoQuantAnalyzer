package com.example.portoquant.simulation;

import java.util.Map;

/**
 * Functional interface for generating a simulated asset price using a stochastic model.
 * 
 * Implementations define how a single simulation path is generated based on input parameters.
 * This interface can support models such as GBM, GARCH, or Jump-Diffusion.
 */
@FunctionalInterface
public interface SimulationRunner {

    /**
     * Generates a single simulated asset price path based on the provided parameters.
     *
     * @param params    a map of model-specific parameters. Common keys include:
     *                  "S0"    - initial asset price (Double)
     *                  "T"     - time horizon in years (Double)
     *                  "steps" - number of time steps (Integer)
     *                  "mu"    - expected return model (ExpectedReturnModel)
     *                  "sigma" - volatility model (VolatilityModel)
     * @return the final simulated asset price for this path
     */
    double generate( Map<String, Object> params);
}
