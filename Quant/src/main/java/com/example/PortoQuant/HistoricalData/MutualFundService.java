package com.example.portoquant.historicaldata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to fetch historical NAV data for mutual funds.
 * 
 * <p>This method is deprecated and may be replaced with a more robust API in the future.</p>
 * 
 * Author: akashsolienkar
 */
public class MutualFundService {
    
    /**
     * Fetches historical NAV data for a specific mutual fund scheme.
     * 
     * @throws IOException if network or stream reading fails
     */
    @Deprecated
    public void getHistoricalData() throws IOException {
        URL url = new URL("https://www.amfiindia.com/spages/NAVAll.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        List<Double> navHistory = new ArrayList<>();
        String scheme = "HDFC Balanced Advantage Fund - Growth";

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(scheme)) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    try {
                        double nav = Double.parseDouble(parts[3]);
                        navHistory.add(nav);
                    } catch (NumberFormatException ignored) {
                        // Ignore lines with invalid NAV values
                    }
                }
            }
        }
        reader.close();
        // navHistory contains the NAV history for the specified scheme, but is unused here
    }
}
