package com.example.portoquant.assets;

import com.example.portoquant.analyticalmodels.ExpectedReturnModel;
import com.example.portoquant.analyticalmodels.VolatilityModel;

/**
 * Abstract base class representing a financial asset.
 * <p>
 * Contains common properties like name, type, allocation in portfolio,
 * expected return model, and volatility model.
 * </p>
 * <p>
 * Subclasses must implement methods to calculate expected return and volatility.
 * </p>
 * 
 * @author akashsolienkar
 */
public abstract class Asset {

    /**
     * Enum for supported asset types.
     */
    public static enum AssetType {
        STOCK,
        BOND,
        CASH,
        MUTUAL_FUND,
        COMMODITY,
        REAL_ESTATE,
        CRYPTO
    }

    private String name; // e.g., "Apple", "Bitcoin"
    private AssetType type;
    private double totalPrice;
    private double allocation; // Weight in portfolio (e.g., 0.25 = 25%)
    private ExpectedReturnModel expectedReturn; // Mu: annual expected return
    private VolatilityModel volatility; // Sigma: standard deviation of returns

    /**
     * Calculates or updates the expected return for this asset.
     */
    public abstract void calculateExpectedReturn();

    /**
     * Calculates or updates the volatility for this asset.
     */
    public abstract void calculateVolatility();

    /**
     * Constructor to initialize name and type of the asset.
     * 
     * @param name the asset name
     * @param type the asset type enum
     */
    public Asset(String name, AssetType type) {
        this.name = name;
        this.type = type;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getAllocation() {
        return allocation;
    }

    public void setAllocation(double allocation) {
        this.allocation = allocation;
    }

    public ExpectedReturnModel getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(ExpectedReturnModel expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    public VolatilityModel getVolatility() {
        return volatility;
    }

    public void setVolatility(VolatilityModel volatility) {
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", allocation=" + allocation +
                ", expectedReturn=" + expectedReturn +
                ", volatility=" + volatility +
                '}';
    }
}
