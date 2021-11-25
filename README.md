# Minimum Bounding Rectangle
____________________________

A simple Java/Spring Boot app for computing the minimum bounding rectangle for an argument GeoJSON polygon. App
uses the geojson-jackson library for serializing and deserializing GeoJSON geometries.

## Usage
_________
The jar file for the Spring Boot app is in the ***build_jar*** directory of the project source code (**GeoJsonApp-0.0.1-SNAPSHOT.jar**). Execute
this jar file to start the app running on localhost:8080:

`java -jar path/to/GeoJsonApp-0.0.1-SNAPSHOT.jar com.maxar.geojsonapp.GeoJsonApplication`

Once the application is running on localhost:8080, make a POST request using *curl* or a REST client (i.e. Postman, Advanced REST Client, etc.):

`POST localhost:8080/geojson/mbr`

Here is a sample input GeoJSON polygon request body:

```json
{
  "type": "Polygon",
  "coordinates": [
    [
      [
        -105.05678173,
        39.67717782
      ],
      [
        -105.12923684,
        39.71973826
      ],
      [
        -105.11209692,
        39.78921714
      ],
      [
        -105.04509542,
        39.82991355
      ],
      [
        -104.94147681,
        39.75029364
      ],
      [
        -104.87603348,
        39.80896988
      ],
      [
        -104.8316255,
        39.74849664
      ],
      [
        -104.93446502,
        39.69396535
      ],
      [
        -105.05678173,
        39.67717782
      ]
    ]
  ]
}
```

The response will be a GeoJSON polygon representing the Minimum Bounding Rectangle for the input Polygon:

```json
{
  "type": "Polygon",
  "coordinates": [
    [
      [
        -105.12923684,
        39.67717782
      ],
      [
        -105.12923684,
        39.82991355
      ],
      [
        -104.8316255,
        39.82991355
      ],
      [
        -104.8316255,
        39.67717782
      ],
      [
        -105.12923684,
        39.67717782
      ]
    ]
  ]
}
```

You can save the above input and output as JSON files and load them in an online GeoJSON visualizer such as [geojson.io](https://geojson.io/) to visualize
the results.