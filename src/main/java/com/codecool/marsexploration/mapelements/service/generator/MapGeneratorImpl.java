package com.codecool.marsexploration.mapelements.service.generator;

import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.configuration.model.MapConfiguration;
import com.codecool.marsexploration.mapelements.model.Map;
import com.codecool.marsexploration.mapelements.model.MapElement;
import com.codecool.marsexploration.mapelements.service.placer.MapElementPlacer;

import java.util.List;

public class MapGeneratorImpl implements MapGenerator {

    private final MapElementsGenerator mapElementsGenerator;
    private final CoordinateCalculator coordinateCalculator;
    private final MapElementPlacer mapElementPlacer;
    private final Map map;

    public MapGeneratorImpl(Map map, MapElementsGenerator mapElementsGenerator, CoordinateCalculator coordinateCalculator, MapElementPlacer mapElementPlacer) {
        this.mapElementsGenerator = mapElementsGenerator;
        this.coordinateCalculator = coordinateCalculator;
        this.mapElementPlacer = mapElementPlacer;
        this.map = map;
    }

    @Override
    public Map generate(MapConfiguration mapConfig) {
        List<MapElement> mapElements = (List<MapElement>) mapElementsGenerator.createAll(mapConfig);

        for (MapElement mapelement : mapElements) {
            boolean canPlaceElement = callCanPlaceElementMethod(map, coordinateCalculator, mapElementPlacer, mapelement);
            if (canPlaceElement) {
                mapElementPlacer.placeElement(
                        mapelement,
                        map.getRepresentation(),
                        coordinateCalculator.getRandomCoordinate(map.getRepresentation().length));
            } else {
                callCanPlaceElementMethod(map, coordinateCalculator, mapElementPlacer, mapelement);
            }
        }

        return map;
    }

    private static boolean callCanPlaceElementMethod(Map map, CoordinateCalculator coordinateCalculator, MapElementPlacer mapElementPlacer, MapElement mapelement) {
        return mapElementPlacer.canPlaceElement(
                mapelement,
                map.getRepresentation(),
                coordinateCalculator.getRandomCoordinate(map.getRepresentation().length));
    }
}
