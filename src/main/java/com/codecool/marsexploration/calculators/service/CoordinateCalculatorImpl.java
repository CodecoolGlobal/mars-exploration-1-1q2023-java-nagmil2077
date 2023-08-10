package com.codecool.marsexploration.calculators.service;

import com.codecool.marsexploration.calculators.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CoordinateCalculatorImpl implements CoordinateCalculator {

    @Override
    public Coordinate getRandomCoordinate(int dimension) {
        Random random = new Random();
        int x = random.nextInt(dimension);
        int y = random.nextInt(dimension);
        return new Coordinate(x, y);
    }

    @Override
    public List<Coordinate> getAdjacentCoordinates(Coordinate coordinate, int dimension) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = -dimension; i <= dimension; i++) {
            for (int j = -dimension; j <= dimension; j++) {
                coordinates.add(new Coordinate(coordinate.x() + i, coordinate.y() + j));
            }
        }
        return coordinates;
    }

    @Override
    public List<Coordinate> getAdjacentCoordinates(Iterable<Coordinate> coordinates, int dimension) {
        List<Coordinate> resultList = new ArrayList<>();
        for (Coordinate coord1 : coordinates) {
            Iterable<Coordinate> adjacentCoordList = getAdjacentCoordinates(coord1, dimension);
            for (Coordinate coord2 : adjacentCoordList) {
                resultList.add(coord2);
            }
        }
        return resultList;
    }
}
