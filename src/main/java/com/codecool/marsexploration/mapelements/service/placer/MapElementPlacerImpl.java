package com.codecool.marsexploration.mapelements.service.placer;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.ArrayList;
import java.util.List;

public class MapElementPlacerImpl implements MapElementPlacer {

    private final int maxRetries = 20;
    private final CoordinateCalculator coordinateCalculator;

    public MapElementPlacerImpl(CoordinateCalculator coordinateCalculator) {
        this.coordinateCalculator = coordinateCalculator;
    }

    @Override
    public boolean canPlaceElement(MapElement element, String[][] map, Coordinate coordinate) {
        String[][] elementRepresentation = element.getRepresentation();
        int dimension = element.getDimension();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (coordinate.x() + i >= map.length || coordinate.y() + j >= map[0].length ||
                        (!elementRepresentation[i][j].equals(" ") && !map[coordinate.x() + i][coordinate.y() + j].equals(" "))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void placeElement(MapElement element, String[][] map, Coordinate coordinate) {
        if (!canPlaceElement(element, map, coordinate)) return;

        String[][] elementRepresentation = element.getRepresentation();
        int dimension = element.getDimension();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (!elementRepresentation[i][j].equals(" ")) { //ahol nem üres
                    map[coordinate.x() + i][coordinate.y() + j] = elementRepresentation[i][j];
                }
            }
        }
    }

    public List<Coordinate> findPreferredLocations(String preferredSymbol, String[][] map) {
        List<Coordinate> preferredLocations = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].equals(preferredSymbol)) {
                    preferredLocations.add(new Coordinate(i, j));
                }
            }
        }

        return preferredLocations;
    }

    @Override
    public boolean attemptToPlaceElement(MapElement element, String[][] map) {
        if (element.getPreferredLocationSymbol() != null && !element.getPreferredLocationSymbol().isEmpty()) {
            List<Coordinate> preferredLocations = findPreferredLocations(element.getPreferredLocationSymbol(), map);

            for (Coordinate preferredLocation : preferredLocations) {
                List<Coordinate> adjacentCells =
                        coordinateCalculator.getAdjacentCoordinates(preferredLocation, element.getDimension());

                for (Coordinate adjacentCell : adjacentCells) {
                    if (canPlaceElement(element, map, adjacentCell)) {
                        placeElement(element, map, adjacentCell);
                        return true;
                    }
                }
            }

            return fallbackPlacementAttempt(element, map);
        } else {
            return fallbackPlacementAttempt(element, map);
        }
    }

    private boolean fallbackPlacementAttempt(MapElement element, String[][] map) {
        int retryCount = 0;

        while (retryCount < maxRetries) {
            Coordinate targetCoord = coordinateCalculator.getRandomCoordinate(map.length);

            if (canPlaceElement(element, map, targetCoord)) {
                placeElement(element, map, targetCoord);
                return true;
            }
            retryCount++;
        }

        return false;
    }
}

