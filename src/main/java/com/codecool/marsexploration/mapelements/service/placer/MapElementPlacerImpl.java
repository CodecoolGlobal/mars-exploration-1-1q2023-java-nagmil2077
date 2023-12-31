package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.Arrays;

public class MapElementPlacerImpl implements MapElementPlacer {
    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        int elementDimension = element.getDimension();
        return coordinate.x() <= map.length - elementDimension && coordinate.y() <= map.length - elementDimension;
    }

    @Override
    public void placeElement(MapElement element, String[][] map, Coordinate coordinate) {
        String[][] elementRepresentation = element.getRepresentation();

        for (int i = 0; i < elementRepresentation.length; i++) {
            for (int j = 0; j < elementRepresentation[i].length; j++) {
                if (map[coordinate.x() + i][coordinate.y() + j].equals(" ")) {
                    map[coordinate.x() + i][coordinate.y() + j] = elementRepresentation[i][j];
                }
            }
        }
    }
}
