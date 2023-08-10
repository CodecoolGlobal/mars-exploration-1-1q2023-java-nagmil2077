package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.List;
import java.util.Random;

public class MapElementPlacerImpl implements MapElementPlacer {

    CoordinateCalculator coordinateCalculator;

    public MapElementPlacerImpl(CoordinateCalculator coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }

    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        boolean canPlace = true;
        Random random = new Random();

        int elementDimension = element.getDimension();
        String preferredLocationSymbol = element.getPreferredLocationSymbol();
        String[][] elementRepresentation = element.getRepresentation();

        // CHECK IF COORDINATE IS WITHIN MAP
        if (coordinate.x() > map.length - elementDimension || coordinate.y() > map.length - elementDimension) {
            canPlace = false;
        } else {
            // IF IT IS WITHIN MAP
            for (int i = 0; i < elementRepresentation.length; i++) {
                for (int j = 0; j < elementRepresentation[i].length; j++) {
                    // CHECK IF MAP'S CELL IS EMPTY
                    if (map[coordinate.x() + i][coordinate.y() + j].equals(" ")) {
                        if (preferredLocationSymbol.length() > 0) {
                            // IF ELEMENT HAS PREFERRED LOCATION
                            searchForPreferredLocation(map, coordinate, random, elementDimension, preferredLocationSymbol, elementRepresentation, i, j);
                        } else {
                            // IF ELEMENT DOES NOT HAVE PREFERRED LOCATION
                            map[coordinate.x() + i][coordinate.y() + j] = elementRepresentation[i][j];
                        }
                    }
                }
            }
        }

        return canPlace;
    }

    private void searchForPreferredLocation(String[][] map, Coordinate coordinate, Random random, int elementDimension, String preferredLocationSymbol, String[][] elementRepresentation, int i, int j) {
        Coordinate searchAround = new Coordinate(coordinate.x() + i, coordinate.y() + j);
        List<Coordinate> adjacentCoordinates = (List<Coordinate>) coordinateCalculator.getAdjacentCoordinates(searchAround, elementDimension);
        for (Coordinate adjCord : adjacentCoordinates) {
            if (adjCord.x() > 0 && adjCord.x() < 25 && adjCord.y() > 0 && adjCord.y() < 25 && map[adjCord.x()][adjCord.y()].equals(preferredLocationSymbol)) {
                findPreferredLocations(map, random, elementRepresentation, i, j, adjCord);
            }
        }
    }

    private void findPreferredLocations(String[][] map, Random random, String[][] elementRepresentation, int i, int j, Coordinate adjCord) {
        Coordinate match = new Coordinate(adjCord.x(), adjCord.y());
        List<Coordinate> neighbouringCoordinates = (List<Coordinate>) coordinateCalculator.getAdjacentCoordinates(match, 1);
        putElementNextToPreferredLocation(map, random, elementRepresentation, i, j, neighbouringCoordinates);
    }

    private void putElementNextToPreferredLocation(String[][] map, Random random, String[][] elementRepresentation, int i, int j, List<Coordinate> neighbouringCoordinates) {
        Coordinate pickedCoordinate = neighbouringCoordinates.get(random.nextInt(neighbouringCoordinates.size()));
        if (map[pickedCoordinate.x()][pickedCoordinate.y()].equals(" ")) {
            map[pickedCoordinate.x()][pickedCoordinate.y()] = elementRepresentation[i][j];
        } else {
            putElementNextToPreferredLocation(map, random, elementRepresentation, i, j, neighbouringCoordinates);
        }
    }

    @Override
    public void placeElement(MapElement element, String[][] map, Coordinate coordinate) {

    }
}
