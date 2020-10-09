package com.logindex.ship.routes.analyzer.processing;

import com.logindex.ship.routes.analyzer.math.HaversizeDistance;

/**
 * Ship route analyzer processor.
 *
 * @author Rafał Bajek
 */
public class AnalyzerProcessor {

    public void run(String cvsFilePath) {
        new ReadDataFromCVSFilePipeline()
                // first, calculate distance for each route.
                .andThen(new DistanceCalculatorPipeline(new HaversizeDistance()))

                // extracts the most popular distance (for debug, please uncomment below, and comment next)
                //.andThen(new DebugExtractMostPopularRouteByDistancePipelineDecorator(new ExtractMostPopularRouteByDistancePipeline()))
                .andThen(new ExtractMostPopularDistancePipeline())

                // selecting the most frequent route for the given distance (the most representative route)
                .andThen(new SelectMostFrequentRoutePipeline())

                // print the result
                .andThen(new PrintTheMostRepresentativeRoutePipeline())

                // run the pipeline
                .apply(cvsFilePath);
    }
}
