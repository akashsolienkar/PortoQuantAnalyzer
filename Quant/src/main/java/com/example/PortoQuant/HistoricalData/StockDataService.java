package com.example.portoquant.historicaldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import com.example.portoquant.datasources.HttpApiClient;

/**
 * Service to fetch historical daily stock prices from Alpha Vantage API.
 * 
 * Author: akashsolienkar
 */
public class StockDataService {

    /**
     * Fetches daily closing prices for a given stock symbol using Alpha Vantage API.
     * <p>
     * Retrieves up to the last 257 trading days of data (about one year).
     * </p>
     * 
     * @param symbol stock ticker symbol (e.g., "IBM")
     * @param apiKey API key for Alpha Vantage (currently unused, replace "demo" in URL)
     * @return list of closing prices ordered from oldest to newest
     * @throws Exception if HTTP request or JSON parsing fails
     */
    public List<Double> fetchDailyPrices(String symbol, String apiKey) throws Exception {

        String urlStr = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" 
                + symbol + "&outputsize=full&apikey=" + apiKey;

        String json = new HttpApiClient.Builder()
                .url(urlStr)
                .connectTimeout(3000)
                .readTimeout(3000)
                .method("GET")
                .build()
                .execute();

        JSONObject jsonObj = new JSONObject(json);
        JSONObject timeSeries = jsonObj.getJSONObject("Time Series (Daily)");
        List<String> dates = new ArrayList<>(timeSeries.keySet());
        dates.sort(Comparator.reverseOrder());

        // Extract last 257 closing prices
        List<Double> closingPrices = new ArrayList<>();
        for (int i = 0; i < Math.min(257, dates.size()); i++) {
            String date = dates.get(i);
            double close = timeSeries.getJSONObject(date).getDouble("4. close");
            closingPrices.add(close);
        }

        // Reverse to chronological order (oldest first)
        Collections.reverse(closingPrices);

        return closingPrices;
    }
}
