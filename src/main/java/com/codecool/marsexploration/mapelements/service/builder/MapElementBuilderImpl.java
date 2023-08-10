package com.codecool.marsexploration.mapelements.service.builder;

import com.codecool.marsexploration.calculators.model.Coordinate;
import com.codecool.marsexploration.calculators.service.CoordinateCalculator;
import com.codecool.marsexploration.calculators.service.DimensionCalculator;
import com.codecool.marsexploration.mapelements.model.MapElement;

import java.util.Arrays;

public class MapElementBuilderImpl implements MapElementBuilder {

    private final DimensionCalculator dimensionCalculator;
    private final CoordinateCalculator coordinateCalculator;

    public MapElementBuilderImpl(DimensionCalculator dimensionCalculator, CoordinateCalculator coordinateCalculator) {
        this.dimensionCalculator = dimensionCalculator;
        this.coordinateCalculator = coordinateCalculator;
    }

    @Override
    public MapElement build(int size, String symbol, String name, int dimensionGrowth, String preferredLocationSymbol) {
        int dimension = dimensionCalculator.calculateDimension(size, dimensionGrowth);
        String[][] representation = new String[dimension][dimension];

        replaceNullWithEmptyStrings(representation);
        placeElementRandomlyInRepresentation(size, symbol, dimension, representation);

//        System.out.println(name);
//        for (String[] rep : representation) {
//            System.out.println(Arrays.toString(rep));
//        }

        return new MapElement(
                representation,
                name,
                dimension,
                preferredLocationSymbol);
    }

    private void replaceNullWithEmptyStrings(String[][] representation) {
        for (String[] row : representation) {
            Arrays.fill(row, " ");
        }
    }

    private void placeElementRandomlyInRepresentation(int size, String symbol, int dimension, String[][] representation) {
        for (int i = 0; i < size; i++) {
            Coordinate randomCoordinate = coordinateCalculator.getRandomCoordinate(dimension);
            if (!representation[randomCoordinate.x()][randomCoordinate.y()].equals(symbol)) {
                representation[randomCoordinate.x()][randomCoordinate.y()] = symbol;
            } else {
                i--;
            }
        }
    }
}
