package com.logindex.ship.routes.analyzer;

import com.logindex.ship.routes.analyzer.processing.AnalyzerProcessor;

/**
 * Main class (start application)
 *
 * @author Rafał Bajek
 */
public class ShipRoutesAnalyzer {

    public static void main(String[] args) {
        String csvFilePath = (args != null && args.length > 0) ? args[0] : "src/main/resources/DEBRV_DEHAM_historical_routes.csv";
        new AnalyzerProcessor().run(csvFilePath);
    }
}
