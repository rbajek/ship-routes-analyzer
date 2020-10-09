package com.logindex.ship.routes.analyzer.math;

import com.logindex.ship.routes.analyzer.model.Point;

/**
 * @author Rafał Bajek
 */
public interface Distance {
    double calculate(Point point1, Point point2);
}
