package net.geedge.common.utils;

import cn.hutool.core.util.ObjectUtil;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/05/21/17:37
 */
public class GeoUtil {
    public static final String GEO_JSON_PATH = "./config/geolocation.json";

    private static SimpleFeatureCollection featureCollection;

    static {
        try {
            String str = Files.readString(Paths.get(GEO_JSON_PATH), StandardCharsets.UTF_8);
            FeatureJSON featureJSON = new FeatureJSON();
            featureCollection = (SimpleFeatureCollection)featureJSON.readFeatureCollection(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,Object> reverseGeocode(Object longitude, Object latitude){
        Map result = Tool.MapUtil.newHashMap();
        if(ObjectUtil.isEmpty(longitude)||ObjectUtil.isEmpty(latitude)){
            return result;
        }

        double lon  = new Double(longitude.toString());
        double lat = new Double(latitude.toString());

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate coordinate = new Coordinate(lon, lat);
        Point point = geometryFactory.createPoint(coordinate);
        SimpleFeatureIterator iterator = featureCollection.features();
        try {
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                if (geometry.contains(point)) {
                    String country = feature.getAttribute("COUNTRY").toString();
                    String superAdministrativeArea = feature.getAttribute("NAME_1").toString();
                    String administrativeArea = feature.getAttribute("NAME_2").toString();
                    result.put("country",country);
                    result.put("superAdministrativeArea",superAdministrativeArea);
                    result.put("administrativeArea",administrativeArea);
                    return result;
                }
            }
        } finally {
            iterator.close();
        }
        return result;
    }
}
