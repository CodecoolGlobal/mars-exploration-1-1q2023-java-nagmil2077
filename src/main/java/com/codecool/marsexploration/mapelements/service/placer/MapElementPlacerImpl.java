package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.List;

public class MapElementPlacerImpl implements MapElementPlacer {

    private final CoordinateCalculator coordinateCalculator;

    public MapElementPlacerImpl(CoordinateCalculator coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }


    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        int elementDimension = element.getDimension();
        String[][] elementRepresentation = element.getRepresentation();
        System.out.println(element.getName());

        if (coordinate.x() <= map.length - elementDimension && coordinate.y() <= map.length - elementDimension) {
            int counter = 0;
            for (String[] row : elementRepresentation) {
                for (String string : row) {
                    if (!string.equals(" ")) {
                        counter++;
                    }
                }
            }
            System.out.println("Element taken: " + counter);

            int counter1 = 0;
            for (String[] row : elementRepresentation) {
                for (String string : row) {
                    if (string.equals(" ")) {
                        counter1++;
                    }
                }
            }
            System.out.println("Element empty: " + counter1);
            System.out.println(elementDimension * elementDimension == counter + counter1);

            int putElementCounter = 0;
            for (int i = 0; i < elementRepresentation.length; i++) {
                for (int j = 0; j < elementRepresentation[i].length; j++) {
                    if (map[coordinate.x() + i][coordinate.y() + j].equals(" ")) {
                        if (!elementRepresentation[i][j].equals(" ")) putElementCounter++;
                    }
                }
            }

            if (putElementCounter != counter) {
                return false;
            } else {
                System.out.println(putElementCounter);
                System.out.println("--------------------------------");
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void placeElement(MapElement element, String[][] map, Coordinate coordinate) {
        String[][] elementRepresentation = element.getRepresentation();
        String elementPreferredLocationSymbol = element.getPreferredLocationSymbol();
        int elementDimension = element.getDimension();

        for (int i = 0; i < elementRepresentation.length; i++) {
            for (int j = 0; j < elementRepresentation[i].length; j++) {
                if (map[coordinate.x() + i][coordinate.y() + j].equals(" ")) {
                    if (elementPreferredLocationSymbol.equals("")) {
                        map[coordinate.x() + i][coordinate.y() + j] = elementRepresentation[i][j];
                    } else {
                        List<Coordinate> adjacentCoordinates = (List<Coordinate>) coordinateCalculator.getAdjacentCoordinates(coordinate, elementDimension);
                        for (Coordinate adjacentCoordinate : adjacentCoordinates) {
                            if ((adjacentCoordinate.x() + i) > 0 && (adjacentCoordinate.y() + j) > 0 && (adjacentCoordinate.x() + i) < map.length && (adjacentCoordinate.y() + j) < map.length) {
                                if (map[adjacentCoordinate.x() + i][adjacentCoordinate.y() + j].equals(elementPreferredLocationSymbol)) {
                                    map[adjacentCoordinate.x() + i][adjacentCoordinate.y() + j] = elementRepresentation[i][j];
                                } else {
                                    map[coordinate.x() + i][coordinate.y() + j] = elementRepresentation[i][j];
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
