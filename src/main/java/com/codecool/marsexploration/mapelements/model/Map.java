package com.codecool.marsexploration.mapelements.model;

public class Map {
    private String[][] representation;

    private boolean successfullyGenerated;

    public Map(String[][] representation) {
        this.representation = representation;
    }

    public boolean isSuccessfullyGenerated() {
        return successfullyGenerated;
    }

    public String[][] getRepresentation() {
        return representation;
    }

    public void setSuccessfullyGenerated(boolean successfullyGenerated) {
        this.successfullyGenerated = successfullyGenerated;
    }

    private static String createStringRepresentation(String[][] arr) {
        StringBuilder formattedMap = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                formattedMap.append(arr[i][j]);
            }

            if (i < arr.length - 1) {
                formattedMap.append("\n");
            }
        }
        return formattedMap.toString();
    }

    @Override
    public String toString() {
        return createStringRepresentation(representation);
    }
}

