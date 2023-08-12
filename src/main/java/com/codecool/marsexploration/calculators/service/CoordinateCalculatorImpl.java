package com.codecool.marsexploration.calculators.service;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.*;

public class CoordinateCalculatorImpl implements CoordinateCalculator {

    @Override
    public Coordinate getRandomCoordinate(int dimension) {
        Random random = new Random();
        int x = random.nextInt(dimension);
        int y = random.nextInt(dimension);
        return new Coordinate(x, y);
    }

    @Override
    public Iterable<Coordinate> getAdjacentCoordinates(Coordinate coordinate, int dimension) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = -dimension; i <= dimension; i++) {
            for (int j = -dimension; j <= dimension; j++) {
                coordinates.add(new Coordinate(coordinate.x() + i, coordinate.y() + j));
            }
        }
        return coordinates;
    }

    @Override
    public Iterable<Coordinate> getAdjacentCoordinates(Iterable<Coordinate> coordinates, int dimension) {
        Set<Coordinate> resultList = new HashSet<>();
        for (Coordinate coord1 : coordinates) {
            Iterable<Coordinate> adjacentCoordList = getAdjacentCoordinates(coord1, dimension);
            for (Coordinate coord2 : adjacentCoordList) {
                resultList.add(coord2);
            }
        }
        return resultList;
    }
}
