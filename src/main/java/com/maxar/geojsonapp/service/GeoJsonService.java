package com.maxar.geojsonapp.service;

import org.geojson.Polygon;
import org.springframework.stereotype.Service;

@Service
public class GeoJsonService implements IGeoJsonService {
    @Override
    public Polygon computeMinimumBoundingRectangle(Polygon polygon) {
        return null;
    }
}
