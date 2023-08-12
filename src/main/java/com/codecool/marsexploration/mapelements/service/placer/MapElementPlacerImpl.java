package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.mapelements.model.MapElement;

public class MapElementPlacerImpl implements MapElementPlacer {
    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        int elementDimension = element.getDimension();
        String[][] elementRepresentation = element.getRepresentation();

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

        for (int i = 0; i < elementRepresentation.length; i++) {
            for (int j = 0; j < elementRepresentation[i].length; j++) {
                if (map[coordinate.x() + i][coordinate.y() + j].equals(" ")) {
                    map[coordinate.x() + i][coordinate.y() + j] = elementRepresentation[i][j];
                }
            }
        }
    }
}
