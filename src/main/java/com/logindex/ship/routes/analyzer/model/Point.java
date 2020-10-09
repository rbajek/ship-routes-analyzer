package com.logindex.ship.routes.analyzer.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Point with location
 */
@ToString
@Getter
@RequiredArgsConstructor
public class Point {

    /**
     * longitude of geo coordinate
     */
    private final double longitude;

    /**
     * latitude of geo coordinate
     */
    private final double latitude;

    /**
     * Instantaneous Speed of a Ship
     */
    private final double instantaneousSpeed;
}
