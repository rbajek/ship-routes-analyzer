package com.logindex.ship.routes.analyzer.math;

import com.logindex.ship.routes.analyzer.model.Point;

/**
 * Implementation of Haversine formula
 *
 * <p>Calculates distance between two geo point in kilometer</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">Description of Haversine formula</a>
 *
 * @author Rafał Bajek
 */
public class HaversizeDistance implements Distance {

    private static final double EARTH_RADIUS_IN_KILOMETERS = 6371;

    @Override
    public double calculate(Point point1, Point point2) {
        // apply formulae
        double innerCalculation = Math.pow(Math.sin(Math.toRadians(point2.getLatitude() - point1.getLatitude()) / 2), 2) +
                Math.pow(Math.sin(Math.toRadians(point2.getLongitude() - point1.getLongitude()) / 2), 2) *
                        Math.cos(Math.toRadians(point1.getLatitude())) *
                        Math.cos(Math.toRadians(point2.getLatitude()));

        double calcResult = 2 * Math.asin(Math.sqrt(innerCalculation));
        return EARTH_RADIUS_IN_KILOMETERS * calcResult;
    }
}
