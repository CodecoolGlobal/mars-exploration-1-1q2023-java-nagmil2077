package com.codecool.marsexploration.mapelements.service.generator;

import com.codecool.marsexploration.calculators.model.Coordinate;
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
        List<MapElement> mapElements = mapElementsGenerator.createAll(mapConfig);
        System.out.println("Number of elements: " + mapElements.size());

        for (MapElement mapelement : mapElements) {
            Coordinate randomCoordinate = coordinateCalculator.getRandomCoordinate(mapelement.getDimension());
            int counter = 0;
            while (counter < 500 && !mapElementPlacer.canPlaceElement(
                    mapelement,
                    map.getRepresentation(),
                    randomCoordinate)) {
                randomCoordinate = coordinateCalculator.getRandomCoordinate(mapelement.getDimension());
                counter++;
            }

            mapElementPlacer.attemptToPlaceElement(
                    mapelement,
                    map.getRepresentation()
                    //coordinateCalculator.getRandomCoordinate(mapelement.getDimension())
                    );
        }

        return map;
    }
}
