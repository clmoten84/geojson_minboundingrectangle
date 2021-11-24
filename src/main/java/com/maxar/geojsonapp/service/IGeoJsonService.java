package com.maxar.geojsonapp.service;

import org.geojson.Polygon;

public interface IGeoJsonService {

    /**
     * Computes the minimum bounding rectangle of the arg geojson Polygon
     * @param polygon polygon to compute MBR on
     * @return MBR Polygon
     */
    public Polygon computeMinimumBoundingRectangle(Polygon polygon);
}
