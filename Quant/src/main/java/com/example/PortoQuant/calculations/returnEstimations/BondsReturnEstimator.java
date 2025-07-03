package com.example.portoquant.calculations.returnestimations;

import java.util.ArrayList;
import java.util.List;

import com.example.portoquant.assets.Asset.AssetType;


/**
 * Utility class to estimate expected return (mu) and volatility (sigma)
 * from historical price data using either log or simple returns.
 */
public class BondsReturnEstimator {

    private static final int TRADING_DAYS_PER_YEAR = 252;

    /**
     * Estimates annualized return (mu) and volatility (sigma) from price data.
     *
     * @param prices List of historical daily prices
     * @param type   Asset type (used to decide log vs simple returns)
     * @return double array: { mu_annualized, sigma_annualized }
     */
    public static double[] estimateFromPrices(List<Double> prices, AssetType type) {
        if (prices == null || prices.size() < 2) {
            return new double[]{0.0, 0.0};  // Not enough data
        }

        List<Double> returns = new ArrayList<>();

        for (int i = 1; i < prices.size(); i++) {
            double pToday = prices.get(i);
            double pYesterday = prices.get(i - 1);

            if (pYesterday == 0) continue;

            double r;
            if (type == com.example.portoquant.assets.Asset.AssetType.BOND || type == com.example.portoquant.assets.Asset.AssetType.CASH) {
                // Use simple returns for bonds/cash
                r = (pToday - pYesterday) / pYesterday;
            } else {
                // Use log returns for stocks, crypto, commodities, ETFs
                r = Math.log(pToday / pYesterday);
            }

            returns.add(r);
        }

        if (returns.isEmpty()) {
            return new double[]{0.0, 0.0}; // no returns calculated
        }

        // Compute average (mean) return
        double sum = 0.0;
        for (double r : returns) sum += r;
        double mean = sum / returns.size();

        // Compute standard deviation (volatility)
        double variance = 0.0;
        for (double r : returns) {
            variance += Math.pow(r - mean, 2);
        }
        variance /= returns.size();
        double stdDev = Math.sqrt(variance);

        // Annualize results
        double muAnnual = mean * TRADING_DAYS_PER_YEAR;
        double sigmaAnnual = stdDev * Math.sqrt(TRADING_DAYS_PER_YEAR);

        return new double[]{muAnnual, sigmaAnnual};
    }
}
