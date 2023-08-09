package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.MapElement;

public class MapElementPlacerImpl implements MapElementPlacer{
    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        return false;
    }

    @Override
    public void placeElement(MapElement element, String[][] map, Coordinate coordinate) {

    }
}
