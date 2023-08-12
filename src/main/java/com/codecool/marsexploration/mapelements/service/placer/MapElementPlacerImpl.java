package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.*;

public class MapElementPlacerImpl implements MapElementPlacer {

    private final CoordinateCalculator coordinateCalculator;
    private Coordinate nextToPreferredSymbol;
    private List<Coordinate> availableCoordinates = new ArrayList<>();

    public MapElementPlacerImpl(CoordinateCalculator coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }


    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        int elementDimension = element.getDimension();
        String[][] elementRepresentation = element.getRepresentation();
        String elementPreferredLocationSymbol = element.getPreferredLocationSymbol();

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
//            System.out.println("Element taken: " + counter);

            int counter1 = 0;
            for (String[] row : elementRepresentation) {
                for (String string : row) {
                    if (string.equals(" ")) {
                        counter1++;
                    }
                }
            }
//            System.out.println("Element empty: " + counter1);
//            System.out.println(elementDimension * elementDimension == counter + counter1);

            int putElementCounter = 0;
            Set<Coordinate> preferredElementsCoordinate = new HashSet<>();
            for (int i = 0; i < elementRepresentation.length; i++) {
                for (int j = 0; j < elementRepresentation[i].length; j++) {
                    if (elementPreferredLocationSymbol.equals("")) { // IF IT DOES NOT HAVE PREFERRED
                        if (map[coordinate.x() + i][coordinate.y() + j].equals(" ")) {
                            if (!elementRepresentation[i][j].equals(" ")) putElementCounter++;
                        }
                    } else { // IF IT HAS PREFERRED
                        if (map[coordinate.x() + i][coordinate.y() + j].equals(elementPreferredLocationSymbol)) {
                            if (coordinate.x() + i > 0 && coordinate.y() + j > 0 && coordinate.x() + i < map.length && coordinate.y() + j < map.length) {
                                preferredElementsCoordinate.add(new Coordinate(coordinate.x() + i, coordinate.y() + j));
                                Set<Coordinate> adjacentCoordinates = (Set<Coordinate>) coordinateCalculator.getAdjacentCoordinates(preferredElementsCoordinate, elementDimension);
                                for (Coordinate adjCoord : adjacentCoordinates) {
                                    if (adjCoord.x() + i > 0 && adjCoord.y() + j > 0 && adjCoord.x() + i < map.length && adjCoord.y() + j < map.length) {
                                        if (map[adjCoord.x() + i][adjCoord.y() + j].equals(" ")) {
                                            putElementCounter = 1;
                                            availableCoordinates.add(new Coordinate(adjCoord.x() + i, adjCoord.y() + j));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(putElementCounter == counter);
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

        if (!elementPreferredLocationSymbol.equals("")) {
            System.out.println("SIZE: " + availableCoordinates.size());
            coordinate = availableCoordinates.get(new Random().nextInt(availableCoordinates.size()));
        }

        for (int i = 0; i < elementRepresentation.length; i++) {
            for (int j = 0; j < elementRepresentation[i].length; j++) {
                if (map[coordinate.x() + i][coordinate.y() + j].equals(" ")) {
                    if (elementPreferredLocationSymbol.equals("")) {
                        map[coordinate.x() + i][coordinate.y() + j] = elementRepresentation[i][j];
                    } else {
                        System.out.println("AVAILABLE: " + availableCoordinates);
                        map[coordinate.x()][coordinate.y()] = elementRepresentation[i][j];
                    }
                }
            }
        }
        availableCoordinates = new ArrayList<>();
    }
}
