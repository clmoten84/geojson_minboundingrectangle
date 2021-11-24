package com.maxar.geojsonapp.controller;

import com.maxar.geojsonapp.service.IGeoJsonService;
import org.geojson.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "geojson")
public class GeoJsonController {

    private final IGeoJsonService geoJsonService;

    @Autowired
    public GeoJsonController(IGeoJsonService geoJsonService) {
        this.geoJsonService = geoJsonService;
    }

    /**
     * Computes and returns the minimum bounding rectangle (MBR) for the arg geojson Polygon
     * @param polygon geojson Polygon to computer MBR for
     * @param req request
     * @return minimum bounding rectangle (MBR) polygon
     */
    @PostMapping(value = "mbr")
    public Polygon computeMininumBoundingRectange(@RequestBody Polygon polygon, HttpServletRequest req) {
        return null;
    }
}
